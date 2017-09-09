package evlibsim;

import Events.ChargingEvent;
import Events.DisChargingEvent;
import Events.ParkingEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static evlibsim.EVLibSim.*;

class Search {

    private static final Menu search = new Menu("Search");
    static final MenuItem searchCharging = new MenuItem("Search ChargingEvent");
    static final MenuItem searchDisCharging = new MenuItem("Search DisChargingEvent");
    static final MenuItem searchExchange = new MenuItem("Search Exchange Battery Event");
    static final MenuItem searchParking = new MenuItem("Search ParkingEvent");
    private static final Button searchChargingEvent = new Button("Search");
    private static final Button searchDisChargingEvent = new Button("Search");
    private static final Button searchExchangeEvent = new Button("Search");
    private static final Button searchParkingEvent = new Button("Search");

    static void createSearchMenu()
    {
        searchCharging.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(400, 280);
            TextField boo;
            Label foo;
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            textfields.add(boo);
            grid.add(boo, 1, 3);
            grid.add(searchChargingEvent, 0, 4);
            searchChargingEvent.setDefaultButton(true);
            root.setCenter(grid);
        });
        searchDisCharging.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(400, 280);
            TextField boo;
            Label foo;
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            grid.add(searchDisChargingEvent, 0, 4);
            searchChargingEvent.setDefaultButton(true);
            root.setCenter(grid);
        });
        searchExchange.setOnAction(e ->
        {
            Maintenance.cleanScreen();
            grid.setMaxSize(400, 280);
            TextField boo;
            Label foo;
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            grid.add(searchExchangeEvent, 0, 3);
            searchExchangeEvent.setDefaultButton(true);
            root.setCenter(grid);
        });

        searchParking.setOnAction(e -> {
            Maintenance.cleanScreen();
            grid.setMaxSize(400, 280);
            TextField boo;
            Label foo;
            foo = new Label("Model of car: ");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car: ");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy: ");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            foo = new Label("Parking time: ");
            grid.add(foo, 0, 4);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 4);
            grid.add(searchParkingEvent, 0, 5);
            searchParkingEvent.setDefaultButton(true);
            root.setCenter(grid);
        });
        searchChargingEvent.setOnAction(e -> {
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, askingAmountCol, kindCol, waitingTimeCol, chargingTimeCol,
                    energyToBeReceivedCol, maxWaitingTimeCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            for(ChargingEvent ch: ChargingEvent.chargingLog)
                if(ch.getElectricVehicle().getDriver().getName().equals(textfields.get(1).getText())&&
                        (ch.getElectricVehicle().getBrand().equals(textfields.get(0).getText())&&
                                (ch.getAmountOfEnergy()==Double.parseDouble(textfields.get(2).getText()))))
                    result.add(ch);
            table.setItems(result);
            table.setMaxSize(1000, 600);
            grid.getChildren().clear();
            root.setCenter(null);
            scroll.setContent(null);
            root.setCenter(table);
        });
        searchDisChargingEvent.setOnAction(e -> {
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList();
            TableView<DisChargingEvent> table = new TableView<>();
            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("EnergyToBeGiven");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<DisChargingEvent, Long> disChargingTimeCol = new TableColumn<>("DisChargingTime");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");
            table.getColumns().addAll(idCol, amountOfEnergyCol, waitingTimeCol, disChargingTimeCol,
                    maxWaitingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            disChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("disChargingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
            for(DisChargingEvent ch: DisChargingEvent.dischargingLog)
                if(ch.getElectricVehicle().getDriver().getName().equals(textfields.get(1).getText())&&
                        (ch.getElectricVehicle().getBrand().equals(textfields.get(0).getText())&&
                                (ch.getAmountOfEnergy()==Double.parseDouble(textfields.get(2).getText()))))
                    result.add(ch);
            table.setItems(result);
            table.setMaxSize(1000, 600);
            grid.getChildren().clear();
            root.setCenter(null);
            scroll.setContent(null);
            root.setCenter(table);
        });
        searchExchangeEvent.setOnAction(e -> {
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, Double> profitCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, waitingTimeCol,
                    maxWaitingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            for(ChargingEvent ch: ChargingEvent.exchangeLog)
                if(ch.getElectricVehicle().getDriver().getName().equals(textfields.get(1).getText())&&
                        (ch.getElectricVehicle().getBrand().equals(textfields.get(0).getText())))
                    result.add(ch);
            table.setItems(result);
            table.setMaxSize(1000, 600);
            grid.getChildren().clear();
            root.setCenter(null);
            scroll.setContent(null);
            root.setCenter(table);
        });
        searchParkingEvent.setOnAction(e -> {
            ObservableList<ParkingEvent> result = FXCollections.observableArrayList();
            TableView<ParkingEvent> table = new TableView<>();
            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, String> parkingTimeCol = new TableColumn<>("ParkingTime");
            TableColumn<ParkingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ParkingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ParkingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ParkingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ParkingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, askingAmountCol, parkingTimeCol, waitingTimeCol, chargingTimeCol,
                    energyToBeReceivedCol, maxWaitingTimeCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            parkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            for(ParkingEvent ch: ParkingEvent.parkLog)
                if(ch.getElectricVehicle().getDriver().getName().equals(textfields.get(1).getText())&&
                        (ch.getElectricVehicle().getBrand().equals(textfields.get(0).getText())&&
                                (ch.getAmountOfEnergy()==Double.parseDouble(textfields.get(2).getText())))&&
                        (ch.getParkingTime() == Double.parseDouble(textfields.get(3).getText())))
                    result.add(ch);
            table.setItems(result);
            table.setMaxSize(1000, 600);
            grid.getChildren().clear();
            root.setCenter(null);
            scroll.setContent(null);
            root.setCenter(table);
        });
        search.getItems().addAll(searchCharging, searchDisCharging, searchExchange, searchParking);
    }
}
