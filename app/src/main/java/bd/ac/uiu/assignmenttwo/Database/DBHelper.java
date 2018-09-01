package bd.ac.uiu.assignmenttwo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import bd.ac.uiu.assignmenttwo.Model.UserModel;

public class DBHelper extends SQLiteOpenHelper {
    // Database
    private static final String DATABASE_NAME="userinfodatabase";
    private static final String TABLE_NAME = "userinfo_table";
    private static int DB_VERSION = 1;
    // Database attribute
    // TABLE coloumn attribute

    //coloumn Name
    private static final String COL_ID ="ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_PHONE = "PHONE";
    private static final String COL_GENDER ="GENDER" ;
    private static final String COL_CITY ="CITY" ;

    //Query for database
    private static final String CREATE_TABLE=
            "CREATE TABLE "+TABLE_NAME+" ( "+COL_ID+" INTEGER, "+COL_NAME+" TEXT NOT NULL, "
            +COL_EMAIL+" TEXT NOT NULL, "
            +COL_PASSWORD+" TEXT NOT NULL, "
            +COL_PHONE+" TEXT NOT NULL, "
            +COL_GENDER+" TEXT NOT NULL, "
            +COL_CITY+" TEXT NOT NULL, PRIMARY KEY("+COL_ID+") )";

    private static final String SELECT_ALL =" ";


    private Context context ;
    private SQLiteDatabase mDatabase;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create a database table here
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //create a custom method to insert value in databse

    public boolean addUserInfoToDatabase(UserModel userModel)
    {
        //need permission to write in Database
        mDatabase= this.getWritableDatabase();

        //Need a ContentValues for insert values in Database
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_NAME, userModel.getName());
        contentValues.put(COL_EMAIL, userModel.getEmail());
        contentValues.put(COL_PASSWORD, userModel.getPassword());
        contentValues.put(COL_PHONE, userModel.getPhone());
        contentValues.put(COL_GENDER, userModel.getGender());
        contentValues.put(COL_CITY, userModel.getCity());

        //now insert contentValues in Database
        long insertResult = mDatabase.insert(TABLE_NAME,null,contentValues);

        if(insertResult == -1)
        {

            mDatabase.close(); //Database write permmission need to close always
            return false;
        }
        else {
            mDatabase.close();
            return true;
        }
    }

    public ArrayList<UserModel> getAllUserInfo()
    {
        ArrayList<UserModel> temp_userlist = new ArrayList<>();

        String query ="SELECT * FROM "+TABLE_NAME;
        mDatabase =this.getReadableDatabase();

        Cursor cursor= mDatabase.rawQuery(query,null,null);

        UserModel temp_usermodel=null;

        if(cursor.moveToFirst())
        {
            do{
                String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
                String password=cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
                String phone= cursor.getString(cursor.getColumnIndex(COL_PHONE));
                String gender= cursor.getString(cursor.getColumnIndex(COL_GENDER));
                String city =cursor.getString(cursor.getColumnIndex(COL_CITY));
                String id =cursor.getString(cursor.getColumnIndex(COL_ID));

                temp_usermodel=new UserModel(name,email,password,phone,gender,city,id);
                //Add to userList
                temp_userlist.add(temp_usermodel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        mDatabase.close();
        Log.d("getAlluserInfo",temp_userlist.toString());
        return temp_userlist;
    }

    public boolean CheckUserName(String userName)
    {
      mDatabase= this.getReadableDatabase();
      Cursor cursor= mDatabase.query(TABLE_NAME,null,"NAME=?",new String[]{userName},null,null,null);
      if (cursor.getCount()>0)
      {
          // not exists
          cursor.close();
          return false;
      }
      else{
          //exists
          cursor.close();
          return true;
      }
    }

    public boolean CheckUserEmail(String userEmail)
    {
        mDatabase= this.getReadableDatabase();
        Cursor cursor= mDatabase.query(TABLE_NAME,null,"EMAIL=?",new String[]{userEmail},null,null,null);
        if (cursor.getCount()>0)
        {
            // not exists
            cursor.close();
            return false;
        }
        else{
            //exists
            cursor.close();
            return true;
        }
    }

    public boolean checkLogin(String username, String userpassword )
    {
        mDatabase=this.getReadableDatabase();
        Cursor cursor= mDatabase.query(TABLE_NAME,null,"NAME=? and PASSWORD=?",new String[]{username,userpassword},null,null,null);
         if(cursor.getCount()>0)
         {
             cursor.moveToFirst();


             String id=cursor.getString(cursor.getColumnIndex(COL_ID));
             String Name=cursor.getString(cursor.getColumnIndex(COL_NAME));
             String Email=cursor.getString(cursor.getColumnIndex(COL_EMAIL));
             String Password=cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
             String Phone=cursor.getString(cursor.getColumnIndex(COL_PHONE));
             String Gender=cursor.getString(cursor.getColumnIndex(COL_GENDER));
             String City=cursor.getString(cursor.getColumnIndex(COL_CITY));

             SharedPreferences preferences = context.getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
             SharedPreferences.Editor editor = preferences.edit();
             editor.putString("userId", id);  //"userId" is String key
             editor.putString("userName", Name);
             editor.putString("userEmail", Email);
             editor.putString("userPassword", Password);
             editor.putString("userPhone", Phone);
             editor.putString("userGender", Gender);
             editor.putString("userCity", City);
             editor.apply();
             Log.i("DataBase :","userfound and saved in loginDetails preferences: ");

             cursor.close();
             return true;

         }
         else{
             cursor.close();
             return false;
         }
    }
}
