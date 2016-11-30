package como.isil.mynotes.rest.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isil.mynotes.rest.R;



import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.presenter.fundo.AddFundoView;
import como.isil.mynotes.rest.presenter.fundo.FundoPresenter;
import como.isil.mynotes.rest.storage.db.CRUDOperations;
import como.isil.mynotes.rest.storage.db.MyDatabase;
import como.isil.mynotes.rest.utils.OnSyncCload;
import como.isil.mynotes.rest.utils.SyncCloud;
import como.isil.mynotes.rest.view.fragments.fundo.ListaFundoFragment;
import como.isil.mynotes.rest.view.listeners.OnFundoListener;

public class AddFundoFragment extends Fragment  implements AddFundoView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText eteId;
    private EditText eteNombre;
    private EditText eteFundo;
    private Button btnAddFundo;

    private String id=null;
    private String nombre;
    private String estado;
    private String sincro;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFundoListener mListener;
    private FundoPresenter fundoPresenter;
CRUDOperations crudfundo;

    OnSyncCload synclistener;
    ProgressDialog progressDialog;
    private Boolean internet = false;
private String idgrabado = "";

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    Boolean graboFundo = false;

    // TODO: Rename and change types and number of parameters
    public static AddFundoFragment newInstance(String param1, String param2) {
        AddFundoFragment fragment = new AddFundoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddFundoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        SyncCloud sc = new SyncCloud(synclistener, super.getActivity());
            crudfundo = new CRUDOperations(new MyDatabase(this.getContext()));
        sc.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_fundo, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFundoListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFundoListener");
        }
        try {
            synclistener = (OnSyncCload) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSyncCloadOnSyncCload");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eteNombre=(EditText)getView().findViewById(R.id.eteNombre);

        btnAddFundo=(Button)getView().findViewById(R.id.btnAddFundo);

        fundoPresenter = new FundoPresenter();
        fundoPresenter.attachedView(this);



        btnAddFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
                //addNoteCloud();
            }
        });
    }

    private void addNote() {
        nombre= eteNombre.getText().toString().trim();
        estado = "NO";
        sincro = "NO";

        FundoEntity fundoEntity= new FundoEntity();
        fundoEntity.setNombreproductor(nombre);
        fundoEntity.setEstado(estado);
        fundoEntity.setSincro(sincro);
        id = String.valueOf( mListener.getCrudOperations().addFundo(fundoEntity));

        if(internet){
            Log.v("sync","si internet add note");
            addNoteCloud();


        }

        Handler handler = ListaFundoFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }
        getActivity().finish();
    }

    private void addNoteCloud(){

        if(Long.parseLong(id)>=0) {
            nombre = eteNombre.getText().toString().trim();
            sincro = "SI";
            idgrabado = id;
            fundoPresenter.addFundo(Integer.parseInt(id), nombre, estado, sincro);
        }
    }

    @Override
    public void showLoading() {
        mListener.showParentLoading();
    }

    @Override
    public void hideLoading() {
        mListener.hideParentLoading();
    }

    @Override
    public void onMessageError(String message) {
        mListener.showMessage(message);
    }

    @Override
    public void onAddFundoSuccess() {
        graboFundo = true;
        FundoEntity fundo = crudfundo.getFundo(Integer.parseInt(idgrabado));
        fundo.setSincro("SI");
        crudfundo.updateFundo(fundo);
        Handler handler = ListaFundoFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }
        getActivity().finish();
    }





    @Override
    public Context getContext() {
        return getActivity();
    }


}
