package mboss.tsm.mboss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import mboss.tsm.Model.Diary;
import mboss.tsm.Repository.DiaryRepository;
import mboss.tsm.Utility.AppDatabase;

public class DiaryListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        loadData();
    }

    private void loadData() {
        DiaryRepository diaryRepository = new DiaryRepository(this);
        diaryRepository.getAllDiaries();
    }

    public void clickToAddNew(View view) {
        EditText edtContent = findViewById(R.id.diaryContent);
        String content = edtContent.getText().toString();
        Diary diary = new Diary(content, null);
        DiaryRepository diaryRepository = new DiaryRepository(this);
        diaryRepository.insertDiary(diary);
    }
}
