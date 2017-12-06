package evlibsim;

import evlib.ev.Battery;
import evlib.ev.Driver;
import evlib.ev.ElectricVehicle;
import evlib.station.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private static final MenuItem planExecution = new MenuItem("Plan execution");
    private static final Button chargingEventCreation = new Button("Creation");
    private static final Button disChargingEventCreation = new Button("Creation");
    private static final Button parkingEventCreation = new Button("Creation");
    private static final Button exchangeEventCreation = new Button("Creation");
    private static String kindOfCharging;
    private static final Image help = new Image("/help.png");
    //private static ObservableList<ChargingEvent> conditions = FXCollections.observableArrayList();

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
            foo.setTooltip(new Tooltip("The remaining energy in the battery before the charging."));
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
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                else
                    tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy() / 1000)));
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
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getCurrentPrice())));
                else
                    tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getCurrentPrice() * 1000)));
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
                if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("slow") / 1000)));
                else
                    tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("slow") / 60000)));
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
                if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside7.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("fast") / 1000)));
                else
                    tempStations.forEach(temp -> inside7.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("fast") / 60000)));
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
            foo.setTooltip(new Tooltip("The remaining energy in the battery before the discharging."));
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
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                else
                    tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy() / 1000)));
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
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getDisUnitPrice())));
                else
                    tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getDisUnitPrice() * 1000)));
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
                if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("discharging") / 1000)));
                else
                    tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("discharging") / 60000)));
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
            foo.setTooltip(new Tooltip("The remaining energy in the battery to be exchanged."));
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
                if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("exchange") / 1000)));
                else
                    tempStations.forEach(temp -> inside5.getChildren().add(new Label(temp.getName() + ": " + temp.getWaitingTime("exchange") / 60000)));
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
            foo.setTooltip(new Tooltip("The remaining energy in the battery before the parking/charging."));
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
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy())));
                else
                    tempStations.forEach(temp -> inside1.getChildren().add(new Label(temp.getName() + ": " + temp.getTotalEnergy() / 1000)));
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
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getInductivePrice())));
                else
                    tempStations.forEach(temp -> inside3.getChildren().add(new Label(temp.getName() + ": " + temp.getInductivePrice() * 1000)));
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
                if (Double.parseDouble(textfields.get(4).getText()) <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The asking amount of energy has to be greater than zero.");
                    alert.showAndWait();
                    return;
                }
                ChargingEvent ch;
                Battery b;
                Driver d = new Driver(textfields.get(0).getText());
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                else
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()) * 1000, Double.parseDouble(textfields.get(2).getText()) * 1000);
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    ch = new ChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()), kindOfCharging);
                else
                    ch = new ChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()) * 1000, kindOfCharging);
                ch.setWaitingTime(currentStation.getWaitingTime(kindOfCharging) + 1000);
                ch.preProcessing();
                if (ch.getCondition().equals("ready")) {
                    ch.execution();
                    Maintenance.completionMessage("creation of the charging event");
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
                if (Double.parseDouble(textfields.get(4).getText()) <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The given amount of energy has to be greater than zero.");
                    alert.showAndWait();
                    return;
                }
                DisChargingEvent dsch;
                Battery b;
                Driver d = new Driver(textfields.get(0).getText());
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                else
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()) * 1000, Double.parseDouble(textfields.get(2).getText()) * 1000);
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    dsch = new DisChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()));
                else
                    dsch = new DisChargingEvent(currentStation, el, Double.parseDouble(textfields.get(4).getText()) * 1000);
                dsch.setWaitingTime(currentStation.getWaitingTime("discharging") + 1000);
                dsch.preProcessing();
                if (dsch.getCondition().equals("ready")) {
                    dsch.execution();
                    Maintenance.completionMessage("creation of the discharging event");
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
                Battery b;
                Driver d = new Driver(textfields.get(0).getText());
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                else
                    b = new Battery(Double.parseDouble(textfields.get(3).getText())* 1000, Double.parseDouble(textfields.get(2).getText()) * 1000);
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                ch = new ChargingEvent(currentStation, el);
                ch.setWaitingTime(currentStation.getWaitingTime("exchange") + 1000);
                ch.preProcessing();
                if (ch.getCondition().equals("ready")) {
                    ch.execution();
                    Maintenance.completionMessage("creation of the charging event");
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
            for (TextField f : textfields) {
                if (textfields.indexOf(f) == 4)
                    continue;
                if (f.getText().isEmpty() && !f.isDisabled()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all the required fields.");
                    alert.showAndWait();
                    return;
                }
            }
            try {
                if (Maintenance.positiveOrZero(2, 3, 5))
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
                Battery b;
                Driver d = new Driver(textfields.get(0).getText());
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(2).getText()));
                else
                    b = new Battery(Double.parseDouble(textfields.get(3).getText()) * 1000, Double.parseDouble(textfields.get(2).getText()) * 1000);
                ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText());
                el.setBattery(b);
                el.setDriver(d);
                if (!textfields.get(4).getText().isEmpty()) {
                    if ((energyUnit.getSelectionModel().getSelectedIndex() == 0) && (timeUnit.getSelectionModel().getSelectedIndex() == 0))
                        ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()) * 1000, Double.parseDouble(textfields.get(4).getText()));
                    else if ((energyUnit.getSelectionModel().getSelectedIndex() == 0) && (timeUnit.getSelectionModel().getSelectedIndex() == 1))
                        ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()) * 60000, Double.parseDouble(textfields.get(4).getText()));
                    else if ((energyUnit.getSelectionModel().getSelectedIndex() == 1) && (timeUnit.getSelectionModel().getSelectedIndex() == 0))
                        ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()) * 1000, Double.parseDouble(textfields.get(4).getText()) * 1000);
                    else
                        ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()) * 60000, Double.parseDouble(textfields.get(4).getText()) * 1000);
                    ch.preProcessing();
                    if (ch.getCondition().equals("ready")) {
                        ch.execution();
                        Maintenance.completionMessage("creation of the parking event");
                    } else
                        Maintenance.noExecution();
                } else if (!Objects.equals(textfields.get(5).getText(), "0")) {
                    if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                        ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()) * 1000);
                    else
                        ch = new ParkingEvent(currentStation, el, (long) Double.parseDouble(textfields.get(5).getText()) * 60000);
                    ch.preProcessing();
                    if (ch.getCondition().equals("ready")) {
                        ch.execution();
                        Maintenance.completionMessage("creation of the parking event");
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
        event.getItems().addAll(charging, discharging, exchange, parking, new SeparatorMenuItem(), planExecution);
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