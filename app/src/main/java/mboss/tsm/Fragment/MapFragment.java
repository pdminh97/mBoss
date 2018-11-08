package mboss.tsm.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mboss.tsm.mboss.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    private LatLng position;
    private Context context;
    private LatLng currentLocation;
    private PolylineOptions polylineOptions;
    private MarkerOptions markerOptions;
    private Button btnDiraction;
    private TextView txtDistance;
    private boolean fullZoom = true;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public MapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        context = container.getContext();

        String latitude = getArguments().getString("latitude");
        String longtitude = getArguments().getString("longtitude");

        position = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longtitude));
        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        txtDistance = view.findViewById(R.id.txtDistance);

        btnDiraction = view.findViewById(R.id.btnDirection);
        btnDiraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);
                    return;
                } else {
                    GoogleApiClientBuilder();
                    mGoogleApiClient.connect();
                    fullZoom = true;
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            mMap.setMyLocationEnabled(true);

            markerOptions = new MarkerOptions();
            markerOptions.position(position);
            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17));
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String key = "AIzaSyDZFo7cHEpn209ftXBweNTU76xre0cAbCA";
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters + "&key=" + key;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        HttpURLConnection urlConnection = null;
        InputStream iStream = null;
        BufferedReader br = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception download url", e.toString());
        } finally {
            if (br != null)
                br.close();
            if (iStream != null)
                iStream.close();
            if (urlConnection != null) ;
            urlConnection.disconnect();
        }
        return data;
    }

    private void drawPolygon(JSONObject result) {
        JSONArray jRoute, jLeg, jStep;
        List<String> polylineList = new ArrayList<>();
        String distance = "";

        try {
            if (result.getString("status").equalsIgnoreCase("OK")) {
                jRoute = result.getJSONArray("routes");
                for (int i = 0; i < jRoute.length(); i++) {
                    jLeg = jRoute.getJSONObject(i).getJSONArray("legs");
                    for (int j = 0; j < jLeg.length(); j++) {
                        distance = jLeg.getJSONObject(j).getJSONObject("distance").getString("text");
                        jStep = jLeg.getJSONObject(j).getJSONArray("steps");
                        for (int k = 0; k < jStep.length(); k++) {
                            String polyline = jStep.getJSONObject(k).getJSONObject("polyline").getString("points");
                            polylineList.add(polyline);
                        }
                    }
                }

                mMap.clear();
                markerOptions = new MarkerOptions();
                markerOptions.position(position);
                mMap.addMarker(markerOptions);

                for (int i = 0; i < polylineList.size(); i++) {
                    polylineOptions = new PolylineOptions();
                    polylineOptions.color(Color.BLUE);
                    polylineOptions.width(7);
                    polylineOptions.addAll(PolyUtil.decode(polylineList.get(i)));
                    mMap.addPolyline(polylineOptions);
                }
                txtDistance.setText("Khoảng cách: " + distance);
                if (fullZoom) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(position);
                    builder.include(currentLocation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 48));
                    fullZoom = false;
                }
            }
        } catch (JSONException e) {
            Log.e("Direction Error", e.getMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient);
            currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        GoogleApiClientBuilder();
        Toast.makeText(getContext(), "Connect Fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        String url = getDirectionsUrl(currentLocation, position);
        DownloadDirectionData task = new DownloadDirectionData();
        task.execute(url);
    }

    private void GoogleApiClientBuilder() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    private class DownloadDirectionData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ;
            try {
                JSONObject jsonObject = new JSONObject(result);
                drawPolygon(jsonObject);
            } catch (JSONException e) {
                Log.d("Background Task", e.toString());
            }
        }
    }
}
