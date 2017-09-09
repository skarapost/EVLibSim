package evlibsim;

import Station.Charger;
import Station.DisCharger;
import Station.ExchangeHandler;
import Station.ParkingSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static evlibsim.EVLibSim.*;

class View {

    private static final Menu overview = new Menu("Station Overview");
    private static final Menu view = new Menu("View");
    static final MenuItem totalActivity = new MenuItem("Overview");
    private static final MenuItem queue = new MenuItem("Queue of Events");
    private static final MenuItem chargingsMenuItem = new MenuItem("Chargings Events");
    private static final MenuItem dischargingsMenuItem = new MenuItem("DisChargings Events");
    private static final MenuItem exchangesMenuItem = new MenuItem("Battery Swapings Events");
    private static final MenuItem parkingsMenuItem = new MenuItem("Parking Events");
    private static final Button chargings = new Button("Chargings Events");
    private static final Button dischargings = new Button("DisChargings Events");
    private static final Button exchanges = new Button("Battery Exchanges Events");
    private static final Button parkings = new Button("Parkings Events");

    static Menu createViewMenu()
    {
        overview.getItems().addAll(totalActivity, new SeparatorMenuItem(), chargingsMenuItem, dischargingsMenuItem, exchangesMenuItem, parkingsMenuItem);
        view.getItems().addAll(overview, queue);

        queue.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 600);
            energies.clear();
            Label foo;
            foo = new Label("ChargingEvent(fast)");
            grid.add(foo, 0, 0);
            int j = 1;
            for(int i = 0; i < currentStation.getFast().size(); i++)
            {
                foo = new Label("Id: " + currentStation.getFast().peek(i).getId());
                grid.add(foo, 0, j++);
                foo = new Label("Energy: " + currentStation.getFast().peek(i).getEnergyToBeReceived());
                grid.add(foo, 0, j++);
                foo = new Label("Brand: " + currentStation.getFast().peek(i).getElectricVehicle().getBrand());
                grid.add(foo, 0, j++);
                foo = new Label("Driver: " + currentStation.getFast().peek(i).getElectricVehicle().getDriver().getName());
                grid.add(foo, 0, j++);
                foo = new Label();
                grid.add(foo, 0, j++);
            }
            foo = new Label("ChargingEvent(slow)");
            grid.add(foo, 1, 0);
            j = 1;
            for(int i = 0; i < currentStation.getSlow().size(); i++)
            {
                foo = new Label("Id: " + currentStation.getSlow().peek(i).getId());
                grid.add(foo, 1, j++);
                foo = new Label("Energy: " + currentStation.getSlow().peek(i).getEnergyToBeReceived());
                grid.add(foo, 1, j++);
                foo = new Label("Brand: " + currentStation.getSlow().peek(i).getElectricVehicle().getBrand());
                grid.add(foo, 1, j++);
                foo = new Label("Driver: " + currentStation.getSlow().peek(i).getElectricVehicle().getDriver().getName());
                grid.add(foo, 1, j++);
                foo = new Label();
                grid.add(foo, 1, j++);
            }
            foo = new Label("DisChargingEvent");
            grid.add(foo, 2, 0);
            j = 1;
            for(int i = 0; i < currentStation.getDischarging().getSize(); i++)
            {
                foo = new Label("Id: " + currentStation.getDischarging().peek(i).getId());
                grid.add(foo, 2, j++);
                foo = new Label("Energy: " + currentStation.getDischarging().get(i).getAmountOfEnergy());
                grid.add(foo, 2, j++);
                foo = new Label("Brand: " + currentStation.getDischarging().get(i).getElectricVehicle().getBrand());
                grid.add(foo, 2, j++);
                foo = new Label("Driver: " + currentStation.getDischarging().get(i).getElectricVehicle().getDriver().getName());
                grid.add(foo, 2, j++);
                foo = new Label();
                grid.add(foo, 2, j++);
            }
            foo = new Label("ChargingEvent(exchange)");
            grid.add(foo, 3, 0);
            j = 1;
            for(int i = 0; i < currentStation.getExchange().size(); i++)
            {
                foo = new Label("Id: " + currentStation.getExchange().peek(i).getId());
                grid.add(foo, 3, j++);
                foo = new Label("Brand: " + currentStation.getExchange().peek(i).getElectricVehicle().getBrand());
                grid.add(foo, 3, j++);
                foo = new Label("Driver: " + currentStation.getExchange().peek(i).getElectricVehicle().getDriver().getName());
                grid.add(foo, 3, j++);
                foo = new Label();
                grid.add(foo, 3, j++);
            }
            root.setCenter(grid);
        });
        totalActivity.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            box.setPadding(new Insets(25));
            box.setSpacing(25);
            box.getChildren().addAll(new Label(stationName.getText()),
                    new Label(totalChargers.getText()),
                    new Label(totalDisChargers.getText()),
                    new Label(totalExchange.getText()),
                    new Label(totalParkingSlots.getText()),
                    new Label("Cars waiting for slow charging: " + String.valueOf(currentStation.getSlow().size())),
                    new Label("Cars waiting for fast charging: " + String.valueOf(currentStation.getFast().size())),
                    new Label("Cars waiting for discharging: " + String.valueOf(currentStation.getDischarging().getSize())),
                    new Label("Cars waiting for battery exchange: " + String.valueOf(currentStation.getExchange().size())),
                    chargings, dischargings, exchanges, parkings);
            chargings.setPrefSize(230,30);
            dischargings.setPrefSize(230, 30);
            exchanges.setPrefSize(230, 30);
            parkings.setPrefSize(230, 30);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for(int i=0; i<currentStation.getSources().length; i++) {
                switch (currentStation.getSources()[i]) {
                    case "solar":
                        if(currentStation.getSpecificAmount("solar") > 0) {
                            pieChartData.add(new PieChart.Data("Solar", currentStation.getSpecificAmount("solar")));
                            continue;
                        }
                        break;
                    case "wind":
                        if(currentStation.getSpecificAmount("wind") > 0) {
                            pieChartData.add(new PieChart.Data("Wind", currentStation.getSpecificAmount("wind")));
                            continue;
                        }
                        break;
                    case "wave":
                        if(currentStation.getSpecificAmount("wave") > 0) {
                            pieChartData.add(new PieChart.Data("Wave", currentStation.getSpecificAmount("wave")));
                            continue;
                        }
                        break;
                    case "hydroelectric":
                        if(currentStation.getSpecificAmount("hydroelectric") > 0) {
                            pieChartData.add(new PieChart.Data("Hydro-Electric", currentStation.getSpecificAmount("hydroelectric")));
                            continue;
                        }
                        break;
                    case "nonrenewable":
                        if(currentStation.getSpecificAmount("nonrenewable") > 0) {
                            pieChartData.add(new PieChart.Data("Non-Renewable", currentStation.getSpecificAmount("nonrenewable")));
                            continue;
                        }
                        break;
                    case "geothermal":
                        if(currentStation.getSpecificAmount("geothermal") > 0) {
                            pieChartData.add(new PieChart.Data("Geothermal", currentStation.getSpecificAmount("geothermal")));
                            continue;
                        }
                        break;
                    case "discharging":
                        if(currentStation.getSpecificAmount("discharging") > 0) {
                            pieChartData.add(new PieChart.Data("Discharging", currentStation.getSpecificAmount("discharging")));
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
            scroll.setMaxSize(820, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            HBox y = new HBox();
            VBox z;
            Label boo;
            int column = 0;
            x.getChildren().add(y);
            for(Charger ch: currentStation.getChargers())
                if(ch.getBusy()&&ch.getChargingEvent().getCondition().equals("charging")) {
                    z = new VBox();
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(15, 15, 15, 15));
                    boo = new Label("Id: " + ch.getChargingEvent().getId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.getChargingEvent().getElectricVehicle().getDriver().getName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.getChargingEvent().getElectricVehicle().getBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Energy: " + ch.getChargingEvent().getEnergyToBeReceived());
                    z.getChildren().add(boo);
                    boo = new Label("Kind: " + ch.getChargingEvent().getKind());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.getChargingEvent().getCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.getChargingEvent().getElapsedChargingTime()/ch.getChargingEvent().getChargingTime();
                    bar.setProgress(progress);
                    if(column <= 5) {
                        y.getChildren().add(z);
                        ++column;
                    }
                    else
                    {
                        column = 0;
                        y = new HBox();
                        y.getChildren().add(z);
                        x.getChildren().add(y);
                    }
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        dischargings.setOnAction(et -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(820, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            HBox y = new HBox();
            VBox z;
            Label boo;
            int column = 0;
            x.getChildren().add(y);
            for(DisCharger ch: currentStation.getDisChargers())
                if(ch.getBusy()&&ch.getDisChargingEvent().getCondition().equals("discharging")) {
                    z = new VBox();
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(15, 15, 15, 15));
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
                    if(column <= 5) {
                        y.getChildren().add(z);
                        ++column;
                    }
                    else
                    {
                        column = 0;
                        y = new HBox();
                        y.getChildren().add(z);
                        x.getChildren().add(y);
                    }
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        exchanges.setOnAction(et -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(820, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            HBox y = new HBox();
            VBox z;
            Label boo;
            int column = 0;
            x.getChildren().add(y);
            for(ExchangeHandler ch: currentStation.getExchangeHandlers())
                if(ch.getBusy()&&ch.getChargingEvent().getCondition().equals("exchange")) {
                    z = new VBox();
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(15, 15, 15, 15));
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
                    if(column <= 5) {
                        y.getChildren().add(z);
                        ++column;
                    }
                    else
                    {
                        column = 0;
                        y = new HBox();
                        y.getChildren().add(z);
                        x.getChildren().add(y);
                    }
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        parkings.setOnAction(et -> {
            Maintenance.cleanScreen();
            ScrollPane scroll = new ScrollPane();
            scroll.setMaxSize(820, 650);
            VBox x = new VBox();
            x.setAlignment(Pos.CENTER);
            HBox y = new HBox();
            VBox z;
            Label boo;
            int column = 0;
            x.getChildren().add(y);
            for(ParkingSlot ch: currentStation.getParkingSlots())
                if(ch.getBusy()&&(ch.getParkingEvent().getCondition().equals("parking"))||ch.getParkingEvent().getCondition().equals("charging")) {
                    z = new VBox();
                    z.setPadding(new Insets(15));
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    boo = new Label("Id: " + ch.getParkingEvent().getId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.getParkingEvent().getElectricVehicle().getDriver().getName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.getParkingEvent().getElectricVehicle().getBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Current Condition: " + ch.getParkingEvent().getCondition());
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
                    if(column <= 5) {
                        y.getChildren().add(z);
                        ++column;
                    }
                    else
                    {
                        column = 0;
                        y = new HBox();
                        y.getChildren().add(z);
                        x.getChildren().add(y);
                    }
                }
            scroll.setContent(x);
            root.setCenter(scroll);
        });
        return view;
    }
}
