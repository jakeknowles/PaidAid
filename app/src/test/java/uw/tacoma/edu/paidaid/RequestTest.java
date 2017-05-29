package uw.tacoma.edu.paidaid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import uw.tacoma.edu.paidaid.model.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by jake on 5/22/17.
 */

public class RequestTest {
    @Mock
    JSONArray jsonArray;

    @Mock
    JSONObject jsonObject;

    private Request mRequest = null;


    public int theUserID = 1;
    public String theUsername = "jake";
    public String theEmail = "test@gmail.com";
    public double theTip = 10.00;
    public double theDistanceAway = 5.0;
    public String theStoreName = "Costco";
    public String theItemsAndComments = "7 Boxes of Fruit Snacks";
    public double theStarRating = 4.5;


    @Before
    public void setUp() {
         mRequest = new Request(theUserID, theUsername, theEmail, theTip, theDistanceAway,
                theStoreName, theItemsAndComments, theStarRating);
//        mRequest = new Request(1, "jake", "test@gmail.com", 10.00, 5.0,
//                   "Costco", "7 Boxes of Fruit Snacks", 4.5);
        MockitoAnnotations.initMocks(this);
    }

    @Test (expected=java.lang.RuntimeException.class)
    public void parseRequestsJSON() {

        when(Request.parseRequestsJSON(anyString(), any(List.class))).thenReturn("Test");
            assertEquals("Success", Request.parseRequestsJSON("[{\"user_id\":\"123456789\",\"username\":" +
                    "\"jake\",\"email\":\"test@gmail.com\",\"tip\":\"10.00\"," +
                    "\"distance\":\"5.0\"," + "\"storename\":\"Costco\"," +
                    "\"items_and_comments\":\", 7 Boxes of Fruit Snacks,\"rating\":\"4.5\" }]", new ArrayList<Request>()));

    }


    @Test
    public void testRequestConstructor() {
        assertNotNull(mRequest);
    }

    @Test
    public void testgetmUserID() {
        assertEquals(theUserID, mRequest.getmUserID());
    }

    @Test (expected=java.lang.AssertionError.class)
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

}