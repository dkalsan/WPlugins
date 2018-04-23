package com.dkalsan.wplugins.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;

public class LimitPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final NumberPicker numberPicker = new NumberPicker(getActivity());

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Izberi število vtičnikov.")
                .setPositiveButton("Nastavi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        valueChangeListener.onValueChange(numberPicker, numberPicker.getValue(), numberPicker.getValue());
                    }
                })
                .setNegativeButton("Prekliči", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        valueChangeListener.onValueChange(numberPicker, 19, 20);
                    }
                })
                .setView(numberPicker);

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        super.onCancel(dialog);
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}