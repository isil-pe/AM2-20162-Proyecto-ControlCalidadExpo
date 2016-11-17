package como.isil.mynotes.rest.presenter.fundo;

import android.content.Context;

/**
 * Created by em on 8/06/16.
 */
public interface AddFundoView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void onAddFundoSuccess();
}
