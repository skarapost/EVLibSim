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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class EVLibSim extends Application {

    static final BorderPane root = new BorderPane();
    static final GridPane grid = new GridPane();
    private static final GridPane stationGrid = new GridPane();
    static final ArrayList<TextField> textfields = new ArrayList<>();
    static final ArrayList<ChargingStation> stations = new ArrayList<>();
    static ChargingStation currentStation;
    static final ArrayList<String> energies = new ArrayList<>();
    static Text t = new Text();
    private static final Button newChargingStation = new Button("New ChargingStation");
    private static final Button showTotalActivity = new Button("Show total activity");
    private static final Button report = new Button("Statistics");
    static final ToggleGroup group = new ToggleGroup();
    static final MenuItem startScreen = new MenuItem("Start Screen");
    static final Menu s = new Menu("Stations");
    private static final MenuItem exitMenuItem = new MenuItem("Exit");
    static final Label stationName = new Label();
    private static final Label energyAmount = new Label();
    static final Label totalChargers = new Label();
    static final Label totalDisChargers = new Label();
    static final Label totalExchange = new Label();
    static final Label totalParkingSlots = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("EVLibSim");
        Scene scene = new Scene(root, 1350, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        MenuBar menuBar = new MenuBar();
        root.setTop(menuBar);
        Menu file = new Menu("File");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        file.getItems().addAll(startScreen, s, exitMenuItem);
        menuBar.getMenus().addAll(file, View.createViewMenu(),
                MenuStation.createStationMenu(), Event.createEventMenu(),
                Search.createSearchMenu(), Energy.createEnergyMenu());
        scene.getStylesheets().add(EVLibSim.class.getResource("EVLibSim.css").toExternalForm());
        grid.getStyleClass().add("grid");
        stationGrid.getStyleClass().add("stationGrid");
        newChargingStation.setPrefSize(220, 60);
        report.setPrefSize(220, 60);
        showTotalActivity.setPrefSize(220, 60);
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
            grid.add(showTotalActivity, 0, 1);
            grid.add(report, 0, 2);
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
        newChargingStation.setOnAction(e -> MenuStation.newStation.fire());
        showTotalActivity.setOnAction(e -> View.totalActivity.fire());
        report.setOnAction(e -> {
            if(!Maintenance.stationCheck())
                return;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Path insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter an absolute path. " +
                    "The name has to be a text(.txt) file: ");
            Optional<String> path = dialog.showAndWait();
            path.ifPresent(s -> currentStation.genReport(s));
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
}