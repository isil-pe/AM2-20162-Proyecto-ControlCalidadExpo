package como.isil.mynotes.rest.view.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import como.isil.mynotes.rest.entity.FundoEntity;


/**
 * Created by emedinaa on 15/09/15.
 */
public class MyDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String title = getArguments().getString("TITLE");
        final int type = getArguments().getInt("TYPE");
        final FundoEntity fundoEntity= (FundoEntity)getArguments().
                getSerializable("FUNDOENTITY");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MyDialogListener)getActivity()).
                                        onPositiveListener(fundoEntity,type);
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
