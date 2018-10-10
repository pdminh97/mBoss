package mboss.tsm.Repository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.RecyclerViewAdapter.BossListRecyclerViewAdapter;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.mboss.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BossRepository {
    private IService service;
    private List<Boss> bosses;
    private Context context;

    public BossRepository(Context context) {
        this.service = APIUtil.getIService();
        this.context = context;
    }

    public void showBossList() {
        final ProgressDialog loading = ProgressDialog.show(context,"Fetching Data","Please wait...",false,false);

        service.getBossListByAccount().enqueue(new Callback<List<Boss>>() {
            @Override
            public void onResponse(Call<List<Boss>> call, Response<List<Boss>> response) {
                if(response.isSuccessful()) {
                    bosses = new ArrayList<>();
                    bosses = response.body();

                    RecyclerView recyclerView = ((Activity) context).findViewById(R.id.rvBosses);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    BossListRecyclerViewAdapter adapter = new BossListRecyclerViewAdapter(bosses);
                    recyclerView.setAdapter(adapter);
                    loading.dismiss();
                } else {
                    Log.d("Error", "Load deo dc, ma code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Boss>> call, Throwable t) {
                Log.d("Error", "Gửi deo dc");
            }
        });
    }
}
