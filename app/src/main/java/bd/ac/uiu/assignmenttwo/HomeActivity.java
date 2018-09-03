package bd.ac.uiu.assignmenttwo;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    SharedPreferences preferences;
    SharedPreferences loginPreferences;
    Button logoutBtn;
    TextView nameTv,emailTv,phoneTv,cityTv,genderTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // init();

        mBottomNavigationView=findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        //initial fragments
        Fragment profileFragment=new ProfileFragment();

        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                profileFragment).commit();

        ((ProfileFragment)profileFragment).setOnButtonClickListener(new ProfileFragment.onButtonClickListener() {
            @Override
            public void Clicked() {

                preferences=getBaseContext().getSharedPreferences("userinfo",MODE_PRIVATE);
                preferences.edit().clear().apply();
                Log.i("HomeActivity::","logoutBtn: logout succesfully done");
                Intent getBack=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(getBack);
                finish();

            }
        });

        //initial User details setup from sharedPreference
//        loginPreferences=getBaseContext().getSharedPreferences("loginDetails",MODE_PRIVATE);
//        String id=loginPreferences.getString("userId","");
//        String Name=loginPreferences.getString("userName","");
//        String Email=loginPreferences.getString("userEmail","");
//        String Password=loginPreferences.getString("userPassword","");
//        String Phone=loginPreferences.getString("userPhone","");
//        String Gender=loginPreferences.getString("userGender","");
//        String City=loginPreferences.getString("userCity","");

//        nameTv.setText(Name);
//        emailTv.setText(Email);
//        phoneTv.setText(Phone);
//        genderTv.setText(Gender);
//        cityTv.setText(City);



        //if user logout button press then userLogin data will be cleared
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                preferences=getBaseContext().getSharedPreferences("userinfo",MODE_PRIVATE);
//                preferences.edit().clear().apply();
//                Log.i("HomeActivity::","logoutBtn: logout succesfully done");
//                Intent getBack=new Intent(HomeActivity.this,MainActivity.class);
//                startActivity(getBack);
//                finish();
//            }
//        });


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
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment =null;

                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new ProfileFragment();
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.nav_feed:
                            selectedFragment= new NewsFeedFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
}
