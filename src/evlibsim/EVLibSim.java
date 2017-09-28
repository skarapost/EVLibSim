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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static evlibsim.Energy.*;
import static evlibsim.Event.*;
import static evlibsim.MenuStation.cs;
import static evlibsim.MenuStation.newChargingStationMI;

public class EVLibSim extends Application {

    static final BorderPane root = new BorderPane();
    static final GridPane grid = new GridPane();
    static final ScrollPane scroll = new ScrollPane();
    private static final GridPane stationGrid = new GridPane();
    static final ArrayList<TextField> textfields = new ArrayList<>();
    static final ArrayList<ChargingStation> stations = new ArrayList<>();
    static ChargingStation currentStation;
    static final ArrayList<String> energies = new ArrayList<>();
    private static final MenuBar menuBar = new MenuBar();
    private static final Menu file = new Menu("File");
    private static final Button newChargingStation = new Button("Station");
    private static final Button newEvent = new Button("Event");
    private static final Button newEnergy = new Button("Energy");
    static final ToggleGroup group = new ToggleGroup();
    private static final MenuItem startScreen = new MenuItem("Start Screen");
    private static final Menu rec = new Menu("Suggestions");
    private static final RadioMenuItem enable = new RadioMenuItem("Enable");
    private static final RadioMenuItem disable = new RadioMenuItem("Disable");
    static final Menu s = new Menu("Stations");
    private static final MenuItem load = new MenuItem("Load");
    private static final MenuItem save = new MenuItem("Save");
    private static final MenuItem saveAs = new MenuItem("Save as...");
    private static final MenuItem exitMenuItem = new MenuItem("Exit");
    private static final MenuItem about = new MenuItem("About");
    private static final Label unitPrice = new Label();
    private static final Label disUnitPrice = new Label();
    private static final Label exchangePrice = new Label();
    private static final Label inductivePrice = new Label();
    private static final Label waitTimeSlow = new Label();
    private static final Label waitTimeFast = new Label();
    private static final Label waitTimeDis = new Label();
    private static final Label waitTimeEx = new Label();
    private static final VBox tBox = new VBox();
    private static final TextArea ta = new TextArea();
    private static final Button bt1 = new Button("New ChargingEvent");
    private static final Button bt2 = new Button("New DisChargingEvent");
    private static final Button bt3 = new Button("New ExchangeEvent");
    private static final Button bt4 = new Button("New ParkingEvent");
    private static final Button bt5 = new Button("New Amounts");
    private static final Button bt6 = new Button("New EnergySource");
    private static final Button bt7 = new Button("Delete EnergySource");
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

