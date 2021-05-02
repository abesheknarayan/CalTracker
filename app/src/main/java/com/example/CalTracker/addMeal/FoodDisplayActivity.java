package com.example.CalTracker.addMeal;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.CalTracker.login.User;
import com.example.CalTracker.main.ReportActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.CalTracker.R;
import com.example.CalTracker.main.MainActivity;

public class FoodDisplayActivity extends AppCompatActivity {


    public static String uid;
    DatabaseReference databaseReference;
    public String foodname,quantity,category;
    public String calorie,carbohydrate,fat,protein;
    public ArrayList<UsersFood> usersFoodArrayList = new ArrayList<>();
    public ArrayList<CustomFood> customFoodArrayList = new ArrayList<>();

    private LinearLayout ll_quit;
    ProgressBar progressBar;
    private TextView calorieIntake,calorieTotal,calorieLeft;

    public String mCalorie,mProtein,mCarbohydrate,mFat;
    private TextView custom_get_food_name,custom_get_food_calorie,custom_get_food_protein,
            custom_get_food_carbohydrate,custom_get_food_fat;

    public FirebaseStorage storage;
    public StorageReference storageReference;
    ImageView food_display_photo;

    public String height,weight,age,gender,bmi,bmr_string;

    double bmr;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        ll_quit = (LinearLayout) findViewById(R.id.ll_display_food_cancel);
        ll_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDisplayActivity.this, MainActivity.class);
                if (intent != null) {
                    FoodDisplayActivity.this.startActivity(intent);
                }
            }
        });

        calorieIntake = (TextView) findViewById(R.id.meal_display_already_intake);
        calorieTotal = (TextView) findViewById(R.id.meal_display_total_intake);
        calorieLeft = (TextView) findViewById(R.id.meal_display_left_intake);
        progressBar = (ProgressBar) findViewById(R.id.calorie_progress_bar);
        getIntakefromDatabaseandDisplay();

        custom_get_food_name = (TextView) findViewById(R.id.custom_get_food_name);
        custom_get_food_calorie = (TextView) findViewById(R.id.custom_get_food_calorie);
        custom_get_food_protein = (TextView) findViewById(R.id.custom_get_food_protein);
        custom_get_food_carbohydrate = (TextView) findViewById(R.id.custom_get_food_carbohydrate);
        custom_get_food_fat = (TextView) findViewById(R.id.custom_get_food_fat);

        getPassItent();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        food_display_photo = (ImageView) findViewById(R.id.food_display_photo);

        get_Weight_BMI_fromDatabase();
    }


    public void get_BMR()
    {
        Log.d("height",height);
        Log.d("weight",weight);
        Log.d("age",age);
        Log.d("gender",gender);
        Double now_height,now_weight,now_age;
        now_height = Double.parseDouble(height);
        now_weight = Double.parseDouble(weight);
        now_age = Double.parseDouble(age);
        DecimalFormat df = new DecimalFormat("0.00");
        // used Miffin-St Jeor Equation
        if(gender.equals("Male"))
        {
            // male
            bmr = 10*now_weight + (6.25)*(now_height) - 5*now_age + 5;
        }
        else{
            // female
            bmr = 10*now_weight + (6.25)*(now_height) - 5*now_age - 161;
        }
        bmr_string = Double.toString(bmr);
        Log.d("get_BMR: ",bmr_string);



    }


     public void get_Weight_BMI_fromDatabase() {
            //get userID
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d("BMI_fromDatabase: ", "here");

            //Get the weight and BMI of the current user from the database
            databaseReference.child("Users").child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);

                            if (user != null) {
                                for (DataSnapshot d : snapshot.getChildren()) {

                                    String userInfo_Key = d.getKey();
                                    if (!userInfo_Key.equals("userID") && !userInfo_Key.equals("username") && !userInfo_Key.equals("email") && !userInfo_Key.equals("password") && !userInfo_Key.equals("confirm_password") && !userInfo_Key.equals("notFirstTime") && !(d.getChildrenCount() == 3)) {

                                        databaseReference.child("Users").child(uid)
                                                .child(userInfo_Key).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot d : dataSnapshot.getChildren()) {

                                                    String d_Key = d.getKey();
                                                    if (d_Key.equals("weight")) {
                                                        weight = d.getValue().toString();
                                                        Log.d("weight", weight);

                                                    } else if (d_Key.equals("height")) {
                                                        height = d.getValue().toString();
                                                        Log.d("height", height);

                                                    } else if (d_Key.equals("age")) {
                                                        age = d.getValue().toString();
                                                        Log.d("age", age);

                                                    } else if (d_Key.equals("gender")) {
                                                        gender = d.getValue().toString();
                                                        Log.d("gender", gender);
                                                    } else if (d_Key.equals("bmi")) {
                                                        bmi = d.getValue().toString();
                                                        Log.d("bmi", bmi);

                                                        Double d_bmi = Double.parseDouble(bmi);
                                                        DecimalFormat df = new DecimalFormat("0.00");
                                                        String str_bmi = df.format(d_bmi);

                                                    }
                                                }
                                                get_BMR();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }

                                        });
                                    }
                                }

                            } else {
                                Toast.makeText(FoodDisplayActivity.this, "displayHeight_BMI ERROR!!!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }



    public void getQuantityCategory(final MyCallBack myCallBack){
        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {

                            final UsersFood usersFood = new UsersFood();
                            for (DataSnapshot dd : d.getChildren()) {
                                String dd_Key = dd.getKey();
                                String dd_Value = dd.getValue().toString();


                                if (dd_Key.equals("foodname")) {
                                    foodname = dd_Value;
                                    usersFood.setFoodname(foodname);

                                    //get quantity &category
                                    Query q1 = databaseReference.child("Users").child(uid)
                                            .orderByChild("foodname")
                                            .equalTo(foodname);

                                    q1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    quantity = dataSnapshot1.child("quantity").getValue().toString();
                                                    category = dataSnapshot1.child("category").getValue().toString();

                                                    usersFood.setQuantity(quantity);
                                                    usersFood.setCategory(category);
                                                    usersFood.incrementFoodCount();
                                                    usersFoodArrayList.add(usersFood);
                                                    myCallBack.onCallback(usersFoodArrayList);
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

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

    public void getIntakefromDatabaseandDisplay(){

        //1. select * from Users
        //2. select * from Food where foodname = "where can get it from User"

        //1. select * from Users
        //get userID
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {

                            final CustomFood customFood = new CustomFood();
                            for (DataSnapshot dd : d.getChildren()) {
                                String dd_Key = dd.getKey();
                                String dd_Value = dd.getValue().toString();

                                //all meal
                                if (dd_Key.equals("foodname")) {
                                    foodname = dd_Value;
                                    customFood.setFoodname(foodname);

                                    //2. select * from Food where foodname = "where can get it from User"
                                    Query query = databaseReference.child("Food")
                                            .orderByChild("foodname")
                                            .equalTo(foodname);

                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.exists()){
                                                for(DataSnapshot data:dataSnapshot.getChildren()){
                                                    String name = data.child("foodname").getValue().toString();
                                                    calorie = data.child("calorie").getValue().toString();
                                                    carbohydrate = data.child("carbs").getValue().toString();
                                                    fat = data.child("fat").getValue().toString();
                                                    protein = data.child("protein").getValue().toString();

                                                    customFood.setFoodname(name);
                                                    customFood.setCalorie(calorie);
                                                    customFood.setCarbs(carbohydrate);
                                                    customFood.setFat(fat);
                                                    customFood.setProtein(protein);

                                                    customFood.incrementFoodCount();

                                                    customFoodArrayList.add(customFood);
                                                }
                                            }
                                            getQuantityCategory(new MyCallBack() {
                                                @Override
                                                public void onCallback(ArrayList<UsersFood> usersFoodArrayList1) {

                                                    double totQuan,totCalorie = 0;
                                                    double totQuan_b,totCalorie_b=0;
                                                    double totQuan_l,totCalorie_l=0;
                                                    double totQuan_d,totCalorie_d=0;
                                                    double totQuan_o,totCalorie_o=0;

                                                    double allCalorieCount = 0;
                                                    double allCalorieCount_b = 0;
                                                    double allCalorieCount_l = 0;
                                                    double allCalorieCount_d = 0;
                                                    double allCalorieCount_o = 0;


                                                    if(usersFoodArrayList1.size() == customFoodArrayList.size()){
                                                        //Calculate the Calorie of all meal
                                                        for(int i=0; i < usersFoodArrayList1.size();i++){
                                                            String tmp_foodname = usersFoodArrayList1.get(i).getFoodname();
                                                            totQuan = Double.parseDouble(usersFoodArrayList1.get(i).getQuantity());

                                                            for(int j =0; j < customFoodArrayList.size();j++){
                                                                if(customFoodArrayList.get(j).getFoodname().equals(tmp_foodname)){
                                                                    totCalorie = Double.parseDouble(customFoodArrayList.get(j).getCalorie());
                                                                    totCalorie = totCalorie * totQuan;

                                                                }
                                                            }
                                                            allCalorieCount = allCalorieCount + totCalorie;
                                                        }
                                                        //set total intake
                                                        //Already ingested
                                                        calorieIntake.setText(Double.toString(allCalorieCount));

                                                        calorieTotal.setText( Double.toString(bmr) + "Cal");

                                                        //intake left
                                                        Double d_calorieLeft = bmr - allCalorieCount;
                                                        calorieLeft.setText(Double.toString(d_calorieLeft)+" cal");

                                                        //progressBar
                                                        int progress = (int)allCalorieCount/25;
                                                        progressBar.setProgress(progress);


                                                        //Log breakfast
                                                        for(int i=0; i < usersFoodArrayList1.size();i++){
                                                            String breakfast_foodname = usersFoodArrayList1.get(i).getFoodname();
                                                            String breakfast_category = usersFoodArrayList1.get(i).getCategory();
                                                            totQuan_b = Double.parseDouble(usersFoodArrayList1.get(i).getQuantity());

                                                            if(breakfast_category.equals("Breakfast")){

                                                                for(int j =0; j < customFoodArrayList.size();j++){
                                                                    if(customFoodArrayList.get(j).getFoodname().equals(breakfast_foodname)){
                                                                        totCalorie_b = Double.parseDouble(customFoodArrayList.get(j).getCalorie());
                                                                        totCalorie_b = totCalorie_b * totQuan_b;

                                                                    }
                                                                }
                                                                allCalorieCount_b = allCalorieCount_b + totCalorie_b;
                                                            }

                                                        }
                                                        //Calculate lunch meal Calorie
                                                        for(int i=0; i < usersFoodArrayList1.size();i++){
                                                            String lunch_foodname = usersFoodArrayList1.get(i).getFoodname();
                                                            String lunch_category = usersFoodArrayList1.get(i).getCategory();

                                                            totQuan_l = Double.parseDouble(usersFoodArrayList1.get(i).getQuantity());

                                                            if(lunch_category.equals("Lunch")){

                                                                for(int j =0; j < customFoodArrayList.size();j++){
                                                                    if(customFoodArrayList.get(j).getFoodname().equals(lunch_foodname)){
                                                                        totCalorie_l = Double.parseDouble(customFoodArrayList.get(j).getCalorie());
                                                                        totCalorie_l = totCalorie_l * totQuan_l;

                                                                    }
                                                                }
                                                                allCalorieCount_l = allCalorieCount_l + totCalorie_l;
                                                            }

                                                        }
                                                        //Calculate dinner Calorie
                                                        for(int i=0; i < usersFoodArrayList1.size();i++){
                                                            String dinner_foodname = usersFoodArrayList1.get(i).getFoodname();
                                                            String dinner_category = usersFoodArrayList1.get(i).getCategory();

                                                            totQuan_d = Double.parseDouble(usersFoodArrayList1.get(i).getQuantity());

                                                            if(dinner_category.equals("Dinner")){

                                                                for(int j =0; j < customFoodArrayList.size();j++){
                                                                    if(customFoodArrayList.get(j).getFoodname().equals(dinner_foodname)){
                                                                        totCalorie_d = Double.parseDouble(customFoodArrayList.get(j).getCalorie());
                                                                        totCalorie_d = totCalorie_d * totQuan_d;

                                                                    }
                                                                }
                                                                allCalorieCount_d = allCalorieCount_d + totCalorie_d;
                                                            }

                                                        }
                                                        //Calculate other meals Calorie
                                                        for(int i=0; i < usersFoodArrayList1.size();i++){
                                                            String other_foodname = usersFoodArrayList1.get(i).getFoodname();
                                                            String other_category = usersFoodArrayList1.get(i).getCategory();

                                                            totQuan_o = Double.parseDouble(usersFoodArrayList1.get(i).getQuantity());

                                                            if(other_category.equals("Other")){

                                                                for(int j =0; j < customFoodArrayList.size();j++){
                                                                    if(customFoodArrayList.get(j).getFoodname().equals(other_foodname)){
                                                                        totCalorie_o = Double.parseDouble(customFoodArrayList.get(j).getCalorie());
                                                                        totCalorie_o = totCalorie_o * totQuan_o;

                                                                    }
                                                                }
                                                                allCalorieCount_o = allCalorieCount_o + totCalorie_o;
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }//=========[end of] if (dd_Key.equals("foodname")) {


                            }//========[end of]for (DataSnapshot dd : d.getChildren())
                        }//=========[end of]for (DataSnapshot d : snapshot.getChildren())
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void getPassItent(){
        //retrieve data
        Intent intent = getIntent();

        //manually input
        //get bundle from intent
        Bundle data = intent.getBundleExtra("data");
        if (data != null) {

            String addFoodName = data.getString("foodname");
            custom_get_food_name.setText(addFoodName);
            System.out.println("---------------------------------------------------"+addFoodName);
            getAddFoodName_FoodInfo(addFoodName);

        }

        //image confirmed
        //get bundle from intent
        Bundle data2 = intent.getBundleExtra("data_image");
        if (data2 != null) {

            String addFoodName2 = data2.getString("foodname2");
            custom_get_food_name.setText(addFoodName2);
            System.out.println("---------------------------------------------------"+addFoodName2);
            getAddFoodName_FoodInfo(addFoodName2);
        }
    }

    public void getAddFoodName_FoodInfo(final String oneFoodName){
        Query q_foodInfo = databaseReference.child("Food")
                .orderByChild("foodname")
                .equalTo(oneFoodName);

        q_foodInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data: snapshot.getChildren()){
                        mCalorie = data.child("calorie").getValue().toString();
                        mProtein = data.child("protein").getValue().toString();
                        mCarbohydrate = data.child("carbs").getValue().toString();
                        mFat = data.child("fat").getValue().toString();

                        custom_get_food_calorie.setText(mCalorie);
                        custom_get_food_protein.setText(mProtein);
                        custom_get_food_carbohydrate.setText(mCarbohydrate);
                        custom_get_food_fat.setText(mFat);
                    }
                }
                getAddFoodImage(oneFoodName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getAddFoodImage(String oneFoodName){
        StorageReference imgRef = storageReference.child("FoodImage/"+oneFoodName+".jpg");
        final long ONE_MEGABYTE = 1024*1024;

        imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                food_display_photo.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
