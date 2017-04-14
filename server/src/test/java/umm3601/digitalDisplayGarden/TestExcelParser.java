package umm3601.digitalDisplayGarden;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.*;

/**
 * Created by benek020 on 3/6/17.
 */
public class TestExcelParser {

    private final static String databaseName = "data-for-testing-only";

    public MongoClient mongoClient = new MongoClient();
    public MongoDatabase testDB;
    public ExcelParser parser;
    public InputStream fromFile;

    @Before
    public void clearAndPopulateDatabase(){
        mongoClient.dropDatabase(databaseName);
        testDB = mongoClient.getDatabase(databaseName);
        fromFile = this.getClass().getResourceAsStream("/AccessionList2016.xlsx");
        parser = new ExcelParser(fromFile, databaseName);
    }



    @Test
    public void testSpeadsheetToDoubleArray(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        //printDoubleArray(plantArray);

        assertEquals(1000, plantArray.length);
        assertEquals(plantArray[40].length, plantArray[963].length);
        assertEquals("2016 Accession List: Steve's Design", plantArray[0][1]);
        assertEquals("Begonia", plantArray[6][1]);

    }

    @Test
    public void testCollapse(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        //System.out.println(plantArray.length);
        //printDoubleArray(plantArray);

        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);

        //printDoubleArray(plantArray);

        assertEquals(362, plantArray.length);
        assertEquals(8, plantArray[30].length);
        assertEquals(8, plantArray[0].length);
        assertEquals(8, plantArray[3].length);
    }

    @Test
    public void testReplaceNulls(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);

        for (String[] row : plantArray){
            for (String cell : row){
                assertNotNull(cell);
            }
        }
    }

    @Test
    public void testPopulateDatabase(){
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);

        parser.populateDatabase(plantArray, "an arbitrary ID");
        MongoCollection plants = testDB.getCollection("plants");


        assertEquals(286, plants.count());
        assertEquals(11, plants.count(eq("commonName", "Geranium")));
    }

    @Test
    public  void testUpdateDatabase() {
        String[][] plantArray = parser.extractFromXLSX(fromFile);
        plantArray = parser.collapseHorizontally(plantArray);
        plantArray = parser.collapseVertically(plantArray);
        parser.replaceNulls(plantArray);

        parser.updateDatabase(plantArray,"id");
        MongoCollection plants = testDB.getCollection("plants");

        assertEquals(286, plants.count());
        assertEquals(11, plants.count(eq("commonName", "Geranium")));
    }


    @Test
    public void getAvailableUploadId() {
        String crrntTime = parser.getAvailableUploadId();
        boolean ans = Pattern.matches("[\\d]{4}-[\\d]{2}-[\\d]{2} [\\d]{2}:[\\d]{2}:[\\d]{2}",crrntTime);
        assertTrue(ans);
    }

    @Test
   public  void testSetLiveUploadID() {
        parser.setLiveUploadId("JAPSER");


        Document searchDocument = new Document();
        searchDocument.append("liveUploadId", "JAPSER");
        MongoClient mongoClient = new MongoClient();
        MongoDatabase test = mongoClient.getDatabase("test");
        MongoCollection configCollection = test.getCollection("config");
        String uploadID = JSON.serialize(configCollection.find(searchDocument));
        assertEquals(80,uploadID.length());
    }

}
