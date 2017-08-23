package evlibsim;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import EV.Battery;
import EV.Driver;
import EV.ElectricVehicle;
import Events.ChargingEvent;
import Events.DisChargingEvent;
import Events.ParkingEvent;
import Sources.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import Station.*;

public class EVLibSim extends Application
{
    private static BorderPane root = new BorderPane();
    private static GridPane grid = new GridPane();
    private static ArrayList<TextField> textfields = new ArrayList<>();
    private static ArrayList<ChargingStation> stations = new ArrayList<>();
    private ChargingStation currentStation;
    private ArrayList<String> energies = new ArrayList<>();
    private ArrayList<MenuItem> newEnergies = new ArrayList<>();
    private ArrayList<MenuItem> newAmounts = new ArrayList<>();
    private ArrayList<MenuItem> deletions = new ArrayList<>();
    private boolean automaticHandling = true;
    private boolean automaticUpdate = false;
    private Text t;
    private Button stationCreation = new Button("Creation");
    private Button chargerCreation = new Button("Creation");
    private Button chargingEventCreation = new Button("Creation");
    private Button disChargingEventCreation = new Button("Creation");
    private Button parkingEventCreation = new Button("Creation");
    private Button exchangeEventCreation = new Button("Creation");
    private Button modifyStation = new Button("Modification");
    private ToggleGroup group = new ToggleGroup();
    private RadioMenuItem cs;
    private MenuItem newEnergy;
    private MenuItem newAmount;
    private MenuItem deletion;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EVLibSim");
        Scene scene = new Scene(root, 1350, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu view = new Menu("View");
        MenuItem startScreenMenuItem = new MenuItem("Start Screen");
        MenuItem totalActivity = new MenuItem("Total Activity");
        MenuItem queue = new MenuItem("Queue of events");
        Menu s = new Menu("Stations");
        Menu search = new Menu("Search");
        MenuItem modifyChargingStation = new MenuItem("Modify ChargingStation");
        MenuItem searchCharging = new MenuItem("Search ChargingEvent");
        MenuItem searchDisCharging = new MenuItem("Search DisChargingEvent");
        MenuItem searchExchangeEvent = new MenuItem("Search Exchange Battery Event");
        MenuItem searchParkingSlot = new MenuItem("Search ParkingEvent");
        Menu station = new Menu("Station");
        MenuItem newStation = new MenuItem("New ChargingStation");
        MenuItem newCharger = new MenuItem("New Charger");
        MenuItem newDisCharger = new MenuItem("New DisCharger");
        MenuItem newExchangeHandler = new MenuItem("New ExchangeHandler");
        MenuItem newParkingSlot = new MenuItem("New ParkingSlot");
        station.getItems().addAll(newStation, new SeparatorMenuItem(), newCharger, newDisCharger, newExchangeHandler, newParkingSlot, new SeparatorMenuItem(), modifyChargingStation);
        search.getItems().addAll(searchCharging, searchDisCharging, searchExchangeEvent, searchParkingSlot);
        Menu energy = new Menu("Energy");
        Menu hydro = new Menu("Hydroelectric energy");
        newEnergy = new MenuItem("Hydroelectric energy");
        newAmount = new MenuItem("New amount of hydroelectric energy");
        deletion = new MenuItem("Energy removal");
        hydro.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        Menu wind = new Menu("Wind energy");
        newEnergy = new MenuItem("Wind energy");
        newAmount = new MenuItem("New amount of wind energy");
        deletion = new MenuItem("Energy removal");
        wind.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        Menu wave = new Menu("Wave energy");
        newEnergy = new MenuItem("Wave energy");
        newAmount = new MenuItem("New amount of wave energy");
        deletion = new MenuItem("Energy removal");
        wave.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        Menu solar = new Menu("Solar energy");
        newEnergy = new MenuItem("Solar energy");
        newAmount = new MenuItem("New amount of solar energy");
        deletion = new MenuItem("Energy removal");
        solar.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        Menu geothermal = new Menu("Geothermal energy");
        newEnergy = new MenuItem("Geothermal energy");
        newAmount = new MenuItem("New amount of geothermal energy");
        deletion = new MenuItem("Energy removal");
        geothermal.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        Menu nonrenewable = new Menu("Nonrenewable energy");
        newEnergy = new MenuItem("Nonrenewable energy");
        newAmount = new MenuItem("New amount of nonrenewwable energy");
        deletion = new MenuItem("Energy removal");
        nonrenewable.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        energy.getItems().addAll(hydro, wind, wave, solar, geothermal, nonrenewable);
        MenuItem totalEnergy = new MenuItem("Total energy");
        root.setTop(menuBar);
        MenuItem exitMenuItem = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        file.getItems().addAll(startScreenMenuItem, s, exitMenuItem);
        view.getItems().addAll(totalActivity, totalEnergy, queue, new SeparatorMenuItem(), about);
        Menu event = new Menu("Event");
        MenuItem charging = new MenuItem("Charging");
        MenuItem discharging = new MenuItem("Discharging");
        MenuItem exchange = new MenuItem("Exchange of Battery");
        MenuItem parking = new MenuItem("Parking/Inductive charging");
        event.getItems().addAll(charging, discharging, exchange, parking);
        menuBar.getMenus().addAll(file, view, station, event, search, energy);
        scene.getStylesheets().add(EVLibSim.class.getResource("EVLibSim.css").toExternalForm());
        grid.getStyleClass().add("grid");

        //MenuItems
        for(MenuItem m: newEnergies) {
            m.setOnAction((ActionEvent e) -> {
                if (!stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("Wind energy")) {
                    if ((currentStation.searchEnergySource("wind") == null)) {
                        energySource = new Wind(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        energyAlert("Wind");
                } else if (mi.getText().equals("Wave energy")) {
                    if ((currentStation.searchEnergySource("wave") == null)) {
                        energySource = new Wave(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        energyAlert("Wave");
                } else if (mi.getText().equals("Solar energy")) {
                    if ((currentStation.searchEnergySource("solar") == null)) {
                        energySource = new Solar(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        energyAlert("Solar");
                } else if (mi.getText().equals("Geothermal energy")) {
                    if ((currentStation.searchEnergySource("geothermal") == null)) {
                        energySource = new Geothermal(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        energyAlert("Geothermal");
                } else if (mi.getText().equals("Nonrenewable energy")) {
                    if ((currentStation.searchEnergySource("nonrenewable") == null)) {
                        energySource = new NonRenewable(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        energyAlert("Nonrenewable");
                } else {
                    if ((currentStation.searchEnergySource("hydroelectric") == null)) {
                        energySource = new HydroElectric(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        energyAlert("Hydroelectric");
                }
            });
        }
        for(MenuItem m: newAmounts) {
            m.setOnAction((ActionEvent e) -> {
                if (!stationCheck())
                    return;
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Addition of energy amount");
                dialog.setHeaderText(null);
                dialog.setContentText("New amount");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    MenuItem mi = (MenuItem) e.getSource();
                    EnergySource energySource;
                    if (mi.getText().equals("New amount of wind energy")) {
                        energySource = currentStation.searchEnergySource("wind");
                        if ((energySource != null))
                            energySource.insertAmount(Double.parseDouble(result.get()));
                        else
                            notEnergyAlert("Wind");
                    } else if (mi.getText().equals("New amount of wave energy")) {
                        energySource = currentStation.searchEnergySource("wave");
                        if ((energySource != null))
                            energySource.insertAmount(Double.parseDouble(result.get()));
                        else
                            notEnergyAlert("Wave");
                    } else if (mi.getText().equals("New amount of solar energy")) {
                        energySource = currentStation.searchEnergySource("solar");
                        if ((energySource != null))
                            energySource.insertAmount(Double.parseDouble(result.get()));
                        else
                            notEnergyAlert("Solar");
                    } else if (mi.getText().equals("New amount of geothermal energy")) {
                        energySource = currentStation.searchEnergySource("geothermal");
                        if ((energySource != null))
                            energySource.insertAmount(Double.parseDouble(result.get()));
                        else
                            notEnergyAlert("Geothermal");
                    } else if (mi.getText().equals("New amount of nonrenewable energy")) {
                        energySource = currentStation.searchEnergySource("nonrenewable");
                        if ((energySource != null))
                            energySource.insertAmount(Double.parseDouble(result.get()));
                        else
                            notEnergyAlert("Nonrenewable");
                    } else {
                        energySource = currentStation.searchEnergySource("hydroelectric");
                        if ((energySource != null))
                            energySource.insertAmount(Double.parseDouble(result.get()));
                        else
                            notEnergyAlert("Hydroelectric");
                    }
                }
            });
        }
        for(MenuItem m: deletions) {
            m.setOnAction((ActionEvent e) -> {
                if (!stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("Remove wind energy")) {
                    energySource = currentStation.searchEnergySource("wind");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        notEnergyAlert("Wind");
                } else if (mi.getText().equals("Remove wave energy")) {
                    energySource = currentStation.searchEnergySource("wave");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        notEnergyAlert("Wave");
                } else if (mi.getText().equals("Remove solar energy")) {
                    energySource = currentStation.searchEnergySource("solar");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        notEnergyAlert("Solar");
                } else if (mi.getText().equals("Remove geothermal energy")) {
                    energySource = currentStation.searchEnergySource("wave");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        notEnergyAlert("Geothermal");
                } else if (mi.getText().equals("Remove nonrenewable energy")) {
                    energySource = currentStation.searchEnergySource("nonrenewable");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        notEnergyAlert("Non-Renewable");
                } else {
                    energySource = currentStation.searchEnergySource("hydroelectric");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        notEnergyAlert("Hydro-Electric");
                }
            });
        }
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (group.getSelectedToggle() != null) {
                newValue.setSelected(true);
                RadioMenuItem rmi = (RadioMenuItem) group.getSelectedToggle();
                String name = rmi.getText();
                for (ChargingStation cs : stations)
                    if (Objects.equals(cs.reName(), name))
                        currentStation = cs;
            }
        });
        about.setOnAction((ActionEvent e) ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Creator: Karapostolakis Sotirios\nemail: skarapos@outlook.com\nYear: 2017");
            alert.showAndWait();
        });
        modifyChargingStation.setOnAction((ActionEvent e) -> {
            if(!stationCheck())
                return;
            cleanScreen();
            grid.setMaxSize(800, 500);
            energies.clear();
            t = new Text("Charging Station Creation");
            TextField boo;
            Label foo;
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Name of charging station:");
            grid.add(foo, 0, 1);
            boo = new TextField(currentStation.reName());
            grid.add(boo, 1, 1);
            textfields.add(boo);
            MenuBar sourc = new MenuBar();
            sourc.setMaxWidth(100);
            Menu src;
            foo = new Label("Charging fee per unit:");
            grid.add(foo, 2, 1);
            boo = new TextField(Double.toString(currentStation.reUnitPrice()));
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit:");
            grid.add(foo, 0, 2);
            boo = new TextField(Double.toString(currentStation.reDisUnitPrice()));
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery exchange fee:");
            grid.add(foo, 2, 2);
            boo = new TextField(Double.toString(currentStation.reExchangePrice()));
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Inductive fee per unit:");
            grid.add(foo, 0, 3);
            boo = new TextField(Double.toString(currentStation.reInductivePrice()));
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Fast charging ratio:");
            grid.add(foo, 2, 3);
            boo = new TextField(Double.toString(currentStation.reChargingRatioFast()));
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Slow charging ratio:");
            grid.add(foo, 0, 4);
            boo = new TextField(Double.toString(currentStation.reChargingRatioSlow()));
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Discharging ratio:");
            grid.add(foo, 2, 4);
            boo = new TextField(Double.toString(currentStation.reDisChargingRatio()));
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio:");
            grid.add(foo, 0, 5);
            boo = new TextField(Double.toString(currentStation.reInductiveRatio()));
            grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Automatic energy update:");
            grid.add(foo, 2, 5);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem t = new RadioMenuItem("True");
            RadioMenuItem f = new RadioMenuItem("False");
            r.getToggles().addAll(t, f);
            if(currentStation.reUpdateMode())
                t.setSelected(true);
            else
                f.setSelected(true);
            src.getItems().addAll(t, f);
            sourc.getMenus().add(src);
            grid.add(sourc, 3, 5);
            foo = new Label("Automatic queue handling:");
            grid.add(foo, 0, 6);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            r = new ToggleGroup();
            RadioMenuItem tr = new RadioMenuItem("True");
            RadioMenuItem fa = new RadioMenuItem("False");
            r.getToggles().addAll(tr, fa);
            if(currentStation.reQueueHandling())
                tr.setSelected(true);
            else
                fa.setSelected(true);
            src.getItems().addAll(tr, fa);
            sourc.getMenus().add(src);
            grid.add(sourc, 1, 6);
            foo = new Label("Update space(millis):");
            grid.add(foo, 2, 6);
            boo = new TextField(Long.toString(currentStation.reUpdateSpace()));
            grid.add(boo, 3, 6);
            textfields.add(boo);
            grid.add(modifyStation, 0, 7);
            modifyStation.setDefaultButton(true);
            root.setCenter(grid);
            t.setOnAction((ActionEvent et) ->
                    automaticUpdate = t.isSelected());
            tr.setOnAction((ActionEvent et) ->
                    automaticHandling = t.isSelected());
        });
        searchCharging.setOnAction((ActionEvent e) -> {
            cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t = new Text("ChargingEvent Search");
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
        searchDisCharging.setOnAction((ActionEvent e) -> {
            cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t = new Text("Discharging Event Search");
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
        searchExchangeEvent.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t = new Text("Exchange Event Search");
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
        newStation.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("Charging Station Creation");
            TextField boo;
            Label foo;
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Name of charging station:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Number of fast chargers:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Number of slow chargers:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Number of exchange slots:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Number of discharging slots:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Number of parking slots:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Energy sources:");
            grid.add(foo, 0, 4);
            MenuBar sourc = new MenuBar();
            sourc.setMaxWidth(100);
            Menu src = new Menu("Energies");
            RadioMenuItem sol = new RadioMenuItem("Solar");
            RadioMenuItem win = new RadioMenuItem("Wind");
            RadioMenuItem wav = new RadioMenuItem("Wave");
            RadioMenuItem hydr = new RadioMenuItem("Hydroelectric");
            RadioMenuItem non = new RadioMenuItem("Non-renewable");
            RadioMenuItem geo = new RadioMenuItem("Geothermal");
            src.getItems().addAll(sol, win, wav, hydr, non, geo);
            sourc.getMenus().add(src);
            grid.add(sourc, 1, 4);
            foo = new Label("Charging fee per unit:");
            grid.add(foo, 2, 4);
            boo = new TextField("1");
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit:");
            grid.add(foo, 0, 5);
            boo = new TextField("1");
            grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Battery exchange fee per unit:");
            grid.add(foo, 2, 5);
            boo = new TextField("1");
            grid.add(boo, 3, 5);
            textfields.add(boo);
            foo = new Label("Inductive charging fee per unit:");
            grid.add(foo, 0, 6);
            boo = new TextField("1");
            grid.add(boo, 1, 6);
            textfields.add(boo);
            foo = new Label("Fast charging ratio:");
            grid.add(foo, 2, 6);
            boo = new TextField("0.01");
            grid.add(boo, 3, 6);
            textfields.add(boo);
            foo = new Label("Slow charging ratio:");
            grid.add(foo, 0, 7);
            boo = new TextField("0.001");
            grid.add(boo, 1, 7);
            textfields.add(boo);
            foo = new Label("Discharging ratio:");
            grid.add(foo, 2, 7);
            boo = new TextField("0.01");
            grid.add(boo, 3, 7);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio:");
            grid.add(foo, 0, 8);
            boo = new TextField("0.01");
            grid.add(boo, 1, 8);
            textfields.add(boo);
            foo = new Label("Automatic energy update:");
            grid.add(foo, 2, 8);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem t = new RadioMenuItem("True");
            RadioMenuItem f = new RadioMenuItem("False");
            r.getToggles().addAll(t, f);
            f.setSelected(true);
            src.getItems().addAll(t, f);
            sourc.getMenus().add(src);
            grid.add(sourc, 3, 8);
            foo = new Label("Automatic queue handling:");
            grid.add(foo, 0, 9);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            r = new ToggleGroup();
            RadioMenuItem tr = new RadioMenuItem("True");
            RadioMenuItem fa = new RadioMenuItem("False");
            r.getToggles().addAll(tr, fa);
            tr.setSelected(true);
            src.getItems().addAll(tr, fa);
            sourc.getMenus().add(src);
            grid.add(sourc, 1, 9);
            foo = new Label("Update space(millis):");
            grid.add(foo, 2, 9);
            boo = new TextField("1000");
            grid.add(boo, 3, 9);
            textfields.add(boo);
            grid.add(stationCreation, 0, 10);
            stationCreation.setDefaultButton(true);
            root.setCenter(grid);
            t.setOnAction((ActionEvent et) ->
                    automaticUpdate = t.isSelected());
            tr.setOnAction((ActionEvent et) ->
                    automaticHandling = t.isSelected());
            sol.setOnAction((ActionEvent et) -> {
                if (sol.isSelected())
                    energies.add("solar");
                else
                    energies.remove("solar");
            });
            win.setOnAction((ActionEvent et) -> {
                if (win.isSelected())
                    energies.add("wind");
                else
                    energies.remove("wind");
            });
            wav.setOnAction((ActionEvent et) -> {
                if (wav.isSelected())
                    energies.add("wave");
                else
                    energies.remove("wave");
            });
            hydr.setOnAction((ActionEvent et) -> {
                if (hydr.isSelected())
                    energies.add("hydroelectric");
                else
                    energies.remove("hydroelectric");
            });
            geo.setOnAction((ActionEvent et) -> {
                if (geo.isSelected())
                    energies.add("geothermal");
                else
                    energies.remove("geothermal");
            });
            non.setOnAction((ActionEvent et) -> {
                if (non.isSelected())
                    energies.add("nonrenewable");
                else
                    energies.remove("nonrenewable");
            });
        });
        newCharger.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            cleanScreen();
            grid.setMaxSize(500, 300);
            t = new Text("Charger Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Kind of charger:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            grid.add(chargerCreation, 0, 3);
            chargerCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        newDisCharger.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            DisCharger ch;
            ch = new DisCharger(currentStation);
            currentStation.addDisCharger(ch);
        });
        newExchangeHandler.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            ExchangeHandler ch;
            ch = new ExchangeHandler(currentStation);
            currentStation.addExchangeHandler(ch);
        });
        newParkingSlot.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            ParkingSlot ch;
            ch = new ParkingSlot(currentStation);
            currentStation.addParkingSlot(ch);
        });
        charging.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("ChargingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Kind of charging:");
            grid.add(foo, 2, 4);
            boo = new TextField();
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Money:");
            grid.add(foo, 0, 5);
            boo = new TextField();
            grid.add(boo, 1, 5);
            textfields.add(boo);
            grid.add(chargingEventCreation, 0, 6);
            chargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        discharging.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("DisChargingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            grid.add(disChargingEventCreation, 0, 5);
            disChargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        exchange.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("Battery Exchange Event Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            grid.add(exchangeEventCreation, 0, 5);
            exchangeEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        parking.setOnAction((ActionEvent e) ->
        {
            if(!stationCheck())
                return;
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("ParkingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Parking time:");
            grid.add(foo, 2, 4);
            boo = new TextField();
            grid.add(boo, 3, 4);
            textfields.add(boo);
            grid.add(parkingEventCreation, 0, 5);
            parkingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        totalEnergy.setOnAction((ActionEvent e) -> {
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("Total Energy");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Total energy:");
            grid.add(foo, 0, 1);
            boo = new TextField(Double.toString(currentStation.reTotalEnergy()));
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Solar energy:");
            grid.add(foo, 2, 1);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("solar")));
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Wind energy:");
            grid.add(foo, 0, 2);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("wind")));
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Wave energy:");
            grid.add(foo, 2, 2);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("wave")));
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Hydroelectric energy:");
            grid.add(foo, 0, 3);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("hydroelectric")));
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Non-renewable:");
            grid.add(foo, 2, 3);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("nonrenewable")));
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Geothermal energy:");
            grid.add(foo, 0, 4);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("geothermal")));
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Discharging energy:");
            grid.add(foo, 2, 4);
            boo = new TextField(Double.toString(currentStation.reSpecificAmount("discharging")));
            grid.add(boo, 3, 4);
            textfields.add(boo);
            root.setCenter(grid);
        });

        //Buttons
        chargerCreation.setOnAction((ActionEvent e) ->
        {
            Charger ch;
            ch = new Charger(currentStation, textfields.get(0).getText());
            currentStation.addCharger(ch);
            completionMessage("Charger");
        });
        stationCreation.setOnAction((ActionEvent e) ->
        {
            if (!fieldCompletionCheck())
                return;
            if (energies.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
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
                    Double.parseDouble(textfields.get(14).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers.");
                alert.showAndWait();
                return;
            }
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
            st.setUpdateSpace(Integer.parseInt(textfields.get(14).getText()));
            st.setUpdateMode(automaticUpdate);
            st.setAutomaticQueueHandling(automaticHandling);
            if (textfields.get(1).getText() != null) {
                int len = Integer.parseInt(textfields.get(1).getText());
                Charger ch;
                for (int i = 0; i < len; i++) {
                    ch = new Charger(st, "fast");
                    st.addCharger(ch);
                }
            }
            if (textfields.get(2).getText() != null) {
                int len = Integer.parseInt(textfields.get(2).getText());
                Charger ch;
                for (int i = 0; i < len; i++) {
                    ch = new Charger(st, "slow");
                    st.addCharger(ch);
                }
            }
            if (textfields.get(3).getText() != null) {
                int len = Integer.parseInt(textfields.get(3).getText());
                ExchangeHandler exch;
                for (int i = 0; i < len; i++) {
                    exch = new ExchangeHandler(st);
                    st.addExchangeHandler(exch);
                }
            }
            if (textfields.get(4).getText() != null) {
                int len = Integer.parseInt(textfields.get(4).getText());
                DisCharger dsch;
                for (int i = 0; i < len; i++) {
                    dsch = new DisCharger(st);
                    st.addDisCharger(dsch);
                }
            }
            if (textfields.get(5).getText() != null) {
                int len = Integer.parseInt(textfields.get(5).getText());
                ParkingSlot ps;
                for (int i = 0; i < len; i++) {
                    ps = new ParkingSlot(st);
                    st.addParkingSlot(ps);
                }
            }
            EnergySource en;
            for (String enr : energies) {
                if (Objects.equals(enr, "solar")) {
                    en = new Solar(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "geothermal")) {
                    en = new Geothermal(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "wind")) {
                    en = new Wind(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "wave")) {
                    en = new Wave(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "nonrenewable")) {
                    en = new NonRenewable(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "hydroelectric")) {
                    en = new HydroElectric(st);
                    st.addEnergySource(en);
                }
            }
            stations.add(st);
            cs = new RadioMenuItem(st.reName());
            group.getToggles().add(cs);
            s.getItems().add(cs);
            if(s.getItems().size() == 1)
                cs.setSelected(true);
            completionMessage("ChargingStation");
        });
        modifyStation.setOnAction((ActionEvent e) -> {
            if (!fieldCompletionCheck())
                return;
            currentStation.setUnitPrice(Double.parseDouble(textfields.get(1).getText()));
            currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(2).getText()));
            currentStation.setExchangePrice(Double.parseDouble(textfields.get(3).getText()));
            currentStation.setInductivePrice((Double.parseDouble(textfields.get(4).getText())));
            currentStation.setChargingRatioFast((Double.parseDouble(textfields.get(5).getText())));
            currentStation.setChargingRatioSlow((Double.parseDouble(textfields.get(6).getText())));
            currentStation.setDisChargingRatio((Double.parseDouble(textfields.get(7).getText())));
            currentStation.setInductiveChargingRatio((Double.parseDouble(textfields.get(8).getText())));
            currentStation.setUpdateSpace((Integer.parseInt(textfields.get(9).getText())));
            currentStation.setAutomaticQueueHandling(automaticHandling);
            currentStation.setUpdateMode(automaticUpdate);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("The station was modified successfully.");
            alert.showAndWait();
        });
        chargingEventCreation.setOnAction((ActionEvent e) -> {
            if (!fieldCompletionCheck())
                return;
            if (!textfields.get(7).getText().equals("fast") && !textfields.get(7).getText().equals("slow")) {
                System.out.println(textfields.get(7).getText());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please put \"slow\" for a slow charging, or \"fast\" for a fast charging.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(8).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(5).getText()) == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The asking amount cannot be negative or zero");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(5).getText()) > (Double.parseDouble(textfields.get(3).getText()) - Double.parseDouble(textfields.get(4).getText()))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The asking amount of energy cannot be greater than the remaining capacity.");
                alert.showAndWait();
                return;
            }
            ChargingEvent ch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(4).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            if (!Objects.equals(textfields.get(5).getText(), "0")) {
                ch = new ChargingEvent(currentStation, el, Double.parseDouble(textfields.get(5).getText()), textfields.get(7).getText());
                ch.preProcessing();
                ch.execution();
            }
            else if (!Objects.equals(textfields.get(8).getText(), "0")) {
                ch = new ChargingEvent(currentStation, el, textfields.get(7).getText(), Double.parseDouble(textfields.get(8).getText()));
                ch.preProcessing();
                ch.execution();
            }
            completionMessage("ChargingEvent");
        });
        disChargingEventCreation.setOnAction((ActionEvent e) ->
        {
            if (!fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(5).getText()) > (Double.parseDouble(textfields.get(4).getText()))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The given amount of energy cannot be greater than the remaining amount.");
                alert.showAndWait();
                return;
            }
            DisChargingEvent dsch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(4).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            if (!Objects.equals(textfields.get(5).getText(), "0")) {
                dsch = new DisChargingEvent(currentStation, el, Double.parseDouble(textfields.get(5).getText()));
                dsch.preProcessing();
                dsch.execution();
            }
            completionMessage("DisChargingEvent");
        });
        exchangeEventCreation.setOnAction((ActionEvent e) -> {
            if (!fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            ChargingEvent ch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(4).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            ch = new ChargingEvent(currentStation, el);
            ch.preProcessing();
            ch.execution();
            completionMessage("ChargingEvent");
        });
        parkingEventCreation.setOnAction((ActionEvent e) -> {
            if (!fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(7).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            ParkingEvent ch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(4).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            if (!Objects.equals(textfields.get(5).getText(), "0")) {
                ch = new ParkingEvent(currentStation, el, Long.parseLong(textfields.get(7).getText()), Double.parseDouble(textfields.get(5).getText()));
                ch.preProcessing();
                ch.execution();
            } else if (!Objects.equals(textfields.get(7).getText(), "0")) {
                ch = new ParkingEvent(currentStation, el, Long.parseLong(textfields.get(7).getText()));
                ch.preProcessing();
                ch.execution();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please select at least a positive parking time.");
                alert.showAndWait();
                return;
            }
            completionMessage("ParkingEvent");
        });
    }

    private static boolean fieldCompletionCheck()
    {
        for(TextField f: textfields) {
            if (f.getText().isEmpty()||f.getText().equals(" ") || f.getText().equals("  ") || f.getText().equals("   ") || f.getText().equals("    ")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields. Numbers have to be >=0.");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }
    private static boolean stationCheck()
    {
        if(stations.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Caution");
            alert.setHeaderText(null);
            alert.setContentText("Please add a ChargingStation");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    private static void cleanScreen()
    {
        grid.getChildren().clear();
        root.setCenter(null);
        root.setLeft(null);
        root.setRight(null);
        root.setBottom(null);
        textfields.clear();
    }
    private static void energyAlert(String energySource)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Caution");
        alert.setHeaderText(null);
        alert.setContentText(energySource + " energy is already an option.");
        alert.showAndWait();
    }
    private static void notEnergyAlert(String energySource)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Caution");
        alert.setHeaderText(null);
        alert.setContentText(energySource + " energy is not an option.");
        alert.showAndWait();
    }
    private static void completionMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Caution");
        alert.setHeaderText(null);
        alert.setContentText("The " + " was created successfully.");
        alert.showAndWait();
    }
}