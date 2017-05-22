package uw.tacoma.edu.paidaid.coreFeatures;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
 * Add Button Fragment from clicking "Add" on home screen. */
public class AddRequestFragment extends DialogFragment implements View.OnClickListener {


    /** Constructor */
    public AddRequestFragment() {}


    /**
     * onCreate
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * onCreateView
     * @param inflater instantiates the layout XML file into its corresponding View objects
     * @param container is a container for ViewGroup views
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_request, container, false);

        /** Attach on click listener */
        Button date = (Button) view.findViewById(R.id.date_picker_button);
        date.setOnClickListener(this);

        return view;
    }

    /**
     * Onclick method part of the onClick listener.
     * @param v the view
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.date_picker_button:
                launchCalendar(v);
                break;
            default:
                break;
        }
    }

    private void launchCalendar(View v) {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new DialogFragment())
                .addToBackStack(null)
                .commit();

    }

    /**
     * onAttach for LoginActivity
     * @param context is the data for the activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    /** OnFragmentInteractionListener */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
