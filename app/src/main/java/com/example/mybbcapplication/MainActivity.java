package com.example.mybbcapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static String TITLE = "TILE";
    public static String DESCRIPTION = "DESCRIPTION";
    public static String DATE = "DATE";

    com.example.myfinalapplication.DBHandler db;
    com.example.myfinalapplication.NewsAdapter newsAdapter;
    ArrayList<Todo> newsModelArrayList;
    Button add_btn;
    ListView listView;

    private ArrayList<Todo> myTodoList = new ArrayList<Todo>();
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AddBtn = findViewById(R.id.addBtn);
        //Initialize database
        db = new com.example.myfinalapplication.DBHandler(this);
        //Fetch data from database
        newsModelArrayList = (ArrayList<Todo>) db.getAllNews();
        //Setting data to adapter
        newsAdapter = new com.example.myfinalapplication.NewsAdapter(this, newsModelArrayList,db);
        //setting adapter to list
        listView.setAdapter(newsAdapter);

        Intent nextPage = new Intent(this, com.example.myfinalapplication.AddNews.class);

        //Clicking add button activating listener to add new data
        add_btn.setOnClickListener(click -> startActivity(nextPage));


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new myListFragment()).commit();
        }


        // toolbar layout instance to toggle the menu items
        Toolbar tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);
        // drawer layout instance to toggle the menu icon to open drawer and back button to close drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,tBar, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private class MyListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return myTodoList.size();
        }

        @Override
        public Object getItem(int i) {
            return myTodoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View newView = old;
            if (newView == null){
                newView = inflater.inflate(R.layout.row_layout, parent, false);
            }
            TextView rowText = newView.findViewById(R.id.textTitle);
            rowText.setText(myTodoList.get(i).getTitle());
            return newView;
        }
    }

    // Declare the onBackPressed method when the back button is pressed this method will call
    @Override
    public void onBackPressed() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finish();
        });

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        String message = null;
        switch (item.getItemId()){
            case R.id.home:
                message = "Home page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.favorites:
                message = "Favorites page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.about:
                message = "About page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.help:
                message = "Help Page";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                alertDialog.setTitle("Help");
                alertDialog.setMessage("1. The home icon in toolbar takes to home page where you can see all the news." + "\n"
                        + "2. The favorites icon in toolbar takes to favorites page where you can add your favorite news." + "\n");

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        //Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home){

        } else if (id == R.id.nav_favorites){

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_help){

        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}