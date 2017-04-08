package umm3601.digitalDisplayGarden;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.awt.Image;
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
        } catch (IOException e) {
        }
        return image;
    }



}
