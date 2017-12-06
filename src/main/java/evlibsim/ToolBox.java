package evlibsim;

import evlib.station.ChargingEvent;
import evlib.station.DisChargingEvent;
import evlib.station.ParkingEvent;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

import static evlibsim.EVLibSim.*;

class ToolBox {

    static final Button slowChargingLog = new Button();
    static final Button fastChargingLog = new Button();
    static final Button disChargingLog = new Button();
    static final Button exchangeLog = new Button();
    static final Button parkingLog = new Button();
    private static final Button showTotalActivity = new Button();
    private static final Button report = new Button();
    private static final javafx.scene.control.ToolBar bar = new javafx.scene.control.ToolBar();
    private static final Image image1 = new Image(View.class.getResourceAsStream("/hist1.png"));
    private static final Image image2 = new Image(View.class.getResourceAsStream("/hist2.png"));
    private static final Image image3 = new Image(View.class.getResourceAsStream("/hist3.png"));
    private static final Image image4 = new Image(View.class.getResourceAsStream("/hist4.png"));
    private static final Image image5 = new Image(View.class.getResourceAsStream("/hist5.png"));
    private static final Image image6 = new Image(View.class.getResourceAsStream("/totalActivity.png"));
    private static final Image image7 = new Image(View.class.getResourceAsStream("/report.png"));

