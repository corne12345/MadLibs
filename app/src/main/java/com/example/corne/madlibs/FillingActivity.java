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

        Intent intent = getIntent();
        String storyLink = (String) intent.getSerializableExtra("storyLink");

        // Check which story is chosen and load this if not yet created
        if (savedInstanceState != null) {
            verhaaltje = (Story) savedInstanceState.getSerializable("saved story");
        } else {
            InputStream is;
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
            verhaaltje = new Story(is);
        }

        placeholder = verhaaltje.getNextPlaceholder();
        nextButton = (Button) findViewById(R.id.button2);
        hintje = (TextView) findViewById(R.id.TextInputEditText);
        hintje.setHint(placeholder);
        remaining = findViewById(R.id.remaining);
        left = verhaaltje.getPlaceholderRemainingCount();
        remaining.setText(Integer.toString(left));

        // Create listener when the next button is clicked
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                String word = hintje.getText().toString();
                verhaaltje.fillInPlaceholder(word);
                left = verhaaltje.getPlaceholderRemainingCount();
                remaining.setText(Integer.toString(left));

                placeholder = verhaaltje.getNextPlaceholder();
                hintje.setHint(placeholder);
                hintje.setText("");
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
