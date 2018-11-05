package mboss.tsm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import mboss.tsm.Model.Comment;
import mboss.tsm.RecyclerViewAdapter.CommentRecyclerViewAdapter;
import mboss.tsm.Repository.ServiceDetailRepository;
import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    Button btnComment;
    EditText cmtContent;
    TextView txtAccountName;
    RatingBar rating;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);
        int clinicID = getArguments().getInt("clinicID");
        final ServiceDetailRepository repository = new ServiceDetailRepository(container.getContext());
        repository.getCommentByClinicID(clinicID);

        btnComment = view.findViewById(R.id.btnComment);
        cmtContent = view.findViewById(R.id.txtCommentContent);
        txtAccountName = view.findViewById(R.id.txtAccountName);
        rating = view.findViewById(R.id.commentRating);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Comment> comments = repository.getComments();
                CommentRecyclerViewAdapter adapter = repository.getCommentAdapter();

                Comment comment = new Comment();
                comment.setAccountName(txtAccountName.getText().toString());
                comment.setCommentTime(new Date());
                comment.setContent(cmtContent.getText().toString());
                comment.setRating(rating.getRating());

                comments.add(0, comment);
                adapter.notifyItemInserted(0);

                cmtContent.setText("");
            }
        });

        return view;
    }



}
