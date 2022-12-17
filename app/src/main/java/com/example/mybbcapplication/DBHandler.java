package com.example.myfinalapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mybbcapplication.Todo;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "newsData";
    private static final String TABLE_NEWS = "News";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";

    public DBHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Any older table is dropped if it exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new News Details
    public void addNews(Todo newsModel){
        //Data Repository is fetched in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //A new map of values is created where column names are the keys
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, newsModel.getTitle());
        cv.put(KEY_DESCRIPTION, newsModel.getDescription());
        cv.put(KEY_DATE, newsModel.getDate());

        //inserting data to particular table
        db.insert(TABLE_NEWS, null, cv);
        db.close();
    }

    //Select record from particular table
    public List<Todo> getAllNews(){
        //Fetch in table format and return in list format
        List<Todo> newsList = new ArrayList<>();

        //query for selecting
        String selectQuery = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase db = this.getWritableDatabase();

        //cursor fetching data
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Todo newsModel = new Todo();
                //storing database
                newsModel.setId(Integer.parseInt(cursor.getString(0)));
                newsModel.setTitle(cursor.getString(1));
                newsModel.setDescription(cursor.getString(2));
                newsModel.setDate(cursor.getString(3));
                newsList.add(newsModel);
            } while (cursor.moveToNext());
        }
        return newsList;
    }

    //function to fetch particular record for id
    public Todo getNews(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NEWS + " WHERE " + KEY_ID + "=" + id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor != null)
            cursor.moveToFirst();

        Todo newsModel = new Todo(Integer.parseInt(cursor.getString(0))
                , cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return newsModel;
    }

    //update record
    public int updateNews(Todo newsModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE, newsModel.getTitle());
        cv.put(KEY_DESCRIPTION, newsModel.getDescription());
        cv.put(KEY_DATE, newsModel.getDate());

        return db.update(TABLE_NEWS, cv, KEY_ID
                + " = ?", new String[]{String.valueOf(newsModel.getId())});
    }

    //Delete record
    public void deleteNews(Todo newsModel){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS, KEY_ID + " = ?", new String[]{String.valueOf(newsModel.getId())});
        db.close();
    }
}
