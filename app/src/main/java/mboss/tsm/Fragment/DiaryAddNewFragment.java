package mboss.tsm.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryAddNewFragment extends Fragment {
    private DiaryFragment diaryFragment;
    private Button btnCancel;
    private LinearLayout lnPhoto;

    public DiaryAddNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_add_new, container, false);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddNewDiary();
            }
        });

        lnPhoto = view.findViewById(R.id.lnPhoto);
        lnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });
        return view;
    }

    private void closeAddNewDiary() {
        Log.e("asd", "Vo");
        if(diaryFragment == null) {
            diaryFragment = new DiaryFragment();
        }
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_down, R.anim.add_diary_up)
                .replace(R.id.diary_add_new_fragment, diaryFragment)
                .addToBackStack(null)
                .commit();
    }

}
