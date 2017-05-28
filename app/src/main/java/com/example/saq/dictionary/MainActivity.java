package com.example.saq.dictionary;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private NavigationView nv;
    private Toolbar tb;
    private DictDatabase db;
    private ListView lv;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ArrayAdapter aa;
    private TextView tv;
    SharedPreferences prefs;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = (Toolbar) this.findViewById(R.id.my_toolbar);
        tb.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Word Addict");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        db=new DictDatabase(this);
        prefs=this.getSharedPreferences("State",MODE_PRIVATE);
        if(prefs.getBoolean("First_time_run",true)) {
            boolean ins = db.insertword("Platitude",
                    "a flat, dull, or trite remark, especially one uttered as if it were fresh or profound.");

            boolean ins2 = db.insertword("Swear",
                    "to make a solemn declaration or affirmation by some sacred being or object, as a deity or the Bible.");

            boolean ins3 = db.insertword("Rap",
                    "to strike, especially with a quick, smart, or light blow");
        }

        lv=(ListView)this.findViewById(R.id.list);
        lv.setVisibility(View.INVISIBLE);
        dl = (DrawerLayout) this.findViewById(R.id.dl);
        nv = (NavigationView) this.findViewById(R.id.nav_view);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,dl,tb,R.string.Open,R.string.Close);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                dl.closeDrawers();
                if (item.getItemId() == R.id.add) {
                    Intent in = new Intent("com.example.saq.dictionary.AddwordActivity");
                    startActivity(in);
                }
                return true;

            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem mi = menu.findItem(R.id.search);
        SearchView sv=(SearchView) MenuItemCompat.getActionView(mi);
        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DictDatabase db=new DictDatabase(getApplicationContext());
                ArrayList<String> suggestions=db.fetchallwords();
                aa=new ArrayAdapter<String>(getApplication(),R.layout.description_textviews,R.id.suggesttext_view
                        ,suggestions);
                lv.setAdapter(aa);
                lv.setVisibility(View.VISIBLE);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (id == R.id.suggesttext_view) {
                    tv = (TextView) findViewById(R.id.suggesttext_view);
                    String word = tv.getText().toString();
                }
                lv.setVisibility(View.INVISIBLE);
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
            public boolean onQueryTextSubmit(String query) {

                Snackbar.make(dl,query,Snackbar.LENGTH_LONG).show();
                lv.setVisibility(View.INVISIBLE);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                aa.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
     }

    protected void onStop(){
        super.onStop();
        prefs=this.getSharedPreferences("State",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putBoolean("First_time_run",false);
        editor.commit();
    }

}
