package como.isil.mynotes.rest.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isil.mynotes.rest.R;


import como.isil.mynotes.rest.presenter.fundo.AddFundoView;
import como.isil.mynotes.rest.presenter.fundo.FundoPresenter;
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
    private String fundo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFundoListener mListener;
    private FundoPresenter fundoPresenter;

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
        eteId=(EditText)getView().findViewById(R.id.eteId);
        eteNombre=(EditText)getView().findViewById(R.id.eteNombre);
        eteFundo=(EditText)getView().findViewById(R.id.eteFundo);
        btnAddFundo=(Button)getView().findViewById(R.id.btnAddFundo);

        fundoPresenter = new FundoPresenter();
        fundoPresenter.attachedView(this);

        btnAddFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addNote();
                addNoteCloud();
            }
        });
    }

    private void addNote() {
        //name= eteName.getText().toString().trim();
        //desc= eteDesc.getText().toString().trim();
        //note= eteNote.getText().toString().trim();

        //NoteEntity noteEntity= new NoteEntity(name,desc,null);
        //mListener.getCrudOperations().addNote(noteEntity);
        getActivity().finish();
    }

    private void addNoteCloud(){
        id= eteId.getText().toString().trim();
        nombre= eteNombre.getText().toString().trim();
        fundo= eteFundo.getText().toString().trim();

        fundoPresenter.addFundo(Integer.parseInt(id), nombre);
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
        getActivity().finish();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
