package uw.tacoma.edu.paidaid.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.model.Request;
import uw.tacoma.edu.paidaid.view.RequestFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Request} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRequestsRecyclerViewAdapter extends RecyclerView.Adapter<MyRequestsRecyclerViewAdapter.ViewHolder> {

    private final List<Request> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRequestsRecyclerViewAdapter(List<Request> items, OnListFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mRequest = mValues.get(position);

        String tip = Request.MONEY_SIGN;
        tip += Double.toString(mValues.get(position).getmTipAmount());
        String storeName = mValues.get(position).getmStoreName();
        String distance = Double.toString(mValues.get(position).getmDistanceAway());
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

//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).getmItemsAndComments());
//        holder.mContentView.setText(mValues.get(position).getmStoreName());
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTipView;
        public final TextView mDistanceView;
        public final TextView mStoreNameView;
        public Request mRequest;

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
