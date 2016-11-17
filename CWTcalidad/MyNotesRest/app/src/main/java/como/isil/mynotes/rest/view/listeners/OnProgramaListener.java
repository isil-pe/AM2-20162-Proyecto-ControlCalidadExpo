package como.isil.mynotes.rest.view.listeners;

import como.isil.mynotes.rest.entity.ProgramaEntity;
import como.isil.mynotes.rest.storage.db.CRUDOperationsPrograma;

/**
 * Created by Fabricio on 15/11/2016.
 */
public interface OnProgramaListener {

    CRUDOperationsPrograma getCrudOperations();
    void deletePrograma(ProgramaEntity programaEntity);
    void showParentLoading();
    void hideParentLoading();
    void showMessage(String message);
}
