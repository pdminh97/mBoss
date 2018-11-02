package mboss.tsm.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Model.Diary;
import mboss.tsm.RecyclerViewAdapter.DiaryRecyclerViewAdapter;
import mboss.tsm.mboss.R;


public class DiaryFragment extends Fragment {
    TextView txtShowNewDiary;
    Fragment diaryAddNewFragment;
    RecyclerView rvDiary;
    DiaryRecyclerViewAdapter adapter;
    List<Diary> diaries;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        diaries = new ArrayList<>();
        addFakeData();
        rvDiary = view.findViewById(R.id.rvDiary);
        rvDiary.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DiaryRecyclerViewAdapter(diaries);
        rvDiary.setAdapter(adapter);
        txtShowNewDiary = view.findViewById(R.id.txtShowNewDiary);
        txtShowNewDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewDiary();
            }
        });

        return view;
    }

    private void addFakeData() {
        Diary diary1 = new Diary();
        Uri[] uri1 = new Uri[1];
        uri1[0] = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.corgi_sea);
        diary1.setDiaryTime("29/10/2018");
        diary1.setContent("Dẫn Mèo Đi Biển");
        diary1.setUriImages(uri1);

        Diary diary2 = new Diary();
        Uri[] uri2 = new Uri[1];
        uri2[0] = Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.cat_sick);
        diary2.setDiaryTime("31/10/2018");
        diary2.setContent("Chó Bị Bệnh Dòiiiiii");
        diary2.setUriImages(uri2);

        diaries.add(diary2);
        diaries.add(diary1);
    }

    private void showAddNewDiary() {
        if (diaryAddNewFragment == null) {
            diaryAddNewFragment = new DiaryAddNewFragment();
        }
        diaryAddNewFragment.setTargetFragment(this, 1);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .add(R.id.diary_add_new_fragment, diaryAddNewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Diary diary = (Diary) data.getSerializableExtra("diary");
            diaries.add(0, diary);
            adapter.notifyItemInserted(0);
        }
    }
}
