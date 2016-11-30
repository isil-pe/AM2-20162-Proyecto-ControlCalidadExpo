package como.isil.mynotes.rest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.isil.mynotes.rest.R;

import java.util.List;

import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.entity.UserEntity;
import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.presenter.fundo.FundosPresenter;
import como.isil.mynotes.rest.presenter.fundo.FundosView;
import como.isil.mynotes.rest.presenter.visita.VisitasPresenter;
import como.isil.mynotes.rest.presenter.visita.VisitasView;
import como.isil.mynotes.rest.storage.PreferencesHelper;
import como.isil.mynotes.rest.storage.db.CRUDOperations;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;
import como.isil.mynotes.rest.storage.db.MyDatabase;
import como.isil.mynotes.rest.view.adapters.FundoAdapter;
import como.isil.mynotes.rest.view.fragments.fundo.ListaFundoFragment;
import como.isil.mynotes.rest.view.fragments.visita.ListaVisitaFragment;
import como.isil.mynotes.rest.view.listeners.OnNavListener;

public class PrincipalActivity extends AppCompatActivity implements OnNavListener, FundosView , VisitasView {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private View rlayLoading,container;
    private List<FundoEntity> lsFundoEntitiesCloud;
    private List<VisitaEntity> lsVisitaEntitiesCloud;
    private FundosPresenter fundosPresenter;
    private VisitasPresenter visitasPresenter;
    private TextView tvCountVisitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Stetho.initializeWithDefaults(this);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        rlayLoading = (findViewById(R.id.rlayLoading));
       tvCountVisitas = (TextView) findViewById(R.id.tvCountVisitas);
        fundosPresenter = new FundosPresenter();
        fundosPresenter.attachedView(this);

        visitasPresenter = new VisitasPresenter();
        visitasPresenter.attachedView(this);
        container=findViewById(R.id.containerVisita);
        CRUDOperationsVisita crud = new CRUDOperationsVisita(new MyDatabase(this));

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {




                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.menuFundos:
                        changeFragment(0);
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.menuVisitas:
                        //Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();

                        if(isConnectingToInternet(getContext())){
                            loadCloudVisitas();

                        }

                        changeFragment(1);
                        return true;
                    case R.id.menuCerrarSesion:
                        //Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        logout();
                        return true;

                    default:
                        return true;

                }

            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void changeFragment(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:

                fragment = new ListaFundoFragment();
                break;
            case 1:

                Log.v("sync","change fragmet 1");
                fragment= new ListaVisitaFragment();
                break;
            case 2:
                //fragment= new HelpFragment();
                break;
        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
        }
    }

    private void logout() {
        PreferencesHelper.signOut(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showLoading() {
        Log.v("sync","show loading");
        this.rlayLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        Log.v("sync","hide loading");
        this.rlayLoading.setVisibility(View.GONE);

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onMessageError(String message) {


        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void renderVisitas(List<VisitaEntity> visitas) {
        Log.v("sync","render visitas");

        lsVisitaEntitiesCloud = visitas;
        CRUDOperationsVisita crudOperations = new CRUDOperationsVisita(new MyDatabase(this.getContext()));
        List<VisitaEntity> visitas2= crudOperations.getAllVisitas();

        for(VisitaEntity visita1 : lsVisitaEntitiesCloud) {


            int cant  = crudOperations.getVisitaContenedor(visita1.getContenedor());
            if(cant==0){
                visita1.setEstado("NO");
                visita1.setSincro("SI");
                crudOperations.addVisita(visita1);
            }

        }

        Handler handler = ListaVisitaFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
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
    public void renderFundos(List<FundoEntity> fundos) {

        Log.v("sync","render");
        lsFundoEntitiesCloud = fundos;
        CRUDOperations crudOperations = new CRUDOperations(new MyDatabase(this.getContext()));
        List<FundoEntity> fundos2= crudOperations.getAllFundosNoEliminados();

        for(FundoEntity fundo1 : lsFundoEntitiesCloud) {


           int cant  = crudOperations.getFundoNombre(fundo1.getNombreproductor());
            if(cant==0){
                fundo1.setEstado("NO");
                fundo1.setSincro("SI");

                crudOperations.addFundo(fundo1);
            }

        }

        Handler handler = ListaFundoFragment.sUpdateHandler;
        if (handler != null) {
            handler.obtainMessage().sendToTarget();
        }
    }



    private void loadCloudVisitas() {
        visitasPresenter.loadVisitas();
    }


}
