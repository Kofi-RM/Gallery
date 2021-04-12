package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.geometry.Insets;
import java.util.Scanner;
import java.io.IOException;
import java.net.URL;
import java.io.InputStreamReader;
import com.google.gson.*;
import javafx.scene.image.*;

public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    TextField url = new TextField();
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");
    String apple1 = "https://itunes.apple.com/search?term=";
    String apple2 = "&limit=35&media=music";
    GalleryApp appl;
    String[] query = new String[35];
    URL search;
    InputStreamReader reader;
    JsonElement jetson;

    public SearchBar(GalleryApp app) {
        super(5);

        appl = app;

        SearchBar.setMargin(pause, new Insets(5));
        SearchBar.setMargin(text, new Insets(8, 0, 5, 0));
        SearchBar.setMargin(url, new Insets(5, 0, 5, 0));
        SearchBar.setMargin(update, new Insets(5, 5, 5, 0));

        update.setOnAction(e -> search());

        getChildren().addAll(pause, text, url, update);
    }

    public void search() {
        Scanner input = new Scanner(url.getText());
        String rawText = url.getText();
        String text;
        int number = 0;

        while(input.hasNext() == true) {
            number++;
            input.next();
        }

        if (number == 1) {
            text = rawText;
        } else if (number > 1) {

            Scanner input2 = new Scanner(url.getText());
            text = input2.next();

            for (int loop = 0; loop < number - 1; loop++) {
                text = text + "+" + input2.next();
            }
        } else {
            return;
            }

        String address = apple1 + text + apple2;
        try {

            search = new URL(address);

            reader = new InputStreamReader(search.openStream());
            jetson = JsonParser.parseReader(reader);



            JsonObject root = jetson.getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");
            int numImages = results.size();

            System.out.println("images " + numImages);

            if (numImages == 0) {
                return;
            }

            String picAdd = results.get(0).getAsJsonObject().get("artworkUrl100").toString();


            picAdd = picAdd.substring(1, picAdd.length() - 1);
            System.out.println("pic " + picAdd);
            Image pic = new Image(picAdd);
            appl.array[0].setImage(pic);
        } catch (IOException ex) {
            System.out.println("no image");
        } // try
        System.out.println(address);
    } // search

    public void defaultSearch() {

    }
}
