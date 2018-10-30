package mboss.tsm.Repository;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

import mboss.tsm.DAO.BossCategoryDAO;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Utility.AppDatabase;

public class BossCategoryRepository {

    private BossCategoryDAO bossCategoryDAO;
    private Activity parentActivity;
    private int ID;
    public BossCategoryRepository(Activity parentActivity) {
        AppDatabase database = AppDatabase.getInstance(parentActivity.getApplicationContext());
        bossCategoryDAO = database.bossCategoryDAO();
        this.parentActivity = parentActivity;

    }



    public void getAllBossCategory(getDataCallBack getDataCallBack) {
        GetAllBossCategoryAsync bossesAsync = new GetAllBossCategoryAsync(bossCategoryDAO, getDataCallBack);
        bossesAsync.execute();
    }
    private class GetAllBossCategoryAsync extends AsyncTask<Void, Void, List<BossCategory>> {
        private BossCategoryDAO bossCategoryDAO;
        private Activity parentActivity;
        List<BossCategory> bossCategoryList;
        private getDataCallBack mGetDataCallBack;
        private int bossID;

        public GetAllBossCategoryAsync( BossCategoryDAO bossCategoryDAO, getDataCallBack mGetDataCallBack) {
            this.bossCategoryDAO = bossCategoryDAO;
            this.mGetDataCallBack = mGetDataCallBack;

        }

        @Override
    protected List<BossCategory> doInBackground(Void... voids) {
        bossCategoryList=bossCategoryDAO.getBossCategory();
        return bossCategoryList;
    }

        @Override
        protected void onPostExecute(List<BossCategory> bossCategories) {
            super.onPostExecute(bossCategories);
            mGetDataCallBack.CallBackSuccess(bossCategories);
        }
    }

    public void InsertBossCategory(BossCategory bossCategory) {
        InsertBossCategoryAsync insertBossCategoryAsync  = new InsertBossCategoryAsync(bossCategoryDAO, bossCategory);
        insertBossCategoryAsync.execute();
    }
    private class InsertBossCategoryAsync extends AsyncTask<BossCategory,Void,Void>{
        private  BossCategoryDAO bossCategoryDAO;
        private BossCategory newBossCategory;

        public InsertBossCategoryAsync(BossCategoryDAO bossCategoryDAO, BossCategory newBossCategory) {
            this.bossCategoryDAO = bossCategoryDAO;
            this.newBossCategory = newBossCategory;
        }


        @Override
        protected Void doInBackground(BossCategory... bossCategories) {
           bossCategoryDAO.insertBossCategory(newBossCategory);
           return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public  interface  getDataCallBack{
        void CallBackSuccess(List<BossCategory> mBossCategory);
        void CallBackFail(String message);
    }

}
