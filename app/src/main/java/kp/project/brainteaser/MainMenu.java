package kp.project.brainteaser;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainMenu extends AppCompatActivity {

    UserHelper userDB;
    private String user_name;
    private int userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setUser();
        Button playButton = (Button) findViewById(R.id.playButton);
        Button achievementsButton = (Button) findViewById(R.id.achivmentsButton);
        Button optionsButton = (Button) findViewById(R.id.optionsButton);
        userDB = new UserHelper(this);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Pressed Play Button", Toast.LENGTH_LONG).show();

            }
        });

        achievementsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainMenu.this, "Pressed Options Button", Toast.LENGTH_LONG).show();
                userDB.logout();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Options.class);
                startActivity(i);

            }
        });
    }
    public void onBackPressed(){
        return;
    }

    public void setUser()
    {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_name = extras.getString("Username");
            userID = extras.getInt("ID");

        }
    }
}

