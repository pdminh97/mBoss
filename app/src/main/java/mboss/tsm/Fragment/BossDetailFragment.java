package mboss.tsm.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
import mboss.tsm.RecyclerViewAdapter.BossCategoryListRecyclerViewAdapter;
import mboss.tsm.Repository.BossCategoryRepository;
import mboss.tsm.mboss.CategoryListActivity;
import mboss.tsm.mboss.DateListActivity;
import mboss.tsm.mboss.EditBossActivity;
import mboss.tsm.mboss.R;

public class BossDetailFragment extends Fragment {
    private Boss mBoss;
    private TextView tvNameDetail;
    private TextView tvGenderDetail;
    private TextView tvSpeciesDetail;
    private ImageView imageDetail;
    private ImageButton imCreate;
    private Button btnEdit;
    private BossCategoryListRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<Category> bossCategories;
    public static final String TITLE = "Title";
    public static final String CATEGORY = "Category";
    private int BossId;
    public static final String BUNDLE_EDIT_DATA = "BUNDLE_EDIT";
    private ImageButton back;
    private TextView emptyView;
    private ImageView emptyImage;
    private FrameLayout frameLayoutDetail;
    private TextView tvAgeDetail;
    private TextView tvWeightDetail;
    private LinearLayout update;
    private TextView title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boss_detail, container, false);
        initialView(view);
        iniatialData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromRoom();
    }

    private void initialView(View view) {
        emptyView = view.findViewById(R.id.empty_message_category);
        emptyImage = view.findViewById(R.id.empty_image_category);
        frameLayoutDetail = view.findViewById(R.id.frameLayoutDetail);
        update = view.findViewById(R.id.update);
        back = view.findViewById(R.id.btn_back);
        tvNameDetail = view.findViewById(R.id.tvName_detail);
        tvSpeciesDetail = view.findViewById(R.id.tvSpecies_detail);
        tvGenderDetail = view.findViewById(R.id.tvGender_detail);
        imageDetail = view.findViewById(R.id.image_detail);
        imCreate = view.findViewById(R.id.imCreate);
        btnEdit = view.findViewById(R.id.btnEditBoss);
        mRecyclerView = view.findViewById(R.id.rvBossCategory);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        tvAgeDetail = view.findViewById(R.id.tvAge_detail);
        tvWeightDetail = view.findViewById(R.id.tvWeight_detail);
        title = view.findViewById(R.id.txt_title);
    }

    private void iniatialData() {
        Bundle bundle = getArguments();
        mBoss = (Boss) bundle.getSerializable(MyBossesFragment.BOSSES);
//        mBoss = (Boss)intent.getSerializableExtra(BossListActivity.BOSSES);
//        Log.e("hehe", "name: "+ mBoss.getBossName() + " gender: " +mBoss.getGender()+ " special: "+ mBoss.getSpecies() +"Pic: "+ mBoss.getPictures());
        title.setText(mBoss.getBossName());
        if(mBoss!=null) {
            tvGenderDetail.setText(mBoss.getGender());
            tvNameDetail.setText(mBoss.getBossName());
            tvSpeciesDetail.setText(mBoss.getSpecies());
            imageDetail.setImageURI(Uri.parse(mBoss.getPictures()));
            tvAgeDetail.setText(mBoss.getBossAge().toString());
            tvWeightDetail.setText(Math.round(mBoss.getBossWeight()) + "Kg");
        }
        imCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentToCategoryList(mBoss);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDataToEditActivity(mBoss);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDataToEditActivity(mBoss);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               onBackPressed();
                MyBossesFragment myBossesFragment = new MyBossesFragment();
                getFragmentManager().beginTransaction().add(R.id.boss_detail_fragment_container, myBossesFragment).commit();
            }
        });

    }

    private void intentDataToEditActivity(Boss boss) {
        Intent intent = new Intent(getActivity(), EditBossActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_EDIT_DATA, boss);
        intent.putExtras(bundle);
//        startActivity(intent);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void intentToCategoryList(Boss boss) {
        Intent intent = new Intent(getActivity(), CategoryListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY, boss.getBossID());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void intentDateListFragment(Category bossCategory, int postion) {

        Intent intent = new Intent(getActivity(), DateListActivity.class);
        Bundle bundle = new Bundle();
        String bossName = mBoss.getBossName();
        bundle.putInt("Position", postion);
        bundle.putString("BossName", bossName);
        bundle.putSerializable(TITLE, bossCategory);
        bundle.putInt("ID", BossId);
        bundle.putSerializable("bundle", (Serializable) bossCategories);
        intent.putExtras(bundle);
//        bundle.putSerializable(TITLE, bossCategory);
//        intent.putExtras(bundle);
//        startActivity(intent);
        startActivityForResult(intent, 1);

    }

    public void update() {
        if (bossCategories.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
            frameLayoutDetail.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyImage.setVisibility(View.VISIBLE);
            frameLayoutDetail.setVisibility(View.GONE);

            if (mAdapter == null) {
                mAdapter = new BossCategoryListRecyclerViewAdapter(getActivity(), bossCategories);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setItemOnListenner(new BossCategoryListRecyclerViewAdapter.OnItemListener() {
                    @Override
                    public void OnClickItemListener(int postion) {
                        intentDateListFragment(bossCategories.get(postion), postion);

                    }
                });
            } else {
                mAdapter.setNotifySetChange(bossCategories);
            }
        }
    }

    private void getDataFromRoom() {
        if (mBoss != null) {
            BossCategoryRepository bossCategoryRepository = new BossCategoryRepository(getActivity());
            bossCategoryRepository.getAllBossCategory(mBoss.getBossID(), new BossCategoryRepository.DataCallBack() {
                @Override
                public void CallBackSuccess(List<BossCategory> mBossCategory) {
                    if (mBossCategory != null) {
                        bossCategories = new ArrayList<>();
                        for (int i = 0; i < mBossCategory.size(); i++) {
                            bossCategories.add(mBossCategory.get(i).getmCategorytList().get(0));
                            BossId = mBossCategory.get(i).getBossID();
                        }
                        update();
                    }
                }

                @Override
                public void CallBackFail(String message) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
