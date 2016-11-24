package como.isil.mynotes.rest.presenter.fundo;

import android.util.Log;

import como.isil.mynotes.rest.entity.FundoEntity;

import como.isil.mynotes.rest.storage.entity.fundo.FundosResponse;
import como.isil.mynotes.rest.storage.request.ApiClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by em on 8/06/16.
 */
public class FundosPresenter {

    private static final String TAG = "FundosPresenter";
    private final String ERROR_MESSAGE= "Ocurriò un error";

    private FundosView fundosView;

    public   void attachedView(FundosView fundosView){
        this.fundosView = fundosView;
    }

    public  void detachView(){
        this.fundosView =null;
    }

    public void loadFundos(){
        fundosView.showLoading();

        Call<FundosResponse> call= ApiClient.getMyApiClient().fundos();
        call.enqueue(new Callback<FundosResponse>() {
            @Override
            public void onResponse(Call<FundosResponse> call, Response<FundosResponse> response) {
                if(response.isSuccessful()){

                    fundosSuccess(response.body());
                }else {
                    fundosError(ERROR_MESSAGE);
                }
            }

            @Override
            public void onFailure(Call<FundosResponse> call, Throwable t) {
                String json="Error ";
                try {
                    json= new StringBuffer().append(t.getMessage()).toString();
                }catch (NullPointerException e) {}
                Log.v(TAG, "json >>>> " + json);

                fundosError(json);
            }
        });
    }

    private void fundosSuccess(FundosResponse fundosResponse) {
        fundosView.hideLoading();

        if(fundosResponse !=null){
            List<FundoEntity> fundos= fundosResponse.getData();

            fundosView.renderFundos(fundos);
        }

    }
    private void fundosError(String messageError){
        fundosView.hideLoading();
        fundosView.onMessageError(messageError);
    }
}
