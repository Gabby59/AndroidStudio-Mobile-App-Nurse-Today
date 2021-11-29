package co.uk.nursetoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.uk.nursetoday.Application.Session;
import co.uk.nursetoday.Model.Topic;

public class Forum_New_Topic extends AppCompatActivity {

    EditText title, details;
    Button publish;
    TextView error;
    DatabaseReference dbref;
    Topic topic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum__new__topic);

        title = findViewById(R.id.et_title_topic);
        details = findViewById(R.id.et_description_topic);
        publish = findViewById(R.id.btn_publish_topic);
        error = findViewById(R.id.tv_error_topic);
        dbref = FirebaseDatabase.getInstance().getReference().child("Forum_Topics");


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate all fields are completed
                if((title.getText().toString().isEmpty()) ||
                        details.getText().toString().isEmpty()){
                    error.setVisibility(View.VISIBLE);
                } else{ //store topic
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String time = sdf.format(new Date());
                    topic = new Topic("Test Topic User",
                            title.getText().toString(), details.getText().toString(), time, 0);
                    dbref.push().setValue(topic);


                    Toast.makeText(Forum_New_Topic.this, "New Topic Published", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Forum_New_Topic.this, Forum_List.class);
                    startActivity(i);
                }


            }
        });



    }
}