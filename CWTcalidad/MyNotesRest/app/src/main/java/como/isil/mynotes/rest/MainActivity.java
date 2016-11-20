package como.isil.mynotes.rest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.facebook.stetho.Stetho;
import com.isil.mynotes.rest.R;


import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.presenter.fundo.FundosPresenter;
import como.isil.mynotes.rest.presenter.fundo.FundosView;
import como.isil.mynotes.rest.storage.PreferencesHelper;
import como.isil.mynotes.rest.storage.db.CRUDOperations;
import como.isil.mynotes.rest.storage.db.MyDatabase;
import como.isil.mynotes.rest.utils.CapitalizeString;
import como.isil.mynotes.rest.utils.OnSyncCload;
import como.isil.mynotes.rest.utils.SyncCloud;
import como.isil.mynotes.rest.view.adapters.FundoAdapter;

import java.util.List;


public class MainActivity extends ActionBarActivity implements FundosView {

    private static final String TAG = "MainActivity";
    private static final int ACTION_ADD = 1;
    private static final int ACTION_DETAIL = 2;

    private TextView tviLogout, tviUser;
    private ListView lstFundos;
    private Button btnAddFundo;
    private View rlayLoading, container;
    private List<FundoEntity> lsFundoEntities;
    private CRUDOperations crudOperations;
    private FundoAdapter fundoAdapter;

    private FundosPresenter fundosPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //populate();
        fundosPresenter = new FundosPresenter();
        fundosPresenter.attachedView(this);
        Stetho.initializeWithDefaults(this);
        init();




        //loadData();
        //loadCloud();

    }

    private void loadCloud() {
        fundosPresenter.loadFundos();
    }

    private void loadData() {
        crudOperations = new CRUDOperations(new MyDatabase(this));
        lsFundoEntities= crudOperations.getAllFundosNoEliminados();
         fundoAdapter = new FundoAdapter(this,lsFundoEntities);
        lstFundos.setAdapter(fundoAdapter);


    }

    private void populate() {

        CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(this));
        // crudOperations.addNote(new NoteEntity("Mi Nota","Esta es un nota ",null));
        // crudOperations.addNote(new NoteEntity("Segunda Nota","Esta es la segunds nota ",null));
        //crudOperations.addNote(new NoteEntity("Tercera Nota","Esta es la tercera nota ",null));
        //crudOperations.addNote(new NoteEntity("Cuarta Nota","Esta es la cuarta nota ",null));
        //crudOperations.addNote(new NoteEntity("Quinta Nota","Esta es la quinta nota ",null));
        //crudOperations.addNote(new NoteEntity("Sexta Nota","Esta es la sexta nota ",null));

        Log.v(TAG, "populate " + crudOperations.getAllFundos());
    }

    private void init() {
        tviLogout = (TextView) findViewById(R.id.tviLogout);
        tviUser = (TextView) findViewById(R.id.tviUser);
        lstFundos = (ListView) (findViewById(R.id.lstFundos));
        btnAddFundo = (Button) (findViewById(R.id.btnAddFundo));
        rlayLoading = (findViewById(R.id.rlayLoading));

        //user Info
        String username = PreferencesHelper.getUserSession(this);
        if (username != null) {
            tviUser.setText("Bienvenido " + new CapitalizeString(username).first());
        }

        //events
        btnAddFundo.setOnClickListener(new View.OnClickListener() {
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

        tviLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void gotoFundo(int action, FundoEntity fundoEntity) {
        Intent intent = new Intent(this, FundoActivity.class);

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

    private void logout() {
        PreferencesHelper.signOut(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResumen");
        loadData();
        //loadCloud();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
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
                .make(container, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void renderFundos(List<FundoEntity> fundos) {
        lsFundoEntities = fundos;
        fundoAdapter = new FundoAdapter(this, lsFundoEntities);
        lstFundos.setAdapter(fundoAdapter);
    }
}
