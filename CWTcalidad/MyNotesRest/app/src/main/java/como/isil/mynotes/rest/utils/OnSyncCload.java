package como.isil.mynotes.rest.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Fabricio on 19/11/2016.
 */
public interface OnSyncCload {


    public void onPreExecute();
    public void onProgressUpdate(String... text);
    public void onPostExecute(String result);
}
