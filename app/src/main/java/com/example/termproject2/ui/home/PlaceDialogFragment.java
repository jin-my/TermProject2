//package com.example.termproject2.ui.home;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.widget.EditText;
//
//import androidx.fragment.app.DialogFragment;
//
//import com.example.termproject2.R;
//
//public class PlaceDialogFragment extends DialogFragment {
//
//
//    private MyDialogListener myListener;
//
//
//    public interface MyDialogListener {
//
//        public void myCallback(String cityName);
//
//    }
//
//
//    public PlaceDialogFragment() {
//
//    }
//
//
//    @Override
//
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//
//        try {
//
//            myListener = (MyDialogListener) getTargetFragment();
//
//        } catch (ClassCastException e) {
//
//            throw new ClassCastException();
//
//        }
//
//    }
//
//
//    @Override
//
//    public Dialog onCreateDialog() {
//        return onCreateDialog();
//    }
//
//    @Override
//
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//
//        builder.setView(inflater.inflate(R.layout.fragment_dialog, null))
//
//                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
//
//
//                    @Override
//
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        EditText edCityName = (EditText)getDialog().findViewById(R.id.city_name);
//
//                        String cityName = edCityName.getText().toString();
//
//
//                        myListener. myCallback(cityName);
//
//                    }
//
//                });
//
//
//        return builder.create();
//
//    }
//
//
//}
