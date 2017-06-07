package kp.project.brainteaser;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Achievements extends AppCompatActivity {
    DatabaseHelper database;
    TextView name1,game1,score1,name2,game2,score2,name3,game3,score3,name4,game4,score4,name5,game5,score5,name6,game6,score6;
    TextView name1II,game1II,score1II,name2II,game2II,score2II,name3II,game3II,score3II,name4II,game4II,score4II,name5II,game5II,score5II,name6II,game6II,score6II;
    TextView name1III,game1III,score1III,name2III,game2III,score2III,name3III,game3III,score3III,name4III,game4III,score4III,name5III,game5III,score5III,name6III,game6III,score6III;
    String name;
    int userID;

     @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievemnts);
        name1 = (TextView) findViewById(R.id.name1);
        name2 = (TextView) findViewById(R.id.name2);
        name3 = (TextView) findViewById(R.id.name3);
        name4 = (TextView) findViewById(R.id.name4);
        name5 = (TextView) findViewById(R.id.name5);
        name6 = (TextView) findViewById(R.id.name6);
        game1 = (TextView) findViewById(R.id.game1);
        game2 = (TextView) findViewById(R.id.game2);
        game3 = (TextView) findViewById(R.id.game3);
        game4 = (TextView) findViewById(R.id.game4);
        game5 = (TextView) findViewById(R.id.game5);
        game6 = (TextView) findViewById(R.id.game6);
        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);
        score4 = (TextView) findViewById(R.id.score4);
        score5 = (TextView) findViewById(R.id.score5);
        score6 = (TextView) findViewById(R.id.score6);
        name1II = (TextView) findViewById(R.id.name1II);
        name2II = (TextView) findViewById(R.id.name2II);
        name3II = (TextView) findViewById(R.id.name3II);
        name4II = (TextView) findViewById(R.id.name4II);
        name5II = (TextView) findViewById(R.id.name5II);
        name6II = (TextView) findViewById(R.id.name6II);
        game1II = (TextView) findViewById(R.id.game1II);
        game2II = (TextView) findViewById(R.id.game2II);
        game3II = (TextView) findViewById(R.id.game3II);
        game4II = (TextView) findViewById(R.id.game4II);
        game5II = (TextView) findViewById(R.id.game5II);
        game6II = (TextView) findViewById(R.id.game6II);
        score1II = (TextView) findViewById(R.id.score1II);
        score2II = (TextView) findViewById(R.id.score2II);
        score3II = (TextView) findViewById(R.id.score3II);
        score4II = (TextView) findViewById(R.id.score4II);
        score5II = (TextView) findViewById(R.id.score5II);
        score6II = (TextView) findViewById(R.id.score6II);
        name1III = (TextView) findViewById(R.id.name1III);
        name2III = (TextView) findViewById(R.id.name2III);
        name3III = (TextView) findViewById(R.id.name3III);
        name4III = (TextView) findViewById(R.id.name4III);
        name5III = (TextView) findViewById(R.id.name5III);
        name6III = (TextView) findViewById(R.id.name6III);
        game1III = (TextView) findViewById(R.id.game1III);
        game2III = (TextView) findViewById(R.id.game2III);
        game3III = (TextView) findViewById(R.id.game3III);
        game4III = (TextView) findViewById(R.id.game4III);
        game5III = (TextView) findViewById(R.id.game5III);
        game6III = (TextView) findViewById(R.id.game6III);
        score1III = (TextView) findViewById(R.id.score1III);
        score2III = (TextView) findViewById(R.id.score2III);
        score3III = (TextView) findViewById(R.id.score3III);
        score4III = (TextView) findViewById(R.id.score4III);
        score5III = (TextView) findViewById(R.id.score5III);
        score6III = (TextView) findViewById(R.id.score6III);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("Name");
            userID = extras.getInt("ID");

        }
        database = new DatabaseHelper(this);
        int []scoresI=getScores("");
        int []scoresII=getScores("II");
        int []scoresIII =getScores("III");
        name1.setText(name);
        name2.setText(name);
        name3.setText(name);
        name4.setText(name);
        name5.setText(name);
        name6.setText(name);
        game1.setText("Order Numbers");
        game2.setText("Two Pairs");
        game3.setText("Switch Colors");
        game4.setText("Word Game");
        game5.setText("Animation Maths");
        game6.setText("Logical Math");
        score1.setText(scoresI[0]+"/20");
        score2.setText(scoresI[1]+"/20");
        score3.setText(scoresI[2]+"/20");
        score4.setText(scoresI[3]+"/20");
        score5.setText(scoresI[4]+"/15");
        score6.setText(scoresI[5]+"/10");
        name1II.setText(name);
        name2II.setText(name);
        name3II.setText(name);
        name4II.setText(name);
        name5II.setText(name);
        name6II.setText(name);
        game1II.setText("Order NumbersII");
        game2II.setText("Two PairsII");
        game3II.setText("Switch ColorsII");
        game4II.setText("Word GameII");
        game5II.setText("Animation MathsII");
        game6II.setText("Logical MathII");
        score1II.setText(scoresII[0]+"/10");
        score2II.setText(scoresII[1]+"/10");
        score3II.setText(scoresII[2]+"/10");
        score4II.setText(scoresII[3]+"/10");
        score5II.setText(scoresII[4]+"/10");
        score6II.setText(scoresII[5]+"/10");
        name1III.setText(name);
        game1III.setText("IQ Test");
        score1III.setText(scoresIII[0]+"/200");

    }

    public void onBackPressed()
    {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        i.putExtra("ID",userID);
        i.putExtra("Name",name);
        startActivity(i);
    }


    public int[] getScores(String niveli)
    {
        int[] scores;
        if(niveli.equals("III"))
        {
            scores = new int[1];
            Cursor res1 = database.getScore(userID, "IQ Test");
            if (res1.getCount() == 0) {
                return scores;
            }
            while (res1.moveToNext()) {
                scores[0] = res1.getInt(3);
            }
        }
        else
        {
            scores = new int[6];
            String game1 = "Order Numbers" + niveli;
            String game2 = "Two Pairs" + niveli;
            String game3 = "Switch Colors" + niveli;
            String game4 = "Word Game" + niveli;
            String game5 = "Animation Maths" + niveli;
            String game6 = "Logical Math" + niveli;
            Cursor res1 = database.getScore(userID, game1);
            if (res1.getCount() == 0) {
                return scores;
            }
            while (res1.moveToNext()) {
                scores[0] = res1.getInt(3);
            }
            Cursor res2 = database.getScore(userID, game2);
            if (res2.getCount() == 0)
                return scores;
            while (res2.moveToNext()) {
                scores[1] = res2.getInt(3);
            }
            Cursor res3 = database.getScore(userID, game3);
            if (res3.getCount() == 0)
                return scores;
            while (res3.moveToNext()) {
                scores[2] = res3.getInt(3);
            }
            Cursor res4 = database.getScore(userID, game4);
            if (res4.getCount() == 0)
                return scores;
            while (res4.moveToNext()) {
                scores[3] = res4.getInt(3);
            }
            Cursor res5 = database.getScore(userID, game5);
            if (res5.getCount() == 0)
                return scores;
            while (res5.moveToNext()) {
                scores[4] = res5.getInt(3);
            }
            Cursor res6 = database.getScore(userID, game6);
            if (res6.getCount() == 0)
                return scores;
            while (res6.moveToNext()) {
                scores[5] = res6.getInt(3);
            }

        }
        return scores;
    }

}
