package mboss.tsm.mboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BossActivityDateDetail extends AppCompatActivity {
private Button btnClose;
private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_date_detail);
        initialView();
        initialData();
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
