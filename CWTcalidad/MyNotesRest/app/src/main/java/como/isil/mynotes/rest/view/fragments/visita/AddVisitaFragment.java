package como.isil.mynotes.rest.view.fragments.visita;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isil.mynotes.rest.R;

import como.isil.mynotes.rest.entity.VisitaEntity;

import como.isil.mynotes.rest.presenter.visita.AddVisitaView;
import como.isil.mynotes.rest.presenter.visita.VisitasView;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;
import como.isil.mynotes.rest.storage.db.MyDatabase;

import como.isil.mynotes.rest.view.listeners.OnVisitaListener;


public class AddVisitaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText txtSemana;
    private EditText txtFundo;
    private EditText txtCalificacion;
    private EditText txtFecha;
    private EditText txtcontenedor;
    private EditText txtcomentario;

    private Button btnAddVisita;

    private String id = null;
    private String semana;
    private String fundo;
    private String calificacion;
    private String fecha;
    private String contenedor;
    private String comentario;


    private String estado;
    private String sincro;
    private OnVisitaListener mListener;

    private AddVisitaView mListenerAddVisitaView;
    CRUDOperationsVisita crudvisita;

    public AddVisitaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVisitaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVisitaFragment newInstance(String param1, String param2) {
        AddVisitaFragment fragment = new AddVisitaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        crudvisita = new CRUDOperationsVisita(new MyDatabase(this.getContext()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_visita, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnVisitaListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFundoListener");
        }

        try {
            mListenerAddVisitaView = (AddVisitaView) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFundoListener");
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
        txtSemana = (EditText) getView().findViewById(R.id.txtSemana);
        txtFundo = (EditText) getView().findViewById(R.id.txtFundo);
        txtCalificacion = (EditText) getView().findViewById(R.id.txtCalificacion);
        txtFecha = (EditText) getView().findViewById(R.id.txtFecha);
        txtcontenedor = (EditText) getView().findViewById(R.id.txtCont);
        txtcomentario = (EditText) getView().findViewById(R.id.txtComentario);
        btnAddVisita = (Button) getView().findViewById(R.id.btnAddVis);
        btnAddVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVisita();

            }
        });
    }

    private void addVisita() {
        semana = txtSemana.getText().toString().trim();
        fundo = txtFundo.getText().toString().trim();
        calificacion = txtCalificacion.getText().toString().trim();
        fecha = txtFecha.getText().toString().trim();
        contenedor = txtcontenedor.getText().toString().trim();
        comentario = txtcomentario.getText().toString().trim();
        estado = "NO";
        sincro = "NO";
        VisitaEntity visitaEntity = new VisitaEntity();
        visitaEntity.setSemana(semana);
        visitaEntity.setIdfundo(fundo);
        visitaEntity.setIdcalificacion(calificacion);
        visitaEntity.setFecvisita(fecha);
        visitaEntity.setContenedor(contenedor);
        visitaEntity.setComentario(comentario);
        visitaEntity.setEstado(estado);
        visitaEntity.setSincro(sincro);
        id = String.valueOf(mListener.getCrudOperations().addVisita(visitaEntity));
        visitaEntity.setIdvisita(Integer.parseInt(id));
        mListenerAddVisitaView.addVisitaCloud(visitaEntity);

        Handler handler = ListaVisitaFragment.sUpdateHandler;

        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }
        getActivity().finish();
    }


}
