package kp.project.brainteaser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrderNumbers extends AppCompatActivity {

    int counter = 1;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, start;
    List<Integer> numbers;
    int score = 0;
    TextView time, result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_numbers);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        start = (Button) findViewById(R.id.startButton);
        b1.setBackgroundResource(android.R.drawable.btn_default);
        b2.setBackgroundResource(android.R.drawable.btn_default);

        b3.setBackgroundResource(android.R.drawable.btn_default);

        b4.setBackgroundResource(android.R.drawable.btn_default);

        b5.setBackgroundResource(android.R.drawable.btn_default);

        b6.setBackgroundResource(android.R.drawable.btn_default);

        b7.setBackgroundResource(android.R.drawable.btn_default);

        b8.setBackgroundResource(android.R.drawable.btn_default);

        b9.setBackgroundResource(android.R.drawable.btn_default);
        time = (TextView)findViewById(R.id.time);
        result = (TextView)findViewById(R.id.result);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.INVISIBLE);
                game();
                new CountDownTimer(60000, 1000)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        int number = Integer.parseInt(time.getText().toString());
                        number = number -1;
                        time.setText(""+number);
                    }

                    @Override
                    public void onFinish() {
                        stop();
                    }
                }.start();


            }
        });


    }
    public void stop()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(OrderNumbers.this);
        alert
                .setMessage("Game Over\nScore: "+score)
                .setCancelable(false)
                .setPositiveButton("Replay", new DialogInterface.OnClickListener(){
                    @Override
                     public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   finish();
                }
            })
        .setNeutralButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(OrderNumbers.this, "Next", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDiaglog = alert.create();
        alertDiaglog.show();
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
        b1.setBackgroundResource(android.R.drawable.btn_default);
        b2.setEnabled(true);
        b2.setBackgroundResource(android.R.drawable.btn_default);
        b3.setEnabled(true);
        b3.setBackgroundResource(android.R.drawable.btn_default);
        b4.setEnabled(true);
        b4.setBackgroundResource(android.R.drawable.btn_default);
        b5.setEnabled(true);
        b5.setBackgroundResource(android.R.drawable.btn_default);
        b6.setEnabled(true);
        b6.setBackgroundResource(android.R.drawable.btn_default);
        b7.setEnabled(true);
        b7.setBackgroundResource(android.R.drawable.btn_default);
        b8.setEnabled(true);
        b8.setBackgroundResource(android.R.drawable.btn_default);
        b9.setEnabled(true);
        b9.setBackgroundResource(android.R.drawable.btn_default);
        numbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
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

        buttons();



    }

    public void buttons() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b1.getText().toString());
                if (number == counter) {
                    b1.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b1.setEnabled(false);
                } else {
                    b1.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b2.getText().toString());
                if (number == counter) {
                    b2.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b2.setEnabled(false);
                } else {
                    b2.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b3.getText().toString());
                if (number == counter) {
                    b3.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b3.setEnabled(false);
                } else {
                    b3.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b4.getText().toString());
                if (number == counter) {
                    b4.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b4.setEnabled(false);
                } else {
                    b4.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b5.getText().toString());
                if (number == counter) {
                    b5.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b5.setEnabled(false);
                } else {
                    b5.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b6.getText().toString());
                if (number == counter) {
                    b6.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b6.setEnabled(false);
                } else {
                    b6.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b7.getText().toString());
                if (number == counter) {
                    b7.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b7.setEnabled(false);
                } else {
                    b7.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b8.getText().toString());
                if (number == counter) {
                    b8.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b8.setEnabled(false);
                } else {
                    b8.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(b9.getText().toString());
                if (number == counter) {
                    b9.setBackgroundColor(Color.parseColor("#00FF00"));
                    counter++;
                    b9.setEnabled(false);
                } else {
                    b9.setBackgroundColor(Color.parseColor("#FF0000"));
                    gameover("Wrong");
                }
                if (counter == 9)
                    gameover("Correct");

            }
        });

    }
    public void onBackPressed()
    {
        return;
    }


}
