package kp.project.brainteaser;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button playButton = (Button) findViewById(R.id.playButton);
        Button achievementsButton = (Button) findViewById(R.id.achivmentsButton);
        Button optionsButton = (Button) findViewById(R.id.optionsButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Pressed Play Button", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        achievementsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Toast.makeText(MainMenu.this, "Pressed Play Button", Toast.LENGTH_LONG).show();
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Toast.makeText(MainMenu.this, "Pressed Play Button", Toast.LENGTH_LONG).show();
            }
        });
    }
}

