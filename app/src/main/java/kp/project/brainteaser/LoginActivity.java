package kp.project.brainteaser;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    UserHelper userDB;
    EditText username,password,registerUsername,registerName,registerPassword,registerDate;
    Button signIn,register,signUp,back;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userDB = new UserHelper(this);
        checkLogin();
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.username);
        password =  (EditText) findViewById(R.id.password);
        signIn = (Button)findViewById(R.id.sign_in);
        register = (Button)findViewById(R.id.register);

        login();
        signUp();
    }

    public void onBackPressed(){
        return;
    }

    public void checkLogin()
    {
        Cursor res = userDB.getData();
        if(res.getCount()== 0)
            return;

        while(res.moveToNext())
        {
            if(res.getString(5).equals("1"))
            {
                String username = res.getString(3);
                int id = res.getInt(0);
                String name = res.getString(1);
                logging(username, id,name );
            }
        }
    }

    public void logging(String username, int id, String name)
    {
        Toast.makeText(LoginActivity.this, "Welcome "+name, Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
       i.putExtra("Username", username);
       i.putExtra("ID",id);
        finish();
        startActivity(i);
    }

    public void login()
    {
        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Cursor res = userDB.getData();
                if(res.getCount()== 0)
                    return;

                while(res.moveToNext())
                {
                    if(user.equals(res.getString(3)))
                    {
                        if(pass.equals( res.getString(4)))
                        {
                          userDB.loginStatus(res.getInt(0));
                          logging(user, res.getInt(0), res.getString(1));
                          return;

                        }
                    }


                }
                Toast.makeText(LoginActivity.this,"Username or Password don't match",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void signUp()
    {
        register.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_sign_up);
                        registerUsername = (EditText)findViewById(R.id.registerUsername);
                        registerPassword = (EditText)findViewById(R.id.registerPassword);
                        registerDate = (EditText)findViewById(R.id.registerDate);
                        registerName = (EditText)findViewById(R.id.registerName);
                        signUp = (Button)findViewById(R.id.signUp);
                        back = (Button)findViewById(R.id.backButton);
                        registerUser();
                    }
                }
        );
    }
    public void registerUser()
    {
        signUp.setOnClickListener(
                new View.OnClickListener(){
                    String status = "0";
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = userDB.insertData(registerName.getText().toString(),registerDate.getText().toString(),registerUsername.getText().toString(), registerPassword.getText().toString(),status);
                        if(isInserted) {
                            Toast.makeText(LoginActivity.this, "User is registered", Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(LoginActivity.this, "User is not registered", Toast.LENGTH_LONG).show();
                    }
                }
        );
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
    }
}



