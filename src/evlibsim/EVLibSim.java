package evlibsim;

import Station.*;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.*;
import java.util.prefs.Preferences;

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
    private static final Button newChargingStation = new Button("Station");
    private static final Button newEvent = new Button("Event");
    private static final Button newEnergy = new Button("Energy");
    private static final Button showTotalActivity = new Button("Activity");
    private static final Button report = new Button("Report");
    private static final Button totalEnergy = new Button("Energy");
    static final ToggleGroup group = new ToggleGroup();
    static final MenuItem startScreen = new MenuItem("Start Screen");
    static final Menu s = new Menu("Stations");
    private static final MenuItem newSession = new MenuItem("New");
    private static final MenuItem open = new MenuItem("Open");
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
    private Stage primaryStage;
    private static final VBox miniBox = new VBox();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EVLibSim");
        Scene scene = new Scene(root, 1350, 800);
        this.primaryStage = primaryStage;
        primaryStage.setScene(scene);
        primaryStage.show();
        MenuBar menuBar = new MenuBar();
        miniBox.getChildren().addAll(report, showTotalActivity, totalEnergy);
        miniBox.getStyleClass().add("mini-box");
        miniBox.setMaxSize(200, 180);
        root.setRight(miniBox);
        BorderPane.setAlignment(miniBox, Pos.CENTER_RIGHT);
        root.setTop(menuBar);
        Menu file = new Menu("File");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        file.getItems().addAll(newSession, open, save, saveAs, startScreen, s, about, exitMenuItem);
        menuBar.getMenus().addAll(file, View.createViewMenu(),
                MenuStation.createStationMenu(), Event.createEventMenu(),
                Search.createSearchMenu(), Energy.createEnergyMenu());
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
            BorderPane.setAlignment(grid, Pos.CENTER);
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
                    if (Objects.equals(cs.reName(), name)) {
                        currentStation = cs;
                        stationName.setText("ChargingStation Name: " + currentStation.reName());
                        energyAmount.setText("Total Energy: " + currentStation.reTotalEnergy());
                        totalChargers.setText("Number of Chargers: " + currentStation.reChargers().length);
                        totalDisChargers.setText("Number of DisChargers: " + currentStation.reDisChargers().length);
                        totalExchange.setText("Number of ExchangeHandlers: " + currentStation.reExchangeHandlers().length);
                        totalParkingSlots.setText("Number of ParkingSlots: " + currentStation.reParkingSlots().length);
                    }
            }
        });
        newChargingStation.setOnAction(e -> MenuStation.newChargingStationMI.fire());
        showTotalActivity.setOnAction(e -> View.totalActivity.fire());
        report.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Path insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter an absolute path. " +
                    "The name has to be a text(.txt) file: ");
            Optional<String> path = dialog.showAndWait();
            path.ifPresent(s -> currentStation.genReport(s));
        });
        totalEnergy.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            Label foo;
            foo = new Label("Total energy: ");
            grid.add(foo, 0, 1);
            foo = new Label(String.valueOf(currentStation.reTotalEnergy()));
            grid.add(foo, 1, 1);
            if(Maintenance.checkEnergy("solar"))
                foo = new Label("Solar*: ");
            else
                foo = new Label("Solar: ");
            grid.add(foo, 2, 1);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("solar")));
            grid.add(foo, 3, 1);
            if(Maintenance.checkEnergy("wind"))
                foo = new Label("Wind*: ");
            else
                foo = new Label("Wind: ");
            grid.add(foo, 0, 2);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("wind")));
            grid.add(foo, 1, 2);
            if(Maintenance.checkEnergy("wave"))
                foo = new Label("Wave*: ");
            else
                foo = new Label("Wave: ");
            grid.add(foo, 2, 2);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("wave")));
            grid.add(foo, 3, 2);
            if(Maintenance.checkEnergy("hydroelectric"))
                foo = new Label("Hydro-Electric*: ");
            else
                foo = new Label("Hydro-Electric: ");
            grid.add(foo, 0, 3);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("hydroelectric")));
            grid.add(foo, 1, 3);
            if(Maintenance.checkEnergy("nonrenewable"))
                foo = new Label("Non-Renewable*: ");
            else
                foo = new Label("Non-Renewable: ");
            grid.add(foo, 2, 3);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("nonrenewable")));
            grid.add(foo, 3, 3);
            if(Maintenance.checkEnergy("geothermal"))
                foo = new Label("Geothermal*: ");
            else
                foo = new Label("Geothermal: ");
            grid.add(foo, 0, 4);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("geothermal")));
            grid.add(foo, 1, 4);
            foo = new Label("Discharging*: ");
            grid.add(foo, 2, 4);
            foo = new Label(String.valueOf(currentStation.reSpecificAmount("discharging")));
            grid.add(foo, 3, 4);
            foo = new Label("*This source is an option for the station.");
            grid.add(foo, 0, 5);
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
        open.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            // Show open file dialog
            File f = fileChooser.showOpenDialog(this.primaryStage);
            if (f != null) {
                loadStationsFromFile(f);
            }
        });
        newSession.setOnAction(e -> {
            stations.clear();
            s.getItems().clear();
            currentStation = null;
            setStationsFilePath(null);
        });
        save.setOnAction(e -> {
            File personFile = getStationsFilePath();
            if (personFile != null) {
                saveStationToFile(personFile);
            } else {
                saveAs.fire();
            }
        });
        saveAs.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "XML files (*.xml)", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            // Show save file dialog
            File f = fileChooser.showSaveDialog(this.primaryStage);
            if (f != null) {
                // Make sure it has the correct extension
                if (!f.getPath().endsWith(".xml")) {
                    f = new File(f.getPath() + ".xml");
                }
                saveStationToFile(f);
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
                stationName.setText("ChargingStation Name: " + currentStation.reName());
                energyAmount.setText("Total Energy: " + currentStation.reTotalEnergy());
                totalChargers.setText("Number of Chargers: " + currentStation.reChargers().length);
                totalDisChargers.setText("Number of DisChargers: " + currentStation.reDisChargers().length);
                totalExchange.setText("Number of ExchangeHandlers: " + currentStation.reExchangeHandlers().length);
                totalParkingSlots.setText("Number of ParkingSlots: " + currentStation.reParkingSlots().length);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private File getStationsFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(EVLibSim.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    private void setStationsFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(EVLibSim.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            // Update the stage title.
            this.primaryStage.setTitle("EVLibSim - " + file.getName());
        } else {
            prefs.remove("filePath");
            // Update the stage title.
            this.primaryStage.setTitle("EVLibSim");
        }
    }
    private void loadStationsFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ChargingStationsWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            // Reading XML from the file and unmarshalling.
            ChargingStationsWrapper wrapper = (ChargingStationsWrapper) um.unmarshal(file);
            stations.clear();
            s.getItems().clear();
            stations.addAll(wrapper.getChargingStations());
            for (ChargingStation station : stations) {
                cs = new RadioMenuItem(station.reName());
                group.getToggles().add(cs);
                s.getItems().add(cs);
                if (s.getItems().size() == 1)
                    cs.setSelected(true);
            }
            // Save the file path to the registry.
            setStationsFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }
    private void saveStationToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(ChargingStationsWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Wrapping our person data.
            ChargingStationsWrapper wrapper = new ChargingStationsWrapper();
            wrapper.setChargingStations();
            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);
            // Save the file path to the registry.
            setStationsFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());
            alert.showAndWait();
        }
    }
}