package evlibsim;

import EVLib.Events.ChargingEvent;
import EVLib.Events.DisChargingEvent;
import EVLib.Station.Charger;
import EVLib.Station.DisCharger;
import EVLib.Station.ExchangeHandler;
import EVLib.Station.ParkingSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static evlibsim.EVLibSim.*;

class View {

    private static final Menu overview = new Menu("Station Overview");
    private static final Menu view = new Menu("View");
    static final MenuItem totalActivity = new MenuItem("Overview");
    private static final MenuItem queue = new MenuItem("Queue of Events");
    private static final MenuItem chargingsMenuItem = new MenuItem("Running chargings");
    private static final MenuItem dischargingsMenuItem = new MenuItem("Running dischargings");
    private static final MenuItem exchangesMenuItem = new MenuItem("Running swappings");
    private static final MenuItem parkingsMenuItem = new MenuItem("Running parkings");
    private static final Button chargings = new Button("Running chargings");
    private static final Button dischargings = new Button("Running dischargings");
    private static final Button exchanges = new Button("Running swappings");
    private static final Button parkings = new Button("Runinng parkings");
    private static final Image image = new Image(View.class.getResourceAsStream("run.png"));

    static Menu createViewMenu()
    {
        overview.getItems().addAll(totalActivity, new SeparatorMenuItem(), chargingsMenuItem, dischargingsMenuItem, exchangesMenuItem, parkingsMenuItem);
        view.getItems().addAll(overview, queue);

        queue.setOnAction(e -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(800, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            x.setSpacing(15);
            HBox z;
            Label foo;
            Button execution;
            for(int i = 0; i < currentStation.getFast().getSize(); i++)
            {
                ChargingEvent et = (ChargingEvent) currentStation.getFast().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Charging");
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
                    if(!currentStation.getQueueHandling()) {
                        et.preProcessing();
                        if (et.getCondition().equals("charging")) {
                            et.execution();
                            queue.fire();
                        }
                        else if (et.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            for(int i = 0; i < currentStation.getSlow().getSize(); i++)
            {
                ChargingEvent et = (ChargingEvent) currentStation.getSlow().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("DisCharging");
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
                    if(!currentStation.getQueueHandling()) {
                        et.preProcessing();
                        if (et.getCondition().equals("charging")) {
                            et.execution();
                            queue.fire();
                        }
                        else if (et.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            for(int i = 0; i < currentStation.getDischarging().getSize(); i++)
            {
                DisChargingEvent r = (DisChargingEvent) currentStation.getDischarging().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Exchange");
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
                    if(!currentStation.getQueueHandling()) {
                        r.preProcessing();
                        if (r.getCondition().equals("discharging")) {
                            r.execution();
                            queue.fire();
                        }
                        else if (r.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("The manual queue handling is disabled.");
                        alert.showAndWait();
                    }
                });
                x.getChildren().add(z);
            }
            for(int i = 0; i < currentStation.getExchange().getSize(); i++)
            {
                ChargingEvent et = (ChargingEvent) currentStation.getExchange().get(i);
                z = new HBox();
                z.setSpacing(15);
                z.setAlignment(Pos.TOP_LEFT);
                z.setPadding(new Insets(5, 5, 5, 5));
                foo = new Label("Parking");
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
                    if(!currentStation.getQueueHandling()) {
                        et.preProcessing();
                        if (et.getCondition().equals("swapping")) {
                            et.execution();
                            queue.fire();
                        }
                        else if (et.getCondition().equals("wait"))
                            Maintenance.queueInsertion();
                    }
                    else
                    {
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
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            Label title = new Label("Running Events");
            title.setAlignment(Pos.CENTER);
            title.setStyle("-fx-font-weight: bold;");
            VBox box = new VBox();
            box.setPadding(new Insets(25));
            box.setSpacing(25);
            box.getChildren().addAll(new Label(stationName.getText()),
                    new Label(totalChargers.getText()),
                    new Label(totalDisChargers.getText()),
                    new Label(totalExchange.getText()),
                    new Label(totalParkingSlots.getText()),
                    new Label("Cars waiting for slow charging: " + String.valueOf(currentStation.getSlow().getSize())),
                    new Label("Cars waiting for fast charging: " + String.valueOf(currentStation.getFast().getSize())),
                    new Label("Cars waiting for discharging: " + String.valueOf(currentStation.getDischarging().getSize())),
                    new Label("Cars waiting for battery exchange: " + String.valueOf(currentStation.getExchange().getSize())),
                    title,
                    chargings, dischargings, exchanges, parkings);
            chargings.setPrefSize(250,30);
            dischargings.setPrefSize(250, 30);
            exchanges.setPrefSize(250, 30);
            parkings.setPrefSize(250, 30);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for(int i=0; i<currentStation.getSources().length; i++) {
                switch (currentStation.getSources()[i]) {
                    case "Solar":
                        if(currentStation.getSpecificAmount("Solar") > 0) {
                            pieChartData.add(new PieChart.Data("Solar", currentStation.getSpecificAmount("Solar")));
                            continue;
                        }
                        break;
                    case "Wind":
                        if(currentStation.getSpecificAmount("Wind") > 0) {
                            pieChartData.add(new PieChart.Data("Wind", currentStation.getSpecificAmount("Wind")));
                            continue;
                        }
                        break;
                    case "Wave":
                        if(currentStation.getSpecificAmount("Wave") > 0) {
                            pieChartData.add(new PieChart.Data("Wave", currentStation.getSpecificAmount("Wave")));
                            continue;
                        }
                        break;
                    case "Hydroelectric":
                        if(currentStation.getSpecificAmount("Hydroelectric") > 0) {
                            pieChartData.add(new PieChart.Data("Hydroelectric", currentStation.getSpecificAmount("Hydroelectric")));
                            continue;
                        }
                        break;
                    case "Nonrenewable":
                        if(currentStation.getSpecificAmount("Nonrenewable") > 0) {
                            pieChartData.add(new PieChart.Data("Nonrenewable", currentStation.getSpecificAmount("Nonrenewable")));
                            continue;
                        }
                        break;
                    case "Geothermal":
                        if(currentStation.getSpecificAmount("Geothermal") > 0) {
                            pieChartData.add(new PieChart.Data("Geothermal", currentStation.getSpecificAmount("Geothermal")));
                            continue;
                        }
                        break;
                    case "DisCharging":
                        if(currentStation.getSpecificAmount("DisCharging") > 0) {
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
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(700, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            x.setSpacing(15);
            HBox z;
            Label boo;
            for(Charger ch: currentStation.getChargers())
                if((ch.getChargingEvent()!=null)&&ch.getChargingEvent().getCondition().equals("charging")) {
                    z = new HBox();
                    z.setSpacing(15);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(5, 5, 5, 5));
                    boo = new Label("Id: " + ch.getChargingEvent().getId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.getChargingEvent().getElectricVehicle().getDriver().getName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.getChargingEvent().getElectricVehicle().getBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Energy: " + ch.getChargingEvent().getEnergyToBeReceived());
                    z.getChildren().add(boo);
                    boo = new Label("Kind: " + ch.getChargingEvent().getKindOfCharging());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.getChargingEvent().getCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.getChargingEvent().getElapsedChargingTime()/ch.getChargingEvent().getChargingTime();
                    bar.setProgress(progress);
                    x.getChildren().add(z);
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        dischargings.setOnAction(et -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(700, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            x.setSpacing(15);
            HBox z;
            Label boo;
            for(DisCharger ch: currentStation.getDisChargers())
                if((ch.getDisChargingEvent()!=null)&&ch.getDisChargingEvent().getCondition().equals("discharging")) {
                    z = new HBox();
                    z.setSpacing(15);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(5, 5, 5, 5));
                    boo = new Label("Id: " + ch.getDisChargingEvent().getId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.getDisChargingEvent().getElectricVehicle().getDriver().getName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.getDisChargingEvent().getElectricVehicle().getBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Energy: " + ch.getDisChargingEvent().getAmountOfEnergy());
                    z.getChildren().add(boo);
                    boo = new Label("Profit: " + ch.getDisChargingEvent().getProfit());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.getDisChargingEvent().getElapsedDisChargingTime()/ch.getDisChargingEvent().getDisChargingTime();
                    bar.setProgress(progress);
                    x.getChildren().add(z);
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        exchanges.setOnAction(et -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(700, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            x.setSpacing(15);
            HBox z;
            Label boo;
            for(ExchangeHandler ch: currentStation.getExchangeHandlers())
                if((ch.getChargingEvent()!=null)&&ch.getChargingEvent().getCondition().equals("swapping")) {
                    z = new HBox();
                    z.setSpacing(15);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(5, 5, 5, 5));
                    boo = new Label("Id: " + ch.getChargingEvent().getId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.getChargingEvent().getElectricVehicle().getDriver().getName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.getChargingEvent().getElectricVehicle().getBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.getChargingEvent().getCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.getChargingEvent().getElapsedChargingTime()/ch.getChargingEvent().getChargingTime();
                    bar.setProgress(progress);
                    x.getChildren().add(z);
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        parkings.setOnAction(et -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(700, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            x.setSpacing(15);
            HBox z;
            Label boo;
            for(ParkingSlot ch: currentStation.getParkingSlots())
                if((ch.getParkingEvent()!=null)&&(ch.getParkingEvent().getCondition().equals("parking")||ch.getParkingEvent().getCondition().equals("charging"))) {
                    z = new HBox();
                    z.setSpacing(15);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(5, 5, 5, 5));
                    boo = new Label("Id: " + ch.getParkingEvent().getId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.getParkingEvent().getElectricVehicle().getDriver().getName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.getParkingEvent().getElectricVehicle().getBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Condition: " + ch.getParkingEvent().getCondition());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.getParkingEvent().getCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress;
                    if(ch.getParkingEvent().getCondition().equals("charging"))
                        progress = (float) ch.getParkingEvent().getElapsedChargingTime() / ch.getParkingEvent().getChargingTime();
                    else
                        progress = (float) ch.getParkingEvent().getElapsedParkingTime() / ch.getParkingEvent().getParkingTime();
                    bar.setProgress(progress);
                    x.getChildren().add(z);
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        return view;
    }
}
