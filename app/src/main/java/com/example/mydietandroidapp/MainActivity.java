package com.example.mydietandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addMeal(View view) {

        // 음식 사진과 식사 장소 입력 부 추가해야함.
        ContentValues addValues = new ContentValues();
        addValues.put(MyContentProvider.NAME,
                ((EditText)findViewById(R.id.editText1)).getText().toString());
        addValues.put(MyContentProvider.MEAL_COUNT,
                Integer.parseInt(((EditText)findViewById(R.id.editText2)).getText().toString()));
        addValues.put(MyContentProvider.REVIEW,
                ((EditText)findViewById(R.id.editText3)).getText().toString());
        addValues.put(MyContentProvider.MEAL_TIME,
                Integer.parseInt(((EditText)findViewById(R.id.editText4)).getText().toString()));

        getContentResolver().insert(MyContentProvider.CONTENT_URI,addValues);

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
                        meal_count + "\n review: " + review + "\n meal_time: " + meal_time);
            }
            editMultipleText.append("\n Total : " + c.getCount());
            c.close();
        }
    }
}