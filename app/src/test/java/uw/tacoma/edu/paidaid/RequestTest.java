package uw.tacoma.edu.paidaid;

import org.junit.Test;

import uw.tacoma.edu.paidaid.model.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by jake on 5/22/17.
 */

public class RequestTest {
    public String testJSON = "{\"user_id\":\"123456789\",\"username\":" +
                             "\"test123\",\"email\":\"jake@gmail.com\",\"tip\":\"10.00\"," +
                             "\"distance\":\"20\"," + "\"storename\":\"Costco\"," +
                             "\"items_and_comments\":\", 2 Vacuums, , Box of Fruit Snacks, Almonds, 2 Tubs of Red Licorice, " +
                             "Black 5 person Tent, 3 Sleeping Bags, 10 Cases of AAA Batteries,\"rating\":\"4.1\" }";
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
    public void testRequestConstructor() {
        assertNotNull(mRequest);
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
        assertTrue(theStarRating == mRequest.getmStarRating());
    }

    @Test
    public void parseRequestsJSON() {

//        ArrayList<Request> mRequestList = Request.parseRequestsJSON();
//        Request testRequest = mRequestList.get(0);
//        assertEquals("User ID does not match", 1 == testRequest.getmUserID());
//        assertTrue("Username does not match", "jake" == testRequest.getmUsername();
//        assertTrue("Email does not match", "jake@gmail.com" == testRequest.getmEmail());
//        assertTrue("Tip amount does not match", 5.0 == testRequest.getmTipAmount());
//        assertEquals("Distace does not match", 5.0 == testRequest.getmDistanceAway());
//        assertEquals("Store name does not match", "Costco" == testRequest.getmStoreName());
//        assertEquals("Items does not match", "10 Boxes of AAA Batteries" == testRequest.getmItemsAndComments());
//        assertEquals("Star Rating does not match", 4.2 == testRequest.getmStarRating());

    }
}