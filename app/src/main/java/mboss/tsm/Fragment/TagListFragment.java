package mboss.tsm.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Tag;
import mboss.tsm.RecyclerViewAdapter.TagListRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.TagingRecyclerViewAdapter;
import mboss.tsm.Repository.BossRepository;
import mboss.tsm.mboss.R;

import static android.app.Activity.RESULT_OK;

public class TagListFragment extends Fragment {
    private RecyclerView rvTagList;
    private Button btnCancelTag;
    private List<Boss> tagingList;
    private RecyclerView rvTaging;
    private Button btnSaveTag;
    private List<Boss> tagList;
    private TagingRecyclerViewAdapter tagingRecyclerViewAdapter;

    public TagListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tag_list_fragment, container, false);

        tagingList = new ArrayList<>();

        rvTaging = view.findViewById(R.id.rvTaging);
        tagingRecyclerViewAdapter = new TagingRecyclerViewAdapter(getContext(), tagingList);
        rvTaging.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvTaging.setAdapter(tagingRecyclerViewAdapter);

        getBossList();
        rvTagList = view.findViewById(R.id.rvTagList);



        btnCancelTag = view.findViewById(R.id.btnCancelTag);
        btnCancelTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTagList();
            }
        });

        btnSaveTag = view.findViewById(R.id.btnSaveTag);
        btnSaveTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("tagingList", (Serializable) tagingList);
                getTargetFragment().onActivityResult(2, RESULT_OK, intent);
                closeTagList();
            }
        });

        return view;
    }

    private void getBossList() {
        BossRepository bossRepository = new BossRepository(getActivity());
        bossRepository.getAllBosses(new BossRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<Boss> mBosses) {
                tagList = mBosses;
                TagListRecyclerViewAdapter adapter = new TagListRecyclerViewAdapter(tagList ,getContext(), tagingList, tagingRecyclerViewAdapter);
                rvTagList.setLayoutManager(new LinearLayoutManager(getContext()));
                rvTagList.setAdapter(adapter);
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
    }

    private void closeTagList() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .remove(this)
                .commit();
    }
}
