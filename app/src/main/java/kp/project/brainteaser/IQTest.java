package kp.project.brainteaser;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class IQTest extends AppCompatActivity {

    Button back,submit,next;
    RadioButton b1,b2,b3,b4;
    RadioGroup btns;
    ImageView v1,v2,v3,v4,question;
    int positionChecked[]=new int[20];
    int rightAnswers[]=new int[20];
    private int score=0;
    DatabaseHelper database;
    String name;
    int userID;
    SoundPlayer sound;
    TextView counter;


    ImageView result1,result2,result3,result4,result5,result6,result7,result8,result9,result10;
    int count=0;
    int countPoints=0;
    int turnoff=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iqtest);
        database = new DatabaseHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getInt("ID");
            name = extras.getString("Name");
        }
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

        back=(Button)findViewById(R.id.back);
        submit=(Button)findViewById(R.id.submit);
        next=(Button)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);
        counter=(TextView)findViewById(R.id.counter);

        b1=(RadioButton)findViewById(R.id.radio1);
        b2=(RadioButton)findViewById(R.id.radio2);
        b3=(RadioButton)findViewById(R.id.radio3);
        b4=(RadioButton)findViewById(R.id.radio4);
        btns=(RadioGroup)findViewById(R.id.group);

        v1=(ImageView)findViewById(R.id.firstAnswer);
        v2=(ImageView)findViewById(R.id.secondAnswer);
        v3=(ImageView)findViewById(R.id.thirdAnswer);
        v4=(ImageView)findViewById(R.id.fourthAnswer);
        question=(ImageView)findViewById(R.id.screen);
        putPoints();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setText("Submit");
                next.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                if(turnoff==1){
                    checkAnswers();
                    gameOver();
                }
                turnoff++;
                startGame();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btns.clearCheck();
                count++;
                counter.setText((count+1)+"/20");
                startGame();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btns.clearCheck();
                count--;
                counter.setText((count+1)+"/20");
                startGame();

            }
        });
    }



    private void putPoints() {

        rightAnswers[0]=2;
        rightAnswers[1]=2;
        rightAnswers[2]=1;
        rightAnswers[3]=4;
        rightAnswers[4]=3;
        rightAnswers[5]=2;
        rightAnswers[6]=2;
        rightAnswers[7]=1;
        rightAnswers[8]=2;
        rightAnswers[9]=3;
        rightAnswers[10]=3;
        rightAnswers[11]=1;
        rightAnswers[12]=3;
        rightAnswers[13]=4;
        rightAnswers[14]=4;
        rightAnswers[15]=1;
        rightAnswers[16]=2;
        rightAnswers[17]=4;
        rightAnswers[18]=4;
        rightAnswers[19]=3;


    }
    public void checkAnswers(){
        for(int i=0;i<20;i++){
            if(rightAnswers[i]==positionChecked[i]){
                countPoints+=10;
            }
        }
    }

    private void startGame(){

        btns.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio1:
                        positionChecked[count]=1;
                        break;
                    case R.id.radio2:
                        positionChecked[count]=2;
                        break;
                    case R.id.radio3:
                        positionChecked[count]=3;
                        break;
                    case R.id.radio4:
                        positionChecked[count]=4;
                        break;
                    default:
                        break;
                }
            }
        });

        if(count==0) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            back.setEnabled(false);

            question.setImageResource(R.drawable.question1);
            v1.setImageResource(R.drawable.answer1_2);
            v2.setImageResource(R.drawable.answer1);
            v3.setImageResource(R.drawable.answer1_3);
            v4.setImageResource(R.drawable.answer1_4);

        }else if(count==1) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            back.setEnabled(true);
            question.setImageResource(R.drawable.question2);
            v1.setImageResource(R.drawable.answer2_1);
            v2.setImageResource(R.drawable.answer2);
            v3.setImageResource(R.drawable.answer2_2);
            v4.setImageResource(R.drawable.answer2_3);

        }else if(count==2) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question3);
            v1.setImageResource(R.drawable.answer3);
            v2.setImageResource(R.drawable.answer3_1);
            v3.setImageResource(R.drawable.answer3_2);
            v4.setImageResource(R.drawable.answer3_3);
        }else if(count==3) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question4);
            v1.setImageResource(R.drawable.answer4_3);
            v2.setImageResource(R.drawable.answer4_1);
            v3.setImageResource(R.drawable.answer4_2);
            v4.setImageResource(R.drawable.answer4);
        }else if(count==4) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question5);
            v1.setImageResource(R.drawable.answer5);
            v2.setImageResource(R.drawable.answer5_1);
            v3.setImageResource(R.drawable.answer5_2);
            v4.setImageResource(R.drawable.answer5_3);
        }else if(count==5) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question6);
            v1.setImageResource(R.drawable.answer6);
            v2.setImageResource(R.drawable.answer6_1);
            v3.setImageResource(R.drawable.answer6_2);
            v4.setImageResource(R.drawable.answer6_3);
        }else if(count==6) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question7);
            v1.setImageResource(R.drawable.answer7_2);
            v2.setImageResource(R.drawable.answer7_1);
            v3.setImageResource(R.drawable.answer7);
            v4.setImageResource(R.drawable.answer7_3);
        }else if(count==7) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question8);
            v1.setImageResource(R.drawable.answer8);
            v2.setImageResource(R.drawable.answer8_1);
            v3.setImageResource(R.drawable.answer8_2);
            v4.setImageResource(R.drawable.answer8_3);
        }else if(count==8) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question9);
            v1.setImageResource(R.drawable.answer9_1);
            v2.setImageResource(R.drawable.answer9);
            v3.setImageResource(R.drawable.answer9_2);
            v4.setImageResource(R.drawable.answer9_3);
        }else if(count==9) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question10);
            v1.setImageResource(R.drawable.answer10_2);
            v2.setImageResource(R.drawable.answer10_1);
            v3.setImageResource(R.drawable.answer10);
            v4.setImageResource(R.drawable.answer10_3);
        }else if(count==10) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }


            question.setImageResource(R.drawable.question11);
            v1.setImageResource(R.drawable.answer11);
            v2.setImageResource(R.drawable.answer11_1);
            v3.setImageResource(R.drawable.answer11_2);
            v4.setImageResource(R.drawable.answer11_3);

        }else if(count==11) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question12);
            v1.setImageResource(R.drawable.answer12);
            v2.setImageResource(R.drawable.answer12_1);
            v3.setImageResource(R.drawable.answer12_2);
            v4.setImageResource(R.drawable.answer12_3);

        }else if(count==12) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question13);
            v1.setImageResource(R.drawable.answer13);
            v2.setImageResource(R.drawable.answer13_1);
            v3.setImageResource(R.drawable.answer13_2);
            v4.setImageResource(R.drawable.answer13_3);
        }else if(count==13) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question14);
            v1.setImageResource(R.drawable.answer14);
            v2.setImageResource(R.drawable.answer14_1);
            v3.setImageResource(R.drawable.answer14_2);
            v4.setImageResource(R.drawable.answer14_3);
        }else if(count==14) {
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question15);
            v1.setImageResource(R.drawable.answer15);
            v2.setImageResource(R.drawable.answer15_1);
            v3.setImageResource(R.drawable.answer15_2);
            v4.setImageResource(R.drawable.answer15_3);
        }else if(count==15) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question16);
            v1.setImageResource(R.drawable.answer16);
            v2.setImageResource(R.drawable.answer16_1);
            v3.setImageResource(R.drawable.answer16_2);
            v4.setImageResource(R.drawable.answer16_3);
        }else if(count==16) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question17);
            v1.setImageResource(R.drawable.answer17);
            v2.setImageResource(R.drawable.answer17_1);
            v3.setImageResource(R.drawable.answer17_2);
            v4.setImageResource(R.drawable.answer17_3);
        }else if(count==17) {

            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question18);
            v1.setImageResource(R.drawable.answer18);
            v2.setImageResource(R.drawable.answer18_1);
            v3.setImageResource(R.drawable.answer18_2);
            v4.setImageResource(R.drawable.answer18_3);
        }else if(count==18) {
            next.setEnabled(true);
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question19);
            v1.setImageResource(R.drawable.answer19);
            v2.setImageResource(R.drawable.answer19_1);
            v3.setImageResource(R.drawable.answer19_2);
            v4.setImageResource(R.drawable.answer19_3);
        }else if(count==19) {
            next.setEnabled(false);
            if (positionChecked[count] == 1) {
                b1.setChecked(true);

            } else if (positionChecked[count] == 2) {
                b2.setChecked(true);

            } else if (positionChecked[count] == 3) {
                b3.setChecked(true);

            } else if (positionChecked[count] == 4) {
                b4.setChecked(true);
            }
            question.setImageResource(R.drawable.question20);
            v1.setImageResource(R.drawable.answer20);
            v2.setImageResource(R.drawable.answer20_1);
            v3.setImageResource(R.drawable.answer20_2);
            v4.setImageResource(R.drawable.answer20_3);
        }

    }

    private void gameOver() {
        String check;
        if(userID != 0)
        {
            boolean status = database.createScore(userID,"IQ Test",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(IQTest.this);
        alertDialogBuilder
                .setMessage("Game Over \nPlayer: "+countPoints+" "+check)
                .setCancelable(false)
                .setPositiveButton("New", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getApplicationContext(),IQTest.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onBackPressed()
    {
        return;
    }


}
