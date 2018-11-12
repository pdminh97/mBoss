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
import mboss.tsm.Model.Category;
import mboss.tsm.RecyclerViewAdapter.DiaryCategoryRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.TagListRecyclerViewAdapter;
import mboss.tsm.RecyclerViewAdapter.TagingRecyclerViewAdapter;
import mboss.tsm.Repository.BossRepository;
import mboss.tsm.Repository.CategoryRepository;
import mboss.tsm.mboss.R;

import static android.app.Activity.RESULT_OK;

public class CategoryListFragment extends Fragment {
    private RecyclerView rvCategory;
    private Category selectedCategory;
    private Button btnSave;
    private Button btnCancel;
    private DiaryCategoryRecyclerViewAdapter adapter;

    public CategoryListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list_fragment, container, false);

        rvCategory = view.findViewById(R.id.rvCategoryList);
        selectedCategory = new Category();
        getCategoryList();

        btnCancel = view.findViewById(R.id.btnCancelCategory);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCategoryList();
            }
        });

        btnSave = view.findViewById(R.id.btnSaveCategory);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Log.e("Er", selectedCategory.getName());
                intent.putExtra("selectedCategory", (Serializable) selectedCategory);
                getTargetFragment().onActivityResult(3, RESULT_OK, intent);
                closeCategoryList();
            }
        });

        return view;
    }

    private void getCategoryList() {
        CategoryRepository categoryRepository = new CategoryRepository(getActivity());
        categoryRepository.getCategories(new CategoryRepository.DataCallBack() {
            @Override
            public void CallBackSuccess(List<Category> categoriesCallback) {
                adapter = new DiaryCategoryRecyclerViewAdapter(categoriesCallback, getContext(), CategoryListFragment.this, rvCategory);
                rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
                rvCategory.setAdapter(adapter);
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
    }

    private void closeCategoryList() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.add_diary_up, R.anim.add_diary_down)
                .remove(this)
                .commit();
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
