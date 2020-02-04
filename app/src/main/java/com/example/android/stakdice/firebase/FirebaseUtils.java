package com.example.android.stakdice.firebase;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.attribute.Attribute;
import com.example.android.stakdice.models.attribute.SimpleValueAttribute;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class FirebaseUtils {

    public static StakCard getStakCardFromDocument(DocumentSnapshot document) {
        return new StakCard(document.getId(),
                0,
                document.getString("name"),
                getAttributeFromDocumentReference(document.getDocumentReference("strength_attribute")),
                getAttributeFromDocumentReference(document.getDocumentReference("toughness_attribute")),
                getAttributeFromDocumentReference(document.getDocumentReference("attack_attribute")),
                getAttributeFromDocumentReference(document.getDocumentReference("knowledge_attribute")),
                document.getString("difficulty"),
                false,
                document.getString("description"));
    }

    public static Attribute getAttributeFromDocumentReference(DocumentReference documentReference) {
//        documentReference
//                .get()
        return new SimpleValueAttribute(3);
    }
}
