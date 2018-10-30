package mboss.tsm.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mboss.tsm.Model.Clinic;
import mboss.tsm.Model.Comment;
import mboss.tsm.Model.Service;
import mboss.tsm.Model.ServiceDetail;
import mboss.tsm.RecyclerViewAdapter.CommentRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.PriceRecycleViewAdapter;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.mboss.R;
import mboss.tsm.mboss.ServiceDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailRepository {
    private IService service;
    private Context context;

    public ServiceDetailRepository(Context context) {
        this.context = context;
        service = APIUtil.getIService();
    }

    public void getClinicByServiceID(final Service serviceDTO) {
        service.getClinicByServiceID(serviceDTO.getServiceID()).enqueue(new Callback<Clinic>() {
            @Override
            public void onResponse(Call<Clinic> call, Response<Clinic> response) {
                if(response.isSuccessful()) {
                    Intent intent = new Intent(context, ServiceDetailActivity.class);
                    intent.putExtra("clinic", response.body());
                    intent.putExtra("serviceID", serviceDTO.getServiceID());
                    intent.putExtra("serviceName", serviceDTO.getServiceName());
                    context.startActivity(intent);
                } else {
                    Log.e("Error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<Clinic> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    public void getServiceDetailByServiceID(int serviceID) {
        service.getServiceDetailByServiceID(serviceID).enqueue(new Callback<List<ServiceDetail>>() {
            @Override
            public void onResponse(Call<List<ServiceDetail>> call, Response<List<ServiceDetail>> response) {
                if(response.isSuccessful()) {
                    List<ServiceDetail> serviceDetails = response.body();
                    PriceRecycleViewAdapter adapter = new PriceRecycleViewAdapter(serviceDetails);
                    RecyclerView recyclerView = ((ServiceDetailActivity) context).findViewById(R.id.rvPrice);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e("Error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<List<ServiceDetail>> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    public void getCommentByClinicID(int clinicID) {
        final ProgressDialog loading = ProgressDialog.show(context,"Fetching Data","Please wait...",false,false);
        service.getCommentByClinicID(clinicID).enqueue(new Callback<List<Comment>>() {
            @Override
           public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()) {
                    List<Comment> comments = response.body();
                    CommentRecyclerViewAdapter adapter = new CommentRecyclerViewAdapter(comments);
                    RecyclerView recyclerView = ((ServiceDetailActivity) context).findViewById(R.id.rvComment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.e("Error", response.code() + "");
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("Error", t.getMessage() + "");
            }
        });
    }
}
