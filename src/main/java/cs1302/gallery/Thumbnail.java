package cs1302.gallery;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Thumbnail extends ImageView {

    Image img;
    private static final String DEFAULT_IMG =
        "http://cobweb.cs.uga.edu/~mec/cs1302/gui/default.png";

    public Thumbnail() {
        super();
        img = new Image(DEFAULT_IMG, 125, 90, false, false);
        setImage(img);
        //System.out.println(getFitWidth());
        //System.out.println(getFitHeight());
//        System.out.println(isPreserveRatio());
        setPreserveRatio(true);
        //setFitWidth(100);
        //setFitHeight(70);
    }
}
