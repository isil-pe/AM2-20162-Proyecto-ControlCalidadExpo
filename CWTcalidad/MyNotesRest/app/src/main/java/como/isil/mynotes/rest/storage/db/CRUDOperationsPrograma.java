package como.isil.mynotes.rest.storage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import como.isil.mynotes.rest.entity.ProgramaEntity;

/**
 * Created by Fabricio on 15/11/2016.
 */
public class CRUDOperationsPrograma {

    private MyDatabase helper;
    public CRUDOperationsPrograma(SQLiteOpenHelper _helper) {
        super();
        // TODO Auto-generated constructor stub
        helper =(MyDatabase)_helper;
    }

    public void addPrograma(ProgramaEntity programaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_IDPRODUCTOR, programaEntity.getIdprograma());
        values.put(MyDatabase.KEY_NOMBREPRODUCTOR, programaEntity.getNombreprograma());


        db.insert(MyDatabase.TABLE_PROGRAMAS, null, values);
        db.close();
    }

    public ProgramaEntity getPrograma(int id)
    {
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TABLE_PROGRAMAS,
                new String[]{MyDatabase.KEY_IDPRODUCTOR, MyDatabase.KEY_NOMBREPRODUCTOR,
                },
                MyDatabase.KEY_NOMBREPRODUCTOR + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        int pid = Integer.parseInt(cursor.getString(0));
        String nombre = cursor.getString(1);


        ProgramaEntity programaEntity= new ProgramaEntity(
                pid, nombre);
        return programaEntity;
    }

    public List<ProgramaEntity> getAllProgramas()
    {
        List<ProgramaEntity> lst =new ArrayList<ProgramaEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TABLE_PROGRAMAS;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                ProgramaEntity programaEntity =new ProgramaEntity();
                programaEntity.setIdprograma(Integer.parseInt(cursor.getString(0)));
                programaEntity.setNombreprograma(cursor.getString(1));


                lst.add(programaEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    public int getProgramasCount()
    {
        String sql= "SELECT * FROM "+MyDatabase.TABLE_PROGRAMAS;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.close();

        return cursor.getCount();
    }

    //--------------------------------------------

    public int updatePrograma(ProgramaEntity programaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_IDPRODUCTOR, programaEntity.getIdprograma());
        values.put(MyDatabase.KEY_NOMBREPRODUCTOR, programaEntity.getNombreprograma());


        return db.update(MyDatabase.TABLE_PROGRAMAS,
                values,
                MyDatabase.KEY_IDPRODUCTOR+"=?",
                new String[]{String.valueOf(programaEntity.getIdprograma())});
    }
    //--------------------------------------------

    public int deletePrograma(ProgramaEntity programaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row= db.delete(MyDatabase.TABLE_PROGRAMAS,
                MyDatabase.KEY_IDPRODUCTOR+"=?",
                new String[]{String.valueOf(programaEntity.getIdprograma())});
        db.close();
        return row;
    }
}
