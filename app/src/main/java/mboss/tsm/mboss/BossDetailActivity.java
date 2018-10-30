package mboss.tsm.mboss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.RecyclerViewAdapter.BossCategoryListRecyclerViewAdapter;
import mboss.tsm.Repository.BossCategoryRepository;

public class BossDetailActivity extends AppCompatActivity {
    private Boss mBoss;
    private TextView tvNameDetail;
    private TextView tvGenderDetail;
    private TextView tvSpeciesDetail;
    private ImageView imageDetail;
    private ImageButton imCreate;
    private Button btnEdit;
    private BossCategoryListRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<BossCategory> bossCategories;
    public static final String TITLE = "Title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_detail);
        getData();
        iniatialData();

    }


    private void getData() {
        tvNameDetail = findViewById(R.id.tvName_detail);
        tvSpeciesDetail = findViewById(R.id.tvSpecies_detail);
        tvGenderDetail = findViewById(R.id.tvGender_detail);
        imageDetail = findViewById(R.id.image_detail);
        imCreate = findViewById(R.id.imCreate);
        btnEdit = findViewById(R.id.btnEditBoss);
        mRecyclerView = findViewById(R.id.rvBossCategory);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }



    private void iniatialData() {
        Intent intent = getIntent();
        mBoss = (Boss)intent.getSerializableExtra(BossListActivity.BOSSES);
//        Log.e("hehe", "name: "+ mBoss.getBossName() + " gender: " +mBoss.getGender()+ " special: "+ mBoss.getSpecies() +"Pic: "+ mBoss.getPictures());
        tvGenderDetail.setText(mBoss.getGender());
        tvNameDetail.setText(mBoss.getBossName());
        tvSpeciesDetail.setText(mBoss.getSpecies());
        imageDetail.setImageURI(Uri.parse(mBoss.getPictures()));

        BossCategoryRepository bossCategoryRepository = new BossCategoryRepository(BossDetailActivity.this);
        bossCategoryRepository.getAllBossCategory(new BossCategoryRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<BossCategory> mBossCategory) {
                bossCategories = mBossCategory;
                update();
            }
            @Override
            public void CallBackFail(String message) {
            }
        });
        imCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BossDetailActivity.this, CategoryListActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BossDetailActivity.this, EditBossActivity.class);
                startActivity(intent);
            }
        });

    }


    private void update() {
        Log.e("ADAPTER", mAdapter +"");
        if (mAdapter == null) {
            mAdapter = new BossCategoryListRecyclerViewAdapter(bossCategories);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setItemOnListenner(new BossCategoryListRecyclerViewAdapter.OnItemListener() {
                @Override
                public void OnClickItemListener(int postion) {
                    Intent intent = new Intent(BossDetailActivity.this, DateListActivity.class);
                    startActivity(intent);
                    //  intentToDetail(bossCategories.get(postion));
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();

        }
    }

//    private void intentToDetail(BossCategory bossCategory){
//        Intent intent=new Intent(BossDetailActivity.this,DateListActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable(TITLE, bossCategory);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }


}
