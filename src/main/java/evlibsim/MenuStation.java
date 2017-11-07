package evlibsim;

import evlib.ev.Battery;
import evlib.sources.*;
import evlib.station.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static evlibsim.EVLibSim.*;

class MenuStation {

    static final MenuItem newChargingStationMI = new MenuItem("New station");
    static final ScrollPane scroll = new ScrollPane();
    private static final Menu stationM = new Menu("Station");
    private static final Menu chargersM = new Menu("Charger");
    private static final MenuItem newChargerMI = new MenuItem("New charger");
    private static final Menu disChargersM = new Menu("DisCharger");
    private static final MenuItem allChargersMI = new MenuItem("All chargers");
    private static final MenuItem newDisChargerMI = new MenuItem("New discharger");
    private static final MenuItem allDisChargersMI = new MenuItem("All dischargers");
    private static final Menu exchangeHandlersM = new Menu("Exchange handler");
    private static final MenuItem newExchangeHandlerMI = new MenuItem("New exchange handler");
    private static final MenuItem allExchangeHandlersMI = new MenuItem("All exchange handlers");
    private static final Menu parkingSlotsM = new Menu("Parking slot");
    private static final MenuItem newParkingSlotMI = new MenuItem("New parking slot");
    private static final MenuItem allParkingSlotsMI = new MenuItem("All parking slots");
    private static final MenuItem modifyChargingStationMI = new MenuItem("Modify station");
    private static final MenuItem newBatteryMI = new MenuItem("New battery");
    private static final MenuItem allBatteriesMI = new MenuItem("Batteries");
    private static final MenuItem batteriesChargingMI = new MenuItem("Batteries charging");
    private static final Button chargingStationCreationB = new Button("Creation");
    private static final Button chargerCreationB = new Button("Creation");
    private static final Button modifyStationB = new Button("Modification");
    private static final Button batteryCreationB = new Button("Creation");
    private static final Image image = new Image(MenuStation.class.getResourceAsStream("/d.png"));
    static RadioMenuItem cs;
    private static boolean automaticHandling;
    private static boolean automaticUpdate;
    private static String kindOfCharging;
    private static Button delete;

