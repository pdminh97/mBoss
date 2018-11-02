package mboss.tsm.Repository;

import android.app.Activity;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import java.util.List;

import mboss.tsm.DAO.BossCategoryDAO;
import mboss.tsm.DAO.BossDAO;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
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

    public void getAllBossCategory(int BossID,DataCallBack getDataCallBack) {
        GetAllBossCategoryAsync bossesAsync = new GetAllBossCategoryAsync(BossID,bossCategoryDAO, getDataCallBack);
        bossesAsync.execute();
    }
    private class GetAllBossCategoryAsync extends AsyncTask<BossCategory,Void, Void> {
        private BossCategoryDAO bossCategoryDAO;
        private DataCallBack mGetDataCallBack;
        private int bossID;
        private List<BossCategory> mBossCategory;
        public GetAllBossCategoryAsync(int bossID, BossCategoryDAO bossCategoryDAO, DataCallBack mGetDataCallBack) {
            this.bossCategoryDAO = bossCategoryDAO;
            this.mGetDataCallBack = mGetDataCallBack;
            this.bossID = bossID;
        }

        @Override
        protected Void doInBackground(BossCategory... bossCategories) {

            try{
                mBossCategory = bossCategoryDAO.getBossCategory(bossID);
            }catch (SQLiteConstraintException e){
                mGetDataCallBack.CallBackFail("Get data failed");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mBossCategory!= null){
                mGetDataCallBack.CallBackSuccess(mBossCategory);
            }else {
                mGetDataCallBack.CallBackFail("Get data failed");
            }
        }
    }

    public void InsertBossCategory(BossCategory bossCategory, getDataCallBack listener) {
        InsertBossCategoryAsync insertBossCategoryAsync  = new InsertBossCategoryAsync(bossCategoryDAO, listener);
        insertBossCategoryAsync.execute(bossCategory);
    }
    public class InsertBossCategoryAsync extends AsyncTask<BossCategory,Void,Void>{
        private  BossCategoryDAO bossCategoryDAO;
        private getDataCallBack mListener;

        public InsertBossCategoryAsync(BossCategoryDAO bossCategoryDAO, getDataCallBack listener) {
            this.bossCategoryDAO = bossCategoryDAO;
            this.mListener= listener;
        }
        @Override
        protected Void doInBackground(BossCategory... bossCategories) {
            try {
                bossCategoryDAO.insertBossCategory(bossCategories);
            }catch (SQLiteConstraintException e){
                mListener.CallBackFail("Add failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.CallBackSuccess(null);
        }
    }


    public  interface  getDataCallBack{
        void CallBackSuccess(BossCategory mBossCategory);
        void CallBackFail (String message);
    }

    public  interface  DataCallBack{
        void CallBackSuccess(List<BossCategory> mBossCategory);
        void CallBackFail (String message);
    }



    public  void  deleteBossCategory(BossCategory bossCategory){
        DeleteBossCategoryAsync deleteBossCategoryAsync=new DeleteBossCategoryAsync(bossCategoryDAO, bossCategory);
        deleteBossCategoryAsync.execute();
    }

    private  class DeleteBossCategoryAsync extends  AsyncTask<BossCategory, Void, Void>{
        private  BossCategoryDAO bossCategoryDAO;
        private  BossCategory bossCategory;

        public DeleteBossCategoryAsync(BossCategoryDAO bossCategoryDAO, BossCategory bossCategory) {
            this.bossCategoryDAO = bossCategoryDAO;
            this.bossCategory = bossCategory;
        }

        @Override
        protected Void doInBackground(BossCategory... bossCategories) {
            bossCategoryDAO.deleteBossCategory(bossCategory);
            return null;
        }

    }

    public void updateBoss(BossCategory bossCategory){
        UpdateBossAsync updateBossAsync=new UpdateBossAsync(bossCategoryDAO);
        updateBossAsync.execute(bossCategory);
    }
    private  class UpdateBossAsync extends AsyncTask<BossCategory, Void, Void>{
        private BossCategoryDAO mbossDAO;
        private int update;

        public UpdateBossAsync(BossCategoryDAO mbossBossCategoryDAO) {
            this.mbossDAO = mbossBossCategoryDAO;

        }

        @Override
        protected Void doInBackground(BossCategory... bossCategories) {
            mbossDAO.updateBossCategory(bossCategories);
            return  null;
        }


    }


}
