package como.isil.mynotes.rest.presenter.visita;

import android.content.Context;

import java.util.List;

import como.isil.mynotes.rest.entity.VisitaEntity;

/**
 * Created by Fabricio on 28/11/2016.
 */
public interface VisitasView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void renderVisitas(List<VisitaEntity> visita);

    boolean isConnectingToInternet(Context context);
}
