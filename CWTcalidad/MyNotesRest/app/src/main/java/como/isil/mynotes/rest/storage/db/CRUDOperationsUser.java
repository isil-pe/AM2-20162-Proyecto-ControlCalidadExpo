package como.isil.mynotes.rest.storage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import como.isil.mynotes.rest.entity.UserEntity;

/**
 * Created by Fabricio on 20/11/2016.
 */
public class CRUDOperationsUser {

    private MyDatabase helper;

    public CRUDOperationsUser(SQLiteOpenHelper _helper) {
        super();
        // TODO Auto-generated constructor stub
        helper = (MyDatabase) _helper;
    }

    public long addUser(UserEntity userEntity) {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_USER, userEntity.getEmail());
        values.put(MyDatabase.KEY_PASSWORD, userEntity.getToken());
        values.put(MyDatabase.KEY_USERNOMBRE, userEntity.getName());
        long id = db.insert(MyDatabase.TABLE_USER, null, values);
        db.close();
        return id;
    }

    public UserEntity getUser(int id) {
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura

        UserEntity userEntity = new UserEntity();
        Cursor cursor = db.query(MyDatabase.TABLE_USER,
                new String[]{
                        MyDatabase.KEY_USERID,
                        MyDatabase.KEY_USER,
                        MyDatabase.KEY_PASSWORD,
                        MyDatabase.KEY_USERNOMBRE,
                },
                MyDatabase.KEY_USERID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);


        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();


            int uid = Integer.parseInt(cursor.getString(0));
            String usuario = cursor.getString(1);
            String password = cursor.getString(2);
            String nombre = cursor.getString(3);


            userEntity = new UserEntity(
                    uid, usuario, nombre, password);
        }




        return userEntity;
    }


    public UserEntity getUserEmail(String u, String p) {
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura

        UserEntity userEntity = new UserEntity();
        String sql = "SELECT * FROM " + MyDatabase.TABLE_USER+" WHERE "+MyDatabase.KEY_USER+"='"+u+"' AND "+MyDatabase.KEY_PASSWORD
                +"= '"+p+"'";

        Cursor cursor = db.rawQuery(sql, null);





        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();


            int uid = Integer.parseInt(cursor.getString(0));
            String usuario = cursor.getString(1);
            String password = cursor.getString(2);
            String nombre = cursor.getString(3);


            userEntity = new UserEntity(
                    uid, usuario, nombre, password);
        }



        return userEntity;
    }



    public int getUsersCount() {
        String sql = "SELECT * FROM " + MyDatabase.TABLE_USER;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.close();

        return cursor.getCount();
    }

    //--------------------------------------------


    //--------------------------------------------

    public void dropTableUser(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql =  "DELETE FROM " + MyDatabase.TABLE_USER;
        db.execSQL(sql);

    }

    public int deleteUser(UserEntity userEntity) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int row = db.delete(MyDatabase.TABLE_USER,
                MyDatabase.KEY_USERID + "=?",
                new String[]{String.valueOf(userEntity.getId())});
        db.close();
        return row;
    }
}
