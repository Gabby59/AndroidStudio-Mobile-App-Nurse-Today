package co.uk.nursetoday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import co.uk.nursetoday.Model.Forum_Thread;
import co.uk.nursetoday.Model.Topic;

public class Forum_AddCom extends AppCompatActivity {

    EditText comment;
    Button post;
    TextView error;
    DatabaseReference dbref, topics;
    String topicKey;
    private Forum_Thread thread;
    private Topic topic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum__add_com);


        Intent i = getIntent();

        topicKey = i.getStringExtra("KEY");
        topic = i.getParcelableExtra("Topic");

        // This section is the set up and size of the pop up "window"
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.6));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        //Until here

        comment = findViewById(R.id.et_comment_addCom);
        post = findViewById(R.id.btn_post_addCom);
        dbref = FirebaseDatabase.getInstance().getReference().child("Forum_Threads");
        topics = FirebaseDatabase.getInstance().getReference().child("Forum_Topics");
        error = findViewById(R.id.tv_validation_addCom);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment.getText().toString().isEmpty()) {
                    error.setVisibility(View.VISIBLE);
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm", Locale.getDefault());
                    String time = sdf.format(new Date());
                   // thread = new Thread(topicKey, Session.LiveSession.user.getStuID(), time, comment.getText().toString());
                    thread = new Forum_Thread(topicKey, "TestAddCom User", time, comment.getText().toString());
                    dbref.push().setValue(thread);
                    int threads = topic.getThreads();
                    topic.setThreads(threads+1);
                    topics.child(topicKey).setValue(topic);
                    Toast.makeText(Forum_AddCom.this, "Comment Added", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Forum_AddCom.this, Forum_Topic_Details.class);
                    i.putExtra("Topic", topic);
                    i.putExtra("KEY", topicKey);
                    startActivity(i);
                }
            }
        });

    }
}