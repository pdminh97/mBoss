package mboss.tsm.mboss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
import mboss.tsm.RecyclerViewAdapter.CategoryListRecyclerViewAdapter;
import mboss.tsm.Repository.BossCategoryRepository;


public class CategoryListActivity extends AppCompatActivity {
    private List<Category> categories;

    private RecyclerView mRecyclerView ;
    private CategoryListRecyclerViewAdapter mAdapter;
    private int BossID;
    private  Category category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        getDataIntentToBossDetail();
        iniatialView();
        initialData();
    }
    private void getDataIntentToBossDetail(){
        Bundle intent = getIntent().getExtras();
        BossID = intent.getInt(BossDetailFragment.CATEGORY);
    }
    private  void iniatialView() {
        mRecyclerView = findViewById(R.id.rvCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CategoryListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

    }
    private void initialData(){
        categories = new ArrayList<>();
        Category category1 = new Category(R.mipmap.tam,"Tắm");
        Category category2 = new Category(R.mipmap.tia,"Tỉa Lông");
        Category category3 = new Category(R.mipmap.mong,"Cắt móng");
        Category category4 = new Category(R.mipmap.an,"Cho ăn");
        Category category5 = new Category(R.mipmap.tiem,"Tiêm");
        Category category6 = new Category(R.mipmap.thuoc,"Uống thuốc");
        Category category7 = new Category(R.mipmap.kham,"Khám định kỳ");
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);
        categories.add(category6);
        categories.add(category7);
        update();
    }
    private void update(){
        if(mAdapter == null) {
            mAdapter = new CategoryListRecyclerViewAdapter(CategoryListActivity.this,categories);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setItemOnListenner(new CategoryListRecyclerViewAdapter.OnItemListener() {
                @Override
                public void OnClickItemListener(int postion) {
                    BossCategoryRepository  mBossCategoryRepository = new BossCategoryRepository(CategoryListActivity.this);
                    BossCategory mBossCategory = new BossCategory();
                    mBossCategory.setBossID(BossID);
                    List<Category> mCategories = new ArrayList<>();
                    mCategories.add(categories.get(postion));
                    mBossCategory.setmCategorytList(mCategories);
                    mBossCategoryRepository.InsertBossCategory(mBossCategory, new BossCategoryRepository.getDataCallBack() {
                        @Override
                        public void CallBackSuccess(BossCategory mBossCategory) {
//                            Intent intent=new Intent(CategoryListActivity.this, BossDetailActivity.class);
////                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void CallBackFail(String message) {

                        }
                    });
                }
            });
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
