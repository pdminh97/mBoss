package mboss.tsm.mboss;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mboss.tsm.Fragment.BossDetailFragment;
import mboss.tsm.Model.Boss;
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
    private EditText edtAge;
    private Uri resultUri;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_boss);

        initialView();
        initialData();
        initialDataImage();
    }

    private void initialView() {

        edtAvata = findViewById(R.id.editImage_avata);
        edtSpinnerSpecies = findViewById(R.id.edtSpecies_spinner);
        edtName = findViewById(R.id.edtName);
        edtSpinnerGender = findViewById(R.id.edtGender_spinner);
        edtAge = findViewById(R.id.edtAge);
        edtWeight = findViewById(R.id.edtWeight);
        edtAvata = findViewById(R.id.editImage_avata);
        back = findViewById(R.id.btn_back);
        mBoss = new Boss();
        Intent intent = this.getIntent();
        mBoss = (Boss) intent.getSerializableExtra(BossDetailFragment.BUNDLE_EDIT_DATA);
        final List<String> listOption = new ArrayList<>();
        listOption.add("Loại thú cưng");
        listOption.add("Chó");
        listOption.add("Mèo");
        listOption.add("Loại khác");
        List<String> listGender = new ArrayList<>();
        listGender.add("Giới tính");
        listGender.add("Cái");
        listGender.add("Đực");
        //listGender=new String[]{"Giới tính","Nữ, Nam"};
        final ArrayAdapter<String> adapterOption = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listOption) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                    tv.setPadding(38, 0, 0, 0);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38, 0, 0, 0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                }
                return view;
            }
        };
        adapterOption.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtSpinnerSpecies.setAdapter(adapterOption);
        int idSpecies = listOption.indexOf(mBoss.getSpecies());
        edtSpinnerSpecies.setSelection(idSpecies);
        edtSpinnerSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                TextView tv = (TextView) view;
                if (pos == 0) {
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                    tv.setPadding(38, 0, 0, 0);

                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38, 0, 0, 0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> adapterGender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listGender) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                    tv.setPadding(38, 0, 0, 0);

                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38, 0, 0, 0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                }
                return view;
            }
        };
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // CustomGenderSpiner mCustomAdapter = new CustomGenderSpiner(spinnerImages,listGender,EditBossActivity.this);
        edtSpinnerGender.setAdapter(adapterGender);
        int idGender = listGender.indexOf(mBoss.getGender());
        edtSpinnerGender.setSelection(idGender);
        edtSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.gray));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                    tv.setPadding(38, 0, 0, 0);

                } else {
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setPadding(38, 0, 0, 0);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initialData() {

        edtAvata.setImageURI(Uri.parse(mBoss.getPictures()));
        edtName.setText(mBoss.getBossName().toUpperCase());
        edtAge.setText(mBoss.getBossAge().toString());
        edtWeight.setText(mBoss.getBossWeight().toString());
        resultUri = Uri.parse(mBoss.getPictures());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finishActivity(1);
                finish();
            }
        });


    }

    public void clickToUpdateBoss(View view) {
        mBoss.setBossName(edtName.getText().toString());
        mBoss.setBossAge(Integer.parseInt(edtAge.getText().toString()));
        mBoss.setBossWeight(Float.parseFloat(edtWeight.getText().toString()));
        mBoss.setPictures(resultUri.toString());
        mBoss.setSpecies(edtSpinnerSpecies.getSelectedItem().toString());
        mBoss.setGender(edtSpinnerGender.getSelectedItem().toString());
        BossRepository bossRepository = new BossRepository(EditBossActivity.this);
        bossRepository.updateBoss(mBoss, new BossRepository.OnCallBackData() {
            @Override
            public void onCallBackData(Boss boss) {
                //  Toast.makeText(EditBossActivity.this,"Sửa thanh Cong",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDataFail() {

            }
        });
//        Intent intent = new Intent(EditBossActivity.this,MainActivity.class);
//        startActivity(intent);


//        Intent intent1 = new Intent(EditBossActivity.this, BossDetailFragment.class);
//        intent1.putExtra("MBOSS", mBoss);
//        setResult(RESULT_OK, intent1);
        finishActivity(1);
//        finish();
    }

    public void initialDataImage() {
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
                UCrop uCrop = UCrop.of(Uri.fromFile(imageFiles.get(0)), Uri.fromFile(new File(getCacheDir(), String.valueOf(UUID.randomUUID() + ".png"))));
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
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(EditBossActivity.this);
        dialogDelete.setMessage("Bạn có chắc muốn xóa " + mBoss.getBossName().toUpperCase() + " không?");
        dialogDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BossRepository bossRepository = new BossRepository(EditBossActivity.this);
                bossRepository.deleteBoss(mBoss);
                Intent intent = new Intent(EditBossActivity.this, MainActivity.class);
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
