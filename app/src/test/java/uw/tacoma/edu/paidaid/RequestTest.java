package uw.tacoma.edu.paidaid;

import org.junit.Test;

import uw.tacoma.edu.paidaid.model.Request;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jake on 5/22/17.
 */

public class RequestTest {

    public String requestJSON = "";
    public double theTip = 10.00;
    public double theDistanceAway = 5.0;
    public String theStoreName = "Costco";
    public String theItemsAndComments = "7 boxes fruit snacks";
    public double theExpirationTime = 530;


    public Request mRequest = new Request(theTip, theDistanceAway, theStoreName, theItemsAndComments);
//    public Request mRequest = new Request(theTip, theDistanceAway, theStoreName, theItemsAndComments,theExpirationTime);


    @Test
    public void parseRequestsJSON() {

//        ArrayList<Request> mRequestList = Request.parseRequestsJSON(requestJSON);
//        Request testRequest = mRequestList.get(0);

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

//    @Test
//    public void testgetmExpirationTime() {
//        assertEquals(theExpirationTime, mRequest.getmExpirationTime());
//    }
}
