package evlibsim;

import evlib.station.ChargingEvent;
import evlib.station.DisChargingEvent;
import evlib.station.ParkingEvent;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.FileChooser;
import javafx.scene.control.ToolBar;

import java.io.File;

import static evlibsim.EVLibSim.*;

class ToolBox {

    static final Button chargingLog = new Button();
    static final Button disChargingLog = new Button();
    static final Button exchangeLog = new Button();
    static final Button parkingLog = new Button();
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
        chargingLog.setTooltip(new Tooltip("Overview of created charging events. The background color of each line describes the event's current condition. " +
                "The meaning of each color is the following: 1)White-Created 2)Blue-Charging 3)Yellow-Waiting" +
                " 4)Green-Completed 5)Red-NonExecutable"));
        chargingLog.getTooltip().setPrefWidth(200);
        chargingLog.getTooltip().setWrapText(true);
        chargingLog.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            refreshButton.setDisable(false);
            EVLibSim.panel = "chargingLog";

            ObservableList<ChargingEvent> result = FXCollections.observableArrayList(ChargingEvent.chargingLog);
            TableView<ChargingEvent> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            table.setMaxSize(1000, 500);

            TableColumn<ChargingEvent, Integer> idCol = new TableColumn<>("Id");
            TableColumn<ChargingEvent, String> stationNameCol = new TableColumn<>("StationName");
            TableColumn<ChargingEvent, Double> askingAmountCol = new TableColumn<>("AskedEnergy");
            TableColumn<ChargingEvent, Double> energyToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
            TableColumn<ChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<ChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<ChargingEvent, Number> chargingTimeCol = new TableColumn<>("ChargTime");
            TableColumn<ChargingEvent, Number> remChargingTimeCol = new TableColumn<>("RemCharTime");
            TableColumn<ChargingEvent, Double> costCol = new TableColumn<>("Cost");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol,askingAmountCol, energyToBeReceivedCol, chargingTimeCol, remChargingTimeCol, costCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
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
            Label white = new Label("Created");
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

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(table, legend);

