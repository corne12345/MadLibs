package com.example.corne.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Console;
import java.io.InputStream;

public class FillingActivity extends AppCompatActivity {

    Story verhaaltje;
    String placeholder;
    Button nextButton;
    TextView hintje;
    TextView remaining;
    int left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling);

        // Retrieve intent from MainActivity
        Intent intent = getIntent();
        String storyLink = (String) intent.getSerializableExtra("storyLink");

        // Check which story is chosen and load this if not yet created
        if (savedInstanceState != null) {
            verhaaltje = (Story) savedInstanceState.getSerializable("saved story");
        } else {
            InputStream is;

            // Link the output of the radiobutton to one of the stories
            if (storyLink.equals("Simple")){
                is = getResources().openRawResource(R.raw.madlib0_simple);
            }
            else if (storyLink.equals("Tarzan")){
                is = getResources().openRawResource(R.raw.madlib1_tarzan);
            }
            else if (storyLink.equals("University")){
                is = getResources().openRawResource(R.raw.madlib2_university);
            }
            else if (storyLink.equals("Clothes")){
                is = getResources().openRawResource(R.raw.madlib3_clothes);
            }
            else{
                is = getResources().openRawResource(R.raw.madlib4_dance);
            }

            // Create new story of the right kind
            verhaaltje = new Story(is);
        }

        // Create hint in editText
        placeholder = verhaaltje.getNextPlaceholder();
        hintje = (TextView) findViewById(R.id.TextInputEditText);
        hintje.setHint(placeholder);

        // Create display of right amount of remaining
        left = verhaaltje.getPlaceholderRemainingCount();
        remaining = findViewById(R.id.remaining);
        remaining.setText(Integer.toString(left));

        // Create listener when the next button is clicked
        nextButton = (Button) findViewById(R.id.button2);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {

                // Fill in the chosen word in the story
                String word = hintje.getText().toString();
                verhaaltje.fillInPlaceholder(word);

                // Update amount of words left
                left = verhaaltje.getPlaceholderRemainingCount();
                remaining.setText(Integer.toString(left));

                // Get next placeholder and clear text
                placeholder = verhaaltje.getNextPlaceholder();
                hintje.setHint(placeholder);
                hintje.setText("");

                // If filling in is finished, start Display activity with story as intent
                Boolean finished = verhaaltje.isFilledIn();
                if (finished == true){
                    String storyline = verhaaltje.toString();
                    Intent displayed = new Intent(FillingActivity.this, DisplayActivity.class);
                    displayed.putExtra("storyline", storyline);
                    startActivity(displayed);
                }

            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("saved story", verhaaltje);
    }
}