    static Menu createStationMenu() {
        chargersM.getItems().addAll(newChargerMI, allChargersMI);
        disChargersM.getItems().addAll(newDisChargerMI, allDisChargersMI);
        exchangeHandlersM.getItems().addAll(newExchangeHandlerMI, allExchangeHandlersMI);
        parkingSlotsM.getItems().addAll(newParkingSlotMI, allParkingSlotsMI);
        stationM.getItems().addAll(newChargingStationMI, new SeparatorMenuItem(), chargersM, disChargersM,
                exchangeHandlersM, parkingSlotsM, new SeparatorMenuItem(), modifyChargingStationMI,
                new SeparatorMenuItem(), newBatteryMI, batteriesChargingMI, allBatteriesMI);

        //Implements the New ChargingStation MenuItem
        newChargingStationMI.setOnAction((ActionEvent e) ->
        {
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(1000, 600);
            TextField boo;
            Label foo;
            foo = new Label("Name: ");
            EVLibSim.grid.add(foo, 0, 0);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Fast chargers: ");
            EVLibSim.grid.add(foo, 2, 0);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("Slow chargers: ");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Exchange slots: ");
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("DisCharging slots: ");
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Parking slots: ");
            EVLibSim.grid.add(foo, 2, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Energy sources: ");
            EVLibSim.grid.add(foo, 0, 3);
            MenuBar sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            Menu src = new Menu("Choice");
            RadioMenuItem sol = new RadioMenuItem("Solar");
            RadioMenuItem win = new RadioMenuItem("Wind");
            RadioMenuItem wav = new RadioMenuItem("Wave");
            RadioMenuItem hydr = new RadioMenuItem("Hydroelectric");
            RadioMenuItem non = new RadioMenuItem("Nonrenewable");
            RadioMenuItem geo = new RadioMenuItem("Geothermal");
            src.getItems().addAll(sol, win, wav, hydr, non, geo);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 1, 3);
            foo = new Label("Charging fee per unit: ");
            EVLibSim.grid.add(foo, 2, 3);
            boo = new TextField("1.0");
            EVLibSim.grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit: ");
            EVLibSim.grid.add(foo, 0, 4);
            boo = new TextField("1.0");
            EVLibSim.grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Battery exchange fee: ");
            EVLibSim.grid.add(foo, 2, 4);
            boo = new TextField("1.0");
            EVLibSim.grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Inductive charging fee per unit: ");
            EVLibSim.grid.add(foo, 0, 5);
            boo = new TextField("1.0");
            EVLibSim.grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Fast charging ratio: ");
            EVLibSim.grid.add(foo, 2, 5);
            boo = new TextField("0.01");
            EVLibSim.grid.add(boo, 3, 5);
            textfields.add(boo);
            foo = new Label("Slow charging ratio: ");
            EVLibSim.grid.add(foo, 0, 6);
            boo = new TextField("0.001");
            EVLibSim.grid.add(boo, 1, 6);
            textfields.add(boo);
            foo = new Label("Discharging ratio: ");
            EVLibSim.grid.add(foo, 2, 6);
            boo = new TextField("0.01");
            EVLibSim.grid.add(boo, 3, 6);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio: ");
            EVLibSim.grid.add(foo, 0, 7);
            boo = new TextField("0.01");
            EVLibSim.grid.add(boo, 1, 7);
            textfields.add(boo);
            foo = new Label("Automatic energy update: ");
            EVLibSim.grid.add(foo, 2, 7);
            sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem te = new RadioMenuItem("True");
            RadioMenuItem fe = new RadioMenuItem("False");
            r.getToggles().addAll(te, fe);
            te.setSelected(true);
            automaticUpdate = true;
            r.selectedToggleProperty().addListener((observable, newValue, oldValue) -> automaticUpdate = te.isSelected());
            src.getItems().addAll(te, fe);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 3, 7);
            foo = new Label("Automatic queue handling: ");
            EVLibSim.grid.add(foo, 0, 8);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            sourc.setId("menubar");
            src = new Menu("Choice");
            ToggleGroup t = new ToggleGroup();
            RadioMenuItem tr = new RadioMenuItem("True");
            RadioMenuItem fa = new RadioMenuItem("False");
            t.getToggles().addAll(tr, fa);
            tr.setSelected(true);
            automaticHandling = true;
            t.selectedToggleProperty().addListener((observable, oldValue, newValue) -> automaticHandling = tr.isSelected());
            src.getItems().addAll(tr, fa);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 1, 8);
            foo = new Label("Update space(millis): ");
            EVLibSim.grid.add(foo, 2, 8);
            boo = new TextField("1000");
            EVLibSim.grid.add(boo, 3, 8);
            textfields.add(boo);
            foo = new Label("Battery exchange duration: ");
            EVLibSim.grid.add(foo, 0, 9);
            boo = new TextField("5000");
            EVLibSim.grid.add(boo, 1, 9);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, chargingStationCreationB);
            grid.add(buttonsBox, 0, 10, 2, 1);
            chargingStationCreationB.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
            sol.setOnAction((ActionEvent et) -> {
                if (sol.isSelected())
                    EVLibSim.energies.add("Solar");
                else
                    EVLibSim.energies.remove("Solar");
            });
            win.setOnAction((ActionEvent et) -> {
                if (win.isSelected())
                    EVLibSim.energies.add("Wind");
                else
                    EVLibSim.energies.remove("Wind");
            });
            wav.setOnAction((ActionEvent et) -> {
                if (wav.isSelected())
                    EVLibSim.energies.add("Wave");
                else
                    EVLibSim.energies.remove("Wave");
            });
            hydr.setOnAction((ActionEvent et) -> {
                if (hydr.isSelected())
                    EVLibSim.energies.add("Hydroelectric");
                else
                    EVLibSim.energies.remove("Hydroelectric");
            });
            geo.setOnAction((ActionEvent et) -> {
                if (geo.isSelected())
                    EVLibSim.energies.add("Geothermal");
                else
                    EVLibSim.energies.remove("Geothermal");
            });
            non.setOnAction((ActionEvent et) -> {
                if (non.isSelected())
                    EVLibSim.energies.add("Nonrenewable");
                else
                    EVLibSim.energies.remove("Nonrenewable");
            });
        });

        //Implements the New Charger MenuItem
        newChargerMI.setOnAction(e ->
        {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(400, 400);
            TextField boo;
            Label foo;
            foo = new Label("Kind: ");
            EVLibSim.grid.add(foo, 0, 0);
            MenuBar sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            Menu src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem slow = new RadioMenuItem("Slow");
            RadioMenuItem fast = new RadioMenuItem("Fast");
            r.getToggles().addAll(slow, fast);
            kindOfCharging = "slow";
            slow.setSelected(true);
            r.selectedToggleProperty().addListener((observable, newValue, oldValue) -> {
                if (slow.isSelected())
                    kindOfCharging = "slow";
                else
                    kindOfCharging = "fast";
            });
            src.getItems().addAll(slow, fast);
            sourc.getMenus().add(src);
            grid.add(sourc, 1, 0);
            foo = new Label("Name: ");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, chargerCreationB);
            grid.add(buttonsBox, 0, 2, 2, 1);
            chargerCreationB.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });

        //Implements the New DisCharge MenuItem.
        newDisChargerMI.setOnAction(e ->
        {
            if (Maintenance.stationCheck())
                return;
            TextInputDialog alert = new TextInputDialog();
            alert.setTitle("Name for DisCharger");
            alert.setHeaderText(null);
            alert.setContentText("Please give a name: ");
            Optional<String> result = alert.showAndWait();
            result.ifPresent(s -> {
                DisCharger ch;
                ch = new DisCharger(currentStation);
                ch.setName(s);
                currentStation.addDisCharger(ch);
                Maintenance.completionMessage("DisCharger creation");
            });
        });

        //Implements the New ExchangeHandler MenuItem.
        newExchangeHandlerMI.setOnAction(e ->
        {
            if (Maintenance.stationCheck())
                return;
            TextInputDialog alert = new TextInputDialog();
            alert.setTitle("Name for ExchangeHandler");
            alert.setHeaderText(null);
            alert.setContentText("Please give a name: ");
            Optional<String> result = alert.showAndWait();
            result.ifPresent(s -> {
                ExchangeHandler ch;
                ch = new ExchangeHandler(currentStation);
                ch.setName(s);
                currentStation.addExchangeHandler(ch);
                Maintenance.completionMessage("ExchangeHandler creation");
            });
        });

        //Implements the New ParkingSlot MenuItem.
        newParkingSlotMI.setOnAction(e ->
        {
            if (Maintenance.stationCheck())
                return;
            TextInputDialog alert = new TextInputDialog();
            alert.setTitle("Name for ParkingSlot");
            alert.setHeaderText(null);
            alert.setContentText("Please give a name: ");
            Optional<String> result = alert.showAndWait();
            result.ifPresent(s -> {
                ParkingSlot ch;
                ch = new ParkingSlot(currentStation);
                ch.setName(s);
                currentStation.addParkingSlot(ch);
                Maintenance.completionMessage("ParkingSlot creation");
            });
        });

        //Implements the Modify ChargingStation.
        modifyChargingStationMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(1000, 600);
            EVLibSim.energies.clear();
            TextField boo;
            Label foo;
            foo = new Label("Name of charging station: ");
            EVLibSim.grid.add(foo, 0, 0);
            boo = new TextField(currentStation.getName());
            EVLibSim.grid.add(boo, 1, 0);
            textfields.add(boo);
            MenuBar sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(70);
            Menu src;
            foo = new Label("Charging fee per unit: ");
            EVLibSim.grid.add(foo, 2, 0);
            boo = new TextField(Double.toString(currentStation.getUnitPrice()));
            EVLibSim.grid.add(boo, 3, 0);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit: ");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField(Double.toString(currentStation.getDisUnitPrice()));
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Battery exchange fee: ");
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField(Double.toString(currentStation.getExchangePrice()));
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Inductive fee per unit: ");
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField(Double.toString(currentStation.getInductivePrice()));
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Fast charging ratio: ");
            EVLibSim.grid.add(foo, 2, 2);
            boo = new TextField(Double.toString(currentStation.getChargingRatioFast()));
            EVLibSim.grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Slow charging ratio: ");
            EVLibSim.grid.add(foo, 0, 3);
            boo = new TextField(Double.toString(currentStation.getChargingRatioSlow()));
            EVLibSim.grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Discharging ratio: ");
            EVLibSim.grid.add(foo, 2, 3);
            boo = new TextField(Double.toString(currentStation.getDisChargingRatio()));
            EVLibSim.grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio: ");
            EVLibSim.grid.add(foo, 0, 4);
            boo = new TextField(Double.toString(currentStation.getInductiveRatio()));
            EVLibSim.grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Automatic energy update: ");
            EVLibSim.grid.add(foo, 2, 4);
            sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem te = new RadioMenuItem("True");
            RadioMenuItem fe = new RadioMenuItem("False");
            r.getToggles().addAll(te, fe);
            automaticUpdate = currentStation.getUpdateMode();
            if (automaticUpdate)
                te.setSelected(true);
            else
                fe.setSelected(true);
            r.selectedToggleProperty().addListener((observable, oldValue, newValue) -> automaticUpdate = te.isSelected());
            src.getItems().addAll(te, fe);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 3, 4);
            foo = new Label("Automatic queue handling: ");
            EVLibSim.grid.add(foo, 0, 5);
            sourc = new MenuBar();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup t = new ToggleGroup();
            RadioMenuItem tr = new RadioMenuItem("True");
            RadioMenuItem fa = new RadioMenuItem("False");
            t.getToggles().addAll(tr, fa);
            automaticHandling = currentStation.getQueueHandling();
            if (automaticHandling)
                tr.setSelected(true);
            else
                fa.setSelected(true);
            t.selectedToggleProperty().addListener((observable, oldValue, newValue) -> automaticHandling = tr.isSelected());
            src.getItems().addAll(tr, fa);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 1, 5);
            foo = new Label("Update space(millis): ");
            EVLibSim.grid.add(foo, 2, 5);
            boo = new TextField(Long.toString(currentStation.getUpdateSpace()));
            EVLibSim.grid.add(boo, 3, 5);
            textfields.add(boo);
            foo = new Label("Battery exchange duration: ");
            EVLibSim.grid.add(foo, 0, 6);
            boo = new TextField(Long.toString(currentStation.getTimeOfExchange()));
            EVLibSim.grid.add(boo, 1, 6);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, modifyStationB);
            grid.add(buttonsBox, 0, 7, 2, 1);
            modifyStationB.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });

        //Implements the All Chargers MenuItem
        allChargersMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            HBox z;
            box.setAlignment(Pos.CENTER);
            box.setSpacing(15);
            scroll.setMaxSize(600, 600);
            scroll.getStyleClass().add("scroll");
            Label foo;
            for (Charger ch : currentStation.getChargers()) {
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Id: " + ch.getId());
                z.getChildren().add(foo);
                foo = new Label("Name: " + ch.getName());
                z.getChildren().add(foo);
                foo = new Label("Kind: " + ch.getKindOfCharging());
                z.getChildren().add(foo);
                foo = new Label("Occupied: " + (ch.getChargingEvent() != null));
                z.getChildren().add(foo);
                delete = new Button();
                delete.setMaxSize(image.getWidth(), image.getHeight());
                delete.setMinSize(image.getWidth(), image.getHeight());
                delete.setGraphic(new ImageView(image));
                delete.setOnAction(et -> {
                    if (ch.getChargingEvent() == null) {
                        if (Maintenance.confirmDeletion()) {
                            currentStation.deleteCharger(ch);
                            Maintenance.completionMessage("Charger deletion");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Charger is busy now. It cannot be deleted.");
                        alert.showAndWait();
                    }
                    allChargersMI.fire();
                });
                z.getChildren().add(delete);
                box.getChildren().add(z);
            }
            scroll.setContent(box);
            root.setCenter(scroll);
        });

        //Implements the All DisChargers MenuItem.
        allDisChargersMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            HBox z;
            box.setAlignment(Pos.CENTER);
            box.setSpacing(15);
            scroll.setMaxSize(600, 600);
            scroll.getStyleClass().add("scroll");
            Label foo;
            for (DisCharger ch : currentStation.getDisChargers()) {
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Id: " + ch.getId());
                z.getChildren().add(foo);
                foo = new Label("Name: " + ch.getName());
                z.getChildren().add(foo);
                foo = new Label("Occupied: " + (ch.getDisChargingEvent() != null));
                z.getChildren().add(foo);
                delete = new Button();
                delete.setMaxSize(image.getWidth(), image.getHeight());
                delete.setMinSize(image.getWidth(), image.getHeight());
                delete.setGraphic(new ImageView(image));
                delete.setOnAction(et -> {
                    if (ch.getDisChargingEvent() == null) {
                        if (Maintenance.confirmDeletion()) {
                            currentStation.deleteDisCharger(ch);
                            Maintenance.completionMessage("DisCharger deletion");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("DisCharger is busy now. It cannot be deleted.");
                        alert.showAndWait();
                    }
                    allDisChargersMI.fire();
                });
                z.getChildren().add(delete);
                box.getChildren().add(z);
            }
            scroll.setContent(box);
            root.setCenter(scroll);
        });

        //Implements the All ExchangeHandlers MenuItem.
        allExchangeHandlersMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            HBox z;
            box.setAlignment(Pos.CENTER);
            box.setSpacing(15);
            scroll.setMaxSize(600, 600);
            scroll.getStyleClass().add("scroll");
            Label foo;
            for (ExchangeHandler ch : currentStation.getExchangeHandlers()) {
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Id: " + ch.getId());
                z.getChildren().add(foo);
                foo = new Label("Name: " + ch.getName());
                z.getChildren().add(foo);
                foo = new Label("Occupied: " + (ch.getChargingEvent() != null));
                z.getChildren().add(foo);
                delete = new Button();
                delete.setMaxSize(image.getWidth(), image.getHeight());
                delete.setMinSize(image.getWidth(), image.getHeight());
                delete.setGraphic(new ImageView(image));
                delete.setOnAction(et -> {
                    if (ch.getChargingEvent() == null) {
                        if (Maintenance.confirmDeletion()) {
                            currentStation.deleteExchangeHandler(ch);
                            Maintenance.completionMessage("ExchangeHandler deletion");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("ExchangeHandler is busy now. It cannot be deleted.");
                        alert.showAndWait();
                    }
                    allExchangeHandlersMI.fire();
                });
                z.getChildren().add(delete);
                box.getChildren().add(z);
            }
            scroll.setContent(box);
            root.setCenter(scroll);
        });

        //Implements the All ParkingSlots MenuItem.
        allParkingSlotsMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            HBox z;
            box.setAlignment(Pos.CENTER);
            box.setSpacing(15);
            scroll.setMaxSize(600, 600);
            scroll.getStyleClass().add("scroll");
            Label foo;
            for (ParkingSlot ch : currentStation.getParkingSlots()) {
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Id: " + ch.getId());
                z.getChildren().add(foo);
                foo = new Label("Name: " + ch.getName());
                z.getChildren().add(foo);
                foo = new Label("Occupied: " + (ch.getParkingEvent() != null));
                z.getChildren().add(foo);
                delete = new Button();
                delete.setMaxSize(image.getWidth(), image.getHeight());
                delete.setMinSize(image.getWidth(), image.getHeight());
                delete.setGraphic(new ImageView(image));
                delete.setOnAction(et -> {
                    if (ch.getParkingEvent() == null) {
                        if (Maintenance.confirmDeletion()) {
                            currentStation.deleteParkingSlot(ch);
                            Maintenance.completionMessage("ParkingSlot deletion");
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("ParkingSlot is busy now. It cannot be deleted.");
                        alert.showAndWait();
                    }
                    allParkingSlotsMI.fire();
                });
                z.getChildren().add(delete);
                box.getChildren().add(z);
            }
            scroll.setContent(box);
            root.setCenter(scroll);
        });

        //Implements the New Battery MenuItem.
        newBatteryMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(400, 280);
            TextField boo;
            Label foo;
            foo = new Label("Battery capacity: ");
            EVLibSim.grid.add(foo, 0, 0);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 0);
            textfields.add(boo);
            foo = new Label("Battery remaining: ");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Maximum chargings: ");
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, batteryCreationB);
            grid.add(buttonsBox, 0, 3, 2, 1);
            batteryCreationB.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });

        //Implements the All Batteries MenuItem.
        allBatteriesMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            HBox z;
            box.setAlignment(Pos.CENTER);
            box.setSpacing(15);
            scroll.setMaxSize(600, 600);
            scroll.getStyleClass().add("scroll");
            Label foo;
            for (Battery b : currentStation.getBatteries()) {
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Id: " + b.getId());
                z.getChildren().add(foo);
                foo = new Label("Capacity: " + b.getCapacity());
                z.getChildren().add(foo);
                foo = new Label("Remaining Amount: " + b.getRemAmount());
                z.getChildren().add(foo);
                foo = new Label("Maximum Chargings: " + b.getMaxNumberOfChargings());
                z.getChildren().add(foo);
                delete = new Button();
                delete.setMaxSize(image.getWidth(), image.getHeight());
                delete.setMinSize(image.getWidth(), image.getHeight());
                delete.setGraphic(new ImageView(image));
                delete.setOnAction(et -> {
                    for (Charger charger : currentStation.getChargers()) {
                        if (charger.getChargingEvent() != null)
                            if (charger.getChargingEvent().getElectricVehicle().getBattery() == b) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Battery is charging now.");
                                alert.showAndWait();
                                return;
                            }
                    }
                    if (Maintenance.confirmDeletion()) {
                        currentStation.deleteBattery(b);
                        Maintenance.completionMessage("Battery deletion");
                        allBatteriesMI.fire();
                    }
                });
                z.getChildren().add(delete);
                box.getChildren().add(z);
            }
            scroll.setContent(box);
            root.setCenter(scroll);
        });

        //Implements the charging of batteries that are for battery exchange
        batteriesChargingMI.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            String[] choices = {"Slow", "Fast"};
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
        //Buttons
        chargerCreationB.setOnAction(e ->
        {
            Charger ch;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z]", "")));
            try {
                ch = new Charger(currentStation, kindOfCharging);
                currentStation.addCharger(ch);
                ch.setName(textfields.get(0).getText());
                Maintenance.completionMessage("Charger creation");
                newChargerMI.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                newChargerMI.fire();
            }
        });
        chargingStationCreationB.setOnAction(e ->
        {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z0-9.]", "")));
            if (energies.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please choose at least one energy source.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(1).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0 ||
                    Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(7).getText()) < 0 ||
                    Double.parseDouble(textfields.get(8).getText()) < 0 ||
                    Double.parseDouble(textfields.get(9).getText()) < 0 ||
                    Double.parseDouble(textfields.get(10).getText()) < 0 ||
                    Double.parseDouble(textfields.get(11).getText()) < 0 ||
                    Double.parseDouble(textfields.get(12).getText()) < 0 ||
                    Double.parseDouble(textfields.get(13).getText()) < 0 ||
                    Double.parseDouble(textfields.get(14).getText()) < 0 ||
                    Double.parseDouble(textfields.get(15).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers.");
                alert.showAndWait();
                return;
            }
            try {
                ChargingStation st;
                st = new ChargingStation(textfields.get(0).getText());
                st.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
                st.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
                st.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
                st.setInductivePrice(Double.parseDouble(textfields.get(9).getText()));
                st.setChargingRatioFast(Double.parseDouble(textfields.get(10).getText()));
                st.setChargingRatioSlow(Double.parseDouble(textfields.get(11).getText()));
                st.setDisChargingRatio(Double.parseDouble(textfields.get(12).getText()));
                st.setInductiveChargingRatio(Double.parseDouble(textfields.get(13).getText()));
                st.setTimeofExchange(Long.parseLong(textfields.get(15).getText()));
                st.setAutomaticUpdateMode(automaticUpdate);
                Energy.updateStorage.setDisable(automaticUpdate);
                st.setUpdateSpace(Integer.parseInt(textfields.get(14).getText()));
                st.setAutomaticQueueHandling(automaticHandling);
                if (textfields.get(1).getText() != null) {
                    int len = Integer.parseInt(textfields.get(1).getText());
                    for (int i = 0; i < len; i++)
                        st.addCharger(new Charger(st, "fast"));
                }
                if (textfields.get(2).getText() != null) {
                    int len = Integer.parseInt(textfields.get(2).getText());
                    for (int i = 0; i < len; i++)
                        st.addCharger(new Charger(st, "slow"));
                }
                if (textfields.get(3).getText() != null) {
                    int len = Integer.parseInt(textfields.get(3).getText());
                    for (int i = 0; i < len; i++)
                        st.addExchangeHandler(new ExchangeHandler(st));
                }
                if (textfields.get(4).getText() != null) {
                    int len = Integer.parseInt(textfields.get(4).getText());
                    for (int i = 0; i < len; i++)
                        st.addDisCharger(new DisCharger(st));
                }
                if (textfields.get(5).getText() != null) {
                    int len = Integer.parseInt(textfields.get(5).getText());
                    for (int i = 0; i < len; i++)
                        st.addParkingSlot(new ParkingSlot(st));
                }
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
                cs = new RadioMenuItem(st.getName());
                group.getToggles().add(cs);
                s.getItems().add(cs);
                if (s.getItems().size() == 1)
                    cs.setSelected(true);
                Maintenance.completionMessage("ChargingStation creation");
                newChargingStationMI.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                newChargingStationMI.fire();
            }
        });
        modifyStationB.setOnAction(e -> {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^a-zA-Z0-9.]", "")));
            try {
                currentStation.setName(textfields.get(0).getText());
                currentStation.setUnitPrice(Double.parseDouble(textfields.get(1).getText()));
                currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(2).getText()));
                currentStation.setExchangePrice(Double.parseDouble(textfields.get(3).getText()));
                currentStation.setInductivePrice((Double.parseDouble(textfields.get(4).getText())));
                currentStation.setChargingRatioFast((Double.parseDouble(textfields.get(5).getText())));
                currentStation.setChargingRatioSlow((Double.parseDouble(textfields.get(6).getText())));
                currentStation.setDisChargingRatio((Double.parseDouble(textfields.get(7).getText())));
                currentStation.setInductiveChargingRatio((Double.parseDouble(textfields.get(8).getText())));
                currentStation.setAutomaticUpdateMode(automaticUpdate);
                Energy.updateStorage.setDisable(automaticUpdate);
                currentStation.setUpdateSpace((Integer.parseInt(textfields.get(9).getText())));
                currentStation.setTimeofExchange(Long.parseLong(textfields.get(10).getText()));
                currentStation.setAutomaticQueueHandling(automaticHandling);
                cs = (RadioMenuItem) group.getSelectedToggle();
                cs.setText(currentStation.getName());
                Maintenance.completionMessage("modification of the ChargingStation");
                modifyChargingStationMI.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                modifyChargingStationMI.fire();
            }
        });


        batteryCreationB.setOnAction(e -> {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^0-9.]", "")));
            if (Double.parseDouble(textfields.get(0).getText()) < Double.parseDouble(textfields.get(1).getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            try {
                Battery bat = new Battery(Integer.parseInt(textfields.get(1).getText()), Integer.parseInt(textfields.get(0).getText()));
                bat.setMaxNumberOfChargings(Integer.parseInt(textfields.get(2).getText()));
                currentStation.joinBattery(bat);
                Maintenance.completionMessage("Battery creation");
                newBatteryMI.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                newBatteryMI.fire();
            }
        });
        return stationM;
    }
}
