package como.isil.mynotes.rest.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {


	public static final int DATABASE_VERSION = 1;
 
	public static final String DATABASE_NAME = "BDCalidad";
    public static final String TABLE_FUNDOS = "tb_fundo";
	public static final String TABLE_PROGRAMAS = "tb_programa";
	public static final String TABLE_VISITAS = "tb_visita";
	public static final String TABLE_USER = "tb_user";

    //Columnas de la Tabla Fundo
    public static final String KEY_IDPRODUCTOR = "idproductor";
    public static final String KEY_NOMBREPRODUCTOR = "nombreproductor";
	public static final String KEY_ESTADOPRODUCTOR = "estadoproductor";
	public static final String KEY_SINCROPRODUCTOR = "sincroproductor";


	//Columnas de la Tabla Programa
	public static final String KEY_IDPROGRAMA = "idprograma";
	public static final String KEY_NOMBREPROGRAMA = "nombreprograma";

	//Columnas de la Tabla Visita
	public static final String KEY_IDVISITA = "idvisita";
	public static final String KEY_SEMANAVISITA = "semana";
	public static final String KEY_IDFUNDO_VISITA = "idfundo";
	public static final String KEY_IDCAL_VISITA = "idcalificacion";
	public static final String KEY_FECHAVISITA = "fecvisita";
	public static final String KEY_CONTENEDOR = "contenedor";
	public static final String KEY_COMENTARIO = "comentario";

	//Columnas de la Tabla User

	public static final String KEY_USERID = "userid";
	public static final String KEY_USER = "user";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_USERNOMBRE = "nombre";



    public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String sqluser= "CREATE TABLE " + TABLE_USER + "("
				+ KEY_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ KEY_USER + " TEXT,"
				+ KEY_PASSWORD + " TEXT,"
				+ KEY_USERNOMBRE + " TEXT"


				+ ")";
		db.execSQL(sqluser);



		String sqlfundos= "CREATE TABLE " + TABLE_FUNDOS + "("
				+ KEY_IDPRODUCTOR + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ KEY_NOMBREPRODUCTOR + " TEXT,"
				+ KEY_ESTADOPRODUCTOR + " TEXT,"
				+ KEY_SINCROPRODUCTOR + " TEXT"

				+ ")";
		db.execSQL(sqlfundos);

		String sqlprograma= "CREATE TABLE " + TABLE_PROGRAMAS + "("
				+ KEY_IDPROGRAMA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + KEY_NOMBREPROGRAMA + " TEXT"
				+ ")";
		db.execSQL(sqlprograma);


		String sqlvisita= "CREATE TABLE " + TABLE_VISITAS + "("
				+ KEY_IDVISITA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"
				+ KEY_SEMANAVISITA + " TEXT,"
				+ KEY_IDFUNDO_VISITA + " TEXT,"
				+ KEY_IDCAL_VISITA + " TEXT,"
				+ KEY_FECHAVISITA + " TEXT,"
				+ KEY_CONTENEDOR + " TEXT,"
				+ KEY_COMENTARIO + " TEXT"
				+ ")";
		db.execSQL(sqlvisita);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sqlfundo= "DROP TABLE IF EXISTS " + TABLE_FUNDOS;
		db.execSQL(sqlfundo);

		String sqlprograma= "DROP TABLE IF EXISTS " + TABLE_PROGRAMAS;
		db.execSQL(sqlprograma);

		String sqlvisita= "DROP TABLE IF EXISTS " + TABLE_VISITAS;
		db.execSQL(sqlvisita);

		String sqluser= "DROP TABLE IF EXISTS " + TABLE_USER;
		db.execSQL(sqluser);
	}

}
