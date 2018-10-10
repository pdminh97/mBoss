package mboss.tsm.Repository;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import mboss.tsm.DAO.DiaryDAO;
import mboss.tsm.Model.Diary;
import mboss.tsm.RecyclerViewAdapter.DiaryListRecyclerViewAdapter;
import mboss.tsm.Utility.AppDatabase;
import mboss.tsm.mboss.R;

public class DiaryRepository {
    private DiaryDAO diaryDAO;
    private Activity parentActivity;

    public DiaryRepository(Activity parentActivity) {
        AppDatabase database = AppDatabase.getInstance(parentActivity.getApplicationContext());
        diaryDAO = database.diaryDAO();
        this.parentActivity = parentActivity;
    }

    public void getAllDiaries() {
        GetAllDiariesAsync diariesAsync = new GetAllDiariesAsync(diaryDAO, parentActivity);
        diariesAsync.execute();
    }

    public void insertDiary(Diary newDiary) {
        InsertDiariesAsync insertDiariesAsync = new InsertDiariesAsync(diaryDAO, newDiary);
        insertDiariesAsync.execute();
    }

    private class GetAllDiariesAsync extends AsyncTask<Void, Void, List<Diary>> {
        private DiaryDAO diaryDAO;
        private Activity parentActivity;

        public GetAllDiariesAsync(DiaryDAO diaryDAO, Activity parentActivity) {
            this.diaryDAO = diaryDAO;
            this.parentActivity = parentActivity;
        }

        @Override
        protected List<Diary> doInBackground(Void... voids) {
            List<Diary> diaryLIst = diaryDAO.getDiaries();
            return diaryLIst;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Diary> diaries) {
            super.onPostExecute(diaries);
            RecyclerView recyclerView = parentActivity.findViewById(R.id.rvDiaryList);
            recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity.getApplicationContext()));
            DiaryListRecyclerViewAdapter adapter = new DiaryListRecyclerViewAdapter(diaries);
            recyclerView.setAdapter(adapter);
        }
    }

    private class InsertDiariesAsync extends AsyncTask<Void, Void, Long> {
        private DiaryDAO diaryDAO;
        private Diary newDiary;

        public InsertDiariesAsync(DiaryDAO diaryDAO, Diary newDiary) {
            this.diaryDAO = diaryDAO;
            this.newDiary = newDiary;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return diaryDAO.insertDiary(newDiary);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            if(result > 0) {

            } else {

            }
        }
    }
}
