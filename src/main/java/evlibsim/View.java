package evlibsim;

import evlib.station.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.Predicate;

import static evlibsim.EVLibSim.*;
import static evlibsim.MenuStation.scroll;

class View {

    static final MenuItem totalActivity = new MenuItem("Overview");
    private static final Menu overview = new Menu("Station overview");
    private static final Menu view = new Menu("View");
    private static final MenuItem queue = new MenuItem("Queue of events");
    private static final MenuItem chargingsMenuItem = new MenuItem("Running chargings");
    private static final MenuItem dischargingsMenuItem = new MenuItem("Running dischargings");
    private static final MenuItem exchangesMenuItem = new MenuItem("Running exchanges");
    private static final MenuItem parkingsMenuItem = new MenuItem("Running parkings");
    private static final Image image = new Image(View.class.getResourceAsStream("/run.png"));

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
        totalActivity.setOnAction((ActionEvent e) -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 700);
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
                            pieChartData.add(new PieChart.Data("DisCharging", currentStation.getSpecificAmount("DisCharging")));
                            continue;
                        }
                        break;
                }
            }
            PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Energy Division");
            chart.setLegendVisible(false);
            chart.setLabelLineLength(25);
            chart.setClockwise(true);
            chart.getData().forEach(data -> {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(String.valueOf(data.getPieValue()));
                Tooltip.install(data.getNode(), tooltip);
            });
            grid.add(chart, 0, 0);
            root.setCenter(grid);
            applyCustomColorSequence(pieChartData, "aqua",
                    "bisque",
                    "chocolate",
                    "coral",
                    "crimson",
                    "yellow",
                    "black");
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart barChart = new BarChart(xAxis, yAxis);
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Running Events");
            int counter = 0;
            for (Charger ch : currentStation.getChargers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("charging"))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data("Char", counter));
            counter = 0;
            for (DisCharger ch : currentStation.getDisChargers())
                if ((ch.getDisChargingEvent() != null) && ch.getDisChargingEvent().getCondition().equals("discharging"))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data("Dis", counter));
            counter = 0;
            for (ExchangeHandler ch : currentStation.getExchangeHandlers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("swapping"))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data("Exch", counter));
            counter = 0;
            for (ParkingSlot ch : currentStation.getParkingSlots())
                if ((ch.getParkingEvent() != null) && (ch.getParkingEvent().getCondition().equals("parking") || ch.getParkingEvent().getCondition().equals("charging")))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data("Park", counter));
            barChart.getData().add(dataSeries1);

            grid.add(barChart, 0, 1);

            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            barChart = new BarChart(xAxis, yAxis);
            dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Infastructure");
            dataSeries1.getData().add(new XYChart.Data("Slow", currentStation.SLOW_CHARGERS));
            dataSeries1.getData().add(new XYChart.Data("Fast", currentStation.FAST_CHARGERS));
            dataSeries1.getData().add(new XYChart.Data("Dis", currentStation.getDisChargers().length));
            dataSeries1.getData().add(new XYChart.Data("Park", currentStation.getParkingSlots().length));
            dataSeries1.getData().add(new XYChart.Data("Exch", currentStation.getExchangeHandlers().length));
            barChart.getData().add(dataSeries1);

            grid.add(barChart, 1, 0);

            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            barChart = new BarChart(xAxis, yAxis);
            dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Waiting List");
            dataSeries1.getData().add(new XYChart.Data("Slow", currentStation.getSlow().getSize()));
            dataSeries1.getData().add(new XYChart.Data("Fast", currentStation.getFast().getSize()));
            dataSeries1.getData().add(new XYChart.Data("Dis", currentStation.getDischarging().getSize()));
            dataSeries1.getData().add(new XYChart.Data("Exch", currentStation.getExchange().getSize()));
            barChart.getData().add(dataSeries1);

            grid.add(barChart, 1, 1);
            Button refresh = new Button("Refresh");
            refresh.setOnAction(q -> totalActivity.fire());
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, refresh);
            grid.add(buttonsBox, 0, 2);
        });

        //Buttons
        chargingsMenuItem.setOnAction(et -> {
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
        dischargingsMenuItem.setOnAction(et -> {
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
        exchangesMenuItem.setOnAction(et -> {
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
        parkingsMenuItem.setOnAction(et -> {
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

    private static void applyCustomColorSequence(ObservableList<PieChart.Data> pieChartData, String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + pieColors[i % pieColors.length] + ";");
            i++;
        }
    }
}
