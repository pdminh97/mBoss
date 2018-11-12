package mboss.tsm.mboss;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Fragment.MyBossesFragment;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
import mboss.tsm.RecyclerViewAdapter.CategoryListRecyclerViewAdapter;
import mboss.tsm.Repository.BossCategoryRepository;
import mboss.tsm.Repository.CategoryRepository;


public class CategoryListActivity extends AppCompatActivity {
    private List<Category> categories;
    private ImageButton back;
    private RecyclerView mRecyclerView;
    private CategoryListRecyclerViewAdapter mAdapter;
    private int BossID;
    private Category category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        getDataIntentToBossDetail();
        iniatialView();
        initialData();
    }

    private void getDataIntentToBossDetail() {
        Bundle intent = getIntent().getExtras();
        BossID = intent.getInt(BossDetailFragment.CATEGORY);
    }

    private void iniatialView() {
        mRecyclerView = findViewById(R.id.rvCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CategoryListActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        back = findViewById(R.id.btn_back);
    }

    private void initialData() {
        categories = new ArrayList<>();
        CategoryRepository categoryRepository = new CategoryRepository(getApplicationContext());
        categoryRepository.getCategories(new CategoryRepository.DataCallBack() {
            @Override
            public void CallBackSuccess(List<Category> categoriesCallback) {
                categories = categoriesCallback;
                update();
            }

            @Override
            public void CallBackFail(String message) {

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
        if (mAdapter == null) {
            mAdapter = new CategoryListRecyclerViewAdapter(CategoryListActivity.this, categories);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setItemOnListenner(new CategoryListRecyclerViewAdapter.OnItemListener() {
                @Override
                public void OnClickItemListener(int postion) {
                    BossCategoryRepository mBossCategoryRepository = new BossCategoryRepository(CategoryListActivity.this);
                    BossCategory mBossCategory = new BossCategory();
                    mBossCategory.setBossID(BossID);
                    List<Category> mCategories = new ArrayList<>();
                    mCategories.add(categories.get(postion));
                    mBossCategory.setmCategorytList(mCategories);
                    mBossCategoryRepository.InsertBossCategory(mBossCategory, new BossCategoryRepository.getDataCallBack() {
                        @Override
                        public void CallBackSuccess(BossCategory mBossCategory) {
                            finish();
                        }

                        @Override
                        public void CallBackFail(String message) {

                        }
                    });
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

}
