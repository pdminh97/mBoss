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
    int BossID;

    public BossActivityRepository(Activity parentActivity) {
        AppDatabase database = AppDatabase.getInstance(parentActivity.getApplicationContext());
        bossActivityDAO = database.bossActivityDAO();
        this.parentActivity = parentActivity;
    }

    public void getAllDate(int BossID, int CategoryID, getDataCallBack getDataCallBack) {
        GetAllDateAsyc getAllDateAsyc = new GetAllDateAsyc(BossID, CategoryID, bossActivityDAO, getDataCallBack);
        getAllDateAsyc.execute();

    }


    private class GetAllDateAsyc extends AsyncTask<Void, Void, List<BossActivity>> {
        List<BossActivity> bossActivities;
        private getDataCallBack mGetDataCallBack;
        private BossActivityDAO bossActivityDAO;
        int BossID;
        int CategoryID;

        public GetAllDateAsyc(int BossID, int CategoryID,BossActivityDAO bossActivityDAO, getDataCallBack mGetDataCallBack) {
            this.mGetDataCallBack = mGetDataCallBack;
            this.bossActivityDAO = bossActivityDAO;
            this.BossID = BossID;
            this.CategoryID = CategoryID;
        }

        @Override
        protected List<BossActivity> doInBackground(Void... voids) {
            bossActivities = bossActivityDAO.getDatePicker(BossID, CategoryID);
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


    public void insertDate(BossActivity bossActivity) {
        InsertDateAsyc insertDateAsyc = new InsertDateAsyc(bossActivityDAO, bossActivity);
        insertDateAsyc.execute();
    }


    public class InsertDateAsyc extends AsyncTask<BossActivity, Void, Void> {
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

    public interface getDataCallBack {
        void CallBackSuccess(List<BossActivity> bossActivities);

        void CallBackFail(String message);
    }
}
