package evlibsim;

import javafx.scene.control.*;
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
            grid.setMaxSize(1000, 900);
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
        return view;
    }
}