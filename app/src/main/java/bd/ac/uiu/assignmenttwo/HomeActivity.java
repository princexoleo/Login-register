package bd.ac.uiu.assignmenttwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences loginPreferences;
    Button logoutBtn;
    TextView nameTv,emailTv,phoneTv,cityTv,genderTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        //initial User details setup from sharedPreference
        loginPreferences=getBaseContext().getSharedPreferences("loginDetails",MODE_PRIVATE);
        String id=loginPreferences.getString("userId","");
        String Name=loginPreferences.getString("userName","");
        String Email=loginPreferences.getString("userEmail","");
        String Password=loginPreferences.getString("userPassword","");
        String Phone=loginPreferences.getString("userPhone","");
        String Gender=loginPreferences.getString("userGender","");
        String City=loginPreferences.getString("userCity","");

        nameTv.setText(Name);
        emailTv.setText(Email);
        phoneTv.setText(Phone);
        genderTv.setText(Gender);
        cityTv.setText(City);



        //if user logout button press then userLogin data will be cleared
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences=getBaseContext().getSharedPreferences("userinfo",MODE_PRIVATE);
                preferences.edit().clear().apply();
                Log.i("HomeActivity::","logoutBtn: logout succesfully done");
                Intent getBack=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(getBack);
                finish();
            }
        });
    }

    private void init() {
        preferences=getSharedPreferences("userinfo",MODE_PRIVATE);
        logoutBtn = findViewById(R.id.logout_btn);

        nameTv =findViewById(R.id.userName_et);
        emailTv =findViewById(R.id.userEmail_et);
        phoneTv =findViewById(R.id.userPhone_et);
        genderTv =findViewById(R.id.userGender_et);
        cityTv =findViewById(R.id.userCity_et);
    }
}
