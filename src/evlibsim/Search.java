package evlibsim;

import Events.ChargingEvent;
import Events.DisChargingEvent;
import Events.ParkingEvent;
import javafx.scene.control.*;

import static evlibsim.EVLibSim.*;

class Search {

    private static final Menu search = new Menu("Search");
    private static final MenuItem searchCharging = new MenuItem("Search ChargingEvent");
    private static final MenuItem searchDisCharging = new MenuItem("Search DisChargingEvent");
    private static final MenuItem searchExchange = new MenuItem("Search Exchange Battery Event");
    private static final MenuItem searchParking = new MenuItem("Search ParkingEvent");
    private static final Button searchChargingEvent = new Button("Search");
    private static final Button searchDisChargingEvent = new Button("Search");
    private static final Button searchExchangeEvent = new Button("Search");
    private static final Button searchParkingEvent = new Button("Search");

    static Menu createSearchMenu()
    {
        searchCharging.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t.setText("ChargingEvent Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            grid.add(searchChargingEvent, 0, 4);
            searchChargingEvent.setDefaultButton(true);
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
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            grid.add(searchDisChargingEvent, 0, 4);
            searchChargingEvent.setDefaultButton(true);
            root.setCenter(grid);
        });
        searchExchange.setOnAction(e ->
        {
            Maintenance.cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t.setText("Exchange Event Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            grid.add(searchChargingEvent, 0, 3);
            searchExchangeEvent.setDefaultButton(true);
            root.setCenter(grid);
        });

        searchParking.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t.setText("ParkingEvent Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            foo = new Label("Parking time: ");
            grid.add(foo, 0, 4);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 4);
            grid.add(searchParkingEvent, 0, 5);
            searchParkingEvent.setDefaultButton(true);
            root.setCenter(grid);
        });
        searchChargingEvent.setOnAction(e -> {
            for(ChargingEvent ch: ChargingEvent.chargingLog)
                if(ch.reElectricVehicle().reDriver().reName().equals(textfields.get(1).getText())&&
                        (ch.reElectricVehicle().reBrand().equals(textfields.get(0).getText())&&
                                (ch.reEnergyAmount()==Double.parseDouble(textfields.get(2).getText()))))
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
                else
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
        });
        searchDisChargingEvent.setOnAction(e -> {
            for(DisChargingEvent ch: DisChargingEvent.dischargingLog)
                if(ch.reElectricVehicle().reDriver().reName().equals(textfields.get(1).getText())&&
                        (ch.reElectricVehicle().reBrand().equals(textfields.get(0).getText())&&
                                (ch.reEnergyAmount()==Double.parseDouble(textfields.get(2).getText()))))
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
                else
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
        });
        searchExchangeEvent.setOnAction(e -> {
            for(ChargingEvent ch: ChargingEvent.exchangeLog)
                if(ch.reElectricVehicle().reDriver().reName().equals(textfields.get(1).getText())&&
                        (ch.reElectricVehicle().reBrand().equals(textfields.get(0).getText())))
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
                else
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
        });
        searchParkingEvent.setOnAction(e -> {
            for(ParkingEvent ch: ParkingEvent.parkLog)
                if(ch.reElectricVehicle().reDriver().reName().equals(textfields.get(1).getText())&&
                        (ch.reElectricVehicle().reBrand().equals(textfields.get(0).getText()))&&
                        (ch.reEnergyToBeReceived() == Double.parseDouble(textfields.get(2).getText()))&&
                        (ch.reParkingTime() == Double.parseDouble(textfields.get(3).getText())))
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
                else
                {
                    Alert dialog = new Alert(Alert.AlertType.INFORMATION);
                    dialog.setTitle("Search Results");
                    dialog.setHeaderText(null);
                    dialog.setContentText("We found it!");
                    dialog.showAndWait();
                }
        });
        search.getItems().addAll(searchCharging, searchDisCharging, searchExchange, searchParking);
        return search;
    }
}
