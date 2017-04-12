package umm3601.digitalDisplayGarden;

import org.joda.time.DateTime;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.awt.Image;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by hamme503 on 4/6/17.
 */
public class ImageHandler {

    private InputStream stream;
    private Image image;

    public ImageHandler(InputStream stream) {
        this.stream = stream;
    }

    public Image extractImage() {
        try {
            image = ImageIO.read(stream);
            System.out.println("We got to the handler!");
        } catch (IOException e) {
        }
        return image;
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