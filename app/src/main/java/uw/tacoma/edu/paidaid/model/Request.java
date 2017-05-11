package uw.tacoma.edu.paidaid.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Request object that contains all necessary request info
 * (tip amount, distance, store name, expiration time, items & comments, rating)
 */
public class Request implements Serializable{


    public static final String MILES_UNITS = "mi.";

    public static final String MONEY_SIGN = "$";

    /** Constant for JSON key tip_amount */
    public static final String TIP_AMOUNT = "tip";

    /** Constant for JSON key distance_away */
    public static final String DISTANCE_AWAY = "distance";

    /** Constant for JSON key store_name */
    public static final String STORE_NAME = "storename";

    /** Constant for JSON key expiration_time */
    public static final String EXPIRATION_TIME = "expiration";

    /** Constant for JSON key items_and_comments */
    public static final String ITEMS_AND_COMMENTS = "items_comments";

    /** Constant for JSON key star_rating */
    public static final String STAR_RATING = "star_rating";

    // Tip amount for picking up request
    private double mTip;

    // Distance away from request
    private double mDistanceAway;

    // Store name of where items are located
    private String  mStoreName;

    // Time until request expires
    private double mExpirationTime;

    // Items & Comments contained in Request
    private String mItemsAndComments;

    // 1 - 5 Star rating
    private int mStarRating;

    /** Request Constructor */
    public Request(double theTip, double theDistanceAway, String theStoreName,
                   String theItemsAndComments) {

        mTip = theTip;
        mDistanceAway = theDistanceAway;
        mStoreName = theStoreName;
//        mExpirationTime = theExpirationTime;
        mItemsAndComments = theItemsAndComments;
//        mStarRating = theStarRating;

    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param requestJSON
     * @return reason or null if successful.
     */
    public static String parseRequestsJSON(String requestJSON, List<Request> requestsList) {
        String reason = null;
        if (requestJSON != null) {
            try {
                JSONArray arr = new JSONArray(requestJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Request request = new Request(obj.getDouble(Request.TIP_AMOUNT), obj.getDouble(Request.DISTANCE_AWAY)
                            , obj.getString(Request.STORE_NAME), obj.getString(Request.ITEMS_AND_COMMENTS));
                    requestsList.add(request);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        return reason;
    }


    /**
     * Getter for mTipAmount
     * @return tip amount ($0.00)
     */
    public double getmTipAmount() {
        return mTip;
    }

    /**
     * Getter for mDistanceAway
     * @return distance away from request
     */
    public double getmDistanceAway() {
        return mDistanceAway;
    }

    /**
     * Getter for mStoreName
     * @return the name of the store where the items are located
     */
    public String getmStoreName() {
        return mStoreName;
    }

    /**
     * Getter for mExpirationTime
     * @return the time until the request expires
     */
    public double getmExpirationTime() {
        return mExpirationTime;
    }

    /**
     * Getter mItemsAndComments
     * @return the request items and additional comments
     */
    public String getmItemsAndComments() {
        return mItemsAndComments;
    }

    /**
     * Getter for mStarRating
     * @return rating of user1 from 1 - 5
     */
    public int getmStarRating() {
        return mStarRating;
    }

}
