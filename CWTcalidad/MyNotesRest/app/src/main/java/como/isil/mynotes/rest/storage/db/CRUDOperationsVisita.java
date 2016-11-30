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

    public long addVisita(VisitaEntity visitaEntity)
    {
        SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
        ContentValues values = new ContentValues();


        values.put(MyDatabase.KEY_SEMANAVISITA, visitaEntity.getSemana());
        values.put(MyDatabase.KEY_IDFUNDO_VISITA, visitaEntity.getIdfundo());
        values.put(MyDatabase.KEY_IDCAL_VISITA, visitaEntity.getIdcalificacion());
        values.put(MyDatabase.KEY_FECHAVISITA, visitaEntity.getFecvisita());
        values.put(MyDatabase.KEY_CONTENEDOR, visitaEntity.getContenedor());
        values.put(MyDatabase.KEY_COMENTARIO, visitaEntity.getComentario());
        values.put(MyDatabase.KEY_ESTADO_VISITA, visitaEntity.getEstado());
        values.put(MyDatabase.KEY_OBJECTID_VISITA, visitaEntity.getObjectId());



       long id = db.insert(MyDatabase.TABLE_VISITAS, null, values);
        db.close();

        return id;
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
                        MyDatabase.KEY_ESTADO_VISITA,
                        MyDatabase.KEY_SINCRO_VISITA,
                        MyDatabase.KEY_OBJECTID_VISITA,

                },
                MyDatabase.KEY_IDVISITA + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();

            int vid = Integer.parseInt(cursor.getString(0));
            String semana = cursor.getString(1);
            String idfundovisita = cursor.getString(2);
            String idcalvisita = cursor.getString(3);
            String fechavisita = cursor.getString(4);
            String contenedor = cursor.getString(5);
            String comentario = cursor.getString(6);
            String estado = cursor.getString(7);
            String sincro = cursor.getString(8);
            String objectid = cursor.getString(9);
            VisitaEntity visitaEntity= new VisitaEntity(
                    vid, semana, idfundovisita, idcalvisita, fechavisita,contenedor,comentario, estado, sincro, objectid);

            return visitaEntity;
        }




        return null;
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
                visitaEntity.setEstado(cursor.getString(7));
                visitaEntity.setSincro(cursor.getString(8));
                visitaEntity.setObjectId(cursor.getString(9));

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
        int count = cursor.getCount();
        cursor.close();

        return count;
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
        values.put(MyDatabase.KEY_ESTADO_VISITA, visitaEntity.getEstado());
        values.put(MyDatabase.KEY_SINCRO_VISITA, visitaEntity.getSincro());
        values.put(MyDatabase.KEY_OBJECTID_VISITA, visitaEntity.getObjectId());

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

    public int getVisitaContenedor(String contenedor) {
        SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura


        String sql = "SELECT * FROM " + MyDatabase.TABLE_VISITAS+" WHERE "+MyDatabase.KEY_CONTENEDOR+"='"+contenedor+"'";

        Cursor cursor = db.rawQuery(sql, null);





        if (cursor != null && cursor.getCount()>0) {
            return cursor.getCount();
        }


return 0;

    }
    
}
