package evlibsim;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import static evlibsim.EVLibSim.*;

class Search {

    private static final Menu search = new Menu("Search");
    private static final MenuItem searchCharging = new MenuItem("Search ChargingEvent");
    private static final MenuItem searchDisCharging = new MenuItem("Search DisChargingEvent");
    private static final MenuItem searchExchangeEvent = new MenuItem("Search Exchange Battery Event");
    private static final MenuItem searchParkingSlot = new MenuItem("Search ParkingEvent");

    public static Menu createSearchMenu()
    {
        searchCharging.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t.setText("ChargingEvent Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            root.setCenter(grid);
        });
        searchDisCharging.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t.setText("Discharging Event Search");
            grid.add(t, 0, 0, 2, 1);
            t.setId("welcome");
            foo = new Label("Model of car:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            root.setCenter(grid);
        });
        searchExchangeEvent.setOnAction(e ->
        {
            Maintenance.cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t.setText("Exchange Event Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Kind of battery:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            root.setCenter(grid);
        });
        search.getItems().addAll(searchCharging, searchDisCharging, searchExchangeEvent, searchParkingSlot);
        return search;
    }
}
