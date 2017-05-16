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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AnimationMathII extends AppCompatActivity {

    ScoreHelper scoreDB;
    TextView t1;
    ImageView i1,i2,i3;
    Button first,second,third, start,pause;
    Animation horizontal,vertical,diagonal;
    ArrayList<Integer> numbers;
    int result;
    int score = 0;
    ProgressBar timeBar;
    CountDownTimer timer;
    long secondsleft = 60000;
    int userID;
    String name;
    SoundPlayer sound;
    OptionsHelper opDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_math_ii);
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
        t1 = (TextView) findViewById(R.id.result);
        i1 = (ImageView) findViewById(R.id.firstImage);
        i2 = (ImageView ) findViewById(R.id.secondImage);
        i3 = (ImageView) findViewById(R.id.thirdImage);
        first = (Button) findViewById(R.id.firstAnswer);
        second = (Button) findViewById(R.id.secondAnswer);
        third = (Button) findViewById(R.id.thirdAnswer);
        numbers = new ArrayList<>();
        horizontal = AnimationUtils.loadAnimation(this, R.anim.horizontalmove);
        vertical = AnimationUtils.loadAnimation(this, R.anim.verticalmove);
        diagonal = AnimationUtils.loadAnimation(this, R.anim.diagonalmove);
        timeBar = (ProgressBar) findViewById(R.id.timeBar);
        timeBar.setMax(60);
        timeBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        scoreDB = new ScoreHelper(this);
        start = (Button) findViewById(R.id.startButton);
        pause = (Button) findViewById(R.id.playPauseButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
                game();
            }
        });


    }
    public void start() {
        start.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
        pause();
        i2.startAnimation(horizontal);
        i1.startAnimation(vertical);
        i3.startAnimation(diagonal);
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
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(AnimationMathII.this);
                pauseMenu

                        .setMessage("Game Paused")
                        .setCancelable(false)
                        .setPositiveButton("Resume", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                start();
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
            boolean status = scoreDB.create(userID,"Animation Maths",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";


        AlertDialog.Builder oalertDialogBuilder=new AlertDialog.Builder(AnimationMathII.this);
        oalertDialogBuilder

                .setMessage("Game Over\nScore: "+score+" "+check+"\nCongrats on finishing the first level")
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
        t1.setText("");
        first.setBackgroundResource(R.drawable.graybtn);
        second.setBackgroundResource(R.drawable.graybtn);
        third.setBackgroundResource(R.drawable.graybtn);
        first.setEnabled(true);
        second.setEnabled(true);
        third.setEnabled(true);
        addNumbers();
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                first.setEnabled(false);
                second.setEnabled(false);
                third.setEnabled(false);
                int nr = Integer.parseInt(first.getText().toString());
                if(nr == result)
                {
                    score++;
                    first.setBackgroundResource(R.drawable.menubutton);
                    t1.setText("Correct");
                    sound.playCorrect();

                }
                else
                {
                    sound.playWrong();
                    t1.setText("Wrong");
                    first.setBackgroundResource(R.drawable.redbtn);
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
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                first.setEnabled(false);
                second.setEnabled(false);
                third.setEnabled(false);
                int nr = Integer.parseInt(second.getText().toString());
                if(nr == result)
                {
                    sound.playCorrect();
                    t1.setText("Correct");
                    score++;
                    second.setBackgroundResource(R.drawable.menubutton);

                }
                else
                {
                    sound.playWrong();
                    t1.setText("Wrong");
                    second.setBackgroundResource(R.drawable.redbtn);
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
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                first.setEnabled(false);
                second.setEnabled(false);
                third.setEnabled(false);
                int nr = Integer.parseInt(third.getText().toString());
                if(nr == result)
                {
                    sound.playCorrect();
                    score++;
                    t1.setText("Correct");
                    third.setBackgroundResource(R.drawable.menubutton);

                }
                else
                {
                    sound.playWrong();
                    t1.setText("Wrong");
                    third.setBackgroundResource(R.drawable.redbtn);
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

    public void addNumbers()
    {
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        Collections.shuffle(numbers);
        result = numbers.get(0) + numbers.get(1) + numbers.get(2);
        if(numbers.get(0)== 1)
            i1.setImageResource(R.drawable.num1);
        if(numbers.get(0)== 2)
            i1.setImageResource(R.drawable.num2);
        if(numbers.get(0)== 3)
            i1.setImageResource(R.drawable.num3);
        if(numbers.get(0)== 4)
            i1.setImageResource(R.drawable.num4);
        if(numbers.get(0)== 5)
            i1.setImageResource(R.drawable.num5);
        if(numbers.get(0)== 6)
            i1.setImageResource(R.drawable.num6);
        if(numbers.get(1)== 1)
            i2.setImageResource(R.drawable.num1);
        if(numbers.get(1)== 2)
            i2.setImageResource(R.drawable.num2);
        if(numbers.get(1)== 3)
            i2.setImageResource(R.drawable.num3);
        if(numbers.get(1)== 4)
            i2.setImageResource(R.drawable.num4);
        if(numbers.get(1)== 5)
            i2.setImageResource(R.drawable.num5);
        if(numbers.get(1)== 6)
            i2.setImageResource(R.drawable.num6);
        if(numbers.get(2)== 1)
            i3.setImageResource(R.drawable.num1);
        if(numbers.get(2)== 2)
            i3.setImageResource(R.drawable.num2);
        if(numbers.get(2)== 3)
            i3.setImageResource(R.drawable.num3);
        if(numbers.get(2)== 4)
            i3.setImageResource(R.drawable.num4);
        if(numbers.get(2)== 5)
            i3.setImageResource(R.drawable.num5);
        if(numbers.get(2)== 6)
            i3.setImageResource(R.drawable.num6);
        assignbuttons();
    }

    public void assignbuttons()
    {
        Random r1 = new Random();
        int nr = r1.nextInt(3);
        if(nr==0) {
            first.setText("" + result);
            second.setText(""+(result+1));
            third.setText(""+(result-2));
        }
        else if (nr==1)
        {
            first.setText("" + (result-1));
            second.setText("" + result);
            third.setText(""+(result+2));
        }
        else
        {
            first.setText("" + (result-2));
            second.setText(""+(result+1));
            third.setText("" + result);
        }
    }

}
