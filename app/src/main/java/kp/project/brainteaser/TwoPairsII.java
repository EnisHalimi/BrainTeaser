package kp.project.brainteaser;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TwoPairsII extends AppCompatActivity {
    ImageView iv_11,iv_12, iv_13,iv_14,iv_15,iv_21,iv_22,iv_23, iv_24, iv_25, iv_31, iv_32, iv_33, iv_34, iv_35, iv_41,
            iv_42,iv_43, iv_44,iv_45;
    //TextView tv_p1;
    int image101, image102, image103, image104, image105, image106, image107, image108, image109,
            image110,image201, image202, image203, image204, image205, image206, image207, image208, image209, image210;
    int array[]=new int[20];
    int firstCard,secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber=1;
    int playerPoins=0;
    TextView t1;
    int userID;
    String name;
    Button pause;
    SoundPlayer sound;
    OptionsHelper opDB;
    ScoreHelper scoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_pairs_ii);
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
        t1 = (TextView) findViewById(R.id.result);

        pause = (Button)findViewById(R.id.playPauseButton);
        pause.setOnClickListener(new View.OnClickListener() {
            AlertDialog pauseDialog;
            @Override
            public void onClick(View v) {
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(TwoPairsII.this);
                pauseMenu

                        .setMessage("Game Paused")
                        .setCancelable(false)
                        .setPositiveButton("Resume", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hide();
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
        //tv_p1=(TextView)findViewById(R.id.tv_p1);

        iv_11=(ImageView)findViewById(R.id.iv_11);
        iv_12=(ImageView)findViewById(R.id.iv_12);
        iv_13=(ImageView)findViewById(R.id.iv_13);
        iv_14=(ImageView)findViewById(R.id.iv_14);
        iv_15=(ImageView)findViewById(R.id.iv_15);
        iv_21=(ImageView)findViewById(R.id.iv_21);
        iv_22=(ImageView)findViewById(R.id.iv_22);
        iv_23=(ImageView)findViewById(R.id.iv_23);
        iv_24=(ImageView)findViewById(R.id.iv_24);
        iv_25=(ImageView)findViewById(R.id.iv_25);
        iv_31=(ImageView)findViewById(R.id.iv_31);
        iv_32=(ImageView)findViewById(R.id.iv_32);
        iv_33=(ImageView)findViewById(R.id.iv_33);
        iv_34=(ImageView)findViewById(R.id.iv_34);
        iv_34=(ImageView)findViewById(R.id.iv_34);
        iv_35=(ImageView)findViewById(R.id.iv_35);
        iv_41=(ImageView)findViewById(R.id.iv_41);
        iv_42=(ImageView)findViewById(R.id.iv_42);
        iv_43=(ImageView)findViewById(R.id.iv_43);
        iv_44=(ImageView)findViewById(R.id.iv_44);
        iv_45=(ImageView)findViewById(R.id.iv_45);
        ;

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_15.setTag("4");
        iv_21.setTag("5");
        iv_22.setTag("6");
        iv_23.setTag("7");
        iv_24.setTag("8");
        iv_25.setTag("9");
        iv_31.setTag("10");
        iv_32.setTag("11");
        iv_33.setTag("12");
        iv_34.setTag("13");
        iv_35.setTag("14");
        iv_41.setTag("15");
        iv_42.setTag("16");
        iv_43.setTag("17");
        iv_44.setTag("18");
        iv_45.setTag("19");

        //load frondofcardsresources
        frontOfCardsResources();
        // tv_p1.setTextColor(Color.GRAY);

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_13, theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_14, theCard);
            }
        });
        iv_15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_15, theCard);
            }
        });
        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_23, theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_24, theCard);
            }
        });
        iv_25.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_25, theCard);
            }
        });
        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_31, theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_32, theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_33, theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_34, theCard);
            }
        });
        iv_35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_35, theCard);
            }
        });
        iv_41.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_41, theCard);
            }
        });
        iv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_42, theCard);
            }
        });
        iv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_43, theCard);
            }
        });
        iv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_44, theCard);
            }
        });
        iv_45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_45, theCard);
            }
        });

    }
    private void doStuff(ImageView iv, int card){
        if(card==0){
            iv.setImageResource(array[0]);
        }else if(card==1){
            iv.setImageResource(array[1]);
        }else if(card==2){
            iv.setImageResource(array[2]);
        }else if(card==3){
            iv.setImageResource(array[3]);
        }else if(card==4){
            iv.setImageResource(array[4]);
        }else if(card==5){
            iv.setImageResource(array[5]);
        }else if(card==6){
            iv.setImageResource(array[6]);
        }else if(card==7){
            iv.setImageResource(array[7]);
        }else if(card==8){
            iv.setImageResource(array[8]);
        }else if(card==9){
            iv.setImageResource(array[9]);
        }else if(card==10){
            iv.setImageResource(array[10]);
        }else if(card==11){
            iv.setImageResource(array[11]);
        }else if(card==12){
            iv.setImageResource(array[12]);
        }else if(card==13){
            iv.setImageResource(array[13]);
        }else if(card==14){
            iv.setImageResource(array[14]);
        }else if(card==15){
            iv.setImageResource(array[15]);
        }else if(card==16){
            iv.setImageResource(array[16]);
        }else if(card==17){
            iv.setImageResource(array[17]);
        }else if(card==18){
            iv.setImageResource(array[18]);
        }else if(card==19){
            iv.setImageResource(array[19]);
        }


        //kontrolloni cili imazh eshte selektuar
        if(cardNumber==1){
            // firstCard=cardscard;
            firstCard=array[card];
            cardNumber=2;
            clickedFirst=card;
            iv.setEnabled(false);
        } else if(cardNumber==2){
            //secondCard=cardscard;
            secondCard=array[card];
            cardNumber=1;
            clickedSecond=card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_15.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_25.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);
            iv_35.setEnabled(false);
            iv_41.setEnabled(false);
            iv_42.setEnabled(false);
            iv_43.setEnabled(false);
            iv_44.setEnabled(false);
            iv_45.setEnabled(false);

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //kontrollohet nese imazhi i kontilluar eshte i barabart
                    calculate();
                }
            },1000);
        }

    }
    private void calculate(){
        if(firstCard==secondCard){
            if(clickedFirst==0){
                iv_11.setVisibility(View.INVISIBLE);
                checkSecondCard();
            }else if(clickedFirst==1){
                iv_12.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==2){
                iv_13.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==3){
                iv_14.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==4){
                iv_15.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==5){
                iv_21.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==6){
                iv_22.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==7){
                iv_23.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==8){
                iv_24.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==9){
                iv_25.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==10){
                iv_31.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==11){
                iv_32.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==12){
                iv_33.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==13){
                iv_34.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==14){
                iv_35.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==15){
                iv_41.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==16){
                iv_42.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==17){
                iv_43.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==18){
                iv_44.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==19){
                iv_45.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }
            //add points
            playerPoins+=2;
            //tv_p1.setText("Points:"+playerPoins);

        }else{
            iv_11.setImageResource(R.drawable.question);
            iv_12.setImageResource(R.drawable.question);
            iv_13.setImageResource(R.drawable.question);
            iv_14.setImageResource(R.drawable.question);
            iv_15.setImageResource(R.drawable.question);
            iv_21.setImageResource(R.drawable.question);
            iv_22.setImageResource(R.drawable.question);
            iv_23.setImageResource(R.drawable.question);
            iv_24.setImageResource(R.drawable.question);
            iv_25.setImageResource(R.drawable.question);
            iv_31.setImageResource(R.drawable.question);
            iv_32.setImageResource(R.drawable.question);
            iv_33.setImageResource(R.drawable.question);
            iv_34.setImageResource(R.drawable.question);
            iv_35.setImageResource(R.drawable.question);
            iv_41.setImageResource(R.drawable.question);
            iv_42.setImageResource(R.drawable.question);
            iv_43.setImageResource(R.drawable.question);
            iv_44.setImageResource(R.drawable.question);
            iv_45.setImageResource(R.drawable.question);

        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_15.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_25.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);
        iv_35.setEnabled(true);
        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);
        iv_45.setEnabled(true);


        checkEnd();
    }

    private void checkSecondCard(){
        if(clickedSecond==0){
            iv_11.setVisibility(View.INVISIBLE);
        }else if(clickedSecond==1){
            iv_12.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==2){
            iv_13.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==3){
            iv_14.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==4){
            iv_15.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==5){
            iv_21.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==6){
            iv_22.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==7){
            iv_23.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==8){
            iv_24.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==9){
            iv_25.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==10){
            iv_31.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==11){
            iv_32.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==12){
            iv_33.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==13){
            iv_34.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==14){
            iv_35.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==15){
            iv_41.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==16){
            iv_42.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==17){
            iv_43.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==18){
            iv_44.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==19){
            iv_45.setVisibility(View.INVISIBLE);

        }

    }

    private void checkEnd(){
        if(iv_11.getVisibility()==View.INVISIBLE &&
                iv_12.getVisibility()==View.INVISIBLE &&
                iv_13.getVisibility()==View.INVISIBLE &&
                iv_14.getVisibility()==View.INVISIBLE &&
                iv_15.getVisibility()==View.INVISIBLE &&
                iv_21.getVisibility()==View.INVISIBLE &&
                iv_22.getVisibility()==View.INVISIBLE &&
                iv_23.getVisibility()==View.INVISIBLE &&
                iv_24.getVisibility()==View.INVISIBLE &&
                iv_25.getVisibility()==View.INVISIBLE &&
                iv_31.getVisibility()==View.INVISIBLE &&
                iv_32.getVisibility()==View.INVISIBLE &&
                iv_33.getVisibility()==View.INVISIBLE &&
                iv_34.getVisibility()==View.INVISIBLE &&
                iv_35.getVisibility()==View.INVISIBLE &&
                iv_41.getVisibility()==View.INVISIBLE &&
                iv_42.getVisibility()==View.INVISIBLE &&
                iv_43.getVisibility()==View.INVISIBLE &&
                iv_44.getVisibility()==View.INVISIBLE &&
                iv_45.getVisibility()==View.INVISIBLE){

            String check;
            if(userID != 0)
            {
                boolean status = scoreDB.create(userID,"Two PairsII",playerPoins);
                if(status)
                    check="Saved";
                else
                    check="Not Saved";
            }
            else
                check="Not Saved";

            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(TwoPairsII.this);
            alertDialogBuilder
                    .setMessage("Game Over \nScore: "+playerPoins+"  "+check)
                    .setCancelable(false)
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i=new Intent(getApplicationContext(),TwoPairsII.class);
                            i.putExtra("ID",userID);
                            i.putExtra("Name",name);
                            finish();
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


    private void frontOfCardsResources() {

        image101 = R.drawable.f1;
        image102 = R.drawable.f2;
        image103 = R.drawable.f3;
        image104 = R.drawable.f4;
        image105 = R.drawable.f5;
        image106 = R.drawable.f6;
        image107 = R.drawable.f7;
        image108 = R.drawable.f8;
        image109 = R.drawable.f9;
        image110 = R.drawable.f10;
        image201 = R.drawable.f1;
        image202 = R.drawable.f2;
        image203 = R.drawable.f3;
        image204 = R.drawable.f4;
        image205 = R.drawable.f5;
        image206 = R.drawable.f6;
        image207 = R.drawable.f7;
        image208 = R.drawable.f8;
        image209 = R.drawable.f9;
        image210 = R.drawable.f10;

        array[0]=image101;
        array[1]=image102;
        array[2]=image103;
        array[3]=image104;
        array[4]=image105;
        array[5]=image106;
        array[6]=image107;
        array[7]=image108;
        array[8]=image109;
        array[9]=image110;
        array[10]=image201;
        array[11]=image202;
        array[12]=image203;
        array[13]=image204;
        array[14]=image205;
        array[15]=image206;
        array[16]=image207;
        array[17]=image208;
        array[18]=image209;
        array[19]=image210;

        shuffleButtonGraphics(array);
    }

    protected void shuffleButtonGraphics(int[]array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int temp = array[i];
            int swapIndex = rand.nextInt(20);
            array[i] = array[swapIndex];
            array[swapIndex] = temp;
        }

    }
}


