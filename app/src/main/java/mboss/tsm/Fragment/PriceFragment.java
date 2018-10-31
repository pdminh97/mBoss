package mboss.tsm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mboss.tsm.Repository.ServiceDetailRepository;
import mboss.tsm.mboss.R;
import mboss.tsm.mboss.ServiceDetailActivity;

public class PriceFragment extends Fragment {
    public PriceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.price_fragment, container, false);
        ServiceDetailRepository repository = new ServiceDetailRepository(view.getContext());
        int serviceID = getArguments().getInt("serviceID");
        repository.getServiceDetailByServiceID(serviceID);
        return view;
    }
}
