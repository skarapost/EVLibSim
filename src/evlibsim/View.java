package evlibsim;

import Station.Charger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static evlibsim.EVLibSim.*;

class View {

    private static final MenuItem totalEnergy = new MenuItem("Total energy");
    static final MenuItem totalActivity = new MenuItem("Total Activity");
    private static final MenuItem queue = new MenuItem("Queue of events");
    private static final MenuItem about = new MenuItem("About");
    private static final Menu view = new Menu("View");

    static Menu createViewMenu()
    {
        view.getItems().addAll(totalActivity, totalEnergy, queue, new SeparatorMenuItem(), about);
        queue.setOnAction(e -> {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 600);
            energies.clear();
            t.setText("Waiting List");
            TextField boo;
            Label foo;
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Name of charging station:");
            grid.add(foo, 0, 1);
            boo = new TextField(currentStation.reName());
            grid.add(boo, 1, 1);
            foo = new Label("Number in the waiting list:");
            grid.add(foo, 2, 1);
            int total = currentStation.reFast().reSize() + currentStation.reSlow().reSize() + currentStation.reDischarging().rSize() + currentStation.reExchange().reSize();
            boo = new TextField(String.valueOf(total));
            grid.add(boo, 3, 1);
            foo = new Label("ChargingEvent(fast)");
            grid.add(foo, 0, 2);
            int j = 3;
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
            grid.add(foo, 1, 2);
            j = 3;
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
            grid.add(foo, 2, 2);
            j = 3;
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
            grid.add(foo, 3, 2);
            j = 3;
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
        about.setOnAction(e ->
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Creator: Karapostolakis Sotirios\nemail: skarapos@outlook.com\nYear: 2017");
            alert.showAndWait();
        });
        totalEnergy.setOnAction(e -> {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("Total Energy");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Total energy:");
            grid.add(foo, 0, 1);
            boo = new TextField(String.valueOf(currentStation.reTotalEnergy()));
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Solar energy:");
            grid.add(foo, 2, 1);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("solar")));
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Wind energy:");
            grid.add(foo, 0, 2);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("wind")));
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Wave energy:");
            grid.add(foo, 2, 2);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("wave")));
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Hydroelectric energy:");
            grid.add(foo, 0, 3);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("hydroelectric")));
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Non-renewable:");
            grid.add(foo, 2, 3);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("nonrenewable")));
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Geothermal energy:");
            grid.add(foo, 0, 4);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("geothermal")));
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Discharging energy:");
            grid.add(foo, 2, 4);
            boo = new TextField(String.valueOf(currentStation.reSpecificAmount("discharging")));
            grid.add(boo, 3, 4);
            textfields.add(boo);
            root.setCenter(grid);
        });
        totalActivity.setOnAction(e -> {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            VBox box = new VBox();
            Button chargings = new Button("Chargings");
            Button dischargings = new Button("DisChargings");
            Button exchanges = new Button("Exchanges");
            Button parkings = new Button("Parkings");
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
            chargings.setPrefSize(170,30);
            dischargings.setPrefSize(170, 30);
            exchanges.setPrefSize(170, 30);
            parkings.setPrefSize(170, 30);
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
                            pieChartData.add(new PieChart.Data("Non-renewable", currentStation.reSpecificAmount("nonrenewable")));
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
            chargings.setOnAction(et -> {
                Maintenance.cleanScreen();
                ScrollPane scroll = new ScrollPane();
                scroll.setMaxSize(950, 450);
                VBox x;
                Label boo;
                int row = 0, column = 0;
                for(Charger ch: currentStation.reChargers())
                    if(ch.reBusy()&&ch.reChargingEvent().reCondition().equals("charging")) {
                        x = new VBox();
                        x.setPadding(new Insets(25));
                        x.setSpacing(25);
                        x.setAlignment(Pos.TOP_LEFT);
                        boo = new Label("Id: " + ch.reChargingEvent().reId());
                        x.getChildren().add(boo);
                        boo = new Label("Driver: " + ch.reChargingEvent().reElectricVehicle().reDriver().reName());
                        x.getChildren().add(boo);
                        boo = new Label("Brand: " + ch.reChargingEvent().reElectricVehicle().reBrand());
                        x.getChildren().add(boo);
                        boo = new Label("Energy to be received: " + ch.reChargingEvent().reEnergyToBeReceived());
                        x.getChildren().add(boo);
                        boo = new Label("Kind: " + ch.reChargingEvent().reKind());
                        x.getChildren().add(boo);
                        ProgressBar bar = new ProgressBar();
                        x.getChildren().add(bar);
                        float progress = (float) ch.reChargingEvent().reElapsedChargingTime()/ch.reChargingEvent().reChargingTime();
                        bar.setProgress(progress);
                        if(column <= 2) {
                            grid.add(x, column, row);
                            ++column;
                        }
                        else
                        {
                            column = 0;
                            ++row;
                            grid.add(x, column, row);
                        }
                    }
                scroll.setContent(grid);
                root.setCenter(scroll);
            });
        });
        return view;
    }
}