        Console console = new Console();
        ta.setEditable(false);
        ta.setMaxSize(300, 200);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);

        BorderPane.setAlignment(tBox, Pos.CENTER_RIGHT);
        tBox.getChildren().addAll(Overview.createOverviewMenu(), History.createSearchMenu(), ta);
        tBox.setSpacing(50);
        tBox.setAlignment(Pos.CENTER_RIGHT);

        root.setTop(menuBar);
        root.setRight(tBox);

        exitMenuItem.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        rec.getItems().addAll(enable, disable);
        enable.setSelected(true);
        ToggleGroup g = new ToggleGroup();
        g.getToggles().addAll(enable, disable);
        g.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if(enable.isSelected()) {
                suggest1.setDisable(false);
                suggest2.setDisable(false);
                suggest3.setDisable(false);
                suggest4.setDisable(false);
            }
            else {
                suggest1.setDisable(true);
                suggest2.setDisable(true);
                suggest3.setDisable(true);
                suggest4.setDisable(true);
            }
        });

        file.getItems().addAll(startScreen, new SeparatorMenuItem(), load, save, saveAs, new SeparatorMenuItem(), s, about, rec, exitMenuItem);
        menuBar.getMenus().addAll(file, View.createViewMenu(), MenuStation.createStationMenu(), Event.createEventMenu(), Energy.createEnergyMenu());
        scene.getStylesheets().add(EVLibSim.class.getResource("EVLibSim.css").toExternalForm());
        grid.getStyleClass().add("grid");
        stationGrid.getStyleClass().add("stationGrid");

        newChargingStation.setPrefSize(230, 60);
        newEvent.setPrefSize(230, 60);
        newEnergy.setPrefSize(230, 60);

        stationGrid.add(waitTimeSlow, 0, 0);
        stationGrid.add(waitTimeFast, 1, 0);
        stationGrid.add(waitTimeDis, 2, 0);
        stationGrid.add(waitTimeEx, 3, 0);
        stationGrid.add(unitPrice, 4, 0);
        stationGrid.add(disUnitPrice, 5, 0);
        stationGrid.add(exchangePrice, 6, 0);
        stationGrid.add(inductivePrice, 7, 0);

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
                        waitTimeSlow.setText("Wait(slow): " + currentStation.getWaitingTime("slow"));
                        waitTimeFast.setText("Wait(fast): " + currentStation.getWaitingTime("fast"));
                        waitTimeDis.setText("Wait(discharging): " + currentStation.getWaitingTime("discharging"));
                        waitTimeEx.setText("Wait(exchange): " + currentStation.getWaitingTime("exchange"));
                        unitPrice.setText("Price(charging): " + currentStation.getCurrentPrice());
                        disUnitPrice.setText("Price(discharging): " + currentStation.getDisUnitPrice());
                        exchangePrice.setText("Price(exchange): " + currentStation.getExchangePrice());
                        inductivePrice.setText("Price(insuctive): " + currentStation.getInductivePrice());
                        startScreen.fire();
                    }
            }
        });

        newChargingStation.setOnAction(e -> newChargingStationMI.fire());

        newEvent.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            bt1.setPrefSize(230, 60);
            bt2.setPrefSize(230, 60);
            bt3.setPrefSize(230, 60);
            bt4.setPrefSize(230, 60);
            grid.setMaxSize(400, 400);
            grid.setMinSize(400, 400);
            grid.add(bt1, 0, 0);
            grid.add(bt2, 0, 1);
            grid.add(bt3, 0, 2);
            grid.add(bt4, 0, 3);
            root.setCenter(grid);
        });

        bt1.setOnAction(e -> charging.fire());
        bt2.setOnAction(e -> discharging.fire());
        bt3.setOnAction(e -> exchange.fire());
        bt4.setOnAction(e -> parking.fire());

        newEnergy.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            bt5.setPrefSize(230, 60);
            bt6.setPrefSize(230, 60);
            bt7.setPrefSize(230, 60);
            grid.setMaxSize(400, 400);
            grid.setMinSize(400, 400);
            grid.add(bt5, 0, 0);
            grid.add(bt6, 0, 1);
            grid.add(bt7, 0, 2);
            root.setCenter(grid);
        });

        bt5.setOnAction(e -> newEnergyPackages.fire());
        bt6.setOnAction(e -> newEnergySource.fire());
        bt7.setOnAction(e -> deleteEnergySource.fire());

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
            if (selectedFile != null) {
                saveFile(selectedFile);
                f = selectedFile;
                primaryStage.setTitle("EVLibSim - [" + f.getPath() + "]");
            }
        });

        save.setOnAction(e -> {
            if(f == null)
                saveAs.fire();
            else
                saveFile(f);
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
                            Platform.exit();
                            System.exit(0);
                            break;
                        }
                        else if(result.get() == ButtonType.CANCEL) {
                            alert.hide();
                            break;
                        }
                }

        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            if(currentStation == null) {
                waitTimeSlow.setText("Wait(slow): -");
                waitTimeFast.setText("Wait(fast): -");
                waitTimeDis.setText("Wait(discharging): -");
                waitTimeEx.setText("Wait(exchange): -");
                unitPrice.setText("Price(charging): -");
                disUnitPrice.setText("Price(discharging): -");
                exchangePrice.setText("Price(exchange): -");
                inductivePrice.setText("Price(inductive): -");
            }
            else
            {
                waitTimeSlow.setText("Wait(slow): " + currentStation.getWaitingTime("slow"));
                waitTimeFast.setText("Wait(fast): " + currentStation.getWaitingTime("fast"));
                waitTimeDis.setText("Wait(discharging): " + currentStation.getWaitingTime("discharging"));
                waitTimeEx.setText("Wait(exchange): " + currentStation.getWaitingTime("exchange"));
                unitPrice.setText("Price(charging): " + currentStation.getCurrentPrice());
                disUnitPrice.setText("Price(discharging): " + currentStation.getDisUnitPrice());
                exchangePrice.setText("Price(exchange): " + currentStation.getExchangePrice());
                inductivePrice.setText("Price(inductive): " + currentStation.getInductivePrice());
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
        int counter;
        stations.clear();
        s.getItems().clear();
        currentStation = null;
        group.getToggles().clear();
        ChargingEvent.chargingLog.clear();
        DisChargingEvent.dischargingLog.clear();
        ChargingEvent.exchangeLog.clear();
        ParkingEvent.parkLog.clear();
        //Prophets of Rage - living on 110
        try {
            while ((line = in.readLine()) != null) {
                tokens = line.split(",");
                switch (tokens[0]) {
                    case "st":
                        st = new ChargingStation(tokens[2]);
                        st.setId(Integer.parseInt(tokens[1]));
                        for (int i = 0; i < Integer.parseInt(tokens[3]); i++)
                            st.addCharger(new Charger(st, "fast"));
                        for (int i = 0; i < Integer.parseInt(tokens[4]); i++)
                            st.addCharger(new Charger(st, "slow"));
                        for (int i = 0; i < Integer.parseInt(tokens[5]); i++)
                            st.addDisCharger(new DisCharger(st));
                        for (int i = 0; i < Integer.parseInt(tokens[6]); i++)
                            st.addExchangeHandler(new ExchangeHandler(st));
                        for (int i = 0; i < Integer.parseInt(tokens[7]); i++)
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
                        while (!tokens[counter].equals("Batteries")) {
                            switch (tokens[counter]) {
                                case "Solar":
                                    st.addEnergySource(new Solar());
                                    break;
                                case "Wind":
                                    st.addEnergySource(new Wind());
                                    break;
                                case "Wave":
                                    st.addEnergySource(new Wave());
                                    break;
                                case "Hydroelectric":
                                    st.addEnergySource(new Hydroelectric());
                                    break;
                                case "Geothermal":
                                    st.addEnergySource(new Geothermal());
                                    break;
                                case "Nonrenewable":
                                    st.addEnergySource(new Nonrenewable());
                                    break;
                            }
                            st.setSpecificAmount(tokens[counter], Double.parseDouble(tokens[counter + 1]));
                            counter += 2;
                        }
                        while ((counter + 1) < tokens.length) {
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
                        if (s.getItems().size() == 1)
                            cs.setSelected(true);
                        break;
                    case "ch":
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
                        event = new ChargingEvent(searchStation(tokens[2]), vehicle, Double.parseDouble(tokens[15]), tokens[16]);
                        event.setId(Integer.parseInt(tokens[1]));
                        event.setCondition("finished");
                        event.setWaitingTime(Long.parseLong(tokens[17]));
                        event.setCost(Double.parseDouble(tokens[18]));
                        event.setEnergyToBeReceived(Double.parseDouble(tokens[19]));
                        event.setChargingTime(Long.parseLong(tokens[20]));
                        event.setMaxWaitingTime(Long.parseLong(tokens[21]));
                        ChargingEvent.chargingLog.add(event);
                        break;
                    case "dis":
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
                        disEvent = new DisChargingEvent(searchStation(tokens[2]), vehicle, Double.parseDouble(tokens[15]));
                        disEvent.setId(Integer.parseInt(tokens[1]));
                        disEvent.setCondition("finished");
                        disEvent.setWaitingTime(Long.parseLong(tokens[16]));
                        disEvent.setProfit(Double.parseDouble(tokens[17]));
                        disEvent.setDisChargingTime(Long.parseLong(tokens[18]));
                        disEvent.setMaxWaitingTime(Long.parseLong(tokens[19]));
                        DisChargingEvent.dischargingLog.add(disEvent);
                        break;
                    case "ex":
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
                        event = new ChargingEvent(searchStation(tokens[2]), vehicle);
                        event.setId(Integer.parseInt(tokens[1]));
                        event.setCondition("finished");
                        event.setWaitingTime(Long.parseLong(tokens[15]));
                        event.setCost(Double.parseDouble(tokens[16]));
                        event.setChargingTime(Long.parseLong(tokens[17]));
                        event.setMaxWaitingTime(Long.parseLong(tokens[18]));
                        ChargingEvent.exchangeLog.add(event);
                        break;
                    default:
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
                        event1 = new ParkingEvent(searchStation(tokens[2]), vehicle,
                                Long.parseLong(tokens[15]), Double.parseDouble(tokens[16]));
                        event1.setId(Integer.parseInt(tokens[1]));
                        event1.setCondition("finished");
                        event1.setCost(Double.parseDouble(tokens[17]));
                        event1.setEnergyToBeReceived(Double.parseDouble(tokens[18]));
                        event1.setChargingTime(Long.parseLong(tokens[19]));
                        ParkingEvent.parkLog.add(event1);
                        break;
                }
            }
        } catch (Exception ex)
        {
            in.close();
            System.out.println("The file is broken");
            stations.clear();
            s.getItems().clear();
            currentStation = null;
            group.getToggles().clear();
            ChargingEvent.chargingLog.clear();
            DisChargingEvent.dischargingLog.clear();
            ChargingEvent.exchangeLog.clear();
            ParkingEvent.parkLog.clear();
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

    private void saveFile(File selectedFile) {
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(selectedFile.getPath(), false), "utf-8");
            StringBuilder line;
            for(ChargingStation st: stations) {
                line = new StringBuilder("st" + "," + st.getId() + "," + st.getName() + "," + st.FAST_CHARGERS + "," + st.SLOW_CHARGERS + "," + st.getDisChargers().length + ","
                        + st.getExchangeHandlers().length + "," + st.getParkingSlots().length + "," + st.getChargingRatioSlow() + "," + st.getChargingRatioFast() + ","
                        + st.getDisChargingRatio() + "," + st.getInductiveRatio() + "," + st.getUnitPrice() + "," + st.getDisUnitPrice() + ","
                        + st.getInductivePrice() + "," + st.getExchangePrice() + "," + st.getUpdateSpace() + ","
                        + st.getUpdateMode() + "," + st.getTimeOfExchange() + "," + st.getQueueHandling() + "," + st.getDeamon() + ","
                        + "Sources" + ",");
                for(String s: st.getSources())
                    line.append(s).append(",").append(st.getSpecificAmount(s)).append(",");
                line.append("Batteries");
                for (Battery bat: st.getBatteries())
                    line.append(",").append(bat.getId()).append(",").append(bat.getRemAmount()).append(",").append(bat.getCapacity()).append(",").append(bat.getNumberOfChargings()).append(",").append(bat.getMaxNumberOfChargings()).append(",").append(bat.getActive());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for(ChargingEvent event:ChargingEvent.chargingLog)
            {
                line = new StringBuilder("ch" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + "," + event.getAmountOfEnergy() + ","
                        + event.getKindOfCharging() + "," + event.getWaitingTime() + "," + event.getCost() + "," + event.getEnergyToBeReceived() + "," + event.getChargingTime() + ","
                        + event.getMaxWaitingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for(DisChargingEvent event: DisChargingEvent.dischargingLog)
            {
                line = new StringBuilder("dis" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + "," + event.getAmountOfEnergy() + ","
                        + event.getWaitingTime() + "," + event.getProfit() + "," + event.getDisChargingTime() + "," + event.getMaxWaitingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for(ChargingEvent event: ChargingEvent.exchangeLog)
            {
                line = new StringBuilder("ex" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + ","
                        + event.getWaitingTime() + "," + event.getCost() + "," + event.getChargingTime() + "," + event.getMaxWaitingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for(ParkingEvent event: ParkingEvent.parkLog)
            {
                line = new StringBuilder("park" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getElectricVehicle().getId() + ","
                        + event.getElectricVehicle().getBrand() + "," + event.getElectricVehicle().getBattery().getId() + ","
                        + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getActive() + "," + event.getElectricVehicle().getDriver().getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                        + event.getElectricVehicle().getDriver().getDebt() + "," + event.getElectricVehicle().getDriver().getProfit() + "," + event.getParkingTime() + ","
                        + event.getAmountOfEnergy() + "," + event.getCost() + "," + event.getEnergyToBeReceived() + "," + event.getChargingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Console extends OutputStream
    {
        private final TextArea output;

        Console()
        {
            this.output = EVLibSim.ta;
        }
        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }
}