package uw.tacoma.edu.paidaid;

import org.junit.Test;

import uw.tacoma.edu.paidaid.model.Request;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jake on 5/22/17.
 */

public class RequestTest {

    public String requestJSON = "";
    public int theUserID = 1;
    public String theUsername = "test123";
    public String theEmail = "test@gmail.com";
    public double theTip = 10.00;
    public double theDistanceAway = 5.0;
    public String theStoreName = "Costco";
    public String theItemsAndComments = "7 boxes fruit snacks";
    public double theStarRating = 4.5;


    public Request mRequest = new Request(theUserID, theUsername, theEmail, theTip, theDistanceAway,
            theStoreName, theItemsAndComments, theStarRating);


    @Test
    public void parseRequestsJSON() {

//        ArrayList<Request> mRequestList = Request.parseRequestsJSON(requestJSON);
//        Request testRequest = mRequestList.get(0);

    }

    @Test
    public void testgetmUserID() {
        assertEquals(theUserID, mRequest.getmUserID());
    }

    @Test
    public void testgetmUsername() {
        assertEquals(theUsername, mRequest.getmUsername());
    }

    @Test
    public void testgetmEmail() {
        assertEquals(theEmail, mRequest.getmEmail());
    }

    @Test
    public void testgetmTipAmount() {
        assertEquals(theTip, mRequest.getmTipAmount());
    }

    @Test
    public void testgetmDistanceAway() {
        assertEquals(theDistanceAway, mRequest.getmDistanceAway());
    }

    @Test
    public void testgetmStoreName() {
        assertEquals(theStoreName, mRequest.getmStoreName());
    }

    @Test
    public void testgetmItemsAndComments() {
        assertEquals(theItemsAndComments, mRequest.getmItemsAndComments());
    }

    @Test
    public void testgetmStarRating() {
        assertEquals(theStarRating, mRequest.getmStarRating());
    }

}
