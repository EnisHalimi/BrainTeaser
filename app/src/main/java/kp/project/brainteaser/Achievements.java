package kp.project.brainteaser;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Achievements extends AppCompatActivity {
    ScoreHelper scoreDB;
    TextView text;
    String user_name;
    int userID;
     @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievemnts);
        text = (TextView) findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_name = extras.getString("Name");
            userID = extras.getInt("ID");

        }
        scoreDB = new ScoreHelper(this);
        Cursor res = scoreDB.getData();


        if(res.getCount()== 0)
            return;

        String row = "";
        while(res.moveToNext())
        {
            if(res.getInt(1) == userID)
            row += user_name+"  "+res.getString(2)+"  "+res.getInt(3)+"\n";

        }
        text.setText(row);
    }

}
