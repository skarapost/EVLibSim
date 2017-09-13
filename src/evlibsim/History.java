package evlibsim;

import Events.ChargingEvent;
import Events.DisChargingEvent;
import Events.ParkingEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static evlibsim.EVLibSim.*;

class History {

    private static final Button searchChargingEvent = new Button("ChargingLog");
    private static final Button searchDisChargingEvent = new Button("DisChargingLog");
    private static final Button searchExchangeEvent = new Button("ExchangeLog");
    private static final Button searchParkingEvent = new Button("ParkingLog");
    private static final VBox nBox = new VBox();

    static VBox createSearchMenu()
    {
        nBox.getStyleClass().add("mini-box");
        Text title = new Text("History");
        title.setStyle("-fx-font-weight: bold;");
        nBox.getChildren().addAll(title, searchChargingEvent, History.searchDisChargingEvent,
                History.searchExchangeEvent, History.searchParkingEvent);
        nBox.setMaxSize(220, 180);

        searchChargingEvent.setPrefSize(220, 50);
        searchDisChargingEvent.setPrefSize(220, 50);
        searchExchangeEvent.setPrefSize(220, 50);
        searchParkingEvent.setPrefSize(220, 50);

        //Buttons
        searchChargingEvent.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.chargingLog);
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, askingAmountCol, kindCol, waitingTimeCol, chargingTimeCol,
                    energyToBeReceivedCol, conditionCol, maxWaitingTimeCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });

        searchDisChargingEvent.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList(DisChargingEvent.dischargingLog);
            TableView<DisChargingEvent> table = new TableView<>();
            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<DisChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<DisChargingEvent, Long> disChargingTimeCol = new TableColumn<>("DisChargingTime");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");
            table.getColumns().addAll(idCol, amountOfEnergyCol, conditionCol, waitingTimeCol, disChargingTimeCol,
                    maxWaitingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            disChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("disChargingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });

        searchExchangeEvent.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.exchangeLog);
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, Double> profitCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, waitingTimeCol, conditionCol,
                    maxWaitingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });

        searchParkingEvent.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ParkingEvent> result = FXCollections.observableArrayList(ParkingEvent.parkLog);
            TableView<ParkingEvent> table = new TableView<>();
            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, String> parkingTimeCol = new TableColumn<>("ParkingTime");
            TableColumn<ParkingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ParkingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ParkingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ParkingEvent, String> conditionCol = new TableColumn<>("condition");
            TableColumn<ParkingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ParkingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, askingAmountCol, parkingTimeCol, waitingTimeCol, chargingTimeCol,
                    energyToBeReceivedCol, conditionCol, maxWaitingTimeCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            parkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });
        return nBox;
    }
}
