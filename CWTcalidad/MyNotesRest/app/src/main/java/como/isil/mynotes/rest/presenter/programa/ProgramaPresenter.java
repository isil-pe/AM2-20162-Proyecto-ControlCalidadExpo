package como.isil.mynotes.rest.presenter.programa;

import android.util.Log;

import como.isil.mynotes.rest.entity.ProgramaEntity;
import como.isil.mynotes.rest.storage.entity.programa.ProgramaRaw;
import como.isil.mynotes.rest.storage.entity.programa.ProgramaResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by em on 8/06/16.
 */
public class ProgramaPresenter {

    private static final String TAG = "ProgramaPresenter";
    private final String ERROR_MESSAGE= "Ocurriò un error";
    private AddProgramaView addProgramaView;
    private String id,nombre;

    public   void attachedView(AddProgramaView addProgramaView){
        this.addProgramaView = addProgramaView;
    }

    public  void detachView(){
        this.addProgramaView =null;
    }

    public void addPrograma(int id, String nombre ){
        ProgramaRaw programaRaw= new ProgramaRaw();
        programaRaw.setIdprograma(id);
        programaRaw.setNombreprograma(nombre);

        addProgramaView.showLoading();
        Call<ProgramaResponse> call = ApiClient.getMyApiClient().addPrograma(programaRaw);
        call.enqueue(new Callback<ProgramaResponse>() {
            @Override
            public void onResponse(Call<ProgramaResponse> call, Response<ProgramaResponse> response) {
                if(response.isSuccessful()){
                    addProgramaSuccess(response.body());
                }else {
                    addProgramaError(ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProgramaResponse> call, Throwable t) {
                String json="Error ";
                try {
                    json= new StringBuffer().append(t.getMessage()).toString();
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                addProgramaError(json);
            }
        });

    }
    public void addProgramaSuccess(ProgramaResponse programaResponse){

        if(programaResponse !=null){
            ProgramaEntity programaEntity= new ProgramaEntity();
            programaEntity.setObjectId(programaResponse.getObjectId());
            programaEntity.setIdprograma(programaResponse.getIdproductor());
            programaEntity.setNombreprograma(programaResponse.getNombreproductor());
        }
        addProgramaView.hideLoading();
        addProgramaView.onAddProgramaSuccess();
    }

    public void addProgramaError(String messageError){
        addProgramaView.hideLoading();
        addProgramaView.onMessageError(messageError);
    }
}
