package mboss.tsm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import mboss.tsm.RecyclerViewAdapter.DiaryImageRecyclerViewAdapter;
import mboss.tsm.mboss.R;


public class DiaryFragment extends Fragment {
    TextView txtShowNewDiary;
    Fragment diaryAddNewFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
//        DiaryImageRecyclerViewAdapter adapter = new DiaryImageRecyclerViewAdapter();
//        RecyclerView recyclerView = view.findViewById(R.id.rvDiaryImage);
//        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
//        recyclerView.setAdapter(adapter);

        txtShowNewDiary = view.findViewById(R.id.txtShowNewDiary);
        txtShowNewDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewDiary();
            }
        });

        return view;
    }

    private void showAddNewDiary() {
        Log.e("asd", "Vo");
        if(diaryAddNewFragment == null) {
            diaryAddNewFragment = new DiaryAddNewFragment();
        }
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .replace(R.id.diary_add_new_fragment, diaryAddNewFragment)
                .addToBackStack(null)
                .commit();
    }

}
