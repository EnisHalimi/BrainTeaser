package kp.project.brainteaser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WordGame extends AppCompatActivity {
    TextView text;
    Button b1,b2,b3,start,pause;
    ArrayList<String> words;
    int score = 0;
    TextView result;
    ProgressBar timeBar;
    CountDownTimer timer;
    long secondsleft = 60000;
    int userID;
    String name;
    ScoreHelper scoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
            name = extras.getString("Name");
        }
        text = (TextView)findViewById(R.id.text);
        b1 = (Button) findViewById(R.id.first);
        b2 = (Button) findViewById(R.id.second);
        b3 = (Button) findViewById(R.id.third);
        words = new ArrayList<>();
        timeBar = (ProgressBar) findViewById(R.id.timeBar);
        timeBar.setMax(60);
        timeBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        scoreDB = new ScoreHelper(this);
        result = (TextView) findViewById(R.id.result);
        start = (Button) findViewById(R.id.startButton);
        pause = (Button) findViewById(R.id.playPauseButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWords();
                start();
                game();
            }
        });

    }

    public void start() {
        start.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
        pause();
        long millisInFuture = secondsleft;
        long countDownInterval = 1000;
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeBar.setProgress((int)secondsleft/1000);
                secondsleft = millisUntilFinished;
            }
            @Override
            public void onFinish() {
                stop();
            }
        }.start();
    }

    public void pause()
    {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(WordGame.this);
                pauseMenu

                        .setMessage("Game Paused")
                        .setCancelable(false)
                        .setPositiveButton("Resume", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                start();
                            }
                        })
                        .setNegativeButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(), AnimationMath.class);
                                i.putExtra("ID",userID);
                                i.putExtra("Name",name);
                                startActivity(i);
                            }
                        })
                        .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(getApplicationContext(), MainMenu.class);
                                i.putExtra("ID",userID);
                                i.putExtra("Name",name);
                                startActivity(i);
                            }
                        });
                AlertDialog pauseDialog = pauseMenu.create();
                pauseDialog.show();
            }
        });

    }


    public void stop()
    {
        String check;
        if(userID != 0)
        {
            boolean status = scoreDB.create(userID,"Word Game",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";


        AlertDialog.Builder oalertDialogBuilder=new AlertDialog.Builder(WordGame.this);
        oalertDialogBuilder

                .setMessage("Game Over\nScore: "+score+" "+check)
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = getIntent();
                        finish();
                        i.putExtra("ID",userID);
                        i.putExtra("Name",name);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(getApplicationContext(), AnimationMath.class);
                        i.putExtra("ID",userID);
                        i.putExtra("Name",name);
                        startActivity(i);
                    }
                })
                .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), MainMenu.class);
                        i.putExtra("ID",userID);
                        i.putExtra("Name",name);
                        startActivity(i);
                    }
                });
        AlertDialog alertDialogBuilder = oalertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    public void game()
    {
        result.setText("");
        b1.setBackgroundResource(R.drawable.graybtn);
        b2.setBackgroundResource(R.drawable.graybtn);
        b3.setBackgroundResource(R.drawable.graybtn);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        shuffleWords();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                if(b1.getTag().equals(text.getTag()))
                {
                    score++;
                    result.setText("Correct");
                    b1.setBackgroundResource(R.drawable.menubutton);
                }

                else{
                    result.setText("Wrong");
                    b1.setBackgroundResource(R.drawable.redbtn);}

                Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game();
                    }
                },1000);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                if(b2.getTag().equals(text.getTag()))
                {
                    score++;
                    b2.setBackgroundResource(R.drawable.menubutton);
                    result.setText("Correct");
                }
                else{
                    result.setText("Wrong");
                    b2.setBackgroundResource(R.drawable.redbtn);}
                Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game();
                    }
                },1000);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                if(b3.getTag().equals(text.getTag()))
                {
                    score++;
                    b3.setBackgroundResource(R.drawable.menubutton);
                    result.setText("Correct");


                }
                else {
                    result.setText("Wrong");
                    b3.setBackgroundResource(R.drawable.redbtn);
                }
                Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        game();
                    }
                },1000);
            }
        });
    }

    public void addWords()
    {
        words.add("Sun");
        words.add("Moon");
        words.add("Football");
        words.add("Game");
        words.add("Brain");
        words.add("Mother");
        words.add("Father");
        words.add("Sister");
        words.add("Brother");
        words.add("Mobile");
        words.add("Computer");
        shuffleWords();
    }

    public void shuffleWords()
    {
        Collections.shuffle(words);
        Random nr = new Random();
        b1.setText(words.get(0));
        b1.setTag("0");
        b2.setText(words.get(1));
        b2.setTag("1");
        b3.setText(words.get(2));
        b3.setTag("2");
        int number = nr.nextInt(3);
        if(number == 0)
            text.setTag("0");
        else if(number == 1)
            text.setTag("1");
        else
            text.setTag("2");
        text.setText(shuffleLetters(words.get(number).toLowerCase()));
    }

    public String shuffleLetters(String word)
    {
        String[] text = word.split("");
        Random random=new Random();
        for (int i=0;i<text.length;i++){
            String temp=text[i];
            int index=random.nextInt(text.length);
            text[i]=text[index];
            text[index]=temp;
        }
        StringBuffer fin = new StringBuffer();
        for(String s: text)
        {
            fin.append(s);
        }
        return fin.toString();
    }

    public void onBackPressed()
    {
        return;
    }

}

