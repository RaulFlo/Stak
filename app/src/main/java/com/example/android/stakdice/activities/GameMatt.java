package com.example.android.stakdice.activities;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.stakdice.R;
import com.example.android.stakdice.StakCard;
import com.example.android.stakdice.StakCardView;
import com.example.android.stakdice.models.attribute.Attribute;

import java.util.Random;

public class GameMatt extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {


    private ImageView imageViewDice,imagePlaceholder;
    private TextView roundView;
    private Random rng = new Random();
    private Button rollButton;
    private int maxClicks = 10;
    private int currentClicks = 0;
    private Button validateBtn;
    private EditText sEditText;
    private EditText tEditText;
    private EditText aEditText;
    private EditText kEditText;

    private static final String IMAGE_VIEW_TAG = "Dice Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);

        //get intent
        Intent intent = getIntent();
        final StakCard stakCard = (StakCard) intent.getSerializableExtra("StakCard");

        StakCardView cardView = findViewById(R.id.stak_card_view);
        //link views
        validateBtn = findViewById(R.id.debug_validate_btn);
        sEditText = findViewById(R.id.s_debug);
        tEditText = findViewById(R.id.t_debug);
        aEditText = findViewById(R.id.a_debug);
        kEditText = findViewById(R.id.k_debug);
        roundView = findViewById(R.id.game_matt_text_view_round);
        rollButton = findViewById(R.id.button_roll);
        findViews();
        implementEvents();


        //Visibility
        imageViewDice.setVisibility(View.INVISIBLE);
        validateBtn.setVisibility(View.INVISIBLE);

        //set current stakCard to view
        cardView.setStakCard(stakCard);

        //buttons
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewDice.setVisibility(View.VISIBLE);
                isClicked();

            }
        });


        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid(stakCard);
            }
        });


    }

    //Find all views and set Tag to all draggable views
    private void findViews() {
        imageViewDice = findViewById(R.id.image_view_dice);
        imageViewDice.setTag(IMAGE_VIEW_TAG);

        imagePlaceholder = findViewById(R.id.placeholder_image);
        imagePlaceholder.setTag(IMAGE_VIEW_TAG);



    }
    //Implement long click and drag listener
    private void implementEvents() {
        //set on click listener
        imagePlaceholder.setOnLongClickListener(this);

        //set drag listener
        sEditText.setOnDragListener(this);
        findViewById(R.id.placeholder_view).setOnDragListener(this);

    }


    private void isValid(StakCard stakCard) {
        Attribute stakCardStrength = stakCard.getStrength();
        Attribute stakCardToughness = stakCard.getToughness();
        Attribute stakCardAgility = stakCard.getAgility();
        Attribute stakCardKnowledge = stakCard.getKnowledge();

        int sValue = Integer.parseInt(sEditText.getText().toString());
        int tValue = Integer.parseInt(tEditText.getText().toString());
        int aValue = Integer.parseInt(aEditText.getText().toString());
        int kValue = Integer.parseInt(kEditText.getText().toString());


        if (stakCardStrength.isValid(sValue) && stakCardToughness.isValid(tValue)
                && stakCardAgility.isValid(aValue) && stakCardKnowledge.isValid(kValue)) {

            Toast.makeText(this, "Passed", Toast.LENGTH_SHORT).show();

            //Change the isBeaten on the card to true, commented out for now
            //stakCard.setBeaten(true);
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void revealValidateButton() {
        if (currentClicks == 10) {
            validateBtn.setVisibility(View.VISIBLE);
        }
    }


    private void isClicked() {

        if (currentClicks == maxClicks) {
            rollButton.setEnabled(false);
            revealValidateButton();
        } else {

            roundView.setText("Round: " + (currentClicks + 1));
            rollDice();
            currentClicks++;
        }

    }



    private void rollDice() {
        int randomNumber = rng.nextInt(6) + 1;

        switch (randomNumber) {
            case 1:
                imageViewDice.setImageResource(R.drawable.dice1);
                imagePlaceholder.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageViewDice.setImageResource(R.drawable.dice2);
                imagePlaceholder.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageViewDice.setImageResource(R.drawable.dice3);
                imagePlaceholder.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageViewDice.setImageResource(R.drawable.dice4);
                imagePlaceholder.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageViewDice.setImageResource(R.drawable.dice5);
                imagePlaceholder.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageViewDice.setImageResource(R.drawable.dice6);
                imagePlaceholder.setImageResource(R.drawable.dice6);
                break;
        }

    }

    // This is the method that the system calls when it dispatches a drag event to the
    // listener.
    @Override
    public boolean onDrag(View view, DragEvent event) {
        // Defines a variable to store the action type for the incoming event
        int action = event.getAction();
        // Handles each of the expected events
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                // Applies a YELLOW or any color tint to the View, when the dragged view entered into drag acceptable view
                // Return true; the return value is ignored.

                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);

                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                // Ignore the event
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                // Re-sets the color tint to blue, if you had set the BLUE color or any color in ACTION_DRAG_STARTED. Returns true; the return value is ignored.

                //  view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

                //If u had not provided any color in ACTION_DRAG_STARTED then clear color filter.
                view.getBackground().clearColorFilter();
                // Invalidate the view to force a redraw in the new tint
                view.invalidate();

                return true;
            case DragEvent.ACTION_DROP:
                // Gets the item containing the dragged data
                ClipData.Item item = event.getClipData().getItemAt(0);

                // Gets the text data from the item.
                String dragData = item.getText().toString();

                // Displays a message containing the dragged data.
                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();

                // Turns off any color tints
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);//remove the dragged view
               LinearLayout container = (LinearLayout) view;//caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                container.addView(v);//Add the dragged view
                v.setVisibility(View.VISIBLE);//finally set Visibility to VISIBLE

                // Returns true. DragEvent.getResult() will return true.
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                // Turns off any color tinting
                view.getBackground().clearColorFilter();

                // Invalidates the view to force a redraw
                view.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult())
                    Toast.makeText(this, "The drop was handled.", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();


                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }


    @Override
    public boolean onLongClick(View view) {
        // Create a new ClipData.
        // This is done in two steps to provide clarity. The convenience method
        // ClipData.newPlainText() can create a plain text ClipData in one step.

        // Create a new ClipData.Item from the ImageView object's tag
        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

        // Instantiates the drag shadow builder.
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

        // Starts the drag
        view.startDrag(data//data to be dragged
                , shadowBuilder //drag shadow
                , view//local data about the drag and drop operation
                , 0//no needed flags
        );

        //Set view visibility to INVISIBLE as we are going to drag the view
        view.setVisibility(View.INVISIBLE);
        return true;
    }



}
