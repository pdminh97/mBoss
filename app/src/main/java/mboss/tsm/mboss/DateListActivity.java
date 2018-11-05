package mboss.tsm.mboss;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Model.BossActivity;
import mboss.tsm.Model.Category;
import mboss.tsm.RecyclerViewAdapter.DateListRecyclerViewAdapter;
import mboss.tsm.Repository.BossActivityRepository;

public class DateListActivity extends AppCompatActivity {
    private ImageButton back;
    private ImageButton imCreate;
    private RecyclerView mRecyclerView;
    private DateListRecyclerViewAdapter adapter;
    private List<BossActivity> mBossActivity;
    private Category bossCategory;
    private TextView title;
    private Switch on_off;
    private String bossName;
    public static final int RequestCodeCategory = 2;
    public static final String CODECATE = "CATE";
    private String CATEGOTY;
    private String BOSSNAME;
    private Boolean CHECK;
    private BossActivity mBoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_list);
        iniatialView();
        iniatialData();

    }

    private void iniatialView() {

        Intent intent = getIntent();
//        boolean check = intent.getExtras().getBoolean("CHECK");
        bossName = intent.getExtras().getString("BossName");
        on_off = (Switch) findViewById(R.id.on_off_notification);
//        if (check) on_off.setChecked(true);
        title = findViewById(R.id.txtTitleCategory);
        imCreate = (ImageButton) findViewById(R.id.imCreateDate);
        mRecyclerView = findViewById(R.id.rvDate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DateListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        back = findViewById(R.id.btn_back);
    }

    private void iniatialData() {
        Intent intent = getIntent();
        bossCategory = (Category) intent.getSerializableExtra(BossDetailFragment.TITLE);

        if (bossCategory.getName() != null) {
            title.setText(bossCategory.getName());
        } else if (CATEGOTY != null) {
            title.setText(CATEGOTY);
        }

        BossActivityRepository bossActivityRepository = new BossActivityRepository(DateListActivity.this);
        bossActivityRepository.getAllDate(new BossActivityRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<BossActivity> bossActivities) {
                mBossActivity = bossActivities;
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
                if (bossCategory.getName() != null) {
                    intent.putExtra("CATEGORY", bossCategory.getName());
                } else {
                    intent.putExtra("CATEGORY", CATEGOTY);
                }
                if (bossName != null) {
                    intent.putExtra("BOSSNAME", bossName);
                } else {
                    intent.putExtra("BOSSNAME", BOSSNAME);
                }
//                startActivity(intent);
                startActivityForResult(intent, RequestCodeCategory);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void update() {
        if (adapter == null) {
            adapter = new DateListRecyclerViewAdapter(mBossActivity);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void DialogDeleteDate() {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodeCategory && resultCode == RESULT_OK && data != null) {
            CATEGOTY = data.getStringExtra("CATEGORY");
            BOSSNAME = data.getStringExtra("BOSSNAME");
            CHECK = data.getBooleanExtra("CHECK", false);
            mBoss = (BossActivity) data.getSerializableExtra("mBoss");
            if (mBoss != null) {
                mBossActivity.add(mBoss);
                adapter.notifyItemChanged(mBossActivity.size() - 1);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        iniatialView();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        iniatialView();
        iniatialData();
    }
}
