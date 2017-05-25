package uw.tacoma.edu.paidaid.coreFeatures;

import android.content.Context;
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
import android.widget.AbsListView;
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
import uw.tacoma.edu.paidaid.view.MyRequestsRecyclerViewAdapter;

/**
 * @Author Dmitriy Onishchenko
 * @Author Jake Knowles
 * @version 5/5/2017
 *
 * A fragment representing a list of Requests.
 *
 */
public class RequestFragment extends Fragment {

    /**
     * URL to download the requests from database.
     */
    private static final String DOWNLOAD_REQUESTS_URL =
            "http://paidaid.x10host.com/requests.php?cmd=requests";

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

    /**
     * Constructor initialize fields.
     */
    public RequestFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            mRecyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            DownloadRequestsTask task = new DownloadRequestsTask();
            task.execute(new String[]{DOWNLOAD_REQUESTS_URL});


        }


        // hide the navigation bar when scrolling
        addScrollListener();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        Log.e("ON START IS CALLED", "START");

        DownloadRequestsTask task = new DownloadRequestsTask();
        task.execute(new String[]{DOWNLOAD_REQUESTS_URL});


    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("ON RESUME IS CALLED", "RESUME");

        DownloadRequestsTask task = new DownloadRequestsTask();
        task.execute(new String[]{DOWNLOAD_REQUESTS_URL});


    }

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
        // get the bottom navigation bar
        final View navBar = getActivity().findViewById(R.id.layout_navigation);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * Boolean flag to know when the toolbar has reached its original position
             */
            private boolean backToTop = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {                       // scrolling down
                    hideToolbarBy(navBar, -dy);
                } else {                            // scrolling up
                    showToolbarBy(navBar, dy);

                }
            }

            private void hideToolbarBy(View toolBar, int dy) {

                if (cannotHideMore(navBar, dy)) {
                    toolBar.setTranslationY(navBar.getHeight());
                } else {
                    toolBar.setTranslationY(navBar.getTranslationY() - dy);
                }

                // let the listener know that the bar is not where it started from
                backToTop = false;
            }

            private boolean cannotHideMore(View toolBar, int dy) {
                return Math.abs(toolBar.getTranslationY()) >= toolBar.getHeight();

            }

            private boolean cannotShowMore(View toolBar, int dy) {
                return toolBar.getTranslationY() < 0;
            }

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
                mRecyclerView.setAdapter(new MyRequestsRecyclerViewAdapter(requestsList, mListener));
            }
        }

    }
}