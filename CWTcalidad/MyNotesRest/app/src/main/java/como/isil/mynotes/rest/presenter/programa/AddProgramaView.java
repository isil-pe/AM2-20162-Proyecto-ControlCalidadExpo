package como.isil.mynotes.rest.presenter.programa;

import android.content.Context;

/**
 * Created by em on 8/06/16.
 */
public interface AddProgramaView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void onAddProgramaSuccess();
}
