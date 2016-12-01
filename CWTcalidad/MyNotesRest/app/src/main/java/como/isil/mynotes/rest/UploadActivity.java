package como.isil.mynotes.rest;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;
import com.isil.mynotes.rest.R;

import java.io.File;
import java.text.DateFormat;

import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.presenter.visita.EditVisitaView;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;
import como.isil.mynotes.rest.storage.db.MyDatabase;


public class UploadActivity extends AppCompatActivity implements EditVisitaView {

    private static final String TAG = "MainActivity";
    private View btnLogInDropbox,btnUpload;
    private TextView tviName;
    private TextView tviEmail;
    private TextView tviType;
    public final static String EXTRA_PATH = "FilesActivity_Path";
    private static final int PICKFILE_REQUEST_CODE = 1;
    private String mPath;
    private String accessToken=null;
    CRUDOperationsVisita crudvisita;
    private ImageView ivifotoVisita;
    String idvisita ;
    String pathFotosubida;
    Uri selectedImageUri;
    String contenedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        btnLogInDropbox= findViewById(R.id.btnLogInDropbox);
        btnUpload= findViewById(R.id.btnUpload);
        tviName= (TextView) findViewById(R.id.tviName);
        tviEmail= (TextView) findViewById(R.id.tviEmail);
        tviType= (TextView) findViewById(R.id.tviType);
            btnLogInDropbox.setVisibility(View.GONE);
        validateExtras();

        ivifotoVisita = (ImageView) findViewById(R.id.iviFotoVisita);
        crudvisita = new CRUDOperationsVisita(new MyDatabase(this));

        if (ContextCompat.checkSelfPermission(getBaseContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(UploadActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    14);

        }

        if(accessToken==null){
            accessToken= Auth.getOAuth2Token();
            if(accessToken!=null){
                loadData();

            }
            Log.v(TAG , "accessToken "+accessToken);
            Auth.startOAuth2Authentication(UploadActivity.this, "svc2vr7355m1hos");
        }





        btnLogInDropbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(UploadActivity.this, "svc2vr7355m1hos");

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    ActivityCompat.requestPermissions(UploadActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            14);

                }
                launchFilePicker();

            }
        });

        String path = getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(accessToken==null){
            accessToken= Auth.getOAuth2Token();
            if(accessToken!=null){
                loadData();

            }
            Log.v(TAG , "accessToken "+accessToken);
        }
    }


    private void loadData(){
        DropboxClientFactory.init(accessToken);
        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                // ((TextView) findViewById(R.id.email_text)).setText(result.getEmail());
                // ((TextView) findViewById(R.id.name_text)).setText(result.getName().getDisplayName());
                // ((TextView) findViewById(R.id.type_text)).setText(result.getAccountType().name());

                Log.v(TAG, "email "+result.getEmail());
                Log.v(TAG, "name "+result.getName().getDisplayName());
                Log.v(TAG, "type "+result.getAccountType().name());
                String email= result.getEmail();
                String name= result.getName().getDisplayName();
                String type= result.getAccountType().name();
                tviName.setText("Name "+name );
                tviEmail.setText("Email "+email);
                tviType.setText("Foto para el Contenedor: "+contenedor);



                }




            @Override
            public void onError(Exception e) {
                Log.e(getClass().getName(), "Failed to get account details.", e);
            }
        }).execute();
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void validateExtras() {
        if(getIntent().getExtras()!=null)
        {
            idvisita= getIntent().getStringExtra("EXTRA_ID_VISITA");
            contenedor = getIntent().getStringExtra("EXTRA_ID_CONTENEDO");
        }
    }
    private void launchFilePicker() {
        // Launch intent to pick file for upload
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // This is the result of a call to launchFilePicker
                selectedImageUri = data.getData();
                pathFotosubida = getRealPathFromURI(this, selectedImageUri);
                uploadFile(data.getData().toString());
            }
        }
    }

    private void uploadFile(String fileUri) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Uploading");
        dialog.show();

        new UploadFileTask(this, DropboxClientFactory.getClient(), new UploadFileTask.Callback() {
            @Override
            public void onUploadComplete(FileMetadata result) {
                dialog.dismiss();

              //  String message = result.getName() + " size " + result.getSize() + " modified " +
                //        DateFormat.getDateTimeInstance().format(result.getClientModified());


                String message = result.getName();

                VisitaEntity visita = crudvisita.getVisita(Integer.parseInt(idvisita));
                visita.setEstado(pathFotosubida);
                crudvisita.updateVisita(visita);



                Toast.makeText(UploadActivity.this, message, Toast.LENGTH_SHORT)
                        .show();
                ivifotoVisita.setImageURI(null);
                ivifotoVisita.setImageURI(selectedImageUri);

                // Reload the folder
                //loadData();
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to upload file.", e);
                Toast.makeText(UploadActivity.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(fileUri, mPath);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onMessageError(String message) {

    }

    @Override
    public void editVisitaSuccess() {

    }

    @Override
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
