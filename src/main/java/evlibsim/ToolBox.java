package evlibsim;

import evlib.station.ChargingEvent;
import evlib.station.DisChargingEvent;
import evlib.station.ParkingEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

import static evlibsim.EVLibSim.*;

class ToolBox {

    private static final Button chargingLog = new Button();
    private static final Button disChargingLog = new Button();
    private static final Button exchangeLog = new Button();
    private static final Button parkingLog = new Button();
    private static final Button showTotalActivity = new Button();
    private static final Button report = new Button();
    private static final Button totalEnergy = new Button();
    private static final javafx.scene.control.ToolBar bar = new javafx.scene.control.ToolBar();
    private static final Image image1 = new Image(View.class.getResourceAsStream("/hist1.png"));
    private static final Image image2 = new Image(View.class.getResourceAsStream("/hist2.png"));
    private static final Image image3 = new Image(View.class.getResourceAsStream("/hist3.png"));
    private static final Image image4 = new Image(View.class.getResourceAsStream("/hist4.png"));
    private static final Image image5 = new Image(View.class.getResourceAsStream("/totalActivity.png"));
    private static final Image image6 = new Image(View.class.getResourceAsStream("/report.png"));
    private static final Image image7 = new Image(View.class.getResourceAsStream("/energy.png"));

    private static void createLogButtons() {
        chargingLog.setGraphic(new ImageView(image1));
        chargingLog.setPrefSize(image1.getWidth(), image1.getHeight());
        chargingLog.setTooltip(new Tooltip("Completed chargings"));
        chargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();

            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.chargingLog);
            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(900, 500);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("Wait");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, kindCol, askingAmountCol, energyToBeReceivedCol, chargingTimeCol,
                    waitingTimeCol, maxWaitingTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);

            root.setCenter(table);
        });

        disChargingLog.setGraphic(new ImageView(image2));
        disChargingLog.setPrefSize(image2.getWidth(), image2.getHeight());
        disChargingLog.setTooltip(new Tooltip("Completed dischargings"));
        disChargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();

            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList(DisChargingEvent.dischargingLog);
            TableView<DisChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(900, 500);

            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("GivenAmount");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("Wait");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<DisChargingEvent, Long> disChargingTimeCol = new TableColumn<>("DisChargTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");

            table.getColumns().addAll(idCol, nameCol, amountOfEnergyCol, disChargingTimeCol, waitingTimeCol, maxWaitingTimeCol, profitCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            disChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("disChargingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
            table.setItems(result);

            root.setCenter(table);
        });

        exchangeLog.setGraphic(new ImageView(image3));
        exchangeLog.setPrefSize(image3.getWidth(), image3.getHeight());
        exchangeLog.setTooltip(new Tooltip("Completed battery exchanges"));
        exchangeLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();

            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.exchangeLog);
            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(900, 500);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("Wait");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Double> profitCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, chargingTimeCol, waitingTimeCol, maxWaitingTimeCol, profitCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);

            root.setCenter(table);
        });

        parkingLog.setGraphic(new ImageView(image4));
        parkingLog.setPrefSize(image4.getWidth(), image4.getHeight());
        parkingLog.setTooltip(new Tooltip("Completed parkings"));
        parkingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();

            ObservableList<ParkingEvent> result = FXCollections.observableArrayList(ParkingEvent.parkLog);
            TableView<ParkingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(900, 500);

            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Double> energyaToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
            TableColumn<ParkingEvent, String> parkingTimeCol = new TableColumn<>("ParkTime");
            TableColumn<ParkingEvent, Long> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ParkingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, nameCol, askingAmountCol, energyaToBeReceivedCol, parkingTimeCol, chargingTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyaToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            parkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);

            root.setCenter(table);
        });
    }

    private static void createOverviewMenu() {

        showTotalActivity.setGraphic(new ImageView(image5));
        showTotalActivity.setPrefSize(image5.getWidth(), image5.getHeight());
        showTotalActivity.setTooltip(new Tooltip("Overview of the charging station"));
        showTotalActivity.setOnAction(e -> View.totalActivity.fire());

        report.setGraphic(new ImageView(image6));
        report.setPrefSize(image6.getWidth(), image6.getHeight());
        report.setTooltip(new Tooltip("Report of the selected charging station"));
        report.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Generate Report");
            fileChooser.setInitialFileName("report.txt");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                currentStation.genReport(selectedFile.getPath());
            }
        });

        totalEnergy.setGraphic(new ImageView(image7));
        totalEnergy.setPrefSize(image7.getWidth(), image7.getHeight());
        totalEnergy.setTooltip(new Tooltip("Total energy in the charging station"));
        totalEnergy.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(600, 300);
            Label foo;
            TextField boo;
            foo = new Label("Total energy: ");
            grid.add(foo, 0, 0);
            boo = new TextField(String.valueOf(currentStation.getTotalEnergy()));
            boo.setEditable(false);
            grid.add(boo, 1, 0);
            if (Maintenance.checkEnergy("Solar"))
                foo = new Label("Solar*: ");
            else
                foo = new Label("Solar: ");
            grid.add(foo, 2, 0);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Solar")));
            boo.setEditable(false);
            grid.add(boo, 3, 0);
            if (Maintenance.checkEnergy("Wind"))
                foo = new Label("Wind*: ");
            else
                foo = new Label("Wind: ");
            grid.add(foo, 0, 1);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Wind")));
            boo.setEditable(false);
            grid.add(boo, 1, 1);
            if (Maintenance.checkEnergy("Wave"))
                foo = new Label("Wave*: ");
            else
                foo = new Label("Wave: ");
            grid.add(foo, 2, 1);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Wave")));
            boo.setEditable(false);
            grid.add(boo, 3, 1);
            if (Maintenance.checkEnergy("Hydroelectric"))
                foo = new Label("Hydroelectric*: ");
            else
                foo = new Label("Hydroelectric: ");
            grid.add(foo, 0, 2);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Hydroelectric")));
            boo.setEditable(false);
            grid.add(boo, 1, 2);
            if (Maintenance.checkEnergy("Nonrenewable"))
                foo = new Label("Nonrenewable*: ");
            else
                foo = new Label("Nonrenewable: ");
            grid.add(foo, 2, 2);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Nonrenewable")));
            boo.setEditable(false);
            grid.add(boo, 3, 2);
            if (Maintenance.checkEnergy("Geothermal"))
                foo = new Label("Geothermal*: ");
            else
                foo = new Label("Geothermal: ");
            grid.add(foo, 0, 3);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Geothermal")));
            boo.setEditable(false);
            grid.add(boo, 1, 3);
            foo = new Label("Discharging*: ");
            grid.add(foo, 2, 3);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("DisCharging")));
            boo.setEditable(false);
            grid.add(boo, 3, 3);
            foo = new Label("*Selected");
            grid.add(foo, 0, 4);
            root.setCenter(grid);
        });
    }

    static javafx.scene.control.ToolBar createToolBar() {
        bar.getItems().addAll(chargingLog, disChargingLog, exchangeLog, parkingLog,
                showTotalActivity, totalEnergy, report);
        bar.setOrientation(Orientation.VERTICAL);
        bar.setStyle("-fx-alignment: center; -fx-spacing: 10;" +
                " -fx-background-color: transparent;");
        BorderPane.setAlignment(bar, Pos.CENTER);
        createLogButtons();
        createOverviewMenu();
        return bar;
    }
}
