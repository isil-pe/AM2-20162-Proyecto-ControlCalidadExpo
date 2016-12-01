package como.isil.mynotes.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.isil.mynotes.rest.R;

import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.presenter.visita.AddVisitaView;
import como.isil.mynotes.rest.presenter.visita.DeleteVisitaPresenter;
import como.isil.mynotes.rest.presenter.visita.DeleteVisitaView;
import como.isil.mynotes.rest.presenter.visita.EditVisitaPresenter;
import como.isil.mynotes.rest.presenter.visita.EditVisitaView;
import como.isil.mynotes.rest.presenter.visita.VisitaPresenter;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;
import como.isil.mynotes.rest.storage.db.MyDatabase;
import como.isil.mynotes.rest.utils.OnSyncCload;
import como.isil.mynotes.rest.view.dialogs.MyDialogFragment;
import como.isil.mynotes.rest.view.dialogs.MyDialogFragmentVisita;
import como.isil.mynotes.rest.view.dialogs.MyDialogListener;
import como.isil.mynotes.rest.view.fragments.visita.AddVisitaFragment;
import como.isil.mynotes.rest.view.fragments.visita.DetailsVisitaFragment;
import como.isil.mynotes.rest.view.fragments.visita.ListaVisitaFragment;
import como.isil.mynotes.rest.view.listeners.OnVisitaListener;

public class VisitaActivity extends ActionBarActivity implements OnVisitaListener, MyDialogListener, DeleteVisitaView , EditVisitaView ,AddVisitaView {

    public static final  int ADD_VISITA=200;
    public static final  int DETAIL_VISITA=201;
    public static final  int UPDATE_VISITA=202;
    private static final String TAG ="VisitaActivity";
    private DeleteVisitaPresenter deleteEmbPresenter;
    private EditVisitaPresenter editVisitaPresenter;
    private AddVisitaFragment addVisitaFragment= AddVisitaFragment.newInstance(null,null);
    private DetailsVisitaFragment detailsvisitaFragment= DetailsVisitaFragment.newInstance(null,null);
    private int fragmentSelected= DETAIL_VISITA;
    private VisitaEntity visitaEntity;

    private CRUDOperationsVisita crudOperations;
    private VisitaEntity tmpVisitaEntity;

    private View rlayLoading, container;;
    private VisitaPresenter visitaPresenter;
    ProgressDialog progressDialog;
    Boolean internet = false;
    String idgrabadoVisita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita);

        validateExtras();

        rlayLoading= findViewById(R.id.rlayLoading);
        crudOperations= new CRUDOperationsVisita(new MyDatabase(this));
        Bundle bundle= new Bundle();
        bundle.putSerializable("VISITA",visitaEntity);
        container=findViewById(R.id.containerVisita);
        changeFragment(fragmentSelected, bundle);

        deleteEmbPresenter= new DeleteVisitaPresenter();
        deleteEmbPresenter.attachedView(this);

        editVisitaPresenter= new EditVisitaPresenter();
        editVisitaPresenter.attachedView(this);

        visitaPresenter = new VisitaPresenter();
        visitaPresenter.attachedView(this);
    }


    private void validateExtras() {
        if(getIntent().getExtras()!=null)
        {
            fragmentSelected= getIntent().getExtras().getInt("FRAGMENT",DETAIL_VISITA);
            visitaEntity= (VisitaEntity)getIntent().getExtras().getSerializable("VISITA");
        }
    }


    private  void changeFragment(int id,Bundle bundle)
    {
        Fragment fragment= null;
        switch (id)
        {
            case ADD_VISITA:
                fragment=addVisitaFragment;
                break;

            case DETAIL_VISITA:
                fragment=detailsvisitaFragment;
                break;

            case UPDATE_VISITA:
                fragment=null;
                break;
        }

        if(fragment!=null)
        {
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.containerVisita, fragment,"TAG_ADDVISITAFRAG").commit();
        }
    }

    @Override
    public CRUDOperationsVisita getCrudOperations() {
        return crudOperations;
    }

    @Override
    public void deleteVisita(VisitaEntity visitaEntity) {
        tmpVisitaEntity= visitaEntity;
        MyDialogFragmentVisita myDialogFragment =new MyDialogFragmentVisita();
        Bundle bundle= new Bundle();
        bundle.putString("TITLE","¿Deseas eliminar la visita?");
        bundle.putInt("TYPE", 100);
        bundle.putSerializable("VISITAENTITY",visitaEntity);
        myDialogFragment.setArguments(bundle);
        myDialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void updateVisita(VisitaEntity visitaEntity) {
        editVisitaCloud(visitaEntity);
        Log.v("Sync","actulizando cloud");
    }

    @Override
    public void showParentLoading() {
        this.rlayLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideParentLoading() {
        this.rlayLoading.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message+" Visita Activity", Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onPositiveListener(Object object, int type) {
        Log.v(TAG, "dialog positive");
        VisitaEntity auxVisitaEntity= (VisitaEntity)object;
        if(auxVisitaEntity!=null)
        {
            //eliminar

            auxVisitaEntity.setSincro(crudOperations.getVisita(auxVisitaEntity.getIdvisita()).getSincro());

            crudOperations.deleteVisita(auxVisitaEntity);
            deleteEmbCloud(auxVisitaEntity);

            Handler handler = ListaVisitaFragment.sUpdateHandler;
            if (handler != null) {
                handler.obtainMessage().sendToTarget();
            }


            auxVisitaEntity=null;

            //cerrar vista

            finish();
        }

    }

    private void editVisitaCloud(VisitaEntity visita) {
        String objectId= visita.getObjectId();


        int id = visita.getIdvisita();
        String semana= visita.getSemana();
        String fundo = visita.getIdfundo();
        String calificacion = visita.getIdcalificacion();
        String fecha =visita.getFecvisita();
        String contenedor = visita.getContenedor();
        String comentario = visita.getComentario();
        String estado = "NO";
        String sincro = "SI";


        editVisitaPresenter.EditVisita(objectId, id, semana,fundo, calificacion, fecha, contenedor, comentario, estado, sincro);
    }

    private void deleteEmbCloud(VisitaEntity visitaEntity) {


        String objectId= tmpVisitaEntity.getObjectId();
        deleteEmbPresenter.deleteEmbarque(objectId);
    }

    @Override
    public void onNegativeListener(Object object, int type) {
        Log.v(TAG, "dialog negative");
    }



    @Override
    public void showLoading() {
        this.rlayLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.rlayLoading.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onMessageError(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message+" Visita Activity", Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void onAddVisitaSuccess(VisitaEntity visitaEntity) {

        VisitaEntity visita = crudOperations.getVisita(Integer.parseInt(idgrabadoVisita));
        if(visita!=null){
            visita.setSincro("SI");
            visita.setObjectId(visitaEntity.getObjectId());
            crudOperations.updateVisita(visita);

        }

        Handler handler = ListaVisitaFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }

    }

    @Override
    public void addVisitaCloud(VisitaEntity visitaEntity) {
        if(isConnectingToInternet(this) ){

            visitaPresenter.addVisita(visitaEntity);
            idgrabadoVisita = String.valueOf(visitaEntity.getIdvisita());
        }
    }

    @Override
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void editVisitaSuccess() {

    }



    @Override
    public void deleteVisitaSuccess() {

    }


}
