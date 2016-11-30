package como.isil.mynotes.rest;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.isil.mynotes.rest.R;



import como.isil.mynotes.rest.entity.UserEntity;
import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.storage.db.CRUDOperationsUser;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;
import como.isil.mynotes.rest.storage.db.MyDatabase;


public class PruebaActivity extends AppCompatActivity {

    private Button btnBuscar;
    private Button btnInsert;
    private Button btnEliminar;
    private Button btnLogin;
    private Button btnInternet;
    private Button btnUpdate;
    private TextView tvId;
    private EditText txtId;

    private EditText txtSemana;
    private EditText txtFundo;
    private EditText txtCali;

    private EditText txtFecha;
    private EditText txtCont;
    private EditText txtComent;

    private EditText txtEstado;
    private EditText txtSincro;

    private View rlayLoading, container;
    Context context;

    Long id ;
    CRUDOperationsVisita crudvisita;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        Stetho.initializeWithDefaults(this);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        txtId = (EditText) findViewById(R.id.txtId);
        tvId = (TextView) findViewById(R.id.tvId);
        txtSemana = (EditText) findViewById(R.id.txtSemana);
        txtFundo = (EditText) findViewById(R.id.txtFundo);
        txtCali = (EditText) findViewById(R.id.txtCalificacion);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtCont = (EditText) findViewById(R.id.txtContenedor);
        txtComent = (EditText) findViewById(R.id.txtComentario);

        txtEstado = (EditText) findViewById(R.id.txtEstado);
        txtSincro = (EditText) findViewById(R.id.txtSincro);


    btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        rlayLoading = (findViewById(R.id.rlayLoading));
        crudvisita = new CRUDOperationsVisita(new MyDatabase(this));
btnInternet = (Button)findViewById(R.id.btnInternet);
context = this;
        btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                if(isConnectingToInternet(context)){
                    hideLoading();

                    Toast.makeText(PruebaActivity.this, "Si internet", Toast.LENGTH_SHORT).show();
                }else{
                        hideLoading();
                    Toast.makeText(PruebaActivity.this, "no internet", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                VisitaEntity visita = new VisitaEntity();
                visita.setIdvisita(Integer.parseInt(tvId.getText().toString()));
                visita.setSemana(txtSemana.getText().toString());
                visita.setIdfundo(txtFundo.getText().toString());
                visita.setIdcalificacion(txtCali.getText().toString());
                visita.setFecvisita(txtFecha.getText().toString());
                visita.setContenedor(txtCont.getText().toString());
                visita.setComentario(txtComent.getText().toString());
                visita.setEstado(txtEstado.getText().toString());
                visita.setSincro(txtSincro.getText().toString());


                crudvisita.updateVisita(visita);

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                 VisitaEntity visita = new VisitaEntity();
                int id = Integer.parseInt(txtId.getText().toString());
                visita  = crudvisita.getVisita(id);
if(visita!=null){
    crudvisita.deleteVisita(visita);

}



            }
        });




        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VisitaEntity visita ;
                int id = Integer.parseInt(txtId.getText().toString());
                visita  = crudvisita.getVisita(id);

                if(visita!=null ){

                    tvId.setText(""+id);
                    txtSemana.setText(visita.getSemana());
                    txtFundo.setText(visita.getIdfundo());
                    txtCali.setText(visita.getIdcalificacion());
                    txtFecha.setText(visita.getFecvisita());
                    txtCont.setText(visita.getContenedor());
                    txtComent.setText(visita.getComentario());
                    txtEstado.setText(visita.getEstado());
                    txtSincro.setText(visita.getSincro());
                }


            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitaEntity visita = new VisitaEntity();

                visita.setSemana(txtSemana.getText().toString());
                visita.setIdfundo(txtFundo.getText().toString());
                visita.setIdcalificacion(txtCali.getText().toString());
                visita.setFecvisita(txtFecha.getText().toString());
                visita.setContenedor(txtCont.getText().toString());
                visita.setComentario(txtComent.getText().toString());
                visita.setEstado(txtEstado.getText().toString());
                visita.setSincro(txtSincro.getText().toString());


                long id = crudvisita.addVisita(visita);
                tvId.setText(String.valueOf(id));
            }
        });



    }





    @Override
    protected void onPause() {
        super.onPause();

    }



    public static boolean isConnectingToInternet(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    public void showLoading() {
        this.rlayLoading.setVisibility(View.VISIBLE);
    }


    public void hideLoading() {
        this.rlayLoading.setVisibility(View.GONE);
    }


}
