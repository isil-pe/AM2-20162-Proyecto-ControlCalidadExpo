package como.isil.mynotes.rest.presenter.fundo;

import android.content.Context;



import java.util.List;

import como.isil.mynotes.rest.entity.FundoEntity;

/**
 * Created by em on 8/06/16.
 */
public interface FundosView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void renderFundos(List<FundoEntity> fundos);

}
