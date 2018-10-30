package mboss.tsm.Repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import mboss.tsm.Model.Service;
import mboss.tsm.RecyclerViewAdapter.ServicesRecyclerViewAdapter;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.mboss.R;
import mboss.tsm.mboss.ServiceActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepository {
    private Context context;
    private IService service;
    private RecyclerView rvServices;
    private ServicesRecyclerViewAdapter adapter;

    public ServiceRepository(Context context) {
        service = APIUtil.getIService();
        this.context = context;
        rvServices = ((ServiceActivity) context).findViewById(R.id.rvServices);
        rvServices.setLayoutManager(new LinearLayoutManager(context));
    }

    public void getTopSevice() {
        final ProgressDialog loading = ProgressDialog.show(context,"Fetching Data","Please wait...",false,false);
        service.getTopService().enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()) {
                    adapter = new ServicesRecyclerViewAdapter(response.body(), context);
                    rvServices.setAdapter(adapter);
                    loading.dismiss();
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

    public void searchSevice(String search) {
        final ProgressDialog loading = ProgressDialog.show(context,"Fetching Data","Please wait...",false,false);
        service.searchService(search).enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()) {
                    adapter = new ServicesRecyclerViewAdapter(response.body(), context);
                    rvServices.setAdapter(adapter);
                    loading.dismiss();
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
}
