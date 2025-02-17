package com.example.astrology.loginSignupSeRelated;

import static android.view.PixelCopy.SUCCESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.astrology.Activities.acceptordeclinepage;
import com.example.astrology.Activities.bookingPage;
import com.example.astrology.R;
import com.example.astrology.models.APITask;
import com.example.astrology.models.IAPITaskCallBack;
import com.example.astrology.models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.Calendar;
import java.util.HashMap;

public class SignUp2 extends AppCompatActivity implements View.OnClickListener, IAPITaskCallBack {
    EditText dateofbirth,placeofbirth,birthtime,passs;
    private int mYear, mMonth, mDay, mHour, mMinute,hour,minutes;
    private String USER_ID = "4545"; // eg "4545"
    private String API_KEY = "ByVOIaODH57QRVi6CqswHXGlcpDvj7tZBRoorY";  // eg "hdkbcsjcn157618678habdkjbck"
    private String API_END_POINT = "https://pdf.astrologyapi.com/v1/";
    FirebaseUser user1;
    private Handler handler;
    String gender,date,time,link;
    FloatingActionButton signup;
    private FirebaseAuth mAuth;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        dateofbirth =findViewById(R.id.Birthdate);

        passs =findViewById(R.id.passsignup);
        birthtime =findViewById(R.id.birthtime);
        signup = findViewById(R.id.signuppage3);

        placeofbirth = findViewById(R.id.birthplace);
        mAuth = FirebaseAuth.getInstance();
        user1 = mAuth.getCurrentUser();
        dateofbirth.setOnClickListener(this);
        birthtime.setOnClickListener( this);
        signup.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                          preregisterUser();
                                      }
                                  }
        );

    }
    public void onClick(View v) {


        if (v == dateofbirth) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                            dateofbirth.setText(date);


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == birthtime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            time = hourOfDay + ":" + minute;

                            birthtime.setText(time);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

    }


    private void preregisterUser( ) {

        mAuth = FirebaseAuth.getInstance();
        String namespre,phonespre,emailspre,passwordspre,dateofbirthst,placeofbirthst,birthtimest;
        namespre = getIntent().getStringExtra("name").toString();
        phonespre =getIntent().getStringExtra("mobile").toString();
        emailspre =getIntent().getStringExtra("email").toString().trim();
        gender = getIntent().getStringExtra("gender");
        passwordspre = passs.getText().toString();
        dateofbirthst = dateofbirth.getText().toString();
        placeofbirthst = placeofbirth.getText().toString();
        birthtimest = birthtime.getText().toString();



        RequestBody formBody = new FormEncodingBuilder()


                .add("name" , namespre)
                .add("gender" , gender)
                .add("day" , String.valueOf(mDay))
                .add("month" , String.valueOf(mMonth))
                .add("year" , String.valueOf(mYear))
                .add("hour" , String.valueOf(mHour))

                .add("min" , String.valueOf(minutes))
                .add("lat" , "19.231")
                .add("lon" , "72.4242")
                .add("language" , "hi")
                .add("tzone" , "5.5")
                .add("place" , placeofbirthst)
                .add("chart_style" , "NORTH_INDIAN")
                .add("footer_link" , "astrologyapi.com")
                .add("logo_url" , String.valueOf(R.drawable.mainlogo))
                .add("company_name" ,"Rudraksha")
                .add("company_info" , "Your Company Info")
                .add("domain_url" , "https://www.astrologyapi.com")
                .add("company_email" , "mail@astrologyapi.com")
                .add("company_landline" , "+91- 221232 22")
                .add("company_mobile" , "+91 1212 1212 12")
                .build();

        // the first argument is the API name
        // for ex- planets or astro_details or general_house_report/sun
        // you can get the API names from https://www.vedicrishiastro.com/docs
        // NOTE: please make sure there is no / before the API name.
        // for example /astro_details will give an error
        executeAPI("basic_horoscope_pdf", formBody);








        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users");

        mAuth.createUserWithEmailAndPassword(emailspre,passwordspre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    userModel pr = new userModel(namespre,dateofbirthst,placeofbirthst,birthtimest,emailspre,phonespre,gender,link);

                    user.child(mAuth.getUid()).setValue(pr).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(SignUp2.this, "User Registered succesfully, Please Login", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUp2.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onSuccess(String response) {
        Log.i("Test Activity", "Response : " + response);
        Message message = Message.obtain();

        message.what = SUCCESS;
        message.obj = response.substring(26,111);

        switch (message.what){
            case SUCCESS:
                String res = (String) message.obj;
                link= res;
                break;
        }

    }


    public void onFailure(String error) {
        Log.e("Test Activity Error", "Response : " + error);
    }

    private void executeAPI(String apiName, RequestBody formData)
    {
        String url = API_END_POINT+apiName;
        APITask apiTask = new APITask(url, USER_ID, API_KEY, formData,  SignUp2.this);
        Thread thread = new Thread(apiTask);
        thread.start();
    }


}
