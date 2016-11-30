package como.isil.mynotes.rest.presenter.visita;

import android.content.Context;

import como.isil.mynotes.rest.entity.VisitaEntity;

/**
 * Created by Fabricio on 28/11/2016.
 */
public interface AddVisitaView {

    void showLoading();
    void hideLoading();
    Context getContext();
    void onMessageError(String message);
    void onAddVisitaSuccess(VisitaEntity visitaEntity);
    void addVisitaCloud(VisitaEntity visitaEntity);
    boolean isConnectingToInternet(Context context);
}
