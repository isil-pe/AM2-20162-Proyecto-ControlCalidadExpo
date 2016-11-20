package como.isil.mynotes.rest.presenter.fundo;

import android.util.Log;

import como.isil.mynotes.rest.entity.FundoEntity;

import como.isil.mynotes.rest.storage.entity.fundo.FundoRaw;
import como.isil.mynotes.rest.storage.entity.fundo.FundoResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by em on 8/06/16.
 */
public class FundoPresenter {

    private static final String TAG = "FundoPresenter";
    private final String ERROR_MESSAGE= "Ocurriò un error";
    private AddFundoView addFundoView;
    private String name,description;

    public   void attachedView(AddFundoView addFundoView){
        this.addFundoView = addFundoView;
    }

    public  void detachView(){
        this.addFundoView =null;
    }

    public void addFundo(int id, String nombre, String estado, String sincro ){
        FundoRaw fundoRaw= new FundoRaw();
        fundoRaw.setIdproductor(id);
        fundoRaw.setNombreproductor(nombre);
        fundoRaw.setEstado(estado);
        fundoRaw.setSincro(sincro);

        addFundoView.showLoading();
        Call<FundoResponse> call = ApiClient.getMyApiClient().addFundo(fundoRaw);
        call.enqueue(new Callback<FundoResponse>() {
            @Override
            public void onResponse(Call<FundoResponse> call, Response<FundoResponse> response) {
                if(response.isSuccessful()){
                    addFundoSuccess(response.body());
                }else {
                    addFundoError(ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<FundoResponse> call, Throwable t) {
                String json="Error ";
                try {
                    json= new StringBuffer().append(t.getMessage()).toString();
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                addFundoError(json);
            }
        });

    }
    public void addFundoSuccess(FundoResponse fundoResponse){

        if(fundoResponse !=null){
            FundoEntity fundoEntity= new FundoEntity();
            fundoEntity.setObjectId(fundoResponse.getObjectId());
            fundoEntity.setIdproductor(fundoResponse.getIdproductor());
            fundoEntity.setNombreproductor(fundoResponse.getNombreproductor());
            fundoEntity.setEstado(fundoResponse.getEstado());
            fundoEntity.setSincro(fundoResponse.getSincro());
        }
        addFundoView.hideLoading();
        addFundoView.onAddFundoSuccess();
    }

    public void addFundoError(String messageError){
        addFundoView.hideLoading();
        addFundoView.onMessageError(messageError);
    }
}
