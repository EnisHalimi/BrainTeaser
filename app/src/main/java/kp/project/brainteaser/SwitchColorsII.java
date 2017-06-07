package kp.project.brainteaser;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Random;

public class SwitchColorsII extends AppCompatActivity {
    int score=0;
    Button b, b1,b2,b3,b4,pause;
    TextView t1, result;
    String []color=new String[11];
    String[]colorButtons=new String[4];
    ProgressBar timeBar;
    CountDownTimer timer;
    long secondsleft = 60000;
    DatabaseHelper database;
    int userID;
    String name;
    int count=0;
    String textColor;
    SoundPlayer sound;
    boolean started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_switch_colors_ii);
        pause = (Button) findViewById(R.id.playPauseButton);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
            name = extras.getString("Name");
        }
        database = new DatabaseHelper(this);
        Cursor res = database.getOptionsData();
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
        b=(Button)findViewById(R.id.startButton);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        t1=(TextView)findViewById(R.id.textColor);


        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        b4.setVisibility(View.INVISIBLE);
        t1.setVisibility(View.INVISIBLE);
        sound = new SoundPlayer(this,soundvolume);
        result= (TextView) findViewById(R.id.result);
        timeBar = (ProgressBar) findViewById(R.id.timeBar);
        pause.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.INVISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        t1.setVisibility(View.INVISIBLE);
        timeBar = (ProgressBar) findViewById(R.id.timeBar);
        timeBar.setMax(60);
        timeBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

        createColorNames();
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();

            }
        });
    }

    public void start() {
        started = true;
        b.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
        pause();
        long millisInFuture = secondsleft;
        long countDownInterval = 1000;

        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeBar.setProgress((int)secondsleft/1000);
                secondsleft = millisUntilFinished;
                if(count == 4)
                {
                    startGame();
                    count=0;
                }
                else
                    count++;

            }
            @Override
            public void onFinish() {
                stop();
            }
        }.start();
        startGame();

    }

    public void pause()
    {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAction();
            }
        });

    }

    public void pauseAction()
    {
        if(started)
        {
            timer.cancel();
        }
        AlertDialog.Builder pauseMenu=new AlertDialog.Builder(SwitchColorsII.this);
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
                        Intent i = new Intent(getApplicationContext(), WordGameII.class);
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


    public void stop()
    {
        String check;
        if(userID != 0)
        {
            boolean status = database.createScore(userID,"Switch ColorsII",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";


        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(SwitchColorsII.this);
        alertDialogBuilder
                .setMessage("Game Over \nScore: "+score+" "+check)
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),SwitchColorsII.class);
                        finish();
                        i.putExtra("ID",userID);
                        i.putExtra("Name",name);
                        startActivity(i);

                    }
                })
                .setNegativeButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(getApplicationContext(), WordGameII.class);
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
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
    }
    private void createColorNames(){
        color[0]="BLACK";
        color[10]="GRAY";
        color[2]="RED";
        color[3]="PINK";
        color[4]="ORANGE";
        color[5]="YELLOW";
        color[6]="GREEN";
        color[7]="BLUE";
        color[8]="PURPLE";
        color[9]="BROWN";
        color[1]="GOLD";

        // shuffleColor(color);

    }
    private void shuffleColor(){
        Random random=new Random();
        for (int i=0;i<color.length;i++){
            String temp=color[i];
            int index=random.nextInt(11);
            color[i]=color[index];
            color[index]=temp;
        }

    }

    private void startGame(){
        started = true;
        pause.setVisibility(View.VISIBLE);
        pause();
        shuffleColor();
        colorButtons[0]=color[0];
        colorButtons[1]=color[1];
        colorButtons[2]=color[2];
        colorButtons[3]=color[3];
        b.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        b4.setVisibility(View.VISIBLE);
        t1.setVisibility(View.VISIBLE);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b1.setBackgroundResource(R.drawable.graybtn);
        b2.setBackgroundResource(R.drawable.graybtn);
        b3.setBackgroundResource(R.drawable.graybtn);
        b4.setBackgroundResource(R.drawable.graybtn);

        assignColorText();
        assignColorButton();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                b4.setEnabled(false);
                String textButtonColor=b1.getText().toString();
                if(textColor.equals(textButtonColor)){
                    score++;
                    sound.playCorrect();
                    result.setText("Correct");
                    b1.setBackgroundResource(R.drawable.button_green);
                }
                else {
                    sound.playWrong();
                    b1.setBackgroundResource(R.drawable.redbtn);
                    result.setText("Wrong");
                }
                count=0;
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startGame();;
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
                String textButtonColor=b2.getText().toString();
                if(textColor.equals(textButtonColor)){
                    score++;
                    sound.playCorrect();
                    result.setText("Correct");
                    b2.setBackgroundResource(R.drawable.button_green);
                }
                else {

                    b2.setBackgroundResource(R.drawable.redbtn);
                    result.setText("Wrong");
                    sound.playWrong();
                }
                count=0;
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startGame();;
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
                String textButtonColor=b3.getText().toString();
                if(textColor.equals(textButtonColor)){
                    sound.playCorrect();
                    result.setText("Correct");
                    score++;
                    b3.setBackgroundResource(R.drawable.button_green);
                }
                else {

                    b3.setBackgroundResource(R.drawable.redbtn);
                    result.setText("Wrong");
                    sound.playWrong();
                }
                count=0;
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startGame();;
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
                String textButtonColor=b4.getText().toString();
                if(textColor.equals(textButtonColor)){
                    sound.playCorrect();
                    result.setText("Correct");
                    score++;
                    b4.setBackgroundResource(R.drawable.button_green);
                }
                else {

                    b4.setBackgroundResource(R.drawable.redbtn);
                    result.setText("Wrong");
                    sound.playWrong();
                }
                count=0;
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startGame();;
                    }
                },1000);
            }
        });
    }

    private void assignColorText(){
        if(color[0].equals("BLACK")){
            t1.setTextColor(Color.BLACK);
        }else if(color[0].equals("GRAY")){
            t1.setTextColor(Color.GRAY);
        }else if(color[0].equals("RED")){
            t1.setTextColor(Color.RED);
        }else if(color[0].equals("PINK")){
            t1.setTextColor(Color.rgb(255,20,147));
        }else if(color[0].equals("ORANGE")){
            t1.setTextColor(Color.rgb(255,140,0));
        }else if(color[0].equals("YELLOW")){
            t1.setTextColor(Color.YELLOW);
        }else if(color[0].equals("GREEN")){
            t1.setTextColor(Color.GREEN);
        }else if(color[0].equals("BLUE")){
            t1.setTextColor(Color.BLUE);
        }else if(color[0].equals("PURPLE")){
            t1.setTextColor(Color.rgb(128,0,128));
        }else if(color[0].equals("BROWN")){
            t1.setTextColor(Color.rgb(102,51,0));
        }else if(color[0].equals("GOLD")){
            t1.setTextColor(Color.rgb(255,215,0));
        }
        textColor= color[0];
        t1.setText(color[1]);
    }

    private void assignColorButton(){
        shuffleButtonsColor();
        b1.setTextColor(Color.WHITE);
        if(colorButtons[0].equals("BLACK")){
            b1.setTextColor(Color.BLACK);
        }else if(colorButtons[0].equals("GRAY")){
            b1.setTextColor(Color.GRAY);
        }else if(colorButtons[0].equals("RED")){
            b1.setTextColor(Color.RED);
        }else if(colorButtons[0].equals("PINK")){
            b1.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[0].equals("ORANGE")){
            b1.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[0].equals("YELLOW")){
            b1.setTextColor(Color.BLACK);
            b1.setTextColor(Color.YELLOW);
        }else if(colorButtons[0].equals("GREEN")){
            b1.setTextColor(Color.BLACK);
            b1.setTextColor(Color.GREEN);
        }else if(colorButtons[0].equals("BLUE")){
            b1.setTextColor(Color.BLUE);
        }else if(colorButtons[0].equals("PURPLE")){
            b1.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[0].equals("BROWN")){
            b1.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[0].equals("GOLD")){
            b1.setTextColor(Color.BLACK);
            b1.setTextColor(Color.rgb(255,215,0));
        }
        b2.setTextColor(Color.WHITE);
        if(colorButtons[1].equals("BLACK")){
            b2.setTextColor(Color.BLACK);
        }else if(colorButtons[1].equals("GRAY")){
            b2.setTextColor(Color.GRAY);
        }else if(colorButtons[1].equals("RED")){
            b2.setTextColor(Color.RED);
        }else if(colorButtons[1].equals("PINK")){
            b2.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[1].equals("ORANGE")){
            b2.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[1].equals("YELLOW")){
            b2.setTextColor(Color.BLACK);
            b2.setTextColor(Color.YELLOW);
        }else if(colorButtons[1].equals("GREEN")){
            b2.setTextColor(Color.BLACK);
            b2.setTextColor(Color.GREEN);
        }else if(colorButtons[1].equals("BLUE")){
            b2.setTextColor(Color.BLUE);
        }else if(colorButtons[1].equals("PURPLE")){
            b2.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[1].equals("BROWN")){
            b2.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[1].equals("GOLD")){
            b2.setTextColor(Color.BLACK);
            b2.setTextColor(Color.rgb(255,215,0));
        }
        b3.setTextColor(Color.WHITE);
        if(colorButtons[2].equals("BLACK")){
            b3.setTextColor(Color.BLACK);
        }else if(colorButtons[2].equals("GRAY")){
            b3.setTextColor(Color.GRAY);
        }else if(colorButtons[2].equals("RED")){
            b3.setTextColor(Color.RED);
        }else if(colorButtons[2].equals("PINK")){
            b3.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[2].equals("ORANGE")){
            b3.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[2].equals("YELLOW")){
            b3.setTextColor(Color.BLACK);
            b3.setTextColor(Color.YELLOW);
        }else if(colorButtons[2].equals("GREEN")){
            b3.setTextColor(Color.BLACK);
            b3.setTextColor(Color.GREEN);
        }else if(colorButtons[2].equals("BLUE")){
            b3.setTextColor(Color.BLUE);
        }else if(colorButtons[2].equals("PURPLE")){
            b3.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[2].equals("BROWN")){
            b3.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[2].equals("GOLD")){
            b3.setTextColor(Color.BLACK);
            b3.setTextColor(Color.rgb(255,215,0));
        }
        b4.setTextColor(Color.WHITE);
        if(colorButtons[3].equals("BLACK")){
            b4.setTextColor(Color.BLACK);
        }else if(colorButtons[3].equals("GRAY")){
            b4.setTextColor(Color.GRAY);
        }else if(colorButtons[3].equals("RED")){
            b4.setTextColor(Color.RED);
        }else if(colorButtons[3].equals("PINK")){
            b4.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[3].equals("ORANGE")){
            b4.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[3].equals("YELLOW")){
            b4.setTextColor(Color.BLACK);
            b4.setTextColor(Color.YELLOW);
        }else if(colorButtons[3].equals("GREEN")){
            b4.setTextColor(Color.BLACK);
            b4.setTextColor(Color.GREEN);
        }else if(colorButtons[3].equals("BLUE")){
            b4.setTextColor(Color.BLUE);
        }else if(colorButtons[3].equals("PURPLE")){
            b4.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[3].equals("BROWN")){
            b4.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[3].equals("GOLD")){
            b4.setTextColor(Color.BLACK);
            b4.setTextColor(Color.rgb(255,215,0));
        }
        b1.setText(colorButtons[2]);
        b2.setText(colorButtons[1]);
        b3.setText(colorButtons[0]);
        b4.setText(colorButtons[3]);

    }
    private void shuffleButtonsColor(){
        Random random=new Random();
        for(int i=0;i<3;i++){
            int index=random.nextInt(3);
            String temp=colorButtons[i];
            colorButtons[i]=colorButtons[index];
            colorButtons[index]=temp;
        }
    }

    public void onBackPressed(){
        pauseAction();
    }



}