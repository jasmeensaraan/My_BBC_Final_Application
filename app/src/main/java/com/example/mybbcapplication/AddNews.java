package com.example.myfinalapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybbcapplication.MainActivity;
import com.example.mybbcapplication.R;
import com.example.mybbcapplication.Todo;

public class AddNews extends AppCompatActivity {

    EditText news_Title;
    EditText news_Description;
    EditText news_Date;
    Button btn_submit;
    com.example.myfinalapplication.DBHandler db;
    Todo newsModel;
    Boolean isEditMode = true;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_news);

        btn_submit = findViewById(R.id.submitBtn);
        news_Title = findViewById(R.id.title);
        news_Description = findViewById(R.id.description);
        news_Date = findViewById(R.id.date);

        Intent forwardPage = new Intent(this, MainActivity.class);
        forwardPage.putExtra("title", news_Title.getText().toString());
        forwardPage.putExtra("description", news_Description.getText().toString());
        forwardPage.putExtra("date", news_Date.getText().toString());
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("title", "");
        String s2 = sh.getString("description", "");
        String s3 = sh.getString("date", "");
        news_Title.setText(s1);
        news_Description.setText(s2);
        news_Date.setText(s3);
        //Initialize database
        db = new com.example.myfinalapplication.DBHandler(this);

        intent = getIntent();
        if(intent != null && intent.getStringExtra("Mode").equals("E")){
            isEditMode = true;
            btn_submit.setText("Update");
            newsModel = db.getNews(intent.getIntExtra("Id",0));
            news_Title.setText(newsModel.getTitle());
            news_Description.setText(newsModel.getDescription());
            news_Date.setText(newsModel.getDate());
        } else {
            isEditMode = false;
            btn_submit.setText("Insert");
        }

        //Clicking submit button activating listener to add new data
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode){
                    newsModel = new Todo(intent.getIntExtra("Id", 0)
                            , news_Title.getText().toString()
                            , news_Description.getText().toString()
                            , news_Date.getText().toString());
                    db.updateNews(newsModel);
                } else {
                    newsModel = new Todo(news_Title.getText().toString()
                            , news_Description.getText().toString()
                            , news_Date.getText().toString());
                    db.addNews(newsModel);
                }
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}
