package umm3601.plant;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
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
}
