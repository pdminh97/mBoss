package mboss.tsm.Repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import mboss.tsm.DAO.DiaryDAO;
import mboss.tsm.Model.Diary;
import mboss.tsm.Utility.AppDatabase;

public class DiaryRepository {
    private DiaryDAO diaryDAO;
    public DiaryRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        this.diaryDAO = database.diaryDAO();
    }

    public void getDiary(DataCallBack dataCallBack) {
        GetDiaryAsync getDiaryAsync = new GetDiaryAsync(diaryDAO, dataCallBack);
        getDiaryAsync.execute();
    }

    public void insertDiary(Diary newDiary) {
        InsertDiaryAsync insertDiaryAsync = new InsertDiaryAsync(diaryDAO, newDiary);
        insertDiaryAsync.execute();
    }

    public void updateDiary(Diary updateDiary) {
        UpdateDiaryAsync updateDiaryAsync = new UpdateDiaryAsync(diaryDAO, updateDiary);
        updateDiaryAsync.execute();
    }


    private class GetDiaryAsync extends AsyncTask<Void, Void, List<Diary>> {
        private DiaryDAO diaryDAO;
        private DataCallBack dataCallBack;

        public GetDiaryAsync(DiaryDAO diaryDAO, DataCallBack dataCallBack) {
            this.diaryDAO = diaryDAO;
            this.dataCallBack = dataCallBack;
        }

        @Override
        protected List<Diary> doInBackground(Void... voids) {
            return diaryDAO.getDiaries();
        }

        @Override
        protected void onPostExecute(List<Diary> diaries) {
            super.onPostExecute(diaries);
            dataCallBack.CallBackSuccess(diaries);
        }
    }

    private class InsertDiaryAsync extends AsyncTask<Void, Void, Void> {
        private DiaryDAO diaryDAO;
        private Diary newDiary;

        public InsertDiaryAsync(DiaryDAO diaryDAO, Diary newDiary) {
            this.diaryDAO = diaryDAO;
            this.newDiary = newDiary;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            diaryDAO.insertDiary(newDiary);
            return null;
        }
    }

    private class UpdateDiaryAsync extends AsyncTask<Void, Void, Void> {
        private DiaryDAO diaryDAO;
        private Diary newDiary;

        public UpdateDiaryAsync(DiaryDAO diaryDAO, Diary newDiary) {
            this.diaryDAO = diaryDAO;
            this.newDiary = newDiary;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            diaryDAO.update(newDiary);
            return null;
        }
    }

    public interface DataCallBack{
        void CallBackSuccess(List<Diary> diaries);
        void CallBackFail (String message);
    }
}
