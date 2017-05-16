package kp.project.brainteaser;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;


public class LogicalMath extends AppCompatActivity {
    ImageView f1, f2, f3, f21, f22, f31, f32, f33, result;
    String fruits[]={"apple", "pear", "strawberry"};
    int value[]=new int[3];
    int playCounter=0;
    EditText textNr;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView text;
    Button button,pause,play;
    private int score=0;
    ScoreHelper scoreDB;
    String name;
    int userID;
    OptionsHelper opDB;
    SoundPlayer sound;
    boolean state=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logical_math);
        scoreDB = new ScoreHelper(this);
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
        f1=(ImageView)findViewById(R.id.f1);
        f2=(ImageView)findViewById(R.id.f2);
        f3=(ImageView)findViewById(R.id.f3);
        f21=(ImageView)findViewById(R.id.f21);
        f22=(ImageView)findViewById(R.id.f22);
        f31=(ImageView)findViewById(R.id.f31);
        f32=(ImageView)findViewById(R.id.f32);
        f33=(ImageView)findViewById(R.id.f33);
        result=(ImageView)findViewById(R.id.result);
        text = (TextView)findViewById(R.id.textView41);
        answer1=(TextView)findViewById(R.id.answer1);
        answer2=(TextView)findViewById(R.id.answer2);
        answer3=(TextView)findViewById(R.id.answer3);
        button=(Button)findViewById(R.id.button);
        play = (Button)findViewById(R.id.gamebutton);
        pause = (Button)findViewById(R.id.playPauseButton);
        pause.setOnClickListener(new View.OnClickListener() {
            AlertDialog pauseDialog;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(LogicalMath.this);
                pauseMenu

                        .setMessage("Game Paused")
                        .setCancelable(false)
                        .setPositiveButton("Resume", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hide();
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
                pauseDialog = pauseMenu.create();
                pauseDialog.show();

            }

            public void hide()
            {
                pauseDialog.hide();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                button.setVisibility(View.INVISIBLE);
                play.setVisibility(View.VISIBLE);
            }
        });

    }



    private void putFruits(){
        Random randomOne=new Random();
        for(int i=0;i<fruits.length;i++){
            int index=randomOne.nextInt(fruits.length);
            String temp=fruits[i];
            fruits[i]=fruits[index];
            fruits[index]=temp;
        }
    }
    private void putValues(){
        Random randomOne=new Random();
        for(int i=0;i<3;i++){
            value[i]=randomOne.nextInt(6)+1;
        }

    }
    private void startGame(){
                 textNr=(EditText)findViewById(R.id.rightAnswer);
                textNr.setText("");
                Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setText("What is the number of last fruit ?");
                putFruits();
                putValues();
                play();
                }
        },1000);
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String num=textNr.getText().toString();
                        if(Integer.toString(value[2]).equals(num)){
                            sound.playCorrect();
                            score++;
                            text.setText("Correct");
                        }
                        else
                        {
                            sound.playWrong();
                            text.setText("Wrong");
                        }
                       startGame();
                    }

                });


    }

    private void play(){
        if(fruits[0].equals("apple")){
            f1.setImageResource(R.drawable.apple);
            f2.setImageResource(R.drawable.apple);
            f3.setImageResource(R.drawable.apple);
            f31.setImageResource(R.drawable.apple);
        }else if(fruits[0].equals("pear")){
            f1.setImageResource(R.drawable.pear);
            f2.setImageResource(R.drawable.pear);
            f3.setImageResource(R.drawable.pear);
            f31.setImageResource(R.drawable.pear);
        }else if(fruits[0].equals("strawberry")){
            f1.setImageResource(R.drawable.strawberry);
            f2.setImageResource(R.drawable.strawberry);
            f3.setImageResource(R.drawable.strawberry);
            f31.setImageResource(R.drawable.strawberry);
        }

        if(fruits[1].equals("apple")){
            f21.setImageResource(R.drawable.apple);
            f22.setImageResource(R.drawable.apple);
            f32.setImageResource(R.drawable.apple);
        }else if(fruits[1].equals("pear")){
            f21.setImageResource(R.drawable.pear);
            f22.setImageResource(R.drawable.pear);
            f32.setImageResource(R.drawable.pear);
        }else if(fruits[1].equals("strawberry")){
            f21.setImageResource(R.drawable.strawberry);
            f22.setImageResource(R.drawable.strawberry);
            f32.setImageResource(R.drawable.strawberry);
        }

        if(fruits[2].equals("apple")){
            f33.setImageResource(R.drawable.apple);
            result.setImageResource(R.drawable.apple);

        }else if(fruits[2].equals("pear")){
            f33.setImageResource(R.drawable.pear);
            result.setImageResource(R.drawable.pear);
        }else if(fruits[2].equals("strawberry")){
            f33.setImageResource(R.drawable.strawberry);
            result.setImageResource(R.drawable.strawberry);
        }

        calculateValues();

    }
    private void calculateValues(){

        int first=value[0];
        int second=value[1];
        int third=value[2];
        int firstL=value[0]*3;
        int secondL=value[1]*2;
        int thirdL=first+second+third;
        System.out.println(third);
        String res=Integer.toString(third);

        String firstLine=Integer.toString(firstL);
        String secondLine=Integer.toString(secondL);
        String thirdLine=Integer.toString(thirdL);

        answer1.setText(firstLine);
        answer2.setText(secondLine);
        answer3.setText(thirdLine);

        playCounter++;
        if(playCounter>=10){
            endGame();
        }
    }

    private void endGame() {

        String check;
        if(userID != 0)
        {
            boolean status = scoreDB.create(userID,"Logical Math",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(LogicalMath.this);
        alertDialogBuilder
                .setMessage("Game Over \nScore: "+score+"  "+check)
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),LogicalMath.class);
                        i.putExtra("ID",userID);
                        i.putExtra("Name",name);
                        finish();
                        startActivity(i);

                    }
                }).setNegativeButton("Next", new DialogInterface.OnClickListener() {
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
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();

    }

}
