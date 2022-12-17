package com.example.myfinalapplication;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybbcapplication.Todo;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    //Variables to be implemented
    private Activity context;
    ArrayList<Todo> news;
    com.example.myfinalapplication.DBHandler db;

    //Constructor
    public NewsAdapter(Activity context,ArrayList<Todo> news, com.example.myfinalapplication.DBHandler db){
        this.context = context;
        this.news = news;
        this.db = db;
    }

    //class to declare news_list items
    public static class Viewholder{
        TextView newsTitle;
        TextView newsDescription;
        TextView newsDate;
        ImageView imgEdit;
        ImageView imgDelete;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        LayoutInflater inflater = context.getLayoutInflater();
        Viewholder vh;
        if (view == null){
            vh = new Viewholder();
            row = inflater.inflate(R.layout.news_list, null, true);
            //Initialize objects
            vh.newsTitle = row.findViewById(R.id.news_title);
            vh.newsDescription = row.findViewById(R.id.news_description);
            vh.newsDate = row.findViewById(R.id.news_date);
            vh.imgEdit = row.findViewById(R.id.img_edit);
            vh.imgDelete = row.findViewById(R.id.img_delete);
            row.setTag(vh);
        } else {
            vh = (Viewholder) view.getTag();
        }

        //setting data
        vh.newsTitle.setText(news.get(i).getTitle());
        vh.newsDescription.setText(news.get(i).getDescription());
        vh.newsDate.setText(news.get(i).getDate());

        //Clicking update button activating listener
        vh.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, com.example.myfinalapplication.AddNews.class);
                intent.putExtra("Mode","E");
                intent.putExtra("Id", news.get(i).getId());
                context.startActivity(intent);
            }
        });

        //Clicking delete button activating listener
        vh.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNews(news.get(i));
                news = (ArrayList<Todo>) db.getAllNews();
                notifyDataSetChanged();
            }
        });

        return row;
    }
}
