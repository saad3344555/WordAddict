package com.example.saq.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SAQ on 09/03/2017.
 */
public class DictDatabase extends SQLiteOpenHelper {

    private static final String Col1="SERIAL";
    private static final String Col2="WORD";
    private static final String Col3="DESCRIPTION";


    public DictDatabase(Context context) {

        super(context,"Words.db",null,1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Words(SERIAL AutoNumber, WORD Text, DESCRIPTION Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Words");
        onCreate(db);
    }

    public boolean insertword(String word, String desc){

        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DictDatabase.Col2,word);
        cv.put(DictDatabase.Col3,desc);

        long id = db.insert("Words",null,cv);

        if(id==-1){
            return false;
        }
        else
        return true;

    }

    public String getdesc(String word){

        SQLiteDatabase db=getReadableDatabase();
        String query="Select * from Words where "+Col2+" like '%"+word+"%'";
        String[] cols={Col2,Col3};
        Cursor c=db.rawQuery(query,null);
        String desc=c.getString(c.getColumnIndex(Col3)).toString();
        return desc;

    }

    public ArrayList<String> fetchallwords(){

        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String> suggestions=new ArrayList<String>();
        String query="Select WORD From Words";
        Cursor c=db.rawQuery(query,null);
        while(c.moveToNext()){
            suggestions.add(c.getString(c.getColumnIndex("WORD")));
        }
        c.close();
        return suggestions;
    }

    public void deleteAll(){

        String query="Delete from Words";
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(query);
        db.close();

    }

}
