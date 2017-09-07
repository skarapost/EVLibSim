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
            for(int i = 0; i < currentStation.reFast().reSize(); i++)
            {
                foo = new Label("Id: " + currentStation.reFast().peek(i).reId());
                grid.add(foo, 0, j++);
                foo = new Label("Energy: " + currentStation.reFast().peek(i).reEnergyToBeReceived());
                grid.add(foo, 0, j++);
                foo = new Label("Brand: " + currentStation.reFast().peek(i).reElectricVehicle().reBrand());
                grid.add(foo, 0, j++);
                foo = new Label("Driver: " + currentStation.reFast().peek(i).reElectricVehicle().reDriver().reName());
                grid.add(foo, 0, j++);
                foo = new Label();
                grid.add(foo, 0, j++);
            }
            foo = new Label("ChargingEvent(slow)");
            grid.add(foo, 1, 0);
            j = 1;
            for(int i = 0; i < currentStation.reSlow().reSize(); i++)
            {
                foo = new Label("Id: " + currentStation.reSlow().peek(i).reId());
                grid.add(foo, 1, j++);
                foo = new Label("Energy: " + currentStation.reSlow().peek(i).reEnergyToBeReceived());
                grid.add(foo, 1, j++);
                foo = new Label("Brand: " + currentStation.reSlow().peek(i).reElectricVehicle().reBrand());
                grid.add(foo, 1, j++);
                foo = new Label("Driver: " + currentStation.reSlow().peek(i).reElectricVehicle().reDriver().reName());
                grid.add(foo, 1, j++);
                foo = new Label();
                grid.add(foo, 1, j++);
            }
            foo = new Label("DisChargingEvent");
            grid.add(foo, 2, 0);
            j = 1;
            for(int i = 0; i < currentStation.reDischarging().rSize(); i++)
            {
                foo = new Label("Id: " + currentStation.reDischarging().peek(i).reId());
                grid.add(foo, 2, j++);
                foo = new Label("Energy: " + currentStation.reDischarging().get(i).reEnergyAmount());
                grid.add(foo, 2, j++);
                foo = new Label("Brand: " + currentStation.reDischarging().get(i).reElectricVehicle().reBrand());
                grid.add(foo, 2, j++);
                foo = new Label("Driver: " + currentStation.reDischarging().get(i).reElectricVehicle().reDriver().reName());
                grid.add(foo, 2, j++);
                foo = new Label();
                grid.add(foo, 2, j++);
            }
            foo = new Label("ChargingEvent(exchange)");
            grid.add(foo, 3, 0);
            j = 1;
            for(int i = 0; i < currentStation.reExchange().reSize(); i++)
            {
                foo = new Label("Id: " + currentStation.reExchange().peek(i).reId());
                grid.add(foo, 3, j++);
                foo = new Label("Brand: " + currentStation.reExchange().peek(i).reElectricVehicle().reBrand());
                grid.add(foo, 3, j++);
                foo = new Label("Driver: " + currentStation.reExchange().peek(i).reElectricVehicle().reDriver().reName());
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
                    new Label("Cars waiting for slow charging: " + String.valueOf(currentStation.reSlow().reSize())),
                    new Label("Cars waiting for fast charging: " + String.valueOf(currentStation.reFast().reSize())),
                    new Label("Cars waiting for discharging: " + String.valueOf(currentStation.reDischarging().rSize())),
                    new Label("Cars waiting for battery exchange: " + String.valueOf(currentStation.reExchange().reSize())),
                    chargings, dischargings, exchanges, parkings);
            chargings.setPrefSize(230,30);
            dischargings.setPrefSize(230, 30);
            exchanges.setPrefSize(230, 30);
            parkings.setPrefSize(230, 30);
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for(int i=0; i<currentStation.reSources().length; i++) {
                switch (currentStation.reSources()[i]) {
                    case "solar":
                        if(currentStation.reSpecificAmount("solar") > 0) {
                            pieChartData.add(new PieChart.Data("Solar", currentStation.reSpecificAmount("solar")));
                            continue;
                        }
                        break;
                    case "wind":
                        if(currentStation.reSpecificAmount("wind") > 0) {
                            pieChartData.add(new PieChart.Data("Wind", currentStation.reSpecificAmount("wind")));
                            continue;
                        }
                        break;
                    case "wave":
                        if(currentStation.reSpecificAmount("wave") > 0) {
                            pieChartData.add(new PieChart.Data("Wave", currentStation.reSpecificAmount("wave")));
                            continue;
                        }
                        break;
                    case "hydroelectric":
                        if(currentStation.reSpecificAmount("hydroelectric") > 0) {
                            pieChartData.add(new PieChart.Data("Hydro-Electric", currentStation.reSpecificAmount("hydroelectric")));
                            continue;
                        }
                        break;
                    case "nonrenewable":
                        if(currentStation.reSpecificAmount("nonrenewable") > 0) {
                            pieChartData.add(new PieChart.Data("Non-Renewable", currentStation.reSpecificAmount("nonrenewable")));
                            continue;
                        }
                        break;
                    case "geothermal":
                        if(currentStation.reSpecificAmount("geothermal") > 0) {
                            pieChartData.add(new PieChart.Data("Geothermal", currentStation.reSpecificAmount("geothermal")));
                            continue;
                        }
                        break;
                    case "discharging":
                        if(currentStation.reSpecificAmount("discharging") > 0) {
                            pieChartData.add(new PieChart.Data("Discharging", currentStation.reSpecificAmount("discharging")));
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
            for(Charger ch: currentStation.reChargers())
                if(ch.reBusy()&&ch.reChargingEvent().reCondition().equals("charging")) {
                    z = new VBox();
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(15, 15, 15, 15));
                    boo = new Label("Id: " + ch.reChargingEvent().reId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.reChargingEvent().reElectricVehicle().reDriver().reName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.reChargingEvent().reElectricVehicle().reBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Energy: " + ch.reChargingEvent().reEnergyToBeReceived());
                    z.getChildren().add(boo);
                    boo = new Label("Kind: " + ch.reChargingEvent().reKind());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.reChargingEvent().reCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.reChargingEvent().reElapsedChargingTime()/ch.reChargingEvent().reChargingTime();
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
            for(DisCharger ch: currentStation.reDisChargers())
                if(ch.reBusy()&&ch.reDisChargingEvent().reCondition().equals("discharging")) {
                    z = new VBox();
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(15, 15, 15, 15));
                    boo = new Label("Id: " + ch.reDisChargingEvent().reId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.reDisChargingEvent().reElectricVehicle().reDriver().reName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.reDisChargingEvent().reElectricVehicle().reBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Energy: " + ch.reDisChargingEvent().reEnergyAmount());
                    z.getChildren().add(boo);
                    boo = new Label("Profit: " + ch.reDisChargingEvent().reProfit());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.reDisChargingEvent().reElapsedDisChargingTime()/ch.reDisChargingEvent().reDisChargingTime();
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
            for(ExchangeHandler ch: currentStation.reExchangeHandlers())
                if(ch.reBusy()&&ch.reChargingEvent().reCondition().equals("exchange")) {
                    z = new VBox();
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    z.setPadding(new Insets(15, 15, 15, 15));
                    boo = new Label("Id: " + ch.reChargingEvent().reId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.reChargingEvent().reElectricVehicle().reDriver().reName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.reChargingEvent().reElectricVehicle().reBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.reChargingEvent().reCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress = (float) ch.reChargingEvent().reElapsedChargingTime()/ch.reChargingEvent().reChargingTime();
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
            for(ParkingSlot ch: currentStation.reParkingSlots())
                if(ch.reBusy()&&(ch.reParkingEvent().reCondition().equals("parking"))||ch.reParkingEvent().reCondition().equals("charging")) {
                    z = new VBox();
                    z.setPadding(new Insets(15));
                    z.setSpacing(25);
                    z.setAlignment(Pos.TOP_LEFT);
                    boo = new Label("Id: " + ch.reParkingEvent().reId());
                    z.getChildren().add(boo);
                    boo = new Label("Driver: " + ch.reParkingEvent().reElectricVehicle().reDriver().reName());
                    z.getChildren().add(boo);
                    boo = new Label("Brand: " + ch.reParkingEvent().reElectricVehicle().reBrand());
                    z.getChildren().add(boo);
                    boo = new Label("Current Condition: " + ch.reParkingEvent().reCondition());
                    z.getChildren().add(boo);
                    boo = new Label("Cost: " + ch.reParkingEvent().reCost());
                    z.getChildren().add(boo);
                    ProgressBar bar = new ProgressBar();
                    z.getChildren().add(bar);
                    float progress;
                    if(ch.reParkingEvent().reCondition().equals("charging"))
                        progress = (float) ch.reParkingEvent().reElapsedChargingTime() / ch.reParkingEvent().reChargingTime();
                    else
                        progress = (float) ch.reParkingEvent().reElapsedParkingTime() / ch.reParkingEvent().reParkingTime();
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
