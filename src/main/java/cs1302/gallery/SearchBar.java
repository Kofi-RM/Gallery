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

public class SearchBar extends HBox {

    Text text = new Text("Search Query:");
    TextField url = new TextField();
    Button pause = new Button("Pause");
    Button update = new Button("Update Images");
    String apple1 = "https://itunes.apple.com/search?term=";
    String apple2 = "&limit=5&media=music";


    public SearchBar() {
        super(5);
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

            URL search = new URL(address);

            InputStreamReader reader = new InputStreamReader(search.openStream());
            JsonElement fuckme = JsonParser.parseReader(reader);

            System.out.println( "\n"  + fuckme);

            JsonObject root = fuckme.getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");
            int numResults = results.size();
            System.out.println(numResults);
        } catch (IOException ex) {
            //textFlow.getChildren().add(new Text(ex.getMessage()));
        } // try
        System.out.println(address);
    }
}
