package uw.tacoma.edu.paidaid.coreFeatures;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.model.Request;
import uw.tacoma.edu.paidaid.view.HomeActivity;
import uw.tacoma.edu.paidaid.view.MyRequestsRecyclerViewAdapter;

/**
 * @Author Dmitriy Onishchenko
 * @Author Jake Knowles
 * @version 5/5/2017
 *
 * Fragment representing a list of Requests.
 */
public class RequestFragment extends Fragment {

    /**
     * URL to download the requests from database.
     */
    private static final String DOWNLOAD_REQUESTS_URL =
            "http://paidaid.x10host.com/requests.php?cmd=requests";

    /**
     * URL to calculate the distance.. need to add additional parameters see buildDistanceURL()
     */
    private static final String DISTANCE_URL =
            "http://maps.googleapis.com/maps/api/directions/json?origin=";

    /**
     * The column count argument.
     */
    private static final String ARG_COLUMN_COUNT = "column-count";

    /**
     * The column count for the grid layout
     */
    private int mColumnCount = 3;

    /**
     * The listener for interaction.
     */
    private OnListFragmentInteractionListener mListener;

    /**
     * The recycler view for the dislplay.
     */
    private RecyclerView mRecyclerView;

    /** Latitude */
    private Double mLatitude;

    /** Longitude */
    private Double mLongitude;

    /** Progress bar loading feature */
    private ProgressBar mProgressBar;

    /**
     * Constructor initialize fields.
     */
    public RequestFragment() {
    }


    /**
     * Gets Column Count if not NULL
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    /**
     * Loads XML layout & deals with Layouts with RecyclerView
     *
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        View v = view.findViewById(R.id.list);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progBar);

        // Set the adapter
        if (v instanceof RecyclerView) {
            Context context = v.getContext();
            RecyclerView recyclerView = (RecyclerView) v;
            mRecyclerView = (RecyclerView) v;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        // Hide the navigation bar when scrolling
        addScrollListener();

        return view;
    }


    /**
     * onResume - Starts DownloadRequestsTask
     */
    @Override
    public void onResume() {
        super.onResume();

        Log.i("resume is called", "request fragment");
        DownloadRequestsTask task = new DownloadRequestsTask();
        task.execute(new String[]{DOWNLOAD_REQUESTS_URL});
    }

    /**
     * onAttach assigns mListener with a listener ELSE - exception
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /**
     * onDetach assigns mListener to null
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface which allows for interaction with this fragment.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Request item);
    }


    /**
     * Scroll listener where we hide the bottom navigtation bar when scrolling through the
     * requests.
     */
    private void addScrollListener() {

        // Get the bottom navigation bar
        final View navBar = getActivity().findViewById(R.id.layout_navigation);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * Boolean flag to know when the toolbar has reached its original position
             */
            private boolean backToTop = true;

            /**
             * Navigation bar scroll helper
             *
             * @param recyclerView recycler view
             * @param dx x-axis difference
             * @param dy y-axis difference
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)                        // scrolling down
                    hideToolbarBy(navBar, -dy);
                 else                              // scrolling up
                    showToolbarBy(navBar, dy);
            }

            /**
             * Hide Toolbar helper
             *
             * @param toolBar toolbar
             * @param dy y-axis difference
             */
            private void hideToolbarBy(View toolBar, int dy) {
                if (cannotHideMore(navBar, dy))
                    toolBar.setTranslationY(navBar.getHeight());
                else
                    toolBar.setTranslationY(navBar.getTranslationY() - dy);

                // Let the listener know that the bar is not where it started from
                backToTop = false;
            }

            /**
             * Hider help
             * @param toolBar toolbar
             * @param dy y-axis difference
             * @return boolean
             */
            private boolean cannotHideMore(View toolBar, int dy) {
                return Math.abs(toolBar.getTranslationY()) >= toolBar.getHeight();

            }

            /**
             * Hider help
             * @param toolBar toolbar
             * @param dy y-axis difference
             * @return boolean
             */
            private boolean cannotShowMore(View toolBar, int dy) {
                return toolBar.getTranslationY() < 0;
            }

            /**
             * Toolbar translation helper
             * @param toolBar toolbar
             * @param dy y-axis difference
             */
            private void showToolbarBy(View toolBar, int dy) {

                if (backToTop) return;

                if (cannotShowMore(toolBar, dy)) {
                    backToTop = true;
                    toolBar.setTranslationY(0);
                } else {
                    toolBar.setTranslationY(toolBar.getTranslationY() + dy);
                }
            }
        });
    }




    /**
     * A task to download the requests from the database.
     */
    private class DownloadRequestsTask extends AsyncTask<String, Void, String> {

        /**
         * onPreExecute
         */
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);

        }

        /**
         * doInBackground -- loading request data
         * @param urls urls
         * @return String
         */
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of Request, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }

            return response;
        }

        /**
         * It checks to see if there was a problem with the URL(Network), if not tries to parse the
         * json object, if successful sets the adapter to the RecyclerViewAdapter, if not successful
         * shows an error message toast.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {

            // Get the location and set the latitude and longitude
            Location loc = ((HomeActivity) getActivity()).getCurrentLocation();

            while(loc == null) {
                Log.i("GOT HERE WHILE", "WHILE LOOP");
                loc = ((HomeActivity) getActivity()).getFusedLocation();
            }

            mLatitude = loc.getLatitude();
            mLongitude = loc.getLongitude();

            // Log.e("REQUEST", result);

            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }


            List<Request> requestsList = new ArrayList<Request>();
            result = Request.parseRequestsJSON(result, requestsList);
            // Something wrong with the JSON returned.

            if (result != null) {
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            if (!requestsList.isEmpty()) {

                // calculate and set distances for all requests
                setDistances(requestsList);

                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setAdapter(new MyRequestsRecyclerViewAdapter(requestsList, mListener));

            }
        }


    }


    private void setDistances(List<Request> theRequests) {

        Location from = new Location("From");
        from.setLatitude(mLatitude);
        from.setLongitude(mLongitude);

        for (Request req: theRequests) {
            Location to = new Location("To");
            to.setLatitude(req.getLatitude());
            to.setLongitude(req.getLongitude());

            double dis = calculateDistanceMiles(from, to);

            req.setDistanceAway(dis);

        }

    }

    /**
     * Calculates the distacne between two Locatons
     * @param from the source
     * @param to the destination
     * @return distance in miles
     */
    public double calculateDistanceMiles(Location from, Location to) {

        double distanceMeters = from.distanceTo(to);
        Double metersInMile = 1609.34;

        // convert meters to miles
        double miles = distanceMeters / metersInMile;

        return miles;

    }
}