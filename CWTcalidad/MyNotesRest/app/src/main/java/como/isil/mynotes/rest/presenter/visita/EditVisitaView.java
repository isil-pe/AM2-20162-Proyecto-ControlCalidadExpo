package como.isil.mynotes.rest.presenter.visita;

import android.content.Context;

/**
 * Created by Fabricio on 29/11/2016.
 */
public interface EditVisitaView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void editVisitaSuccess();
    boolean isConnectingToInternet(Context context);
}
