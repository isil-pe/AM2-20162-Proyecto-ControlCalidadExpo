package como.isil.mynotes.rest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.isil.mynotes.rest.R;

import como.isil.mynotes.rest.utils.OnSyncCload;
import como.isil.mynotes.rest.utils.SyncCloud;

public class PruebaActivity extends AppCompatActivity implements  OnSyncCload{

    private Button button;
    private EditText time;
    private TextView finalResult;
    OnSyncCload mlistener;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        button = (Button) findViewById(R.id.btn_run);
        finalResult = (TextView) findViewById(R.id.tv_result);

        try {
            mlistener = (OnSyncCload) PruebaActivity.this;
        } catch (ClassCastException e) {
            throw new ClassCastException(PruebaActivity.this.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncCloud sc = new SyncCloud(mlistener, PruebaActivity.this);

                sc.execute();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onPreExecute() {
        progressDialog = ProgressDialog.show(PruebaActivity.this,
                "ProgressDialog",
                "Wait for check conection");
    }



    @Override
    public void onProgressUpdate(String... text) {
        finalResult.setText(text[0]);
    }

    @Override
    public void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        progressDialog.dismiss();
        finalResult.setText(result);

    }
}
