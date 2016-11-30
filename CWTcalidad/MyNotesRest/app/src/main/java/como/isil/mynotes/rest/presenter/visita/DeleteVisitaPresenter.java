package como.isil.mynotes.rest.presenter.visita;

import android.util.Log;

import como.isil.mynotes.rest.storage.entity.visita.VisitaResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fabricio on 29/11/2016.
 */
public class DeleteVisitaPresenter {

    private static final String TAG = "DeleteEmbPresenter";
    private DeleteVisitaView deleteVisitaView;
    private String objectId;

    public   void attachedView(DeleteVisitaView deleteVisitaView){

        this.deleteVisitaView = deleteVisitaView;
    }

    public  void detachView(){
        this.deleteVisitaView=null;
    }

    public void deleteEmbarque(String objectId) {
        this.deleteVisitaView.showLoading();

        Call<VisitaResponse> deleteRequest = ApiClient.getMyApiClient().deleteVisita(objectId);
        deleteRequest.enqueue(new Callback<VisitaResponse>() {
            @Override
            public void onResponse(Call<VisitaResponse> call, Response<VisitaResponse> response) {
                // use response.code, response.headers, etc.
                deleteVisitaSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VisitaResponse> call, Throwable t) {
                // handle failure
            }
        });

    }

    private void deleteVisitaSuccess(VisitaResponse visitaResponse) {
        this.deleteVisitaView.hideLoading();
        this.deleteVisitaView.deleteVisitaSuccess();
    }
    private void deleteVisitaError(String messageError){
        this.deleteVisitaView.hideLoading();
        this.deleteVisitaView.onMessageError(messageError);
    }
}
