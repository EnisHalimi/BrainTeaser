package kp.project.brainteaser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Random;


public class TwoPairs extends AppCompatActivity {

    TextView tv_p1;
    //TextView tv_p2;

    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24,iv_31, iv_32, iv_33, iv_34;

    //array per imazhet
    int []cardsArray={101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};

    //imazhet aktuale
    int image101, image102, image103, image104, image105, image106, image201, image202, image203, image204, image205, image206;

    int array[]=new int[12];

    int firstCard,secondCard;
    int clickedFirst, clickedSecond;
    int clicked;
    int cardNumber=1;
    int playerPoins=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_pairs);

        tv_p1=(TextView)findViewById(R.id.tv_p1);

        iv_11=(ImageView)findViewById(R.id.iv_11);
        iv_12=(ImageView)findViewById(R.id.iv_12);
        iv_13=(ImageView)findViewById(R.id.iv_13);
        iv_14=(ImageView)findViewById(R.id.iv_14);
        iv_21=(ImageView)findViewById(R.id.iv_21);
        iv_22=(ImageView)findViewById(R.id.iv_22);
        iv_23=(ImageView)findViewById(R.id.iv_23);
        iv_24=(ImageView)findViewById(R.id.iv_24);
        iv_31=(ImageView)findViewById(R.id.iv_31);
        iv_32=(ImageView)findViewById(R.id.iv_32);
        iv_33=(ImageView)findViewById(R.id.iv_33);
        iv_34=(ImageView)findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        //load frondofcardsresources
        frontOfCardsResources();
        tv_p1.setTextColor(Color.GRAY);

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
            @Override
            public void onClick(View v) {
                int theCard= Integer.parseInt((String)v.getTag());
                doStuff(iv_34, theCard);
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
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

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
                iv_21.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==5){
                iv_22.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==6){
                iv_23.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==7){
                iv_24.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==8){
                iv_31.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==9){
                iv_32.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==10){
                iv_33.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }else if(clickedFirst==11){
                iv_34.setVisibility(View.INVISIBLE);
                checkSecondCard();

            }
            //add points
            playerPoins+=2;
            tv_p1.setText("Points:"+playerPoins);

        }else{
            iv_11.setImageResource(R.drawable.question);
            iv_12.setImageResource(R.drawable.question);
            iv_13.setImageResource(R.drawable.question);
            iv_14.setImageResource(R.drawable.question);
            iv_21.setImageResource(R.drawable.question);
            iv_22.setImageResource(R.drawable.question);
            iv_23.setImageResource(R.drawable.question);
            iv_24.setImageResource(R.drawable.question);
            iv_31.setImageResource(R.drawable.question);
            iv_32.setImageResource(R.drawable.question);
            iv_33.setImageResource(R.drawable.question);
            iv_34.setImageResource(R.drawable.question);
            //ndryshon radhen e lojtareve
        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

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
            iv_21.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==5){
            iv_22.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==6){
            iv_23.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==7){
            iv_24.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==8){
            iv_31.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==9){
            iv_32.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==10){
            iv_33.setVisibility(View.INVISIBLE);

        }else if(clickedSecond==11){
            iv_34.setVisibility(View.INVISIBLE);

        }

    }

    private void checkEnd(){
        if(iv_11.getVisibility()==View.INVISIBLE &&
                iv_12.getVisibility()==View.INVISIBLE &&
                iv_13.getVisibility()==View.INVISIBLE &&
                iv_14.getVisibility()==View.INVISIBLE &&
                iv_21.getVisibility()==View.INVISIBLE &&
                iv_22.getVisibility()==View.INVISIBLE &&
                iv_23.getVisibility()==View.INVISIBLE &&
                iv_24.getVisibility()==View.INVISIBLE &&
                iv_31.getVisibility()==View.INVISIBLE &&
                iv_32.getVisibility()==View.INVISIBLE &&
                iv_33.getVisibility()==View.INVISIBLE &&
                iv_34.getVisibility()==View.INVISIBLE){

            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(TwoPairs.this);
            alertDialogBuilder
                    .setMessage("Game Over \nScore: "+playerPoins)
                    .setCancelable(false)
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(getApplicationContext(),TwoPairs.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("Next", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(), SwitchColors.class);
                    startActivity(i);
                }
            })
                    .setNeutralButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), MainMenu.class);
                            startActivity(i);
                        }
                    });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void frontOfCardsResources() {

        image101 = R.drawable.num3;
        image102 = R.drawable.num5;
        image103 = R.drawable.num1;
        image104 = R.drawable.num6;
        image105 = R.drawable.num2;
        image106 = R.drawable.num4;
        image201 = R.drawable.num3;
        image202 = R.drawable.num5;
        image203 = R.drawable.num1;
        image204 = R.drawable.num6;
        image205 = R.drawable.num2;
        image206 = R.drawable.num4;

        array[0]=image101;
        array[1]=image102;
        array[2]=image103;
        array[3]=image104;
        array[4]=image105;
        array[5]=image106;
        array[6]=image201;
        array[7]=image202;
        array[8]=image203;
        array[9]=image204;
        array[10]=image205;
        array[11]=image206;

        shuffleButtonGraphics(array);
    }

    protected void shuffleButtonGraphics(int[]array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int temp = array[i];
            int swapIndex = rand.nextInt(12);
            array[i] = array[swapIndex];
            array[swapIndex] = temp;
        }

    }
    public void onBackPressed(){
        return;
    }
}
