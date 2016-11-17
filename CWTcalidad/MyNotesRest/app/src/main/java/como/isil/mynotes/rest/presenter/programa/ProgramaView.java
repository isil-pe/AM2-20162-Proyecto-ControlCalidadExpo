package como.isil.mynotes.rest.presenter.programa;

import android.content.Context;

import java.util.List;

import como.isil.mynotes.rest.entity.FundoEntity;
import como.isil.mynotes.rest.entity.ProgramaEntity;

/**
 * Created by em on 8/06/16.
 */
public interface ProgramaView {

    void showLoading();
    void hideLoading();
    Context getContext();

    void onMessageError(String message);
    void renderProgramas(List<ProgramaEntity> programas);

}
