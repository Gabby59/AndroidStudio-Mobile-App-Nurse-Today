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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import co.uk.nursetoday.Model.User_Agency;
import co.uk.nursetoday.Model.User_Nurse;
import co.uk.nursetoday.R;

public class Register_Nurse extends AppCompatActivity {

     private EditText username, nurse_fname, nurse_lname, nurse_email, nurse_pw;
     private Button btn_nurseReg;
     private TextView error;

     DatabaseReference dbref,nurses; // create objects to communicate with FB DB, either write or read
     FirebaseAuth fbAuth;
     Query dbquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nurse);

        username = findViewById(R.id.et_nurse_username_register);
        nurse_fname = findViewById(R.id.et_nurse_fname_register);
        nurse_lname = findViewById(R.id.et_nurse_lname_register);
        nurse_email = findViewById(R.id.et_nurse_email_register);
        nurse_pw = findViewById(R.id.et_nurse_pass_register);
        error = findViewById(R.id.tv_nurse_error_register);
        btn_nurseReg = findViewById(R.id.btn_nurseReg);

        //link dbref to user node in FB
        dbref = FirebaseDatabase.getInstance().getReference("users");
        //instantiate the child of users: nurses
        nurses = dbref.child("nurses");
        //initialize our FB auth
        fbAuth = FirebaseAuth.getInstance();


/*
       btn_nurseReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = nurses.push().getKey();
                User_Nurse user = new User_Nurse(username.getText().toString(), nurse_fname.getText().toString(),
                        nurse_lname.getText().toString(), nurse_email.getText().toString(),
                        nurse_pw.getText().toString());

                // create a child node of the existing node it is pointing to (user_nurse)
                nurses.child(id).setValue(user);

                Intent i = new Intent(Register_Nurse.this, Splash.class);
                i.putExtra("name",user.getFirst_name()); //i.putExtra("name", u.getFname());
                i.putExtra("surname",user.getLast_name()); // i.putExtra("surname", u.getSname());
                startActivity(i);
            }
        });
*/
        btn_nurseReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first we check if there is any field empty
                if (username.getText().toString().isEmpty() || nurse_fname.getText().toString().isEmpty() ||
                        nurse_lname.getText().toString().isEmpty() || nurse_email.getText().toString().isEmpty() ||
                        nurse_pw.getText().toString().isEmpty()) {
                    error.setText("All fields are required!");
                    error.setVisibility(View.VISIBLE);
                } else {
                    //we query the database to get us all entries with the same Agency Title as the one entered
                    dbquery = FirebaseDatabase.getInstance().getReference("users").child("nurses").orderByChild("username")
                            .equalTo(username.getText().toString());
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
                        fbAuth.createUserWithEmailAndPassword(nurse_email.getText().toString(), nurse_pw.getText().toString()).addOnSuccessListener(authResult -> {
                            //logging in user in the background to store his Name and ID on the Firebase Database
                            fbAuth.signInWithEmailAndPassword(nurse_email.getText().toString(), nurse_pw.getText().toString()).addOnSuccessListener(authResult1 -> {

                                //dbref = FirebaseDatabase.getInstance().getReference("users");
                                //Storing user's details in Realtime database with the UID from Auth as Primary Key
                                String id = nurses.push().getKey();
                                User_Nurse user = new User_Nurse(username.getText().toString(), nurse_fname.getText().toString(), nurse_lname.getText().toString(),
                                        nurse_email.getText().toString(), nurse_pw.getText().toString());
                                //agencies.child(id).setValue(user);
                                nurses.child(fbAuth.getUid()).setValue(user);
                                fbAuth.signOut();
                                //directing to splash page
                                Intent i = new Intent(Register_Nurse.this, Splash.class);
                                i.putExtra("name", user.getUsername());
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
