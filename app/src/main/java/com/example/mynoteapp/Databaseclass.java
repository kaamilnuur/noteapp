package com.example.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Databaseclass extends SQLiteOpenHelper {

     Context context ;
     private static final  String Database_name="My_DB";
     private static final  String TABLE_NAME="Note_tbl";
     private   static  final  int VERSIOIN=1;
     private static final  String COL_ID="id";
     private  static final  String COL_title="title";
     private static final  String COL_dis="discriptioin";
    public Databaseclass(@Nullable Context context) {
        super(context, Database_name, null, VERSIOIN);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_title + " TEXT, " +
                COL_dis + " TEXT);";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);

    }
    void  AddNote (String  title, String discriptio){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_title,title);
        cv.put(COL_dis,discriptio);
        long result=db.insert(TABLE_NAME,null,cv);
        if (result==-1){
            Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"succefull",Toast.LENGTH_LONG).show();
        }
    }
    Cursor  readAllData(){
      String  query="SELECT * FROM " + TABLE_NAME ;
      SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return  cursor;
    }

    void  deleteAllNote(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="DELETE FROM "+ TABLE_NAME;
        db.execSQL(query);
    }
    void  updateNote(String title,String discription,String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=  new ContentValues();
        cv.put(COL_title,title);
        cv.put(COL_dis, discription);

        long result=db.update(TABLE_NAME,cv,"id=?",new String[]{id});
        if(result==-1){
            Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"succefull",Toast.LENGTH_LONG).show();
        }
    }
    public  void  deleteSingleItem( String id){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete(TABLE_NAME,"id=?",new String[]{id});
        if(result==-1){
            Toast.makeText(context,"failed",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context,"Item Deleted succefully",Toast.LENGTH_LONG).show();
        }
    }

}
