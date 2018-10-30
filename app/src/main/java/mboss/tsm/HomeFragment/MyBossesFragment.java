package mboss.tsm.HomeFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.RecyclerViewAdapter.BossListRecyclerViewAdapter;
import mboss.tsm.Repository.BossRepository;
import mboss.tsm.mboss.AddNewBossActivity;
import mboss.tsm.mboss.BossDetailActivity;
import mboss.tsm.mboss.R;

public class MyBossesFragment extends Fragment {
    private ImageButton imCreate;
    private RecyclerView mRecyclerView;
    private BossListRecyclerViewAdapter mAdapter;
    private List<Boss> mListBoss;
    public static final String BOSSES = "Bosses";

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
                startActivity(intent);
            }
        });

    }

    private void update() {
        if (mAdapter == null) {
            mAdapter = new BossListRecyclerViewAdapter(mListBoss);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setItemOnListenner(new BossListRecyclerViewAdapter.setOnIteamlistener() {
                @Override
                public void setOnIteamListener(int position) {
                    intentToDetail(mListBoss.get(position));
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void intentToDetail(Boss boss) {
        Intent intent = new Intent(getActivity(), BossDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BOSSES, boss);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
