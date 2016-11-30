package como.isil.mynotes.rest.view.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import como.isil.mynotes.rest.entity.VisitaEntity;

/**
 * Created by Fabricio on 29/11/2016.
 */
public class MyDialogFragmentVisita extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String title = getArguments().getString("TITLE");
        final int type = getArguments().getInt("TYPE");
        final VisitaEntity visitaEntity= (VisitaEntity)getArguments().
                getSerializable("VISITAENTITY");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MyDialogListener)getActivity()).
                                        onPositiveListener(visitaEntity,type);
                            }
                        }
                )
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MyDialogListener)getActivity()).
                                        onNegativeListener(null,type);
                            }
                        }
                )
                .create();
    }
}
