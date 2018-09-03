package bd.ac.uiu.assignmenttwo;


import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.WindowManager;


import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    Button logoutBtn;
    TextView nameTv,emailTv,phoneTv,genderTv,cityTv;

    SharedPreferences preferences;
    SharedPreferences loginPreferences;
    onButtonClickListener listener;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.i("profileFragment:","user profile Fragment");

        View v=inflater.inflate(R.layout.fragment_profile,container,false);
        init(v);



        loginPreferences=getActivity().getSharedPreferences("loginDetails",MODE_PRIVATE);

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
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                preferences=getActivity().getSharedPreferences("userinfo",MODE_PRIVATE);
//                preferences.edit().clear().apply();
//                Log.i("HomeActivity::","logoutBtn: logout succesfully done");
//                Intent getBack=new Intent(getActivity(),MainActivity.class);
//                startActivity(getBack);
//                onStop();
//            }
//        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.Clicked();
            }
        });


        return v;
    }

    public void setOnButtonClickListener(onButtonClickListener listener)
    {
        this.listener=listener;
    }

    private void init(View v) {
        logoutBtn =v.findViewById(R.id.logout_btn);

        nameTv =v.findViewById(R.id.userName_et);
        emailTv =v.findViewById(R.id.userEmail_et);
        phoneTv =v.findViewById(R.id.userPhone_et);
        genderTv =v.findViewById(R.id.userGender_et);
        cityTv =v.findViewById(R.id.userCity_et);
    }

    public interface onButtonClickListener{

        public void Clicked();
    }


}
