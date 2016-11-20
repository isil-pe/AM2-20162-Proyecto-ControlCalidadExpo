package como.isil.mynotes.rest.view.fragments;

import android.app.Activity;
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


import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.view.fragments.fundo.ListaFundoFragment;
import como.isil.mynotes.rest.view.listeners.OnFundoListener;

public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnDeleteFundo;
    private Button btnEditFundo;
    private EditText eteId;
    private EditText eteNombre;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFundoListener mListener;
    private FundoEntity fundoEntity;

    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFundoListener) activity;
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
        btnDeleteFundo=(Button)getView().findViewById(R.id.btnDeleteFundo);
        btnEditFundo=(Button)getView().findViewById(R.id.btnEditFundo);

        eteId= (EditText)getView().findViewById(R.id.eteId);
        eteNombre= (EditText)getView().findViewById(R.id.eteNombre);

        if(getArguments()!=null)
        {
            fundoEntity= (FundoEntity)getArguments().getSerializable("FUNDO");
        }
        if(fundoEntity!=null)
        {
            //TODO mostrar INFO
            String id= String.valueOf(fundoEntity.getIdproductor());
            String nombre= fundoEntity.getNombreproductor();

            eteId.setText(id);
            eteNombre.setText(nombre);
        }

        btnDeleteFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteFundo(fundoEntity);

            }
        });

        btnEditFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFundo();
            }
        });
    }
    private void editFundo()
    {
        //TODO validar campos
        //extraer lo valores
        String id= eteId.getText().toString().trim();
        String nombre= eteNombre.getText().toString().trim();


        FundoEntity nFundoEntity=new FundoEntity();
        nFundoEntity.setIdproductor(Integer.parseInt(id));
        nFundoEntity.setNombreproductor(nombre);

        nFundoEntity.setEstado( mListener.getCrudOperations().getFundo(Integer.parseInt(id)).getEstado());
        nFundoEntity.setSincro(mListener.getCrudOperations().getFundo(Integer.parseInt(id)).getSincro());



        //llamar el método de crudoperation
        mListener.getCrudOperations().updateFundo(nFundoEntity);

        //cerrar la pantalla
        Handler handler = ListaFundoFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }
        getActivity().finish();
    }
}
