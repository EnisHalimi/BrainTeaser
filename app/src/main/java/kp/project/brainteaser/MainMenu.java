package kp.project.brainteaser;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final TextView mContentView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.dummy_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContentView.getVisibility() == View.INVISIBLE)
                    mContentView.setVisibility(View.VISIBLE);
                else
                    mContentView.setVisibility(View.INVISIBLE);
            }
        });
    }
}

