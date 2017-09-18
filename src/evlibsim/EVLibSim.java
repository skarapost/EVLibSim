package evlibsim;

import EVLib.EV.Battery;
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
import javafx.util.Duration;

import java.io.*;
import java.util.*;

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
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
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
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null)
                try {
                    saveFile(selectedFile);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
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

    private void saveFile(File selectedFile) throws FileNotFoundException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(selectedFile.getPath(), false), "utf-8"));
            String line = null;
            for(ChargingStation st: stations) {
                line = "st" + "," + st.getId() + "," + st.getName() + "," + st.getChargers() + "," + st.getDisChargers() + ","
                        + st.getExchangeHandlers() + "," + st.getParkingSlots() + "," + st.getChargingRatioSlow() + "," + st.getChargingRatioFast() + ","
                        + st.getDisChargingRatio() + "," + st.getInductiveRatio() + "," + st.getUnitPrice() + "," + st.getDisUnitPrice() + ","
                        + st.getInductivePrice() + "," + st.getInductivePrice() + "," + st.getExchangePrice() + "," + st.getUpdateSpace() + ","
                        + st.getUpdateMode() + "," + st.getTimeOfExchange() + "," + st.getQueueHandling() + "," + st.getDeamon() + ","
                        + "Sources" + ",";
                for(String s: st.getSources())
                    line += s + "," + String.valueOf(st.getSpecificAmount("s")) + ",";
                line += "Batteries";
                for (Battery bat: st.getBatteries())
                    line += "," + bat.getId() + "," + bat.getRemAmount() + "," + bat.getCapacity() + "," + bat.getNumberOfChargings() + ","
                            + bat.getMaxNumberOfChargings() + "," + bat.getActive();
                line += System.getProperty("line.separator");
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