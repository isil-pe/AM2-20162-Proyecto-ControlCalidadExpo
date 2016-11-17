package como.isil.mynotes.rest.storage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import como.isil.mynotes.rest.entity.VisitaEntity;


/**
 * Created by Fabricio on 15/11/2016.
 */
public class CRUDOperationsVisita {
    private MyDatabase helper;
    public CRUDOperationsVisita(SQLiteOpenHelper _helper) {
        super();
        // TODO Auto-generated constructor stub
        helper =(MyDatabase)_helper;
    }

    public void addVisita(VisitaEntity visitaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();


        values.put(MyDatabase.KEY_SEMANAVISITA, visitaEntity.getSemana());
        values.put(MyDatabase.KEY_IDFUNDO_VISITA, visitaEntity.getIdfundo());
        values.put(MyDatabase.KEY_IDCAL_VISITA, visitaEntity.getIdcalificacion());
        values.put(MyDatabase.KEY_FECHAVISITA, visitaEntity.getFecvisita());
        values.put(MyDatabase.KEY_CONTENEDOR, visitaEntity.getContenedor());
        values.put(MyDatabase.KEY_COMENTARIO, visitaEntity.getComentario());



        db.insert(MyDatabase.TABLE_VISITAS, null, values);
        db.close();
    }

    public VisitaEntity getVisita(int id)
    {
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura
        Cursor cursor = db.query(MyDatabase.TABLE_VISITAS,
                new String[]{
                        MyDatabase.KEY_IDVISITA,
                        MyDatabase.KEY_SEMANAVISITA,
                        MyDatabase.KEY_IDFUNDO_VISITA,
                        MyDatabase.KEY_IDCAL_VISITA,
                        MyDatabase.KEY_FECHAVISITA,
                        MyDatabase.KEY_CONTENEDOR,
                        MyDatabase.KEY_COMENTARIO,

                },
                MyDatabase.KEY_IDVISITA + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        int vid = Integer.parseInt(cursor.getString(0));
        String semana = cursor.getString(1);
        String idfundovisita = cursor.getString(2);
        String idcalvisita = cursor.getString(3);
        String fechavisita = cursor.getString(4);
        String contenedor = cursor.getString(5);
        String comentario = cursor.getString(6);


        VisitaEntity visitaEntity= new VisitaEntity(
                vid, semana, idfundovisita, idcalvisita, fechavisita,contenedor,comentario);
        return visitaEntity;
    }

    public List<VisitaEntity> getAllVisitas()
    {
        List<VisitaEntity> lst =new ArrayList<VisitaEntity>();
        String sql= "SELECT  * FROM " + MyDatabase.TABLE_VISITAS;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do
            {
                VisitaEntity visitaEntity =new VisitaEntity();
                visitaEntity.setIdvisita(Integer.parseInt(cursor.getString(0)));
                visitaEntity.setSemana(cursor.getString(1));
                visitaEntity.setIdfundo(cursor.getString(2));
                visitaEntity.setIdcalificacion(cursor.getString(3));
                visitaEntity.setFecvisita(cursor.getString(4));
                visitaEntity.setContenedor(cursor.getString(5));
                visitaEntity.setComentario(cursor.getString(6));


                lst.add(visitaEntity);
            }while(cursor.moveToNext());
        }
        return lst;
    }

    public int getVisitasCount()
    {
        String sql= "SELECT * FROM "+MyDatabase.TABLE_VISITAS;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.close();

        return cursor.getCount();
    }

    //--------------------------------------------

    public int updateVisita(VisitaEntity visitaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabase.KEY_IDVISITA, visitaEntity.getIdvisita());
        values.put(MyDatabase.KEY_SEMANAVISITA, visitaEntity.getSemana());
        values.put(MyDatabase.KEY_IDFUNDO_VISITA, visitaEntity.getIdfundo());
        values.put(MyDatabase.KEY_IDCAL_VISITA, visitaEntity.getIdcalificacion());
        values.put(MyDatabase.KEY_FECHAVISITA, visitaEntity.getFecvisita());
        values.put(MyDatabase.KEY_CONTENEDOR,visitaEntity.getContenedor());
        values.put(MyDatabase.KEY_COMENTARIO, visitaEntity.getComentario());


        return db.update(MyDatabase.TABLE_VISITAS,
                values,
                MyDatabase.KEY_IDVISITA+"=?",
                new String[]{String.valueOf(visitaEntity.getIdvisita())});
    }
    //--------------------------------------------

    public int deleteVisita(VisitaEntity visitaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        int row= db.delete(MyDatabase.TABLE_VISITAS,
                MyDatabase.KEY_IDVISITA+"=?",
                new String[]{String.valueOf(visitaEntity.getIdvisita())});
        db.close();
        return row;
    }
    
}
