package mboss.tsm.mboss;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mboss.tsm.Fragment.MyBossesFragment;
import mboss.tsm.Model.Boss;
import mboss.tsm.RecyclerViewAdapter.CustomGenderSpiner;
import mboss.tsm.Repository.BossRepository;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
//private String destinationFileName = "cropImage.png";
public class AddNewBossActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner mSpinnerOption;
    private Spinner mSpinnerGender;
    private ImageView imageView;
    private Uri resultUri;
    private EditText mEditTextName, mBossAge, mBossWeight;
    private Boss newBoss;
    private Button mBtnAddBoss;
    private int[] spinnerImages;
    private ImageButton back;
    private String[] listGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_boss);
        iniatialView();
        iniatialData();
    }

    private  void  iniatialData(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessImageLibrary();
            }
        });
        mBtnAddBoss.setOnClickListener(AddNewBossActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }

    private  void iniatialView(){
        mSpinnerOption = findViewById(R.id.species_spinner);
        mSpinnerGender = findViewById(R.id.gender_spinner);
        imageView = findViewById(R.id.image_avata);
        mEditTextName = findViewById(R.id.edtName);
        mBtnAddBoss = findViewById(R.id.button_add);
        mBossAge=findViewById(R.id.addAge);
        mBossWeight=findViewById(R.id.addWeight);
        List<String> listOption = new ArrayList<>();
        listOption.add("Loại thú cưng");
        listOption.add("Chó");
        listOption.add("Mèo");
        listOption.add("Chim");
        listOption.add("Sóc");
        listOption.add("Loại khác");

        List<String> listGender = new ArrayList<>();
        listGender.add("Giới tính");
        listGender.add("Cái");
        listGender.add("Đực");
        spinnerImages = new int[]{R.mipmap.female,R.mipmap.male};
        ArrayAdapter<String> adapterOption = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listOption){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView,  @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                    tv.setPadding(38,0,0,0);
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38,0,0,0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                }
                return view;
            }
        };
        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOption.setAdapter(adapterOption);
        mSpinnerOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                    tv.setPadding(38,0,0,0);
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38,0,0,0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> adapterGender= new ArrayAdapter(this, android.R.layout.simple_spinner_item, listGender){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position,  @Nullable View convertView,  @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                    tv.setPadding(38,0,0,0);
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38,0,0,0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                }
                return view;
            }
        };
        //  CustomGenderSpiner mCustomAdapter = new CustomGenderSpiner(spinnerImages, listGender, AddNewBossActivity.this);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGender.setAdapter(adapterGender);
        mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                    tv.setPadding(38,0,0,0);

                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38,0,0,0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14f);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back = findViewById(R.id.btn_back);

    }


    public void accessImageLibrary() {
        EasyImage.openGallery(AddNewBossActivity.this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
            imageView.setImageDrawable(null);
            imageView.setImageURI(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, AddNewBossActivity.this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                UCrop uCrop = UCrop.of(Uri.fromFile(imageFiles.get(0)), Uri.fromFile(new File(getCacheDir(), String.valueOf(UUID.randomUUID()+".png"))));
                uCrop.withAspectRatio(1, 1);
                uCrop.withMaxResultSize(1000, 1000);
                uCrop = advancedOptions(uCrop);
                uCrop.start(AddNewBossActivity.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
            }
        });
    }

    private UCrop advancedOptions(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.withMaxResultSize(1000, 1000);
        return uCrop.withOptions(options);
    }

    private void clickToAddBoss(){
        newBoss = new Boss();
        if(mSpinnerOption.getSelectedItemPosition()>0){
            newBoss.setSpecies(mSpinnerOption.getSelectedItem().toString());
        }
        if(mSpinnerGender.getSelectedItemPosition() == 0) {
        }
        else if(mSpinnerGender.getSelectedItemPosition()== 1) {
            newBoss.setGender("Cái");
        }
        else {
            newBoss.setGender("Đực");
        }
        newBoss.setBossName(mEditTextName.getText().toString().toUpperCase());
        newBoss.setPictures(resultUri.toString());
        newBoss.setBossAge(Integer.parseInt(mBossAge.getText().toString()));
        newBoss.setBossWeight(Float.parseFloat(mBossWeight.getText().toString()));
        BossRepository bossRepository = new BossRepository(AddNewBossActivity.this);
        bossRepository.InsertBoss(newBoss);
//        intentToBossList();
        Intent intent = new Intent();
        intent.putExtra("mBoss", newBoss);
        setResult(RESULT_OK, intent);
        finish();


    }
//    private  void intentToBossList(){
//        Intent intent = new Intent(AddNewBossActivity.this,MainActivity.class);
//        startActivity(intent);
//        finishActivity(1);
//    }

    @Override
    public void onClick(View v) {
        int pos  = v.getId();
        switch (pos){
            case R.id.button_add:
                clickToAddBoss();
                break;
        }
    }
    private boolean validateName(){
        String inputName=mEditTextName.getEditableText().toString().trim();
        if(inputName.isEmpty()){
            mEditTextName.setError("Tên không được để trống");
            return false;
        }
        mEditTextName.setError(null);
        return true;

    }
}
