package com.example.android.stakdice.firebase;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.stakdice.activities.main.MainActivity;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.attribute.Attribute;
import com.example.android.stakdice.models.attribute.RangeValueAttribute;
import com.example.android.stakdice.models.attribute.SimpleValueAttribute;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ConvertDocumentsToModels extends AsyncTask<DocumentSnapshot, Void, List<StakCard>> {

    public interface Listener {
        void onStakCardsConverted(List<StakCard> stakCards);
    }

    private Listener listener;

    public ConvertDocumentsToModels(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected List<StakCard> doInBackground(DocumentSnapshot... documentSnapshots) {
        List<StakCard> stakCards = new ArrayList<>(documentSnapshots.length);
        for (DocumentSnapshot documentSnapshot : documentSnapshots) {
            try {
                stakCards.add(getStakCardFromDocument(documentSnapshot));
            } catch (Exception e) {
                Log.d(MainActivity.class.getSimpleName(), "Error converting firebase cards to local cards");
            }
        }
        return stakCards;
    }

    @Override
    protected void onPostExecute(List<StakCard> stakCards) {
        super.onPostExecute(stakCards);
        listener.onStakCardsConverted(stakCards);
    }

    private StakCard getStakCardFromDocument(DocumentSnapshot document) throws InterruptedException, ExecutionException, TimeoutException {
        return new StakCard(document.getId(),
                document.getString("image_resource_url"),
                document.getString("name"),
                getAttributeFromDocumentReference(document.getDocumentReference("strength_attribute")),
                getAttributeFromDocumentReference(document.getDocumentReference("toughness_attribute")),
                getAttributeFromDocumentReference(document.getDocumentReference("attack_attribute")),
                getAttributeFromDocumentReference(document.getDocumentReference("knowledge_attribute")),
                document.getString("difficulty"),
                false,
                document.getString("description"));
    }

    // because I use documentReference.get().getResult() ,it's a blocking call so have to do this on a background thread
    private Attribute getAttributeFromDocumentReference(DocumentReference documentReference) throws InterruptedException, ExecutionException, TimeoutException {
        DocumentSnapshot documentSnapshot = Tasks.await(documentReference.get(), 500, TimeUnit.SECONDS);

        if (documentSnapshot != null) {
            String type = documentSnapshot.getString("type");
            switch (type) {
                case "simple_value_attribute":
                    return getSimpleValueAttribute(documentSnapshot);
                case "range_value_attribute":
                    return getRangeValueAttribute(documentSnapshot);
                default:
                    return new SimpleValueAttribute(0);

            }
        }

        return new SimpleValueAttribute(3);
    }

    private SimpleValueAttribute getSimpleValueAttribute(DocumentSnapshot documentSnapshot) {
        return new SimpleValueAttribute(documentSnapshot.getLong("attributeValue").intValue());
    }

    private RangeValueAttribute getRangeValueAttribute(DocumentSnapshot documentSnapshot) {
        return new RangeValueAttribute(documentSnapshot.getLong("startRange").intValue(), documentSnapshot.getLong("endRange").intValue());
    }
}
