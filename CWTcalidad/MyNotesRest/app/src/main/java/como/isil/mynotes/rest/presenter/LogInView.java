package como.isil.mynotes.rest.presenter;

import android.content.Context;

/**
 * Created by em on 8/06/16.
 */
public interface LogInView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void gotoMain();
}
