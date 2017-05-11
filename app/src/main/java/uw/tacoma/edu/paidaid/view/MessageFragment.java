package uw.tacoma.edu.paidaid.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uw.tacoma.edu.paidaid.R;

/** Message Fragment is from clicking on the Messages button - allowing the user to click on a specific
 * message and see their conversation */
public class MessageFragment extends Fragment {

        /** OnFragmentInteractionListener */
        private OnFragmentInteractionListener mListener;

        /** Constructor */
        public MessageFragment() {}

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
            return inflater.inflate(R.layout.fragment_message, container, false);
        }


        /**
         * onButtonPressed
         * @param uri uri is a string of characters used to identify a resource.
         */
        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }

        /** OnFragmentInteractionListener */
        public interface OnFragmentInteractionListener {
            void onFragmentInteraction(Uri uri);
        }
}
