package mboss.tsm.Repository;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import mboss.tsm.DAO.DiaryDAO;
import mboss.tsm.Model.Diary;
import mboss.tsm.Utility.AppDatabase;

public class BossActivityRepository {
    private Context context;
//    private BossActivityDAO bossActivityDAO;
    private DiaryDAO diaryDAO;

    private Activity parentActivity;

    public BossActivityRepository(Activity parentActivity) {
        AppDatabase database = AppDatabase.getInstance(parentActivity.getApplicationContext());
//        bossActivityDAO = database.bossActivityDAO();
        this.parentActivity = parentActivity;
    }

    public void getDatePickedForBossActivity(int BossID, int CategoryID, getDataCallBack getDataCallBack) {
        GetAllDateAsyc getAllDateAsyc = new GetAllDateAsyc(BossID, CategoryID, diaryDAO, getDataCallBack);
        getAllDateAsyc.execute();

    }

    public void getDatePickerForBossActivityCompleted(int BossID, int CategoryID, getDataCallBack getDataCallBack) {
        GetAllDateBossActivityDone getAllDateBossActivityDone = new GetAllDateBossActivityDone(BossID, CategoryID, diaryDAO, getDataCallBack);
        getAllDateBossActivityDone.execute();
    }


    private class GetAllDateBossActivityDone extends AsyncTask<Void, Void, List<Diary>> {
        List<Diary> diaries;
        private getDataCallBack mGetDataCallBack;
        private DiaryDAO diaryDAO;
        int BossID;
        int CategoryID;


        protected List<Diary> doInBackground(Void... voids) {
            diaries = diaryDAO.getBossActivityCompleted(BossID, CategoryID);
            return diaries;
        }

        public GetAllDateBossActivityDone(int BossID, int CategoryID, DiaryDAO diaryDAO, getDataCallBack mGetDataCallBack) {
            this.mGetDataCallBack = mGetDataCallBack;
            this.diaryDAO = diaryDAO;
            this.BossID = BossID;
            this.CategoryID = CategoryID;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Diary> diaries) {
            super.onPostExecute(diaries);
            mGetDataCallBack.CallBackSuccess(diaries);
        }
    }

    private class GetAllDateAsyc extends AsyncTask<Void, Void, List<Diary>> {
        List<Diary> mBossDiaries;
        private getDataCallBack mGetDataCallBack;
//        private BossActivityDAO bossActivityDAO;
        private DiaryDAO diaryDAO;
        int BossID;
        int CategoryID;

        public GetAllDateAsyc(int BossID, int CategoryID, DiaryDAO diaryDAO, getDataCallBack mGetDataCallBack) {
            this.mGetDataCallBack = mGetDataCallBack;
            this.diaryDAO = diaryDAO;
            this.BossID = BossID;
            this.CategoryID = CategoryID;
        }

        @Override
        protected List<Diary> doInBackground(Void... voids) {
            mBossDiaries = diaryDAO.getDatePickedForBossActivity(BossID, CategoryID);
            return mBossDiaries;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Diary> mBossDiaries) {
            super.onPostExecute(mBossDiaries);
            mGetDataCallBack.CallBackSuccess(mBossDiaries);
        }
    }


    public void insertDatePickerToDiary(Diary newDiary) {
        InsertDateAsyc insertDateAsyc = new InsertDateAsyc(diaryDAO, newDiary);
        insertDateAsyc.execute();
    }

    public class InsertDateAsyc extends AsyncTask<Diary, Void, Void> {
        private DiaryDAO diaryDAO;
        private Diary newDiary;

        public InsertDateAsyc(DiaryDAO diaryDAO, Diary newDiary) {
            this.diaryDAO = diaryDAO;
            this.newDiary = newDiary;
        }


        @Override
        protected Void doInBackground(Diary... diaries) {
            diaryDAO.insertDiary(newDiary);
            return null;
        }
    }

    public void finishBossActivity(Diary diary, getStatusCallBack getStatusCallBack) {
        finishBossActivityAsync finishBossActivityAsync = new finishBossActivityAsync(diaryDAO, getStatusCallBack);
        finishBossActivityAsync.execute(diary);
    }

    public class finishBossActivityAsync extends AsyncTask<Diary, Void, Void> {
        private DiaryDAO diaryDAO;
        private getStatusCallBack getStatusCallBack;

        public finishBossActivityAsync(DiaryDAO diaryDAO, getStatusCallBack getStatusCallBack) {
            this.diaryDAO = diaryDAO;
            this.getStatusCallBack = getStatusCallBack;
        }
        @Override
        protected Void doInBackground(Diary... diaries) {
            diaryDAO.completeBossActivity(diaries);
            return null;
        }
    }

    public interface getDataCallBack {
        void CallBackSuccess(List<Diary> mBossDiaries);

        void CallBackFail(String message);
    }

    public interface getStatusCallBack {
        void CallBackSuccess(Diary diary);

        void CallBackFail(String message);
    }
}
