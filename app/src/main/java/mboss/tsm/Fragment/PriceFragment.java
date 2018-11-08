package mboss.tsm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import mboss.tsm.Repository.ClinicRepository;
import mboss.tsm.mboss.R;

public class PriceFragment extends Fragment {
    private ExpandableListView exPrice;
    public PriceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.price_fragment, container, false);
        ClinicRepository repository = new ClinicRepository(view.getContext());
        int clinicID = getArguments().getInt("clinicID");

        exPrice = view.findViewById(R.id.exPrice);
        repository.getServicesByClinicID(clinicID, exPrice);
        return view;
    }
}
