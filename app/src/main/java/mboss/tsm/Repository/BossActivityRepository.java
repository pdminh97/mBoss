package mboss.tsm.Repository;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import mboss.tsm.DAO.BossActivityDAO;
import mboss.tsm.Model.BossActivity;
import mboss.tsm.Utility.AppDatabase;

public class BossActivityRepository {
    private Context context;
    private BossActivityDAO bossActivityDAO;
    private Activity parentActivity;

    public BossActivityRepository(Activity parentActivity) {
        AppDatabase database = AppDatabase.getInstance(parentActivity.getApplicationContext());
        bossActivityDAO = database.bossActivityDAO();
        this.parentActivity = parentActivity;
    }

    public  void getAllDate(getDataCallBack getDataCallBack){
       GetAllDateAsyc getAllDateAsyc=new GetAllDateAsyc(bossActivityDAO, getDataCallBack);
       getAllDateAsyc.execute();

    }


    private  class GetAllDateAsyc extends AsyncTask<Void, Void, List<BossActivity>>{
List<BossActivity> bossActivities;
private getDataCallBack mGetDataCallBack;
        private BossActivityDAO bossActivityDAO;

        public GetAllDateAsyc(BossActivityDAO bossActivityDAO, getDataCallBack mGetDataCallBack ) {
            this.mGetDataCallBack = mGetDataCallBack;
            this.bossActivityDAO = bossActivityDAO;
        }

        @Override
        protected List<BossActivity> doInBackground(Void... voids) {
            bossActivities=bossActivityDAO.getDatePicker();
            return bossActivities;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<BossActivity> bossActivities) {
            super.onPostExecute(bossActivities);
            mGetDataCallBack.CallBackSuccess(bossActivities);
        }
    }






    public void insertDate(BossActivity bossActivity){
        InsertDateAsyc insertDateAsyc=new InsertDateAsyc(bossActivityDAO, bossActivity);
        insertDateAsyc.execute();
    }


    public class InsertDateAsyc extends AsyncTask<BossActivity, Void, Void>{
        private BossActivityDAO bossActivityDAO;
private BossActivity newBossActivity;

        public InsertDateAsyc(BossActivityDAO bossActivityDAO, BossActivity newBossActivity) {
            this.bossActivityDAO = bossActivityDAO;
            this.newBossActivity = newBossActivity;
        }


        @Override
        protected Void doInBackground(BossActivity... bossActivities) {
            bossActivityDAO.insertDate(newBossActivity);
            return null;
        }
    }

    public  interface  getDataCallBack{
        void CallBackSuccess(List<BossActivity> bossActivities);
        void CallBackFail(String message);
    }
}
