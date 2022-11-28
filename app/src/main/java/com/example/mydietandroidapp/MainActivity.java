package com.example.mydietandroidapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
    }

    // 갤러리 여는 코드
    public void onClickGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    // 여기까지

    public void addMeal(View view) {

        // 음식 사진과 식사 장소 입력 부 추가해야함.
        ContentValues addValues = new ContentValues();
        addValues.put(MyContentProvider.NAME,
                ((EditText) findViewById(R.id.editText1)).getText().toString());
        addValues.put(MyContentProvider.MEAL_COUNT,
                Integer.parseInt(((EditText) findViewById(R.id.editText2)).getText().toString()));
        addValues.put(MyContentProvider.REVIEW,
                ((EditText) findViewById(R.id.editText3)).getText().toString());
        addValues.put(MyContentProvider.MEAL_TIME,
                Integer.parseInt(((EditText) findViewById(R.id.editText4)).getText().toString()));

//        if (imageUri != null) {
//            System.out.println("dssfd");
//            addValues.put(MyContentProvider.IMAGE_URI,
//                    imageUri.toString()
//            );
//        } else {
//            addValues.put(MyContentProvider.IMAGE_URI,
//                    "image");
//        }

        getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);

        Toast.makeText(getBaseContext(),
                "Record Added", Toast.LENGTH_LONG).show();
    }

    public void getStudents(View view) {
        String[] columns = new String[]{"_id", "name",
                "meal_count", "review", "meal_time"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI, columns, null,
                null, null, null);
        if (c != null) {
            EditText editMultipleText = findViewById(R.id.editText4);
            editMultipleText.setText("");
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                int meal_count = c.getInt(2);
                String review = c.getString(3);
                int meal_time = c.getInt(4);


                editMultipleText.append("id: " + id + "\n name: " + name + "\n meal_count: " +
                        meal_count + "\n review: " + review + "\n meal_time: " + meal_time
                );
            }
            editMultipleText.append("\n Total : " + c.getCount());
            c.close();
        }
    }
}