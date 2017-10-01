package evlibsim;

import EVLib.EV.Battery;
import EVLib.EV.Driver;
import EVLib.EV.ElectricVehicle;
import EVLib.Events.ChargingEvent;
import EVLib.Events.DisChargingEvent;
import EVLib.Events.ParkingEvent;
import EVLib.Events.PricingPolicy;
import EVLib.Station.ChargingStation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static evlibsim.EVLibSim.*;

class Event {

    static final MenuItem charging = new MenuItem("New charging");
    static final MenuItem discharging = new MenuItem("New discharging");
    static final MenuItem exchange = new MenuItem("New battery exchange)");
    static final MenuItem parking = new MenuItem("New parking");
    private static final Menu event = new Menu("Event");
    private static final MenuItem policy = new MenuItem("Pricing policy");
    private static final Button chargingEventCreation = new Button("Creation");
    private static final Button disChargingEventCreation = new Button("Creation");
    private static final Button parkingEventCreation = new Button("Creation");
    private static final Button exchangeEventCreation = new Button("Creation");
    private static final Button policyCreation1 = new Button("Creation");
    private static final Button policyCreation2 = new Button("Creation");
    static final Button suggest1 = new Button("Suggestions");
    static final Button suggest2 = new Button("Suggestions");
    static final Button suggest3 = new Button("Suggestions");
    static final Button suggest4 = new Button("Suggestions");
    private static String kindOfCharging;