    private static void createLogButtons() {
        fastChargingLog.setGraphic(new ImageView(image1));
        fastChargingLog.setPrefSize(image1.getWidth(), image1.getHeight());
        fastChargingLog.setTooltip(new Tooltip("Overview of events for fast charging."));
        fastChargingLog.getTooltip().setPrefWidth(200);
        fastChargingLog.getTooltip().setWrapText(true);
        fastChargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "fastChargingLog";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (ChargingEvent event: ChargingEvent.chargingLog)
                if (event.getKindOfCharging().equals("fast"))
                    result.add(event);

            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(1000, 500);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> stationNameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Number> askingAmountCol = new TableColumn<>("AskedEnergy");
            TableColumn<ChargingEvent, Number> energyToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Number> remChargingTimeCol = new TableColumn<>("RemCharTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol,askingAmountCol, energyToBeReceivedCol, chargingTimeCol, remChargingTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0) {
                askingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy()));
                energyToBeReceivedCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getEnergyToBeReceived()));
            }
            else {
                askingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy()/1000));
                energyToBeReceivedCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getEnergyToBeReceived()/1000));
            }
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime()/1000));
                remChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                remChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

            table.setItems(result);

            table.setRowFactory(tv -> new TableRow<ChargingEvent>() {
                @Override
                public void updateItem(ChargingEvent paramP, boolean empty) {
                    if (paramP == null)
                        return;
                    String style;
                    switch (paramP.getCondition()) {
                        case "arrived":
                        case "ready":
                            style = "-fx-control-inner-background: rgba(255,255,255,0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255,255,255,0.8);";
                            setStyle(style);
                            break;
                        case "charging":
                            style = "-fx-control-inner-background: rgba(0,0,255,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,0,255,0.5);";
                            setStyle(style);
                            break;
                        case "wait":
                            style = "-fx-control-inner-background: rgba(255, 255, 0, 0.5);"
                                    + "-fx-control-inner-background-alt: rgba(255, 255, 0, 0.5);";
                            setStyle(style);
                            break;
                        case "finished":
                            style = "-fx-control-inner-background: rgba(0,128,0,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,128,0,0.5);";
                            setStyle(style);
                            break;
                        default:
                            style = "-fx-control-inner-background: rgba(255, 69, 0, 0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255, 69, 0, 0.8);";
                            setStyle(style);
                            break;
                    }
                    super.updateItem(paramP, empty);
                }
            });

            HBox legend = new HBox();
            Label white = new Label("Ready");
            white.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 255, 0.8)));
            Label blue = new Label("Charging");
            blue.setGraphic(new Rectangle(10, 10, Color.rgb(0, 0, 255, 0.5)));
            Label yellow = new Label("Waiting");
            yellow.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 0, 0.5)));
            Label green = new Label("Completed");
            green.setGraphic(new Rectangle(10, 10, Color.rgb(0, 128, 0, 0.5)));
            Label red = new Label("NonExecutable");
            red.setGraphic(new Rectangle(10, 10, Color.rgb(255, 69, 0, 0.8)));
            legend.getChildren().addAll(white, blue, yellow, green, red);
            legend.getStyleClass().add("legendBox");

            Text headLine = new Text("Total of Fast Charging Events");
            headLine.setStyle("-fx-font-family: Lato; -fx-font-size: 19; -fx-font-weight: bold;");

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(headLine, table, legend);

            root.setCenter(tableBox);
        });

        slowChargingLog.setGraphic(new ImageView(image2));
        slowChargingLog.setPrefSize(image2.getWidth(), image2.getHeight());
        slowChargingLog.setTooltip(new Tooltip("Overview of events for slow charging."));
        slowChargingLog.getTooltip().setPrefWidth(200);
        slowChargingLog.getTooltip().setWrapText(true);
        slowChargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "slowChargingLog";
            ObservableList<ChargingEvent> result = FXCollections.observableArrayList();

            for (ChargingEvent event: ChargingEvent.chargingLog)
                if (event.getKindOfCharging().equals("slow"))
                    result.add(event);

            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(1000, 500);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> stationNameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Number> askingAmountCol = new TableColumn<>("AskedEnergy");
            TableColumn<ChargingEvent, Number> energyToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Number> remChargingTimeCol = new TableColumn<>("RemCharTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol,askingAmountCol, energyToBeReceivedCol, chargingTimeCol, remChargingTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0) {
                askingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy()));
                energyToBeReceivedCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getEnergyToBeReceived()));
            }
            else {
                askingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy()/1000));
                energyToBeReceivedCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getEnergyToBeReceived()/1000));
            }
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime()/1000));
                remChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                remChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));

            table.setItems(result);

            table.setRowFactory(tv -> new TableRow<ChargingEvent>() {
                @Override
                public void updateItem(ChargingEvent paramP, boolean empty) {
                    if (paramP == null)
                        return;
                    String style;
                    switch (paramP.getCondition()) {
                        case "arrived":
                        case "ready":
                            style = "-fx-control-inner-background: rgba(255,255,255,0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255,255,255,0.8);";
                            setStyle(style);
                            break;
                        case "charging":
                            style = "-fx-control-inner-background: rgba(0,0,255,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,0,255,0.5);";
                            setStyle(style);
                            break;
                        case "wait":
                            style = "-fx-control-inner-background: rgba(255, 255, 0, 0.5);"
                                    + "-fx-control-inner-background-alt: rgba(255, 255, 0, 0.5);";
                            setStyle(style);
                            break;
                        case "finished":
                            style = "-fx-control-inner-background: rgba(0,128,0,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,128,0,0.5);";
                            setStyle(style);
                            break;
                        default:
                            style = "-fx-control-inner-background: rgba(255, 69, 0, 0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255, 69, 0, 0.8);";
                            setStyle(style);
                            break;
                    }
                    super.updateItem(paramP, empty);
                }
            });

            HBox legend = new HBox();
            Label white = new Label("Ready");
            white.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 255, 0.8)));
            Label blue = new Label("Charging");
            blue.setGraphic(new Rectangle(10, 10, Color.rgb(0, 0, 255, 0.5)));
            Label yellow = new Label("Waiting");
            yellow.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 0, 0.5)));
            Label green = new Label("Completed");
            green.setGraphic(new Rectangle(10, 10, Color.rgb(0, 128, 0, 0.5)));
            Label red = new Label("NonExecutable");
            red.setGraphic(new Rectangle(10, 10, Color.rgb(255, 69, 0, 0.8)));
            legend.getChildren().addAll(white, blue, yellow, green, red);
            legend.getStyleClass().add("legendBox");

            Text headLine = new Text("Total of Slow Charging Events");
            headLine.setStyle("-fx-font-family: Lato; -fx-font-size: 19; -fx-font-weight: bold;");

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(headLine, table, legend);

            root.setCenter(tableBox);
        });

        disChargingLog.setGraphic(new ImageView(image3));
        disChargingLog.setPrefSize(image3.getWidth(), image3.getHeight());
        disChargingLog.setTooltip(new Tooltip("Overview of discharging events."));
        disChargingLog.getTooltip().setPrefWidth(200);
        disChargingLog.getTooltip().setWrapText(true);
        disChargingLog.setOnAction((ActionEvent e) -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "disChargingLog";

            ObservableList<DisChargingEvent> result = FXCollections.observableArrayList(DisChargingEvent.dischargingLog);
            TableView<DisChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(1000, 500);

            TableColumn<DisChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<DisChargingEvent, String> stationNameCol = new TableColumn<>("StationName");
            TableColumn<DisChargingEvent, Number> amountOfEnergyCol = new TableColumn<>("GivenAmount");
            TableColumn<DisChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<DisChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<DisChargingEvent, Number> disChargingTimeCol = new TableColumn<>("DisChargTime");
            TableColumn<DisChargingEvent, Number> remDisCharTimeCol = new TableColumn<>("RemDisTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol,amountOfEnergyCol, disChargingTimeCol, remDisCharTimeCol, profitCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                amountOfEnergyCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy()));
            else
                amountOfEnergyCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy() / 1000));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                disChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getDisChargingTime() / 1000));
                remDisCharTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingDisChargingTime() / 1000));
            }
            else {
                disChargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getDisChargingTime() / 60000));
                remDisCharTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingDisChargingTime() / 60000));
            }
            profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));
            table.setItems(result);

            table.setRowFactory(tv -> new TableRow<DisChargingEvent>() {
                @Override
                public void updateItem(DisChargingEvent paramP, boolean empty) {
                    if (paramP == null)
                        return;
                    String style;
                    switch (paramP.getCondition()) {
                        case "arrived":
                        case "ready":
                            style = "-fx-control-inner-background: rgba(255,255,255,0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255,255,255,0.8);";
                            setStyle(style);
                            break;
                        case "discharging":
                            style = "-fx-control-inner-background: rgba(0,0,255,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,0,255,0.5);";
                            setStyle(style);
                            break;
                        case "wait":
                            style = "-fx-control-inner-background: rgba(255, 255, 0, 0.5);"
                                    + "-fx-control-inner-background-alt: rgba(255, 255, 0, 0.5);";
                            setStyle(style);
                            break;
                        case "finished":
                            style = "-fx-control-inner-background: rgba(0,128,0,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,128,0,0.5);";
                            setStyle(style);
                            break;
                        default:
                            style = "-fx-control-inner-background: rgba(255, 69, 0, 0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255, 69, 0, 0.8);";
                            setStyle(style);
                            break;
                    }
                    super.updateItem(paramP, empty);
                }
            });

            HBox legend = new HBox();
            Label white = new Label("Ready");
            white.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 255, 0.8)));
            Label blue = new Label("Discharging");
            blue.setGraphic(new Rectangle(10, 10, Color.rgb(0, 0, 255, 0.5)));
            Label yellow = new Label("Waiting");
            yellow.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 0, 0.5)));
            Label green = new Label("Completed");
            green.setGraphic(new Rectangle(10, 10, Color.rgb(0, 128, 0, 0.5)));
            Label red = new Label("NonExecutable");
            red.setGraphic(new Rectangle(10, 10, Color.rgb(255, 69, 0, 0.8)));
            legend.getChildren().addAll(white, blue, yellow, green, red);
            legend.getStyleClass().add("legendBox");

            Text headLine = new Text("Total of Discharging Events");
            headLine.setStyle("-fx-font-family: Lato; -fx-font-size: 19; -fx-font-weight: bold;");

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(headLine, table, legend);

            root.setCenter(tableBox);
        });

        exchangeLog.setGraphic(new ImageView(image4));
        exchangeLog.setPrefSize(image4.getWidth(), image4.getHeight());
        exchangeLog.setTooltip(new Tooltip("Overview of battery exchange events."));
        exchangeLog.getTooltip().setPrefWidth(200);
        exchangeLog.getTooltip().setWrapText(true);
        exchangeLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "exchangeLog";

            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.exchangeLog);
            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(1000, 500);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> stationNameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Number> remCharTimeCol = new TableColumn<>("RemCharTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol, chargingTimeCol, remCharTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 1000));
                remCharTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                remCharTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);

            table.setRowFactory(tv -> new TableRow<ChargingEvent>() {
                @Override
                public void updateItem(ChargingEvent paramP, boolean empty) {
                    if (paramP == null)
                        return;
                    String style;
                    switch (paramP.getCondition()) {
                        case "arrived":
                        case "ready":
                            style = "-fx-control-inner-background: rgba(255,255,255,0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255,255,255,0.8);";
                            setStyle(style);
                            break;
                        case "swapping":
                            style = "-fx-control-inner-background: rgba(0,0,255,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,0,255,0.5);";
                            setStyle(style);
                            break;
                        case "wait":
                            style = "-fx-control-inner-background: rgba(255, 255, 0, 0.5);"
                                    + "-fx-control-inner-background-alt: rgba(255, 255, 0, 0.5);";
                            setStyle(style);
                            break;
                        case "finished":
                            style = "-fx-control-inner-background: rgba(0,128,0,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,128,0,0.5);";
                            setStyle(style);
                            break;
                        default:
                            style = "-fx-control-inner-background: rgba(255, 69, 0, 0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255, 69, 0, 0.8);";
                            setStyle(style);
                            break;
                    }
                    super.updateItem(paramP, empty);
                }
            });

            HBox legend = new HBox();
            Label white = new Label("Ready");
            white.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 255, 0.8)));
            Label blue = new Label("Swapping");
            blue.setGraphic(new Rectangle(10, 10, Color.rgb(0, 0, 255, 0.5)));
            Label yellow = new Label("Waiting");
            yellow.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 0, 0.5)));
            Label green = new Label("Completed");
            green.setGraphic(new Rectangle(10, 10, Color.rgb(0, 128, 0, 0.5)));
            Label red = new Label("NonExecutable");
            red.setGraphic(new Rectangle(10, 10, Color.rgb(255, 69, 0, 0.8)));
            legend.getChildren().addAll(white, blue, yellow, green, red);
            legend.getStyleClass().add("legendBox");

            Text headLine = new Text("Total of Battery Exchange Events");
            headLine.setStyle("-fx-font-family: Lato; -fx-font-size: 19; -fx-font-weight: bold;");

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(headLine, table, legend);

            root.setCenter(tableBox);
        });

        parkingLog.setGraphic(new ImageView(image5));
        parkingLog.setPrefSize(image5.getWidth(), image5.getHeight());
        parkingLog.setTooltip(new Tooltip("Overview of parking/inductive charging events."));
        parkingLog.getTooltip().setPrefWidth(200);
        parkingLog.getTooltip().setWrapText(true);
        parkingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "parkingLog";

            ObservableList<ParkingEvent> result = FXCollections.observableArrayList(ParkingEvent.parkLog);
            TableView<ParkingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(1000, 500);

            TableColumn<ParkingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ParkingEvent, String> stationNameCol = new TableColumn<>("StationName");
            TableColumn<ParkingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ParkingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ParkingEvent, Number> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Number> energyToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
            TableColumn<ParkingEvent, Number> parkingTimeCol = new TableColumn<>("ParkTime");
            TableColumn<ParkingEvent, Number> remParkTimeCol = new TableColumn<>("RemParkTime");
            TableColumn<ParkingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ParkingEvent, Number> remCharTimeCol = new TableColumn<>("RemCharTime");
            TableColumn<ParkingEvent, Double> costCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol, askingAmountCol,
                    energyToBeReceivedCol, parkingTimeCol, remParkTimeCol, chargingTimeCol, remCharTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getDriver().getName()));
            brandCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getElectricVehicle().getBrand()));
            if (energyUnit.getSelectionModel().getSelectedIndex() == 0) {
                askingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy()));
                energyToBeReceivedCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getEnergyToBeReceived()));
            }
            else {
                askingAmountCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getAmountOfEnergy() / 1000));
                energyToBeReceivedCol.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().getEnergyToBeReceived() / 1000));
            }
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                parkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getParkingTime() / 1000));
                remParkTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingParkingTime() / 1000));
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 1000));
                remCharTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 1000));
            }
            else {
                parkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getParkingTime() / 60000));
                remParkTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingParkingTime() / 60000));
                chargingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getChargingTime() / 60000));
                remCharTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingChargingTime() / 60000));
            }
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            table.setItems(result);

            table.setRowFactory(tv -> new TableRow<ParkingEvent>() {
                @Override
                public void updateItem(ParkingEvent paramP, boolean empty) {
                    if (paramP == null)
                        return;
                    String style;
                    switch (paramP.getCondition()) {
                        case "arrived":
                        case "ready":
                            style = "-fx-control-inner-background: rgba(255,255,255,0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255,255,255,0.8);";
                            setStyle(style);
                            break;
                        case "charging":
                            style = "-fx-control-inner-background: rgb(30, 144, 255);"
                                    + "-fx-control-inner-background-alt: rgb(30, 144, 255);";
                            setStyle(style);
                            break;
                        case "parking":
                            style = "-fx-control-inner-background: rgba(255, 165, 0, 0.5);"
                                    + "-fx-control-inner-background-alt: rgba(255, 165, 0, 0.5);";
                            setStyle(style);
                            break;
                        case "wait":
                            style = "-fx-control-inner-background: rgba(255, 255, 0, 0.5);"
                                    + "-fx-control-inner-background-alt: rgba(255, 255, 0, 0.5);";
                            setStyle(style);
                            break;
                        case "finished":
                            style = "-fx-control-inner-background: rgba(0,128,0,0.5);"
                                    + "-fx-control-inner-background-alt: rgba(0,128,0,0.5);";
                            setStyle(style);
                            break;
                        default:
                            style = "-fx-control-inner-background: rgba(255, 69, 0, 0.8);"
                                    + "-fx-control-inner-background-alt: rgba(255, 69, 0, 0.8);";
                            setStyle(style);
                            break;
                    }
                    super.updateItem(paramP, empty);
                }
            });

            HBox legend = new HBox();
            Label white = new Label("Ready");
            white.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 255, 0.8)));
            Label blue = new Label("Charging");
            blue.setGraphic(new Rectangle(10, 10, Color.rgb(0, 0, 255, 0.5)));
            Label orange = new Label("Parking");
            orange.setGraphic(new Rectangle(10, 10, Color.rgb(255, 165, 0, 0.5)));
            Label yellow = new Label("Waiting");
            yellow.setGraphic(new Rectangle(10, 10, Color.rgb(255, 255, 0, 0.5)));
            Label green = new Label("Completed");
            green.setGraphic(new Rectangle(10, 10, Color.rgb(0, 128, 0, 0.5)));
            Label red = new Label("NonExecutable");
            red.setGraphic(new Rectangle(10, 10, Color.rgb(255, 69, 0, 0.8)));
            legend.getChildren().addAll(white, blue, orange, yellow, green, red);
            legend.getStyleClass().add("legendBox");

            Text headLine = new Text("Total of Parking Events");
            headLine.setStyle("-fx-font-family: Lato; -fx-font-size: 19; -fx-font-weight: bold;");

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(headLine, table, legend);

            root.setCenter(tableBox);
        });
    }

    private static void createOverviewMenu() {

        showTotalActivity.setGraphic(new ImageView(image6));
        showTotalActivity.setPrefSize(image6.getWidth(), image6.getHeight());
        showTotalActivity.setTooltip(new Tooltip("Overview of the charging station."));
        showTotalActivity.getTooltip().setWrapText(true);
        showTotalActivity.setOnAction(e -> View.totalActivity.fire());

        report.setGraphic(new ImageView(image7));
        report.setPrefSize(image7.getWidth(), image7.getHeight());
        report.setTooltip(new Tooltip("Report of the selected charging station."));
        report.getTooltip().setWrapText(true);
        report.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Generate Report");
            fileChooser.setInitialFileName("report.txt");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files(.txt)", "*.txt"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null)
                currentStation.genReport(selectedFile.getPath());
        });
    }

    static ToolBar createToolBar() {
        bar.getItems().addAll(fastChargingLog, slowChargingLog, disChargingLog, exchangeLog, parkingLog,
                showTotalActivity, report);
        bar.setOrientation(Orientation.VERTICAL);
        bar.getStyleClass().add("sidePanels");
        bar.setStyle("-fx-spacing: 8px;");
        createLogButtons();
        createOverviewMenu();
        return bar;
    }
}