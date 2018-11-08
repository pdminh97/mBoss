package mboss.tsm.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mboss.tsm.Model.Diary;
import mboss.tsm.Model.Tag;
import mboss.tsm.RecyclerViewAdapter.DiaryRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.TagFilterSpinnerAdapter;
import mboss.tsm.RecyclerViewAdapter.TagedRecyclerViewAdapter;
import mboss.tsm.mboss.R;


public class DiaryFragment extends Fragment {
    TextView txtShowNewDiary;
    Fragment diaryAddNewFragment;
    RecyclerView rvDiary;
    DiaryRecyclerViewAdapter adapter;
    List<Diary> diaries;
    LinearLayoutManager linearLayoutManager;
    FrameLayout modifyDiaryContainer;
    TextView removeDiary;
    TextView editDiary;
    int position = -1;
    LinearLayout lnFilterDiary;
    FrameLayout filterDiaryContainer;
    Button btnCancelFilter;
    TextView timeFilter;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    Spinner tagFilter;
    TagFilterSpinnerAdapter tagFilterAdapter;
    List<Tag> tags;
    RecyclerView rvtagFilter;
    TagedRecyclerViewAdapter tagAdapter;
    List<Tag> tagedList;
    Button btnDoneFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        diaries = new ArrayList<>();
        addFakeData();
        rvDiary = view.findViewById(R.id.rvDiary);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvDiary.setLayoutManager(linearLayoutManager);
        adapter = new DiaryRecyclerViewAdapter(getContext(), diaries, this);
        rvDiary.setAdapter(adapter);
        txtShowNewDiary = view.findViewById(R.id.txtShowNewDiary);
        txtShowNewDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewDiary(null);
            }
        });
        modifyDiaryContainer = view.findViewById(R.id.modifyDiaryContainer);
        modifyDiaryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDiaryContainer.setVisibility(View.GONE);
            }
        });
        removeDiary = view.findViewById(R.id.removeDiary);
        removeDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Bạn chắc chắn muốn xóa bài nhật ký này?")
                            .setCancelable(false)
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    diaries.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    modifyDiaryContainer.setVisibility(View.GONE);
                                }
                            })
                            .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        editDiary = view.findViewById(R.id.editDiary);
        editDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > -1) {
                    if (diaryAddNewFragment == null) {
                        diaryAddNewFragment = new DiaryAddNewFragment();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("editdiary", diaries.get(position));
                    showAddNewDiary(bundle);
                }
            }
        });

        filterDiaryContainer = view.findViewById(R.id.filterDiaryContainer);
        filterDiaryContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDiaryContainer.setVisibility(View.GONE);
            }
        });
        btnCancelFilter = view.findViewById(R.id.btnCancelFilter);
        btnCancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDiaryContainer.setVisibility(View.GONE);
            }
        });
        lnFilterDiary = view.findViewById(R.id.lnFilterDiary);
        lnFilterDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDiaryContainer.setVisibility(View.VISIBLE);
            }
        });
        timeFilter = view.findViewById(R.id.timeFilter);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timeFilter.setText(dateFormat.format(calendar.getTime()));
        timeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker();
            }
        });

        rvtagFilter = view.findViewById(R.id.rvtagFilter);
        tagedList = new ArrayList<>();
        tagAdapter = new TagedRecyclerViewAdapter(getContext(), tagedList);
        rvtagFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvtagFilter.setAdapter(tagAdapter);

        tagFilter = view.findViewById(R.id.tagFilter);
        tags = new ArrayList<>();
        tags.add(new Tag("cat_avt", "Chó"));
        tags.add(new Tag("dog_avt", "Mèo"));
        tagFilterAdapter = new TagFilterSpinnerAdapter(tags, getContext());
        tagFilter.setAdapter(tagFilterAdapter);
        tagFilter.setSelection(0);
        tagFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    tagedList.clear();
                    tagAdapter.notifyDataSetChanged();
                } else {
                    if (tagedList.contains(tagFilter.getSelectedItem())) {
                        tagedList.remove(tagFilter.getSelectedItem());
                        tagAdapter.notifyDataSetChanged();
                    } else {
                        tagedList.add(tags.get(position));
                        tagAdapter.notifyItemInserted(tagedList.size() - 1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnDoneFilter = view.findViewById(R.id.btnDoneFilter);
        btnDoneFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List filterList = new ArrayList();
                if(!(DateUtils.isToday(calendar.getTimeInMillis()) && tagedList.size() == 0)) {
                    try {
                        for (Diary diary : diaries) {
                            if (calendar.getTime().after(dateFormat.parse(diary.getDiaryTime()))) {
                                if(tagedList.size() > 0) {
                                    List<String> tagedListName = new ArrayList<>();
                                    for (int i = 0; i < tagedList.size(); i++) {
                                        tagedListName.add(tagedList.get(i).getName());
                                    }
                                    List<String> diaryTagName = new ArrayList<>();
                                    for (int i = 0; i < diary.getTageds().size(); i++) {
                                        diaryTagName.add(diary.getTageds().get(i).getName());
                                    }
                                    if (diaryTagName.containsAll(tagedListName)) {
                                        filterList.add(diary);
                                    }
                                } else {
                                    filterList.add(diary);
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    filterList = diaries;
                }
                adapter = new DiaryRecyclerViewAdapter(getContext(), filterList, DiaryFragment.this);
                rvDiary.setAdapter(adapter);
                filterDiaryContainer.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void initDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                timeFilter.setText(dateFormat.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void addFakeData() {
        Diary diary1 = new Diary();
        List<Uri> uri1 = new ArrayList<>();
        uri1.add(Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.corgi_sea));
        uri1.add(Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.corgi_sea2));

        List<Tag> tag1 = new ArrayList<>();
        tag1.add(new Tag("dog_avt", "Chó"));
        diary1.setDiaryTime("29/10/2018");
        diary1.setContent("Dẫn Chó Đi Biển");
        diary1.setUriImages(uri1);
        diary1.setTageds(tag1);

        Diary diary2 = new Diary();
        List<Uri> uri2 = new ArrayList<>();
        uri2.add(Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.cat_sick));
        uri2.add(Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.meo_om));
        List<Tag> tag2 = new ArrayList<>();
        tag2.add(new Tag("cat_avt", "Mèo"));
        diary2.setDiaryTime("31/10/2018");
        diary2.setContent("Mèo Bị Bệnh Dòiiiiii");
        diary2.setUriImages(uri2);
        diary2.setTageds(tag2);

        diaries.add(diary2);
        diaries.add(diary1);
    }

    private void showAddNewDiary(Bundle bundle) {
        diaryAddNewFragment = new DiaryAddNewFragment();
        diaryAddNewFragment.setArguments(bundle);
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
            if (resultCode == 1) {
                Diary diary = (Diary) data.getParcelableExtra("diary");
                diaries.add(0, diary);
                adapter.notifyItemInserted(0);
                adapter.notifyDataSetChanged();
                linearLayoutManager.smoothScrollToPosition(rvDiary, null, 0);
            } else if (resultCode == 2) {
                Diary diary = (Diary) data.getParcelableExtra("diary");
                diaries.set(position, diary);
                adapter.notifyItemChanged(position);
                adapter.notifyDataSetChanged();
                modifyDiaryContainer.setVisibility(View.GONE);
            }
        }
    }

    public void showModifyFragment(int position) {
        modifyDiaryContainer.setVisibility(View.VISIBLE);
        this.position = position;
    }
}
