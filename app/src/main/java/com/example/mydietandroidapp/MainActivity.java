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
import android.util.Log;
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
    public static ArrayList<Meal> mealsInfo = new ArrayList<>();
    Button navigate_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);

        navigate_btn = (Button) findViewById(R.id.navigate_btn);
        navigate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.mydietandroidapp.LAUNCH");
                startActivity(intent);
            }
        });
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

    public void navigateMealList(View view) {
        Intent intent = new Intent(".MainActivity2");
        startActivity(intent);
    }


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

        if (imageUri != null) {

            addValues.put(MyContentProvider.IMAGE_URI,
                    imageUri.toString()
            );
        } else {
            addValues.put(MyContentProvider.IMAGE_URI,
                    "no image" );
        }
//        System.out.println(imageUri.toString());
        System.out.println(getPackageName());
        getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);

        imageUri = null;
        Toast.makeText(getBaseContext(),
                "Record Added", Toast.LENGTH_LONG).show();

    }

    public void getStudents(View view) {
        String[] columns = new String[]{"_id", "name",
                "meal_count", "review", "meal_time", "image_uri"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI, columns, null,
                null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String name = c.getString(1);
                int meal_count = c.getInt(2);
                String review = c.getString(3);
                int meal_time = c.getInt(4);
                String image_uri = c.getString(5);

                mealsInfo.add(new Meal(name, meal_count, review, meal_time, image_uri));
            }
            c.close();
        }
    }

}