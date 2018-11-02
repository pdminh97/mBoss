package mboss.tsm.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mboss.tsm.RecyclerViewAdapter.TagListRecyclerViewAdapter;
import mboss.tsm.mboss.R;

public class TagListFragment extends Fragment {
    private RecyclerView rvTagList;
    private Button btnCancelTag;
    public TagListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tag_list_fragment, container, false);
        rvTagList = view.findViewById(R.id.rvTagList);
        TagListRecyclerViewAdapter adapter = new TagListRecyclerViewAdapter(getContext());
        rvTagList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTagList.setAdapter(adapter);

        btnCancelTag = view.findViewById(R.id.btnCancelTag);
        btnCancelTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTagList();
            }
        });

        return view;
    }

    private void closeTagList() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .remove(this)
                .commit();
    }
}
