package mboss.tsm.Fragment;


import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Category;
import mboss.tsm.Model.Diary;
import mboss.tsm.Model.Tag;
import mboss.tsm.RecyclerViewAdapter.DiaryImageRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.TagedRecyclerViewAdapter;
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
    private LinearLayout lnTag;
    private LinearLayout lnCategory;
    private int PICK_IMAGE_MULTIPLE = 1;
    private RecyclerView rvImage;
    private List<String> imageUris;
    private RecyclerView rvTaged;
    private TagListFragment tagListFragment;
    private CategoryListFragment categoryListFragment;
    private CircleImageView civCategory;
    private Category selectedCategory;
    private List<Boss> tagedList;
    private Diary editedDiary;
    private TagedRecyclerViewAdapter tagAdapter;
    private DiaryImageRecyclerViewAdapter imageAdapter;

    public DiaryAddNewFragment() {
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
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
                if (editedDiary == null) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String current = format.format(new Date());
                    Diary diary = new Diary();
                    diary.setDiaryTime(current);
                    diary.setContent(edtDiaryContent.getText().toString());
                    diary.setUriImages(imageUris);
                    diary.setTageds(tagedList);
                    diary.setStatus(true);
                    if(selectedCategory != null)
                        diary.setCategoryImage(selectedCategory.getImage());
                    Intent intent = new Intent();
                    intent.putExtra("diary", diary);
                    getTargetFragment().onActivityResult(1, 1, intent);
                    closeAddNewDiary();
                } else {
                    editedDiary.setContent(edtDiaryContent.getText().toString());
                    editedDiary.setTageds(tagedList);
                    editedDiary.setUriImages(imageUris);
                    if(selectedCategory != null)
                        editedDiary.setCategoryImage(selectedCategory.getImage());
                    Intent intent = new Intent();
                    intent.putExtra("diary", editedDiary);
                    getTargetFragment().onActivityResult(1, 2, intent);
                    closeAddNewDiary();
                }
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
                startActivityForResult(Intent.createChooser(intent, "Chọn Hình"), PICK_IMAGE_MULTIPLE);
            }
        });

        lnTag = view.findViewById(R.id.lnTag);
        lnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTagList();
            }
        });

        lnCategory = view.findViewById(R.id.lnCategory);
        lnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryList();
            }
        });
        civCategory = view.findViewById(R.id.category_diary);

        rvImage = view.findViewById(R.id.rvImagesAddNewDiary);
        imageUris = new ArrayList<>();
        imageAdapter = new DiaryImageRecyclerViewAdapter(getContext(), imageUris);
        rvImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvImage.setAdapter(imageAdapter);


        rvTaged = view.findViewById(R.id.rvTaged);
        tagedList = new ArrayList<>();
        tagAdapter = new TagedRecyclerViewAdapter(getContext(), tagedList);
        rvTaged.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvTaged.setAdapter(tagAdapter);

        if(getArguments() != null) {
            editedDiary = (Diary) getArguments().get("editdiary");
            showEditDairy();
        } else {
            editedDiary = null;
            imageUris.clear();
            tagedList.clear();
        }

        return view;
    }

    private void showEditDairy() {
        if(editedDiary != null) {
            if(editedDiary.getUriImages() != null) {
                if(imageUris == null)
                    imageUris = new ArrayList<>();
                imageUris.clear();
                imageUris.addAll(editedDiary.getUriImages());
                imageAdapter.notifyDataSetChanged();
            }
            civCategory.setImageResource(editedDiary.getCategoryImage());
            edtDiaryContent.setText(editedDiary.getContent());
            tagedList.clear();
            tagedList.addAll(editedDiary.getTageds());
            tagAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK) {
            ClipData clipData = data.getClipData();
            //Select 1 image
            if (data.getData() != null) {
                imageUris.clear();
                imageUris.add(data.getData().toString());
            }

            if (clipData != null) {
                int imageCount = clipData.getItemCount();
                imageUris.clear();
                for (int i = 0; i < imageCount; i++) {
                    imageUris.add(clipData.getItemAt(i).getUri().toString());
                }
            }

            if(imageUris != null) {
                if(editedDiary != null) {
                    if(editedDiary.getUriImages() != null)
                        imageUris.addAll(editedDiary.getUriImages());
                }
                imageAdapter.notifyDataSetChanged();
            }
        }


        if(requestCode == 2) {
            tagedList.clear();
            tagedList.addAll((List<Boss>) data.getSerializableExtra("tagingList"));
            tagAdapter.notifyDataSetChanged();
        }

        if(requestCode == 3) {
            selectedCategory = (Category) data.getSerializableExtra("selectedCategory");
            if(selectedCategory != null)
                civCategory.setImageResource(selectedCategory.getImage());
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

        tagListFragment.setTargetFragment(this, 2);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .add(R.id.tag_boss_list_container, tagListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showCategoryList() {
        if(categoryListFragment == null) {
            categoryListFragment = new CategoryListFragment();
        }

        categoryListFragment.setTargetFragment(this, 3);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .add(R.id.tag_boss_list_container, categoryListFragment)
                .addToBackStack(null)
                .commit();
    }

}
