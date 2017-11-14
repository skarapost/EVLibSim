package evlibsim;

import evlib.station.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import static evlibsim.EVLibSim.*;

class View {

    static final MenuItem totalActivity = new MenuItem("Overview");
    private static final Menu overview = new Menu("Station overview");
    private static final Menu view = new Menu("View");
    private static final Menu queue = new Menu("Queue of events");
    private static final MenuItem slowChargingsQueue = new MenuItem("Slow chargings");
    private static final MenuItem fastChargingsQueue = new MenuItem("Fast chargings");
    private static final MenuItem dischargingsQueue = new MenuItem("Dischargings");
    private static final MenuItem exchangesQueue = new MenuItem("Battery exchanges");
    private static final MenuItem chargingsMenuItem = new MenuItem("Running chargings");
    private static final MenuItem dischargingsMenuItem = new MenuItem("Running dischargings");
    private static final MenuItem exchangesMenuItem = new MenuItem("Running battery exchanges");
    private static final MenuItem parkingsMenuItem = new MenuItem("Running parkings");
    private static final Image image = new Image(View.class.getResourceAsStream("/run.png"));

    static Menu createViewMenu() {
        overview.getItems().addAll(totalActivity, new SeparatorMenuItem(), chargingsMenuItem, dischargingsMenuItem,
                exchangesMenuItem, parkingsMenuItem);
        queue.getItems().addAll(fastChargingsQueue, slowChargingsQueue, dischargingsQueue, exchangesQueue);
        view.getItems().addAll(overview, queue);

        fastChargingsQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getFast().getSize(); i++)
                result.add((ChargingEvent) currentStation.getFast().get(i));

