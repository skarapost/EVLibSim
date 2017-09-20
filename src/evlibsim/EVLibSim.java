package evlibsim;

import EVLib.EV.Battery;
import EVLib.EV.Driver;
import EVLib.EV.ElectricVehicle;
import EVLib.Events.ChargingEvent;
import EVLib.Events.DisChargingEvent;
import EVLib.Events.ParkingEvent;
import EVLib.Sources.*;
import EVLib.Station.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.*;
import java.util.*;

import static evlibsim.MenuStation.cs;

public class EVLibSim extends Application {

    static final BorderPane root = new BorderPane();
    static final GridPane grid = new GridPane();
    static final ScrollPane scroll = new ScrollPane();
    private static final GridPane stationGrid = new GridPane();
    static final ArrayList<TextField> textfields = new ArrayList<>();
    static final ArrayList<ChargingStation> stations = new ArrayList<>();
    static ChargingStation currentStation;
    static final ArrayList<String> energies = new ArrayList<>();
    private static MenuBar menuBar = new MenuBar();
    private static final Button newChargingStation = new Button("Station");
    private static final Button newEvent = new Button("Event");
    private static final Button newEnergy = new Button("Energy");
    static final ToggleGroup group = new ToggleGroup();
    private static final MenuItem startScreen = new MenuItem("Start Screen");
    static final Menu s = new Menu("Stations");
    private static final MenuItem load = new MenuItem("Load");
    private static final MenuItem save = new MenuItem("Save");
    private static final MenuItem saveAs = new MenuItem("Save as...");
    private static final MenuItem exitMenuItem = new MenuItem("Exit");
    private static final MenuItem about = new MenuItem("About");
    static final Label stationName = new Label();
    private static final Label energyAmount = new Label();
    static final Label totalChargers = new Label();
    static final Label totalDisChargers = new Label();
    static final Label totalExchange = new Label();
    static final Label totalParkingSlots = new Label();
    private static final VBox tBox = new VBox();
    private static String energy;
    private static TextArea ta = new TextArea();
    private static Button bt1 = new Button("ChargingEvent");
    private static Button bt2 = new Button("DisChargingEvent");
    private static Button bt3 = new Button("ExchangeEvent");
    private static Button bt4 = new Button("ParkingEvent");
    private static Button bt5 = new Button("New Amounts");
    private static Button bt6 = new Button("New EnergySource");
    private static Button bt7 = new Button("Delete EnergySource");
    private File f = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EVLibSim");
        Scene scene = new Scene(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        Console console = new Console(ta);
        ta.setEditable(false);
        ta.setMaxSize(220, 200);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);

        BorderPane.setAlignment(tBox, Pos.CENTER_RIGHT);
        tBox.getChildren().addAll(Overview.createOverviewMenu(), History.createSearchMenu(), ta);
        tBox.setSpacing(50);
        tBox.setAlignment(Pos.CENTER_RIGHT);

        root.setTop(menuBar);
        root.setRight(tBox);

        Menu file = new Menu("File");
        exitMenuItem.setOnAction(e -> {
            for(ChargingStation st: stations) {
                for (Charger ch : st.getChargers())
                    if (ch.getChargingEvent() != null)
                        ch.stopCharger();
                for (DisCharger ch : st.getDisChargers())
                    if (ch.getDisChargingEvent() != null)
                        ch.stopDisCharger();
                for (ExchangeHandler h : st.getExchangeHandlers())
                    if (h.getChargingEvent() != null)
                        h.stopExchangeHandler();
                for (ParkingSlot h : st.getParkingSlots())
                    if (h.getParkingEvent() != null)
                        h.stopParkingSlot();
            }
            Platform.exit();
        });
        file.getItems().addAll(startScreen, new SeparatorMenuItem(), load, save, saveAs, new SeparatorMenuItem(), s, about, exitMenuItem);
        menuBar.getMenus().addAll(file, View.createViewMenu(), MenuStation.createStationMenu(), Event.createEventMenu(), Energy.createEnergyMenu());
        scene.getStylesheets().add(EVLibSim.class.getResource("EVLibSim.css").toExternalForm());
        grid.getStyleClass().add("grid");
        stationGrid.getStyleClass().add("stationGrid");

        newChargingStation.setPrefSize(220, 60);
        newEvent.setPrefSize(220, 60);
        newEnergy.setPrefSize(220, 60);

