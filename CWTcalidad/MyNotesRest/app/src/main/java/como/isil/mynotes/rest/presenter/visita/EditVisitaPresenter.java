package como.isil.mynotes.rest.presenter.visita;

import como.isil.mynotes.rest.storage.entity.visita.EditVisitaRaw;
import como.isil.mynotes.rest.storage.entity.visita.VisitaResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fabricio on 29/11/2016.
 */
public class EditVisitaPresenter {

    private static final String TAG = "EditVisitaPresenter";
    private EditVisitaView editVisitaView;

    private int idvisita;
    private String semana;
    private String idfundo;
    private String idcalificacion;
    private String fecvisita;
    private String contenedor;
    private String comentario;
    private String objectId;
    private String estado;
    private String sincro;


    public   void attachedView(EditVisitaView editVisitaView){

        this.editVisitaView = editVisitaView;
    }

    public  void detachView(){
        this.editVisitaView=null;
    }

    public void EditVisita(String objectId, int idvisita, String semana, String idfundo, String idcalificacion, String fecvisita, String contenedor, String comentario,  String estado, String sincro) {
        EditVisitaRaw editVisitaRaw = new EditVisitaRaw();
        editVisitaRaw.setIdvisita(idvisita);
        editVisitaRaw.setSemana(semana);
        editVisitaRaw.setIdfundo(idfundo);
        editVisitaRaw.setIdcalificacion(idcalificacion);
        editVisitaRaw.setFecvisita(fecvisita);
        editVisitaRaw.setContenedor( contenedor);
        editVisitaRaw.setComentario ( comentario);
        editVisitaRaw.setObjectId ( objectId);
        editVisitaRaw.setEstado ( estado);
        editVisitaRaw.setSincro ( sincro);

        editVisitaView.showLoading();
        Call<VisitaResponse> updateRequest = ApiClient.getMyApiClient().editVisita(editVisitaRaw);
        updateRequest.enqueue(new Callback<VisitaResponse>() {
            @Override
            public void onResponse(Call<VisitaResponse> call, Response<VisitaResponse> response) {
                // use response.code, response.headers, etc.
                editVisitaSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VisitaResponse> call, Throwable t) {
                // handle failure
            }
        });
    }

    private void editVisitaSuccess(VisitaResponse visitaResponse) {
        editVisitaView.hideLoading();
        editVisitaView.editVisitaSuccess();
    }

    private void editVisitaError(String messageError) {
        editVisitaView.hideLoading();
        editVisitaView.onMessageError(messageError);}
}
