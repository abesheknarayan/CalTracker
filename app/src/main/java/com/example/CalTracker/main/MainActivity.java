package com.example.CalTracker.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.CalTracker.R;
import com.example.CalTracker.addMeal.*;
import com.example.CalTracker.login.*;
import com.example.CalTracker.news.*;
import com.example.CalTracker.userSetting.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static String uid;

    public TextView textView_bmi,textView_weight,textView_name;
    public ImageView imageView_userImage;
    public String weight,gender,bmi,username;//get the height and weight from the database

    private static final int REQUEST_TAKE_PHOTO = 101;
    MarshmallowPermission marshmallowPermission = new MarshmallowPermission(this);
    public DatabaseReference databaseReference;
    public FirebaseStorage storage;
    public StorageReference storageReference;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        //Get all news data packaged with list
        ArrayList<NewsBean> allNews = NewsUtils.getAllNews(mContext);
        ListView lv_news = (ListView) findViewById(R.id.lv_news);
        //Create an adapter to listview
        NewsAdapter newsAdapter = new NewsAdapter(mContext, allNews);
        lv_news.setAdapter(newsAdapter);
        //Set the click event of the listview item
        lv_news.setOnItemClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        textView_name=(TextView) findViewById(R.id.main_display_user_name);
        textView_weight = (TextView) findViewById(R.id.main_display_user_weight);
        textView_bmi = (TextView) findViewById(R.id.main_display_user_BMI);

        getUsername_fromDatabse();
        getWeight_BMI_fromDatabase();

        imageView_userImage = (ImageView) findViewById(R.id.user_image);
        getUserImage_fromDatabase();


        //define button
        final Button mtReportBtn=findViewById(R.id.btn_main_to_report);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        mtReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                if (intent != null) {
                    MainActivity.this.startActivity(intent);
                }
            }
        });
        bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(0).getItemId());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_add:
                        userChoice();
                        break;
                    case R.id.navigation_user:
                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    // here


//    @Override
//    public void onBackPressed() {
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        NewsBean bean = (NewsBean) parent.getItemAtPosition(position);

        String url = bean.news_url;

        //open browser
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }

    public void userChoice(){
        onManualClick();
    }

    public void onManualClick(){
        Intent intent = new Intent(MainActivity.this, ManuallyInputActivity.class);
        if (intent != null) {
            startActivity(intent);
        }
    }

    /*
    Take Photo action
     */

    


    public void getUsername_fromDatabse(){
        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Get the username of the current user from the database
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){

                            for(DataSnapshot d: snapshot.getChildren()){

                                String userInfo_Key = d.getKey();
                                if(userInfo_Key.equals("username")) {
                                    username = d.getValue().toString();
                                    textView_name.setText(username);
                                }
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"displayUsername ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void getWeight_BMI_fromDatabase(){
        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Get the weight and height of the current user from the database
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){

                            for(DataSnapshot d: snapshot.getChildren()){

                                String userInfo_Key = d.getKey();
                                if(!userInfo_Key.equals("userID") && !userInfo_Key.equals("username") && !userInfo_Key.equals("email") && !userInfo_Key.equals("password")&& !userInfo_Key.equals("confirm_password")&& !userInfo_Key.equals("security")) {

                                    databaseReference.child("Users").child(uid)
                                            .child(userInfo_Key).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot d: dataSnapshot.getChildren()) {

                                                String d_Key = d.getKey();
                                                if(d_Key.equals("weight")){
                                                    weight = d.getValue().toString();

                                                    Double d_weight = Double.parseDouble(weight);
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    String str_weight = df.format(d_weight);
                                                    textView_weight.setText(str_weight);

                                                }

                                                if(d_Key.equals("bmi")){
                                                    bmi = d.getValue().toString();

                                                    Double d_bmi = Double.parseDouble(bmi);
                                                    DecimalFormat df = new DecimalFormat(("0.00"));
                                                    String str_bmi = df.format(d_bmi);
                                                    textView_bmi.setText(str_bmi);

                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"displayHeight_BMI ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    public void getUserImage_fromDatabase(){


        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //Get the gender of the current user from the database
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        if(user!=null){

                            for(DataSnapshot d: snapshot.getChildren()){

                                String userInfo_Key = d.getKey();
                                if(!userInfo_Key.equals("userID") && !userInfo_Key.equals("username") && !userInfo_Key.equals("email") && !userInfo_Key.equals("password")&& !userInfo_Key.equals("confirm_password")&& !userInfo_Key.equals("security")) {

                                    databaseReference.child("Users").child(uid)
                                            .child(userInfo_Key).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot d: dataSnapshot.getChildren()) {

                                                String d_Key = d.getKey();
                                                if(d_Key.equals("gender")){
                                                    gender = d.getValue().toString();

                                                    StorageReference femaleRef = storageReference.child("UserImage/icon_female.png");
                                                    StorageReference maleRef = storageReference.child("UserImage/icon_male.png");


                                                    //set the Image of the current user by his/her gender
                                                    final long ONE_MEGABYTE = 1024 * 1024;
                                                    if(gender.equals("Male")){

                                                        maleRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                            @Override
                                                            public void onSuccess(byte[] bytes) {
                                                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                imageView_userImage.setImageBitmap(bmp);

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception exception) {
                                                                Toast.makeText(getApplicationContext(), "Loading UserImage Male ERROR!!", Toast.LENGTH_LONG).show();
                                                            }
                                                        });

                                                    }
                                                    else{//Female
                                                        femaleRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                            @Override
                                                            public void onSuccess(byte[] bytes) {
                                                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                imageView_userImage.setImageBitmap(bmp);

                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception exception) {
                                                                Toast.makeText(getApplicationContext(), "Loading UserImage Female ERROR!!", Toast.LENGTH_LONG).show();
                                                            }
                                                        });

                                                    }

                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }else{
                            Toast.makeText(MainActivity.this,"displayHeight_BMI ERROR!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}