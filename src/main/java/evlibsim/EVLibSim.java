package evlibsim;

import evlib.ev.Battery;
import evlib.ev.Driver;
import evlib.ev.ElectricVehicle;
import evlib.sources.*;
import evlib.station.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static evlibsim.Energy.*;
import static evlibsim.Event.*;
import static evlibsim.MenuStation.cs;
import static evlibsim.MenuStation.newChargingStationMI;
import static evlibsim.MenuStation.policy;

public class EVLibSim extends Application {

    static final BorderPane root = new BorderPane();
    static final GridPane grid = new GridPane();
    static final ArrayList<TextField> textfields = new ArrayList<>();
    static final ArrayList<ChargingStation> stations = new ArrayList<>();
    static final ArrayList<String> energies = new ArrayList<>();
    static final ToggleGroup group = new ToggleGroup();
    //Top menu bar
    private static final MenuBar leftMenuBar = new MenuBar();
    private static final HBox rightMenuBar = new HBox();
    private static final HBox topMenuBar = new HBox();
    static final Button refreshButton = new Button();
    private static final Button homeButton = new Button();
    private static final Image refreshImage = new Image("/refresh.png");
    private static final Label timeUnitLabel = new Label("Time unit: ");
    private static final Label energyUnitLabel = new Label("Energy unit: ");
    static final ChoiceBox<String> timeUnit = new ChoiceBox<>(FXCollections.observableArrayList("Second", "Minute"));
    static final ChoiceBox<String> energyUnit = new ChoiceBox<>(FXCollections.observableArrayList("Watt", "KiloWatt"));
    //File menu item
    private static final Menu file = new Menu("File");
    private static final MenuItem export = new MenuItem("Export to csv...");
    private static final Menu rec = new Menu("Suggestions");
    private static final RadioMenuItem enable = new RadioMenuItem("Enable");
    private static final RadioMenuItem disable = new RadioMenuItem("Disable");
    static final MenuItem startScreen = new MenuItem("Start Screen");
    static final Menu s = new Menu("Stations");
    private static final MenuItem newSession = new MenuItem("New");
    private static final MenuItem load = new MenuItem("Open...");
    private static final MenuItem save = new MenuItem("Save");
    private static final MenuItem saveAs = new MenuItem("Save as...");
    private static final MenuItem exitMenuItem = new MenuItem("Exit");
    private static final MenuItem about = new MenuItem("About");
    //Labels for the left box
    private static final Label unitPrice = new Label();
    private static final Label disUnitPrice = new Label();
    private static final Label exchangePrice = new Label();
    private static final Label inductivePrice = new Label();
    private static final Label waitTimeSlow = new Label();
    private static final Label waitTimeFast = new Label();
    private static final Label waitTimeDis = new Label();
    private static final Label waitTimeEx = new Label();
    //VBox objects for each part of left box
    private final VBox box1 = new VBox();
    private final VBox box2 = new VBox();
    private final VBox box3 = new VBox();
    private final VBox leftBox = new VBox();
    private static final TextArea ta = new TextArea();

    //Buttons' box
    private static final HBox buttonsBox = new HBox();

    //Buttons of startScreen's grid
    private static final Button newStation = new Button("Station");
    private static final Button newEvent = new Button("New event");
    private static final Button newEnergy = new Button("Energy");
    private static final Button bt1 = new Button("New charging");
    private static final Button bt2 = new Button("New discharging");
    private static final Button bt3 = new Button("New battery exchange");
    private static final Button bt4 = new Button("New parking");
    private static final Button bt5 = new Button("Add energy");
    private static final Button bt6 = new Button("Add energy source");
    private static final Button bt7 = new Button("Delete energy source");
    private static final Button bt8 = new Button("Sort energies");
    private static final Button bt9 = new Button("New charging station");
    private static final Button bt10 = new Button("New charging pricing policy");

    static ChargingStation currentStation;

    static Stage primaryStage;

    private File f = null;

    private static final Image image = new Image("/backArrow.png");
    static final Button cancel = new Button("Cancel");
    private static final Image imageHome = new Image("/home.png");

