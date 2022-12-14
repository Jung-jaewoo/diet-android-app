package com.example.mydietandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MealDBManager extends SQLiteOpenHelper {
    static final String MEAL_DB = "Meal.db";
    static final String MEAL_TABLE = "Meals";
    Context context = null;
    private static MealDBManager dbManager = null;

    // 음식 사진과 식사 장소도 추가해야함.
    static final String CREATE_DB = " CREATE TABLE " + MEAL_TABLE +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name TEXT NOT NULL, meal_count INTEGER NOT NULL," +
            " review TEXT NOT NULL, meal_date TEXT NOT NULL, meal_time TEXT NOT NULL, image_uri TEXT NOT NULL, address TEXT NOT NULL);";

    public static MealDBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new MealDBManager(context, MEAL_DB, null, 1);
        }
        return dbManager;
    }

    public MealDBManager(Context context, String dbName, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, dbName, factory, version);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create: " + CREATE_DB  );
        db.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

    }

    public long insert(ContentValues addValue) {
        return getWritableDatabase().insert(MEAL_TABLE, null, addValue);
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(MEAL_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return getWritableDatabase().delete(MEAL_TABLE, whereClause,
                whereArgs);
    }
}

