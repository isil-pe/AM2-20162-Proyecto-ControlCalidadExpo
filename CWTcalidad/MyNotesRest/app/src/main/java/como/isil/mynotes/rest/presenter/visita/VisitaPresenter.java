package como.isil.mynotes.rest.presenter.visita;

import android.util.Log;

import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.storage.entity.visita.VisitaRaw;
import como.isil.mynotes.rest.storage.entity.visita.VisitaResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fabricio on 28/11/2016.
 */
public class VisitaPresenter {


    private static final String TAG = "VisitaPresenter";
    private final String ERROR_MESSAGE= "Ocurriò un error";
    private AddVisitaView addVisitaView;



    public   void attachedView(AddVisitaView addVisitaView){
        this.addVisitaView = addVisitaView;
    }

    public  void detachView(){
        this.addVisitaView=null;
    }

    public void addVisita(
            VisitaEntity visitaEntity){
        VisitaRaw visitaRaw= new VisitaRaw();

        visitaRaw.setSemana(visitaEntity.getSemana());
        visitaRaw.setIdfundo(visitaEntity.getIdfundo());
        visitaRaw.setIdcalificacion(visitaEntity.getIdcalificacion());
        visitaRaw.setFecvisita(visitaEntity.getFecvisita());
        visitaRaw.setContenedor(visitaEntity.getContenedor());
        visitaRaw.setComentario(visitaEntity.getComentario());
        visitaRaw.setEstado(visitaEntity.getEstado());
        visitaRaw.setSincro(visitaEntity.getSincro());

        addVisitaView.showLoading();
        Call<VisitaResponse> call = ApiClient.getMyApiClient().addVisita(visitaRaw);
        call.enqueue(new Callback<VisitaResponse>() {
            @Override
            public void onResponse(Call<VisitaResponse> call, Response<VisitaResponse> response) {
                if(response.isSuccessful()){
                    addVisitaSuccess(response.body());
                }else {
                    addVisitaError(ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<VisitaResponse> call, Throwable t) {
                String json="Error ";
                try {
                    json= new StringBuffer().append(t.getMessage()).toString();
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                addVisitaError(json);
            }
        });

    }
    public void addVisitaSuccess(VisitaResponse visitaResponse){

        if(visitaResponse!=null){
            VisitaEntity visitaEntity= new VisitaEntity();
            visitaEntity.setObjectId(visitaResponse.getObjectId());

            visitaEntity.setSemana(visitaResponse.getSemana());
            visitaEntity.setIdfundo(visitaResponse.getIdfundo());
            visitaEntity.setIdcalificacion(visitaResponse.getIdcalificacion());
            visitaEntity.setFecvisita(visitaResponse.getFecvisita());
            visitaEntity.setContenedor(visitaResponse.getContenedor());
            visitaEntity.setComentario(visitaResponse.getComentario());
            addVisitaView.onAddVisitaSuccess(visitaEntity);
        }
        addVisitaView.hideLoading();

    }

    public void addVisitaError(String messageError){
        addVisitaView.hideLoading();
        addVisitaView.onMessageError(messageError);
    }
}
