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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderNumbersII extends AppCompatActivity {

    ScoreHelper scoreDB;
    int counter = 1;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9,b10,b11,b12,b13,b14,b15,b16,start, pause;
    List<Integer> numbers;
    int score = 0;
    TextView result;
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
        setContentView(R.layout.activity_order_numbers_ii);
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
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        b10 = (Button) findViewById(R.id.button10);
        b11 = (Button) findViewById(R.id.button11);
        b12 = (Button) findViewById(R.id.button12);
        b13 = (Button) findViewById(R.id.button13);
        b14 = (Button) findViewById(R.id.button14);
        b15 = (Button) findViewById(R.id.button15);
        b16 = (Button) findViewById(R.id.button16);
        start = (Button) findViewById(R.id.startButton);
        pause = (Button)findViewById(R.id.playPauseButton);
        pause.setVisibility(View.INVISIBLE);
        result = (TextView)findViewById(R.id.result);
        timeBar = (ProgressBar) findViewById(R.id.timeBar);
        timeBar.setMax(60);
        timeBar.getProgressDrawable().setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        scoreDB = new ScoreHelper(this);
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
                AlertDialog.Builder pauseMenu=new AlertDialog.Builder(OrderNumbersII.this);
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
        });

    }


    public void stop()
    {

        String check;
        if(userID != 0)
        {
            boolean status = scoreDB.create(userID,"Order NumbersII",score);
            if(status)
                check="Saved";
            else
                check="Not Saved";
        }
        else
            check="Not Saved";


        AlertDialog.Builder oalertDialogBuilder=new AlertDialog.Builder(OrderNumbersII.this);
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
        AlertDialog alertDialogBuilder = oalertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    public void gameover(String value) {
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        b4.setEnabled(false);
        b5.setEnabled(false);
        b6.setEnabled(false);
        b7.setEnabled(false);
        b8.setEnabled(false);
        b9.setEnabled(false);
        b10.setEnabled(false);
        b11.setEnabled(false);
        b12.setEnabled(false);
        b13.setEnabled(false);
        b14.setEnabled(false);
        b15.setEnabled(false);
        b16.setEnabled(false);

        if(value.equals("Correct"))
            score++;
        result.setText(value);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game();
            }
        },1000);

    }




    public void game() {
        result.setText("");
        counter = 1;
        b1.setEnabled(true);
        b1.setBackgroundResource(R.drawable.graybtn);
        b2.setEnabled(true);
        b2.setBackgroundResource(R.drawable.graybtn);
        b3.setEnabled(true);
        b3.setBackgroundResource(R.drawable.graybtn);
        b4.setEnabled(true);
        b4.setBackgroundResource(R.drawable.graybtn);
        b5.setEnabled(true);
        b5.setBackgroundResource(R.drawable.graybtn);
        b6.setEnabled(true);
        b6.setBackgroundResource(R.drawable.graybtn);
        b7.setEnabled(true);
        b7.setBackgroundResource(R.drawable.graybtn);
        b8.setEnabled(true);
        b8.setBackgroundResource(R.drawable.graybtn);
        b9.setEnabled(true);
        b9.setBackgroundResource(R.drawable.graybtn);
        b10.setEnabled(true);
        b10.setBackgroundResource(R.drawable.graybtn);
        b11.setEnabled(true);
        b11.setBackgroundResource(R.drawable.graybtn);
        b12.setEnabled(true);
        b12.setBackgroundResource(R.drawable.graybtn);
        b13.setEnabled(true);
        b13.setBackgroundResource(R.drawable.graybtn);
        b14.setEnabled(true);
        b14.setBackgroundResource(R.drawable.graybtn);
        b15.setEnabled(true);
        b15.setBackgroundResource(R.drawable.graybtn);
        b16.setEnabled(true);
        b16.setBackgroundResource(R.drawable.graybtn);
        numbers = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        b1.setText("" + numbers.get(0));
        b2.setText("" + numbers.get(1));
        b3.setText("" + numbers.get(2));
        b4.setText("" + numbers.get(3));
        b5.setText("" + numbers.get(4));
        b6.setText("" + numbers.get(5));
        b7.setText("" + numbers.get(6));
        b8.setText("" + numbers.get(7));
        b9.setText("" + numbers.get(8));
        b10.setText("" + numbers.get(9));
        b11.setText("" + numbers.get(10));
        b12.setText("" + numbers.get(11));
        b13.setText("" + numbers.get(12));
        b14.setText("" + numbers.get(13));
        b15.setText("" + numbers.get(14));
        b16.setText("" + numbers.get(15));

        buttons();



    }

    public void buttons() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b1.getText().toString());
                if (number == counter) {
                    b1.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b1.setEnabled(false);
                } else {
                    b1.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b2.getText().toString());
                if (number == counter) {
                    b2.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b2.setEnabled(false);
                } else {
                    b2.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b3.getText().toString());
                if (number == counter) {
                    b3.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b3.setEnabled(false);
                } else {
                    b3.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b4.getText().toString());
                if (number == counter) {
                    b4.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b4.setEnabled(false);
                } else {
                    b4.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b5.getText().toString());
                if (number == counter) {
                    b5.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b5.setEnabled(false);
                } else {
                    b5.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b6.getText().toString());
                if (number == counter) {
                    b6.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b6.setEnabled(false);
                } else {
                    b6.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b7.getText().toString());
                if (number == counter) {
                    b7.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b7.setEnabled(false);
                } else {
                    b7.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b8.getText().toString());
                if (number == counter) {
                    b8.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b8.setEnabled(false);
                } else {
                    b8.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b9.getText().toString());
                if (number == counter) {
                    b9.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b9.setEnabled(false);
                } else {
                    b9.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b10.getText().toString());
                if (number == counter) {
                    b10.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b10.setEnabled(false);
                } else {
                    b10.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b11.getText().toString());
                if (number == counter) {
                    b11.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b11.setEnabled(false);
                } else {
                    b11.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b12.getText().toString());
                if (number == counter) {
                    b12.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b12.setEnabled(false);
                } else {
                    b12.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b13.getText().toString());
                if (number == counter) {
                    b13.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b13.setEnabled(false);
                } else {
                    b13.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b14.getText().toString());
                if (number == counter) {
                    b14.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b14.setEnabled(false);
                } else {
                    b14.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b15.getText().toString());
                if (number == counter) {
                    b15.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b15.setEnabled(false);
                } else {
                    b15.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });
        b16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b16.getText().toString());
                if (number == counter) {
                    b16.setBackgroundResource(R.drawable.button_green);
                    counter++;
                    b16.setEnabled(false);
                } else {
                    b16.setBackgroundResource(R.drawable.redbtn);
                    gameover("Wrong");
                    sound.playWrong();
                }
                if (counter == 16) {
                    gameover("Correct");
                    sound.playCorrect();

                }

            }
        });

    }
    public void onBackPressed()
    {
        return;
    }


}

