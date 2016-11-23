package como.isil.mynotes.rest;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.isil.mynotes.rest.R;



import como.isil.mynotes.rest.entity.UserEntity;
import como.isil.mynotes.rest.storage.db.CRUDOperationsUser;
import como.isil.mynotes.rest.storage.db.MyDatabase;


public class PruebaActivity extends AppCompatActivity {

    private Button btnBuscar;
    private Button btnInsert;
    private Button btnEliminar;
    private Button btnLogin;
    private TextView tvId;
    private EditText txtId;

    private EditText txtEmail;
    private EditText txtNombre;
    private EditText txtPass;


    Long id ;
    CRUDOperationsUser cruduser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        Stetho.initializeWithDefaults(this);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        txtId = (EditText) findViewById(R.id.txtId);
        tvId = (TextView) findViewById(R.id.tvId);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        cruduser = new CRUDOperationsUser(new MyDatabase(this));



        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                UserEntity user = new UserEntity();
                int id = Integer.parseInt(txtId.getText().toString());
                user  = getuser(id);

                cruduser.deleteUser(user);


            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                UserEntity user = new UserEntity();
                int id = Integer.parseInt(txtId.getText().toString());
                user  = getuser(id);

                if(user!=null){

                    tvId.setText(""+id);
                    txtEmail.setText(user.getEmail());
                    txtNombre.setText(user.getName());
                    txtPass.setText(user.getToken());
                }


            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity user = new UserEntity();
                user.setEmail(txtEmail.getText().toString());
                user.setName(txtNombre.getText().toString());
                user.setToken(txtPass.getText().toString());
                String id = String.valueOf(cruduser.addUser(user));
                tvId.setText(id);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("sync","btn login");
                UserEntity user;
                String username = txtEmail.getText().toString();
                String password =  txtPass.getText().toString();
                user  = cruduser.getUserEmail(username, password);

                if(user.getEmail()!=null ){

if(user.getEmail().equals(username)&& user.getToken().equals(password)){
                    tvId.setText(""+ user.getId());

                    txtEmail.setText(user.getEmail());
                    txtNombre.setText(user.getName());
                    txtPass.setText(user.getToken());

                    Toast.makeText(PruebaActivity.this, "Login succe", Toast.LENGTH_SHORT).show();}
                }else
                {

                    Toast.makeText(PruebaActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public UserEntity getuser(int id){

        UserEntity user = cruduser.getUser(id);
        return user;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }






}
