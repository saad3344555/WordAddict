package com.example.saq.dictionary;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AddwordActivity extends AppCompatActivity {

    private Toolbar tb;
    private  DictDatabase db;
    private LinearLayout c;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        tb = (Toolbar) this.findViewById(R.id.my_toolbar);
        c=(LinearLayout) this.findViewById(R.id.layout);
        tb.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Word Addict");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextInputLayout t1=(TextInputLayout)findViewById(R.id.input1);
        final TextInputLayout t2=(TextInputLayout)findViewById(R.id.input2);
        final EditText ed1=(EditText)findViewById(R.id.add_word);
        final EditText ed2=(EditText)findViewById(R.id.add_description);
        Button b1=(Button)findViewById(R.id.add_button);
        Button b2=(Button)findViewById(R.id.home_button);
        b1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().isEmpty()){
                    t1.setErrorEnabled(true);
                    t1.setError("Enter the word");
                    return;
                }
                if(ed2.getText().toString().isEmpty()){
                    t2.setErrorEnabled(true);
                    t2.setError("Enter the description");
                    return;
                }

                if(ed1.getText().toString()!=null && ed2.getText().toString()!=null){
                    db=new DictDatabase(getApplicationContext());
                    boolean vars = db.insertword(ed1.getText().toString(), ed2.getText().toString());
                    if (vars) {
                        Snackbar.make(c,"Word Added Successfully",Snackbar.LENGTH_LONG).show();
                    }
                    if (!vars) {
                        Snackbar.make(c,"Failed to Add the word", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            this.finish();
        }

        return true;
    }
}
