package evlibsim;

import evlib.ev.Battery;
import evlib.ev.Driver;
import evlib.ev.ElectricVehicle;
import evlib.station.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static evlibsim.EVLibSim.*;

class Event {

    static final MenuItem charging = new MenuItem("New charging");
    static final MenuItem discharging = new MenuItem("New discharging");
    static final MenuItem exchange = new MenuItem("New battery exchange");
    static final MenuItem parking = new MenuItem("New parking");
    static final Button suggest1 = new Button("Suggestions");
    static final Button suggest2 = new Button("Suggestions");
    static final Button suggest3 = new Button("Suggestions");
    static final Button suggest4 = new Button("Suggestions");
    private static final Menu event = new Menu("Event");
    private static final MenuItem policy = new MenuItem("New charging pricing policy");
    private static final MenuItem planExecution = new MenuItem("Plan execution");
    private static final Button chargingEventCreation = new Button("Creation");
    private static final Button disChargingEventCreation = new Button("Creation");
    private static final Button parkingEventCreation = new Button("Creation");
    private static final Button exchangeEventCreation = new Button("Creation");
    private static final Button policyCreation1 = new Button("Creation");
    private static final Button policyCreation2 = new Button("Creation");
    private static String kindOfCharging;
    private static final Image help = new Image("/help.png");

