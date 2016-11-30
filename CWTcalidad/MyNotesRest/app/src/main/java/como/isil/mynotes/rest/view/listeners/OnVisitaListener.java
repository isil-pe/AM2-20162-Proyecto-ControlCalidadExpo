package como.isil.mynotes.rest.view.listeners;

import como.isil.mynotes.rest.entity.VisitaEntity;
import como.isil.mynotes.rest.storage.db.CRUDOperationsVisita;

/**
 * Created by Fabricio on 28/11/2016.
 */
public interface OnVisitaListener {

    CRUDOperationsVisita getCrudOperations();
    void deleteVisita(VisitaEntity visitaEntity);
    void updateVisita(VisitaEntity visitaEntity);
    void showParentLoading();
    void hideParentLoading();
    void showMessage(String message);}
