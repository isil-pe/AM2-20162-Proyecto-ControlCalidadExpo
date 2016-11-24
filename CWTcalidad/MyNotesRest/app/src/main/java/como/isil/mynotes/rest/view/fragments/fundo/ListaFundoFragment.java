package como.isil.mynotes.rest.view.fragments.fundo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isil.mynotes.rest.R;

import java.util.List;

import como.isil.mynotes.rest.FundoActivity;
import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.presenter.fundo.FundosView;
import como.isil.mynotes.rest.storage.PreferencesHelper;
import como.isil.mynotes.rest.storage.db.CRUDOperations;
import como.isil.mynotes.rest.storage.db.MyDatabase;
import como.isil.mynotes.rest.utils.CapitalizeString;
import como.isil.mynotes.rest.view.adapters.FundoAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaFundoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaFundoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaFundoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private ListView lstFundos;
    private List<FundoEntity> fundos;
    private CRUDOperations crudOperations;
    private FundoAdapter fundoAdapter;
    private static final int ACTION_ADD = 1;
    private static final int ACTION_DETAIL = 2;
    public static Handler sUpdateHandler;
    Button  btnAddFundo2;

    FundosView mlistener;
    public ListaFundoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaFundoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaFundoFragment newInstance(String param1, String param2) {
        ListaFundoFragment fragment = new ListaFundoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadData() {
        crudOperations = new CRUDOperations(new MyDatabase(getView().getContext()));
        fundos= crudOperations.getAllFundosNoEliminados();
        fundoAdapter = new FundoAdapter(getView().getContext(),fundos);
        lstFundos.setAdapter(fundoAdapter);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        sUpdateHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // call you update method here.

                loadData();

            }
        };

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        //1. DATA


        //2 Lista lstContacts
        //3 Item row_contact
        //4 Adapter
        //MailAdapter mailAdapter= new MailAdapter(data,getActivity());
        //lstEmails.setAdapter(mailAdapter);



        //selectedFirst();
    }


    private void init() {
        //tviLogout = (TextView) findViewById(R.id.tviLogout);
        //tviUser = (TextView) findViewById(R.id.tviUser);
        lstFundos = (ListView)getView().findViewById(R.id.lstFundos);
        btnAddFundo2 = (Button) getView().findViewById(R.id.btnAddfundo2);
        //rlayLoading = (findViewById(R.id.rlayLoading));

        //user Info
        String username = PreferencesHelper.getUserSession(getView().getContext());
        if (username != null) {
            //tviUser.setText("Bienvenido " + new CapitalizeString(username).first());
        }

        //events
        btnAddFundo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFundo(ACTION_ADD, null);
            }
        });

        lstFundos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FundoEntity fundoEntity = (FundoEntity) adapterView.getAdapter().getItem(i);
                gotoFundo(ACTION_DETAIL, fundoEntity);
            }
        });

        /*tviLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_fundo, container, false);
    }

    private void gotoFundo(int action, FundoEntity fundoEntity) {
        Intent intent = new Intent(getView().getContext(), FundoActivity.class);

        switch (action) {
            case ACTION_ADD:
                intent.putExtra("FRAGMENT", FundoActivity.ADD_FUNDO);
                startActivity(intent);
                break;
            case ACTION_DETAIL:
                intent.putExtra("FRAGMENT", FundoActivity.DETAIL_FUNDO);
                intent.putExtra("FUNDO", fundoEntity);
                startActivity(intent);
                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mlistener = (FundosView) context ;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mlistener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
