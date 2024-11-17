package com.project.intellifit_trainer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class StartWorkoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startworkout);

        // Set up the button to open a webpage
        Button openWebButton = findViewById(R.id.openWebButton);
        openWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartWorkoutActivity.this, com.project.intellifit_trainer.WebViewActivity.class);
                // Pass the URL to the WebViewActivity
                intent.putExtra("URL", "https://aiworkout.profematika.com"); // Replace with the desired URL
                startActivity(intent);
            }
        });
    }
}
