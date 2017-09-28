package evlibsim;

import EVLib.Events.ChargingEvent;
import EVLib.Events.DisChargingEvent;
import EVLib.Events.ParkingEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static evlibsim.EVLibSim.root;

class History {

    private static final Button chargingLog = new Button("ChargingLog");
    private static final Button disChargingLog = new Button("DisChargingLog");
    private static final Button exchangeLog = new Button("ExchangeLog");
    private static final Button parkingLog = new Button("ParkingLog");
    private static final VBox nBox = new VBox();

    static VBox createSearchMenu()
    {
        nBox.getStyleClass().add("mini-box");
        Text title = new Text("History");
        title.setStyle("-fx-font-weight: bold;");
        nBox.getChildren().addAll(title, chargingLog, disChargingLog, exchangeLog, parkingLog);
        nBox.setMaxSize(300, 180);

        chargingLog.setPrefSize(220, 50);
        disChargingLog.setPrefSize(220, 50);
        exchangeLog.setPrefSize(220, 50);
        parkingLog.setPrefSize(220, 50);

        //Buttons
        chargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.chargingLog);
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, nameCol, askingAmountCol, energyToBeReceivedCol, kindCol, waitingTimeCol, maxWaitingTimeCol, chargingTimeCol,
                    conditionCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });

        disChargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList(DisChargingEvent.dischargingLog);
            TableView<DisChargingEvent> table = new TableView<>();
            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("EnergyAmount");
            TableColumn<DisChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<DisChargingEvent, Long> disChargingTimeCol = new TableColumn<>("DisChargingTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");
            table.getColumns().addAll(idCol, nameCol, amountOfEnergyCol, conditionCol, maxWaitingTimeCol, waitingTimeCol, disChargingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            disChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("disChargingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });

        exchangeLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.exchangeLog);
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, Double> profitCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, nameCol, waitingTimeCol, maxWaitingTimeCol, conditionCol, chargingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });

        parkingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ParkingEvent> result = FXCollections.observableArrayList(ParkingEvent.parkLog);
            TableView<ParkingEvent> table = new TableView<>();
            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, String> nameCol = new TableColumn<>("StationName");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Double> energyaToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ParkingEvent, String> parkingTimeCol = new TableColumn<>("ParkingTime");
            TableColumn<ParkingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ParkingEvent, String> conditionCol = new TableColumn<>("condition");
            TableColumn<ParkingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, nameCol, askingAmountCol, energyaToBeReceivedCol, parkingTimeCol, chargingTimeCol, conditionCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyaToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            parkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });
        return nBox;
    }
}
