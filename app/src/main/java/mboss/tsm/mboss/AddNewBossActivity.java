package mboss.tsm.mboss;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private EditText mEditTextName;
    private Boss newBoss;
    private Button mBtnAddBoss;
    private int[] spinnerImages;

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
    }

    private  void iniatialView(){
        mSpinnerOption = findViewById(R.id.species_spinner);
        mSpinnerGender = findViewById(R.id.gender_spinner);
        imageView = findViewById(R.id.image_avata);
        mEditTextName = findViewById(R.id.edtName);
        mBtnAddBoss = findViewById(R.id.button_add);
        List<String> listOption = new ArrayList<>();
        listOption.add("Chó");
        listOption.add("Mèo");
        listOption.add("Khác");
        List<String> listGender = new ArrayList<>();
        listGender.add("Đực");
        listGender.add("Cái");
        spinnerImages = new int[]{R.mipmap.female,R.mipmap.male};
        ArrayAdapter<String> adapterOption = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listOption);
        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOption.setAdapter(adapterOption);
        mSpinnerOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Toast.makeText(AddNewBossActivity.this, mSpinnerOption.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        CustomGenderSpiner mCustomAdapter = new CustomGenderSpiner(spinnerImages,AddNewBossActivity.this);
        mSpinnerGender.setAdapter(mCustomAdapter);
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
        newBoss.setSpecies(mSpinnerOption.getSelectedItem().toString());
        if(mSpinnerGender.getSelectedItemPosition() == 0){
            newBoss.setGender("Cái");
        }else {
            newBoss.setGender("Đực");
        }
        newBoss.setBossName(mEditTextName.getText().toString());
        newBoss.setPictures(resultUri.toString());
        BossRepository bossRepository = new BossRepository(AddNewBossActivity.this);
        bossRepository.InsertBoss(newBoss);
        intentToBossList();

    }
    private  void intentToBossList(){
        Intent intent = new Intent(AddNewBossActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int pos  = v.getId();
        switch (pos){
            case R.id.button_add:
                clickToAddBoss();
                break;
        }
    }
}
