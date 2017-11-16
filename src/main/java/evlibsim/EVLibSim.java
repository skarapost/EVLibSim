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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
    static final ArrayList<TextField> textfields = new ArrayList<>();
    static final ArrayList<ChargingStation> stations = new ArrayList<>();
    static final ArrayList<String> energies = new ArrayList<>();
    static final ToggleGroup group = new ToggleGroup();
    //Top menu bar
    private static final MenuBar leftMenuBar = new MenuBar();
    private static final HBox rightMenuBar = new HBox();
    private static final HBox topMenuBar = new HBox();
    static final Button refreshButton = new Button();
    private static final Image refreshImage = new Image("/refresh.png");
    //File menu item
    private static final Menu file = new Menu("File");
    private static final MenuItem export = new MenuItem("Export to csv...");
    private static final Menu rec = new Menu("Suggestions");
    private static final RadioMenuItem enable = new RadioMenuItem("Enable");
    private static final RadioMenuItem disable = new RadioMenuItem("Disable");
    static final MenuItem startScreen = new MenuItem("Start Screen");
    static final Menu s = new Menu("Stations");
    private static final MenuItem load = new MenuItem("Load");
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
    private static final Button newChargingStation = new Button("New station");
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

    static ChargingStation currentStation;

    static Stage primaryStage;

    private File f = null;

    private static final Image image = new Image(View.class.getResourceAsStream("/backArrow.png"));
    static final Button cancel = new Button("Cancel");

    static String panel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        EVLibSim.primaryStage = primaryStage;
        primaryStage.setTitle("EVLibSim");
        Scene scene = new Scene(root);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1000);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        file.getItems().addAll(startScreen, new SeparatorMenuItem(), load,
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
                case "chargingLog":
                        ToolBox.chargingLog.fire();
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
                default:
                        System.out.println("System problem");
            }
        });

        rightMenuBar.getStyleClass().add("menu-bar");
        HBox.setHgrow(leftMenuBar, Priority.ALWAYS);
        HBox.setHgrow(rightMenuBar, Priority.NEVER);
        rightMenuBar.getChildren().add(refreshButton);

        topMenuBar.getChildren().addAll(leftMenuBar, rightMenuBar);

        scene.getStylesheets().add(EVLibSim.class.getResource("/EVLibSim.css").toExternalForm());

        grid.getStyleClass().add("grid");

        //Setting of startScreens panels
        root.setTop(topMenuBar);
        root.setRight(ToolBox.createToolBar());
        root.setLeft(leftBox);

        //Configuration of buttons's box
        buttonsBox.getChildren().addAll(EVLibSim.cancel);
        buttonsBox.setAlignment(Pos.CENTER_LEFT);
        buttonsBox.setStyle("-fx-background-color: transparent; -fx-spacing: 15px;");

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

        prices.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
        wait.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        output.setStyle("-fx-font-weight: bold; -fx-font-size: 15");

        Console console = new Console();
        ta.setEditable(false);
        ta.setMaxHeight(150);
        ta.setMaxWidth(180);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);

        box1.getChildren().addAll(prices, unitPrice, disUnitPrice, exchangePrice, inductivePrice);
        box1.getStyleClass().add("box");

        box2.getChildren().addAll(wait, waitTimeSlow, waitTimeFast, waitTimeEx, waitTimeDis);
        box2.getStyleClass().add("box");

        box3.getChildren().addAll(output, ta);
        box3.getStyleClass().add("box");

        leftBox.setAlignment(Pos.CENTER);
        leftBox.setStyle("-fx-spacing: 25;");
        leftBox.getChildren().addAll(box1, box2, box3);

        BorderPane.setAlignment(leftBox, Pos.CENTER);

        //Start screen
        startScreen.setOnAction((ActionEvent e) -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(420, 420);
            grid.setMinSize(420, 420);
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
                        waitTimeSlow.setText("Slow: " + currentStation.getWaitingTime("slow"));
                        waitTimeFast.setText("Fast: " + currentStation.getWaitingTime("fast"));
                        waitTimeDis.setText("Discharging: " + currentStation.getWaitingTime("discharging"));
                        waitTimeEx.setText("Exchange: " + currentStation.getWaitingTime("exchange"));
                        unitPrice.setText("Charging: " + currentStation.getCurrentPrice());
                        disUnitPrice.setText("Discharging: " + currentStation.getDisUnitPrice());
                        exchangePrice.setText("Exchange: " + currentStation.getExchangePrice());
                        inductivePrice.setText("Inductive: " + currentStation.getInductivePrice());
                        Energy.updateStorage.setDisable(currentStation.getUpdateMode());
                        startScreen.fire();
                    }
            }
        });


        //Start screen buttons
        newChargingStation.setPrefSize(230, 60);
        newEvent.setPrefSize(230, 60);
        newEnergy.setPrefSize(230, 60);

        newChargingStation.setOnAction(e -> newChargingStationMI.fire());

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
            Dialog<ArrayList<Boolean>> dialog = new Dialog<>();
            ArrayList<Boolean> results = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                results.add(false);
            dialog.setTitle("Export to csv");
            dialog.setHeaderText(null);
            ButtonType exportButtonType = new ButtonType("Export", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(exportButtonType, ButtonType.CANCEL);

            GridPane gp = new GridPane();
            gp.setHgap(10);
            gp.setVgap(10);
            gp.setPadding(new Insets(20, 150, 10, 10));

            RadioButton box1 = new RadioButton("Chargings");
            box1.setOnAction(w -> {
                results.remove(0);
                results.add(0, box1.isSelected());
            });
            RadioButton box2 = new RadioButton("Dischargings");
            box2.setOnAction(w -> {
                results.remove(1);
                results.add(1, box2.isSelected());
            });
            RadioButton box3 = new RadioButton("Battery exchanges");
            box3.setOnAction(w -> {
                results.remove(2);
                results.add(2, box3.isSelected());
            });
            RadioButton box4 = new RadioButton("Parkings/Inductive chargings");
            box4.setOnAction(w -> {
                results.remove(3);
                results.add(3, box4.isSelected());
            });
            ToggleGroup gr = new ToggleGroup();
            gr.getToggles().addAll(box1, box2, box3, box4);

            gp.add(box1, 0, 0);
            gp.add(box2, 0, 1);
            gp.add(box3, 0, 2);
            gp.add(box4, 0, 3);

            dialog.getDialogPane().setContent(gp);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == exportButtonType)
                    return results;
                return null;
            });

            Optional<ArrayList<Boolean>> result = dialog.showAndWait();

            result.ifPresent(s -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.setInitialFileName("results.csv");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.csv)", "*.csv"));
                File selectedFile = fileChooser.showSaveDialog(primaryStage);
                OutputStreamWriter writer;
                try {
                    writer = new OutputStreamWriter(new FileOutputStream(selectedFile.getPath(), false), "utf-8");
                    StringBuilder line;
                    if (results.indexOf(true) == 0) {
                        line = new StringBuilder("Id,StationName,KindOfCharging,AskingAmount,EnergyReceived,ChargingTime,WaitingTime,MaxWaitingTime,Cost");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                        for (ChargingEvent event : ChargingEvent.chargingLog) {
                            line = new StringBuilder(event.getId() + "," + event.getChargingStationName() + "," + event.getKindOfCharging() + "," +
                                    event.getAmountOfEnergy() + "," + event.getEnergyToBeReceived() + "," + event.getChargingTime() + "," + event.getWaitingTime() + "," +
                                    event.getMaxWaitingTime() + "," + event.getCost());
                            line.append(System.getProperty("line.separator"));
                            writer.write(line.toString());
                        }
                    } else if (results.indexOf(true) == 1) {
                        line = new StringBuilder("Id,StationName,AskingAmount,DisChargingTime,WaitingTime,MaxWaitingTime,Profit");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                        for (DisChargingEvent event : DisChargingEvent.dischargingLog) {
                            line = new StringBuilder(event.getId() + "," + event.getChargingStationName() + "," +
                                    event.getAmountOfEnergy() + "," + event.getDisChargingTime() + "," + event.getWaitingTime() + ","
                                    + event.getMaxWaitingTime() + "," + event.getProfit());
                            line.append(System.getProperty("line.separator"));
                            writer.write(line.toString());
                        }
                    } else if (results.indexOf(true) == 2) {
                        line = new StringBuilder("Id,StationName,ChargingTime,WaitingTime,MaxWaitingTime,Cost");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                        for (ChargingEvent event : ChargingEvent.exchangeLog) {
                            line = new StringBuilder(event.getId() + "," + event.getChargingStationName() + "," +
                                    event.getChargingTime() + "," + event.getWaitingTime() + ","
                                    + event.getMaxWaitingTime() + "," + event.getCost());
                            line.append(System.getProperty("line.separator"));
                            writer.write(line.toString());
                        }
                    } else {
                        line = new StringBuilder("Id,StationName,AskingAmount,EnergyReceived,ChargingTime,ParkingTime,Cost");
                        line.append(System.getProperty("line.separator"));
                        writer.write(line.toString());
                        for (ParkingEvent event : ParkingEvent.parkLog) {
                            line = new StringBuilder(event.getId() + "," + event.getChargingStationName() + "," +
                                    event.getAmountOfEnergy() + "," + event.getEnergyToBeReceived() + ","
                                    + event.getChargingTime() + "," + event.getParkingTime() + "," + event.getCost());
                            line.append(System.getProperty("line.separator"));
                            writer.write(line.toString());
                        }
                    }
                    writer.close();
                } catch (Exception er) {
                    er.printStackTrace();
                }
            });
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
            if (selectedFile != null)
                try {
                    openFile(selectedFile);
                    f = selectedFile;
                    primaryStage.setTitle("EVLibSim - [" + selectedFile.getPath() + "]");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        });

        //Handling of exit button
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
            int noThreads = currentGroup.activeCount();
            Thread[] lsThreads = new Thread[noThreads];
            currentGroup.enumerate(lsThreads);
            for (int i = 0; i < noThreads; i++)
                if (lsThreads[i].getName().contains("Charger") || lsThreads[i].getName().contains("DisCharger") || lsThreads[i].getName().contains("ExchangeHandler") || lsThreads[i].getName().contains("ParkingSlot")) {
                    e.consume();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.getButtonTypes().add(ButtonType.CANCEL);
                    alert.setContentText("There are still some running tasks.\nClose the application?");
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
                disUnitPrice.setText("DisCharging: -");
                exchangePrice.setText("Exchange: -");
                inductivePrice.setText("Inductive: -");
            } else {
                waitTimeSlow.setText("Slow: " + currentStation.getWaitingTime("slow"));
                waitTimeFast.setText("Fast: " + currentStation.getWaitingTime("fast"));
                waitTimeDis.setText("Discharging: " + currentStation.getWaitingTime("discharging"));
                waitTimeEx.setText("Exchange: " + currentStation.getWaitingTime("exchange"));
                unitPrice.setText("Charging: " + currentStation.getCurrentPrice());
                disUnitPrice.setText("Discharging: " + currentStation.getDisUnitPrice());
                exchangePrice.setText("Exchange: " + currentStation.getExchangePrice());
                inductivePrice.setText("Inductive: " + currentStation.getInductivePrice());
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    //Load file function
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
        javafx.scene.text.Font.getFamilies();
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
                        event = new ChargingEvent(searchStation(tokens[2]), null, Double.parseDouble(tokens[3]), tokens[4]);
                        event.setId(Integer.parseInt(tokens[1]));
                        event.setCondition("finished");
                        event.setWaitingTime(Long.parseLong(tokens[5]));
                        event.setCost(Double.parseDouble(tokens[6]));
                        event.setEnergyToBeReceived(Double.parseDouble(tokens[7]));
                        event.setChargingTime(Long.parseLong(tokens[8]));
                        event.setMaxWaitingTime(Long.parseLong(tokens[9]));
                        ChargingEvent.chargingLog.add(event);
                        break;
                    case "dis":
                        disEvent = new DisChargingEvent(searchStation(tokens[2]), null, Double.parseDouble(tokens[3]));
                        disEvent.setId(Integer.parseInt(tokens[1]));
                        disEvent.setCondition("finished");
                        disEvent.setWaitingTime(Long.parseLong(tokens[4]));
                        disEvent.setProfit(Double.parseDouble(tokens[5]));
                        disEvent.setDisChargingTime(Long.parseLong(tokens[6]));
                        disEvent.setMaxWaitingTime(Long.parseLong(tokens[7]));
                        DisChargingEvent.dischargingLog.add(disEvent);
                        break;
                    case "ex":
                        event = new ChargingEvent(searchStation(tokens[2]), null);
                        event.setId(Integer.parseInt(tokens[1]));
                        event.setCondition("finished");
                        event.setWaitingTime(Long.parseLong(tokens[3]));
                        event.setCost(Double.parseDouble(tokens[4]));
                        event.setChargingTime(Long.parseLong(tokens[5]));
                        event.setMaxWaitingTime(Long.parseLong(tokens[6]));
                        ChargingEvent.exchangeLog.add(event);
                        break;
                    default:
                        event1 = new ParkingEvent(searchStation(tokens[2]), null,
                                Long.parseLong(tokens[3]), Double.parseDouble(tokens[4]));
                        event1.setId(Integer.parseInt(tokens[1]));
                        event1.setCondition("finished");
                        event1.setCost(Double.parseDouble(tokens[5]));
                        event1.setEnergyToBeReceived(Double.parseDouble(tokens[6]));
                        event1.setChargingTime(Long.parseLong(tokens[7]));
                        ParkingEvent.parkLog.add(event1);
                        break;
                }
            }
        } catch (Exception ex) {
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

    //Returns a ChargingStation according to its name
    private ChargingStation searchStation(String name) {
        for (ChargingStation st : stations)
            if (name.equals(st.getName()))
                return st;
        return stations.get(0);
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
                        + st.getExchangeHandlers().length + "," + st.getParkingSlots().length + "," + st.getChargingRatioSlow() + "," + st.getChargingRatioFast() + ","
                        + st.getDisChargingRatio() + "," + st.getInductiveRatio() + "," + st.getUnitPrice() + "," + st.getDisUnitPrice() + ","
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
                        + event.getKindOfCharging() + "," + event.getWaitingTime() + "," + event.getCost() + "," + event.getEnergyToBeReceived() + ","
                        + event.getChargingTime() + "," + event.getMaxWaitingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (DisChargingEvent event : DisChargingEvent.dischargingLog) {
                line = new StringBuilder("dis" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getAmountOfEnergy() + ","
                        + event.getWaitingTime() + "," + event.getProfit() + "," + event.getDisChargingTime() + "," + event.getMaxWaitingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (ChargingEvent event : ChargingEvent.exchangeLog) {
                line = new StringBuilder("ex" + "," + event.getId() + "," + event.getStation().getName() + ","
                        + event.getWaitingTime() + "," + event.getCost() + "," + event.getChargingTime() + ","
                        + event.getMaxWaitingTime());
                line.append(System.getProperty("line.separator"));
                writer.write(line.toString());
            }
            for (ParkingEvent event : ParkingEvent.parkLog) {
                line = new StringBuilder("park" + "," + event.getId() + "," + event.getStation().getName() + "," + event.getParkingTime() + ","
                        + event.getAmountOfEnergy() + "," + event.getCost() + "," + event.getEnergyToBeReceived() + "," + event.getChargingTime());
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
            output.appendText(String.valueOf((char) i));
        }
    }
}