    //Builds the Event category in the main MenuBar
    static Menu createEventMenu() {
        //Implements the New ChargingEvent MenuItem
        charging.setOnAction(e ->
        {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand: ");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Battery capacity: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining: ");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Waiting time: ");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Kind of Charging: ");
            EVLibSim.grid.add(foo, 0, 4);
            MenuBar sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            Menu src = new Menu("Kinds");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem slow = new RadioMenuItem("slow");
            RadioMenuItem fast = new RadioMenuItem("fast");
            r.getToggles().addAll(slow, fast);
            slow.setSelected(true);
            kindOfCharging = "slow";
            src.getItems().addAll(slow, fast);
            sourc.getMenus().add(src);
            grid.add(sourc, 1, 4);
            foo = new Label("Money: ");
            grid.add(foo, 2, 4);
            boo = new TextField();
            grid.add(boo, 3, 4);
            textfields.add(boo);
            grid.add(chargingEventCreation, 0, 5);
            //Suggestion button in the New ChargingEvent MenuItem
            suggest1.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions Box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#e0e2e6;");
                g.setHgap(30);
                g.setVgap(30);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestEnergy()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                VBox inside2 = new VBox();
                inside2.setMaxSize(80, 30);
                title = new Label("Energy");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestPrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getCurrentPrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(70, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("slow")).subList(0, Math.min(stations.size(), 5)));
                VBox inside5 = new VBox();
                inside5.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside5.setPadding(new Insets(10, 10, 10, 10));
                inside5.setSpacing(20);
                tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("slow"))));
                VBox inside6 = new VBox();
                inside6.setMaxSize(150, 30);
                title = new Label("Wait(Slow)");
                inside6.getChildren().add(title);
                inside6.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside6.setPadding(new Insets(10, 10, 10, 10));
                inside6.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside6, inside5);
                g.add(outside, 2, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("fast")).subList(0, Math.min(stations.size(), 5)));
                VBox inside7 = new VBox();
                inside7.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside7.setPadding(new Insets(10, 10, 10, 10));
                inside7.setSpacing(20);
                tempStations.forEach(temp -> inside7.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("fast"))));
                VBox inside8 = new VBox();
                inside8.setMaxSize(150, 30);
                title = new Label("Wait(Fast)");
                inside8.getChildren().add(title);
                inside8.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside8.setPadding(new Insets(10, 10, 10, 10));
                inside8.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside8, inside7);
                g.add(outside, 3, 0);

                Button close= new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 850, 450);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            grid.add(suggest1, 1, 5);
            chargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
            slow.setOnAction(ew -> {
                if (slow.isSelected())
                    kindOfCharging = "slow";
            });
            fast.setOnAction(ew -> {
                if (fast.isSelected())
                    kindOfCharging = "fast";
            });
        });
        //Implements the New DisChargingEvent MenuItem
        discharging.setOnAction(e ->
        {
            if(Maintenance.stationCheck())
                return;

            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand: ");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Battery capacity: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining: ");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Waiting time: ");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            grid.add(disChargingEventCreation, 0, 4);
            //Suggestion button for DisChargingEvent MenuItem
            suggest2.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions Box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#e0e2e6;");
                g.setHgap(30);
                g.setVgap(30);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestEnergy()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                VBox inside2 = new VBox();
                inside2.setMaxSize(80, 30);
                title = new Label("Energy");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestDisPrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getDisUnitPrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(70, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("discharging")).subList(0, Math.min(stations.size(), 5)));
                VBox inside5 = new VBox();
                inside5.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside5.setPadding(new Insets(10, 10, 10, 10));
                inside5.setSpacing(20);
                tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("discharging"))));
                VBox inside6 = new VBox();
                inside6.setMaxSize(150, 30);
                title = new Label("Wait");
                inside6.getChildren().add(title);
                inside6.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside6.setPadding(new Insets(10, 10, 10, 10));
                inside6.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside6, inside5);
                g.add(outside, 2, 0);

                Button close= new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 650, 450);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            grid.add(suggest2, 1, 4);
            disChargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        //Implements the New ChargingEvent(exchange)
        exchange.setOnAction(e ->
        {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand: ");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Battery capacity: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining: ");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Waiting time: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            grid.add(exchangeEventCreation, 0, 4);
            //Suggestion button for New ChargingEvent(exchange)
            suggest3.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions Box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#e0e2e6;");
                g.setHgap(30);
                g.setVgap(30);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestBatteries()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getBatteries().size())));
                VBox inside2 = new VBox();
                inside2.setMaxSize(80, 30);
                title = new Label("BatInventory");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestExchangePrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getExchangePrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(70, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("exchange")).subList(0, Math.min(stations.size(), 5)));
                VBox inside5 = new VBox();
                inside5.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside5.setPadding(new Insets(10, 10, 10, 10));
                inside5.setSpacing(20);
                tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("exchange"))));
                VBox inside6 = new VBox();
                inside6.setMaxSize(150, 30);
                title = new Label("Wait");
                inside6.getChildren().add(title);
                inside6.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside6.setPadding(new Insets(10, 10, 10, 10));
                inside6.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside6, inside5);
                g.add(outside, 2, 0);

                Button close= new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 650, 450);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            grid.add(suggest3, 1, 4);
            exchangeEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        //Implements the New ParkingEvent MenuItem
        parking.setOnAction(e ->
        {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand: ");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Battery capacity: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining: ");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Parking time: ");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            grid.add(parkingEventCreation, 0, 4);
            //Suggestion button for New ParkingEvent MenuItem
            suggest4.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions Box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#e0e2e6;");
                g.setHgap(30);
                g.setVgap(30);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestEnergy()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                VBox inside2 = new VBox();
                inside2.setMaxSize(80, 30);
                title = new Label("Energy");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestPrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getInductivePrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(70, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#e3e4e6; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                Button close= new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 450, 450);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            grid.add(suggest4, 1, 4);
            parkingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        //Implements the New PricingPolicy MenuItem
        policy.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select the kind of policy: ");
            ButtonType buttonTypeOne = new ButtonType("Stable space");
            ButtonType buttonTypeTwo = new ButtonType("Changing space");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            TextField boo;
            Label foo;
            grid.setMaxSize(500, 300);
            if(result.isPresent()) {
                if (result.get() == buttonTypeOne) {
                    Maintenance.cleanScreen();
                    foo = new Label("Duration: ");
                    grid.add(foo, 0, 1);
                    boo = new TextField();
                    grid.add(boo, 1, 1);
                    textfields.add(boo);
                    foo = new Label("Prices(Separated with comma): ");
                    grid.add(foo, 0, 2);
                    boo = new TextField();
                    grid.add(boo, 1, 2);
                    textfields.add(boo);
                    grid.add(policyCreation1, 0, 3);
                    policyCreation1.setDefaultButton(true);
                } else if (result.get() == buttonTypeTwo) {
                    Maintenance.cleanScreen();
                    foo = new Label("Durations(Separated with comma): ");
                    grid.add(foo, 0, 1);
                    boo = new TextField();
                    grid.add(boo, 1, 1);
                    textfields.add(boo);
                    foo = new Label("Prices(Separated with comma): ");
                    grid.add(foo, 0, 2);
                    boo = new TextField();
                    grid.add(boo, 1, 2);
                    textfields.add(boo);
                    grid.add(policyCreation2, 0, 3);
                    policyCreation2.setDefaultButton(true);
                }
            }
            root.setCenter(grid);
        });

        //Buttons
        chargingEventCreation.setOnAction(e -> {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z0-9.]", "")));
            if (Double.parseDouble(textfields.get(2).getText()) < 0 ||
                    Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(4).getText()) == 0 && Double.parseDouble(textfields.get(6).getText()) == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Money and asking amount cannot be both zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(4).getText()) > (Double.parseDouble(textfields.get(2).getText()) - Double.parseDouble(textfields.get(3).getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The asking amount of energy cannot be greater than the remaining capacity.");
                alert.showAndWait();
                return;
            }
            try {
                ChargingEvent ch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (!Objects.equals(textfields.get(4).getText(), "0"))
                    ch = new ChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()), kindOfCharging);
                else
                    ch = new ChargingEvent(currentStation, el, kindOfCharging, Double.parseDouble(textfields.get(6).getText()));
                ch.setWaitingTime(Long.parseLong(textfields.get(5).getText()));
                ch.preProcessing();
                if (ch.getCondition().equals("ready"))
                    ch.execution();
                else if (ch.getCondition().equals("wait")) {
                    Maintenance.queueInsertion();
                    return;
                } else {
                    Maintenance.noExecution();
                    return;
                }
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                charging.fire();
            }
            Maintenance.completionMessage("ChargingEvent creation");
            charging.fire();
        });
        disChargingEventCreation.setOnAction(e ->
        {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z0-9.]", "")));
            if (Double.parseDouble(textfields.get(2).getText()) < 0 ||
                    Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(4).getText()) > (Double.parseDouble(textfields.get(3).getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The given amount of energy cannot be greater than the remaining amount.");
                alert.showAndWait();
                return;
            }
            try {
                DisChargingEvent dsch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (!Objects.equals(textfields.get(4).getText(), "0")) {
                    dsch = new DisChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()));
                    dsch.setWaitingTime(Long.parseLong(textfields.get(5).getText()));
                    dsch.preProcessing();
                    if (dsch.getCondition().equals("ready"))
                        dsch.execution();
                    else if (dsch.getCondition().equals("wait")) {
                        Maintenance.queueInsertion();
                        return;
                    } else {
                        Maintenance.noExecution();
                        return;
                    }
                    dsch.execution();
                }
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                discharging.fire();
            }
            Maintenance.completionMessage("DisChargingEvent creation");
            discharging.fire();
        });
        exchangeEventCreation.setOnAction(e -> {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z0-9.]", "")));
            if (Double.parseDouble(textfields.get(2).getText()) < 0 ||
                    Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            try {
                ChargingEvent ch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                ch = new ChargingEvent(currentStation, el);
                ch.setWaitingTime(Long.parseLong(textfields.get(4).getText()));
                ch.preProcessing();
                if (ch.getCondition().equals("ready"))
                    ch.execution();
                else if (ch.getCondition().equals("wait")) {
                    Maintenance.queueInsertion();
                    return;
                } else {
                    Maintenance.noExecution();
                    return;
                }
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                exchange.fire();
            }
            Maintenance.completionMessage("ChargingEvent creation");
            exchange.fire();
        });
        parkingEventCreation.setOnAction(e -> {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z0-9.]", "")));
            if (Double.parseDouble(textfields.get(2).getText()) < 0 ||
                    Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText()) +
                    Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a smaller amount.");
                alert.showAndWait();
                return;
            }
            try {
                ParkingEvent ch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (!Objects.equals(textfields.get(4).getText(), "0")) {
                    ch = new ParkingEvent(currentStation, el, Long.parseLong(textfields.get(5).getText()), Double.parseDouble(textfields.get(4).getText()));
                    ch.preProcessing();
                    if (ch.getCondition().equals("ready"))
                        ch.execution();
                    else {
                        Maintenance.noExecution();
                        return;
                    }
                } else if (!Objects.equals(textfields.get(5).getText(), "0")) {
                    ch = new ParkingEvent(currentStation, el, Long.parseLong(textfields.get(5).getText()));
                    ch.preProcessing();
                    if (ch.getCondition().equals("ready"))
                        ch.execution();
                    else {
                        Maintenance.noExecution();
                        return;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select at least a positive parking time.");
                    alert.showAndWait();
                    return;
                }
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                parking.fire();
            }
            Maintenance.completionMessage("ParkingEvent creation");
            parking.fire();
        });
        policyCreation1.setOnAction(e -> {
            if(Maintenance.fieldCompletionCheck())
                return;
            if(Maintenance.confirmCreation())
                return;
            String text = textfields.get(1).getText().replaceAll("[^0-9,]+","");
            textfields.get(1).setText(text);
            String[] prices = textfields.get(1).getText().split(",");
            double[] p = new double[prices.length];
            for(int i=0; i<prices.length; i++)
                p[i] = Double.parseDouble(prices[i]);
            PricingPolicy policy = new PricingPolicy(Long.parseLong(textfields.get(0).getText()), p);
            currentStation.setPricingPolicy(policy);
            Maintenance.completionMessage("PricingPolicy creation");
        });
        policyCreation2.setOnAction(e -> {
            if(Maintenance.fieldCompletionCheck())
                return;
            if(Maintenance.confirmCreation())
                return;
            String text = textfields.get(0).getText().replaceAll("[^0-9,]+","");
            textfields.get(0).setText(text);
            String[] spaces = textfields.get(0).getText().split(",");
            text = textfields.get(1).getText().replaceAll("[^0-9,]+","");
            textfields.get(1).setText(text);
            String[] prices = textfields.get(1).getText().split(",");
            if(spaces.length != prices.length) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The number of time spaces and prices have to be equal.");
                alert.showAndWait();
                return;
            }
            long[] s = new long[spaces.length];
            double[] p = new double[prices.length];
            for(int i = 0; i<spaces.length; i++) {
                s[i] = Long.parseLong(spaces[i]);
                p[i] = Double.parseDouble(prices[i]);
            }
            PricingPolicy policy = new PricingPolicy(s, p);
            currentStation.setPricingPolicy(policy);
            Maintenance.completionMessage("PricingPolicy creation");
        });
        event.getItems().addAll(charging, discharging, exchange, parking, new SeparatorMenuItem(), policy);
        return event;
    }

    //Returns the ChargingStation objects in a descending order based on their energy inventory.
    private static ChargingStation[] bestEnergy() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for(int i = 0; i < st.length; i++){
            for(int j = 1; j < (st.length - i); j++){
                if(st[j-1].getTotalEnergy() < st[j].getTotalEnergy()){
                    temp = st[j-1];
                    st[j-1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }

    //Returns the ChargingStation objects in an ascending order based on their price per energy unit for the charging function.
    private static ChargingStation[] bestPrice() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for(int i = 0; i < st.length; i++){
            for(int j = 1; j < (st.length - i); j++){
                if(st[j-1].getCurrentPrice() > st[j].getCurrentPrice()){
                    temp = st[j-1];
                    st[j-1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }

    //Returns the ChargingStation objects in an ascending order based on their price per swapping for the exchange function.
    private static ChargingStation[] bestExchangePrice() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getExchangePrice() > st[j].getExchangePrice()) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }

    //Returns the ChargingStation objects in an ascending order based on their price per energy unit for the discharging function.
    private static ChargingStation[] bestDisPrice() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for(int i = 0; i < st.length; i++){
            for(int j = 1; j < (st.length - i); j++){
                if(st[j-1].getDisUnitPrice() > st[j].getDisUnitPrice()){
                    temp = st[j-1];
                    st[j-1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }

    //Returns the ChargingStation objects in an ascending order based on their price per energy unit for the inductive charging.
    private static ChargingStation[] bestInductivePrice() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getInductivePrice() > st[j].getInductivePrice()) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }

    private static ChargingStation[] bestBatteries() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getBatteries().size() < st[j].getBatteries().size()) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }

    //Returns the ChargingStation objects in an ascending order based on their waiting time for the given function(slow, fast, exchange, park).
    private static ChargingStation[] bestTime(String kind) {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for(int i = 0; i < st.length; i++){
            for(int j = 1; j < (st.length - i); j++){
                if(st[j-1].getWaitingTime(kind) > st[j].getWaitingTime(kind)){
                    temp = st[j-1];
                    st[j-1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }
}