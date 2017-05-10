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


    /** Constant for JSON key tip_amount */
    public static final String TIP_AMOUNT = "tip_amount";

    /** Constant for JSON key distance_away */
    public static final String DISTANCE_AWAY = "distance_away";

    /** Constant for JSON key store_name */
    public static final String STORE_NAME = "store_name";

    /** Constant for JSON key expiration_time */
    public static final String EXPIRATION_TIME = "expiration_time";

    /** Constant for JSON key items_and_comments */
    public static final String ITEMS_AND_COMMENTS = "items_and_comments";

    /** Constant for JSON key star_rating */
    public static final String STAR_RATING = "star_rating";

    // Tip amount for picking up request
    private double mTipAmount;

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
    public Request(double theTipAmount, double theDistanceAway, String theStoreName,
                   double theExpirationTime, String theItemsAndComments, int theStarRating) {

        mTipAmount = theTipAmount;
        mDistanceAway = theDistanceAway;
        mStoreName = theStoreName;
        mExpirationTime = theExpirationTime;
        mItemsAndComments = theItemsAndComments;
        mStarRating = theStarRating;

    }


    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns course list if success.
     * @param courseJSON
     * @return reason or null if successful.
     */
    public static String parseRequestsJSON(String courseJSON, List<Request> courseList) {
        String reason = null;
//        if (courseJSON != null) {
//            try {
//                JSONArray arr = new JSONArray(courseJSON);
//
//                for (int i = 0; i < arr.length(); i++) {
//                    JSONObject obj = arr.getJSONObject(i);
//                    Request course = new Request(obj.getString(Request.ID), obj.getString(Request.SHORT_DESC)
//                            , obj.getString(Request.LONG_DESC), obj.getString(Request.PRE_REQS));
//                    courseList.add(course);
//                }
//            } catch (JSONException e) {
//                reason =  "Unable to parse data, Reason: " + e.getMessage();
//            }
//
//        }
        return reason;
    }


    /**
     * Getter for mTipAmount
     * @return tip amount ($0.00)
     */
    public double getmTipAmount() {
        return mTipAmount;
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
     * @return rating of user from 1 - 5
     */
    public int getmStarRating() {
        return mStarRating;
    }

}
