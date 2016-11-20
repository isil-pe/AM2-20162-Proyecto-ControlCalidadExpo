package como.isil.mynotes.rest.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isil.mynotes.rest.R;


import como.isil.mynotes.rest.MainActivity;
import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.presenter.fundo.AddFundoView;
import como.isil.mynotes.rest.presenter.fundo.FundoPresenter;
import como.isil.mynotes.rest.utils.OnSyncCload;
import como.isil.mynotes.rest.utils.SyncCloud;
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

    private String id;
    private String nombre;
    private String estado;
    private String sincro;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFundoListener mListener;
    private FundoPresenter fundoPresenter;


    OnSyncCload synclistener;
    ProgressDialog progressDialog;
    Boolean internet = false;
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
        //desc= eteDesc.getText().toString().trim();
        //note= eteNote.getText().toString().trim();

        FundoEntity fundoEntity= new FundoEntity();
        fundoEntity.setNombreproductor(nombre);
        fundoEntity.setEstado(estado);
        Log.v("pruabddfundo",""+internet);
if(internet){
    Log.v("pruabddfundo","si internet");
    addNoteCloud();

    if(graboFundo) {
        fundoEntity.setSincro("SI");
    }
}else{
    fundoEntity.setSincro("NO");

}


        mListener.getCrudOperations().addFundo(fundoEntity);
        getActivity().finish();
    }

    private void addNoteCloud(){


        nombre= eteNombre.getText().toString().trim();
        sincro = "SI";
        fundoPresenter.addFundo(Integer.parseInt(id), nombre,estado,sincro);
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
        getActivity().finish();
    }



    @Override
    public void onPostExecute(String result) {
        internet = Boolean.parseBoolean(result);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


}
