package evlibsim;

import EV.Battery;
import EV.Driver;
import EV.ElectricVehicle;
import Events.ChargingEvent;
import Events.DisChargingEvent;
import Events.ParkingEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.util.Objects;
import static evlibsim.EVLibSim.*;

class Event {
    private static final Menu event = new Menu("Event");
    private static final MenuItem charging = new MenuItem("Charging");
    private static final MenuItem discharging = new MenuItem("Discharging");
    private static final MenuItem exchange = new MenuItem("Exchange of Battery");
    private static final MenuItem parking = new MenuItem("Parking/Inductive charging");
    private static final Button chargingEventCreation = new Button("Creation");
    private static final Button disChargingEventCreation = new Button("Creation");
    private static final Button parkingEventCreation = new Button("Creation");
    private static final Button exchangeEventCreation = new Button("Creation");

    public static Menu createEventMenu()
    {
        charging.setOnAction(e ->
        {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("ChargingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Kind of charging:");
            grid.add(foo, 2, 4);
            boo = new TextField();
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Money:");
            grid.add(foo, 0, 5);
            boo = new TextField();
            grid.add(boo, 1, 5);
            textfields.add(boo);
            grid.add(chargingEventCreation, 0, 6);
            chargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        discharging.setOnAction(e ->
        {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("DisChargingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            grid.add(disChargingEventCreation, 0, 5);
            disChargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        exchange.setOnAction(e ->
        {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            t.setText("Battery Exchange Event Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            grid.add(exchangeEventCreation, 0, 5);
            exchangeEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        parking.setOnAction(e ->
        {
            if(!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("ParkingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Driver's name:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Parking time:");
            grid.add(foo, 2, 4);
            boo = new TextField();
            grid.add(boo, 3, 4);
            textfields.add(boo);
            grid.add(parkingEventCreation, 0, 5);
            parkingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        chargingEventCreation.setOnAction(e -> {
            if (!Maintenance.fieldCompletionCheck())
                return;
            if (!textfields.get(7).getText().equals("fast") && !textfields.get(7).getText().equals("slow")) {
                System.out.println(textfields.get(7).getText());
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please put \"slow\" for a slow charging, or \"fast\" for a fast charging.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(8).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(5).getText()) == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The asking amount cannot be negative or zero");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(5).getText()) > (Double.parseDouble(textfields.get(3).getText()) - Double.parseDouble(textfields.get(4).getText()))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The asking amount of energy cannot be greater than the remaining capacity.");
                alert.showAndWait();
                return;
            }
            ChargingEvent ch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(4).getText()), Double.parseDouble(textfields.get(3).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            if (!Objects.equals(textfields.get(5).getText(), "0")) {
                ch = new ChargingEvent(currentStation, el, Double.parseDouble(textfields.get(5).getText()), textfields.get(7).getText());
                ch.preProcessing();
                ch.execution();
            }
            else if (!Objects.equals(textfields.get(8).getText(), "0")) {
                ch = new ChargingEvent(currentStation, el, textfields.get(7).getText(), Double.parseDouble(textfields.get(8).getText()));
                ch.preProcessing();
                ch.execution();
            }
            Maintenance.completionMessage("ChargingEvent");
            startScreen.fire();
        });
        disChargingEventCreation.setOnAction(e ->
        {
            if (!Maintenance.fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(5).getText()) > (Double.parseDouble(textfields.get(4).getText()))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The given amount of energy cannot be greater than the remaining amount.");
                alert.showAndWait();
                return;
            }
            DisChargingEvent dsch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(4).getText()), Double.parseDouble(textfields.get(3).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            if (!Objects.equals(textfields.get(5).getText(), "0")) {
                dsch = new DisChargingEvent(currentStation, el, Double.parseDouble(textfields.get(5).getText()));
                dsch.preProcessing();
                dsch.execution();
            }
            Maintenance.completionMessage("DisChargingEvent");
            startScreen.fire();
        });
        exchangeEventCreation.setOnAction(e -> {
            if (!Maintenance.fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            ChargingEvent ch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(4).getText()), Double.parseDouble(textfields.get(3).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            ch = new ChargingEvent(currentStation, el);
            ch.preProcessing();
            ch.execution();
            Maintenance.completionMessage("ChargingEvent");
            startScreen.fire();
        });
        parkingEventCreation.setOnAction(e -> {
            if (!Maintenance.fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(7).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(3).getText()) < Double.parseDouble(textfields.get(4).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            ParkingEvent ch;
            Driver d = new Driver(textfields.get(0).getText());
            Battery b = new Battery(Double.parseDouble(textfields.get(3).getText()), Double.parseDouble(textfields.get(4).getText()));
            ElectricVehicle el = new ElectricVehicle(textfields.get(1).getText(), Integer.parseInt(textfields.get(2).getText()));
            el.vehicleJoinBattery(b);
            el.setDriver(d);
            if (!Objects.equals(textfields.get(5).getText(), "0")) {
                ch = new ParkingEvent(currentStation, el, Long.parseLong(textfields.get(7).getText()), Double.parseDouble(textfields.get(5).getText()));
                ch.preProcessing();
                ch.execution();
            } else if (!Objects.equals(textfields.get(7).getText(), "0")) {
                ch = new ParkingEvent(currentStation, el, Long.parseLong(textfields.get(7).getText()));
                ch.preProcessing();
                ch.execution();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please select at least a positive parking time.");
                alert.showAndWait();
                return;
            }
            Maintenance.completionMessage("ParkingEvent");
            startScreen.fire();
        });
        event.getItems().addAll(charging, discharging, exchange, parking);
        return event;
    }
}
