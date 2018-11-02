//package mboss.tsm.mboss;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.widget.ImageButton;
//
//import java.util.List;
//
//import mboss.tsm.Model.Boss;
//import mboss.tsm.RecyclerViewAdapter.BossListRecyclerViewAdapter;
//import mboss.tsm.Repository.BossRepository;
//
//public class BossListActivity extends AppCompatActivity {
//    private ImageButton imCreate;
//    private RecyclerView mRecyclerView;
//    private BossListRecyclerViewAdapter mAdapter;
//    private List<Boss> mListBoss;
//    public static final String BOSSES = "Bosses";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_boss_list);
//        iniatialView();
//        iniatialData();
//    }
//
//    private void iniatialView() {
//        imCreate = (ImageButton) findViewById(R.id.imCreateBoss);
//        mRecyclerView = findViewById(R.id.rvBosses);
//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//
//    }
//
//    private void iniatialData() {
//        BossRepository bossRepository = new BossRepository(BossListActivity.this);
//        bossRepository.getAllBosses(new BossRepository.getDataCallBack() {
//            @Override
//            public void CallBackSuccess(List<Boss> mBosses) {
//                mListBoss = mBosses;
//                update();
//            }
//
//            @Override
//            public void CallBackFail(String message) {
//            }
//        });
////        imCreate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(BossListActivity.this, AddNewBossActivity.class);
////                startActivity(intent);
////            }
////        });
//
//    }
//
//    private void update() {
//        if (mAdapter == null) {
//            mAdapter = new BossListRecyclerViewAdapter(mListBoss);
//            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.setItemOnListenner(new BossListRecyclerViewAdapter.setOnIteamlistener() {
//                @Override
//                public void setOnIteamListener(int position) {
//                    intentToDetail(mListBoss.get(position));
//                }
//            });
//        } else {
//            mAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private void intentToDetail(Boss boss) {
//        //change to BossDetail
//        Intent intent = new Intent(BossListActivity.this, BossDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(BOSSES, boss);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
//}
