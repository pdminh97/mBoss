package mboss.tsm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mboss.tsm.RecyclerViewAdapter.DiaryImageRecyclerViewAdapter;
import mboss.tsm.mboss.R;


public class DiaryFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        DiaryImageRecyclerViewAdapter adapter = new DiaryImageRecyclerViewAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.rvDiaryImage);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        recyclerView.setAdapter(adapter);
        return view;
    }

}
