package co.uk.nursetoday.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.uk.nursetoday.Model.Forum_Thread;
import co.uk.nursetoday.Model.Topic;
import co.uk.nursetoday.R;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.Holder> {

    ArrayList<Topic> list;
    //create a forum interface object
    Holder.ForumInterface listener;

    public ForumAdapter(ArrayList<Topic> list, Holder.ForumInterface _listener) {
        this.list = list;
        listener = _listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumcard, parent,
                false);
        //create an instance of the Holder object
        Holder holder = new Holder(v, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.title.setText("Subject: " + list.get(position).getSubject());
        holder.author.setText("Started by: " + list.get(position).getUsername());
        holder.date.setText("On: " + list.get(position).getDate());
        holder.replies.setText("Number of replies: " +list.get(position).getThreads().toString());

        //Picasso.get().load(list.get(position).getImageURl) - NOT NEEDED HERE, ONLY FOR JOBS
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, author, date, replies;
        ForumInterface listener;

        public Holder(@NonNull View itemView, ForumInterface _listener) {
            super(itemView);
            title = itemView.findViewById(R.id.topicTitle_forum);
            author = itemView.findViewById(R.id.tv_author_forum);
            date = itemView.findViewById(R.id.tv_date_forum);
            replies = itemView.findViewById(R.id.tv_replies_forum);
            listener = _listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onTopicClick(getAdapterPosition());
        }

        public interface ForumInterface {
            public void onTopicClick (int i);
        }

    }
}
