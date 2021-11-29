package co.uk.nursetoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.uk.nursetoday.Adapters.TopicAdapter;
import co.uk.nursetoday.Model.Forum_Thread;
import co.uk.nursetoday.Model.Topic;
import co.uk.nursetoday.View.Dashboard;

public class Forum_Topic_Details extends AppCompatActivity {

    TextView author, title, date, details;
    ImageView back;
    Topic topic;
    String topicKey;
    Button addCom;
    RecyclerView rv;
    RecyclerView.LayoutManager manager;
    DatabaseReference dbref;
    ArrayList<Forum_Thread> threads = new ArrayList<>();
    TopicAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum__topic__details);

        back = findViewById(R.id.iv_back_topicDetail);
        author = findViewById(R.id.tv_author_topicDetail);
        title = findViewById(R.id.tv_title_topicDetail);
        date = findViewById(R.id.tv_date_topicDetail);
        details = findViewById(R.id.tv_description_topicDetail);
        addCom = findViewById(R.id.btn_addCom_topicDetail);

        rv = findViewById(R.id.rv_threads_topicDetail);
        manager = new LinearLayoutManager(Forum_Topic_Details.this);
        rv.setLayoutManager(manager);

        Intent i = getIntent();
        Topic topic = i.getParcelableExtra("Topic");
        topicKey = i.getStringExtra("KEY");
        dbref = FirebaseDatabase.getInstance().getReference().child("Forum_Threads");
        dbref.addListenerForSingleValueEvent(listener);

        author.setText("From: " + topic.getUsername());
        title.setText("Subject: " + topic.getSubject());
        date.setText("On: " + topic.getDate());
        details.setText(topic.getDetails());

        back.setOnClickListener(v -> {
            Intent intent = new Intent(Forum_Topic_Details.this, Forum_List.class);
            startActivity(intent);
        });

        addCom.setOnClickListener(v -> {
            Intent intent2 = new Intent(Forum_Topic_Details.this, Forum_AddCom.class);
            intent2.putExtra("Topic", topic);
            intent2.putExtra("KEY", topicKey);
            startActivity(intent2);
        });
    }

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                threads.clear();
                for(DataSnapshot dss: dataSnapshot.getChildren())
                    if (dss.getValue(Forum_Thread.class).getTopic().equals(topicKey)) {
                        threads.add(dss.getValue(Forum_Thread.class));
                    }
                adapter = new TopicAdapter(threads);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


}