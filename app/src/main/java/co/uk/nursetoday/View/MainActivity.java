package co.uk.nursetoday.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.uk.nursetoday.Application.Session;
import co.uk.nursetoday.Model.User_Agency;
import co.uk.nursetoday.Model.User_Nurse;
import co.uk.nursetoday.Model.Users;
import co.uk.nursetoday.R;


public class MainActivity extends AppCompatActivity {

    private EditText userEmail, password;
    private Button btnNurseSignIn, btnAgencySignIn;
    private TextView completeFields, resetpw, signup;
    private FirebaseAuth fbAuth;
    private Query dbref;
    private Intent i;
    private static Context appContext;

   // ArrayList<Users> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//link our Java widgets to the XML views
        userEmail = findViewById(R.id.et_userEmail);
        password = findViewById(R.id.et_password);
        btnAgencySignIn = findViewById(R.id.btn_agency_signin);
        btnNurseSignIn = findViewById(R.id.btn_nurse_signin);
        completeFields = findViewById(R.id.tv_complete_field_validation);
        resetpw = findViewById(R.id.tv_resetpw);
        signup = findViewById(R.id.tv_signup);
        fbAuth = FirebaseAuth.getInstance();

        appContext = getApplicationContext();
        if(Session.LiveSession.user!=null){//Session.LiveSession.user!=null){
            i=new Intent(MainActivity.this,Dashboard.class);
            startActivity(i);
        }

    }


    public static Context getAppContext(){return appContext;}

    @Override
    protected void onStart(){
        super.onStart();

        btnAgencySignIn.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){

                //first, check for empty fields validation
                if(userEmail.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"All fields are required!",Toast.LENGTH_LONG).show();
                    completeFields.setVisibility(View.VISIBLE);
                }

                //if all fields are completed,will proceed with the user sign
                else{
                    fbAuth.signInWithEmailAndPassword(userEmail.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            //user records will be captured and compared with database
                            ValueEventListener listener=new ValueEventListener(){
                                @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                                    for(DataSnapshot dss:dataSnapshot.getChildren())
                                    {
                                        if(fbAuth.getCurrentUser().getUid().matches(dss.getKey())){
                                            Session.LiveSession.user_agency = (User_Agency) dss.getValue(User_Agency.class);

                                        }
                                    }
                                    if(Session.LiveSession.user_agency!=null)//Session.LiveSession.user!=null)
                                    {
                                        Toast.makeText(MainActivity.this,Session.LiveSession.user_agency.getFirst_name()+
                                                "Loggedin",Toast.LENGTH_LONG).show();

                                        i=new Intent(MainActivity.this,Dashboard.class);
                                        startActivity(i);

                                //if the user tries to sign in with the wrong type of account
                                    }else{
                                        completeFields.setText("Wrong type of account. Are you registered as a nurse or agency?");
                                        completeFields.setVisibility(View.VISIBLE);
                                        fbAuth.signOut();
                                    }
                                }

                                @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError){

                                }
                            };
    //here the entered ID and the ID in the database will be compared using the "listener"
                            dbref = FirebaseDatabase.getInstance().getReference("users").child("agencies").orderByChild("email").equalTo(userEmail.getText().toString());
                            dbref.addListenerForSingleValueEvent(listener);
                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        @Override
                                public void onFailure(@NonNull Exception e){
//TOCODEdependingontheloginproblemfornowIwillleaveatoastofnonexistantuser
                            Toast.makeText(MainActivity.this,"Wrong Credentials.\nForgot your password?",Toast.LENGTH_LONG).show();
                        }
                    });
                }}
        });




       btnNurseSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //first, check for empty fields validation
                if(userEmail.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"All fields are required!",Toast.LENGTH_LONG).show();
                    completeFields.setVisibility(View.VISIBLE);
                }

                //if all fields are completed,will proceed with the user sign
                else{
                    fbAuth.signInWithEmailAndPassword(userEmail.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            //user records will be captured and compared with database
                            ValueEventListener listener=new ValueEventListener(){
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                                    for(DataSnapshot dss:dataSnapshot.getChildren())
                                    {
                                        if(fbAuth.getCurrentUser().getUid().matches(dss.getKey())){
                                            Session.LiveSession.user_nurse = (User_Nurse) dss.getValue(User_Nurse.class);
                                            //Session.LiveSession.user = dss.getValue(Users.class);
                                        }
                                    }
                                    if(Session.LiveSession.user_nurse!=null)//Session.LiveSession.user!=null)
                                    {
                                        Toast.makeText(MainActivity.this, "Welcome " + Session.LiveSession.user_nurse.getFirst_name()
                                              ,Toast.LENGTH_LONG).show();

                                        i=new Intent(MainActivity.this,Dashboard.class);
                                        startActivity(i);

                                        //if the user tries to sign in with the wrong type of account
                                    }else{
                                        completeFields.setText("Wrong type of account. Are you registered as a nurse or agency?");
                                        completeFields.setVisibility(View.VISIBLE);
                                        fbAuth.signOut();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError){

                                }
                            };
                            //here the entered ID and the ID in the database will be compared using the "listener"
                            dbref = FirebaseDatabase.getInstance().getReference("users").child("nurses").orderByChild("email").equalTo(userEmail.getText().toString());
                            dbref.addListenerForSingleValueEvent(listener);
                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e){
//TOCODEdependingontheloginproblemfornowIwillleaveatoastofnonexistantuser
                            Toast.makeText(MainActivity.this,"Wrong Credentials.\nForgot your password?",Toast.LENGTH_LONG).show();
                        }
                    });
                }}
        });


        signup.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                i=new Intent(MainActivity.this,User_Type_Reg.class);
                startActivity(i);
            }
        });

        resetpw.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                i=new Intent(MainActivity.this,ResetPw.class);
                startActivity(i);
            }
        });


    }



/*
btnAgReg.setOnClickListener(newView.OnClickListener(){
@Override
publicvoidonClick(Viewv){

Stringag_id=ref.push().getKey();//pushmethodtestablishaprimarykeyandhgetkeywillreturnreturnthestringaskey,whichwillbesavedinStringid
Agency_Usersagency_users=newAgency_Users(et_userEmail.getText().toString(),et_password.getText().toString());//createauserobjectwhichwillbeaddedinthedatabase
ref.child(ag_id).setValue(agency_users);//savestherecord
}
});
*/
}