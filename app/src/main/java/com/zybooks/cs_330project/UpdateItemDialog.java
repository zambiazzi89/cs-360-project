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

public class UpdateItemDialog extends AppCompatDialogFragment {
    private TextInputEditText updateItemTitle;
    private TextInputEditText updateItemQuantity;
    private TextInputEditText updateItemUnits;
    private UpdateItemDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int itemIndex = getArguments().getInt("itemIndex");
        long id = getArguments().getLong("id");
        String oldTitle = getArguments().getString("title");
        String oldQuantity = getArguments().getString("quantity");
        String oldUnits = getArguments().getString("units");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_item, null);

        builder.setView(view)
                .setTitle("Update Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }})
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String itemTitle = updateItemTitle.getText().toString();
                        String itemQuantity = updateItemQuantity.getText().toString();
                        String itemUnits = updateItemUnits.getText().toString();
                        if (itemTitle == "" || itemQuantity == "" || itemUnits == "") {
                            Toast toast = Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            listener.updateItemFromDialog(id, itemIndex, itemTitle, itemQuantity, itemUnits);
                        }
                    }
                });

        updateItemTitle = view.findViewById(R.id.addItemTitleInput);
        updateItemQuantity = view.findViewById(R.id.addItemQuantityInput);
        updateItemUnits = view.findViewById(R.id.addItemUnitsInput);

        updateItemTitle.setText(oldTitle);
        updateItemQuantity.setText(oldQuantity);
        updateItemUnits.setText(oldUnits);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (UpdateItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddItemDialogListener");
        }
    }

    public interface UpdateItemDialogListener{
        void updateItemFromDialog(long id, int itemIndex, String updatedItemTitle, String updatedItemQuantity, String updatedItemUnits);
    }
}
