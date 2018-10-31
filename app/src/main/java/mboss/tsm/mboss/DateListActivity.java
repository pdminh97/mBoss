package mboss.tsm.mboss;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.BossActivity;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.RecyclerViewAdapter.DateListRecyclerViewAdapter;
import mboss.tsm.Repository.BossActivityRepository;

public class DateListActivity extends AppCompatActivity {
    private ImageButton imCreate;
    private RecyclerView mRecyclerView ;
    private DateListRecyclerViewAdapter adapter;
    private List<BossActivity> mBossActivity;
    private BossCategory bossCategory;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);
        iniatialView();
        iniatialData();
    }

    private  void iniatialView(){
        title=findViewById(R.id.txtTitleCategory);
        imCreate = (ImageButton) findViewById(R.id.imCreateDate);
        mRecyclerView = findViewById(R.id.rvDate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DateListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private  void iniatialData(){
//        Intent intent= getIntent();
//        bossCategory = (BossCategory) intent.getSerializableExtra(BossDetailActivity.TITLE);
//        title.setText(bossCategory.getmCategorytList().getName());
        BossActivityRepository bossActivityRepository= new BossActivityRepository(DateListActivity.this);
        bossActivityRepository.getAllDate(new BossActivityRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<BossActivity> bossActivities) {
                mBossActivity=bossActivities;
                update();
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
        imCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DateListActivity.this, AddDateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void update(){
        if(adapter == null){
            adapter = new DateListRecyclerViewAdapter(mBossActivity);
            mRecyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    public  void  DialogDeleteDate(){
        AlertDialog.Builder dialogDelete= new AlertDialog.Builder(this);
        dialogDelete.setMessage("Bạn có chắc muốn xóa không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogDelete.show();
    }
}
