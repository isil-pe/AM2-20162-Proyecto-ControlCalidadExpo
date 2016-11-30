package como.isil.mynotes.rest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.isil.mynotes.rest.R;



import como.isil.mynotes.rest.entity.UserEntity;
import como.isil.mynotes.rest.presenter.LogInPresenter;
import como.isil.mynotes.rest.presenter.LogInView;
import como.isil.mynotes.rest.storage.PreferencesHelper;

import como.isil.mynotes.rest.storage.db.CRUDOperationsUser;
import como.isil.mynotes.rest.storage.db.MyDatabase;



public class LoginActivity extends ActionBarActivity implements LogInView  {

    private Button btnLogin;
    private EditText eteUsername;
    private EditText etePassword;
    private String username;
    private String password;
    private View rlayLoading,container;



    private LogInPresenter logInPresenter;
    CRUDOperationsUser cruduser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Stetho.initializeWithDefaults(this);
        logInPresenter= new LogInPresenter();
        logInPresenter.attachedView(this);
        init();





    }

    private void init() {
        eteUsername=(EditText)findViewById(R.id.eteUsername);
        etePassword=(EditText)findViewById(R.id.etePassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        rlayLoading=findViewById(R.id.rlayLoading);
        container=findViewById(R.id.container);
         cruduser= new CRUDOperationsUser(new MyDatabase(this));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    //gotoMain();




                    login(isConnectingToInternet(getContext()));




                }
            }
        });


        etePassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event!=null){
                    Log.v("CONSOLE ","keycode "+event.getKeyCode()+ " actionId "+actionId);
                }

                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if (validateForm()) {
                        login(isConnectingToInternet(getContext()));
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void gotoMain() {
        savePreferences();
       Intent intent= new Intent(this,PrincipalActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


    }

    private void savePreferences() {
        PreferencesHelper.saveSession(this,username,password);


        cruduser.dropTableUser();
        UserEntity user = new UserEntity();
        user.setEmail(username);
        user.setToken(password);
        cruduser.addUser(user);
    }

    private boolean validateForm() {
        username= eteUsername.getText().toString();
        password= etePassword.getText().toString();

        if(username.isEmpty())
        {
            eteUsername.setError("Error campo username");
            return false;
        }
        if(password.isEmpty())
        {
            etePassword.setError("Error campo password");
            return false;
        }



        return true;
    }

    public Boolean loginLocal(String username, String password){



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
                .make(container,message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }






    public void login(boolean internet){
        if(internet){

            logInPresenter.logIn(username,password);

        }else{
            showLoading();
            if(cruduser.getUserEmail(username,password).getEmail()!=null){
                hideLoading();
                gotoMain();
            }else{
                hideLoading();
                Snackbar snackbar = Snackbar
                        .make(container,"Login fallo BD Local", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

    }}
}
