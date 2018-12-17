package com.example.corne.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private String storyChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Select checked radiobox
        RadioGroup storytype = (RadioGroup) findViewById(R.id.Storytype);
        storytype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkecRadioButton = (RadioButton)group.findViewById(checkedId);
                storyChosen = (String) checkecRadioButton.getText();
            }
        });

        // Create onClickListener for startButton to make intent containg chosen story
        startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FillingActivity.class);
                intent.putExtra("storyLink", storyChosen);
                startActivity(intent);
            }
        });


    }

}
