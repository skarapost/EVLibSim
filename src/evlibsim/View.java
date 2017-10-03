package evlibsim;

import EVLib.Station.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static evlibsim.EVLibSim.currentStation;
import static evlibsim.EVLibSim.root;
import static evlibsim.MenuStation.scroll;

class View {

    private static final Menu overview = new Menu("Station overview");
    private static final Menu view = new Menu("View");
    static final MenuItem totalActivity = new MenuItem("Overview");
    private static final MenuItem queue = new MenuItem("Queue of events");
    private static final MenuItem chargingsMenuItem = new MenuItem("Running chargings");
    private static final MenuItem dischargingsMenuItem = new MenuItem("Running dischargings");
    private static final MenuItem exchangesMenuItem = new MenuItem("Running exchanges");
    private static final MenuItem parkingsMenuItem = new MenuItem("Running parkings");
    private static final Button chargings = new Button("Running chargings");
    private static final Button dischargings = new Button("Running dischargings");
    private static final Button exchanges = new Button("Running exchanges");
    private static final Button parkings = new Button("Runinng parkings");
    private static final Image image = new Image(View.class.getResourceAsStream("run.png"));

    static Menu createViewMenu() {
        overview.getItems().addAll(totalActivity, new SeparatorMenuItem(), chargingsMenuItem, dischargingsMenuItem, exchangesMenuItem, parkingsMenuItem);
        view.getItems().addAll(overview, queue);

        queue.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            scroll.setMaxSize(700, 650);
            scroll.getStyleClass().add("scroll");
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            x.setSpacing(15);
            HBox z;
            Label foo;
            Button execution;
            for (int i = 0; i < currentStation.getFast().getSize(); i++) {
                ChargingEvent et = (ChargingEvent) currentStation.getFast().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("FastCharging");
                z.getChildren().add(foo);
                foo = new Label("Position: " + i);
                z.getChildren().add(foo);
                foo = new Label("Id: " + et.getId());
                z.getChildren().add(foo);
                foo = new Label("Driver: " + et.getElectricVehicle().getDriver().getName());
                z.getChildren().add(foo);
                foo = new Label("Brand: " + et.getElectricVehicle().getBrand());
                z.getChildren().add(foo);
                foo = new Label("Energy: " + et.getEnergyToBeReceived());
                z.getChildren().add(foo);
                foo = new Label("Kind: " + et.getKindOfCharging());
                z.getChildren().add(foo);
                foo = new Label("Cost: " + et.getCost());
                z.getChildren().add(foo);
                execution = new Button();
                execution.setGraphic(new ImageView(image));
                z.getChildren().add(execution);
                execution.setMaxSize(image.getWidth(), image.getHeight());
                execution.setMinSize(image.getWidth(), image.getHeight());
                execution.setOnAction(er -> {
                    if (!currentStation.getQueueHandling()) {
                        et.preProcessing();
                        if (et.getCondition().equals("charging")) {
                            et.execution();
                            queue.fire();
                        } else if (et.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            for (int i = 0; i < currentStation.getSlow().getSize(); i++) {
                ChargingEvent et = (ChargingEvent) currentStation.getSlow().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("SlowCharging");
                z.getChildren().add(foo);
                foo = new Label("Position: " + i);
                z.getChildren().add(foo);
                foo = new Label("Id: " + et.getId());
                z.getChildren().add(foo);
                foo = new Label("Driver: " + et.getElectricVehicle().getDriver().getName());
                z.getChildren().add(foo);
                foo = new Label("Brand: " + et.getElectricVehicle().getBrand());
                z.getChildren().add(foo);
                foo = new Label("Energy: " + et.getEnergyToBeReceived());
                z.getChildren().add(foo);
                foo = new Label("Kind: " + et.getKindOfCharging());
                z.getChildren().add(foo);
                foo = new Label("Cost: " + et.getCost());
                z.getChildren().add(foo);
                execution = new Button();
                execution.setGraphic(new ImageView(image));
                z.getChildren().add(execution);
                execution.setMaxSize(image.getWidth(), image.getHeight());
                execution.setMinSize(image.getWidth(), image.getHeight());
                execution.setOnAction(er -> {
                    if (!currentStation.getQueueHandling()) {
                        et.preProcessing();
                        if (et.getCondition().equals("charging")) {
                            et.execution();
                            queue.fire();
                        } else if (et.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            for (int i = 0; i < currentStation.getDischarging().getSize(); i++) {
                DisChargingEvent r = (DisChargingEvent) currentStation.getDischarging().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("DisCharging");
                z.getChildren().add(foo);
                foo = new Label("Position: " + i);
                z.getChildren().add(foo);
                foo = new Label("Id: " + r.getId());
                z.getChildren().add(foo);
                foo = new Label("Driver: " + r.getElectricVehicle().getDriver().getName());
                z.getChildren().add(foo);
                foo = new Label("Brand: " + r.getElectricVehicle().getBrand());
                z.getChildren().add(foo);
                foo = new Label("Energy: " + r.getAmountOfEnergy());
                z.getChildren().add(foo);
                foo = new Label("Profit: " + r.getProfit());
                z.getChildren().add(foo);
                execution = new Button();
                execution.setGraphic(new ImageView(image));
                z.getChildren().add(execution);
                execution.setMaxSize(image.getWidth(), image.getHeight());
                execution.setMinSize(image.getWidth(), image.getHeight());
                execution.setOnAction(er -> {
                    if (!currentStation.getQueueHandling()) {
                        r.preProcessing();
                        if (r.getCondition().equals("discharging")) {
                            r.execution();
                            queue.fire();
                        } else if (r.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            for (int i = 0; i < currentStation.getExchange().getSize(); i++) {
                ChargingEvent et = (ChargingEvent) currentStation.getExchange().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Exchange");
                z.getChildren().add(foo);
                foo = new Label("Position: " + i);
                z.getChildren().add(foo);
                foo = new Label("Id: " + et.getId());
                z.getChildren().add(foo);
                foo = new Label("Driver: " + et.getElectricVehicle().getDriver().getName());
                z.getChildren().add(foo);
                foo = new Label("Brand: " + et.getElectricVehicle().getBrand());
                z.getChildren().add(foo);
                foo = new Label("Cost: " + et.getCost());
                z.getChildren().add(foo);
                execution = new Button();
                execution.setGraphic(new ImageView(image));
                z.getChildren().add(execution);
                execution.setMaxSize(image.getWidth(), image.getHeight());
                execution.setMinSize(image.getWidth(), image.getHeight());
                execution.setOnAction(er -> {
                    if (!currentStation.getQueueHandling()) {
                        et.preProcessing();
                        if (et.getCondition().equals("swapping")) {
                            et.execution();
                            queue.fire();
                        } else if (et.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        totalActivity.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            Label title = new Label("Running Events");
            title.setAlignment(Pos.CENTER);
            title.setStyle("-fx-font: 15 Lato bold;");
            VBox box = new VBox();
            box.setPadding(new Insets(25));
            box.setSpacing(25);
            box.getChildren().addAll(new Label("Name: " + currentStation.getName()),
                    new Label("Total slow chargers: " + currentStation.SLOW_CHARGERS),
                    new Label("Total fast chargers: " + currentStation.FAST_CHARGERS),
                    new Label("Total dischargers: " + currentStation.getDisChargers().length),
                    new Label("Total exchange handlers: " + currentStation.getExchangeHandlers().length),
                    new Label("Total parking slots: " + currentStation.getParkingSlots().length),
                    new Label("Cars waiting for slow charging: " + String.valueOf(currentStation.getSlow().getSize())),
                    new Label("Cars waiting for fast charging: " + String.valueOf(currentStation.getFast().getSize())),
                    new Label("Cars waiting for discharging: " + String.valueOf(currentStation.getDischarging().getSize())),
                    new Label("Cars waiting for battery exchange: " + String.valueOf(currentStation.getExchange().getSize())),
                    title,
                    chargings, dischargings, exchanges, parkings);
            chargings.setPrefSize(250, 30);
            dischargings.setPrefSize(250, 30);
            exchanges.setPrefSize(250, 30);
            parkings.setPrefSize(250, 30);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (int i = 0; i < currentStation.getSources().length; i++) {
                switch (currentStation.getSources()[i]) {
                    case "Solar":
                        if (currentStation.getSpecificAmount("Solar") > 0) {
                            pieChartData.add(new PieChart.Data("Solar", currentStation.getSpecificAmount("Solar")));
                            continue;
                        }
                        break;
                    case "Wind":
                        if (currentStation.getSpecificAmount("Wind") > 0) {
                            pieChartData.add(new PieChart.Data("Wind", currentStation.getSpecificAmount("Wind")));
                            continue;
                        }
                        break;
                    case "Wave":
                        if (currentStation.getSpecificAmount("Wave") > 0) {
                            pieChartData.add(new PieChart.Data("Wave", currentStation.getSpecificAmount("Wave")));
                            continue;
                        }
                        break;
                    case "Hydroelectric":
                        if (currentStation.getSpecificAmount("Hydroelectric") > 0) {
                            pieChartData.add(new PieChart.Data("Hydroelectric", currentStation.getSpecificAmount("Hydroelectric")));
                            continue;
                        }
                        break;
                    case "Nonrenewable":
                        if (currentStation.getSpecificAmount("Nonrenewable") > 0) {
                            pieChartData.add(new PieChart.Data("Nonrenewable", currentStation.getSpecificAmount("Nonrenewable")));
                            continue;
                        }
                        break;
                    case "Geothermal":
                        if (currentStation.getSpecificAmount("Geothermal") > 0) {
                            pieChartData.add(new PieChart.Data("Geothermal", currentStation.getSpecificAmount("Geothermal")));
                            continue;
                        }
                        break;
                    case "DisCharging":
                        if (currentStation.getSpecificAmount("DisCharging") > 0) {
                            pieChartData.add(new PieChart.Data("Discharging", currentStation.getSpecificAmount("DisCharging")));
                            continue;
                        }
                        break;
                }
            }
            final PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Energy Sources");
            chart.setLabelLineLength(25);
            chart.setClockwise(true);
            root.setCenter(chart);
            root.setLeft(box);
        });
        chargingsMenuItem.setOnAction(et -> chargings.fire());
        dischargingsMenuItem.setOnAction(et -> dischargings.fire());
        exchangesMenuItem.setOnAction(et -> exchanges.fire());
        parkingsMenuItem.setOnAction(et -> parkings.fire());

        //Buttons
        chargings.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            for (Charger ch : currentStation.getChargers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("charging"))
                    result.add(ch.getChargingEvent());
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");
            TableColumn<ChargingEvent, Long> elapseTimeCol = new TableColumn<>("RemainingChargingTime");
            table.getColumns().addAll(idCol, askingAmountCol, energyToBeReceivedCol, kindCol, waitingTimeCol, maxWaitingTimeCol, chargingTimeCol,
                    conditionCol, costCol, elapseTimeCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            elapseTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingChargingTime"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });
        dischargings.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList();
            for (DisCharger ch : currentStation.getDisChargers())
                if ((ch.getDisChargingEvent() != null) && ch.getDisChargingEvent().getCondition().equals("discharging"))
                    result.add(ch.getDisChargingEvent());
            TableView<DisChargingEvent> table = new TableView<>();
            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("EnergyAmount");
            TableColumn<DisChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<DisChargingEvent, Long> disChargingTimeCol = new TableColumn<>("DisChargingTime");
            TableColumn<DisChargingEvent, Long> elapsedDisChargingTimeCol = new TableColumn<>("RemainingDisChargingTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");
            table.getColumns().addAll(idCol, amountOfEnergyCol, conditionCol, maxWaitingTimeCol, waitingTimeCol,
                    disChargingTimeCol, elapsedDisChargingTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            disChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("disChargingTime"));
            elapsedDisChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingDisChargingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });
        exchanges.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            for (ExchangeHandler ch : currentStation.getExchangeHandlers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("swapping"))
                    result.add(ch.getChargingEvent());
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            TableColumn<ChargingEvent, String> conditionCol = new TableColumn<>("Condition");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ChargingEvent, Long> elapsedExchangeTimeCol = new TableColumn<>("RemainingChargingTime");
            TableColumn<ChargingEvent, Double> profitCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, waitingTimeCol, maxWaitingTimeCol, conditionCol, chargingTimeCol, elapsedExchangeTimeCol, profitCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            elapsedExchangeTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingChargingTime"));
            profitCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });
        parkings.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ParkingEvent> result = FXCollections.observableArrayList();
            for (ParkingSlot ch : currentStation.getParkingSlots())
                if ((ch.getParkingEvent() != null) && (ch.getParkingEvent().getCondition().equals("parking") || ch.getParkingEvent().getCondition().equals("charging")))
                    result.add(ch.getParkingEvent());
            TableView<ParkingEvent> table = new TableView<>();
            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyToBeReceived");
            TableColumn<ParkingEvent, String> parkingTimeCol = new TableColumn<>("ParkingTime");
            TableColumn<ParkingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ParkingEvent, Long> chargingTimeCol = new TableColumn<>("ChargingTime");
            TableColumn<ParkingEvent, Long> elapsedParkingTimeCol = new TableColumn<>("RemainingParkingTime");
            TableColumn<ParkingEvent, Long> elapsedChargingTimeCol = new TableColumn<>("RemainingChargingTime");
            TableColumn<ParkingEvent, String> conditionCol = new TableColumn<>("condition");
            TableColumn<ParkingEvent, Double> costCol = new TableColumn<>("Cost");
            table.getColumns().addAll(idCol, askingAmountCol, energyToBeReceivedCol, parkingTimeCol, waitingTimeCol,
                    chargingTimeCol, elapsedParkingTimeCol, elapsedChargingTimeCol, conditionCol, costCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            parkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            elapsedParkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingParkingTime"));
            elapsedChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingChargingTime"));
            conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);
            table.setMaxSize(1000, 600);
            root.setCenter(table);
        });
        return view;
    }
}
