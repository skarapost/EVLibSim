package evlibsim;

import evlib.ev.Battery;
import evlib.sources.*;
import evlib.station.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.function.Predicate;

import static evlibsim.EVLibSim.*;

class MenuStation {

    static final MenuItem newChargingStationMI = new MenuItem("New station");
    private static final Menu stationM = new Menu("Station");
    private static final Menu chargersM = new Menu("Charger");
    private static final MenuItem newChargerMI = new MenuItem("New charger");
    private static final Menu disChargersM = new Menu("Discharger");
    static final MenuItem allChargersMI = new MenuItem("All chargers");
    private static final MenuItem newDisChargerMI = new MenuItem("New discharger");
    static final MenuItem allDisChargersMI = new MenuItem("All dischargers");
    private static final Menu exchangeHandlersM = new Menu("Exchange handler");
    private static final MenuItem newExchangeHandlerMI = new MenuItem("New exchange handler");
    static final MenuItem allExchangeHandlersMI = new MenuItem("All exchange handlers");
    private static final Menu parkingSlotsM = new Menu("Parking slot");
    private static final MenuItem newParkingSlotMI = new MenuItem("New parking slot");
    static final MenuItem allParkingSlotsMI = new MenuItem("All parking slots");
    static final MenuItem modifyChargingStationMI = new MenuItem("Modify station");
    private static final MenuItem newBatteryMI = new MenuItem("New battery");
    static final MenuItem allBatteriesMI = new MenuItem("Batteries");
    private static final MenuItem batteriesChargingMI = new MenuItem("Batteries charging");
    static final MenuItem policy = new MenuItem("New charging pricing policy");
    private static final Button chargingStationCreationB = new Button("Creation");
    private static final Button chargerCreationB = new Button("Creation");
    private static final Button modifyStationB = new Button("Save changes");
    private static final Button batteryCreationB = new Button("Creation");
    private static final Button policyCreation1 = new Button("Creation");
    private static final Button policyCreation2 = new Button("Creation");
    private static final Image image = new Image("/d.png");
    private static final Image help = new Image("/help.png");

    private static final ChoiceBox<String> cb1 = new ChoiceBox<>();
    private static final ChoiceBox<String> cb2 = new ChoiceBox<>();
    private static final ChoiceBox<String> cb3 = new ChoiceBox<>();
    private static final ChoiceBox<String> cb4 = new ChoiceBox<>();
    private static final ChoiceBox<String> update = new ChoiceBox<>();
    private static final ChoiceBox<String> handling = new ChoiceBox<>();
    private static final ChoiceBox<String> kindOfCharging = new ChoiceBox<>();

    private static final Label fooboo = new Label();

