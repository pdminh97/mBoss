package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mboss.tsm.Model.Comment;
import mboss.tsm.mboss.R;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    private List<Comment> comments;

    public CommentRecyclerViewAdapter(List<Comment> comments) {
        this.comments = comments;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.comment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.accountName.setText(comments.get(i).getAccountName());

        viewHolder.time.setText(formatDate(comments.get(i).getCommentTime()));
        viewHolder.content.setText(comments.get(i).getContent());
        viewHolder.rating.setRating((float)comments.get(i).getRating());
    }

    @Override
    public int getItemCount() {
        if(comments != null) {
            return comments.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView accountName;
        private TextView time;
        private TextView content;
        private RatingBar rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accountName = itemView.findViewById(R.id.txtAccountName);
            time = itemView.findViewById(R.id.txtCommentTime);
            content = itemView.findViewById(R.id.txtCommentContent);
            rating = itemView.findViewById(R.id.commentRating);
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(date);
    }
}
