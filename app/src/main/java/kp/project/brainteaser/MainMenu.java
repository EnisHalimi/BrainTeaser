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
    ScoreHelper scoreDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setUser();
        Button playButton = (Button) findViewById(R.id.playButton);
        Button achievementsButton = (Button) findViewById(R.id.achivmentsButton);
        Button optionsButton = (Button) findViewById(R.id.optionsButton);
        userDB = new UserHelper(this);
        scoreDB = new ScoreHelper(this);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_play);
                Button niveli1 = (Button)findViewById(R.id.niveli1);
                Button niveli2= (Button)findViewById(R.id.niveli2);
                Button niveli3 = (Button)findViewById(R.id.niveli3);

                int [] score = getScores("");
                /*if(score[0] < 10 || score[1] < 10  || score[2] < 10  || score[3] < 10 ||  score[4] < 7 || score[5] < 5 )
                {
                    niveli2.setEnabled(false);
                    niveli2.setText("Niveli i 2 (Locked)");
                }
                else {
                    niveli2.setEnabled(true);
                }*/
                niveli1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), OrderNumbers.class);
                        i.putExtra("ID",userID);
                        i.putExtra("Name",user_name);
                        startActivity(i);
                    }
                });
                niveli2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent i = new Intent(getApplicationContext(), OrderNumbersII.class);
                            i.putExtra("ID", userID);
                            i.putExtra("Name", user_name);
                            startActivity(i);

                    }
                });
                niveli3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), IQTest.class);
                        i.putExtra("ID", userID);
                        i.putExtra("Name", user_name);
                        startActivity(i);
                    }
                });

            }
        });

        achievementsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               // userDB.logout();
                Intent i = new Intent(getApplicationContext(), Achievements.class);
                i.putExtra("ID",userID);
                i.putExtra("Name",user_name);
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
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        i.putExtra("ID",userID);
        i.putExtra("Name",user_name);
        startActivity(i);
    }

    public void setUser()
    {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                user_name = extras.getString("Name");
                userID = extras.getInt("ID");

            }


    }

    public int[] getScores(String niveli)
    {
        int [] scores = new int[6];
        String game1 = "Order Numbers"+niveli;
        String game2 = "Two Pairs"+niveli;
        String game3 = "Switch Colors"+niveli;
        String game4 = "Word Game"+niveli;
        String game5 = "Animation Maths"+niveli;
        String game6 = "Logical Math"+niveli;
        Cursor res1 = scoreDB.getScore(userID,game1);
        if(res1.getCount()== 0) {
            return scores;
        }
        while(res1.moveToNext())
        {
            scores[0] = res1.getInt(3);
        }
        Cursor res2 = scoreDB.getScore(userID,game2);
        if(res2.getCount()== 0)
            return scores;
        while(res2.moveToNext())
        {
            scores[1] = res2.getInt(3);
        }
        Cursor res3 = scoreDB.getScore(userID,game3);
        if(res3.getCount()== 0)
            return scores;
        while(res3.moveToNext())
        {
            scores[2] = res3.getInt(3);
        }
        Cursor res4 = scoreDB.getScore(userID,game4);
        if(res4.getCount()== 0)
            return scores;
        while(res4.moveToNext())
        {
            scores[3] = res4.getInt(3);
        }
        Cursor res5 = scoreDB.getScore(userID,game5);
        if(res5.getCount()== 0)
            return scores;
        while(res5.moveToNext())
        {
            scores[4] = res5.getInt(3);
        }
        Cursor res6 = scoreDB.getScore(userID,game6);
        if(res6.getCount()== 0)
            return scores;
        while(res6.moveToNext())
        {
            scores[5] = res6.getInt(3);
        }
        return scores;
    }
}

