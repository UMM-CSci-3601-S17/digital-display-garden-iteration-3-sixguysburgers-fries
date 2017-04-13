package umm3601.digitalDisplayGarden;

import org.joda.time.DateTime;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by hamme503 on 4/6/17.
 */
public class ImageHandler {

    private InputStream stream;
    private InputStream stream0;
    private Image image;
    private String fileName;
    private String imgFileName;

    public ImageHandler(InputStream stream, InputStream stream0) {
        this.stream = stream;
        this.stream0 = stream0;
    }

    public Image extractImage() {
        try {
            image = ImageIO.read(stream);
            System.out.println("We got to the handler!");
        } catch (IOException e) {
        }
        return image;
    }

    public String extractFileName() {

        String toReturn = convertStreamToString(stream0);
        return toReturn;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getAvailableUploadId(){

        StringBuilder sb = new StringBuilder();
        // Send all output to the Appendable object sb
        Formatter formatter = new Formatter(sb);

        java.util.Date juDate = new Date();
        DateTime dt = new DateTime(juDate);

        int day = dt.getDayOfMonth();
        int month = dt.getMonthOfYear();
        int year = dt.getYear();
        int hour = dt.getHourOfDay();
        int minute = dt.getMinuteOfHour();
        int seconds = dt.getSecondOfMinute();

        formatter.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, seconds);
        return sb.toString();

    }



}