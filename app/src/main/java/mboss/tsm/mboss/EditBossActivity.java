package mboss.tsm.mboss;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Fragment.MyBossesFragment;
import mboss.tsm.Model.Boss;
import mboss.tsm.RecyclerViewAdapter.CustomGenderSpiner;
import mboss.tsm.Repository.BossRepository;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class EditBossActivity extends AppCompatActivity {
    public Boss mBoss;
    private Spinner edtSpinnerSpecies;
    private Spinner edtSpinnerGender;
    private ImageView edtAvata;
    private EditText edtName;
    private EditText edtWeight;
    private EditText edtColor;
    private List<String> item ;
    private Uri resultUri;
    private int[] spinnerImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_boss);
        initialView();
        initialData();
        initialDataImage();
    }
    private void initialView(){
        edtAvata=findViewById(R.id.editImage_avata);
        edtSpinnerSpecies=findViewById(R.id.edtSpecies_spinner);
        edtName=findViewById(R.id.edtName);
        edtSpinnerGender=findViewById(R.id.edtGender_spinner);
        edtColor=findViewById(R.id.edtColor);
        edtWeight=findViewById(R.id.edtWeight);
        edtAvata=findViewById(R.id.editImage_avata);

        final List<String> listOption = new ArrayList<>();
        listOption.add("Chó");
        listOption.add("Mèo");
        listOption.add("Khác");
        final List<String> listGender = new ArrayList<>();
        listGender.add("Đực");
        listGender.add("Cái");
        spinnerImages = new int[]{R.mipmap.female,R.mipmap.male};
        final ArrayAdapter<String> adapterOption = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listOption);
        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtSpinnerSpecies.setAdapter(adapterOption);
        edtSpinnerSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                for (int i = 0; i <adapterOption.getCount() ; i++) {
                    if(mBoss.getSpecies().equalsIgnoreCase(adapterOption.getItem(i))){
                        edtSpinnerSpecies.setSelection(i);
                    }
                }
            }
        });
        CustomGenderSpiner mCustomAdapter = new CustomGenderSpiner(spinnerImages,EditBossActivity.this);
        edtSpinnerGender.setAdapter(mCustomAdapter);

    }

    private  void initialData() {
        mBoss = new Boss();
        Intent intent = this.getIntent();
        mBoss= (Boss) intent.getSerializableExtra(BossDetailFragment.BUNDLE_EDIT_DATA);
//        Bundle bundle = intent.getExtras();
//        mBoss = (Boss) bundle.getSerializable("Data");
//        Bundle bundle = getIntent().getBundleExtra("bundle");
//        String name = bundle.getString("Name");
//        String species=bundle.getString("Species");
//        String gender=bundle.getString("Gender");
//        String picture=bundle.getString("Picture");
//        String color=bundle.getString("Color");
//        int weight=bundle.getInt("Weight");
//        item = new ArrayList<>();
//        item.add("Đực");
//        item.add("Cái");
//        ArrayAdapter<String> adapterOption = new ArrayAdapter(this, android.R.layout.simple_spinner_item, item);
//        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        edtSpinnerGender.setAdapter(adapterOption);
//        edtSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//        for (int position = 0; position < item.size(); position++) {
//            if(item.get(position).equalsIgnoreCase(mBoss.getGender())) {
//                edtSpinnerGender.setSelection((int) adapterOption.getItemId(position));
//                return;
//            }
//        }

        edtAvata.setImageURI(Uri.parse(mBoss.getPictures()));
        edtName.setText(mBoss.getBossName().toUpperCase());
        edtColor.setText(mBoss.getColor());
        edtWeight.setText(mBoss.getBossWeight());

    }

    public void clickToUpdateBoss(View view) {
        mBoss.setBossName(edtName.getText().toString());
        mBoss.setBossWeight(edtWeight.getText().toString());
        mBoss.setColor(edtColor.getText().toString());
        BossRepository bossRepository = new BossRepository(EditBossActivity.this);
        bossRepository.updateBoss(mBoss, new BossRepository.OnCallBackData() {
            @Override
            public void onCallBackData(Boss boss) {
                Toast.makeText(EditBossActivity.this,"Thành Công",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDataFail() {

            }
        });
        Intent intent = new Intent(EditBossActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public  void initialDataImage(){
        edtAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessImageLibrary();
            }
        });

    }

    public void accessImageLibrary() {
        EasyImage.openGallery(EditBossActivity.this, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            resultUri = UCrop.getOutput(data);
            edtAvata.setImageDrawable(null);
            edtAvata.setImageURI(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, EditBossActivity.this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                UCrop uCrop = UCrop.of(Uri.fromFile(imageFiles.get(0)), Uri.fromFile(new File(getCacheDir(), String.valueOf(UUID.randomUUID()+".png"))));
                uCrop.withAspectRatio(1, 1);
                uCrop.withMaxResultSize(1000, 1000);
                uCrop = advancedOptions(uCrop);
                uCrop.start(EditBossActivity.this);
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

    public void clickToDeleteBoss(View view) {

        AlertDialog.Builder dialogDelete= new AlertDialog.Builder(EditBossActivity.this);
        dialogDelete.setMessage("Bạn có chắc muốn xóa "+ mBoss.getBossName().toUpperCase()+" không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BossRepository bossRepository = new BossRepository(EditBossActivity.this);
                bossRepository.deleteBoss(mBoss);
                Intent intent = new Intent(EditBossActivity.this,MainActivity.class);
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
}
