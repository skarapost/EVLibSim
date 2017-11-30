package evlibsim;

import evlib.station.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import static evlibsim.EVLibSim.*;

class View {

    static final MenuItem totalActivity = new MenuItem("Overview");
    static final MenuItem slowChargingsQueue = new MenuItem("Slow chargings");
    static final MenuItem fastChargingsQueue = new MenuItem("Fast chargings");
    static final MenuItem dischargingsQueue = new MenuItem("Dischargings");
    static final MenuItem exchangesQueue = new MenuItem("Battery exchanges");
    static final MenuItem chargingsMenuItem = new MenuItem("Running chargings");
    static final MenuItem dischargingsMenuItem = new MenuItem("Running dischargings");
    static final MenuItem exchangesMenuItem = new MenuItem("Running battery exchanges");
    static final MenuItem parkingsMenuItem = new MenuItem("Running parkings");
    private static final Menu overview = new Menu("Station overview");
    private static final Menu view = new Menu("View");
    private static final Menu queue = new Menu("Queue of events");
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
            refreshButton.setDisable(false);
            EVLibSim.panel = "fastChargingsQueue";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getFast().getSize(); i++)
                result.add((ChargingEvent) currentStation.getFast().get(i));

            TableView<ChargingEvent> table = new TableView<>();
            table.setMaxSize(900, 500);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn executionCol = new TableColumn<>("Execution");
            executionCol.setEditable(false);

            executionCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ChargingEvent, Boolean>, ObservableValue>) param -> new SimpleBooleanProperty(param.getValue() != null));

            executionCol.setCellFactory((Callback<TableColumn<ChargingEvent, Boolean>, TableCell<ChargingEvent, Boolean>>) param -> {
                ButtonCell btnCell = new ButtonCell(table);
                btnCell.chargingOnAction();
                return btnCell;
            });

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Number> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, askingAmountCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 1000));
            else
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 60000));

            table.setItems(result);

            root.setCenter(table);
        });

        slowChargingsQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "slowChargingsQueue";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getSlow().getSize(); i++)
                result.add((ChargingEvent) currentStation.getSlow().get(i));

            TableView<ChargingEvent> table = new TableView<>();
            table.setMaxSize(900, 500);
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
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Number> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, askingAmountCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 1000));
            else
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 60000));

            table.setItems(result);

            root.setCenter(table);
        });

        dischargingsQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "dischargingsQueue";
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getDischarging().getSize(); i++)
                result.add((DisChargingEvent) currentStation.getDischarging().get(i));

            TableView<DisChargingEvent> table = new TableView<>();
            table.setMaxSize(900, 500);
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
            TableColumn<DisChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<DisChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<DisChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<DisChargingEvent, Number> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, askingAmountCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 1000));
            else
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 60000));

            table.setItems(result);

            root.setCenter(table);
        });

        exchangesQueue.setOnAction(ew -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "exchangesQueue";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (int i = 0; i < currentStation.getExchange().getSize(); i++)
                result.add((ChargingEvent) currentStation.getExchange().get(i));

            TableView<ChargingEvent> table = new TableView<>();
            table.setMaxSize(900, 500);
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
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Number> maxWaitingTimeCol = new TableColumn<>("MaxWaitingTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, maxWaitingTimeCol, executionCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0)
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 1000));
            else
                maxWaitingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getMaxWaitingTime() / 60000));

            table.setItems(result);

            root.setCenter(table);
        });

        totalActivity.setOnAction((ActionEvent e) -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "overview";
            EVLibSim.panel = "overview";
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
            chart.setLabelLineLength(10);
            chart.setClockwise(true);
            chart.setLabelsVisible(true);
            chart.getData().forEach(data -> {
                Tooltip tooltip = new Tooltip();
                tooltip.setWrapText(true);
                tooltip.setText(String.valueOf(data.getPieValue()));
                Tooltip.install(data.getNode(), tooltip);
            });

            applyCustomColorSequence(pieChartData, "#B96E6E", "#B9A46E", "#A1B96E", "#6EB97A", "#6EB9AF", "#6E8DB9", "#B96E82");

            grid.add(new VBox(chart), 0, 0);

            root.setCenter(grid);

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
            barChart.setTitle("Running Events");
            int counter = 0;
            for (Charger ch : currentStation.getChargers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("charging"))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data<>("Char", counter));
            counter = 0;
            for (DisCharger ch : currentStation.getDisChargers())
                if ((ch.getDisChargingEvent() != null) && ch.getDisChargingEvent().getCondition().equals("discharging"))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data<>("Dis", counter));
            counter = 0;
            for (ExchangeHandler ch : currentStation.getExchangeHandlers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("swapping"))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data<>("Exch", counter));
            counter = 0;
            for (ParkingSlot ch : currentStation.getParkingSlots())
                if ((ch.getParkingEvent() != null) && (ch.getParkingEvent().getCondition().equals("parking") || ch.getParkingEvent().getCondition().equals("charging")))
                    counter++;
            dataSeries1.getData().add(new XYChart.Data<>("Park", counter));
            barChart.getData().add(dataSeries1);

            for (XYChart.Series<String, Number> s : barChart.getData()) {
                for (XYChart.Data<String, Number> d : s.getData()) {
                    if (d.getXValue().equals("Char"))
                        Tooltip.install(d.getNode(), new Tooltip("Chargings"));
                    else if (d.getXValue().equals("Dis"))
                        Tooltip.install(d.getNode(), new Tooltip("Dischargings"));
                    else if (d.getXValue().equals("Exch"))
                        Tooltip.install(d.getNode(), new Tooltip("Battery exchanges"));
                    else
                        Tooltip.install(d.getNode(), new Tooltip("Parkings/inductive chargings"));
                }
            }

            Node n = barChart.lookup(".data0.chart-bar");
            n.setStyle("-fx-bar-fill: #B97879");
            n = barChart.lookup(".data1.chart-bar");
            n.setStyle("-fx-bar-fill: #8378B9");
            n = barChart.lookup(".data2.chart-bar");
            n.setStyle("-fx-bar-fill: #78B9A2");
            n = barChart.lookup(".data3.chart-bar");
            n.setStyle("-fx-bar-fill: #87B978");


            barChart.setLegendVisible(false);

            grid.add(new VBox(barChart), 0, 1);

            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Infrastructure");
            dataSeries1 = new XYChart.Series<>();
            dataSeries1.getData().add(new XYChart.Data<>("Slow", currentStation.SLOW_CHARGERS));
            dataSeries1.getData().add(new XYChart.Data<>("Fast", currentStation.FAST_CHARGERS));
            dataSeries1.getData().add(new XYChart.Data<>("Dis", currentStation.getDisChargers().length));
            dataSeries1.getData().add(new XYChart.Data<>("Exch", currentStation.getExchangeHandlers().length));
            dataSeries1.getData().add(new XYChart.Data<>("Park", currentStation.getParkingSlots().length));
            barChart.getData().add(dataSeries1);

            for (XYChart.Series<String, Number> s : barChart.getData()) {
                for (XYChart.Data<String, Number> d : s.getData()) {
                    if (d.getXValue().equals("Slow"))
                        Tooltip.install(d.getNode(), new Tooltip("Slow chargers"));
                    else if (d.getXValue().equals("Fast"))
                        Tooltip.install(d.getNode(), new Tooltip("Fast chargers"));
                    else if (d.getXValue().equals("Dis"))
                        Tooltip.install(d.getNode(), new Tooltip("Dischargers"));
                    else if (d.getXValue().equals("Park"))
                        Tooltip.install(d.getNode(), new Tooltip("Parking slots"));
                    else
                        Tooltip.install(d.getNode(), new Tooltip("Exchange handlers"));
                }
            }

            n = barChart.lookup(".data0.chart-bar");
            n.setStyle("-fx-bar-fill: #B97879");
            n = barChart.lookup(".data1.chart-bar");
            n.setStyle("-fx-bar-fill: #8378B9");
            n = barChart.lookup(".data2.chart-bar");
            n.setStyle("-fx-bar-fill: #78B9A2");
            n = barChart.lookup(".data3.chart-bar");
            n.setStyle("-fx-bar-fill: #87B978");
            n = barChart.lookup(".data4.chart-bar");
            n.setStyle("-fx-bar-fill: #B9A478");

            barChart.setLegendVisible(false);

            grid.add(new VBox(barChart), 1, 0);

            xAxis = new CategoryAxis();
            yAxis = new NumberAxis();
            barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Waiting List");
            dataSeries1 = new XYChart.Series<>();
            dataSeries1.getData().add(new XYChart.Data<>("Slow", currentStation.getSlow().getSize()));
            dataSeries1.getData().add(new XYChart.Data<>("Fast", currentStation.getFast().getSize()));
            dataSeries1.getData().add(new XYChart.Data<>("Dis", currentStation.getDischarging().getSize()));
            dataSeries1.getData().add(new XYChart.Data<>("Exch", currentStation.getExchange().getSize()));
            barChart.getData().add(dataSeries1);

            for (XYChart.Series<String, Number> s : barChart.getData()) {
                for (XYChart.Data<String, Number> d : s.getData()) {
                    if (d.getXValue().equals("Slow"))
                        Tooltip.install(d.getNode(), new Tooltip("Queue for slow charging"));
                    else if (d.getXValue().equals("Fast"))
                        Tooltip.install(d.getNode(), new Tooltip("Queue for fast charging"));
                    else if (d.getXValue().equals("Dis"))
                        Tooltip.install(d.getNode(), new Tooltip("Queue for discharging"));
                    else
                        Tooltip.install(d.getNode(), new Tooltip("Queue for battery exchange"));
                }
            }

            n = barChart.lookup(".data0.chart-bar");
            n.setStyle("-fx-bar-fill: #B97879");
            n = barChart.lookup(".data1.chart-bar");
            n.setStyle("-fx-bar-fill: #8378B9");
            n = barChart.lookup(".data2.chart-bar");
            n.setStyle("-fx-bar-fill: #78B9A2");
            n = barChart.lookup(".data3.chart-bar");
            n.setStyle("-fx-bar-fill: #87B978");

            barChart.setLegendVisible(false);

            grid.add(new VBox(barChart), 1, 1);
        });
        //contagiar, lembrarias
        //Buttons
        chargingsMenuItem.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "chargingsMenuItem";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            for (Charger ch : currentStation.getChargers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("charging"))
                    result.add(ch.getChargingEvent());
            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("EnergyAmount");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyReceived");
            TableColumn<ChargingEvent, String> kindCol = new TableColumn<>("Kind");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<ChargingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Number> elapseTimeCol = new TableColumn<>("RemChargTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, askingAmountCol, energyToBeReceivedCol, kindCol, maxWaitingTimeCol, chargingTimeCol,
                    elapseTimeCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            kindCol.setCellValueFactory(new PropertyValueFactory<>("kindOfCharging"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 1000));
                elapseTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                elapseTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }

            table.setItems(result);
            table.setMaxSize(900, 500);

            root.setCenter(table);
        });
        dischargingsMenuItem.setOnAction(et -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "dischargingsMenuItem";
            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList();
            for (DisCharger ch : currentStation.getDisChargers())
                if ((ch.getDisChargingEvent() != null) && ch.getDisChargingEvent().getCondition().equals("discharging"))
                    result.add(ch.getDisChargingEvent());
            TableView<DisChargingEvent> table = new TableView<>();
            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<DisChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("EnergyAmount");
            TableColumn<DisChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<DisChargingEvent, Number> disChargingTimeCol = new TableColumn<>("DisChargTime");
            TableColumn<DisChargingEvent, Number> elapsedDisChargingTimeCol = new TableColumn<>("RemDisChargTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, amountOfEnergyCol, disChargingTimeCol, maxWaitingTimeCol, elapsedDisChargingTimeCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                disChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getDisChargingTime() / 1000));
                elapsedDisChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingDisChargingTime() / 1000));
            }
            else {
                disChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getDisChargingTime() / 60000));
                elapsedDisChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingDisChargingTime() / 60000));
            }

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
            EVLibSim.panel = "exchangesMenuItem";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();
            for (ExchangeHandler ch : currentStation.getExchangeHandlers())
                if ((ch.getChargingEvent() != null) && ch.getChargingEvent().getCondition().equals("swapping"))
                    result.add(ch.getChargingEvent());
            TableView<ChargingEvent> table = new TableView<>();
            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Long> maxWaitingTimeCol = new TableColumn<>("MaxWait");
            TableColumn<ChargingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Number> elapsedExchangeTimeCol = new TableColumn<>("RemChargTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, chargingTimeCol, maxWaitingTimeCol, elapsedExchangeTimeCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            maxWaitingTimeCol.setCellValueFactory(new PropertyValueFactory<>("maxWaitingTime"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty( (double)p.getValue().getChargingTime() / 1000));
                elapsedExchangeTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                elapsedExchangeTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }

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
            EVLibSim.panel = "parkingsMenuItem";
            ObservableList<ParkingEvent> result = FXCollections.observableArrayList();
            for (ParkingSlot ch : currentStation.getParkingSlots())
                if ((ch.getParkingEvent() != null) && (ch.getParkingEvent().getCondition().equals("parking") || ch.getParkingEvent().getCondition().equals("charging")))
                    result.add(ch.getParkingEvent());
            TableView<ParkingEvent> table = new TableView<>();
            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ParkingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Double> energyToBeReceivedCol = new TableColumn<>("EnergyReceived");
            TableColumn<ParkingEvent, Number> parkingTimeCol = new TableColumn<>("ParkTime");
            TableColumn<ParkingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ParkingEvent, Number> elapsedParkingTimeCol = new TableColumn<>("RemParkTime");
            TableColumn<ParkingEvent, Number> elapsedChargingTimeCol = new TableColumn<>("RemChargTime");
            table.getColumns().addAll(idCol, nameCol, brandCol, askingAmountCol, energyToBeReceivedCol, parkingTimeCol,
                    chargingTimeCol, elapsedParkingTimeCol, elapsedChargingTimeCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                parkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getParkingTime() / 1000));
                elapsedParkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingParkingTime() / 1000));
            }
            else {
                parkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getParkingTime() / 60000));
                elapsedParkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingParkingTime() / 60000));
            }
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 1000));
                elapsedChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                elapsedChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }

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
