package umm3601.plant;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import umm3601.digitalDisplayGarden.Plant;
import umm3601.digitalDisplayGarden.PlantController;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

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
        boolean ans = true;
        assertEquals(ans, plantResponse);
    }

    @Test
    public void addFlowerRating() {
        String aJSON = "{ \"id\" : \"58d1c36efb0cac4e15afd278\","+
                "\"like\" : true }";
        System.out.println(aJSON);
        boolean plantResponse = plantController
                .addFlowerRating(aJSON,
                        "second uploadId");
        boolean ans = true;
        assertEquals(ans, plantResponse);
    }

//    @Test
//    public void incrementMetadata() {
//        boolean myPlant = plantController.
//                incrementMetadata("58d1c36efb0cac4e15afd278", "pageViews");
//        System.out.println(plantController.
//                incrementMetadata("58d1c36efb0cac4e15afd278", "pageViews"));
//        assertEquals(true, myPlant);
//    }

//    @Test
//    public void addVisit() {
//        String myPlant = plantController.addVisit();
//    }

//    @Test
//    public void AddFlowerRatingReturnsTrueWithValidJsonInput() throws IOException{
//
//        String json = "{like: true, id: \"58d1c36efb0cac4e15afd202\"}";
//
//        assertTrue(plantController.addFlowerRating(json, "first uploadId"));
//
//        MongoClient mongoClient = new MongoClient();
//        MongoDatabase db = mongoClient.getDatabase(databaseName);
//        MongoCollection plants = db.getCollection("plants");
//
//        FindIterable doc = plants.find(new Document().append("_id", new ObjectId("58d1c36efb0cac4e15afd202")));
//        Iterator iterator = doc.iterator();
//        Document result = (Document) iterator.next();
//
//        List<Document> ratings = (List<Document>) ((Document) result.get("metadata")).get("ratings");
//        assertEquals(1, ratings.size());
//
//        Document rating = ratings.get(0);
//        assertTrue(rating.getBoolean("like"));
//        assertEquals(new ObjectId("58d1c36efb0cac4e15afd202"),rating.get("ratingOnObjectOfId"));
//    }
}
