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



//
//
////            final View tabBar = getActivity().findViewById(R.id.fake_tab);
////            final View coloredBackgroundView =        findViewById(R.id.colored_background_view);
//            final View toolbarContainer = getActivity().findViewById(R.id.toolbar);
////            final View toolbar = getActivity().findViewById(R.id.toolbar);
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                    super.onScrollStateChanged(recyclerView, newState);
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        if (Math.abs(toolbarContainer.getTranslationY()) >      toolbarContainer.getHeight()) {
//                            hideToolbar();
//                        } else {
//                            showToolbar();
//                        }
//                    }
//                }
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    if (dy > 0) {
//                        hideToolbarBy(dy);
//                    } else {
//                        showToolbarBy(dy);
//                    }
//                }
//
//                private void hideToolbarBy(int dy) {
//                    if (cannotHideMore(dy)) {
//                        toolbarContainer.setTranslationY(-tabBar.getBottom());
//                    } else {
//                        toolbarContainer.setTranslationY(toolbarContainer.getTranslationY() - dy);
//                    }
//                }
//                private boolean cannotHideMore(int dy) {
//                    return Math.abs(toolbarContainer.getTranslationY() - dy) > 2;
//                }
//                private void showToolbarBy(int dy) {
//                    if (cannotShowMore(dy)) {
//                        toolbarContainer.setTranslationY(0);
//                    } else {
//                        toolbarContainer.setTranslationY(toolbarContainer.getTranslationY() - dy);
//                    }
//                }
//                private boolean cannotShowMore(int dy) {
//                    return toolbarContainer.getTranslationY() - dy > 0;
//                }
//
//                private void showToolbar() {
//                    toolbarContainer
//                            .animate()
//                            .translationY(0)
//                            .start();
//                }
//                private void hideToolbar() {
//                    toolbarContainer
//                            .animate()
//                            .translationY(-1)
//                            .start();
//                }
//            });







        }


        final View navBar = getActivity().findViewById(R.id.layout_navigation);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    hideToolbarBy(navBar, -dy);
//                    getActivity().findViewById(R.id.toolbar).setVisibility(View.INVISIBLE);
//                    getActivity().findViewById(R.id.layout_navigation).setVisibility(View.INVISIBLE);
                } else {

                    showToolbarBy(navBar, dy);


//                    getActivity().findViewById(R.id.toolbar).setVisibility(View.VISIBLE);
//                    getActivity().findViewById(R.id.layout_navigation).setVisibility(View.VISIBLE);

                    // Scrolling down
                }
            }

            private void hideToolbarBy(View toolBar, int dy) {
                Log.e("WINDOW", Integer.toString(getActivity().getWindow().getDecorView().getHeight()));
                Log.e("navbartop", Float.toString(navBar.getTranslationY()));
                Log.e("navbarheigth", Float.toString(navBar.getHeight()));


                if (cannotHideMore(navBar, dy)) {
                    toolBar.setTranslationY(navBar.getHeight());
                } else {
                    toolBar.setTranslationY(navBar.getTranslationY() - dy);
                }
            }
            private boolean cannotHideMore(View toolBar, int dy) {
                return Math.abs(toolBar.getTranslationY()) > toolBar.getHeight();

            }

            private boolean cannotShowMore(View toolBar, int dy) {
                    return toolBar.getTranslationY() < 0;
                }

            private void showToolbarBy(View toolBar, int dy) {

                Log.e("navbartop", Float.toString(navBar.getTranslationY()));
                Log.e("navbarheigth", Float.toString(navBar.getHeight()));
                    if (cannotShowMore(toolBar, dy)) {
                        toolBar.setTranslationY(0);
                    } else {
                        toolBar.setTranslationY(toolBar.getTranslationY() + dy);
                    }
                }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        });



        return view;
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