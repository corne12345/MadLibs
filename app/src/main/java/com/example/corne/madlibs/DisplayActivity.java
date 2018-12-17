package com.example.corne.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    TextView verhaal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Get intent of FillingActivity
        Intent intent = getIntent();
        String txt = (String) intent.getSerializableExtra("storyline");
        verhaal = findViewById(R.id.verhaal);

        // Use HTML reading to set text in <b> tags to bold
        verhaal.setText(Html.fromHtml(txt, Html.FROM_HTML_MODE_LEGACY));
    }

    @Override

    // Back method to go back to MainActivity
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
