package co.uk.nursetoday.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import co.uk.nursetoday.Model.User_Agency;
import co.uk.nursetoday.Model.User_Nurse;
import co.uk.nursetoday.R;

public class Register_Agency extends AppCompatActivity {


    EditText ag_name, ag_fname, ag_lname, ag_email, ag_pw;
    Button btn_agReg;
    TextView error;
    DatabaseReference dbref, agencies;
    FirebaseAuth fbAuth;
    Query dbquery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agency);

        //Link all the Java Views to their XML counterparts
        ag_name = findViewById(R.id.et_ag_title_register);
        ag_fname = findViewById(R.id.et_ag_fname_register);
        ag_lname = findViewById(R.id.et_ag_lname_register);
        ag_email = findViewById(R.id.et_ag_email_register);
        ag_pw = findViewById(R.id.et_ag_pass_register);
        btn_agReg = findViewById(R.id.btn_ag_register);
        error = findViewById(R.id.tv_ag_error_register);

        //initialize our FB auth
        fbAuth = FirebaseAuth.getInstance();

        //link dbref to user node in FB
        dbref = FirebaseDatabase.getInstance().getReference("users");
        //instantiate the child of users: Agencies
        agencies = dbref.child("agencies");


        btn_agReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //first we check if there is any field empty
                if (ag_name.getText().toString().isEmpty() || ag_fname.getText().toString().isEmpty() ||
                        ag_lname.getText().toString().isEmpty() || ag_email.getText().toString().isEmpty() ||
                        ag_pw.getText().toString().isEmpty()) {
                    error.setText("All fields are required!");
                    error.setVisibility(View.VISIBLE);
                } else {
                    //we query the database to get us all entries with the same Agency Title as the one entered
                    dbquery = FirebaseDatabase.getInstance().getReference("users").child("agencies").orderByChild("username")
                            .equalTo(ag_name.getText().toString());
                    dbquery.addListenerForSingleValueEvent(listener);
                }
            }


            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot dss : dataSnapshot.getChildren()) { // if the listener finds a user with the same ID as the one typed, error message is shown
                            error.setText("This username is already in use");
                            error.setVisibility(View.VISIBLE);
                        }
                    }// if ID is unique the user is created on the Firebase Authentication
                    else {
                        fbAuth.createUserWithEmailAndPassword(ag_email.getText().toString(), ag_pw.getText().toString()).addOnSuccessListener(authResult -> {
                            //logging in user in the background to store his Name and ID on the Firebase Database
                            fbAuth.signInWithEmailAndPassword(ag_email.getText().toString(), ag_pw.getText().toString()).addOnSuccessListener(authResult1 -> {
                                //dbref = FirebaseDatabase.getInstance().getReference("users");
                                //Storing user's details in Realtime database with the UID from Auth as Primary Key
                                String id = agencies.push().getKey();
                                User_Agency user = new User_Agency(ag_name.getText().toString(), ag_fname.getText().toString(), ag_lname.getText().toString(),
                                        ag_email.getText().toString(), ag_pw.getText().toString());
                                //agencies.child(id).setValue(user);
                                agencies.child(fbAuth.getUid()).setValue(user);
                                fbAuth.signOut();
                                //directing to splash page
                                Intent i = new Intent(Register_Agency.this, Splash.class);
                                i.putExtra("name", user.getFirst_name());
                                startActivity(i);
                            });
                        }).addOnFailureListener(e -> {
                            error.setText(e.toString());
                            error.setVisibility(View.VISIBLE);
                        });
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };

        });
    }
}