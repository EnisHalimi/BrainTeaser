package kp.project.brainteaser;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WordGameII extends AppCompatActivity {
    TextView text;
    Button b1,b2,b3,b4,start,pause;
    ArrayList<String> words;
    int score = 0;
    TextView result;
    ProgressBar timeBar;
    CountDownTimer timer;
    long secondsleft = 60000;
    int userID;
    String name;
    ScoreHelper scoreDB;
    SoundPlayer sound;
    OptionsHelper opDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_game_ii);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
            name = extras.getString("Name");
        }
        opDB = new OptionsHelper(this);
        Cursor res = opDB.getData();
        if(res.getCount()== 0)
            return;
        float soundvolume = 0;
        while(res.moveToNext())
        {
            if(res.getInt(2) == 1)
                soundvolume = 1;
            else
                soundvolume = 0;
        }
        sound = new SoundPlayer(this,soundvolume);
        text = (TextView)findViewById(R.id.text);
        b1 = (Button) findViewById(R.id.first);
        b2 = (Button) findViewById(R.id.second);
        b3 = (Button) findViewById(R.id.third);
        b4 = (Button) findViewById(R.id.fourth);
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
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(WordGameII.this);
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
                                Intent i = new Intent(getApplicationContext(), AnimationMathII.class);
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
            boolean status = scoreDB.create(userID,"Word GameII",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";


        AlertDialog.Builder oalertDialogBuilder=new AlertDialog.Builder(WordGameII.this);
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

                        Intent i = new Intent(getApplicationContext(), AnimationMathII.class);
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
        b4.setBackgroundResource(R.drawable.graybtn);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        shuffleWords();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                if(b1.getTag().equals(text.getTag()))
                {
                    sound.playCorrect();
                    score++;
                    result.setText("Correct");
                    b1.setBackgroundResource(R.drawable.menubutton);
                }

                else{
                    sound.playWrong();
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
                b4.setEnabled(false);
                if(b2.getTag().equals(text.getTag()))
                {
                    sound.playCorrect();
                    score++;
                    b2.setBackgroundResource(R.drawable.menubutton);
                    result.setText("Correct");
                }
                else{
                    sound.playWrong();
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
                b4.setEnabled(false);
                if(b3.getTag().equals(text.getTag()))
                {
                    score++;
                    b3.setBackgroundResource(R.drawable.menubutton);
                    result.setText("Correct");
                    sound.playCorrect();

                }
                else {
                    sound.playWrong();
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
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                if(b4.getTag().equals(text.getTag()))
                {
                    score++;
                    b4.setBackgroundResource(R.drawable.menubutton);
                    result.setText("Correct");
                    sound.playCorrect();

                }
                else {
                    sound.playWrong();
                    result.setText("Wrong");
                    b4.setBackgroundResource(R.drawable.redbtn);
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
        words.add("Through");
        words.add("Great");
        words.add("Much");
        words.add("Before");
        words.add("Cause");
        words.add("Differ");
        words.add("Against");
        words.add("Pattern");
        words.add("Center");
        words.add("Money");
        words.add("Person");
        words.add("Sentence");
        words.add("Home");
        words.add("Power");
        words.add("Certain");
        words.add("Science");
        words.add("Govern");
        words.add("Machine");
        words.add("Dark");
        words.add("Figure");
        words.add("Toward");
        words.add("Simple");
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
        b4.setText(words.get(3));
        b4.setTag("3");
        int number = nr.nextInt(4);
        if(number == 0)
            text.setTag("0");
        else if(number == 1)
            text.setTag("1");
        else if(number == 2)
            text.setTag("2");
        else
            text.setTag("3");
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
