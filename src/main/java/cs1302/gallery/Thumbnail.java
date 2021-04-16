package cs1302.gallery;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Thumbnail extends ImageView {

    Image img;
    private static final String DEFAULT_IMG =
        "http://cobweb.cs.uga.edu/~mec/cs1302/gui/default.png";

    public Thumbnail() {
        super();
        img = new Image(DEFAULT_IMG, 145, 100, false, false);
        setImage(img);
        setFitWidth(145);
        setFitHeight(100);

        setPreserveRatio(true);
    }
}
