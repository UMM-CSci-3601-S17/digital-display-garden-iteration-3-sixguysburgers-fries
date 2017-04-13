package umm3601.plant;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.PlantController;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

public class UnorganizedTests {

    private final static String databaseName = "data-for-testing-only";
    private PlantController plantController;

    @Before
    public void populateDB() throws IOException {
        PopulateMockDatabase db = new PopulateMockDatabase();
        db.clearAndPopulateDBAgain();
        plantController = new PlantController(databaseName);
        plantController.postData("second uploadId");

//        plantController.setLiveUploadId(uploadId);
    }

    @Test
    public void getPlantByID() {
        String myPlant = plantController.getPlantByPlantID("16040.0", "second uploadId");
        System.out.println();System.out.println(myPlant);System.out.println();
        assertEquals("{ " +
                "\"_id\" : { \"$oid\" : \"58d1c36efb0cac4e15afd204\" }, " +
                "\"commonName\" : \"Dianthus\", " +
                "\"cultivar\" : \"Jolt™ Pink F1\" }",

                myPlant);
    }


    @Test
    public void getFeedBackForPlantByID() {
        String myPlant = plantController.getFeedbackForPlantByPlantID("16040.0", "second uploadId");
        assertEquals("{ \"commentCount\" : 0 , " +
                        "\"likeCount\" : 0 , " +
                        "\"dislikeCount\" : 0}",
                myPlant);
    }

    @Test
    public void storePlantComment() {
        String aJSON = "{ \"plantId\" : \"58d1c36efb0cac4e15afd278\","+
                "\"comment\" : \"Yes I swear it's the truth. And I owe it all to you.\" }";
        System.out.println(aJSON);
        boolean plantResponse = plantController
                .storePlantComment(aJSON+
                                    "\"comment\" : \"Yes I swear it's the truth."+
                                " And I owe it all to you.\"}",
                        "second uploadId");
        assertTrue(plantResponse);
    }

    @Test
    public void addFlowerRating() {
        String aJSON = "{ \"id\" : \"58d1c36efb0cac4e15afd278\","+
                "\"like\" : true }";
        System.out.println(aJSON);
        boolean plantResponse = plantController
                .addFlowerRating(aJSON,
                        "second uploadId");
        assertTrue(plantResponse);
    }

    @Test
    public void incrementMetadata() {
    //This method is called from inside of getPlantByPlantId();
        boolean myPlant = plantController.
                incrementMetadata("58d1c36efb0cac4e15afd278", "pageViews");
        assertFalse(myPlant);
        boolean myPlant2 = plantController.incrementMetadata("16001.0","pageViews");
        assertTrue(myPlant2);


    //This is necessary to test the data separately from getPlantByPlantId();
        Document searchDocument = new Document();
        searchDocument.append("id", "16001.0");
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> plantCollection = db.getCollection("plants");
        String before = JSON.serialize(plantCollection.find(searchDocument));
        plantController.incrementMetadata("16001.0","pageViews");
        String after = JSON.serialize(plantCollection.find(searchDocument));

        assertFalse(before.equals(after));
    }

    @Test
    public void DestroyDB() {
        String destroyed = plantController.destroyDb();
        assertEquals("destroyed", destroyed);
    }

    @Test
    public void PData() {
        String data = plantController.postData("second uploadId");
        assertEquals("posted", data);
    }

    @Test
    public void LikeData() {
        String json = plantController.getLikeDataForAllPlants("second uploadId");
        String compared = "[[\"Bed\",\"Total Likes\",\"Total Visits\"],[\"7.0\",0,0],[\"12\",0,0]]";

        assertEquals(json, compared);
    }

    @Test
    public void getDataForOneBedTest() {
        String json = plantController.getDataForOneBed("second uploadId","7.0");
        System.out.println(json);
        String notExpected = "[[\"Cultivar\",\"Rating\",\"Visits\"],[\"Jolt™ Pink F1\",0,1]]";
        String expected = "[[\"Cultivar\",\"Rating\",\"Visits\"],[\"Jolt™ Pink F1\",0,0]]";

        Assert.assertNotEquals(json,notExpected);
        assertEquals(json,expected);

    }

    @Test
    public void makeJSONTest(){
        Object[][] toInsert = new Object[2][2];

        toInsert[0][0] = "wassup";
        toInsert[0][1] = "not much";
        toInsert[1][0] = "Shut the hell up";
        toInsert[1][1] = 2;

        String expected = "[[\"wassup\",\"not much\"],[\"Shut the hell up\",2]]";

        assertEquals(expected, plantController.makeJSON(toInsert));

    }

}
