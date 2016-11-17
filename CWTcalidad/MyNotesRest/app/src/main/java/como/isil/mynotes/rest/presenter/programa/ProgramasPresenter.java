package como.isil.mynotes.rest.presenter.programa;

import android.util.Log;

import java.util.List;

import como.isil.mynotes.rest.entity.ProgramaEntity;
import como.isil.mynotes.rest.storage.entity.programa.ProgramasResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by em on 8/06/16.
 */
public class ProgramasPresenter {

    private static final String TAG = "ProgramasPresenter";
    private final String ERROR_MESSAGE= "Ocurriò un error";

    private ProgramaView programasView;

    public   void attachedView(ProgramaView programasView){
        this.programasView = programasView;
    }

    public  void detachView(){
        this.programasView =null;
    }

    public void loadProgramas(){
        programasView.showLoading();

        Call<ProgramasResponse> call= ApiClient.getMyApiClient().programas();
        call.enqueue(new Callback<ProgramasResponse>() {
            @Override
            public void onResponse(Call<ProgramasResponse> call, Response<ProgramasResponse> response) {
                if(response.isSuccessful()){

                    programasSuccess(response.body());
                }else {
                    programasError(ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<ProgramasResponse> call, Throwable t) {
                String json="Error ";
                try {
                    json= new StringBuffer().append(t.getMessage()).toString();
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                programasError(json);
            }
        });
    }

    private void programasSuccess(ProgramasResponse programasResponse) {
        programasView.hideLoading();

        if(programasResponse !=null){
            List<ProgramaEntity> programas= programasResponse.getData();
            programasView.renderProgramas(programas);
        }

    }
    private void programasError(String messageError){
        programasView.hideLoading();
        programasView.onMessageError(messageError);
    }
}
