package mboss.tsm.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.BossActivity;
import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
import mboss.tsm.RecyclerViewAdapter.DateListRecyclerViewAdapter;
import mboss.tsm.Repository.BossActivityRepository;
import mboss.tsm.Repository.BossCategoryRepository;
import mboss.tsm.mboss.AddDateActivity;
import mboss.tsm.mboss.DateListActivity;
import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateListFragment extends Fragment {

    private ImageButton imCreate;
    private ImageButton deleteCate;
    private RecyclerView mRecyclerView ;
    private DateListRecyclerViewAdapter adapter;
    private List<BossActivity> mBossActivity;
    private Category bossCategory;
    private TextView title;
    private int pos,BossID;
    private BossCategory mBossCategory;
    private List<Category> mCategories;
    private BossDetailFragment bossDetailFragment;
    public DateListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_date_list, container, false);
        iniatialView(view);
        iniatialData();
        return view;
    }

    private  void iniatialView(View view) {
        title = view.findViewById(R.id.txtTitleCategory);
        imCreate = (ImageButton) view.findViewById(R.id.imCreateDate);
        mRecyclerView = view.findViewById(R.id.rvDate);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        deleteCate = (ImageButton) view.findViewById(R.id.btn_delete_bosscategory);
    }
    private  void iniatialData(){
        Bundle bundle = getArguments();
        bossCategory = (Category) bundle.getSerializable(BossDetailFragment.TITLE);
        title.setText(bossCategory.getName());
        pos = bundle.getInt("Position");
        mCategories = (List<Category>) bundle.getSerializable("bundle");
        BossID = bundle.getInt("ID");
        BossActivityRepository bossActivityRepository= new BossActivityRepository(getActivity());
        bossActivityRepository.getAllDate(new BossActivityRepository.getDataCallBack() {
            @Override
            public void CallBackSuccess(List<BossActivity> bossActivities) {
                mBossActivity=bossActivities;
                update();
            }

            @Override
            public void CallBackFail(String message) {

            }
        });
        imCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   openDialog();
                Intent intent = new Intent(getActivity(), AddDateActivity.class);
                startActivity(intent);
            }
        });

        deleteCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogDelete= new AlertDialog.Builder(getContext());
                dialogDelete.setMessage("Bạn có chắc muốn xóa hoạt động này không?");
                dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // bossCategoryRepository.deleteBossCategory(bossCategory);
                        for (int i = 0; i <mCategories.size() ; i++) {
                            if(mCategories.get(i).getName().equalsIgnoreCase(bossCategory.getName())){
                                mCategories.remove(i);
                                BossCategoryRepository bossCategoryRepository = new BossCategoryRepository(getActivity());
                                mBossCategory = new BossCategory();
                                mBossCategory.setBossID(BossID);
                                mBossCategory.setmCategorytList(mCategories);
                                bossCategoryRepository.updateBoss(mBossCategory);
                            }
                        }

                        Intent intent = new Intent(getActivity(), BossDetailFragment.class);
                        startActivity(intent);
                    }
                });
                dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogDelete.show();
            }
        });
    }

//    public void openDialog(){
//        AddNewDateActivity activity=new AddNewDateActivity();
//        activity.show(getSupportFragmentManager(),"date dialog");
//
//    }

    private void update(){
        if(adapter == null){
            adapter = new DateListRecyclerViewAdapter(mBossActivity);
            mRecyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    public  void  DialogDeleteDate(){
        final AlertDialog.Builder dialogDelete= new AlertDialog.Builder(getContext());
        dialogDelete.setMessage("Bạn có chắc muốn xóa không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BossActivityRepository bossActivityRepository=new BossActivityRepository(getActivity());
//                        bossActivityRepository.deleteDate(bossActivity);
                Intent intent = new Intent(getActivity(), DateListActivity.class);
                startActivity(intent);
            }
        });
        dialogDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

//    public void clickToDeleteBossCategory(View view) {
//
//
//    }

    public BossDetailFragment getBossActivity() {
        return bossDetailFragment;
    }
}
