package mboss.tsm.Fragment;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import mboss.tsm.Model.Diary;
import mboss.tsm.RecyclerViewAdapter.ImageRecyclerViewAdapter;
import mboss.tsm.mboss.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryAddNewFragment extends Fragment {
    private Button btnCancel;
    private Button btnSave;
    private EditText edtDiaryContent;
    private LinearLayout lnPhoto;
    //private LinearLayout lnTag;
    private int PICK_IMAGE_MULTIPLE = 1;
    private RecyclerView rvImage;
    private Uri[] imageUris;
    //private RecyclerView rvTaged;
    private TagListFragment tagListFragment;
    //private List<Tag> tagedList;

    public DiaryAddNewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_add_new, container, false);
        edtDiaryContent = view.findViewById(R.id.edtDiaryContent);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAddNewDiary();
            }
        });

        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String current = format.format(new Date());
                Diary diary = new Diary();
                diary.setDiaryTime(current);
                diary.setContent(edtDiaryContent.getText().toString());
                diary.setUriImages(imageUris);
                Intent intent = new Intent();
                intent.putExtra("diary", diary);
                getTargetFragment().onActivityResult(1, RESULT_OK, intent);
                closeAddNewDiary();
            }
        });

        lnPhoto = view.findViewById(R.id.lnPhoto);
        lnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });

//        lnTag = view.findViewById(R.id.lnTag);
//        lnTag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTagList();
//            }
//        });

        rvImage = view.findViewById(R.id.rvImagesAddNewDiary);


        //rvTaged = view.findViewById(R.id.rvTaged);
//        tagedList = new ArrayList<>();
//        TagedRecyclerViewAdapter adapter = new TagedRecyclerViewAdapter(getContext(), tagedList);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            //Select 1 image
            if (data.getData() != null) {
                imageUris = new Uri[1];
                imageUris[0] = data.getData();
            }

            if (clipData != null) {
                int imageCount = clipData.getItemCount();
                imageUris = new Uri[imageCount];
                for (int i = 0; i < imageCount; i++) {
                    imageUris[i] = clipData.getItemAt(i).getUri();
                }
            }

            if(imageUris != null) {
                ImageRecyclerViewAdapter adapter = new ImageRecyclerViewAdapter(imageUris);
                //rvImage.setLayoutManager(new GridLayoutManager(getContext(), 2));
                //rvImage.setLayoutManager(new LinearLayoutManager(getContext()));
                rvImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                rvImage.setAdapter(adapter);
            }
        }
    }

    private void closeAddNewDiary() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .remove(this)
                .commit();
    }

    private void showTagList() {
        if(tagListFragment == null) {
            tagListFragment = new TagListFragment();
        }

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .add(R.id.tag_boss_list_container, tagListFragment)
                .addToBackStack(null)
                .commit();
    }

}
