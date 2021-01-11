package edu.gatech.seclass.words6300;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class AddTileDialog extends DialogFragment {

    private EditText editText_char;
    private EditText editText_value;
    private String title;
    //required.
    public AddTileDialog()
    { }

    public static AddTileDialog newInstance(String title) {
        AddTileDialog frag = new AddTileDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("button", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_add_tile_dialog, null);
        editText_char = (EditText)view.findViewById(R.id.editText_char);
        editText_char.setInputType(InputType.TYPE_CLASS_TEXT);
        editText_value = (EditText)view.findViewById(R.id.editText_value);
        editText_value.setInputType(InputType.TYPE_CLASS_NUMBER);
        title = getArguments().getString("title");
        String bText;

        if( title.equals("Add Tile")) {
            bText = "Add";
        }
        else{
            bText = "Remove";
        }

        System.out.println("creating dialog.");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Enter Values for Letter:");
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton(bText,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                //verify both are added:
                System.out.println("Clicked positive button.");
                boolean errorState = false;
                if (editText_char.length() != 1) {
                    editText_char.setError("Invalid Character.");
                    errorState = true;
                }
                if (editText_value.length() < 1) {
                    editText_value.setError("Invalid Value.");
                    errorState = true;
                }
                if (!errorState) {

                    // create a new letter and add to or remove it from pool:
                    Letter letter = new Letter(editText_char.getText().charAt(0),
                            Integer.parseInt(editText_value.getText().toString()));
                    if( title == "Add Tile") {
                        System.out.println("adding tile in activity: " + letter.toString());
                        ((AdjustSettingsActivity) getActivity())
                                .addLetter(letter);
                    }
                    else {
                        System.out.println("removing tile in activity: " + letter.toString());
                        ((AdjustSettingsActivity) getActivity())
                                .removeLetter(letter);
                    }
                        // redraw pool - this will happen when the dialog is recreated:

                   // dialog.dismiss();
                }
            }
        });
        alertDialogBuilder.setNeutralButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Clicked neutral button: " + dialog.toString());
                dialog.dismiss();
            }

        });

        return alertDialogBuilder.create();
    }

}
