package mboss.tsm.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.RecyclerViewAdapter.BossListRecyclerViewAdapter;
import mboss.tsm.Repository.BossRepository;
import mboss.tsm.mboss.AddNewBossActivity;
import mboss.tsm.mboss.R;

public class MyBossesFragment extends Fragment {
    private ImageButton imCreate;
    private RecyclerView mRecyclerView;
    private BossListRecyclerViewAdapter mAdapter;
    private List<Boss> mListBoss;
    public static final String BOSSES = "Bosses";
    private TextView emptyView;
    private ImageView emptyImage;
    private FrameLayout frameLayout;
    private Boss mBoss;

    public MyBossesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bosses, container, false);
        iniatialView(view);
        iniatialData();

        return view;

    }

    private void iniatialView(View view) {
        imCreate = (ImageButton) view.findViewById(R.id.imCreateBoss);
        mRecyclerView = view.findViewById(R.id.rvBosses);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        emptyView = (TextView) view.findViewById(R.id.empty_item_message);
        emptyImage = view.findViewById(R.id.empty_item_image);
        frameLayout = view.findViewById(R.id.frameLayout);


    }

    private void iniatialData() {
        BossRepository bossRepository = new BossRepository(getActivity());
        bossRepository.getAllBosses(new BossRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<Boss> mBosses) {
                mListBoss = mBosses;
                update();
            }

            @Override
            public void CallBackFail(String message) {
            }
        });
        imCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNewBossActivity.class);
//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == AddNewBossActivity.RESULT_OK && data != null) {
                iniatialData();
        }
        iniatialData();
    }

    private void update() {
        if (mListBoss.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyImage.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);

            if (mAdapter == null) {
                mAdapter = new BossListRecyclerViewAdapter(mListBoss);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setItemOnListenner(new BossListRecyclerViewAdapter.setOnIteamlistener() {
                    @Override
                    public void setOnIteamListener(int position) {
                        showBossDetail(mListBoss.get(position));
                    }
                });

            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    private void showBossDetail(Boss boss) {
        BossDetailFragment bossDetailFragment = new BossDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOSSES, boss);
        bossDetailFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.boss_detail_fragment_container, bossDetailFragment).commit();
    }
}
