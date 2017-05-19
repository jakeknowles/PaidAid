package uw.tacoma.edu.paidaid.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.model.Request;
import uw.tacoma.edu.paidaid.pager.RequestFragment.OnListFragmentInteractionListener;

/**
 * @Author Dmitriy Onishchenko
 * @Author Jake Knowles
 * @version 5/5/2017
 *
 * The Recycler view adapter which binds a view to the holder which will display requests
 * in the layout that we want.
 */
public class MyRequestsRecyclerViewAdapter extends RecyclerView.Adapter<MyRequestsRecyclerViewAdapter.ViewHolder> {

    /**
     * The list of requests
     */
    private final List<Request> mRequests;
    /**
     * The interaction listener.
     */
    private final OnListFragmentInteractionListener mListener;

    /**
     * Constructor
     * @param requests the list of requests
     * @param listener the interaction listener
     */
    public MyRequestsRecyclerViewAdapter(List<Request> requests, OnListFragmentInteractionListener listener) {
        mRequests = requests;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mRequest = mRequests.get(position);

        String tip = Request.MONEY_SIGN;
        tip += Double.toString(mRequests.get(position).getmTipAmount());
        String storeName = mRequests.get(position).getmStoreName();
        String distance = Double.toString(mRequests.get(position).getmDistanceAway());
        distance += Request.MILES_UNITS;

        holder.mTipView.setText(tip);
        holder.mDistanceView.setText(distance);
        holder.mStoreNameView.setText(storeName);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mRequest);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    /**
     * This is the view holder for each individual request which will be displayed
     * in grid layout form on our home screen.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The view holder.
         */
        public final View mView;
        /**
         * The tip amount view component.
         */
        public final TextView mTipView;
        /**
         * The distance view component.
         */
        public final TextView mDistanceView;
        /**
         * The storename view component.
         */
        public final TextView mStoreNameView;
        /**
         * The request object.
         */
        public Request mRequest;

        /**
         * Constructor
         * @param view the view which this view holder will be associated with this viewholder.
         */
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTipView = (TextView) view.findViewById(R.id.tip_amount);
            mDistanceView = (TextView) view.findViewById(R.id.distance);
            mStoreNameView = (TextView) view.findViewById(R.id.storename);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTipView.getText() + "'";
        }
    }

}
