package com.example.nacho.proyectosdm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ActionMode;
import android.widget.AbsListView;

public class SeleccionExtrasDialog extends DialogFragment
    implements DialogInterface.OnMultiChoiceClickListener{

    public static interface OnSeleccionExtrasRealizada {
        void onSeleccionExtraRealizada(boolean [] seleccion);
    }

    private static final String ARG_SELECCION = "seleccion";

    private OnSeleccionExtrasRealizada callback;
    private boolean[] seleccion;

    public static DialogFragment crear(boolean [] seleccion) {
        DialogFragment dialogo = new SeleccionExtrasDialog();

        Bundle args = new Bundle();
        args.putBooleanArray(ARG_SELECCION, seleccion);

        dialogo.setArguments(args);

        return dialogo;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnSeleccionExtrasRealizada) context;
        }
        catch (ClassCastException e) {
            throw new RuntimeException("No puedes usar SeleccionExtrasDialog en una clase que no implemente OnSeleccionExtrasRealizada", e);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        seleccion = getArguments().getBooleanArray(ARG_SELECCION);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Selecciona los extras que quieras")
                .setMultiChoiceItems(R.array.extras, seleccion, this)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        callback.onSeleccionExtraRealizada(seleccion);
                    }
                })
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
        seleccion[i] = b;
    }
}
