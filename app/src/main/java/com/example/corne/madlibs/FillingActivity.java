package com.example.corne.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

public class FillingActivity extends AppCompatActivity {

    Story verhaaltje;
    String placeholder;
    Button nextButton;
    TextView hintje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling);

        // Check which story is chosen and load this
        Intent intent = getIntent();
        String storyLink = (String) intent.getSerializableExtra("storyLink");
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
        placeholder = verhaaltje.getNextPlaceholder();
        nextButton = (Button) findViewById(R.id.button2);
        hintje = (TextView) findViewById(R.id.TextInputEditText);
        hintje.setHint(placeholder);

        // Create listener when the next button is clicked
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                String word = hintje.getText().toString();
                verhaaltje.fillInPlaceholder(word);
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


}
