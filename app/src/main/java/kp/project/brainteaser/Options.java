package kp.project.brainteaser;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class Options extends AppCompatActivity {
    Switch musicSwitch, soundSwitch,languageSwitch;
    OptionsHelper optionsDB;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        musicSwitch = (Switch) findViewById(R.id.musicSwitch);
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        languageSwitch = (Switch) findViewById(R.id.languageSwitch);
        optionsDB = new OptionsHelper(this);
        saveButton = (Button) findViewById(R.id.saveButton);
        Cursor res = optionsDB.getData();
        if(res.getCount() > 0)
        {
            while(res.moveToNext())
            {
                if(res.getInt(1) == 1)
                    musicSwitch.setChecked(true);
                else
                    musicSwitch.setChecked(false);
                if(res.getInt(2) == 1)
                    soundSwitch.setChecked(true);
                else
                    soundSwitch.setChecked(false);
                if(res.getInt(3) == 1)
                    languageSwitch.setChecked(true);
                else
                    languageSwitch.setChecked(false);
            }
        }
        save();

    }

    public void save()
    {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int music;
                int sound;
                int language;
                if( musicSwitch.isChecked())
                    music=1;
                else
                    music=0;
                if( soundSwitch.isChecked())
                    sound=1;
                else
                    sound=0;
                if( languageSwitch.isChecked())
                    language=1;
                else
                    language=0;
                boolean isInserted = optionsDB.update(music,sound,language);
                if(isInserted) {
                    Toast.makeText(Options.this, "Saved", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(Options.this, "Not saved", Toast.LENGTH_LONG).show();

             }
        });
    }


}
