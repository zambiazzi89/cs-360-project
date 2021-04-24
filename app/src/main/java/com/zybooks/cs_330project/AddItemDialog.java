package com.zybooks.cs_330project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class AddItemDialog extends AppCompatDialogFragment {
    private TextInputEditText addItemTitle;
    private TextInputEditText addItemQuantity;
    private TextInputEditText addItemUnits;
    private AddItemDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_item, null);

        builder.setView(view)
                .setTitle("Add Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }})
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String itemTitle = addItemTitle.getText().toString();
                        String itemQuantity = addItemQuantity.getText().toString();
                        String itemUnits = addItemUnits.getText().toString();
                        if (itemTitle == "" || itemQuantity == "" || itemUnits == "") {
                            Toast toast = Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            listener.getItemFromDialog(itemTitle, itemQuantity, itemUnits);
                        }
                    }
                });

        addItemTitle = view.findViewById(R.id.addItemTitleInput);
        addItemQuantity = view.findViewById(R.id.addItemQuantityInput);
        addItemUnits = view.findViewById(R.id.addItemUnitsInput);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddItemDialogListener");
        }
    }

    public interface AddItemDialogListener{
        void getItemFromDialog(String addItemTitle, String addItemQuantity, String addItemUnits);
    }
}
