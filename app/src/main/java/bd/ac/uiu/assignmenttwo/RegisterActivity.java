package bd.ac.uiu.assignmenttwo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import bd.ac.uiu.assignmenttwo.Database.DBHelper;
import bd.ac.uiu.assignmenttwo.Model.UserModel;

public class RegisterActivity extends AppCompatActivity {

    EditText nNameEditText, nEmailEditText, nPasswordEditText, nPhoneEditText, nCityEditText;
    Button registerButton;
    CheckBox nMaleCB, nFemaleCB;
    boolean cb=false;
    String uName,uEmail,uPassword,uPhone,uCity,uGender;

    DBHelper mDatabase;
    UserModel temp_userModel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        nMaleCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cb=true;
                    uGender="Male";
                    nFemaleCB.setActivated(false);
                    Log.i("nMaleCB:","Male checkbox checked");
                }
                else {
                    cb=false;
                    nFemaleCB.setActivated(true);
                }

            }
        });

        nFemaleCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cb=true;
                    uGender="Female";
                    nMaleCB.setActivated(false);
                    Log.i("nFemaleCB:","female checkbox checked");
                }
                else {
                    cb=false;
                    nMaleCB.setActivated(true);
                }
            }
        });

       registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               uName = nNameEditText.getText().toString();
               uEmail = nEmailEditText.getText().toString();
               uPassword = nPasswordEditText.getText().toString();
               uPhone = nPhoneEditText.getText().toString();
               uCity = nCityEditText.getText().toString();

               Log.i("registerActivity::",""+nNameEditText.getText().toString()+" "+uPassword);

               Log.i("registerButton","register Button Clicked");
               if(!isEmpty(uName) &&
                       !isEmpty(uEmail) &&
                       !isEmpty(uPassword) &&
                       !isEmpty(uPhone)  &&
                       !isEmpty(uCity) &&
                       cb)
               {

                   Log.i("registerActivity::",""+uName+" "+uPassword);
                   Log.i("registerButton","register Fields are not Empty");
                   //store user information
                   if (mDatabase.CheckUserName(uName) && mDatabase.CheckUserEmail(uEmail))
                   {
                       //user name not exists in database
                       temp_userModel=new UserModel(uName,uEmail,uPassword,uPhone,uGender,uCity);
                       if(mDatabase.addUserInfoToDatabase(temp_userModel))
                       {
                           Log.i("insertData","Data insert successfully");
                           Toast.makeText(getBaseContext(), "Account Created Successfully", Toast.LENGTH_LONG).show();

                           Intent regCom = new Intent(RegisterActivity.this,MainActivity.class);
                           startActivity(regCom);
                           finish();
                       }
                       else{
                           Log.i("insertData","Something is wrong to insert Data");
                       }

                   }
                   else {

                       String message="user name or email already exists in Database";
                       Toast.makeText(getBaseContext(), ""+message, Toast.LENGTH_SHORT).show();

                   }

               }
               else {
                   Toast.makeText(getBaseContext(), "Filled all the info correctly", Toast.LENGTH_SHORT).show();
               }
           }
       });


    }

    private void init() {

        nNameEditText = findViewById(R.id.new_name_et_id);
        nEmailEditText = findViewById(R.id.new_email_et_id);
        nPasswordEditText = findViewById(R.id.new_pass_et_id);
        nPhoneEditText = findViewById(R.id.new_phone_et_id);
        nCityEditText = findViewById(R.id.new_city_et_id);

        nMaleCB =findViewById(R.id.male_checkbox_id);
        nFemaleCB =findViewById(R.id.female_checkbox_id);

        registerButton = findViewById(R.id.register_button_id);

        mDatabase=new DBHelper(this);
    }

    private boolean isEmpty(String etText) {
        if (etText.trim().length() > 0)
            return false;

        return true;
    }
}