    static Menu createStationMenu() {
        chargersM.getItems().addAll(newChargerMI, allChargersMI);
        disChargersM.getItems().addAll(newDisChargerMI, allDisChargersMI);
        exchangeHandlersM.getItems().addAll(newExchangeHandlerMI, allExchangeHandlersMI);
        parkingSlotsM.getItems().addAll(newParkingSlotMI, allParkingSlotsMI);
        stationM.getItems().addAll(newChargingStationMI, modifyChargingStationMI, new SeparatorMenuItem(), chargersM, disChargersM,
                exchangeHandlersM, parkingSlotsM, new SeparatorMenuItem(), policy,
                new SeparatorMenuItem(), newBatteryMI, batteriesChargingMI, allBatteriesMI);

        update.setItems(FXCollections.observableArrayList("Direct", "Scheduled"));
        handling.setItems(FXCollections.observableArrayList("Automatic", "Manual"));

        update.getSelectionModel().selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            if (textfields.size() == 12) {
                if (newValue.equals("Scheduled")) {
                    fooboo.setText("Update space*: ");
                    textfields.get(10).setDisable(false);
                } else {
                    fooboo.setText("Update space: ");
                    textfields.get(10).setDisable(true);
                    textfields.get(10).setText("");
                }
            } else {
                if (newValue.equals("Scheduled")) {
                    fooboo.setText("Update space*: ");
                    textfields.get(14).setDisable(false);
                } else {
                    fooboo.setText("Update space: ");
                    textfields.get(14).setDisable(true);
                    textfields.get(14).setText("");
                }
            }
        });

        //Implements the New ChargingStation MenuItem
        newChargingStationMI.setOnAction((ActionEvent e) -> {
            Maintenance.cleanScreen();
            energyUnit.setDisable(true);
            timeUnit.setDisable(true);
            EVLibSim.grid.setMaxSize(750, 600);
            TextField boo;
            Label foo;
            foo = new Label("Name*: ");
            foo.setTooltip(new Tooltip("The name of the charging station."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 0);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Fast chargers*: ");
            foo.setTooltip(new Tooltip("Number of chargers that operate with fast rate."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 0);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Slow chargers*: ");
            foo.setTooltip(new Tooltip("Number of chargers that operate with slow rate."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Exchange handlers*: ");
            foo.setTooltip(new Tooltip("Number of handlers that implement the battery exchange function."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Dischargers*: ");
            foo.setTooltip(new Tooltip("Number of dischargers in the station."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Parking slots*: ");
            foo.setTooltip(new Tooltip("Number of parking slots."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Energy sources*: ");
            foo.setTooltip(new Tooltip("The kind of energy sources that provide energy to the station. "));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 3);
            CheckBox chb1 = new CheckBox("Solar");
            CustomMenuItem item1 = new CustomMenuItem(chb1);
            chb1.setOnAction(d -> {
                if (chb1.isSelected())
                    energies.add("Solar");
                else
                    energies.remove("Solar");
            });
            CheckBox chb2 = new CheckBox("Wind");
            CustomMenuItem item2 = new CustomMenuItem(chb2);
            chb2.setOnAction(d -> {
                if (chb2.isSelected())
                    energies.add("Wind");
                else
                    energies.remove("Wind");
            });
            CheckBox chb3 = new CheckBox("Nonrenewable");
            CustomMenuItem item3 = new CustomMenuItem(chb3);
            chb3.setOnAction(d -> {
                if (chb3.isSelected())
                    energies.add("Nonrenewable");
                else
                    energies.remove("Nonrenewable");
            });
            CheckBox chb4 = new CheckBox("Wave");
            CustomMenuItem item4 = new CustomMenuItem(chb4);
            chb4.setOnAction(d -> {
                if (chb4.isSelected())
                    energies.add("Wave");
                else
                    energies.remove("Wave");
            });
            CheckBox chb5 = new CheckBox("Geothermal");
            CustomMenuItem item5 = new CustomMenuItem(chb5);
            chb5.setOnAction(d -> {
                if (chb5.isSelected())
                    energies.add("Geothermal");
                else
                    energies.remove("Geothermal");
            });
            CheckBox chb6 = new CheckBox("Hydroelectric");
            CustomMenuItem item6 = new CustomMenuItem(chb6);
            chb6.setOnAction(d -> {
                if (chb6.isSelected())
                    energies.add("Hydroelectric");
                else
                    energies.remove("Hydroelectric");
            });
            MenuButton menuButton = new MenuButton("Choices");
            menuButton.setMaxWidth(150);
            menuButton.getItems().setAll(item1, item2, item3, item4, item5, item6);
            item1.setHideOnClick(false);
            item2.setHideOnClick(false);
            item3.setHideOnClick(false);
            item4.setHideOnClick(false);
            item5.setHideOnClick(false);
            item6.setHideOnClick(false);
            EVLibSim.grid.add(menuButton, 1, 3);
            foo = new Label("Charging fee*: ");
            foo.setTooltip(new Tooltip("The price of each energy unit for the charging function. The energy metering unit is defined by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 3);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Discharging fee*: ");
            foo.setTooltip(new Tooltip("The price of each energy unit for the discharging function. The energy metering unit is defined by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 4);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Battery exchange fee*: ");
            foo.setTooltip(new Tooltip("The price for each battery swapping function."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 4);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Inductive charging fee*: ");
            foo.setTooltip(new Tooltip("The price of each energy unit for the inductive charging function. The energy metering unit is defined by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 5);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Fast charging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a fast charger charges a vehicle. It is defined as the amount of energy units per time unit, a vehicle receives."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 5);
            if ((energyUnit.getSelectionModel().getSelectedIndex() == 0) && (timeUnit.getSelectionModel().getSelectedIndex() == 0)) {
                cb1.setItems(FXCollections.observableArrayList("53.3 W/s", "13.8 W/s", "12 W/s"));
                EVLibSim.grid.add(cb1, 3, 5);
                cb2.setItems(FXCollections.observableArrayList("6.1 W/s", "3 W/s", "1.9 W/s", "0.8 W/s"));
                EVLibSim.grid.add(cb2, 1, 6);
                cb3.setItems(FXCollections.observableArrayList("13.8 W/s", "6.1 W/s", "1.9 W/s"));
                EVLibSim.grid.add(cb3, 3, 6);
                cb4.setItems(FXCollections.observableArrayList("1.9 W/s", "1 W/s"));
                EVLibSim.grid.add(cb4, 1, 7);
            } else if ((energyUnit.getSelectionModel().getSelectedIndex() == 0) && (timeUnit.getSelectionModel().getSelectedIndex() == 1)) {
                cb1.setItems(FXCollections.observableArrayList("2000 W/m", "833.3 W/m", "716.6 W/m"));
                EVLibSim.grid.add(cb1, 3, 5);
                cb2.setItems(FXCollections.observableArrayList("366.6 W/m", "183.3 W/m", "116.6 W/m", "50 W/m"));
                EVLibSim.grid.add(cb2, 1, 6);
                cb3.setItems(FXCollections.observableArrayList("833.3 W/m", "366.6 W/m", "116.6 W/m"));
                EVLibSim.grid.add(cb3, 3, 6);
                cb4.setItems(FXCollections.observableArrayList("116.6 W/m", "60 W/m"));
                EVLibSim.grid.add(cb4, 1, 7);
            } else if ((energyUnit.getSelectionModel().getSelectedIndex() == 1) && (timeUnit.getSelectionModel().getSelectedIndex() == 0)) {
                cb1.setItems(FXCollections.observableArrayList("0.03 kW/s", "0.013 kW/s", "0.011 kW/s"));
                EVLibSim.grid.add(cb1, 3, 5);
                cb2.setItems(FXCollections.observableArrayList("0.006 kW/s", "0.003 kW/s", "0.002 kW/s", "0.0008 kW/s"));
                EVLibSim.grid.add(cb2, 1, 6);
                cb3.setItems(FXCollections.observableArrayList("0.013 kW/s", "0.006 kW/s", "0.002 kW/s"));
                EVLibSim.grid.add(cb3, 3, 6);
                cb4.setItems(FXCollections.observableArrayList("0.002 kW/s", "0.001 kW/s"));
                EVLibSim.grid.add(cb4, 1, 7);
            } else {
                cb1.setItems(FXCollections.observableArrayList("2 kW/m", "0.833 kW/m", "0.716 kW/m"));
                EVLibSim.grid.add(cb1, 3, 5);
                cb2.setItems(FXCollections.observableArrayList("0.366 kW/m", "0.183 kW/m", "0.116 kW/m", "0.05 kW/m"));
                EVLibSim.grid.add(cb2, 1, 6);
                cb3.setItems(FXCollections.observableArrayList("0.833 kW/m", "0.366 kW/m", "0.116 kW/m"));
                EVLibSim.grid.add(cb3, 3, 6);
                cb4.setItems(FXCollections.observableArrayList("0.116 kW/m", "0.06 kW/m"));
                EVLibSim.grid.add(cb4, 1, 7);
            }
            cb1.getSelectionModel().selectFirst();
            cb2.getSelectionModel().selectFirst();
            cb3.getSelectionModel().selectFirst();
            cb4.getSelectionModel().selectFirst();
            foo = new Label("Slow charging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a slow charger charges a vehicle. It is defined as the amount of energy units per time unit, a vehicle receives."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 6);
            foo = new Label("Discharging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a discharger discharges a vehicle. It is defined as the amount of energy units per time unit, the vehicle gives."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 6);
            foo = new Label("Inductive charging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a parking slot charges a vehicle inductively. It is defined as the amount of energy units per time unit, the vehicle receives. The charging during parking is inductive."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 7);
            foo = new Label("Energy storage update*: ");
            foo.setTooltip(new Tooltip("The way in which each energy storage update is implemented."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 7);
            EVLibSim.grid.add(update, 3, 7);
            foo = new Label("Queue handling*: ");
            foo.setTooltip(new Tooltip("The way in which the waiting list is manipulated. If the handling is automatic the events of the list are executed automatically, otherwise should be executed by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 8);
            handling.getSelectionModel().selectFirst();
            EVLibSim.grid.add(handling, 1, 8);
            fooboo.setText("Update space: ");
            fooboo.setTooltip(new Tooltip("In case of scheduled energy storage update, it defines the time space between each update."));
            fooboo.setGraphic(new ImageView(help));
            fooboo.getTooltip().setPrefWidth(200);
            fooboo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(fooboo, 2, 8);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 8);
            textfields.add(boo);
            boo.setDisable(true);
            foo = new Label("Battery exchange duration*: ");
            foo.setTooltip(new Tooltip("The time duration of a battery exchange function."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 9);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 9);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, chargingStationCreationB);
            grid.add(buttonsBox, 2, 10, 2, 1);
            chargingStationCreationB.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 10);
            EVLibSim.root.setCenter(EVLibSim.grid);
            update.getSelectionModel().selectFirst();
        });

        //Implements the New Charger MenuItem
        newChargerMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(300, 300);
            TextField boo;
            Label foo;
            foo = new Label("Kind*: ");
            EVLibSim.grid.add(foo, 0, 0);
            kindOfCharging.setItems(FXCollections.observableArrayList("Fast", "Slow"));
            kindOfCharging.getSelectionModel().selectFirst();
            grid.add(kindOfCharging, 1, 0);
            foo = new Label("Name: ");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, chargerCreationB);
            grid.add(buttonsBox, 0, 3, 2, 1);
            chargerCreationB.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 2);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });

        //Implements the New DisCharge MenuItem.
        newDisChargerMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            TextInputDialog alert = new TextInputDialog();
            alert.setTitle("Name for discharger");
            alert.setHeaderText(null);
            alert.setContentText("Optional name: ");
            Optional<String> result = alert.showAndWait();
            result.ifPresent(s -> {
                DisCharger ch;
                ch = new DisCharger(currentStation);
                if (!result.get().equals(""))
                    ch.setName(s);
                currentStation.addDisCharger(ch);
                Maintenance.completionMessage("creation of the discharger");
            });
        });

        //Implements the New ExchangeHandler MenuItem.
        newExchangeHandlerMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            TextInputDialog alert = new TextInputDialog();
            alert.setTitle("Name for exchange handler");
            alert.setHeaderText(null);
            alert.setContentText("Optional name: ");
            Optional<String> result = alert.showAndWait();
            result.ifPresent(s -> {
                ExchangeHandler ch;
                ch = new ExchangeHandler(currentStation);
                if (!result.get().equals(""))
                    ch.setName(s);
                currentStation.addExchangeHandler(ch);
                Maintenance.completionMessage("creation of the exchange handler");
            });
        });

        //Implements the New ParkingSlot MenuItem.
        newParkingSlotMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            TextInputDialog alert = new TextInputDialog();
            alert.setTitle("Name for parking slot");
            alert.setHeaderText(null);
            alert.setContentText("Optional name: ");
            Optional<String> result = alert.showAndWait();
            result.ifPresent(s -> {
                ParkingSlot ch;
                ch = new ParkingSlot(currentStation);
                if (!result.get().equals(""))
                    ch.setName(s);
                currentStation.addParkingSlot(ch);
                Maintenance.completionMessage("creation of the parking slot");
            });
        });

        //Implements the Modify ChargingStation.
        modifyChargingStationMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            energyUnit.setDisable(true);
            timeUnit.setDisable(true);
            EVLibSim.grid.setMaxSize(750, 600);
            EVLibSim.energies.clear();
            TextField boo;
            Label foo;
            foo = new Label("Name*: ");
            foo.setTooltip(new Tooltip("Name of the charging station."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 0);
            boo = new TextField(currentStation.getName());
            EVLibSim.grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Fast chargers*: ");
            foo.setTooltip(new Tooltip("Number of chargers that operate with fast rate."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 0);
            boo = new TextField(String.valueOf(currentStation.FAST_CHARGERS));
            EVLibSim.grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Slow chargers*: ");
            foo.setTooltip(new Tooltip("Number of chargers that operate with slow rate."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField(String.valueOf(currentStation.SLOW_CHARGERS));
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Exchange handlers*: ");
            foo.setTooltip(new Tooltip("Number of handlers that implement the battery exchange function."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField(String.valueOf(currentStation.getExchangeHandlers().length));
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Dischargers*: ");
            foo.setTooltip(new Tooltip("Number of dischargers in the station."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField(String.valueOf(currentStation.getDisChargers().length));
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Parking slots*: ");
            foo.setTooltip(new Tooltip("Number of parking slots."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 2);
            boo = new TextField(String.valueOf(currentStation.getParkingSlots().length));
            EVLibSim.grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Energy sources*: ");
            foo.setTooltip(new Tooltip("The kind of energy sources that provide energy to the station. "));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 3);
            CheckBox chb1 = new CheckBox("Solar");
            CustomMenuItem item1 = new CustomMenuItem(chb1);
            energies.addAll(Arrays.asList(currentStation.getSources()));
            if (Arrays.asList(currentStation.getSources()).contains("Solar"))
                chb1.setSelected(true);
            chb1.setOnAction(d -> {
                if (chb1.isSelected())
                    energies.add("Solar");
                else
                    energies.remove("Solar");
            });
            CheckBox chb2 = new CheckBox("Wind");
            CustomMenuItem item2 = new CustomMenuItem(chb2);
            if (Arrays.asList(currentStation.getSources()).contains("Wind"))
                chb2.setSelected(true);
            chb2.setOnAction(d -> {
                if (chb2.isSelected())
                    energies.add("Wind");
                else
                    energies.remove("Wind");
            });
            CheckBox chb3 = new CheckBox("Nonrenewable");
            CustomMenuItem item3 = new CustomMenuItem(chb3);
            if (Arrays.asList(currentStation.getSources()).contains("Nonrenewable"))
                chb3.setSelected(true);
            chb3.setOnAction(d -> {
                if (chb3.isSelected())
                    energies.add("Nonrenewable");
                else
                    energies.remove("Nonrenewable");
            });
            CheckBox chb4 = new CheckBox("Wave");
            CustomMenuItem item4 = new CustomMenuItem(chb4);
            if (Arrays.asList(currentStation.getSources()).contains("Wave"))
                chb4.setSelected(true);
            chb4.setOnAction(d -> {
                if (chb4.isSelected())
                    energies.add("Wave");
                else
                    energies.remove("Wave");
            });
            CheckBox chb5 = new CheckBox("Geothermal");
            CustomMenuItem item5 = new CustomMenuItem(chb5);
            if (Arrays.asList(currentStation.getSources()).contains("Geothermal"))
                chb5.setSelected(true);
            chb5.setOnAction(d -> {
                if (chb5.isSelected())
                    energies.add("Geothermal");
                else
                    energies.remove("Geothermal");
            });
            CheckBox chb6 = new CheckBox("Hydroelectric");
            CustomMenuItem item6 = new CustomMenuItem(chb6);
            if (Arrays.asList(currentStation.getSources()).contains("Hydroelectric"))
                chb6.setSelected(true);
            chb6.setOnAction(d -> {
                if (chb6.isSelected())
                    energies.add("Hydroelectric");
                else
                    energies.remove("Hydroelectric");
            });
            MenuButton menuButton = new MenuButton("Choices");
            menuButton.setMaxWidth(150);
            menuButton.getItems().setAll(item1, item2, item3, item4, item5, item6);
            item1.setHideOnClick(false);
            item2.setHideOnClick(false);
            item3.setHideOnClick(false);
            item4.setHideOnClick(false);
            item5.setHideOnClick(false);
            item6.setHideOnClick(false);
            EVLibSim.grid.add(menuButton, 1, 3);
            foo = new Label("Charging fee*: ");
            foo.setTooltip(new Tooltip("The price of each energy unit for the charging function. The energy metering unit is defined by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 3);
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getUnitPrice()));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getUnitPrice() * 1000));
            EVLibSim.grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Discharging fee*: ");
            foo.setTooltip(new Tooltip("The price of each energy unit for the discharging function. The energy metering unit is defined by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 4);
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisUnitPrice()));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisUnitPrice() * 1000));
            EVLibSim.grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Battery exchange fee*: ");
            foo.setTooltip(new Tooltip("The price of each battery swapping function."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 4);
            boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getExchangePrice()));
            EVLibSim.grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Inductive charging fee*: ");
            foo.setTooltip(new Tooltip("The price of each energy unit for the inductive charging function. The energy metering unit is defined by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 5);
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductivePrice()));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductivePrice() * 1000));
            EVLibSim.grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Fast charging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a fast charger charges a vehicle. It is defined as the amount of energy units per time unit, a vehicle receives."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 5);
            if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateFast() * 1000));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 1))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateFast()));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 1)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateFast() * 60000));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateFast() * 60));
            EVLibSim.grid.add(boo, 3, 5);
            textfields.add(boo);
            foo = new Label("Slow charging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a slow charger charges a vehicle. It is defined as the amount of energy units per time unit, a vehicle gives."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 6);
            if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateSlow() * 1000));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 1))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateSlow()));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 1)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateSlow() * 60000));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getChargingRateSlow() * 60));
            EVLibSim.grid.add(boo, 1, 6);
            textfields.add(boo);
            foo = new Label("Discharging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a discharger discharges a vehicle. It is defined as the amount of energy units per time unit the vehicle provides."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 6);
            if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisChargingRate() * 1000));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 1))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisChargingRate()));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 1)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisChargingRate() * 60000));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisChargingRate() * 60));
            EVLibSim.grid.add(boo, 3, 6);
            textfields.add(boo);
            foo = new Label("Inductive charging rate*: ");
            foo.setTooltip(new Tooltip("The rate in which a parking slot charges a vehicle inductively. It is defined as the amount of energy units per time unit, the vehicle receives. The charging during parking is inductive."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 7);
            if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductiveRate() * 1000));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)&&(energyUnit.getSelectionModel().getSelectedIndex() == 1))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductiveRate()));
            else if ((EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 1)&&(energyUnit.getSelectionModel().getSelectedIndex() == 0))
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductiveRate() * 60000));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductiveRate() * 60));
            EVLibSim.grid.add(boo, 1, 7);
            textfields.add(boo);
            foo = new Label("Energy storage update*: ");
            foo.setTooltip(new Tooltip("The way in which the energy storage update is implemented. When a user adds energy to the station, direct means the energy amounts are transfered immediately. In the opposite, scheduled denotes that each set of energy amounts will be transferred at a scheduled time moment."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 2, 7);
            EVLibSim.grid.add(update, 3, 7);
            foo = new Label("Queue handling*: ");
            foo.setTooltip(new Tooltip("The way the waiting list is manipulated. If the handling is automatic the events of the list are executed automatically, otherwise should be executed by the user."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 8);
            if (currentStation.getQueueHandling()) {
                handling.getSelectionModel().selectFirst();
            } else {
                handling.getSelectionModel().selectLast();
            }
            EVLibSim.grid.add(handling, 1, 8);
            if (currentStation.getUpdateMode())
                fooboo.setText("Update space*: ");
            else
                fooboo.setText("Update space: ");
            fooboo.setTooltip(new Tooltip("In case of scheduled energy storage update, it defines the time space between each update."));
            fooboo.setGraphic(new ImageView(help));
            fooboo.getTooltip().setPrefWidth(200);
            fooboo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(fooboo, 2, 8);

            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getUpdateSpace() / 1000));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getUpdateSpace() / 60000));
            if (!currentStation.getUpdateMode()) {
                boo.setDisable(true);
                boo.setText("");
            }
            EVLibSim.grid.add(boo, 3, 8);
            textfields.add(boo);

            foo = new Label("Battery exchange duration*: ");
            foo.setTooltip(new Tooltip("The time duration of a battery exchange function."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 9);
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getTimeOfExchange() / 1000));
            else
                boo = new TextField(new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getTimeOfExchange() / 60000));
            EVLibSim.grid.add(boo, 1, 9);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, modifyStationB);
            grid.add(buttonsBox, 2, 10, 2, 1);
            modifyStationB.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 10);
            EVLibSim.root.setCenter(EVLibSim.grid);
            if (!currentStation.getUpdateMode())
                update.getSelectionModel().selectFirst();
            else
                update.getSelectionModel().selectLast();
        });

        //Implements the All Chargers MenuItem
        allChargersMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "allChargers";
            ObservableList<Charger> result = FXCollections.observableArrayList();
            result.addAll(Arrays.asList(currentStation.getChargers()));
            TableView<Charger> table = new TableView<>();
            table.setMaxSize(700, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn deleteCol = new TableColumn("Delete");
            deleteCol.setEditable(false);
            deleteCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Charger, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));
            deleteCol.setCellFactory((Callback<TableColumn<Charger, Boolean>, TableCell<Charger, Boolean>>) param -> {
                ButtonCell btn = new ButtonCell(table);
                btn.chargerOnAction();
                return btn;
            });
            TableColumn<Charger, Integer> idCol = new TableColumn<>("Id");
            TableColumn<Charger, String> nameCol = new TableColumn<>("Name");
            TableColumn<Charger, String> kindCol = new TableColumn<>("KindOfCharging");
            TableColumn<Charger, Boolean> occupiedCol = new TableColumn<>("Occupied");
            table.getColumns().addAll(idCol, nameCol, kindCol, occupiedCol, deleteCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            occupiedCol.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().getChargingEvent() != null));
            table.setItems(result);
            root.setCenter(table);
        });

        //Implements the All DisChargers MenuItem.
        allDisChargersMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "allDisChargers";
            ObservableList<DisCharger> result = FXCollections.observableArrayList();
            result.addAll(Arrays.asList(currentStation.getDisChargers()));
            TableView<DisCharger> table = new TableView<>();
            table.setMaxSize(700, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn deleteCol = new TableColumn("Delete");
            deleteCol.setEditable(false);
            deleteCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<DisCharger, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));
            deleteCol.setCellFactory((Callback<TableColumn<DisCharger, Boolean>, TableCell<DisCharger, Boolean>>) param -> {
                ButtonCell btn = new ButtonCell(table);
                btn.dischargerOnAction();
                return btn;
            });
            TableColumn<DisCharger, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisCharger, String> nameCol = new TableColumn<>("Name");
            TableColumn<DisCharger, Boolean> occupiedCol = new TableColumn<>("Occupied");
            table.getColumns().addAll(idCol, nameCol, occupiedCol, deleteCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            occupiedCol.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().getDisChargingEvent() != null));
            table.setItems(result);
            root.setCenter(table);
        });

        //Implements the All ExchangeHandlers MenuItem.
        allExchangeHandlersMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "allExchangeHandlers";
            ObservableList<ExchangeHandler> result = FXCollections.observableArrayList();
            result.addAll(Arrays.asList(currentStation.getExchangeHandlers()));
            TableView<ExchangeHandler> table = new TableView<>();
            table.setMaxSize(700, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn deleteCol = new TableColumn("Delete");
            deleteCol.setEditable(false);
            deleteCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ExchangeHandler, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));
            deleteCol.setCellFactory((Callback<TableColumn<ExchangeHandler, Boolean>, TableCell<ExchangeHandler, Boolean>>) param -> {
                ButtonCell btn = new ButtonCell(table);
                btn.exchangeHandlerOnAction();
                return btn;
            });
            TableColumn<ExchangeHandler, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ExchangeHandler, String> nameCol = new TableColumn<>("Name");
            TableColumn<ExchangeHandler, Boolean> occupiedCol = new TableColumn<>("Occupied");
            table.getColumns().addAll(idCol, nameCol, occupiedCol, deleteCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            occupiedCol.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().getChargingEvent() != null));
            table.setItems(result);
            root.setCenter(table);
        });

        //Implements the All ParkingSlots MenuItem.
        allParkingSlotsMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "allParkingSlots";
            ObservableList<ParkingSlot> result = FXCollections.observableArrayList();
            result.addAll(Arrays.asList(currentStation.getParkingSlots()));
            TableView<ParkingSlot> table = new TableView<>();
            table.setMaxSize(700, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn deleteCol = new TableColumn("Delete");
            deleteCol.setEditable(false);
            deleteCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ParkingSlot, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));
            deleteCol.setCellFactory((Callback<TableColumn<ParkingSlot, Boolean>, TableCell<ParkingSlot, Boolean>>) param -> {
                ButtonCell btn = new ButtonCell(table);
                btn.parkingSlotOnAction();
                return btn;
            });
            TableColumn<ParkingSlot, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingSlot, String> nameCol = new TableColumn<>("Name");
            TableColumn<ParkingSlot, Boolean> occupiedCol = new TableColumn<>("Occupied");
            table.getColumns().addAll(idCol, nameCol, occupiedCol, deleteCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            occupiedCol.setCellValueFactory(p -> new SimpleBooleanProperty(p.getValue().getParkingEvent() != null));
            table.setItems(result);
            root.setCenter(table);
        });

        //Implements the New Battery MenuItem.
        newBatteryMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(300, 250);
            TextField boo;
            Label foo;
            foo = new Label("Battery capacity*: ");
            foo.setTooltip(new Tooltip("The capacity of the battery."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 0);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Battery remaining*: ");
            foo.setTooltip(new Tooltip("The remaining energy in the battery."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Maximum chargings*: ");
            foo.setTooltip(new Tooltip("The maximum number of full chargings the battery stands."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, batteryCreationB);
            grid.add(buttonsBox, 0, 4, 2, 1);
            batteryCreationB.setDefaultButton(true);
            grid.add(new Label("*Required"), 0, 3, 2, 1);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });

        //Implements the All Batteries MenuItem.
        allBatteriesMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "allBatteries";
            ObservableList<Battery> result = FXCollections.observableArrayList();
            result.addAll(Arrays.asList(currentStation.getBatteries()));
            TableView<Battery> table = new TableView<>();
            table.setMaxSize(700, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            TableColumn deleteCol = new TableColumn("Delete");
            deleteCol.setEditable(false);
            deleteCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Battery, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));
            deleteCol.setCellFactory((Callback<TableColumn<Battery, Boolean>, TableCell<Battery, Boolean>>) param -> {
                ButtonCell btn = new ButtonCell(table);
                btn.batteriesOnAction();
                return btn;
            });
            TableColumn<Battery, Integer> idCol = new TableColumn<>("Id");
            TableColumn<Battery, Number> capacityCol = new TableColumn<>("Capacity");
            TableColumn<Battery, Number> remainingAmountCol = new TableColumn<>("RemAmount");
            TableColumn<Battery, Integer> numberOfChargingsCol = new TableColumn<>("Chargings");
            TableColumn<Battery, Integer> maxNumberOfChargings = new TableColumn<>("MaximumChargings");
            table.getColumns().addAll(idCol, capacityCol, remainingAmountCol, numberOfChargingsCol, maxNumberOfChargings, deleteCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0) {
                capacityCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getCapacity()));
                remainingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getRemAmount()));
            }
            else {
                capacityCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getCapacity() / 1000));
                remainingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getRemAmount() / 1000));
            }
            numberOfChargingsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfChargings"));
            maxNumberOfChargings.setCellValueFactory(new PropertyValueFactory<>("maxNumberOfChargings"));
            table.setItems(result);
            root.setCenter(table);
        });

        //Implements the charging of batteries that are for battery exchange
        batteriesChargingMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            String[] choices = {"Fast", "Slow"};
            ChoiceDialog<String> dialog = new ChoiceDialog<>(Arrays.asList(choices).get(0), Arrays.asList(choices));
            dialog.setTitle("Information");
            dialog.setHeaderText(null);
            dialog.setContentText("Kind of charging: ");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> {
                currentStation.batteriesCharging(s.toLowerCase());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("The chargings started.");
                alert.showAndWait();
            });
        });

        //Implements the New PricingPolicy MenuItem
        policy.setOnAction((ActionEvent e) -> {
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
                    foo = new Label("Example: We want 5 prices with a specific validity period for each one.");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 0, 2, 1);
                    foo = new Label("First field(time space duration): 15");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 1, 2, 1);
                    foo = new Label("Second field(price): 4,3,9,10,17");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 2, 2, 1);
                    foo = new Label("We can select as many prices as we desire.");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 3, 2, 1);
                    foo = new Label("Duration*: ");
                    foo.setGraphic(new ImageView(help));
                    foo.setTooltip(new Tooltip("The time duration of each price."));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(foo, 0, 4);
                    boo = new TextField();
                    grid.add(boo, 1, 4);
                    textfields.add(boo);
                    foo = new Label("Prices*: ");
                    foo.setGraphic(new ImageView(help));
                    foo.setTooltip(new Tooltip("The prices of the time spaces."));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(foo, 0, 5);
                    boo = new TextField();
                    grid.add(boo, 1, 5);
                    textfields.add(boo);
                    grid.add(new Label("*Required"), 0, 6);
                    Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
                    HBox buttonsBox = EVLibSim.getButtonsBox();
                    buttonsBox.getChildren().removeIf(buttonPredicate);
                    buttonsBox.getChildren().add(0, policyCreation1);
                    grid.add(buttonsBox, 0, 7, 2, 1);
                    policyCreation1.setDefaultButton(true);
                    root.setCenter(grid);
                } else if (result.get() == buttonTypeTwo) {
                    Maintenance.cleanScreen();
                    grid.setMaxSize(450, 180);
                    foo = new Label("Example: We want 4 prices with different time validity period for each one.");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 0, 2, 1);
                    foo = new Label("First field(time spaces durations): 35,20,90,48");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 1, 2, 1);
                    foo = new Label("Second field(prices): 15,23,7,56");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 2, 2, 1);
                    foo = new Label("We can select as many prices/time spaces as we want.");
                    foo.setPrefWidth(280);
                    foo.setWrapText(true);
                    grid.add(foo, 0, 3, 2, 1);
                    foo = new Label("Durations*: ");
                    grid.add(foo, 0, 4);
                    boo = new TextField();
                    foo.setTooltip(new Tooltip("The time duration of each price."));
                    foo.setGraphic(new ImageView(help));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(boo, 1, 4);
                    textfields.add(boo);
                    foo = new Label("Prices*: ");
                    foo.setTooltip(new Tooltip("The valid price of each time space."));
                    foo.setGraphic(new ImageView(help));
                    foo.getTooltip().setPrefWidth(200);
                    foo.getTooltip().setWrapText(true);
                    grid.add(foo, 0, 5);
                    boo = new TextField();
                    grid.add(boo, 1, 5);
                    textfields.add(boo);
                    grid.add(new Label("*Required"), 0, 6);
                    Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
                    HBox buttonsBox = EVLibSim.getButtonsBox();
                    buttonsBox.getChildren().removeIf(buttonPredicate);
                    buttonsBox.getChildren().add(0, policyCreation2);
                    grid.add(buttonsBox, 0, 7, 2, 1);
                    policyCreation2.setDefaultButton(true);
                    root.setCenter(grid);
                }
            }
        });

        //Buttons
        chargerCreationB.setOnAction(e -> {
            Maintenance.trimTextfields();
            try {
                Charger ch;
                ch = new Charger(currentStation, kindOfCharging.getValue().toLowerCase());
                currentStation.addCharger(ch);
                if (!textfields.get(0).getText().isEmpty())
                    ch.setName(textfields.get(0).getText());
                Maintenance.completionMessage("creation of the charger");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
            }
        });
        chargingStationCreationB.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Maintenance.positiveOrZero(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
                    return;
                for (ChargingStation station: stations)
                    if (station.getName().equals(textfields.get(0).getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select another name.");
                        alert.showAndWait();
                        return;
                    }
                if (Double.parseDouble(textfields.get(1).getText()) > 1000)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please define less fast chargers.");
                    alert.showAndWait();
                    return;
                }
                if (Double.parseDouble(textfields.get(2).getText()) > 1000)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please define less slow chargers.");
                    alert.showAndWait();
                    return;
                }
                if (Double.parseDouble(textfields.get(3).getText()) > 1000)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please define less dischargers.");
                    alert.showAndWait();
                    return;
                }
                if (Double.parseDouble(textfields.get(4).getText()) > 1000)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please define less exchange handlers.");
                    alert.showAndWait();
                    return;
                }
                if (Double.parseDouble(textfields.get(5).getText()) > 1000)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please define less parking slots.");
                    alert.showAndWait();
                    return;
                }
                if (energies.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please choose at least one energy source.");
                    alert.showAndWait();
                    return;
                }
                ChargingStation st;
                st = new ChargingStation(textfields.get(0).getText());
                if ((energyUnit.getSelectionModel().getSelectedIndex() == 0)&&(EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)) {
                    st.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
                    st.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
                    st.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
                    st.setInductivePrice(Double.parseDouble(textfields.get(9).getText()));
                    st.setChargingRateFast(Double.parseDouble(cb1.getValue().replaceAll("[^0-9.]","")) / 1000);
                    st.setChargingRateSlow(Double.parseDouble(cb2.getValue().replaceAll("[^0-9.]","")) / 1000);
                    st.setDisChargingRate(Double.parseDouble(cb3.getValue().replaceAll("[^0-9.]","")) / 1000);
                    st.setInductiveChargingRate(Double.parseDouble(cb4.getValue().replaceAll("[^0-9.]","")) / 1000);
                    st.setTimeofExchange((long)Double.parseDouble(textfields.get(11).getText()) * 1000);
                } else if ((energyUnit.getSelectionModel().getSelectedIndex() == 0)&&(EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 1)) {
                    st.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
                    st.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
                    st.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
                    st.setInductivePrice(Double.parseDouble(textfields.get(9).getText()));
                    st.setChargingRateFast(Double.parseDouble(cb1.getValue().replaceAll("[^0-9.]","")) / 60000);
                    st.setChargingRateSlow(Double.parseDouble(cb2.getValue().replaceAll("[^0-9.]","")) / 60000);
                    st.setDisChargingRate(Double.parseDouble(cb3.getValue().replaceAll("[^0-9.]","")) / 60000);
                    st.setInductiveChargingRate(Double.parseDouble(cb4.getValue().replaceAll("[^0-9.]","")) / 60000);
                    st.setTimeofExchange((long)Double.parseDouble(textfields.get(11).getText()) * 60000);
                } else if ((energyUnit.getSelectionModel().getSelectedIndex() == 1)&&(EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)) {
                    st.setUnitPrice(Double.parseDouble(textfields.get(6).getText()) / 1000);
                    st.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()) / 1000);
                    st.setExchangePrice(Double.parseDouble(textfields.get(8).getText()) / 1000);
                    st.setInductivePrice(Double.parseDouble(textfields.get(9).getText()) / 1000);
                    st.setChargingRateFast(Double.parseDouble(cb1.getValue().replaceAll("[^0-9.]","")));
                    st.setChargingRateSlow(Double.parseDouble(cb2.getValue().replaceAll("[^0-9.]","")));
                    st.setDisChargingRate(Double.parseDouble(cb3.getValue().replaceAll("[^0-9.]","")));
                    st.setInductiveChargingRate(Double.parseDouble(cb4.getValue().replaceAll("[^0-9.]","")));
                    st.setTimeofExchange((long)Double.parseDouble(textfields.get(11).getText()) * 1000);
                } else {
                    st.setUnitPrice(Double.parseDouble(textfields.get(6).getText()) / 1000);
                    st.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()) / 1000);
                    st.setExchangePrice(Double.parseDouble(textfields.get(8).getText()) / 1000);
                    st.setInductivePrice(Double.parseDouble(textfields.get(9).getText()) / 1000);
                    st.setChargingRateFast(Double.parseDouble(cb1.getValue().replaceAll("[^0-9.]","")) / 60);
                    st.setChargingRateSlow(Double.parseDouble(cb2.getValue().replaceAll("[^0-9.]","")) / 60);
                    st.setDisChargingRate(Double.parseDouble(cb3.getValue().replaceAll("[^0-9.]","")) / 60);
                    st.setInductiveChargingRate(Double.parseDouble(cb4.getValue().replaceAll("[^0-9.]","")) / 60);
                    st.setTimeofExchange((long)Double.parseDouble(textfields.get(11).getText()) * 60000);
                }
                if(update.getSelectionModel().getSelectedIndex() == 1) {
                    st.setAutomaticUpdateMode(true);
                    if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                        st.setUpdateSpace((int) Double.parseDouble(textfields.get(10).getText()) * 1000);
                    else
                        st.setUpdateSpace((int) Double.parseDouble(textfields.get(10).getText()) * 60000);
                } else
                    st.setAutomaticUpdateMode(false);
                if(handling.getSelectionModel().getSelectedIndex() == 0)
                    st.setAutomaticQueueHandling(true);
                else
                    st.setAutomaticQueueHandling(false);
                if (textfields.get(1).getText() != null)
                    for (int i = 0; i < Integer.parseInt(textfields.get(1).getText()); i++)
                        st.addCharger(new Charger(st, "fast"));

                if (textfields.get(2).getText() != null)
                    for (int i = 0; i < Integer.parseInt(textfields.get(2).getText()); i++)
                        st.addCharger(new Charger(st, "slow"));

                if (textfields.get(3).getText() != null)
                    for (int i = 0; i < Integer.parseInt(textfields.get(3).getText()); i++)
                        st.addExchangeHandler(new ExchangeHandler(st));

                if (textfields.get(4).getText() != null)
                    for (int i = 0; i < Integer.parseInt(textfields.get(4).getText()); i++)
                        st.addDisCharger(new DisCharger(st));

                if (textfields.get(5).getText() != null)
                    for (int i = 0; i < Integer.parseInt(textfields.get(5).getText()); i++)
                        st.addParkingSlot(new ParkingSlot(st));

                for (String enr : energies) {
                    if (Objects.equals(enr, "Solar"))
                        st.addEnergySource(new Solar());
                    else if (Objects.equals(enr, "Geothermal"))
                        st.addEnergySource(new Geothermal());
                    else if (Objects.equals(enr, "Wind"))
                        st.addEnergySource(new Wind());
                    else if (Objects.equals(enr, "Wave"))
                        st.addEnergySource(new Wave());
                    else if (Objects.equals(enr, "Nonrenewable"))
                        st.addEnergySource(new Nonrenewable());
                    else if (Objects.equals(enr, "Hydroelectric"))
                        st.addEnergySource(new Hydroelectric());
                }
                stations.add(st);
                s.getItems().add(st.getName());
                if (s.getItems().size() == 1) {
                    s.getSelectionModel().select(st.getName());
                    currentStation = st;
                }
                Maintenance.completionMessage("creation of the charging station");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
            }
        });
        modifyStationB.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Maintenance.positiveOrZero(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
                    return;
                for (ChargingStation station : stations) {
                    if (station == currentStation)
                        continue;
                    if (station.getName().equals(textfields.get(0).getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select another name.");
                        alert.showAndWait();
                        return;
                    }
                }
                currentStation.setName(textfields.get(0).getText());
                if ((energyUnit.getSelectionModel().getSelectedIndex() == 0) && (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)) {
                    currentStation.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
                    currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
                    currentStation.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
                    currentStation.setInductivePrice(Double.parseDouble(textfields.get(9).getText()));
                    currentStation.setChargingRateFast(Double.parseDouble(textfields.get(10).getText()) / 1000);
                    currentStation.setChargingRateSlow(Double.parseDouble(textfields.get(11).getText()) / 1000);
                    currentStation.setDisChargingRate(Double.parseDouble(textfields.get(12).getText()) / 1000);
                    currentStation.setInductiveChargingRate(Double.parseDouble(textfields.get(13).getText()) / 1000);
                    currentStation.setTimeofExchange((long) Double.parseDouble(textfields.get(15).getText()) * 1000);
                } else if ((energyUnit.getSelectionModel().getSelectedIndex() == 0) && (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 1)) {
                    currentStation.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
                    currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
                    currentStation.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
                    currentStation.setInductivePrice(Double.parseDouble(textfields.get(9).getText()));
                    currentStation.setChargingRateFast(Double.parseDouble(textfields.get(10).getText()) / 60000);
                    currentStation.setChargingRateSlow(Double.parseDouble(textfields.get(11).getText()) / 60000);
                    currentStation.setDisChargingRate(Double.parseDouble(textfields.get(12).getText()) / 60000);
                    currentStation.setInductiveChargingRate(Double.parseDouble(textfields.get(13).getText()) / 60000);
                    currentStation.setTimeofExchange((long) Double.parseDouble(textfields.get(15).getText()) * 60000);
                } else if ((energyUnit.getSelectionModel().getSelectedIndex() == 1) && (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)) {
                    currentStation.setUnitPrice(Double.parseDouble(textfields.get(6).getText()) / 1000);
                    currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()) / 1000);
                    currentStation.setExchangePrice(Double.parseDouble(textfields.get(8).getText()) / 1000);
                    currentStation.setInductivePrice(Double.parseDouble(textfields.get(9).getText()) / 1000);
                    currentStation.setChargingRateFast(Double.parseDouble(textfields.get(10).getText()));
                    currentStation.setChargingRateSlow(Double.parseDouble(textfields.get(11).getText()));
                    currentStation.setDisChargingRate(Double.parseDouble(textfields.get(12).getText()));
                    currentStation.setInductiveChargingRate(Double.parseDouble(textfields.get(13).getText()));
                    currentStation.setTimeofExchange((long) Double.parseDouble(textfields.get(15).getText()) * 1000);
                } else {
                    currentStation.setUnitPrice(Double.parseDouble(textfields.get(6).getText()) / 1000);
                    currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()) / 1000);
                    currentStation.setExchangePrice(Double.parseDouble(textfields.get(8).getText()) / 1000);
                    currentStation.setInductivePrice(Double.parseDouble(textfields.get(9).getText()) / 1000);
                    currentStation.setChargingRateFast(Double.parseDouble(textfields.get(10).getText()) / 60);
                    currentStation.setChargingRateSlow(Double.parseDouble(textfields.get(11).getText()) / 60);
                    currentStation.setDisChargingRate(Double.parseDouble(textfields.get(12).getText()) / 60);
                    currentStation.setInductiveChargingRate(Double.parseDouble(textfields.get(13).getText()) / 60);
                    currentStation.setTimeofExchange((long) Double.parseDouble(textfields.get(15).getText()) * 60000);
                }
                energies.forEach(w -> {
                    if (!Arrays.asList(currentStation.getSources()).contains(w)) {
                        switch (w) {
                            case "Solar":
                                currentStation.addEnergySource(new Solar());
                                break;
                            case "Wave":
                                currentStation.addEnergySource(new Wave());
                                break;
                            case "Wind":
                                currentStation.addEnergySource(new Wind());
                                break;
                            case "Hydroelectric":
                                currentStation.addEnergySource(new Hydroelectric());
                                break;
                            case "Nonrenewable":
                                currentStation.addEnergySource(new Nonrenewable());
                                break;
                            default:
                                currentStation.addEnergySource(new Geothermal());
                                break;
                        }
                    }
                });
                Arrays.asList(currentStation.getSources()).forEach(w -> {
                    if (!energies.contains(w))
                        currentStation.deleteEnergySource(currentStation.getEnergySource(w));
                });
                if (update.getSelectionModel().getSelectedIndex() == 1) {
                    currentStation.setAutomaticUpdateMode(true);
                    if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                        currentStation.setUpdateSpace((int) Double.parseDouble(textfields.get(14).getText()) * 1000);
                    else
                        currentStation.setUpdateSpace((int) Double.parseDouble(textfields.get(14).getText()) * 60000);
                }
                else
                    currentStation.setAutomaticUpdateMode(false);
                if(handling.getSelectionModel().getSelectedIndex() == 0)
                    currentStation.setAutomaticQueueHandling(true);
                else
                    currentStation.setAutomaticQueueHandling(false);
                s.getItems().set(s.getSelectionModel().getSelectedIndex(), textfields.get(0).getText());
                s.getSelectionModel().select(textfields.get(0).getText());
                Maintenance.completionMessage("modification of the charging station");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
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
                    if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                        p[i] = Double.parseDouble(prices[i]);
                    else
                        p[i] = Double.parseDouble(prices[i]) / 1000;
                PricingPolicy policy = new PricingPolicy(timeDuration, p);
                currentStation.setPricingPolicy(policy);
                Maintenance.completionMessage("creation of the pricing policy");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
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
                        s[i] = (long) Double.parseDouble(spaces[i]) * 1000;
                    else
                        s[i] = (long) Double.parseDouble(spaces[i]) * 60000;
                    if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                        p[i] = Double.parseDouble(prices[i]);
                    else
                        p[i] = Double.parseDouble(prices[i]) / 1000;
                }
                PricingPolicy policy = new PricingPolicy(s, p);
                currentStation.setPricingPolicy(policy);
                Maintenance.completionMessage("creation of the pricing policy");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
            }
        });

        batteryCreationB.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            try {
                if (Double.parseDouble(textfields.get(0).getText()) < Double.parseDouble(textfields.get(1).getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                    alert.showAndWait();
                    return;
                }
                if (Double.parseDouble(textfields.get(2).getText()) < 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The number of chargings has to be at least 1.");
                    alert.showAndWait();
                    return;
                }
                Battery bat;
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                    bat = new Battery(Integer.parseInt(textfields.get(1).getText()), Integer.parseInt(textfields.get(0).getText()));
                else
                    bat = new Battery(Integer.parseInt(textfields.get(1).getText()) * 1000, Integer.parseInt(textfields.get(0).getText()) * 1000);
                bat.setMaxNumberOfChargings(Integer.parseInt(textfields.get(2).getText()));
                currentStation.joinBattery(bat);
                Maintenance.completionMessage("creation of the battery");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
            }
        });
        return stationM;
    }

    private static class ButtonCell<T> extends TableCell<T, Boolean> {
        private final Button cellButton = new Button();
        private final TableView tableview;

        ButtonCell(TableView tblView) {
            this.cellButton.setGraphic(new ImageView(image));
            this.cellButton.setMaxSize(image.getWidth(), image.getHeight());
            this.cellButton.setMinSize(image.getWidth(), image.getHeight());
            tableview = tblView;
        }

        void chargerOnAction() {
            cellButton.setOnAction((ActionEvent e) -> {
                int selectdIndex = getTableRow().getIndex();
                Charger ch = (Charger) tableview.getItems().get(selectdIndex);
                if (ch.getChargingEvent() == null) {
                    if (Maintenance.confirmDeletion()) {
                        currentStation.deleteCharger(ch);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The charger is occupied now. It cannot be deleted.");
                    alert.showAndWait();
                }
                allChargersMI.fire();
            });
        }

        void dischargerOnAction() {
            cellButton.setOnAction((ActionEvent e) -> {
                int selectedIndex = getTableRow().getIndex();
                DisCharger ch = (DisCharger) tableview.getItems().get(selectedIndex);
                if (ch.getDisChargingEvent() == null) {
                    if (Maintenance.confirmDeletion()) {
                        currentStation.deleteDisCharger(ch);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The discharger is occupied now. It cannot be deleted.");
                    alert.showAndWait();
                }
                allDisChargersMI.fire();
            });
        }

        void exchangeHandlerOnAction() {
            cellButton.setOnAction((ActionEvent e) -> {
                int selectedIndex = getTableRow().getIndex();
                ExchangeHandler ch = (ExchangeHandler) tableview.getItems().get(selectedIndex);
                if (ch.getChargingEvent() == null) {
                    if (Maintenance.confirmDeletion()) {
                        currentStation.deleteExchangeHandler(ch);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The exchange handler is occupied now. It cannot be deleted.");
                    alert.showAndWait();
                }
                allExchangeHandlersMI.fire();
            });
        }

        void parkingSlotOnAction() {
            cellButton.setOnAction((ActionEvent e) -> {
                int selectedIndex = getTableRow().getIndex();
                ParkingSlot ch = (ParkingSlot) tableview.getItems().get(selectedIndex);
                if (ch.getParkingEvent() == null) {
                    if (Maintenance.confirmDeletion()) {
                        currentStation.deleteParkingSlot(ch);
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The parking slot is occupied now. It cannot be deleted.");
                    alert.showAndWait();
                }
                allParkingSlotsMI.fire();
            });
        }

        void batteriesOnAction() {
            cellButton.setOnAction((ActionEvent e) -> {
                int selectedIndex = getTableRow().getIndex();
                Battery ch = (Battery) tableview.getItems().get(selectedIndex);
                for (Charger charger : currentStation.getChargers())
                    if (charger.getChargingEvent() != null)
                        if (charger.getChargingEvent().getElectricVehicle().getBattery() == ch) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The battery is charging now. It cannot be deleted.");
                            alert.showAndWait();
                            return;
                        }
                if (Maintenance.confirmDeletion())
                    currentStation.deleteBattery(ch);
                allBatteriesMI.fire();
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty)
                setGraphic(cellButton);
        }
    }
}
