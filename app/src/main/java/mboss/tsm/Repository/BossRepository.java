package mboss.tsm.Repository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.DAO.BossDAO;
import mboss.tsm.Model.Boss;
import mboss.tsm.RecyclerViewAdapter.BossListRecyclerViewAdapter;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import mboss.tsm.Utility.AppDatabase;
import mboss.tsm.mboss.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BossRepository {
    //    private IService service;
//    private List<Boss> bosses;
    private Context context;
    private BossDAO bossDAO;
    private Activity parentActivity;

//    public BossRepository(Context context) {
//        this.service = APIUtil.getIService();
//        this.context = context;
//    }

//    public void showBossList() {
//        final ProgressDialog loading = ProgressDialog.show(context,"Fetching Data","Please wait...",false,false);
//
//        service.getBossListByAccount().enqueue(new Callback<List<Boss>>() {
//            @Override
//            public void onResponse(Call<List<Boss>> call, Response<List<Boss>> response) {
//                if(response.isSuccessful()) {
//                    bosses = new ArrayList<>();
//                    bosses = response.body();
//
//                    RecyclerView recyclerView = ((Activity) context).findViewById(R.id.rvBosses);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
//                    BossListRecyclerViewAdapter adapter = new BossListRecyclerViewAdapter(bosses);
//                    recyclerView.setAdapter(adapter);
//                    loading.dismiss();
//                } else {
//                    Log.d("Error", "Load deo dc, ma code: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Boss>> call, Throwable t) {
//                Log.d("Error", "Gửi deo dc");
//            }
//        });
//    }

    public BossRepository(Activity parentActivity) {
        AppDatabase database = AppDatabase.getInstance(parentActivity.getApplicationContext());
        bossDAO = database.bossDAO();
        this.parentActivity = parentActivity;
    }

    public void getAllBosses(getDataCallBack getDataCallBack) {
        GetAllBossesAsync bossesAsync = new GetAllBossesAsync(bossDAO, getDataCallBack);
        bossesAsync.execute();
    }
    private class GetAllBossesAsync extends AsyncTask<Void, Void, List<Boss>> {
        private BossDAO bossDAO;
        private Activity parentActivity;
        List<Boss> bossList;
        private getDataCallBack mGetDataCallBack;

        public GetAllBossesAsync(BossDAO bossDAO, getDataCallBack mGetDataCallBack) {
            this.bossDAO = bossDAO;
            this.mGetDataCallBack = mGetDataCallBack;
        }

        @Override
        protected List<Boss> doInBackground(Void... voids) {
            bossList = bossDAO.getBosses();
            return bossList;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Boss> bosses) {
            super.onPostExecute(bosses);
//            RecyclerView recyclerView = parentActivity.findViewById(R.id.rvBosses);
//            recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity.getApplicationContext()));
//            BossListRecyclerViewAdapter adapter = new BossListRecyclerViewAdapter(bosses);
            mGetDataCallBack.CallBackSuccess(bosses);
//            recyclerView.setAdapter(adapter);
        }
    }


    public void InsertBoss(Boss boss) {
//        service.insertBoss(boss.getBossName(), boss.getBossAge(),boss.getBossWeight()).enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                if(response.isSuccessful()) {
//                    Toast.makeText(context, "Added successfully Server",Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Added Fail Server",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//
//            }
//        });
        InsertBossAsync insertBossAsync  = new InsertBossAsync(bossDAO, boss);
        insertBossAsync.execute();
    }
    private class InsertBossAsync extends AsyncTask< Boss,Void,Void>{
        private  BossDAO bossDAO;
        private  Boss newBoss;

        public InsertBossAsync(BossDAO bossDAO, Boss newBoss) {
            this.bossDAO = bossDAO;
            this.newBoss = newBoss;
        }

        @Override
        protected Void doInBackground(Boss... bosses) {
            bossDAO.insertBoss(newBoss);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            bossDAO.updateBoss(newBoss);
        }
    }


    public  interface  getDataCallBack{
        void CallBackSuccess(List<Boss> mBosses);
        void CallBackFail (String message);
    }
}