        stationGrid.add(stationName, 0, 0);
        stationGrid.add(energyAmount, 1, 0);
        stationGrid.add(totalChargers, 2, 0);
        stationGrid.add(totalDisChargers, 3, 0);
        stationGrid.add(totalExchange, 4, 0);
        stationGrid.add(totalParkingSlots, 5, 0);

        root.setBottom(stationGrid);

        startScreen.setOnAction((ActionEvent e) -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(400, 380);
            grid.setMinSize(400, 380);
            grid.add(newChargingStation, 0, 0);
            grid.add(newEvent, 0, 1);
            grid.add(newEnergy, 0, 2);
            grid.setAlignment(Pos.CENTER);
            root.setCenter(grid);
        });

        startScreen.fire();

        group.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (group.getSelectedToggle() != null) {
                newValue.setSelected(true);
                RadioMenuItem rmi = (RadioMenuItem) group.getSelectedToggle();
                String name = rmi.getText();
                for (ChargingStation cs : stations)
                    if (Objects.equals(cs.getName(), name)) {
                        currentStation = cs;
                        stationName.setText("ChargingStation Name: " + currentStation.getName());
                        energyAmount.setText("Total Energy: " + currentStation.getTotalEnergy());
                        totalChargers.setText("Number of Chargers: " + currentStation.getChargers().length);
                        totalDisChargers.setText("Number of DisChargers: " + currentStation.getDisChargers().length);
                        totalExchange.setText("Number of ExchangeHandlers: " + currentStation.getExchangeHandlers().length);
                        totalParkingSlots.setText("Number of ParkingSlots: " + currentStation.getParkingSlots().length);
                    }
            }
        });

        newChargingStation.setOnAction(e -> MenuStation.newChargingStationMI.fire());

        newEvent.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            bt1.setPrefSize(220, 60);
            bt2.setPrefSize(220, 60);
            bt3.setPrefSize(220, 60);
            bt4.setPrefSize(220, 60);
            grid.setMaxSize(400, 400);
            grid.setMinSize(400, 400);
            grid.add(bt1, 0, 0);
            grid.add(bt2, 0, 1);
            grid.add(bt3, 0, 2);
            grid.add(bt4, 0, 3);
            root.setCenter(grid);
        });

        bt1.setOnAction(e -> Event.charging.fire());
        bt2.setOnAction(e -> Event.discharging.fire());
        bt3.setOnAction(e -> Event.exchange.fire());
        bt4.setOnAction(e -> Event.parking.fire());
        bt5.setOnAction(e -> Energy.newEnergyPackages.fire());
        bt6.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(320, 280);
            grid.setMinSize(320, 280);
            TextField foo;
            grid.add(new Label("Energy sources: "), 0, 0);
            MenuBar sourc = new MenuBar();
            ToggleGroup f = new ToggleGroup();
            sourc.setId("menubar");
            sourc.setMaxWidth(100);
            Menu src = new Menu("Choice");
            RadioMenuItem sol = new RadioMenuItem("Solar");
            RadioMenuItem win = new RadioMenuItem("Wind");
            RadioMenuItem wav = new RadioMenuItem("Wave");
            RadioMenuItem hyd = new RadioMenuItem("Hydroelectric");
            RadioMenuItem non = new RadioMenuItem("Non-renewable");
            RadioMenuItem geo = new RadioMenuItem("Geothermal");
            src.getItems().addAll(sol, win, wav, hyd, non, geo);
            sourc.getMenus().add(src);
            sol.setSelected(true);
            grid.add(sourc, 1, 0);
            grid.add(new Label("Initial Amount:"), 0, 1);
            foo = new TextField();
            grid.add(foo, 1, 1);
            textfields.add(foo);
            Button creation = new Button("Creation");
            f.getToggles().addAll(sol, win, wav, hyd, geo, non);
            grid.add(creation, 0, 2);
            creation.setDefaultButton(true);
            sol.setOnAction((ActionEvent et) -> {
                if (sol.isSelected())
                    energy = "solar";
            });
            win.setOnAction((ActionEvent et) -> {
                if (win.isSelected())
                    energy = "wind";
            });
            wav.setOnAction((ActionEvent et) -> {
                if (wav.isSelected())
                    energy = "wave";
            });
            hyd.setOnAction((ActionEvent et) -> {
                if (hyd.isSelected())
                    energy = "hydroelectric";
            });
            geo.setOnAction((ActionEvent et) -> {
                if (geo.isSelected())
                    energy = "geothermal";
            });
            non.setOnAction((ActionEvent et) -> {
                if (non.isSelected())
                    energy = "nonrenewable";
            });
            root.setCenter(grid);
            creation.setOnAction(et -> {
                if(Maintenance.fieldCompletionCheck())
                    return;
                switch (energy)
                {
                    case "Solar":
                        if(!Maintenance.checkEnergy("solar")) {
                            currentStation.addEnergySource(new Solar());
                            if(Double.parseDouble(textfields.get(0).getText())>0)
                                currentStation.getEnergySource("solar").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            Maintenance.completionMessage("energy addition");
                        }
                        else
                            Maintenance.energyAlert("Solar");
                        break;
                    case "Geothermal":
                        if(!Maintenance.checkEnergy("geothermal")) {
                            currentStation.addEnergySource(new Geothermal());
                            if(Double.parseDouble(textfields.get(0).getText())>0)
                                currentStation.getEnergySource("geothermal").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            Maintenance.completionMessage("energy addition");
                        }
                        else
                            Maintenance.energyAlert("Geothermal");
                        break;
                    case "Wind":
                        if(!Maintenance.checkEnergy("wind")) {
                            currentStation.addEnergySource(new Wind());
                            if(Double.parseDouble(textfields.get(0).getText())>0)
                                currentStation.getEnergySource("wind").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            Maintenance.completionMessage("energy addition");
                        }
                        else
                            Maintenance.energyAlert("Wind");
                        break;
                    case "Wave":
                        if(!Maintenance.checkEnergy("wave")) {
                            currentStation.addEnergySource(new Wave());
                            if(Double.parseDouble(textfields.get(0).getText())>0)
                                currentStation.getEnergySource("wave").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            Maintenance.completionMessage("energy addition");
                        }
                        else
                            Maintenance.energyAlert("Wave");
                        break;
                    case "Hydro-Electric":
                        if(!Maintenance.checkEnergy("hydroelectric")) {
                            currentStation.addEnergySource(new HydroElectric());
                            if(Double.parseDouble(textfields.get(0).getText())>0)
                                currentStation.getEnergySource("hydroelectric").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            Maintenance.completionMessage("energy addition");
                        }
                        else
                            Maintenance.energyAlert("Hydro-Electric");
                        break;
                    case "Non-Renewable":
                        if(!Maintenance.checkEnergy("nonrenewable")) {
                            currentStation.addEnergySource(new NonRenewable());
                            if(Double.parseDouble(textfields.get(0).getText())>0)
                                currentStation.getEnergySource("nonrenewable").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            Maintenance.completionMessage("energy addition");
                        }
                        else
                            Maintenance.energyAlert("Non-Renewable");
                        break;
                }
            });
        });

        bt7.setOnAction(e -> {
            List<String> choices = new ArrayList<>();
            choices.add("Solar");
            choices.add("Wind");
            choices.add("Wave");
            choices.add("Geothermal");
            choices.add("Non-Renewable");
            choices.add("Hydro-Electric");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Solar", choices);
            dialog.setTitle("Information");
            dialog.setHeaderText(null);
            dialog.setContentText("EnergySource: ");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent())
                switch (result.get())
                {
                    case "Solar":
                        if(Maintenance.checkEnergy("solar")) {
                            currentStation.deleteEnergySource(currentStation.getEnergySource("solar"));
                            Maintenance.completionMessage("energy removal");
                        }
                        else
                            Maintenance.notEnergyAlert("Solar");
                        break;
                    case "Geothermal":
                        if(Maintenance.checkEnergy("geothermal")) {
                            currentStation.deleteEnergySource(currentStation.getEnergySource("geothermal"));
                            Maintenance.completionMessage("energy removal");
                        }
                        else
                            Maintenance.notEnergyAlert("Geothermal");
                        break;
                    case "Wind":
                        if(Maintenance.checkEnergy("wind")) {
                            currentStation.deleteEnergySource(currentStation.getEnergySource("wind"));
                            Maintenance.completionMessage("energy removal");
                        }
                        else
                            Maintenance.notEnergyAlert("Wind");
                        break;
                    case "Wave":
                        if(Maintenance.checkEnergy("wave")) {
                            currentStation.deleteEnergySource(currentStation.getEnergySource("wave"));
                            Maintenance.completionMessage("energy removal");
                        }
                        else
                            Maintenance.notEnergyAlert("Wave");
                        break;
                    case "Hydro-Electric":
                        if(Maintenance.checkEnergy("hydroelectric")) {
                            currentStation.deleteEnergySource(currentStation.getEnergySource("hydroelectric"));
                            Maintenance.completionMessage("energy removal");
                        }
                        else
                            Maintenance.notEnergyAlert("Hydro-Electric");
                        break;
                    case "Non-Renewable":
                        if(Maintenance.checkEnergy("nonrenewable")) {
                            currentStation.deleteEnergySource(currentStation.getEnergySource("nonrenewable"));
                            Maintenance.completionMessage("energy removal");
                        }
                        else
                            Maintenance.notEnergyAlert("Non-Renewable");
                        break;
                }
        });

        newEnergy.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            bt5.setPrefSize(220, 60);
            bt6.setPrefSize(220, 60);
            bt7.setPrefSize(220, 60);
            grid.setMaxSize(400, 400);
            grid.setMinSize(400, 400);
            grid.add(bt5, 0, 0);
            grid.add(bt6, 0, 1);
            grid.add(bt7, 0, 2);
            root.setCenter(grid);
        });

        about.setOnAction(e ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Creator: Karapostolakis Sotirios\nemail: skarapos@outlook.com\nYear: 2017");
            alert.showAndWait();
        });

        saveAs.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null)
                try {
                    saveFile(selectedFile);
                    f = selectedFile;
                    primaryStage.setTitle("EVLibSim - [" + f.getPath() + "]");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
        });

        save.setOnAction(e -> {
            if(f == null)
                saveAs.fire();
            else
                try {
                    saveFile(f);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
        });

        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if(selectedFile != null)
                try {
                    openFile(selectedFile);
                    f = selectedFile;
                    primaryStage.setTitle("EVLibSim - [" + selectedFile.getPath() + "]");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        });


        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lsThreads = new Thread[noThreads];
            currentGroup.enumerate(lsThreads);
            for (int i=0; i<noThreads; i++)
                if(lsThreads[i].getName().contains("Charger")||lsThreads[i].getName().contains("DisCharger")||lsThreads[i].getName().contains("ExchangeHandler")||lsThreads[i].getName().contains("ParkingSlot"))
                {
                    e.consume();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.setContentText("There are still some tasks running.\nDo you want to close the application?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent())
                        if(result.get() == ButtonType.OK) {
                            for(ChargingStation st: stations) {
                                for (Charger ch : st.getChargers())
                                    if (ch.getChargingEvent() != null)
                                        ch.stopCharger();
                                for (DisCharger ch : st.getDisChargers())
                                    if (ch.getDisChargingEvent() != null)
                                        ch.stopDisCharger();
                                for (ExchangeHandler h : st.getExchangeHandlers())
                                    if (h.getChargingEvent() != null)
                                        h.stopExchangeHandler();
                                for (ParkingSlot h : st.getParkingSlots())
                                    if (h.getParkingEvent() != null)
                                        h.stopParkingSlot();
                            }
                            Platform.exit();
                        }
                        else if(result.get() == ButtonType.CANCEL) {
                            alert.hide();
                            break;
                        }
                }

        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            if(currentStation == null) {
                stationName.setText("ChargingStation Name: -");
                energyAmount.setText("Total Energy: -");
                totalChargers.setText("Number of Chargers: -");
                totalDisChargers.setText("Number of DisChargers: -");
                totalExchange.setText("Number of ExchangeHandlers: -");
                totalParkingSlots.setText("Number of ParkingSlots: -");
            }
            else
            {
                stationName.setText("ChargingStation Name: " + currentStation.getName());
                energyAmount.setText("Total Energy: " + currentStation.getTotalEnergy());
                totalChargers.setText("Number of Chargers: " + currentStation.getChargers().length);
                totalDisChargers.setText("Number of DisChargers: " + currentStation.getDisChargers().length);
                totalExchange.setText("Number of ExchangeHandlers: " + currentStation.getExchangeHandlers().length);
                totalParkingSlots.setText("Number of ParkingSlots: " + currentStation.getParkingSlots().length);
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    private void openFile(File selectedFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(selectedFile));
        String line;
        String[] tokens;
        ChargingEvent event;
        DisChargingEvent disEvent;
        ParkingEvent event1;
        ChargingStation st;
        Battery bat;
        ElectricVehicle vehicle;
        Driver driver;
        int counter = 0;
        stations.clear();
        s.getItems().clear();
        currentStation = null;
        group.getToggles().clear();
        ChargingEvent.chargingLog.clear();
        DisChargingEvent.dischargingLog.clear();
        ChargingEvent.exchangeLog.clear();
        ParkingEvent.parkLog.clear();
        //Prophets of Rage - living on 110
        while ((line = in.readLine()) != null)
        {
            tokens = line.split(",");
            if(tokens[0].equals("st"))
            {
                st = new ChargingStation(tokens[2]);
                st.setId(Integer.parseInt(tokens[1]));
                for(int i=0; i < Integer.parseInt(tokens[3]); i++)
                    st.addCharger(new Charger(st, "fast"));
                for(int i=0; i < Integer.parseInt(tokens[4]); i++)
                    st.addCharger(new Charger(st, "slow"));
                for(int i=0; i < Integer.parseInt(tokens[5]); i++)
                    st.addDisCharger(new DisCharger(st));
                for(int i=0; i < Integer.parseInt(tokens[6]); i++)
                    st.addExchangeHandler(new ExchangeHandler(st));
                for(int i=0; i < Integer.parseInt(tokens[7]); i++)
                    st.addParkingSlot(new ParkingSlot(st));
                st.setChargingRatioSlow(Double.parseDouble(tokens[8]));
                st.setChargingRatioFast(Double.parseDouble(tokens[9]));
                st.setDisChargingRatio(Double.parseDouble(tokens[10]));
                st.setInductiveChargingRatio(Double.parseDouble(tokens[11]));
                st.setUnitPrice(Double.parseDouble(tokens[12]));
                st.setDisUnitPrice(Double.parseDouble(tokens[13]));
                st.setInductivePrice(Double.parseDouble(tokens[14]));
                st.setExchangePrice(Double.parseDouble(tokens[15]));
                st.setAutomaticUpdateMode(Boolean.parseBoolean(tokens[17]));
                st.setUpdateSpace(Integer.parseInt(tokens[16]));
                st.setTimeofExchange(Long.parseLong(tokens[18]));
                st.setAutomaticQueueHandling(Boolean.parseBoolean(tokens[19]));
                st.setDeamon(Boolean.parseBoolean(tokens[20]));
                counter = 22;
                while(!tokens[counter].equals("Batteries"))
                {
                    switch (tokens[counter]) {
                        case "solar":
                            st.addEnergySource(new Solar());
                            break;
                        case "wind":
                            st.addEnergySource(new Wind());
                            break;
                        case "wave":
                            st.addEnergySource(new Wave());
                            break;
                        case "hydroelectric":
                            st.addEnergySource(new HydroElectric());
                            break;
                        case "geothermal":
                            st.addEnergySource(new Geothermal());
                            break;
                        case "nonrenewable":
                            st.addEnergySource(new NonRenewable());
                            break;
                    }
                    st.setSpecificAmount(tokens[counter], Double.parseDouble(tokens[counter + 1]));
                    counter += 2;
                }
                while((counter + 1) < tokens.length)
                {
                    bat = new Battery(Double.parseDouble(tokens[counter + 2]), Double.parseDouble(tokens[counter + 3]));
                    bat.setId(Integer.parseInt(tokens[counter + 1]));
                    bat.setNumberOfChargings(Integer.parseInt(tokens[counter + 4]));
                    bat.setMaxNumberOfChargings(Integer.parseInt(tokens[counter + 5]));
                    bat.setActive(Boolean.parseBoolean(tokens[counter + 6]));
                    st.joinBattery(bat);
                    counter += 6;
                }
                stations.add(st);
                cs = new RadioMenuItem(st.getName());
                group.getToggles().add(cs);
                s.getItems().add(cs);
                if(s.getItems().size() == 1)
                    cs.setSelected(true);
            }
            else if (tokens[0].equals("ch"))
            {
                driver = new Driver(tokens[12]);
                driver.setDebt(Double.parseDouble(tokens[13]));
                driver.setProfit(Double.parseDouble(tokens[14]));
                driver.setId(Integer.parseInt(tokens[11]));
                vehicle = new ElectricVehicle(tokens[4]);
                vehicle.setDriver(driver);
                vehicle.setId(Integer.parseInt(tokens[3]));
                bat = new Battery(Double.parseDouble(tokens[6]), Double.parseDouble(tokens[7]));
                bat.setId(5);
                bat.setNumberOfChargings(Integer.parseInt(tokens[8]));
                bat.setMaxNumberOfChargings(Integer.parseInt(tokens[9]));
                bat.setActive(Boolean.parseBoolean(tokens[10]));
                vehicle.setBattery(bat);
                event = new ChargingEvent(searchStation(tokens[2]) ,vehicle, Double.parseDouble(tokens[15]), tokens[17]);
                event.setId(Integer.parseInt(tokens[1]));
                event.setEnergyToBeReceived(Double.parseDouble(tokens[16]));
                event.setCondition("finished");
                event.setChargingTime(Long.parseLong(tokens[18]));
                event.setMaxWaitingTime(Long.parseLong(tokens[19]));
                event.setWaitingTime(Long.parseLong(tokens[20]));
                event.setCost(Double.parseDouble(tokens[21]));
                ChargingEvent.chargingLog.add(event);
            }
            else if (tokens[0].equals("dis"))
            {
                driver = new Driver(tokens[12]);
                driver.setDebt(Double.parseDouble(tokens[13]));
                driver.setProfit(Double.parseDouble(tokens[14]));
                driver.setId(Integer.parseInt(tokens[11]));
                vehicle = new ElectricVehicle(tokens[4]);
                vehicle.setDriver(driver);
                vehicle.setId(Integer.parseInt(tokens[3]));
                bat = new Battery(Double.parseDouble(tokens[6]), Double.parseDouble(tokens[7]));
                bat.setId(5);
                bat.setNumberOfChargings(Integer.parseInt(tokens[8]));
                bat.setMaxNumberOfChargings(Integer.parseInt(tokens[9]));
                bat.setActive(Boolean.parseBoolean(tokens[10]));
                vehicle.setBattery(bat);
                disEvent = new DisChargingEvent(searchStation(tokens[2]) ,vehicle, Double.parseDouble(tokens[15]));
                disEvent.setId(Integer.parseInt(tokens[1]));
                disEvent.setCondition("finished");
                disEvent.setDisChargingTime(Long.parseLong(tokens[16]));
                disEvent.setMaxWaitingTime(Long.parseLong(tokens[17]));
                disEvent.setWaitingTime(Long.parseLong(tokens[18]));
                disEvent.setProfit(Double.parseDouble(tokens[19]));
                DisChargingEvent.dischargingLog.add(disEvent);
            }
            else if (tokens[0].equals("ex"))
            {
                driver = new Driver(tokens[12]);
                driver.setDebt(Double.parseDouble(tokens[13]));
                driver.setProfit(Double.parseDouble(tokens[14]));
                driver.setId(Integer.parseInt(tokens[11]));
                vehicle = new ElectricVehicle(tokens[4]);
                vehicle.setDriver(driver);
                vehicle.setId(Integer.parseInt(tokens[3]));
                bat = new Battery(Double.parseDouble(tokens[6]), Double.parseDouble(tokens[7]));
                bat.setId(5);
                bat.setNumberOfChargings(Integer.parseInt(tokens[8]));
                bat.setMaxNumberOfChargings(Integer.parseInt(tokens[9]));
                bat.setActive(Boolean.parseBoolean(tokens[10]));
                vehicle.setBattery(bat);
                event = new ChargingEvent(searchStation(tokens[2]) ,vehicle);
                event.setId(Integer.parseInt(tokens[1]));
                event.setCondition("finished");
                event.setMaxWaitingTime(Long.parseLong(tokens[15]));
                event.setWaitingTime(Long.parseLong(tokens[16]));
                event.setCost(Double.parseDouble(tokens[17]));
                ChargingEvent.exchangeLog.add(event);
            }
            else
            {
                driver = new Driver(tokens[12]);
                driver.setDebt(Double.parseDouble(tokens[13]));
                driver.setProfit(Double.parseDouble(tokens[14]));
                driver.setId(Integer.parseInt(tokens[11]));
                vehicle = new ElectricVehicle(tokens[4]);
                vehicle.setDriver(driver);
                vehicle.setId(Integer.parseInt(tokens[3]));
                bat = new Battery(Double.parseDouble(tokens[6]), Double.parseDouble(tokens[7]));
                bat.setId(5);
                bat.setNumberOfChargings(Integer.parseInt(tokens[8]));
                bat.setMaxNumberOfChargings(Integer.parseInt(tokens[9]));
                bat.setActive(Boolean.parseBoolean(tokens[10]));
                vehicle.setBattery(bat);
                event1 = new ParkingEvent(searchStation(tokens[2]) ,vehicle,
                        Long.parseLong(tokens[15]), Double.parseDouble(tokens[16]));
                event1.setId(Integer.parseInt(tokens[1]));
                event1.setCondition("finished");
                event1.setEnergyToBeReceived(Double.parseDouble(tokens[17]));
                event1.setCost(Double.parseDouble(tokens[19]));
                event1.setChargingTime(Long.parseLong(tokens[18]));
                ParkingEvent.parkLog.add(event1);
            }
        }
        in.close();
    }

    private ChargingStation searchStation(String name)
    {
        for(ChargingStation st: stations)
            if(name.equals(st.getName()))
                return st;
        return stations.get(0);
    }

    private void saveFile(File selectedFile) throws FileNotFoundException {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(selectedFile.getPath(), false), "utf-8");
            String line = null;
            for(ChargingStation st: stations) {
                line = "st" + "," + st.getId() + "," + st.getName() + "," + st.FAST_CHARGERS + "," + st.SLOW_CHARGERS + "," + st.getDisChargers().length + ","
                        + st.getExchangeHandlers().length + "," + st.getParkingSlots().length + "," + st.getChargingRatioSlow() + "," + st.getChargingRatioFast() + ","
                        + st.getDisChargingRatio() + "," + st.getInductiveRatio() + "," + st.getUnitPrice() + "," + st.getDisUnitPrice() + ","
                        + st.getInductivePrice() + "," + st.getExchangePrice() + "," + st.getUpdateSpace() + ","
                        + st.getUpdateMode() + "," + st.getTimeOfExchange() + "," + st.getQueueHandling() + "," + st.getDeamon() + ","
                        + "Sources" + ",";
                for(String s: st.getSources())
                    line = line + s + "," + st.getSpecificAmount(s) + ",";
                line += "Batteries";
                for (Battery bat: st.getBatteries())
                    line = line + "," + bat.getId() + "," + bat.getRemAmount() + "," + bat.getCapacity() + "," + bat.getNumberOfChargings() + ","
                            + bat.getMaxNumberOfChargings() + "," + bat.getActive();
                line = line + System.getProperty("line.separator");
                writer.write(line);
            }
            for(ChargingEvent event:ChargingEvent.chargingLog)
            {
                line += "ch" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + "," + event.getAmountOfEnergy() + ","
                        + event.getEnergyToBeReceived() + "," + event.getKindOfCharging() + "," + event.getChargingTime() + "," + event.getMaxWaitingTime() + ","
                        + event.getWaitingTime() + "," + event.getCost();
                line += System.getProperty("line.separator");
                writer.write(line);
            }
            for(DisChargingEvent event: DisChargingEvent.dischargingLog)
            {
                line += "dis" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + "," + event.getAmountOfEnergy() + ","
                        + event.getDisChargingTime() + "," + event.getMaxWaitingTime() + "," + event.getWaitingTime() + "," + event.getProfit();
                line += System.getProperty("line.separator");
                writer.write(line);
            }
            for(ChargingEvent event: ChargingEvent.exchangeLog)
            {
                line += "ex" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + ","
                        + event.getMaxWaitingTime() + "," + event.getWaitingTime() + "," + event.getCost();
                line += System.getProperty("line.separator");
                writer.write(line);
            }
            for(ParkingEvent event: ParkingEvent.parkLog)
            {
                line += "ex" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + "," + event.getParkingTime() + ","
                        + event.getAmountOfEnergy() + "," + event.getEnergyToBeReceived() + "," + event.getChargingTime() + "," + event.getCost();
                line += System.getProperty("line.separator");
                writer.write(line);
            }
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Console extends OutputStream
    {
        private TextArea output;

        Console(TextArea ta)
        {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException
        {
            output.appendText(String.valueOf((char) i));
        }
    }
}