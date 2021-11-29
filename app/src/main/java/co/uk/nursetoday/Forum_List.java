package co.uk.nursetoday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.uk.nursetoday.Adapters.ForumAdapter;
import co.uk.nursetoday.Model.Topic;

public class Forum_List extends AppCompatActivity implements ForumAdapter.Holder.ForumInterface {

    Button newTopic;
    RecyclerView rv;
    RecyclerView.LayoutManager manager;
    ForumAdapter adapter;
    DatabaseReference dbref;
    ArrayList<Topic> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum__list);

        newTopic=findViewById(R.id.btn_newTopic_forumList);
        rv=findViewById(R.id.rv_forum);
        dbref = FirebaseDatabase.getInstance().getReference("Forum_Topics");
        manager = new LinearLayoutManager(Forum_List.this);
        rv.setLayoutManager(manager);
        dbref.addListenerForSingleValueEvent(listener);

        newTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Forum_List.this, Forum_New_Topic.class);
                startActivity(i);
            }
        });

    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dss:snapshot.getChildren()){
                Topic topic = dss.getValue(Topic.class);
                list.add(topic);
            }
            adapter = new ForumAdapter(list, Forum_List.this::onTopicClick);
            rv.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    @Override
    public void onTopicClick(int i) {
        Intent intent = new Intent(Forum_List.this, Forum_Topic_Details.class);
        //pass the object topic which was clicked
        intent.putExtra("Topic", list.get(i));
        startActivity(intent);
    }


}