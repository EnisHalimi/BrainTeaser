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
 import android.widget.Switch;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.lang.reflect.Array;
        import java.util.Random;

public class SwitchColors extends AppCompatActivity {
    int score=0;
    Button  b1,b2,b3,pause,start;
    TextView t1,result;
    String []color=new String[10];
    String[]colorButtons=new String[3];
    ProgressBar timeBar;
    CountDownTimer timer;
    long secondsleft = 60000;
    ScoreHelper scoreDB;
    int userID;
    String name;
    int count=0;
    String textColor;
    SoundPlayer sound;
    OptionsHelper opDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_colors);
        pause = (Button) findViewById(R.id.playPauseButton);
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
        scoreDB = new ScoreHelper(this);
        start = (Button) findViewById(R.id.startButton);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        t1 = (TextView) findViewById(R.id.textColor);
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
        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();
                startGame();
            }
        });
    }
    public void pause()
    {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(SwitchColors.this);
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
                                Intent i = new Intent(getApplicationContext(), WordGame.class);
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

    }

    public void stop()
    {
        String check;
        if(userID != 0)
        {
            boolean status = scoreDB.create(userID,"Switch Colors",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";


        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(SwitchColors.this);
        alertDialogBuilder
                .setMessage("Game Over \nScore: "+score+" "+check)
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),SwitchColors.class);
                        finish();
                        i.putExtra("ID",userID);
                        i.putExtra("Name",name);
                        startActivity(i);

                    }
                })
                .setNegativeButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), WordGame.class);
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
            int index=random.nextInt(10);
            color[i]=color[index];
            color[index]=temp;
        }

    }

    private void startGame(){
        shuffleColor();
        result.setText("");
        colorButtons[0]=color[0];
        colorButtons[1]=color[1];
        colorButtons[2]=color[2];
        start.setVisibility(View.INVISIBLE);
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        t1.setVisibility(View.VISIBLE);
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        assignColorText();
        assignColorButton();
        b1.setBackgroundResource(R.drawable.graybtn);
        b2.setBackgroundResource(R.drawable.graybtn);
        b3.setBackgroundResource(R.drawable.graybtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(false);
                String textButtonColor=b1.getText().toString();
                if(textColor.equals(textButtonColor)){
                    score++;
                    sound.playCorrect();
                    result.setText("Correct");
                    b1.setBackgroundResource(R.drawable.menubutton);
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
                String textButtonColor=b2.getText().toString();
                if(textColor.equals(textButtonColor)){
                    score++;
                    sound.playCorrect();
                    result.setText("Correct");
                    b2.setBackgroundResource(R.drawable.menubutton);
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
                                String textButtonColor=b3.getText().toString();
                if(textColor.equals(textButtonColor)){
                    sound.playCorrect();
                    result.setText("Correct");
                    score++;
                    b3.setBackgroundResource(R.drawable.menubutton);
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
    }

    private void assignColorText(){
        if(color[0].equals("BLACK")){
            t1.setTextColor(Color.BLACK);
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
        t1.setText(color[4]);
    }

    private void assignColorButton(){
        shuffleButtonsColor();
        if(colorButtons[0].equals("BLACK")){
            b1.setTextColor(Color.BLACK);
        }else if(colorButtons[0].equals("RED")){
            b1.setTextColor(Color.RED);
        }else if(colorButtons[0].equals("PINK")){
            b1.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[0].equals("ORANGE")){
            b1.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[0].equals("YELLOW")){
            b1.setTextColor(Color.YELLOW);
        }else if(colorButtons[0].equals("GREEN")){
            b1.setTextColor(Color.GREEN);
        }else if(colorButtons[0].equals("BLUE")){
            b1.setTextColor(Color.BLUE);
        }else if(colorButtons[0].equals("PURPLE")){
            b1.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[0].equals("BROWN")){
            b1.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[0].equals("GOLD")){
            b1.setTextColor(Color.rgb(255,215,0));
        }
         if(colorButtons[1].equals("BLACK")){
            b2.setTextColor(Color.BLACK);
        }else if(colorButtons[1].equals("RED")){
            b2.setTextColor(Color.RED);
        }else if(colorButtons[1].equals("PINK")){
            b2.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[1].equals("ORANGE")){
            b2.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[1].equals("YELLOW")){
            b2.setTextColor(Color.YELLOW);
        }else if(colorButtons[1].equals("GREEN")){
            b2.setTextColor(Color.GREEN);
        }else if(colorButtons[1].equals("BLUE")){
            b2.setTextColor(Color.BLUE);
        }else if(colorButtons[1].equals("PURPLE")){
            b2.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[1].equals("BROWN")){
            b2.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[1].equals("GOLD")){
            b2.setTextColor(Color.rgb(255,215,0));
        }

        if(colorButtons[2].equals("BLACK")){
            b3.setTextColor(Color.BLACK);
        }else if(colorButtons[2].equals("RED")){
            b3.setTextColor(Color.RED);
        }else if(colorButtons[2].equals("PINK")){
            b3.setTextColor(Color.rgb(255,20,147));
        }else if(colorButtons[2].equals("ORANGE")){
            b3.setTextColor(Color.rgb(255,140,0));
        }else if(colorButtons[2].equals("YELLOW")){
            b3.setTextColor(Color.YELLOW);
        }else if(colorButtons[2].equals("GREEN")){
            b3.setTextColor(Color.GREEN);
        }else if(colorButtons[2].equals("BLUE")){
            b3.setTextColor(Color.BLUE);
        }else if(colorButtons[2].equals("PURPLE")){
            b3.setTextColor(Color.rgb(128,0,128));
        }else if(colorButtons[2].equals("BROWN")){
            b3.setTextColor(Color.rgb(102,51,0));
        }else if(colorButtons[2].equals("GOLD")){
            b3.setTextColor(Color.rgb(255,215,0));
        }
        b1.setText(colorButtons[2]);
        b2.setText(colorButtons[1]);
        b3.setText(colorButtons[0]);

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
        return;
    }

}