            root.setCenter(tableBox);
        });

        disChargingLog.setGraphic(new ImageView(image2));
        disChargingLog.setPrefSize(image2.getWidth(), image2.getHeight());
        disChargingLog.setTooltip(new Tooltip("Overview of created discharging events. The background color of each line describes the event's current condition. " +
                "The meaning of each color is the following: 1)White-Created 2)Blue-Discharging 3)Yellow-Waiting" +
                " 4)Green-Completed 5)Red-NonExecutable"));
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
            TableColumn<DisChargingEvent, Double> amountOfEnergyCol = new TableColumn<>("GivenAmount");
            TableColumn<DisChargingEvent, String> nameCol = new TableColumn<>("Name");
            TableColumn<DisChargingEvent, String> brandCol = new TableColumn<>("Brand");
            TableColumn<DisChargingEvent, Number> disChargingTimeCol = new TableColumn<>("DisChargTime");
            TableColumn<DisChargingEvent, Number> remDisCharTimeCol = new TableColumn<>("RemDisTime");
            TableColumn<DisChargingEvent, Double> profitCol = new TableColumn<>("Profit");

            table.getColumns().addAll(idCol, nameCol, brandCol, stationNameCol,amountOfEnergyCol, disChargingTimeCol, remDisCharTimeCol, profitCol);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            stationNameCol.setCellValueFactory(new PropertyValueFactory<>("chargingStationName"));
            amountOfEnergyCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
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
            Label white = new Label("Created");
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

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(table, legend);

            root.setCenter(tableBox);
        });

        exchangeLog.setGraphic(new ImageView(image3));
        exchangeLog.setPrefSize(image3.getWidth(), image3.getHeight());
        exchangeLog.setTooltip(new Tooltip("Overview of created battery exchange events. The background color of each line describes the event's current condition. " +
                "The meaning of each color is the following: 1)White-Created 2)Blue-Swapping 3)Yellow-Waiting" +
                " 4)Green-Completed 5)Red-NonExecutable"));
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
            Label white = new Label("Created");
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

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(table, legend);

            root.setCenter(tableBox);
        });

        parkingLog.setGraphic(new ImageView(image4));
        parkingLog.setPrefSize(image4.getWidth(), image4.getHeight());
        parkingLog.setTooltip(new Tooltip("Overview of created parking events. The background color of each line describes the event's current condition. " +
                "The meaning of each color is the following: 1)White-Created 2)Blue-Charging 3)Orange-Parking 4)Yellow-Waiting" +
                " 5)Green-Completed 6)Red-NonExecutable"));
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
            TableColumn<ParkingEvent, Double> askingAmountCol = new TableColumn<>("AskingAmount");
            TableColumn<ParkingEvent, Double> energyToBeReceivedCol = new TableColumn<>("ReceivedEnergy");
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
            askingAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountOfEnergy"));
            energyToBeReceivedCol.setCellValueFactory(new PropertyValueFactory<>("energyToBeReceived"));
            if (EVLibSim.timeUnit.getSelectionModel().getSelectedIndex() == 0) {
                parkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getParkingTime() / 1000));
                remParkTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingParkingTime() / 1000));
            }
            else {
                parkingTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getParkingTime() / 60000));
                remParkTimeCol.setCellValueFactory(p -> new SimpleDoubleProperty((double) p.getValue().getRemainingParkingTime() / 60000));
            }
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
            Label white = new Label("Created");
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

            VBox tableBox = new VBox();
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setSpacing(25);
            tableBox.getChildren().addAll(table, legend);

            root.setCenter(tableBox);
        });
    }

    private static void createOverviewMenu() {

        showTotalActivity.setGraphic(new ImageView(image5));
        showTotalActivity.setPrefSize(image5.getWidth(), image5.getHeight());
        showTotalActivity.setTooltip(new Tooltip("Overview of the charging station."));
        showTotalActivity.getTooltip().setWrapText(true);
        showTotalActivity.setOnAction(e -> View.totalActivity.fire());

        report.setGraphic(new ImageView(image6));
        report.setPrefSize(image6.getWidth(), image6.getHeight());
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

        totalEnergy.setGraphic(new ImageView(image7));
        totalEnergy.setPrefSize(image7.getWidth(), image7.getHeight());
        totalEnergy.setTooltip(new Tooltip("Total energy in the charging station."));
        totalEnergy.getTooltip().setWrapText(true);
        totalEnergy.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(600, 300);
            Label foo;
            foo = new Label("Total energy: ");
            grid.add(foo, 0, 0);
            foo = new Label(String.valueOf(currentStation.getTotalEnergy()));
            grid.add(foo, 1, 0);
            if (Maintenance.checkEnergy("Solar"))
                foo = new Label("Solar*: ");
            else
                foo = new Label("Solar: ");
            grid.add(foo, 2, 0);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("Solar")));
            grid.add(foo, 3, 0);
            if (Maintenance.checkEnergy("Wind"))
                foo = new Label("Wind*: ");
            else
                foo = new Label("Wind: ");
            grid.add(foo, 0, 1);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("Wind")));
            grid.add(foo, 1, 1);
            if (Maintenance.checkEnergy("Wave"))
                foo = new Label("Wave*: ");
            else
                foo = new Label("Wave: ");
            grid.add(foo, 2, 1);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("Wave")));
            grid.add(foo, 3, 1);
            if (Maintenance.checkEnergy("Hydroelectric"))
                foo = new Label("Hydroelectric*: ");
            else
                foo = new Label("Hydroelectric: ");
            grid.add(foo, 0, 2);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("Hydroelectric")));
            grid.add(foo, 1, 2);
            if (Maintenance.checkEnergy("Nonrenewable"))
                foo = new Label("Nonrenewable*: ");
            else
                foo = new Label("Nonrenewable: ");
            grid.add(foo, 2, 2);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("Nonrenewable")));
            grid.add(foo, 3, 2);
            if (Maintenance.checkEnergy("Geothermal"))
                foo = new Label("Geothermal*: ");
            else
                foo = new Label("Geothermal: ");
            grid.add(foo, 0, 3);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("Geothermal")));
            grid.add(foo, 1, 3);
            foo = new Label("Discharging*: ");
            grid.add(foo, 2, 3);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("DisCharging")));
            grid.add(foo, 3, 3);
            foo = new Label("*Selected");
            grid.add(foo, 0, 4);
            root.setCenter(grid);
        });
    }

    static ToolBar createToolBar() {
        bar.getItems().addAll(chargingLog, disChargingLog, exchangeLog, parkingLog,
                showTotalActivity, totalEnergy, report);
        bar.setOrientation(Orientation.VERTICAL);
        bar.getStyleClass().add("sidePanels");
        createLogButtons();
        createOverviewMenu();
        return bar;
    }
}