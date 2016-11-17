package como.isil.mynotes.rest.view.listeners;

import como.isil.mynotes.rest.entity.FundoEntity;

import como.isil.mynotes.rest.storage.db.CRUDOperations;

/**
 * Created by emedinaa on 15/09/15.
 */
public interface OnFundoListener {

     CRUDOperations getCrudOperations();
     void deleteFundo(FundoEntity fundoEntity);
     void showParentLoading();
     void hideParentLoading();
     void showMessage(String message);
}
