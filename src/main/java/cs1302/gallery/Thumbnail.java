package cs1302.gallery;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Thumbnail extends ImageView {

    Image img;
    private static final String DEFAULT_IMG =
        "http://cobweb.cs.uga.edu/~mec/cs1302/gui/default.png";

    public Thumbnail() {
        super();
        img = new Image(DEFAULT_IMG, 50, 50, false, false);
        setImage(img);
    }
}
