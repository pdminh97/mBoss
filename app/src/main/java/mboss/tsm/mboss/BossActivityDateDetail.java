package mboss.tsm.mboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mboss.tsm.Model.Diary;

public class BossActivityDateDetail extends AppCompatActivity {
private Button btnClose;
private ImageButton back;
private Diary bossActivity;
private TextView diary_content;
private TextView txtDate;
private TextView txtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_date_detail);
        initialView();
        initialData();
        bossActivity = (Diary) getIntent().getParcelableExtra("bossActivity");
        diary_content = findViewById(R.id.diary_content);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        if(bossActivity != null) {
            diary_content.setText(bossActivity.getContent());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                Date date = dateFormat.parse(bossActivity.getDiaryTime());
                txtDate.setText(date.getDate() + "-" + date.getMonth() + "-" + (1900 + date.getYear()));
                txtTime.setText(date.getHours() + ":" + date.getMinutes());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialView(){
        btnClose = findViewById(R.id.ibnClose);
        back = findViewById(R.id.btn_back);
    }
    private void initialData(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clickToIntentEditDate(View view) {
        Intent intent = new Intent(BossActivityDateDetail.this, BossEditDateActivity.class);
        startActivityForResult(intent, 1);
    }
}
