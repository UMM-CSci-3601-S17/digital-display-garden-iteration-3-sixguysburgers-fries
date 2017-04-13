package umm3601.digitalDisplayGarden;


import com.mongodb.MongoClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import com.mongodb.util.JSON;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.types.ObjectId;

import org.bson.conversions.Bson;
import java.io.OutputStream;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.fields;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Updates.push;

public class PlantController {

    MongoClient mongoClient = new MongoClient(); // Defaults!
    private final MongoCollection<Document> plantCollection;
    private final MongoCollection<Document> commentCollection;
    private final MongoCollection<Document> configCollection;
    String dbName;
    private final MongoCollection<Document> graphInfoCollection;

    public PlantController(String databaseName) throws IOException {
        // Set up our server address
        // (Default host: 'localhost', default port: 27017)
        // ServerAddress testAddress = new ServerAddress();

        // Try connecting to the server
        //MongoClient mongoClient = new MongoClient(testAddress, credentials);

        // Try connecting to a database
        MongoDatabase db = mongoClient.getDatabase(databaseName);

        plantCollection = db.getCollection("plants");
        commentCollection = db.getCollection("comments");
        configCollection = db.getCollection("config");
        dbName = databaseName;
        graphInfoCollection = db.getCollection("graphData");
    }

    public String getLiveUploadId() {
        try {
            FindIterable<Document> findIterable = configCollection.find(exists("liveUploadId"));
            Iterator<Document> iterator = findIterable.iterator();
            Document doc = iterator.next();

            return doc.getString("liveUploadId");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" [hint] Database might be empty? Couldn't getLiveUploadId");
            throw e;
        }
    }

    // List plants
    public String listPlants(Map<String, String[]> queryParams, String uploadId) {
        Document filterDoc = new Document();
        filterDoc.append("uploadId", uploadId);

        if (queryParams.containsKey("gardenLocation")) {
            String location = (queryParams.get("gardenLocation")[0]);
            filterDoc = filterDoc.append("gardenLocation", location);
        }


        if (queryParams.containsKey("commonName")) {
            String commonName = (queryParams.get("commonName")[0]);
            filterDoc = filterDoc.append("commonName", commonName);
        }

        FindIterable<Document> matchingPlants = plantCollection.find(filterDoc);


        return JSON.serialize(matchingPlants);
    }

    public String destroyDb(){
        MongoDatabase db = mongoClient.getDatabase(dbName);

        db.drop();

        return "destroyed";
    }

    public String postData(String uploadId) {
        Document filterDoc = new Document();
        filterDoc.append("uploadId", uploadId);

        FindIterable matchingPlants = plantCollection.find(filterDoc);

        Iterator iterator = matchingPlants.iterator();
        graphInfoCollection.drop();

        while (iterator.hasNext()) {
            Document result = (Document) iterator.next();
            int total = 0;
            int likes = 0;
            int dislikes = 0;
            Document out = new Document();

            String name = (String) result.get("cultivar");
            List<Document> ratings = (List<Document>) ((Document) result.get("metadata")).get("ratings");
            int view = (int) ((Document) result.get("metadata")).get("pageViews");
            String id = (String) result.get("id");
            for (Document rating : ratings) {
                if (rating.get("like").equals(true)) {
                    total++;
                    likes++;
                } else if (rating.get("like").equals(false)) {
                    total--;
                    dislikes++;
                }
            }

            out.append("cultivar", name);
            out.append("rating", total);
            out.append("pageViews", view);
            out.append("likes", likes);
            out.append("dislikes", dislikes);
            out.append("id", id);
            out.append("gardenLocation", result.get("gardenLocation"));

            graphInfoCollection.insertOne(out);

        }


        return "posted";
    }

    public String getLikeDataForAllPlants(String uploadId) {
        ArrayList<String> beds = new ArrayList<>();
        FindIterable<Document> allData = graphInfoCollection.find();
        Iterator iterator = allData.iterator();

        while (iterator.hasNext()) {
            Document current = (Document) iterator.next();
            String gardenLocation = (String) current.get("gardenLocation");

            if (!beds.contains((gardenLocation))) {
                beds.add(gardenLocation);
            }
        }

        Object[][] dataTable = new Object[beds.size() + 1][3];

        dataTable[0][0] = "Bed";
        dataTable[0][1] = "Total Likes";
        dataTable[0][2] = "Total Visits";
        int dataCounter = 1;
        for (int i = 0; i < beds.size(); i++) {
            FindIterable<Document> currentBedMembers = graphInfoCollection.find(new Document("gardenLocation", beds.get(i)));
            iterator = currentBedMembers.iterator();
            dataTable[dataCounter][0] = beds.get(i);
            Integer currentRating = 0;
            Integer currentViews = 0;
            while(iterator.hasNext()){
                Document current = (Document) iterator.next();
                currentRating += (Integer) current.get("rating");
                currentViews += (Integer) current.get("pageViews");
            }
            dataTable[dataCounter][1] = currentRating;
            dataTable[dataCounter][2] = currentViews;
            dataCounter++;
        }

        System.out.println(makeJSON(dataTable));
        return makeJSON(dataTable);
    }

    public String getDataForOneBed(String uploadid, String gardenLocation){
        FindIterable<Document> matchingInfo = graphInfoCollection.find(new Document("gardenLocation", gardenLocation));
        Iterator iterator = matchingInfo.iterator();
        ArrayList<Document> info = new ArrayList<>();

        while(iterator.hasNext()){
            Document current = (Document) iterator.next();
            info.add(current);
        }

        Object[][] dataTable = new Object[info.size() + 1][3];
        dataTable[0][0] = "Cultivar";
        dataTable[0][1] = "Rating";
        dataTable[0][2] = "Visits";
        int dataCounter = 1;
        for(int i = 0; i < info.size(); i++){
            dataTable[dataCounter][0] = info.get(i).get("cultivar");
            dataTable[dataCounter][1] = info.get(i).get("rating");
            dataTable[dataCounter][2] = info.get(i).get("pageViews");
            dataCounter++;
        }
        System.out.println(makeJSON(dataTable));
        return makeJSON(dataTable);
    }

    //taken from revolverenguardia
    public String makeJSON(Object[][] in) {
        JsonArray outerArray = new JsonArray();
        for (int i = 0; i < in.length; i++) {
            JsonArray innerArray = new JsonArray();
            for (int j = 0; j < in[i].length; j++) {
                Object a = in[i][j];
                Class objectClass = a.getClass();
                if (objectClass == String.class) {
                    innerArray.add(in[i][j].toString());
                } else if (Number.class.isAssignableFrom(objectClass)) {
                    innerArray.add((Number) in[i][j]);
                } else if (objectClass == JsonElement.class) {
                    innerArray.add((JsonElement) in[i][j]);
                } else {
                    throw new RuntimeException("WHAT ARE YOU");
                }
            }

            outerArray.add(innerArray);
        }
        return outerArray.toString();
    }


    /**
     * Takes a String representing an ID number of a plant
     * and when the ID is found in the database returns a JSON document
     * as a String of the following form
     * <p>
     * <code>
     * {
     * "plantID"        : String,
     * "commonName" : String,
     * "cultivar"   : String
     * }
     * </code>
     * <p>
     * If the ID is invalid or not found, the following JSON value is
     * returned
     * <p>
     * <code>
     * null
     * </code>
     *
     * @param plantID  an ID number of a plant in the DB
     * @param uploadID Dataset to find the plant
     * @return a string representation of a JSON value
     */
    public String getPlantByPlantID(String plantID, String uploadID) {

        FindIterable<Document> jsonPlant;
        String returnVal;
        try {

            jsonPlant = plantCollection.find(and(eq("id", plantID),
                    eq("uploadId", uploadID)))
                    .projection(fields(include("commonName", "cultivar")));

            Iterator<Document> iterator = jsonPlant.iterator();

            if (iterator.hasNext()) {
                incrementMetadata(plantID, "pageViews");
                addVisit(plantID);
                returnVal = iterator.next().toJson();
            } else {
                returnVal = "null";
            }

        } catch (IllegalArgumentException e) {
            returnVal = "null";
        }

        return returnVal;

    }

    /**
     * @param plantID  The plant to get feedback of
     * @param uploadID Dataset to find the plant
     * @return JSON for the number of comments, likes, and dislikes
     * Of the form:All
     * {
     * commentCount: number
     * likeCount: number
     * dislikeCount: number
     * }
     */

    public String getFeedbackForPlantByPlantID(String plantID, String uploadID) {
        Document out = new Document();

        Document filter = new Document();
        filter.put("commentOnPlant", plantID);
        filter.put("uploadId", uploadID);
        long comments = commentCollection.count(filter);
        long likes = 0;
        long dislikes = 0;


        //Get a plant by plantID
        FindIterable doc = plantCollection.find(new Document().append("id", plantID).append("uploadId", uploadID));

        Iterator iterator = doc.iterator();
        if (iterator.hasNext()) {
            Document result = (Document) iterator.next();

            //Get metadata.rating array
            List<Document> ratings = (List<Document>) ((Document) result.get("metadata")).get("ratings");

            //Loop through all of the entries within the array, counting like=true(like) and like=false(dislike)
            for (Document rating : ratings) {
                if (rating.get("like").equals(true))
                    likes++;
                else if (rating.get("like").equals(false))
                    dislikes++;
            }
        }


        out.put("commentCount", comments);
        out.put("likeCount", likes);
        out.put("dislikeCount", dislikes);
        return JSON.serialize(out);
    }

    public String getGardenLocationsAsJson(String uploadID) {
        AggregateIterable<Document> documents
                = plantCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(eq("uploadId", uploadID)), //!! Order is important here
                        Aggregates.group("$gardenLocation"),
                        Aggregates.sort(Sorts.ascending("_id"))
                ));
        return JSON.serialize(documents);
    }

    public String[] getGardenLocations(String uploadID) {
        Document filter = new Document();
        filter.append("uploadId", uploadID);
        DistinctIterable<String> bedIterator = plantCollection.distinct("gardenLocation", filter, String.class);
        List<String> beds = new ArrayList<String>();
        for (String s : bedIterator) {
            beds.add(s);
        }
        return beds.toArray(new String[beds.size()]);
    }

    /**
     * Accepts string representation of JSON object containing
     * at least the following.
     * <code>
     * {
     * plantId: String,
     * comment: String
     * }
     * </code>
     * If either of the keys are missing or the types of the values are
     * wrong, false is returned.
     *
     * @param json     string representation of JSON object
     * @param uploadID Dataset to find the plant
     * @return true iff the comment was successfully submitted
     */

    public boolean storePlantComment(String json, String uploadID) {

        try {

            Document toInsert = new Document();
            Document parsedDocument = Document.parse(json);

            if (parsedDocument.containsKey("plantId") && parsedDocument.get("plantId") instanceof String) {

                FindIterable<Document> jsonPlant = plantCollection.find(eq("_id",
                        new ObjectId(parsedDocument.getString("plantId"))));

                Iterator<Document> iterator = jsonPlant.iterator();

                if (iterator.hasNext()) {
                    toInsert.put("commentOnPlant", iterator.next().getString("id"));
                } else {
                    return false;
                }

            } else {
                return false;
            }

            if (parsedDocument.containsKey("comment") && parsedDocument.get("comment") instanceof String) {
                toInsert.put("comment", parsedDocument.getString("comment"));
            } else {
                return false;
            }

            toInsert.append("uploadId", uploadID);

            commentCollection.insertOne(toInsert);

        } catch (BsonInvalidOperationException e) {
            e.printStackTrace();
            return false;
        } catch (org.bson.json.JsonParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public void writeComments(OutputStream outputStream, String uploadId) throws IOException {

        FindIterable iter = commentCollection.find(
                and(
                        exists("commentOnPlant"),
                        eq("uploadId", uploadId)
                ));
        Iterator iterator = iter.iterator();

        CommentWriter commentWriter = new CommentWriter(outputStream);

        while (iterator.hasNext()) {
            Document comment = (Document) iterator.next();
            commentWriter.writeComment(comment.getString("commentOnPlant"),
                    comment.getString("comment"),
                    ((ObjectId) comment.get("_id")).getDate());
        }
        commentWriter.complete();
    }

    /**
     * Adds a like or dislike to the specified plant.
     *
     * @param id       a hexstring specifiying the oid
     * @param like     true if this is a like, false if this is a dislike
     * @param uploadID Dataset to find the plant
     * @return true iff the operation succeeded.
     */

    public boolean addFlowerRating(String id, boolean like, String uploadID) {

        Document filterDoc = new Document();

        ObjectId objectId;

        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return false;
        }

        filterDoc.append("_id", new ObjectId(id));
        filterDoc.append("uploadId", uploadID);

        Document rating = new Document();
        rating.append("like", like);
        rating.append("ratingOnObjectOfId", objectId);

        return null != plantCollection.findOneAndUpdate(filterDoc, push("metadata.ratings", rating));
    }

    /**
     * Accepts string representation of JSON object containing
     * at least the following:
     * <code>
     * {
     * id: String,
     * like: boolean
     * }
     * </code>
     *
     * @param json     string representation of a JSON object
     * @param uploadID Dataset to find the plant
     * @return true iff the operation succeeded.
     */

    public boolean addFlowerRating(String json, String uploadID) {
        boolean like;
        String id;

        try {

            Document parsedDocument = Document.parse(json);

            if(parsedDocument.containsKey("id") && parsedDocument.get("id") instanceof String){
                id = parsedDocument.getString("id");
            } else {
                return false;
            }

            if(parsedDocument.containsKey("like") && parsedDocument.get("like") instanceof Boolean){
                like = parsedDocument.getBoolean("like");
            } else {
                return false;
            }

        } catch (BsonInvalidOperationException e){
            e.printStackTrace();
            return false;
        } catch (org.bson.json.JsonParseException e){
            return false;
        }

        return addFlowerRating(id, like, uploadID);
    }

    /**
     *
     * @return a sorted JSON array of all the distinct uploadIds in the DB
     */
    public String listUploadIds() {
        AggregateIterable<Document> documents
                = plantCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$uploadId"),
                        Aggregates.sort(Sorts.ascending("_id"))
                ));
        List<String> lst = new LinkedList<>();
        for(Document d: documents) {
            lst.add(d.getString("_id"));
        }
        return JSON.serialize(lst);
//        return JSON.serialize(plantCollection.distinct("uploadId","".getClass()));
    }






    /**
     * Finds a plant and atomically increments the specified field
     * in its metadata object. This method returns true if the plant was
     * found successfully (false otherwise), but there is no indication of
     * whether the field was found.
     *
     * @param plantID a ID number of a plant in the DB
     * @param field a field to be incremented in the metadata object of the plant
     * @return true if a plant was found
     * @throws com.mongodb.MongoCommandException when the id is valid and the field is empty
     */
    public boolean incrementMetadata(String plantID, String field) {

        Document searchDocument = new Document();
        searchDocument.append("id", plantID);

        Bson updateDocument = inc("metadata." + field, 1);

        return null != plantCollection.findOneAndUpdate(searchDocument, updateDocument);
    }
    public boolean addVisit(String plantID) {

        Document filterDoc = new Document();
        filterDoc.append("id", plantID);

        Document visit = new Document();
        visit.append("visit", new ObjectId());

        return null != plantCollection.findOneAndUpdate(filterDoc, push("metadata.visits", visit));
    }

}