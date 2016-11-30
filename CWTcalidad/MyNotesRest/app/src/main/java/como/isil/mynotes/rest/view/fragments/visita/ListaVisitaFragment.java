package como.isil.mynotes.rest.view.fragments.visita;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.os.Handler;
import com.isil.mynotes.rest.R;

import java.util.List;

import como.isil.mynotes.rest.VisitaActivity;
import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.presenter.visita.VisitasView;
import como.isil.mynotes.rest.storage.PreferencesHelper;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;
import como.isil.mynotes.rest.storage.db.MyDatabase;
import como.isil.mynotes.rest.view.adapters.VisitaAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaVisitaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaVisitaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaVisitaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    private ListView lstVisitas;
    private List<VisitaEntity> visitas;
    private CRUDOperationsVisita crudOperations;
    private VisitaAdapter visitaAdapter;
    private static final int ACTION_ADD = 1;
    private static final int ACTION_DETAIL = 2;
    public static Handler sUpdateHandler;
    Button btnAddVisita;

    VisitasView mlistener;

    public ListaVisitaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaVisitaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaVisitaFragment newInstance(String param1, String param2) {
        ListaVisitaFragment fragment = new ListaVisitaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void loadData() {
        crudOperations = new CRUDOperationsVisita(new MyDatabase(getView().getContext()));
        visitas= crudOperations.getAllVisitas();
        visitaAdapter = new VisitaAdapter(getView().getContext(),visitas);
        lstVisitas.setAdapter(visitaAdapter);


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
        Log.v("sync","list fragment create");
        loadData();
    }

    private void init() {
        Log.v("sync","init lista vista fragment");
        //tviLogout = (TextView) findViewById(R.id.tviLogout);
        //tviUser = (TextView) findViewById(R.id.tviUser);
        lstVisitas = (ListView)getView().findViewById(R.id.lstVisitas);
        btnAddVisita = (Button) getView().findViewById(R.id.btnAddvisita);
        //rlayLoading = (findViewById(R.id.rlayLoading));

        //user Info
        String username = PreferencesHelper.getUserSession(getView().getContext());
        if (username != null) {
            //tviUser.setText("Bienvenido " + new CapitalizeString(username).first());
        }

        //events
        btnAddVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFundo(ACTION_ADD, null);
            }
        });

        lstVisitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VisitaEntity visitaEntity = (VisitaEntity) adapterView.getAdapter().getItem(i);
                gotoFundo(ACTION_DETAIL, visitaEntity);
            }
        });

        /*tviLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });*/
    }

    private void gotoFundo(int action, VisitaEntity visitaEntity) {
        Intent intent = new Intent(getView().getContext(), VisitaActivity.class);

        switch (action) {
            case ACTION_ADD:
                intent.putExtra("FRAGMENT", VisitaActivity.ADD_VISITA);
                startActivity(intent);
                break;
            case ACTION_DETAIL:
                intent.putExtra("FRAGMENT", VisitaActivity.DETAIL_VISITA);
                intent.putExtra("VISITA", visitaEntity);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_visita, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistener = (VisitasView) context ;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
