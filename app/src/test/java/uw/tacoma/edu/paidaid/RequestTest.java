package uw.tacoma.edu.paidaid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import uw.tacoma.edu.paidaid.model.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @Author: Jake Knowles
 * @Author  Dmitriy Onishchenko
 * @version 5/29/2017
 */

/** JUnit Test for Request class -- Testing getters and JSONParser method */
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
    public float theStarRating =  4;
    public double theLongitude = 10.0;
    public double theLatitude = 10.0;


    @Before
    public void setUp() {
         mRequest = new Request(theUserID, theUsername, theEmail, theTip, theDistanceAway,
                theStoreName, theItemsAndComments, theStarRating, theLongitude, theLatitude);
        MockitoAnnotations.initMocks(this);
    }

    @Test (expected = RuntimeException.class)
    public void testParseRequestsJSONSuccess() {


        String json = "[{'userid':'123456789','username':'jake','email':'test@gmail.com'," +
                "'tip':'10.00','distance':'5.0','storename':'Costco'," +
                "'items_comments':'7 Boxes of Fruit Snacks','rating':'4'}]";
        String result = Request.parseRequestsJSON(json, new ArrayList<Request>());

        assertNull(result);

    }

    @Test
    public void testParseRequestsJSONFail() {


        String json = "[{'user_id':'123456789','username':'jake','email':'test@gmail.com'," +
                "'tip':'10.00','distance':'5.0','store_name':'Costco'," +
                "'itemscomments':'7 Boxes of Fruit Snacks','rating':'4.5' }]";
        String result = Request.parseRequestsJSON(json, new ArrayList<Request>());

        assertNotNull(result);

    }

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
    public double getLatitude() {
        assertEquals(theLatitude, mRequest.getLatitude());
    }

    @Test
    public double getLongitude() {
        assertEquals(theLongitude, mRequest.getLongitude());
    }

}