package bd.ac.uiu.assignmenttwo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import bd.ac.uiu.assignmenttwo.Database.DBHelper;
import bd.ac.uiu.assignmenttwo.Model.UserModel;

public class MainActivity extends AppCompatActivity {
    Button uLogingButton, nRegisterButton;
    EditText uNameEditText, uPasswordEditText;
    Boolean cb=false;
    CheckBox rememberCB;

    DBHelper mDatabase;
    ArrayList<UserModel> tempUserInfo_list=null;

    SharedPreferences preferences;

    String userName=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if user Saved his/her login session then it skip the login activity
        //if user saved login data then it created a userdetails sharedPreference
        // now we check it ..
        preferences = getBaseContext().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if(preferences.contains("username")){
            Log.i("LoginActivity: ","Skip Login acitivity !");

            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            setContentView(R.layout.activity_main);
            init();

            rememberCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb = true;
                        Log.i("remberCB: :", "Remmeber me checkbox checked");
                    } else {
                        cb = false;
                    }
                }
            });
            uLogingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = uNameEditText.getText().toString();
                    String userPassword = uPasswordEditText.getText().toString();

                    //check user given info is not empty
                    if (!isEmpty(userName) && !isEmpty(userPassword)) {
                        Log.i("LoginActivity: ","login fields are not empty");
                        //check username and password to database
                        // if found on the database then intent to HomeActivity
                        if (mDatabase.checkLogin(userName, userPassword)) {
                            Toast.makeText(MainActivity.this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                            if (cb) {
                                Log.i("LoginActivity: ","Sharedperference created !");
                                //remember my login
                               // preferences = getBaseContext().getSharedPreferences("userinfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("username", userName);
                                editor.putString("userpassword", userPassword);
                                editor.apply();
                            }

                            Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(loginIntent);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else {
                        Toast.makeText(MainActivity.this, "Failed !! Empty Fields are not allowed", Toast.LENGTH_SHORT).show();
                    }



                }
            });


            //if user don't have an any account
            //then user should create a new account
            nRegisterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(registerIntent);
                }
            });

        }
    }

    private void init() {

        uNameEditText = findViewById(R.id.u_name_et);
        uPasswordEditText = findViewById(R.id.u_password_et);

        rememberCB= findViewById(R.id.remember_me_checkbox);

        nRegisterButton = findViewById(R.id.register_button_id);
        uLogingButton = findViewById(R.id.login_btn);


        mDatabase=new DBHelper(this);
    }
    private boolean isEmpty(String etText) {
        if (etText.trim().length() > 0)
            return false;

        return true;
    }
}
