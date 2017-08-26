package evlibsim;

import Station.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class EVLibSim extends Application
{
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
    private Timer timer;
    private final Label stationName = new Label();
    private final Label energyAmount = new Label();
    private final Label totalChargers = new Label();
    private final Label totalDisChargers = new Label();
    private final Label totalExchange = new Label();
    private final Label totalParkingSlots = new Label();

    public static void main(String[] args) { launch(args); }

    private class UpdateLabels extends TimerTask
    {
        @Override
        public void run()
        {
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
        }
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
        menuBar.getMenus().addAll(file, View.createViewMenu(), MenuStation.createStationMenu(), Event.createEventMenu(), Search.createSearchMenu(), Energy.createEnergyMenu());
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

        timer = new Timer();
        timer.schedule(new UpdateLabels(), 0, 10000);

        startScreen.setOnAction(e -> {
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
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
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
        newChargingStation.setOnAction(e -> {
            MenuStation.newStation.fire();
        });
        showTotalActivity.setOnAction(e -> {
            View.totalActivity.fire();
        });
        report.setOnAction(e -> {
            if(!Maintenance.stationCheck())
                return;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Path insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter the absolute path: ");
            Optional<String> path = dialog.showAndWait();
            if (path.isPresent())
                currentStation.genReport(path.get());
        });
    }
}