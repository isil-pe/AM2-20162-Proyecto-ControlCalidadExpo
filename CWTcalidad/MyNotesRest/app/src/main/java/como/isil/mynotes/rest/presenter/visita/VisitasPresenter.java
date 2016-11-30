package como.isil.mynotes.rest.presenter.visita;

import android.util.Log;

import java.util.List;

import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.storage.entity.visita.VisitasResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fabricio on 28/11/2016.
 */
public class VisitasPresenter {
    private static final String TAG = "VisitasPresenter";
    private final String ERROR_MESSAGE= "Ocurriò un error";

    private VisitasView visitasView;

    public   void attachedView(VisitasView visitasView){
        this.visitasView = visitasView;
    }

    public  void detachView(){
        this.visitasView=null;
    }

    public void loadVisitas(){
        visitasView.showLoading();

        Call<VisitasResponse> call= ApiClient.getMyApiClient().visitas();
        call.enqueue(new Callback<VisitasResponse>() {
            @Override
            public void onResponse(Call<VisitasResponse> call, Response<VisitasResponse> response) {
                if(response.isSuccessful()){

                    visitasSuccess(response.body());
                }else {
                    visitasError(ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<VisitasResponse> call, Throwable t) {
                String json="Error ";
                try {
                    json= new StringBuffer().append(t.getMessage()).toString();
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                visitasError(json);
            }
        });
    }

    private void visitasSuccess(VisitasResponse visitasResponse) {
        visitasView.hideLoading();

        if(visitasResponse!=null){
            List<VisitaEntity> visitas= visitasResponse.getData();
            visitasView.renderVisitas(visitas);
        }

    }
    private void visitasError(String messageError){
        visitasView.hideLoading();
        visitasView.onMessageError(messageError);
    }
}
