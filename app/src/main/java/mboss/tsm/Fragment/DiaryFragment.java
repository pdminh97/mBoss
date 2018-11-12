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
import android.util.Log;
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

import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Category;
import mboss.tsm.Model.Diary;
import mboss.tsm.Model.Tag;
import mboss.tsm.RecyclerViewAdapter.CategoryFilterRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.CategoryFilterSpinnerAdapter;
import mboss.tsm.RecyclerViewAdapter.DiaryRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.TagFilterSpinnerAdapter;
import mboss.tsm.RecyclerViewAdapter.TagedRecyclerViewAdapter;
import mboss.tsm.Repository.BossRepository;
import mboss.tsm.Repository.CategoryRepository;
import mboss.tsm.Repository.DiaryRepository;
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
    List<Boss> tags;
    RecyclerView rvtagFilter;
    TagedRecyclerViewAdapter tagAdapter;
    List<Boss> tagedList;
    Button btnDoneFilter;
    RecyclerView rvCategory;
    Spinner categoryFilter;
    CategoryFilterSpinnerAdapter categoryAdapter;
    List<Category> categoryList;
    CategoryFilterRecyclerViewAdapter categoryFilterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        rvDiary = view.findViewById(R.id.rvDiary);
        loadDiaryData();

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
        initTagFilterData();


        rvCategory = view.findViewById(R.id.rvCategoryFilter);
        categoryFilter = view.findViewById(R.id.categoryFilter);
        categoryList = new ArrayList<>();
        categoryFilterAdapter = new CategoryFilterRecyclerViewAdapter(getContext(), categoryList);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(categoryFilterAdapter);
        initCategoryFilterData();

        btnDoneFilter = view.findViewById(R.id.btnDoneFilter);
        btnDoneFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List filterList = new ArrayList();
                if(!(DateUtils.isToday(calendar.getTimeInMillis()) && tagedList.size() == 0 && categoryList.size() == 0)) {
                    try {
                        for (Diary diary : diaries) {
                            if (calendar.getTime().after(dateFormat.parse(diary.getDiaryTime()))) {
                                if(tagedList.size() > 0 && categoryList.size() > 0) {
                                    List<Integer> tagedListID = new ArrayList<>();
                                    for (int i = 0; i < tagedList.size(); i++) {
                                        tagedListID.add(tagedList.get(i).getBossID());
                                    }
                                    List<Integer> diaryTagID = new ArrayList<>();
                                    for (int i = 0; i < diary.getTageds().size(); i++) {
                                        diaryTagID.add(diary.getTageds().get(i).getBossID());
                                    }

                                    List<Integer> categoryListID = new ArrayList<>();
                                    for (int i = 0; i < categoryList.size(); i++) {
                                        categoryListID.add(categoryList.get(i).getImage());
                                    }
                                    if (diaryTagID.containsAll(tagedListID) && categoryListID.contains(diary.getCategoryImage())) {
                                        filterList.add(diary);
                                    }
                                } else if (tagedList.size() > 0) {
                                    List<Integer> tagedListID = new ArrayList<>();
                                    for (int i = 0; i < tagedList.size(); i++) {
                                        tagedListID.add(tagedList.get(i).getBossID());
                                    }
                                    List<Integer> diaryTagID = new ArrayList<>();
                                    for (int i = 0; i < diary.getTageds().size(); i++) {
                                        diaryTagID.add(diary.getTageds().get(i).getBossID());
                                    }
                                    if (diaryTagID.containsAll(tagedListID)) {
                                        filterList.add(diary);
                                    }
                                } else if (categoryList.size() > 0) {
                                    List<Integer> categoryListID = new ArrayList<>();
                                    for (int i = 0; i < categoryList.size(); i++) {
                                        categoryListID.add(categoryList.get(i).getImage());
                                    }
                                    if (categoryListID.contains(diary.getCategoryImage())) {
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

    private void loadDiaryData() {

        diaries = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvDiary.setLayoutManager(linearLayoutManager);
        adapter = new DiaryRecyclerViewAdapter(getContext(), diaries, this);
        rvDiary.setAdapter(adapter);

        DiaryRepository diaryRepository = new DiaryRepository(getContext());
        diaryRepository.getDiary(new DiaryRepository.DataCallBack() {
            @Override
            public void CallBackSuccess(List<Diary> diariesCallback) {
                diaries.clear();
                diaries.addAll(diariesCallback);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
    }

    private void initCategoryFilterData() {
        CategoryRepository categoryRepository = new CategoryRepository(getContext());
        categoryRepository.getCategories(new CategoryRepository.DataCallBack() {
            @Override
            public void CallBackSuccess(final List<Category> categories) {
                categoryAdapter = new CategoryFilterSpinnerAdapter(categories, getContext());
                categoryFilter.setAdapter(categoryAdapter);
                categoryFilter.setSelection(0);
                categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            categoryList.clear();
                            categoryFilterAdapter.notifyDataSetChanged();
                        } else {
                            if (categoryList.contains(categoryFilter.getSelectedItem())) {
                                categoryList.remove(categoryFilter.getSelectedItem());
                                categoryFilterAdapter.notifyDataSetChanged();
                            } else {
                                categoryList.add(categories.get(position));
                                categoryFilterAdapter.notifyItemInserted(categories.size() - 1);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
    }

    private void initTagFilterData() {
        BossRepository bossRepository = new BossRepository(getActivity());
        bossRepository.getAllBosses(new BossRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<Boss> mBosses) {
                tags = mBosses;
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
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
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

    private void showAddNewDiary(Bundle bundle) {
        diaryAddNewFragment = new DiaryAddNewFragment();
        diaryAddNewFragment.setArguments(bundle);
        diaryAddNewFragment.setTargetFragment(this, 1);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .replace(R.id.diary_add_new_fragment, diaryAddNewFragment)
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
                linearLayoutManager.smoothScrollToPosition(rvDiary, null, 0);
                DiaryRepository diaryRepository = new DiaryRepository(getContext());
                diaryRepository.insertDiary(diary);
            } else if (resultCode == 2) {
                Diary diary = (Diary) data.getParcelableExtra("diary");
                diaries.set(position, diary);
                adapter.notifyItemChanged(position);
                adapter.notifyDataSetChanged();
                modifyDiaryContainer.setVisibility(View.GONE);
                DiaryRepository diaryRepository = new DiaryRepository(getContext());
                diaryRepository.updateDiary(diary);
            }
        }
    }

    public void showModifyFragment(int position) {
        modifyDiaryContainer.setVisibility(View.VISIBLE);
        this.position = position;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(diaryAddNewFragment != null)
            diaryAddNewFragment.setTargetFragment(null, -1);
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}
