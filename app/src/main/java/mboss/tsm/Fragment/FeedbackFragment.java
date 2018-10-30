package mboss.tsm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mboss.tsm.Repository.ServiceDetailRepository;
import mboss.tsm.Repository.ServiceRepository;
import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {


    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);
        int clinicID = getArguments().getInt("clinicID");
        ServiceDetailRepository repository = new ServiceDetailRepository(container.getContext());
        repository.getCommentByClinicID(clinicID);
        return view;
    }

}
