package como.isil.mynotes.rest.storage.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import como.isil.mynotes.rest.entity.FundoEntity;

public class CRUDOperations {

	private MyDatabase helper;

	public CRUDOperations(SQLiteOpenHelper _helper) {
		super();
		// TODO Auto-generated constructor stub
		helper = (MyDatabase) _helper;
	}

	public long addFundo(FundoEntity fundoEntity) {
		SQLiteDatabase db = helper.getWritableDatabase(); //modo escritura
		ContentValues values = new ContentValues();
		values.put(MyDatabase.KEY_NOMBREPRODUCTOR, fundoEntity.getNombreproductor());
		values.put(MyDatabase.KEY_ESTADOPRODUCTOR, fundoEntity.getEstado());
		values.put(MyDatabase.KEY_SINCROPRODUCTOR, fundoEntity.getSincro());
		long id = db.insert(MyDatabase.TABLE_FUNDOS, null, values);
		db.close();
        return id;
	}

	public FundoEntity getFundo(int id) {
		SQLiteDatabase db = helper.getReadableDatabase(); //modo lectura

        FundoEntity fundoEntity = new FundoEntity();
        Cursor cursor = db.query(MyDatabase.TABLE_FUNDOS,
				new String[]{
                        MyDatabase.KEY_IDPRODUCTOR,
                        MyDatabase.KEY_NOMBREPRODUCTOR,
                        MyDatabase.KEY_ESTADOPRODUCTOR,
                        MyDatabase.KEY_SINCROPRODUCTOR,
				},
				MyDatabase.KEY_IDPRODUCTOR + "=?",
				new String[]{String.valueOf(id)}, null, null, null);


        if (cursor != null && cursor.getCount()>0) {
			cursor.moveToFirst();


            int fid = Integer.parseInt(cursor.getString(0));
            String nombre = cursor.getString(1);
            String estado = cursor.getString(2);
            String sincro = cursor.getString(3);


            fundoEntity = new FundoEntity(
                    fid, nombre, estado, sincro);
		}




        return fundoEntity;
	}

	public List<FundoEntity> getAllFundos() {
        List<FundoEntity> lst = new ArrayList<FundoEntity>();
        String sql = "SELECT  * FROM " + MyDatabase.TABLE_FUNDOS;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FundoEntity fundoEntity = new FundoEntity();
                fundoEntity.setIdproductor(Integer.parseInt(cursor.getString(0)));
                fundoEntity.setNombreproductor(cursor.getString(1));


                lst.add(fundoEntity);
            } while (cursor.moveToNext());
        }
        return lst;
    }

    public List<FundoEntity> getAllFundosNoEliminados() {
        List<FundoEntity> lst = new ArrayList<FundoEntity>();
        String sql = "SELECT  * FROM " + MyDatabase.TABLE_FUNDOS +" WHERE "+MyDatabase.KEY_ESTADOPRODUCTOR+" != 'ELIMINADO' ";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                FundoEntity fundoEntity = new FundoEntity();
                fundoEntity.setIdproductor(Integer.parseInt(cursor.getString(0)));
                fundoEntity.setNombreproductor(cursor.getString(1));
				fundoEntity.setEstado(cursor.getString(2));
				fundoEntity.setSincro(cursor.getString(3));


                lst.add(fundoEntity);
            } while (cursor.moveToNext());
        }
        return lst;
    }

	public int getFundosCount() {
		String sql = "SELECT * FROM " + MyDatabase.TABLE_FUNDOS;
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		cursor.close();

		return cursor.getCount();
	}

	//--------------------------------------------

	public int updateFundo(FundoEntity fundoEntity) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MyDatabase.KEY_IDPRODUCTOR, fundoEntity.getIdproductor());
		values.put(MyDatabase.KEY_NOMBREPRODUCTOR, fundoEntity.getNombreproductor());
		values.put(MyDatabase.KEY_ESTADOPRODUCTOR, fundoEntity.getEstado());
		values.put(MyDatabase.KEY_SINCROPRODUCTOR, fundoEntity.getSincro());


		return db.update(MyDatabase.TABLE_FUNDOS,
				values,
				MyDatabase.KEY_IDPRODUCTOR + "=?",
				new String[]{String.valueOf(fundoEntity.getIdproductor())});
	}
	//--------------------------------------------

	public int deleteFundo(FundoEntity fundoEntity) {
		SQLiteDatabase db = helper.getWritableDatabase();

		int row = db.delete(MyDatabase.TABLE_FUNDOS,
				MyDatabase.KEY_IDPRODUCTOR + "=?",
				new String[]{String.valueOf(fundoEntity.getIdproductor())});
		db.close();
		return row;
	}
}