            TableView<ChargingEvent> table = new TableView<>();
            table.setMaxSize(600, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn executionCol = new TableColumn("Execution");
            executionCol.setEditable(false);

            executionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ChargingEvent, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));

            executionCol.setCellFactory((Callback<TableColumn<ChargingEvent, Boolean>, TableCell<ChargingEvent, Boolean>>) param -> {
                ButtonCell btnCell = new ButtonCell(table);
                btnCell.chargingOnAction();
                return btnCell;
            });

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, askingAmountCol, waitingTimeCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));

            table.setItems(result);

            root.setCenter(table);
        });

        slowChargingsQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getSlow().getSize(); i++)
                result.add((ChargingEvent) currentStation.getSlow().get(i));

            TableView<ChargingEvent> table = new TableView<>();
            table.setMaxSize(600, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn executionCol = new TableColumn("Execution");
            executionCol.setEditable(false);

            executionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ChargingEvent, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));

            executionCol.setCellFactory((Callback<TableColumn<ChargingEvent, Boolean>, TableCell<ChargingEvent, Boolean>>) param -> {
                ButtonCell btnCell = new ButtonCell(table);
                btnCell.chargingOnAction();
                return btnCell;
            });

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, askingAmountCol, waitingTimeCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));

            table.setItems(result);

            root.setCenter(table);
        });

        dischargingsQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getDischarging().getSize(); i++)
                result.add((DisChargingEvent) currentStation.getDischarging().get(i));

            TableView<DisChargingEvent> table = new TableView<>();
            table.setMaxSize(600, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn executionCol = new TableColumn("Execution");
            executionCol.setEditable(false);

            executionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<DisChargingEvent, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));

            executionCol.setCellFactory((Callback<TableColumn<DisChargingEvent, Boolean>, TableCell<DisChargingEvent, Boolean>>) param -> {
                ButtonCell btnCell = new ButtonCell(table);
                btnCell.disChargingOnAction();
                return btnCell;
            });

            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, askingAmountCol, waitingTimeCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));

            table.setItems(result);

            root.setCenter(table);
        });

        exchangesQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getExchange().getSize(); i++)
                result.add((ChargingEvent) currentStation.getExchange().get(i));

            TableView<ChargingEvent> table = new TableView<>();
            table.setMaxSize(600, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn executionCol = new TableColumn("Execution");
            executionCol.setEditable(false);

            executionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ChargingEvent, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));

            executionCol.setCellFactory((Callback<TableColumn<ChargingEvent, Boolean>, TableCell<ChargingEvent, Boolean>>) param -> {
                ButtonCell btnCell = new ButtonCell(table);
                btnCell.exchangeOnAction();
                return btnCell;
            });

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("WaitingTime");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, askingAmountCol, waitingTimeCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));

            table.setItems(result);

            root.setCenter(table);
        });

        totalActivity.setOnAction((ActionEvent e) -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
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
            applyCustomColorSequence(pieChartData, "aqua", "bisque", "chocolate", "coral", "crimson", "yellow", "black");
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
        });

        //Buttons
        chargingsMenuItem.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            for (Charger ch : currentStation.getChargers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("charging"))
                    result.add(ch.getChargingEvent());
            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyReceived");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("Wait");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Long> elapseTimeCol = new TableColumn<>("RemChargTime");
            table.getColumns().addAll(idCol, askingAmountCol, energyToBeReceivedCol, kindCol, waitingTimeCol, maxWaitingTimeCol, chargingTimeCol,
                    elapseTimeCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            elapseTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingChargingTime"));

            table.setItems(result);
            table.setMaxSize(900, 500);

            root.setCenter(table);
        });
        dischargingsMenuItem.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList();
            for (DisCharger ch : currentStation.getDisChargers())
                if ((ch.getDisChargingEvent() != null) && ch.getDisChargingEvent().getCondition().equals("discharging"))
                    result.add(ch.getDisChargingEvent());
            TableView<DisChargingEvent> table = new TableView<>();
            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("EnergyAmount");
            TableColumn<DisChargingEvent, Long> waitingTimeCol = new TableColumn<>("Wait");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<DisChargingEvent, Long> disChargingTimeCol = new TableColumn<>("DisChargTime");
            TableColumn<DisChargingEvent, Long> elapsedDisChargingTimeCol = new TableColumn<>("RemDisChargTime");
            table.getColumns().addAll(idCol, amountOfEnergyCol, disChargingTimeCol, maxWaitingTimeCol, waitingTimeCol,
                    elapsedDisChargingTimeCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            disChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("disChargingTime"));
            elapsedDisChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingDisChargingTime"));

            table.setItems(result);
            table.setMaxSize(900, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            root.setCenter(table);
        });
        exchangesMenuItem.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            for (ExchangeHandler ch : currentStation.getExchangeHandlers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("swapping"))
                    result.add(ch.getChargingEvent());
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, Long> waitingTimeCol = new TableColumn<>("Wait");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<ChargingEvent, Long> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Long> elapsedExchangeTimeCol = new TableColumn<>("RemChargTime");
            table.getColumns().addAll(idCol, chargingTimeCol, waitingTimeCol, maxWaitingTimeCol, elapsedExchangeTimeCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            waitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("waitingTime"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            elapsedExchangeTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingChargingTime"));
            table.setItems(result);
            table.setMaxSize(900, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            root.setCenter(table);
        });
        parkingsMenuItem.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            ObservableList<ParkingEvent> result = FXCollections.observableArrayList();
            for (ParkingSlot ch : currentStation.getParkingSlots())
                if ((ch.getParkingEvent() != null) && (ch.getParkingEvent().getCondition().equals("parking") || ch.getParkingEvent().getCondition().equals("charging")))
                    result.add(ch.getParkingEvent());
            TableView<ParkingEvent> table = new TableView<>();
            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyReceived");
            TableColumn<ParkingEvent, String> parkingTimeCol = new TableColumn<>("ParkTime");
            TableColumn<ParkingEvent, Long> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ParkingEvent, Long> elapsedParkingTimeCol = new TableColumn<>("RemParkTime");
            TableColumn<ParkingEvent, Long> elapsedChargingTimeCol = new TableColumn<>("RemChargTime");
            table.getColumns().addAll(idCol, askingAmountCol, energyToBeReceivedCol, parkingTimeCol,
                    chargingTimeCol, elapsedParkingTimeCol, elapsedChargingTimeCol);
            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            parkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("parkingTime"));
            chargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("chargingTime"));
            elapsedParkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingParkingTime"));
            elapsedChargingTimeCol.setCellValueFactory(new PropertyValueFactory<>("remainingChargingTime"));
            table.setItems(result);
            table.setMaxSize(900, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

    private static class ButtonCell<T> extends TableCell<T, Boolean> {
        private final Button cellButton = new Button();
        private final TableView tableView;
        private String kind = null;

        ButtonCell(TableView tblView) {
            this.tableView = tblView;
            this.cellButton.setGraphic(new ImageView(image));
            this.cellButton.setMaxSize(image.getWidth(), image.getHeight());
            this.cellButton.setMinSize(image.getWidth(), image.getHeight());
        }

        void chargingOnAction() {
            cellButton.setOnAction(e -> {
                int selectedIndex = getTableRow().getIndex();

                ChargingEvent event = (ChargingEvent) tableView.getItems().get(selectedIndex);
                kind = event.getKindOfCharging();

                if (!currentStation.getQueueHandling()) {
                    event.preProcessing();
                    if (event.getCondition().equals("charging")) {
                        event.execution();
                        if (this.kind.equalsIgnoreCase("slow"))
                            slowChargingsQueue.fire();
                        else
                            fastChargingsQueue.fire();
                    } else if (event.getCondition().equals("wait"))
                        Maintenance.queueInsertion();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The manual queue handling is disabled.");
                    alert.showAndWait();
                }
            });
        }

        void disChargingOnAction() {
            cellButton.setOnAction(e -> {
                int selectedIndex = getTableRow().getIndex();

                DisChargingEvent event = (DisChargingEvent) tableView.getItems().get(selectedIndex);

                if (!currentStation.getQueueHandling()) {
                    event.preProcessing();
                    if (event.getCondition().equals("discharging")) {
                        event.execution();
                        dischargingsQueue.fire();
                    } else if (event.getCondition().equals("wait"))
                        Maintenance.queueInsertion();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The manual queue handling is disabled.");
                    alert.showAndWait();
                }
            });
        }

        void exchangeOnAction() {
            cellButton.setOnAction(e -> {
                int selectedIndex = getTableRow().getIndex();

                ChargingEvent event = (ChargingEvent) tableView.getItems().get(selectedIndex);

                if (!currentStation.getQueueHandling()) {
                    event.preProcessing();
                    if (event.getCondition().equals("swapping")) {
                        event.execution();
                        exchangesQueue.fire();
                    } else if (event.getCondition().equals("wait"))
                        Maintenance.queueInsertion();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The manual queue handling is disabled.");
                    alert.showAndWait();
                }
            });
        }

        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty)
                setGraphic(cellButton);
        }
    }
}