    static String panel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        EVLibSim.primaryStage = primaryStage;
        primaryStage.setTitle("EVLibSim");
        Scene scene = new Scene(root);
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1100);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        file.getItems().addAll(startScreen, new SeparatorMenuItem(), newSession, load,
                save, saveAs, new SeparatorMenuItem(), s, about, rec,
                new SeparatorMenuItem(), export, exitMenuItem);

        leftMenuBar.getMenus().addAll(file, View.createViewMenu(),
                MenuStation.createStationMenu(), Event.createEventMenu(),
                Energy.createEnergyMenu());

        refreshButton.setGraphic(new ImageView(refreshImage));

        refreshButton.setOnAction(et -> {
            switch (panel) {
                case "slowChargingsQueue":
                    View.slowChargingsQueue.fire();
                    break;
                case "fastChargingsQueue":
                    View.fastChargingsQueue.fire();
                    break;
                case "dischargingsQueue":
                    View.dischargingsQueue.fire();
                    break;
                case "exchangesQueue":
                    View.exchangesQueue.fire();
                    break;
                case "chargingsMenuItem":
                    View.chargingsMenuItem.fire();
                    break;
                case "dischargingsMenuItem":
                    View.dischargingsMenuItem.fire();
                    break;
                case "exchangesMenuItem":
                    View.exchangesMenuItem.fire();
                    break;
                case "parkingsMenuItem":
                    View.parkingsMenuItem.fire();
                    break;
                case "slowChargingLog":
                    ToolBox.slowChargingLog.fire();
                    break;
                case "fastChargingLog":
                    ToolBox.fastChargingLog.fire();
                    break;
                case "disChargingLog":
                    ToolBox.disChargingLog.fire();
                    break;
                case "exchangeLog":
                    ToolBox.exchangeLog.fire();
                    break;
                case "parkingLog":
                    ToolBox.parkingLog.fire();
                    break;
                case "overview":
                    View.totalActivity.fire();
                    break;
                case "allChargers":
                    MenuStation.allChargersMI.fire();
                    break;
                case "allDisChargers":
                    MenuStation.allDisChargersMI.fire();
                    break;
                case "allParkingSlots":
                    MenuStation.allParkingSlotsMI.fire();
                    break;
                case "allExchangeHandlers":
                    MenuStation.allExchangeHandlersMI.fire();
                    break;
                case "allBatteries":
                    MenuStation.allBatteriesMI.fire();
                    break;
                default:
                    System.out.println("System problem");
            }
        });

        homeButton.setGraphic(new ImageView(imageHome));

        homeButton.setOnAction(e -> startScreen.fire());

        rightMenuBar.getStyleClass().add("menu-bar");
        HBox.setHgrow(leftMenuBar, Priority.ALWAYS);
        HBox.setHgrow(rightMenuBar, Priority.NEVER);
        HBox timeBox = new HBox(timeUnitLabel, timeUnit);
        timeBox.getStyleClass().add("menu-bar");
        HBox energyBox = new HBox(energyUnitLabel, energyUnit);
        energyBox.getStyleClass().add("menu-bar");
        rightMenuBar.getChildren().addAll(timeBox, energyBox, homeButton, refreshButton);
        rightMenuBar.setSpacing(10);

        timeUnit.getSelectionModel().selectFirst();
        timeUnit.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if (!refreshButton.isDisabled())
                refreshButton.fire();
        });

        energyUnit.getSelectionModel().selectFirst();
        energyUnit.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) -> {
            if (!refreshButton.isDisabled())
                refreshButton.fire();
        });

        topMenuBar.getChildren().addAll(leftMenuBar, rightMenuBar);

        scene.getStylesheets().add(EVLibSim.class.getResource("/EVLibSim.css").toExternalForm());
        scene.getRoot().getStyleClass().add("main-root");

        grid.getStyleClass().add("grid");

        //Setting of startScreens panels
        root.setTop(topMenuBar);
        root.setRight(ToolBox.createToolBar());
        root.setLeft(leftBox);
        root.setBottom(box3);

        //Configuration of buttons's box
        buttonsBox.getChildren().addAll(EVLibSim.cancel);
        buttonsBox.getStyleClass().add("buttonsBox");

        //Global buttons(cancel)
        cancel.setOnAction(e -> startScreen.fire());

        //Control of suggestion box menu item
        rec.getItems().addAll(enable, disable);
        enable.setSelected(true);
        ToggleGroup g = new ToggleGroup();
        g.getToggles().addAll(enable, disable);
        g.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (enable.isSelected()) {
                suggest1.setDisable(false);
                suggest2.setDisable(false);
                suggest3.setDisable(false);
                suggest4.setDisable(false);
            } else {
                suggest1.setDisable(true);
                suggest2.setDisable(true);
                suggest3.setDisable(true);
                suggest4.setDisable(true);
            }
        });

        //Left box
        Label prices = new Label("Prices");
        Label wait = new Label("Wait");
        Label output = new Label("Output");

        prices.getStyleClass().add("chart-title");
        wait.getStyleClass().add("chart-title");
        output.getStyleClass().add("chart-title");

        Console console = new Console();
        ta.setEditable(false);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);

        box1.getChildren().addAll(prices, unitPrice, disUnitPrice, exchangePrice, inductivePrice);
        box1.getStyleClass().add("box");

        box2.getChildren().addAll(wait, waitTimeFast, waitTimeSlow, waitTimeEx, waitTimeDis);
        box2.getStyleClass().add("box");

        box3.getChildren().addAll(output, ta);
        box3.getStyleClass().add("bottomBox");

        leftBox.getStyleClass().add("sidePanels");
        leftBox.setSpacing(45);
        leftBox.getChildren().addAll(box1, box2);

        //Start screen
        startScreen.setOnAction((ActionEvent e) -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(420, 420);
            grid.setMinSize(420, 420);
            grid.add(newStation, 0, 0);
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
                        if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                            waitTimeSlow.setText("Slow: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("slow") / 1000));
                        else
                            waitTimeSlow.setText("Slow: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("slow") / 60000));
                        waitTimeSlow.setTooltip(new Tooltip("The waiting time for an available slow charger ."));
                        waitTimeSlow.getTooltip().setPrefWidth(200);
                        waitTimeSlow.getTooltip().setWrapText(true);

                        if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                            waitTimeFast.setText("Fast: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("fast") / 1000));
                        else
                            waitTimeFast.setText("Fast: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("fast") / 60000));
                        waitTimeFast.setTooltip(new Tooltip("The waiting time for an available fast charger."));
                        waitTimeFast.getTooltip().setPrefWidth(200);
                        waitTimeFast.getTooltip().setWrapText(true);

                        if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                            waitTimeDis.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("discharging") / 1000));
                        else
                            waitTimeDis.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("discharging") / 60000));
                        waitTimeDis.setTooltip(new Tooltip("The waiting time for an available discharger."));
                        waitTimeDis.getTooltip().setPrefWidth(200);
                        waitTimeDis.getTooltip().setWrapText(true);

                        if (timeUnit.getSelectionModel().getSelectedIndex() == 0)
                            waitTimeEx.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("exchange") / 1000));
                        else
                            waitTimeEx.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("exchange") / 60000));
                        waitTimeEx.setTooltip(new Tooltip("The waiting time for an available battery exchange handler."));
                        waitTimeEx.getTooltip().setPrefWidth(200);
                        waitTimeEx.getTooltip().setWrapText(true);

                        if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                            unitPrice.setText("Charging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getCurrentPrice()));
                        else
                            unitPrice.setText("Charging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getCurrentPrice() * 1000));
                        unitPrice.setTooltip(new Tooltip("The price of each energy unit for charging."));
                        unitPrice.getTooltip().setPrefWidth(200);
                        unitPrice.getTooltip().setWrapText(true);

                        if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                            disUnitPrice.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisUnitPrice()));
                        else
                            disUnitPrice.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisUnitPrice() * 1000));
                        disUnitPrice.setTooltip(new Tooltip("The price of each energy unit for discharging."));
                        disUnitPrice.getTooltip().setPrefWidth(200);
                        disUnitPrice.getTooltip().setWrapText(true);

                        exchangePrice.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getExchangePrice()));
                        exchangePrice.setTooltip(new Tooltip("The price of each battery exchange operation."));
                        exchangePrice.getTooltip().setPrefWidth(200);
                        exchangePrice.getTooltip().setWrapText(true);

                        if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                            inductivePrice.setText("Inductive: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductivePrice()));
                        else
                            inductivePrice.setText("Inductive: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductivePrice() * 1000));
                        inductivePrice.setTooltip(new Tooltip("The price of each energy unit for inductive charging. A vehicle can charge inductively only through a parking event."));
                        inductivePrice.getTooltip().setPrefWidth(200);
                        inductivePrice.getTooltip().setWrapText(true);
                        startScreen.fire();
                    }
            }
        });


        //Start screen buttons
        newStation.setPrefSize(230, 60);
        newEvent.setPrefSize(230, 60);
        newEnergy.setPrefSize(230, 60);

        newStation.setOnAction(e -> {
            Maintenance.cleanScreen();
            bt9.setPrefSize(230, 60);
            bt10.setPrefSize(230, 60);
            grid.add(bt9, 0, 0);
            grid.add(bt10, 0, 1);
            Button back = new Button();
            back.setGraphic(new ImageView(image));
            back.setPrefSize(image.getWidth(), image.getHeight());
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(back);
            grid.add(box, 0, 4);
            back.setOnAction(ey -> startScreen.fire());
            root.setCenter(grid);
        });

        bt9.setOnAction(e -> newChargingStationMI.fire());
        bt10.setOnAction(e -> policy.fire());

        newEvent.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            bt1.setPrefSize(230, 60);
            bt2.setPrefSize(230, 60);
            bt3.setPrefSize(230, 60);
            bt4.setPrefSize(230, 60);
            grid.add(bt1, 0, 0);
            grid.add(bt2, 0, 1);
            grid.add(bt3, 0, 2);
            grid.add(bt4, 0, 3);
            Button back = new Button();
            back.setGraphic(new ImageView(image));
            back.setPrefSize(image.getWidth(), image.getHeight());
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(back);
            grid.add(box, 0, 4);
            back.setOnAction(ey -> startScreen.fire());
            root.setCenter(grid);
        });

        bt1.setOnAction(e -> charging.fire());
        bt2.setOnAction(e -> discharging.fire());
        bt3.setOnAction(e -> exchange.fire());
        bt4.setOnAction(e -> parking.fire());

        newEnergy.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            bt5.setPrefSize(230, 60);
            bt6.setPrefSize(230, 60);
            bt7.setPrefSize(230, 60);
            bt8.setPrefSize(230, 60);
            grid.add(bt5, 0, 0);
            grid.add(bt6, 0, 1);
            grid.add(bt7, 0, 2);
            grid.add(bt8, 0, 3);
            Button back = new Button();
            back.setGraphic(new ImageView(image));
            back.setPrefSize(image.getWidth(), image.getHeight());
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            box.getChildren().add(back);
            grid.add(box, 0, 4);
            back.setOnAction(ey -> startScreen.fire());
            root.setCenter(grid);
        });

        bt5.setOnAction(e -> newEnergyPackages.fire());
        bt6.setOnAction(e -> newEnergySource.fire());
        bt7.setOnAction(e -> deleteEnergySource.fire());
        bt8.setOnAction(e -> sortEnergies.fire());

        //File's menu items

        about.setOnAction(e ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Creator: Karapostolakis Sotirios\nemail: skarapos@outlook.com\nYear: 2017");
            alert.showAndWait();
        });

        export.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.setInitialFileName("results.csv");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.csv)", "*.csv"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);

            OutputStreamWriter writer;

            try {
                if (selectedFile != null) {

                    writer = new OutputStreamWriter(new FileOutputStream(selectedFile.getPath(), false), "utf-8");
                    StringBuilder line;
                    line = new StringBuilder("Id,Name,Brand,StationName,Type,KindOfCharging,Energy,EnergyReceived,Condition," +
                            "ChargingTime,RemChargingTime,DischargingTime,RemDischargingTime,ParkingTime,RemParkingTime,Cost,Profit");
                    line.append(System.getProperty("line.separator"));
                    writer.write(line.toString());
                    for (ChargingEvent event : ChargingEvent.chargingLog) {
                        line = new StringBuilder(event.getId() + "," + event.getElectricVehicle().getDriver().getName() + "," +
                                event.getElectricVehicle().getBrand() + "," + event.getStation().getName() + "," + "charging," +
                                event.getKindOfCharging() + "," + event.getAmountOfEnergy() + "," + event.getEnergyToBeReceived() + "," +
                                event.getCondition() + "," + event.getChargingTime() + "," + event.getRemainingChargingTime() + "," + "0,0,0,0," + event.getCost() + ",0");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                    }
                    for (DisChargingEvent event : DisChargingEvent.dischargingLog) {
                        line = new StringBuilder(event.getId() + "," + event.getElectricVehicle().getDriver().getName() + "," +
                                event.getElectricVehicle().getBrand() + "," + event.getStation().getName() + ",discharging,0," +
                                event.getAmountOfEnergy() + ",0," + event.getCondition() + ",0,0," + event.getDisChargingTime() + "," +
                                event.getRemainingDisChargingTime() + ",0,0,0," + event.getProfit());
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                    }
                    for (ChargingEvent event : ChargingEvent.exchangeLog) {
                        line = new StringBuilder(event.getId() + "," + event.getElectricVehicle().getDriver().getName() + ","
                                + event.getElectricVehicle().getBrand() + "," + event.getStation().getName() + ",exchange,0,0,0," + event.getCondition() + "," +
                                event.getChargingTime() + "," + event.getRemainingChargingTime() + ",0,0,0,0," + event.getCost() + ",0");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                    }
                    for (ParkingEvent event : ParkingEvent.parkLog) {
                        line = new StringBuilder(event.getId() + "," + event.getElectricVehicle().getDriver().getName() + "," +
                                event.getElectricVehicle().getBrand() + "," + event.getStation().getName() + ",parking,0," +
                                event.getAmountOfEnergy() + "," + event.getEnergyToBeReceived() + "," + event.getCondition() + ",0,0,0,0,"
                                + event.getParkingTime() + "," + event.getRemainingParkingTime() + "," + event.getChargingTime() + "," +
                                event.getRemainingChargingTime() + "," + event.getCost() + ",0");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                    }
                    writer.close();
                }
            } catch (Exception er) {
                er.printStackTrace();
            }
        });

        exitMenuItem.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        saveAs.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.setInitialFileName("evlibsimProgress.txt");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                saveFile(selectedFile);
                f = selectedFile;
                primaryStage.setTitle("EVLibSim - [" + f.getPath() + "]");
            }
        });

        save.setOnAction(e -> {
            if (f == null)
                saveAs.fire();
            else
                saveFile(f);
        });

        load.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                f = selectedFile;
                openFile(selectedFile);
            }
        });

        newSession.setOnAction(e -> {
            startScreen.fire();
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lsThreads = new Thread[noThreads];
            currentGroup.enumerate(lsThreads);
            for (int i = 0; i < noThreads; i++)
                if (lsThreads[i].getName().contains("Charger") || lsThreads[i].getName().contains("Discharger")
                        || lsThreads[i].getName().contains("ExchangeHandler") || lsThreads[i].getName().contains("ParkingSlot"))
                    lsThreads[i].interrupt();
            stations.clear();
            energies.clear();
            s.getItems().clear();
            currentStation = null;
            group.getToggles().clear();
            ChargingEvent.chargingLog.clear();
            DisChargingEvent.dischargingLog.clear();
            ChargingEvent.exchangeLog.clear();
            ParkingEvent.parkLog.clear();
            primaryStage.setTitle("EVLibSim");
        });

        //Handling of exit button
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lsThreads = new Thread[noThreads];
            currentGroup.enumerate(lsThreads);
            for (int i = 0; i < noThreads; i++)
                if (lsThreads[i].getName().contains("Charger") || lsThreads[i].getName().contains("Discharger") || lsThreads[i].getName().contains("ExchangeHandler") || lsThreads[i].getName().contains("ParkingSlot")) {
                    e.consume();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.setContentText("There are still some running tasks.\nDo you still want to close the application?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent())
                        if (result.get() == ButtonType.OK) {
                            Platform.exit();
                            System.exit(0);
                            break;
                        } else if (result.get() == ButtonType.CANCEL) {
                            alert.hide();
                            break;
                        }
                }

        });

        //Timeline controling the leftbox
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            if (currentStation == null) {
                waitTimeSlow.setText("Slow: -");
                waitTimeFast.setText("Fast: -");
                waitTimeDis.setText("Discharging: -");
                waitTimeEx.setText("Exchange: -");
                unitPrice.setText("Charging: -");
                disUnitPrice.setText("Discharging: -");
                exchangePrice.setText("Exchange: -");
                inductivePrice.setText("Inductive: -");
            } else {
                if (timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                    waitTimeSlow.setText("Slow: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("slow") / 1000));
                    waitTimeFast.setText("Fast: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("fast") / 1000));
                    waitTimeDis.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("discharging") / 1000));
                    waitTimeEx.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("exchange") / 1000));
                }
                else {
                    waitTimeSlow.setText("Slow: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("slow") / 60000));
                    waitTimeFast.setText("Fast: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("fast") / 60000));
                    waitTimeDis.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("discharging") / 60000));
                    waitTimeEx.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format((double) currentStation.getWaitingTime("exchange") / 60000));
                }
                if (energyUnit.getSelectionModel().getSelectedIndex() == 0) {
                    unitPrice.setText("Charging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getCurrentPrice()));
                    disUnitPrice.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisUnitPrice()));
                    exchangePrice.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getExchangePrice()));
                    inductivePrice.setText("Inductive: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductivePrice()));
                }
                else {
                    unitPrice.setText("Charging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getCurrentPrice() * 1000));
                    disUnitPrice.setText("Discharging: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getDisUnitPrice() * 1000));
                    exchangePrice.setText("Exchange: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getExchangePrice()));
                    inductivePrice.setText("Inductive: " + new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US)).format(currentStation.getInductivePrice() * 1000));
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    //Load file function
    private void openFile(File selectedFile) {
        String line;
        String[] tokens;
        ChargingEvent event;
        DisChargingEvent disEvent;
        ParkingEvent event1;
        ChargingStation st;
        Battery bat;
        ElectricVehicle vehicle;
        int counter;
        newSession.fire();
        try (BufferedReader in = new BufferedReader(new FileReader(selectedFile))) {
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
                        st.setChargingRateSlow(Double.parseDouble(tokens[8]));
                        st.setChargingRateFast(Double.parseDouble(tokens[9]));
                        st.setDisChargingRate(Double.parseDouble(tokens[10]));
                        st.setInductiveChargingRate(Double.parseDouble(tokens[11]));
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
                            bat = new Battery(Double.parseDouble(tokens[counter + 2]),
                                    Double.parseDouble(tokens[counter + 3]));
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
                        vehicle = new ElectricVehicle(tokens[10]);
                        vehicle.setDriver(new Driver(tokens[9]));
                        vehicle.getDriver().setId(Integer.parseInt(tokens[18]));
                        event = new ChargingEvent(searchStation(tokens[2]), vehicle,
                                Double.parseDouble(tokens[3]), tokens[4]);
                        event.setId(Integer.parseInt(tokens[1]));
                        event.setCost(Double.parseDouble(tokens[5]));
                        event.setEnergyToBeReceived(Double.parseDouble(tokens[6]));
                        event.setChargingTime(Long.parseLong(tokens[7]));
                        event.setMaxWaitingTime(Long.parseLong(tokens[8]));
                        event.getElectricVehicle().setBattery(
                                new Battery(Double.parseDouble(tokens[14]), Double.parseDouble(tokens[15])));
                        event.getElectricVehicle().getBattery().setId(Integer.parseInt(tokens[13]));
                        event.getElectricVehicle().getBattery().setMaxNumberOfChargings(Integer.parseInt(tokens[16]));
                        event.getElectricVehicle().getBattery().setNumberOfChargings(Integer.parseInt(tokens[17]));
                        switch (tokens[12]) {
                            case "finished":
                                event.setCondition("finished");
                                break;
                            case "charging":
                                currentStation.assignCharger(event);
                                event.setCondition("ready");
                                event.setChargingTime(Long.parseLong(tokens[11]));
                                event.execution();
                                break;
                            case "wait":
                                event.setCondition("wait");
                                if (tokens[4].equals("slow"))
                                    currentStation.getSlow().add(event);
                                else
                                    currentStation.getFast().add(event);
                                break;
                            case "nonExecutable":
                                event.setCondition("nonExecutable");
                                break;
                        }
                        break;
                    case "dis":
                        vehicle = new ElectricVehicle(tokens[8]);
                        vehicle.setDriver(new Driver(tokens[7]));
                        vehicle.getDriver().setId(Integer.parseInt(tokens[16]));
                        disEvent = new DisChargingEvent(searchStation(tokens[2]), vehicle,
                                Double.parseDouble(tokens[3]));
                        disEvent.setId(Integer.parseInt(tokens[1]));
                        disEvent.setProfit(Double.parseDouble(tokens[4]));
                        disEvent.setDisChargingTime(Long.parseLong(tokens[5]));
                        disEvent.setMaxWaitingTime(Long.parseLong(tokens[6]));
                        disEvent.getElectricVehicle().setBattery(
                                new Battery(Double.parseDouble(tokens[12]), Double.parseDouble(tokens[13])));
                        disEvent.getElectricVehicle().getBattery().setId(Integer.parseInt(tokens[11]));
                        disEvent.getElectricVehicle().getBattery().setMaxNumberOfChargings(Integer.parseInt(tokens[14]));
                        disEvent.getElectricVehicle().getBattery().setNumberOfChargings(Integer.parseInt(tokens[15]));
                        switch (tokens[10]) {
                            case "finished":
                                disEvent.setCondition("finished");
                                break;
                            case "discharging":
                                currentStation.assignDisCharger(disEvent);
                                disEvent.setCondition("ready");
                                disEvent.setDisChargingTime(Long.parseLong(tokens[9]));
                                disEvent.execution();
                                break;
                            case "wait":
                                disEvent.setCondition("wait");
                                currentStation.getDischarging().add(disEvent);
                                break;
                            case "nonExecutable":
                                disEvent.setCondition("nonExecutable");
                                break;
                        }
                        break;
                    case "ex":
                        vehicle = new ElectricVehicle(tokens[7]);
                        vehicle.setDriver(new Driver(tokens[6]));
                        vehicle.getDriver().setId(Integer.parseInt(tokens[15]));
                        event = new ChargingEvent(searchStation(tokens[2]), vehicle);
                        event.setId(Integer.parseInt(tokens[1]));
                        event.setCost(Double.parseDouble(tokens[3]));
                        event.setChargingTime(Long.parseLong(tokens[4]));
                        event.setMaxWaitingTime(Long.parseLong(tokens[5]));
                        event.getElectricVehicle().setBattery(
                                new Battery(Double.parseDouble(tokens[11]), Double.parseDouble(tokens[12])));
                        event.getElectricVehicle().getBattery().setId(Integer.parseInt(tokens[10]));
                        event.getElectricVehicle().getBattery().setMaxNumberOfChargings(Integer.parseInt(tokens[13]));
                        event.getElectricVehicle().getBattery().setNumberOfChargings(Integer.parseInt(tokens[14]));
                        switch (tokens[9]) {
                            case "finished":
                                event.setCondition("finished");
                                break;
                            case "swapping":
                                currentStation.assignExchangeHandler(event);
                                bat = currentStation.assignBattery(event);
                                if (bat != null) {
                                    Battery b = new Battery(bat.getRemAmount(), bat.getCapacity());
                                    b.setId(bat.getId());
                                    b.setMaxNumberOfChargings(bat.getMaxNumberOfChargings());
                                    b.setNumberOfChargings(bat.getNumberOfChargings());
                                    currentStation.joinBattery(b);
                                } else {
                                    currentStation.joinBattery(new Battery(1500, 1500));
                                    bat = currentStation.assignBattery(event);
                                }
                                event.setCondition("ready");
                                event.setChargingTime(Long.parseLong(tokens[8]));
                                event.execution();
                                break;
                            case "wait":
                                event.setCondition("wait");
                                currentStation.getExchange().add(event);
                                break;
                            case "nonExecutable":
                                event.setCondition("nonExecutable");
                                break;
                        }
                        break;
                    default:
                        vehicle = new ElectricVehicle(tokens[9]);
                        vehicle.setDriver(new Driver(tokens[8]));
                        vehicle.getDriver().setId(Integer.parseInt(tokens[18]));
                        event1 = new ParkingEvent(searchStation(tokens[2]), vehicle,
                                Long.parseLong(tokens[3]), Double.parseDouble(tokens[4]));
                        event1.setId(Integer.parseInt(tokens[1]));
                        event1.setCost(Double.parseDouble(tokens[5]));
                        event1.setEnergyToBeReceived(Double.parseDouble(tokens[6]));
                        event1.setChargingTime(Long.parseLong(tokens[7]));
                        event1.getElectricVehicle().setBattery(
                                new Battery(Double.parseDouble(tokens[14]), Double.parseDouble(tokens[15])));
                        event1.getElectricVehicle().getBattery().setId(Integer.parseInt(tokens[13]));
                        event1.getElectricVehicle().getBattery().setMaxNumberOfChargings(Integer.parseInt(tokens[16]));
                        event1.getElectricVehicle().getBattery().setNumberOfChargings(Integer.parseInt(tokens[17]));
                        switch (tokens[12]) {
                            case "finished":
                                event1.setCondition("finished");
                                break;
                            case "charging":
                                currentStation.assignParkingSlot(event1);
                                event1.setCondition("ready");
                                event1.setChargingTime(Long.parseLong(tokens[10]));
                                event1.setParkingTime(Long.parseLong(tokens[11]));
                                event1.execution();
                                break;
                            case "parking":
                                currentStation.assignParkingSlot(event1);
                                event1.setCondition("ready");
                                event1.setParkingTime(Long.parseLong(tokens[11]));
                                event1.execution();
                                break;
                            case "nonExecutable":
                                event1.setCondition("nonExecutable");
                                break;
                        }
                        break;
                }
            }
            primaryStage.setTitle("EVLibSim - [" + f.getPath() + "]");
        } catch (Exception ex) {
            newSession.fire();
            System.out.println("The file is broken");
        }
    }

    //Returns a ChargingStation according to its name
    private ChargingStation searchStation(String name) {
        for (ChargingStation st : stations)
            if (name.equals(st.getName()))
                return st;
        return null;
    }

    public static HBox getButtonsBox() {
        return buttonsBox;
    }

    //Saves a file
    private void saveFile(File selectedFile) {
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(selectedFile.getPath(), false), "utf-8");
            StringBuilder line;
            for (ChargingStation st : stations) {
                line = new StringBuilder("st" + "," + st.getId() + "," + st.getName() + "," + st.FAST_CHARGERS + "," + st.SLOW_CHARGERS + "," + st.getDisChargers().length + ","
                        + st.getExchangeHandlers().length + "," + st.getParkingSlots().length + "," + st.getChargingRateSlow() + "," + st.getChargingRateFast() + ","
                        + st.getDisChargingRate() + "," + st.getInductiveRate() + "," + st.getUnitPrice() + "," + st.getDisUnitPrice() + ","
                        + st.getInductivePrice() + "," + st.getExchangePrice() + "," + st.getUpdateSpace() + ","
                        + st.getUpdateMode() + "," + st.getTimeOfExchange() + "," + st.getQueueHandling() + "," + st.getDeamon() + ","
                        + "Sources" + ",");
                for (String s : st.getSources())
                    line.append(s).append(",").append(st.getSpecificAmount(s)).append(",");
                line.append("Batteries");
                for (Battery bat : st.getBatteries())
                    line.append(",").append(bat.getId()).append(",").append(bat.getRemAmount()).append(",").append(bat.getCapacity()).append(",").append(bat.getNumberOfChargings()).append(",").append(bat.getMaxNumberOfChargings()).append(",").append(bat.getActive());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (ChargingEvent event : ChargingEvent.chargingLog) {
                line = new StringBuilder("ch" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getAmountOfEnergy() + ","
                        + event.getKindOfCharging() + "," + event.getCost() + "," + event.getEnergyToBeReceived() + ","
                        + event.getChargingTime() + "," + event.getMaxWaitingTime() + "," + event.getElectricVehicle().getDriver().getName() + "," +
                        event.getElectricVehicle().getBrand() + "," + event.getRemainingChargingTime() + "," + event.getCondition() + "," +
                        event.getElectricVehicle().getBattery().getId() + "," + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getNumberOfChargings() + ","
                        + event.getElectricVehicle().getDriver().getId());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (DisChargingEvent event : DisChargingEvent.dischargingLog) {
                line = new StringBuilder("dis" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getAmountOfEnergy() + ","
                        + event.getProfit() + "," + event.getDisChargingTime() + "," + event.getMaxWaitingTime() +
                        "," + event.getElectricVehicle().getDriver().getName() + "," + event.getElectricVehicle().getBrand() + "," + event.getRemainingDisChargingTime()
                        + "," + event.getCondition() + "," + event.getElectricVehicle().getBattery().getId() + "," + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getNumberOfChargings() + ","
                        + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," +
                        event.getElectricVehicle().getDriver().getId());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (ChargingEvent event : ChargingEvent.exchangeLog) {
                line = new StringBuilder("ex" + "," + event.getId() + "," + event.getStation().getName() + ","
                        + event.getCost() + "," + event.getChargingTime() + "," + event.getMaxWaitingTime() + ","
                        + event.getElectricVehicle().getDriver().getName() + "," + event.getElectricVehicle().getBrand() + "," + event.getRemainingChargingTime()
                        + "," + event.getCondition() + "," + event.getElectricVehicle().getBattery().getId() + "," + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getNumberOfChargings()
                        + "," + event.getElectricVehicle().getDriver().getId());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (ParkingEvent event : ParkingEvent.parkLog) {
                line = new StringBuilder("park" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getParkingTime() + ","
                        + event.getAmountOfEnergy() + "," + event.getCost() + "," + event.getEnergyToBeReceived() + "," + event.getChargingTime() + ","
                        + event.getElectricVehicle().getDriver().getName() + "," + event.getElectricVehicle().getBrand() + "," + event.getRemainingChargingTime()
                        + "," + event.getRemainingParkingTime() + "," + event.getCondition() + "," + event.getElectricVehicle().getBattery().getId() + "," + event.getElectricVehicle().getBattery().getRemAmount() + "," + event.getElectricVehicle().getBattery().getCapacity() + ","
                        + event.getElectricVehicle().getBattery().getMaxNumberOfChargings() + "," + event.getElectricVehicle().getBattery().getNumberOfChargings() + "," +
                        event.getElectricVehicle().getDriver().getId());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Class for the redirection of the system output to the leftbox's text area
    private static class Console extends OutputStream {
        private final TextArea output;

        Console() {
            this.output = EVLibSim.ta;
        }

        @Override
        public void write(int i) throws IOException {
            Platform.runLater(() -> output.appendText(String.valueOf((char) i)));
        }
    }
}