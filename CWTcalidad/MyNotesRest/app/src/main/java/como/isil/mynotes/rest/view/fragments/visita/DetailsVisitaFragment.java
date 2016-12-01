package como.isil.mynotes.rest.view.fragments.visita;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isil.mynotes.rest.R;

import como.isil.mynotes.rest.UploadActivity;
import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.presenter.visita.AddVisitaView;
import como.isil.mynotes.rest.presenter.visita.DeleteVisitaPresenter;
import como.isil.mynotes.rest.presenter.visita.DeleteVisitaView;
import como.isil.mynotes.rest.presenter.visita.EditVisitaPresenter;
import como.isil.mynotes.rest.presenter.visita.EditVisitaView;
import como.isil.mynotes.rest.view.listeners.OnVisitaListener;


public class DetailsVisitaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button btnDeleteVisita;
    private Button btnEditVisita;
    private Button btnUploadPhoto;
    private EditText eteId;
    private EditText eteSemana;
    private EditText eteFundo;
    private EditText eteCalificacion;
    private EditText eteFecha;
    private EditText eteContenedor;
    private EditText eteComentario;
    private String updObjecid;


    private OnVisitaListener mListener;
    private AddVisitaView addVisitaViewmListener;
    private VisitaEntity visitaEntity;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DetailsVisitaFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailsVisitaFragment newInstance(String param1, String param2) {
        DetailsVisitaFragment fragment = new DetailsVisitaFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_visita, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnVisitaListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        try {
            addVisitaViewmListener = (AddVisitaView) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
        btnDeleteVisita = (Button) getView().findViewById(R.id.btnDeleteVisita);
        btnEditVisita = (Button) getView().findViewById(R.id.btnEditVisita);
        btnUploadPhoto = (Button) getView().findViewById(R.id.btnUploadPhoto);
        eteId = (EditText) getView().findViewById(R.id.eteIdVisita);
        eteSemana = (EditText) getView().findViewById(R.id.txtSemanaDetail);
        eteFundo = (EditText) getView().findViewById(R.id.txtFundoDetail);
        eteCalificacion = (EditText) getView().findViewById(R.id.txtCalificacionDetail);
        eteFecha = (EditText) getView().findViewById(R.id.txtFechaDetail);
        eteContenedor = (EditText) getView().findViewById(R.id.txtContDetail);
        eteComentario = (EditText) getView().findViewById(R.id.txtComentarioDetail);


        if (getArguments() != null) {
            visitaEntity = (VisitaEntity) getArguments().getSerializable("VISITA");
        }
        if (visitaEntity != null) {
            //TODO mostrar INFO
            String id = String.valueOf(visitaEntity.getIdvisita());
            String semana = visitaEntity.getSemana();
            String fundo = visitaEntity.getIdfundo();
            String calificacion = visitaEntity.getIdcalificacion();
            String fecha = visitaEntity.getFecvisita();
            String contenedor = visitaEntity.getContenedor();
            String comentario = visitaEntity.getComentario();
            updObjecid = visitaEntity.getObjectId();
            eteId.setText(id);
            eteSemana.setText(semana);
            eteFundo.setText(fundo);
            eteCalificacion.setText(calificacion);
            eteFecha.setText(fecha);
            eteContenedor.setText(contenedor);
            eteComentario.setText(comentario);
        }

        btnDeleteVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteVisita(visitaEntity);


            }
        });

        btnEditVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editVisita();

            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),UploadActivity.class);
                intent.putExtra("EXTRA_ID_VISITA", String.valueOf(visitaEntity.getIdvisita()));
                intent.putExtra("EXTRA_ID_CONTENEDO", String.valueOf(visitaEntity.getContenedor()));
                startActivity(intent);

            }
        });
    }

    private void editVisita() {
        //TODO validar campos
        //extraer lo valores
        String id = eteId.getText().toString().trim();
        String semana = eteSemana.getText().toString().trim();
        String fundo = eteFundo.getText().toString().trim();
        String calificacion = eteCalificacion.getText().toString().trim();
        String fecha = eteFecha.getText().toString().trim();
        String contenedor = eteContenedor.getText().toString().trim();
        String comentario = eteComentario.getText().toString().trim();


        VisitaEntity nVisitaEntity = new VisitaEntity();
        nVisitaEntity.setIdvisita(Integer.parseInt(id));
        nVisitaEntity.setSemana(semana);
        nVisitaEntity.setIdfundo(fundo);
        nVisitaEntity.setIdcalificacion(calificacion);
        nVisitaEntity.setFecvisita(fecha);
        nVisitaEntity.setContenedor(contenedor);
        nVisitaEntity.setComentario(comentario);
        nVisitaEntity.setObjectId(updObjecid);
        nVisitaEntity.setEstado(mListener.getCrudOperations().getVisita(Integer.parseInt(id)).getEstado());
        nVisitaEntity.setSincro(mListener.getCrudOperations().getVisita(Integer.parseInt(id)).getSincro());


        //llamar el método de crudoperation
        mListener.getCrudOperations().updateVisita(nVisitaEntity);

        if(nVisitaEntity.getObjectId()=="" || nVisitaEntity.getObjectId()==null){

            addVisitaViewmListener.addVisitaCloud(nVisitaEntity);
        }else{

            mListener.updateVisita(nVisitaEntity);
        }


        //cerrar la pantalla
        updObjecid = null;
        Handler handler = ListaVisitaFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }
        getActivity().finish();
    }


}
