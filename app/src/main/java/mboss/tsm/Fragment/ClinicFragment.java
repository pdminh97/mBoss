package mboss.tsm.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Repository.ClinicRepository;
import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClinicFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private Context context;
    private List<Suggestion> mSuggestions = new ArrayList<>();
    private FloatingSearchView searchView;
    private ClinicRepository clinicRepository;
    private Spinner snView;
    private RecyclerView rvClinic;
    private SupportMapFragment map;
    private LinearLayout lnMapContainer;
    private Button btnFindNearBy;
    private EditText edtRadius;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LatLng currentLocation;

    public ClinicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.clinic_fragment, container, false);
        context = view.getContext();

        lnMapContainer = view.findViewById(R.id.lnMapContainer);
        map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        rvClinic = view.findViewById(R.id.rvClinic);

        clinicRepository = new ClinicRepository(view.getContext());
        clinicRepository.getTopClinic(rvClinic);

        searchView = view.findViewById(R.id.floating_search_view);
        prepareSearchView();

        snView = view.findViewById(R.id.snView);
        prepareSpinnerView();

        edtRadius = view.findViewById(R.id.edtRadius);
        btnFindNearBy = view.findViewById(R.id.btnFindNearBy);
        btnFindNearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                } else {
                    GoogleApiClientBuilder();
                    googleApiClient.connect();
                    snView.setVisibility(View.VISIBLE);
                    snView.setSelection(1);
                }
            }
        });

        return view;
    }

    private void prepareSpinnerView() {
        String[] items = {"Danh Sách", "Bản Đồ"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        snView.setAdapter(adapter);
        snView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    rvClinic.setVisibility(View.VISIBLE);
                    lnMapContainer.setVisibility(View.GONE);
                } else if (position == 1) {
                    rvClinic.setVisibility(View.GONE);
                    lnMapContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void prepareSearchView() {
        mSuggestions.add(new Suggestion("Phòng Khám"));
        mSuggestions.add(new Suggestion("Thú y"));
        mSuggestions.add(new Suggestion("Phòng Chăm Sóc"));

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                searchView.showProgress();
                searchView.swapSuggestions(getSuggestion(newQuery));
                searchView.hideProgress();
            }
        });

        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                searchView.showProgress();
                searchView.swapSuggestions(getSuggestion(searchView.getQuery()));
                searchView.hideProgress();
            }

            @Override
            public void onFocusCleared() {

            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Suggestion suggestion = (Suggestion) searchSuggestion;
                clinicRepository.searchClinic(suggestion.getBody(), rvClinic);
                snView.setSelection(0);
                searchView.closeMenu(true);
            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (!currentQuery.isEmpty()) {
                    clinicRepository.searchClinic(currentQuery, rvClinic);
                    snView.setSelection(0);
                    searchView.closeMenu(true);
                }
            }
        });
    }

    private List<Suggestion> getSuggestion(String query) {
        List<Suggestion> suggestions = new ArrayList<>();
        for (Suggestion suggestion : mSuggestions) {
            if (suggestion.getBody().toLowerCase().contains(query.toLowerCase())) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(checkLocationPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        float radius = Float.parseFloat(edtRadius.getText().toString());
        String coordinate = currentLocation.latitude + "," + currentLocation.longitude;
        clinicRepository.searchNearBy(coordinate, radius, map, rvClinic, currentLocation);
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Connect Fail", Toast.LENGTH_SHORT).show();
        GoogleApiClientBuilder();
    }

    private void GoogleApiClientBuilder() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    @SuppressLint("ParcelCreator")
    private class Suggestion implements SearchSuggestion {
        private String mName;
        private boolean mIsHistory = false;

        public Suggestion(String suggestion) {
            mName = suggestion.toLowerCase();
        }

        public void setIsHistory(boolean isHistory) {
            this.mIsHistory = isHistory;
        }

        public boolean getIsHistory() {
            return this.mIsHistory;
        }

        @Override
        public String getBody() {
            return mName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(googleApiClient != null)
            googleApiClient.disconnect();
    }

    private boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return false;
        } else {
            return true;
        }
    }
}