    //Builds the Event category in the main MenuBar
    static Menu createEventMenu() {
        //Implements the New ChargingEvent MenuItem
        charging.setOnAction(e ->
        {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 400);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The name of the driver."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 0);
            boo = new TextField();
            grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Vehicle's brand*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The brand of the vehicle."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 0);
            boo = new TextField();
            grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Battery capacity*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The capacity of the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Battery remaining*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The remaining energy in the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Amount of energy*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The asking amount of energy."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Kind of charging*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The desired charging ratio."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 2);
            kindOfCharging = "fast";
            ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("Fast", "Slow"));
            cb.getSelectionModel().selectedIndexProperty().addListener((ov, value, newValue) -> {
                if (newValue.intValue() == 0)
                    kindOfCharging = "fast";
                else
                    kindOfCharging = "slow";
            });
            cb.getSelectionModel().selectFirst();
            cb.setMaxWidth(150);
            grid.add(cb, 3, 2);
            //Suggestion button in the New ChargingEvent MenuItem
            suggest1.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions box");
                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color: #D8E2F2;");
                g.setHgap(15);
                g.setVgap(15);
                g.setPadding(new Insets(15, 15, 15, 15));
                VBox outside;
                Label title;
                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestEnergy()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setMinWidth(200);
                inside1.setMaxWidth(200);
                inside1.setStyle("-fx-background-color: #F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                VBox inside2 = new VBox();
                inside2.setMinSize(100, 30);
                inside2.setMaxSize(100, 30);
                title = new Label("Energy");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestPrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setMinWidth(200);
                inside3.setMaxWidth(200);
                inside3.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getCurrentPrice())));
                VBox inside4 = new VBox();
                inside4.setMinSize(100, 30);
                inside4.setMaxSize(100, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("slow")).subList(0, Math.min(stations.size(), 5)));
                VBox inside5 = new VBox();
                inside5.setMinWidth(200);
                inside5.setMaxWidth(200);
                inside5.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside5.setPadding(new Insets(10, 10, 10, 10));
                inside5.setSpacing(20);
                tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("slow"))));
                VBox inside6 = new VBox();
                inside6.setMinSize(100, 30);
                inside6.setMaxSize(100, 30);
                title = new Label("Wait(Slow)");
                inside6.getChildren().add(title);
                inside6.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside6.setPadding(new Insets(10, 10, 10, 10));
                inside6.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside6, inside5);
                g.add(outside, 2, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("fast")).subList(0, Math.min(stations.size(), 5)));
                VBox inside7 = new VBox();
                inside7.setMinWidth(200);
                inside7.setMaxWidth(200);
                inside7.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside7.setPadding(new Insets(10, 10, 10, 10));
                inside7.setSpacing(20);
                tempStations.forEach(temp -> inside7.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("fast"))));
                VBox inside8 = new VBox();
                inside8.setMinSize(100, 30);
                inside8.setMaxSize(100, 30);
                title = new Label("Wait(Fast)");
                inside8.getChildren().add(title);
                inside8.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside8.setPadding(new Insets(10, 10, 10, 10));
                inside8.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside8, inside7);
                g.add(outside, 3, 0);
                Button close = new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());
                Scene scene1 = new Scene(g, 870, 450);
                popupwindow.setMinWidth(850);
                popupwindow.setMinHeight(400);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, chargingEventCreation);
            buttonsBox.getChildren().add(1, suggest1);
            grid.add(buttonsBox, 2, 3, 2, 1);
            chargingEventCreation.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 3);
            root.setCenter(grid);
        });
        //Implements the New DisChargingEvent MenuItem
        discharging.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 400);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The name of the driver."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 0);
            boo = new TextField();
            grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Vehicle's brand*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The brand of the vehicle."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 0);
            boo = new TextField();
            grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Battery capacity*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The capacity of the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Battery remaining*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The remaining energy in the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Amount of energy*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The providing amount of energy."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            //Suggestion button for DisChargingEvent MenuItem
            suggest2.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#D8E2F2;");
                g.setHgap(15);
                g.setVgap(15);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestEnergy()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setMaxWidth(200);
                inside1.setMinWidth(200);
                inside1.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                VBox inside2 = new VBox();
                inside2.setMaxSize(100, 30);
                inside2.setMinSize(100, 30);
                title = new Label("Energy");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestDisPrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setMaxWidth(200);
                inside3.setMinWidth(200);
                inside3.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getDisUnitPrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(100, 30);
                inside4.setMinSize(100, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("discharging")).subList(0, Math.min(stations.size(), 5)));
                VBox inside5 = new VBox();
                inside5.setMaxWidth(200);
                inside5.setMinWidth(200);
                inside5.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside5.setPadding(new Insets(10, 10, 10, 10));
                inside5.setSpacing(20);
                tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("discharging"))));
                VBox inside6 = new VBox();
                inside6.setMaxSize(100, 30);
                inside6.setMinSize(100, 30);
                title = new Label("Wait");
                inside6.getChildren().add(title);
                inside6.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside6.setPadding(new Insets(10, 10, 10, 10));
                inside6.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside6, inside5);
                g.add(outside, 2, 0);

                Button close = new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 870, 450);
                popupwindow.setMinHeight(400);
                popupwindow.setMinWidth(850);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, disChargingEventCreation);
            buttonsBox.getChildren().add(1, suggest2);
            grid.add(buttonsBox, 2, 3, 2, 1);
            disChargingEventCreation.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 3);
            root.setCenter(grid);
        });
        //Implements the New ChargingEvent(exchange)
        exchange.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 400);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The name of the driver."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 0);
            boo = new TextField();
            grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Vehicle's brand*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The brand of the vehicle."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 0);
            boo = new TextField();
            grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Battery capacity*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The capacity of the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Battery remaining*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The remaining energy in the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            //Suggestion button for New ChargingEvent(exchange)
            suggest3.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#D8E2F2;");
                g.setHgap(15);
                g.setVgap(15);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestBatteries()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setMaxWidth(200);
                inside1.setMinWidth(200);
                inside1.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getBatteries().length)));
                VBox inside2 = new VBox();
                inside2.setMaxSize(100, 30);
                inside2.setMinSize(100, 30);
                title = new Label("Batteries");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestExchangePrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setMinWidth(200);
                inside3.setMaxWidth(200);
                inside3.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getExchangePrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(100, 30);
                inside4.setMinSize(100, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestTime("exchange")).subList(0, Math.min(stations.size(), 5)));
                VBox inside5 = new VBox();
                inside5.setMaxWidth(200);
                inside5.setMinWidth(200);
                inside5.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside5.setPadding(new Insets(10, 10, 10, 10));
                inside5.setSpacing(20);
                tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("exchange"))));
                VBox inside6 = new VBox();
                inside6.setMaxSize(100, 30);
                inside6.setMinSize(100, 30);
                title = new Label("Wait");
                inside6.getChildren().add(title);
                inside6.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside6.setPadding(new Insets(10, 10, 10, 10));
                inside6.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside6, inside5);
                g.add(outside, 2, 0);

                Button close = new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 870, 450);
                popupwindow.setMinWidth(850);
                popupwindow.setMinHeight(400);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, exchangeEventCreation);
            buttonsBox.getChildren().add(1, suggest3);
            grid.add(buttonsBox, 2, 2, 2, 1);
            exchangeEventCreation.setDefaultButton(true);
            grid.add(new Label("*Required"), 0 , 2);
            root.setCenter(grid);
        });
        //Implements the New ParkingEvent MenuItem
        parking.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 400);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The name of the driver."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 0);
            boo = new TextField();
            grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Vehicle's brand*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The brand of the vehicle."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 0);
            boo = new TextField();
            grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Battery capacity*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The capacity of the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Battery remaining*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The remaining energy in the battery."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Amount of energy*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The amount of energy the vehicle receives via inductive charging."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Parking time*: ");
            foo.setGraphic(new ImageView(help));
            foo.setTooltip(new Tooltip("The time the vehicle wants to park. During this time the vehicle either is parked, or it charges. If the amount of energy demands more time to be transfered then the vehicle receives energy as long as the parking time."));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            //Suggestion button for New ParkingEvent MenuItem
            suggest4.setOnAction(eu -> {
                Stage popupwindow = new Stage();
                popupwindow.initModality(Modality.APPLICATION_MODAL);
                popupwindow.setTitle("Suggestions box");

                GridPane g = new GridPane();
                g.setAlignment(Pos.CENTER);
                g.setStyle("-fx-background-color:#D8E2F2;");
                g.setHgap(15);
                g.setVgap(15);
                g.setPadding(new Insets(15, 15, 15, 15));

                VBox outside;
                Label title;

                ArrayList<ChargingStation> tempStations = new ArrayList<>(Arrays.asList(bestEnergy()).subList(0, Math.min(stations.size(), 5)));
                VBox inside1 = new VBox();
                inside1.setMinWidth(200);
                inside1.setMaxWidth(200);
                inside1.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside1.setPadding(new Insets(10, 10, 10, 10));
                inside1.setSpacing(20);
                tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                VBox inside2 = new VBox();
                inside2.setMaxSize(100, 30);
                inside2.setMinSize(100, 30);
                title = new Label("Energy");
                inside2.getChildren().add(title);
                inside2.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside2.setPadding(new Insets(10, 10, 10, 10));
                inside2.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside2, inside1);
                g.add(outside, 0, 0);

                tempStations = new ArrayList<>(Arrays.asList(bestInductivePrice()).subList(0, Math.min(stations.size(), 5)));
                VBox inside3 = new VBox();
                inside3.setMinWidth(200);
                inside3.setMaxWidth(200);
                inside3.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 0 5 5 5; -fx-background-radius: 0 5 5 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside3.setPadding(new Insets(10, 10, 10, 10));
                inside3.setSpacing(20);
                tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getInductivePrice())));
                VBox inside4 = new VBox();
                inside4.setMaxSize(100, 30);
                inside4.setMinSize(100, 30);
                title = new Label("Price");
                inside4.getChildren().add(title);
                inside4.setStyle("-fx-background-color:#F0F1F3; -fx-border-radius: 5 5 0 0; -fx-background-radius: 5 5 0 0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0.0, 0, 1);");
                inside4.setPadding(new Insets(10, 10, 10, 10));
                inside4.setSpacing(20);
                outside = new VBox();
                outside.getChildren().addAll(inside4, inside3);
                g.add(outside, 1, 0);

                Button close = new Button("Close");
                g.add(close, 0, 1);
                close.setOnAction(ew -> popupwindow.close());

                Scene scene1 = new Scene(g, 870, 450);
                popupwindow.setMinWidth(850);
                popupwindow.setMinHeight(400);
                popupwindow.setScene(scene1);
                popupwindow.showAndWait();
            });
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, parkingEventCreation);
            buttonsBox.getChildren().add(1, suggest4);
            grid.add(buttonsBox, 2, 3, 2, 1);
            parkingEventCreation.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 3);
            root.setCenter(grid);
        });

        planExecution.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Set plan execution");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    currentStation.execEvents(selectedFile.getPath());
                } catch (Exception e1) {
                    System.out.println("Plan execution failed");
                }
            }
        });

        //Implements the New PricingPolicy MenuItem
        policy.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select the kind of policy: ");
            ButtonType buttonTypeOne = new ButtonType("Fixed time");
            ButtonType buttonTypeTwo = new ButtonType("Changing time");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            TextField boo;
            Label foo;
            if (result.isPresent()) {
                if (result.get() == buttonTypeOne) {
                    Maintenance.cleanScreen();
                    grid.setMaxSize(450, 180);
                    foo = new Label("Duration*: ");
                    foo.setGraphic(new ImageView(help));
                    foo.setTooltip(new Tooltip("The time duration of each price."));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(foo, 0, 0);
                    boo = new TextField();
                    grid.add(boo, 1, 0);
                    textfields.add(boo);
                    foo = new Label("Prices*: ");
                    foo.setGraphic(new ImageView(help));
                    foo.setTooltip(new Tooltip("The prices of the time spaces. All values need to be separated by commas."));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(foo, 0, 1);
                    boo = new TextField();
                    grid.add(boo, 1, 1);
                    textfields.add(boo);
                    grid.add(new Label("*Required"), 0, 2);
                    Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
                    HBox buttonsBox = EVLibSim.getButtonsBox();
                    buttonsBox.getChildren().removeIf(buttonPredicate);
                    buttonsBox.getChildren().add(0, policyCreation1);
                    grid.add(buttonsBox, 0, 3, 2, 1);
                    policyCreation1.setDefaultButton(true);
                    root.setCenter(grid);
                } else if (result.get() == buttonTypeTwo) {
                    Maintenance.cleanScreen();
                    grid.setMaxSize(450, 180);
                    foo = new Label("Durations*: ");
                    grid.add(foo, 0, 0);
                    boo = new TextField();
                    foo.setTooltip(new Tooltip("The time duration of each price. All values need to be separated by commas."));
                    foo.setGraphic(new ImageView(help));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(boo, 1, 0);
                    textfields.add(boo);
                    foo = new Label("Prices*: ");
                    foo.setTooltip(new Tooltip("The prices of the time spaces. All values need to be separated by commas."));
                    foo.setGraphic(new ImageView(help));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(foo, 0, 1);
                    boo = new TextField();
                    grid.add(boo, 1, 1);
                    textfields.add(boo);
                    grid.add(new Label("*Required"), 0, 2);
                    Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
                    HBox buttonsBox = EVLibSim.getButtonsBox();
                    buttonsBox.getChildren().removeIf(buttonPredicate);
                    buttonsBox.getChildren().add(0, policyCreation2);
                    grid.add(buttonsBox, 0, 3, 2, 1);
                    policyCreation2.setDefaultButton(true);
                    root.setCenter(grid);
                }
            }
        });

        //Buttons
        chargingEventCreation.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Maintenance.positiveOrZero(2, 3, 4))
                    return;
                if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                    alert.showAndWait();
                    return;
                }
                if ((Double.parseDouble(textfields.get(4).getText()) > (Double.parseDouble(textfields.get(2).getText()) - Double.parseDouble(textfields.get(3).getText())))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The asking amount of energy cannot be greater than the remaining capacity.");
                    alert.showAndWait();
                    return;
                }
                ChargingEvent ch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                ch = new ChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()), kindOfCharging);
                ch.setWaitingTime(currentStation.getWaitingTime(kindOfCharging) + 1000);
                ch.preProcessing();
                if (ch.getCondition().equals("ready")) {
                    ch.execution();
                    Maintenance.completionMessage("charging event creation");
                } else if (ch.getCondition().equals("wait"))
                    Maintenance.queueInsertion();
                else
                    Maintenance.noExecution();
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                charging.fire();
            }
        });
        disChargingEventCreation.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Maintenance.positiveOrZero(2, 3, 4))
                    return;
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
                DisChargingEvent dsch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                dsch = new DisChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()));
                dsch.setWaitingTime(currentStation.getWaitingTime("discharging") + 1000);
                dsch.preProcessing();
                if (dsch.getCondition().equals("ready")) {
                    dsch.execution();
                    Maintenance.completionMessage("Discharging event creation");
                } else if (dsch.getCondition().equals("wait"))
                    Maintenance.queueInsertion();
                else
                    Maintenance.noExecution();
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                discharging.fire();
            }
        });
        exchangeEventCreation.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Maintenance.positiveOrZero(2, 3))
                    return;
                if (Double.parseDouble(textfields.get(2).getText()) < Double.parseDouble(textfields.get(3).getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                    alert.showAndWait();
                    return;
                }
                ChargingEvent ch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                ch = new ChargingEvent(currentStation, el);
                ch.setWaitingTime(currentStation.getWaitingTime("exchange") + 1000);
                ch.preProcessing();
                if (ch.getCondition().equals("ready")) {
                    ch.execution();
                    Maintenance.completionMessage("Charging event creation");
                } else if (ch.getCondition().equals("wait"))
                    Maintenance.queueInsertion();
                else
                    Maintenance.noExecution();
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                exchange.fire();
            }
        });
        parkingEventCreation.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Maintenance.positiveOrZero(2, 3, 4, 5))
                    return;
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
                    alert.setContentText("Please select a smaller amount of energy.");
                    alert.showAndWait();
                    return;
                }
                ParkingEvent ch;
                Driver d = new Driver(textfields.get(0).getText());
                Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (!Objects.equals(textfields.get(4).getText(), "0")) {
                    ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()), Double.parseDouble(textfields.get(4).getText()));
                    ch.preProcessing();
                    if (ch.getCondition().equals("ready")) {
                        ch.execution();
                        Maintenance.completionMessage("Parking event creation");
                    } else
                        Maintenance.noExecution();
                } else if (!Objects.equals(textfields.get(5).getText(), "0")) {
                    ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()));
                    ch.preProcessing();
                    if (ch.getCondition().equals("ready")) {
                        ch.execution();
                        Maintenance.completionMessage("Parking event creation");
                    } else
                        Maintenance.noExecution();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select a positive parking time.");
                    alert.showAndWait();
                }
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                parking.fire();
            }
        });
        policyCreation1.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                String text = textfields.get(1).getText().replaceAll("[^0-9,.]+", "");
                textfields.get(1).setText(text);
                String[] prices = textfields.get(1).getText().split(",");
                double[] p = new double[prices.length];
                long timeDuration;
                if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                    timeDuration = (long) Double.parseDouble(textfields.get(0).getText()) * 1000;
                else
                    timeDuration = (long) Double.parseDouble(textfields.get(0).getText()) * 60000;
                for (int i = 0; i < prices.length; i++)
                    p[i] = Double.parseDouble(prices[i]);
                PricingPolicy policy = new PricingPolicy(timeDuration, p);
                currentStation.setPricingPolicy(policy);
                Maintenance.completionMessage("pricing policy creation");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                startScreen.fire();
            }
        });
        policyCreation2.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                String text = textfields.get(0).getText().replaceAll("[^0-9,.]+", "");
                textfields.get(0).setText(text);
                String[] spaces = textfields.get(0).getText().split(",");
                text = textfields.get(1).getText().replaceAll("[^0-9,.]+", "");
                textfields.get(1).setText(text);
                String[] prices = textfields.get(1).getText().split(",");
                if (spaces.length != prices.length) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The number of time spaces and prices have to be equal.");
                    alert.showAndWait();
                    return;
                }
                long[] s = new long[spaces.length];
                double[] p = new double[prices.length];
                for (int i = 0; i < spaces.length; i++) {
                    if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                        s[i] = (long) Double.parseDouble(spaces[i])*1000;
                    else
                        s[i] = (long) Double.parseDouble(spaces[i])*60000;
                    p[i] = Double.parseDouble(prices[i]);
                }
                PricingPolicy policy = new PricingPolicy(s, p);
                currentStation.setPricingPolicy(policy);
                Maintenance.completionMessage("pricing policy creation");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                startScreen.fire();
            }
        });
        event.getItems().addAll(charging, discharging, exchange, parking, new SeparatorMenuItem(), policy, planExecution);
        return event;
    }

    //Returns the ChargingStation objects in a descending order based on their energy inventory.
    private static ChargingStation[] bestEnergy() {
        ChargingStation[] st = new ChargingStation[stations.size()];
        stations.forEach(station -> st[stations.indexOf(station)] = station);
        ChargingStation temp;
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getTotalEnergy() < st[j].getTotalEnergy()) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
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
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getCurrentPrice() > st[j].getCurrentPrice()) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
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
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getDisUnitPrice() > st[j].getDisUnitPrice()) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
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
                if (st[j - 1].getBatteries().length < st[j].getBatteries().length) {
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
        for (int i = 0; i < st.length; i++) {
            for (int j = 1; j < (st.length - i); j++) {
                if (st[j - 1].getWaitingTime(kind) > st[j].getWaitingTime(kind)) {
                    temp = st[j - 1];
                    st[j - 1] = st[j];
                    st[j] = temp;
                }
            }
        }
        return st;
    }
}