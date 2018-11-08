package mboss.tsm.Repository;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import mboss.tsm.Model.Clinic;
import mboss.tsm.Model.Service;
import mboss.tsm.RecyclerViewAdapter.ClinicRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.PriceExpendableAdapter;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.mboss.ClinicDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicRepository {
    private IService service;
    private Context context;
    private ClinicRecyclerViewAdapter adapter;

    public ClinicRepository(Context context) {
        this.context = context;
        service = APIUtil.getIService();
    }

    public void getTopClinic(final RecyclerView rvClinic) {
        final ProgressDialog loading = ProgressDialog.show(context, "Đang Lấy Dữ Liệu", "Vui Lòng Chờ...", false, true);
        loading.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loading.dismiss();
            }
        });
        service.getTopClinic().enqueue(new Callback<List<Clinic>>() {
            @Override
            public void onResponse(Call<List<Clinic>> call, Response<List<Clinic>> response) {
                if (response.isSuccessful()) {
                    adapter = new ClinicRecyclerViewAdapter(response.body(), context);
                    rvClinic.setLayoutManager(new LinearLayoutManager(context));
                    rvClinic.setAdapter(adapter);
                } else {
                    Log.e("Error", response.code() + "");
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<Clinic>> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    public void searchClinic(String search, final RecyclerView rvClinic) {
        final ProgressDialog loading = ProgressDialog.show(context, "Đang Lấy Dữ Liệu", "Vui Lòng Chờ...", false, true);
        loading.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loading.dismiss();
            }
        });
        service.searchClinic(search).enqueue(new Callback<List<Clinic>>() {
            @Override
            public void onResponse(Call<List<Clinic>> call, Response<List<Clinic>> response) {
                if (response.isSuccessful()) {
                    adapter = new ClinicRecyclerViewAdapter(response.body(), context);
                    rvClinic.setAdapter(adapter);
                } else {
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", response.code() + "");
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<Clinic>> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    public void getClinicDetailByID(int clinicID) {
        final ProgressDialog loading = ProgressDialog.show(context, "Đang Lấy Dữ Liệu", "Vui Lòng Chờ...", false, true);
        loading.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loading.dismiss();
            }
        });
        service.getClinicByID(clinicID).enqueue(new Callback<Clinic>() {
            @Override
            public void onResponse(Call<Clinic> call, Response<Clinic> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(context, ClinicDetailActivity.class);
                    intent.putExtra("clinic", response.body());
                    context.startActivity(intent);
                } else {
                    Log.e("Error", response.code() + "");
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<Clinic> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    public void getServicesByClinicID(int clinicID, final ExpandableListView expandableListView) {
        service.getServicesByClinicID(clinicID).enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    PriceExpendableAdapter adapter = new PriceExpendableAdapter(context, response.body());
                    expandableListView.setAdapter(adapter);
                } else {
                    Log.e("Error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    public void searchNearBy(String coordinate, float radius, final SupportMapFragment map, final RecyclerView rvClinic, final LatLng currentposition) {
        service.searchNearBy(coordinate, radius).enqueue(new Callback<List<Clinic>>() {
            @Override
            public void onResponse(Call<List<Clinic>> call, Response<List<Clinic>> response) {
                if (response.isSuccessful()) {
                    adapter = new ClinicRecyclerViewAdapter(response.body(), context);
                    rvClinic.setAdapter(adapter);
                    prepareMap(map, response.body(), currentposition);
                } else {
                    Log.e("Error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<List<Clinic>> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    private void prepareMap(SupportMapFragment map, final List<Clinic> clinics, final LatLng currentLocation) {
        final HashMap<String, Clinic> markerClinic = new HashMap<>();
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((ClinicDetailActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                } else {
                    googleMap.setMyLocationEnabled(true);
                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            MarkerOptions markerOptions;
                            Marker marker;
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();

                            builder.include(currentLocation);
                            LatLng latLng;
                            for (Clinic clinic:clinics) {
                                markerOptions = new MarkerOptions();
                                latLng = new LatLng(Double.parseDouble(clinic.getLatitude()), Double.parseDouble(clinic.getLongtitude()));
                                markerOptions.position(latLng);
                                markerOptions.title(clinic.getName());
                                builder.include(latLng);
                                marker = googleMap.addMarker(markerOptions);
                                marker.showInfoWindow();
                                markerClinic.put(marker.getId(), clinic);

                            }
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 48));
                        }
                    });

                    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent = new Intent(context, ClinicDetailActivity.class);
                            intent.putExtra("clinic", markerClinic.get(marker.getId()));
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
