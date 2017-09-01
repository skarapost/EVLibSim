package evlibsim;

import EV.Battery;
import Sources.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import Station.*;
import java.util.Objects;
import static evlibsim.EVLibSim.*;

class MenuStation {
    
    private static final Menu station = new Menu("Station");
    static final MenuItem newStation = new MenuItem("New ChargingStation");
    private static final MenuItem newCharger = new MenuItem("New Charger");
    private static final MenuItem newDisCharger = new MenuItem("New DisCharger");
    private static final MenuItem newExchangeHandler = new MenuItem("New ExchangeHandler");
    private static final MenuItem newParkingSlot = new MenuItem("New ParkingSlot");
    private static final MenuItem modifyChargingStation = new MenuItem("Modify ChargingStation");
    private static final MenuItem newBattery = new MenuItem("New Battery");
    private static boolean automaticHandling = true;
    private static boolean automaticUpdate = false;
    private static RadioMenuItem cs;
    private static final Button stationCreation = new Button("Creation");
    private static final Button chargerCreation = new Button("Creation");
    private static final Button modifyStation = new Button("Modification");
    private static final Button batteryCreation = new Button("Creation");
    
    static Menu createStationMenu() {
        station.getItems().addAll(newStation, new SeparatorMenuItem(), newCharger, newDisCharger, newExchangeHandler, newParkingSlot, new SeparatorMenuItem(), modifyChargingStation, new SeparatorMenuItem(), newBattery);

        newStation.setOnAction((ActionEvent e) ->
        {
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(1000, 600);
            t.setText("ChargingStation Creation");
            TextField boo;
            Label foo;
            t.setId("welcome");
            EVLibSim.grid.add(t, 0, 0, 2, 1);
            foo = new Label("Name of charging station:");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Number of fast chargers:");
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Number of slow chargers:");
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Number of exchange slots:");
            EVLibSim.grid.add(foo, 2, 2);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Number of discharging slots:");
            EVLibSim.grid.add(foo, 0, 3);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Number of parking slots:");
            EVLibSim.grid.add(foo, 2, 3);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Energy sources:");
            EVLibSim.grid.add(foo, 0, 4);
            MenuBar sourc = new MenuBar();
            sourc.setMaxWidth(100);
            Menu src = new Menu("Energies");
            RadioMenuItem sol = new RadioMenuItem("Solar");
            RadioMenuItem win = new RadioMenuItem("Wind");
            RadioMenuItem wav = new RadioMenuItem("Wave");
            RadioMenuItem hydr = new RadioMenuItem("Hydroelectric");
            RadioMenuItem non = new RadioMenuItem("Non-renewable");
            RadioMenuItem geo = new RadioMenuItem("Geothermal");
            src.getItems().addAll(sol, win, wav, hydr, non, geo);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 1, 4);
            foo = new Label("Charging fee per unit:");
            EVLibSim.grid.add(foo, 2, 4);
            boo = new TextField("1");
            EVLibSim.grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit:");
            EVLibSim.grid.add(foo, 0, 5);
            boo = new TextField("1");
            EVLibSim.grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Battery exchange fee per unit:");
            EVLibSim.grid.add(foo, 2, 5);
            boo = new TextField("1");
            EVLibSim.grid.add(boo, 3, 5);
            textfields.add(boo);
            foo = new Label("Inductive charging fee per unit:");
            EVLibSim.grid.add(foo, 0, 6);
            boo = new TextField("1");
            EVLibSim.grid.add(boo, 1, 6);
            textfields.add(boo);
            foo = new Label("Fast charging ratio:");
            EVLibSim.grid.add(foo, 2, 6);
            boo = new TextField("0.01");
            EVLibSim.grid.add(boo, 3, 6);
            textfields.add(boo);
            foo = new Label("Slow charging ratio:");
            EVLibSim.grid.add(foo, 0, 7);
            boo = new TextField("0.001");
            EVLibSim.grid.add(boo, 1, 7);
            textfields.add(boo);
            foo = new Label("Discharging ratio:");
            EVLibSim.grid.add(foo, 2, 7);
            boo = new TextField("0.01");
            EVLibSim.grid.add(boo, 3, 7);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio:");
            EVLibSim.grid.add(foo, 0, 8);
            boo = new TextField("0.01");
            EVLibSim.grid.add(boo, 1, 8);
            textfields.add(boo);
            foo = new Label("Automatic energy update:");
            EVLibSim.grid.add(foo, 2, 8);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem te = new RadioMenuItem("True");
            RadioMenuItem fe = new RadioMenuItem("False");
            r.getToggles().addAll(te, fe);
            fe.setSelected(true);
            src.getItems().addAll(te, fe);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 3, 8);
            foo = new Label("Automatic queue handling:");
            EVLibSim.grid.add(foo, 0, 9);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            r = new ToggleGroup();
            RadioMenuItem tr = new RadioMenuItem("True");
            RadioMenuItem fa = new RadioMenuItem("False");
            r.getToggles().addAll(tr, fa);
            tr.setSelected(true);
            src.getItems().addAll(tr, fa);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 1, 9);
            foo = new Label("Update space(millis):");
            EVLibSim.grid.add(foo, 2, 9);
            boo = new TextField("1000");
            EVLibSim.grid.add(boo, 3, 9);
            textfields.add(boo);
            EVLibSim.grid.add(stationCreation, 0, 10);
            stationCreation.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
            te.setOnAction((ActionEvent et) ->
                    automaticUpdate = te.isSelected());
            tr.setOnAction((ActionEvent et) ->
                    automaticHandling = tr.isSelected());
            sol.setOnAction((ActionEvent et) -> {
                if (sol.isSelected())
                    EVLibSim.energies.add("solar");
                else
                    EVLibSim.energies.remove("solar");
            });
            win.setOnAction((ActionEvent et) -> {
                if (win.isSelected())
                    EVLibSim.energies.add("wind");
                else
                    EVLibSim.energies.remove("wind");
            });
            wav.setOnAction((ActionEvent et) -> {
                if (wav.isSelected())
                    EVLibSim.energies.add("wave");
                else
                    EVLibSim.energies.remove("wave");
            });
            hydr.setOnAction((ActionEvent et) -> {
                if (hydr.isSelected())
                    EVLibSim.energies.add("hydroelectric");
                else
                    EVLibSim.energies.remove("hydroelectric");
            });
            geo.setOnAction((ActionEvent et) -> {
                if (geo.isSelected())
                    EVLibSim.energies.add("geothermal");
                else
                    EVLibSim.energies.remove("geothermal");
            });
            non.setOnAction((ActionEvent et) -> {
                if (non.isSelected())
                    EVLibSim.energies.add("nonrenewable");
                else
                    EVLibSim.energies.remove("nonrenewable");
            });
        });
        newCharger.setOnAction(e ->
        {
            if (!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(500, 300);
            t = new Text("Charger Creation");
            t.setId("welcome");
            EVLibSim.grid.add(t, 0, 0, 2, 1);
            TextField boo;
            Label foo;
            foo = new Label("Kind of charger:");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            EVLibSim.grid.add(chargerCreation, 0, 3);
            chargerCreation.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });
        newDisCharger.setOnAction(e ->
        {
            if (!Maintenance.stationCheck())
                return;
            if (!Maintenance.confirmCreation("DisCharger"))
                return;
            DisCharger ch;
            ch = new DisCharger(currentStation);
            currentStation.addDisCharger(ch);
            Maintenance.completionMessage("DisCharger");
        });
        newExchangeHandler.setOnAction(e ->
        {
            if (!Maintenance.stationCheck())
                return;
            if (!Maintenance.confirmCreation("ExchangeHandler"))
                return;
            ExchangeHandler ch;
            ch = new ExchangeHandler(currentStation);
            currentStation.addExchangeHandler(ch);
            Maintenance.completionMessage("ExchangeHandler");
        });
        newParkingSlot.setOnAction(e ->
        {
            if (!Maintenance.stationCheck())
                return;
            if (!Maintenance.confirmCreation("ParkingSlot"))
                return;
            ParkingSlot ch;
            ch = new ParkingSlot(currentStation);
            currentStation.addParkingSlot(ch);
            Maintenance.completionMessage("ParkingSlot");
        });
        modifyChargingStation.setOnAction(e -> {
            if (!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(1000, 600);
            EVLibSim.energies.clear();
            t.setText("ChargingStation Modification");
            TextField boo;
            Label foo;
            t.setId("welcome");
            EVLibSim.grid.add(t, 0, 0, 2, 1);
            foo = new Label("Name of charging station:");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField(currentStation.reName());
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            MenuBar sourc = new MenuBar();
            sourc.setMaxWidth(70);
            sourc.setStyle("-fx-border-radius: 15 15 15 15;");
            Menu src;
            foo = new Label("Charging fee per unit:");
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField(Double.toString(currentStation.reUnitPrice()));
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit:");
            EVLibSim.grid.add(foo, 0, 2);
            boo = new TextField(Double.toString(currentStation.reDisUnitPrice()));
            EVLibSim.grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Battery exchange fee:");
            EVLibSim.grid.add(foo, 2, 2);
            boo = new TextField(Double.toString(currentStation.reExchangePrice()));
            EVLibSim.grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Inductive fee per unit:");
            EVLibSim.grid.add(foo, 0, 3);
            boo = new TextField(Double.toString(currentStation.reInductivePrice()));
            EVLibSim.grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Fast charging ratio:");
            EVLibSim.grid.add(foo, 2, 3);
            boo = new TextField(Double.toString(currentStation.reChargingRatioFast()));
            EVLibSim.grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Slow charging ratio:");
            EVLibSim.grid.add(foo, 0, 4);
            boo = new TextField(Double.toString(currentStation.reChargingRatioSlow()));
            EVLibSim.grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Discharging ratio:");
            EVLibSim.grid.add(foo, 2, 4);
            boo = new TextField(Double.toString(currentStation.reDisChargingRatio()));
            EVLibSim.grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio:");
            EVLibSim.grid.add(foo, 0, 5);
            boo = new TextField(Double.toString(currentStation.reInductiveRatio()));
            EVLibSim.grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Automatic energy update:");
            EVLibSim.grid.add(foo, 2, 5);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem te = new RadioMenuItem("True");
            RadioMenuItem fe = new RadioMenuItem("False");
            r.getToggles().addAll(te, fe);
            if (currentStation.reUpdateMode())
                te.setSelected(true);
            else
                fe.setSelected(true);
            src.getItems().addAll(te, fe);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 3, 5);
            foo = new Label("Automatic queue handling:");
            EVLibSim.grid.add(foo, 0, 6);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            r = new ToggleGroup();
            RadioMenuItem tr = new RadioMenuItem("True");
            RadioMenuItem fa = new RadioMenuItem("False");
            r.getToggles().addAll(tr, fa);
            if (currentStation.reQueueHandling())
                tr.setSelected(true);
            else
                fa.setSelected(true);
            src.getItems().addAll(tr, fa);
            sourc.getMenus().add(src);
            EVLibSim.grid.add(sourc, 1, 6);
            foo = new Label("Update space(millis):");
            EVLibSim.grid.add(foo, 2, 6);
            boo = new TextField(Long.toString(currentStation.reUpdateSpace()));
            EVLibSim.grid.add(boo, 3, 6);
            textfields.add(boo);
            EVLibSim.grid.add(modifyStation, 0, 7);
            modifyStation.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
            te.setOnAction((ActionEvent et) ->
                    automaticUpdate = te.isSelected());
            tr.setOnAction((ActionEvent et) ->
                    automaticHandling = tr.isSelected());
        });
        chargerCreation.setOnAction(e ->
        {
            Charger ch;
            ch = new Charger(currentStation, textfields.get(0).getText());
            currentStation.addCharger(ch);
            Maintenance.completionMessage("Charger");
            startScreen.fire();
        });
        stationCreation.setOnAction(e ->
        {
            if (!Maintenance.fieldCompletionCheck())
                return;
            if (energies.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please choose at least one energy source.");
                alert.showAndWait();
                return;
            }
            if (Double.parseDouble(textfields.get(1).getText()) < 0 ||
                    Double.parseDouble(textfields.get(2).getText()) < 0 ||
                    Double.parseDouble(textfields.get(3).getText()) < 0 ||
                    Double.parseDouble(textfields.get(4).getText()) < 0 ||
                    Double.parseDouble(textfields.get(5).getText()) < 0 ||
                    Double.parseDouble(textfields.get(6).getText()) < 0 ||
                    Double.parseDouble(textfields.get(7).getText()) < 0 ||
                    Double.parseDouble(textfields.get(8).getText()) < 0 ||
                    Double.parseDouble(textfields.get(9).getText()) < 0 ||
                    Double.parseDouble(textfields.get(10).getText()) < 0 ||
                    Double.parseDouble(textfields.get(11).getText()) < 0 ||
                    Double.parseDouble(textfields.get(12).getText()) < 0 ||
                    Double.parseDouble(textfields.get(13).getText()) < 0 ||
                    Double.parseDouble(textfields.get(14).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers.");
                alert.showAndWait();
                return;
            }
            ChargingStation st;
            st = new ChargingStation(textfields.get(0).getText());
            st.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
            st.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
            st.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
            st.setInductivePrice(Double.parseDouble(textfields.get(9).getText()));
            st.setChargingRatioFast(Double.parseDouble(textfields.get(10).getText()));
            st.setChargingRatioSlow(Double.parseDouble(textfields.get(11).getText()));
            st.setDisChargingRatio(Double.parseDouble(textfields.get(12).getText()));
            st.setInductiveChargingRatio(Double.parseDouble(textfields.get(13).getText()));
            st.setAutomaticUpdateMode(automaticUpdate);
            st.setUpdateSpace(Integer.parseInt(textfields.get(14).getText()));
            st.setAutomaticQueueHandling(automaticHandling);
            if (textfields.get(1).getText() != null) {
                int len = Integer.parseInt(textfields.get(1).getText());
                Charger ch;
                for (int i = 0; i < len; i++) {
                    ch = new Charger(st, "fast");
                    st.addCharger(ch);
                }
            }
            if (textfields.get(2).getText() != null) {
                int len = Integer.parseInt(textfields.get(2).getText());
                Charger ch;
                for (int i = 0; i < len; i++) {
                    ch = new Charger(st, "slow");
                    st.addCharger(ch);
                }
            }
            if (textfields.get(3).getText() != null) {
                int len = Integer.parseInt(textfields.get(3).getText());
                ExchangeHandler exch;
                for (int i = 0; i < len; i++) {
                    exch = new ExchangeHandler(st);
                    st.addExchangeHandler(exch);
                }
            }
            if (textfields.get(4).getText() != null) {
                int len = Integer.parseInt(textfields.get(4).getText());
                DisCharger dsch;
                for (int i = 0; i < len; i++) {
                    dsch = new DisCharger(st);
                    st.addDisCharger(dsch);
                }
            }
            if (textfields.get(5).getText() != null) {
                int len = Integer.parseInt(textfields.get(5).getText());
                ParkingSlot ps;
                for (int i = 0; i < len; i++) {
                    ps = new ParkingSlot(st);
                    st.addParkingSlot(ps);
                }
            }
            EnergySource en;
            for (String enr : energies) {
                if (Objects.equals(enr, "solar")) {
                    en = new Solar(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "geothermal")) {
                    en = new Geothermal(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "wind")) {
                    en = new Wind(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "wave")) {
                    en = new Wave(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "nonrenewable")) {
                    en = new NonRenewable(st);
                    st.addEnergySource(en);
                } else if (Objects.equals(enr, "hydroelectric")) {
                    en = new HydroElectric(st);
                    st.addEnergySource(en);
                }
            }
            stations.add(st);
            cs = new RadioMenuItem(st.reName());
            group.getToggles().add(cs);
            s.getItems().add(cs);
            if(s.getItems().size() == 1)
                cs.setSelected(true);
            Maintenance.completionMessage("ChargingStation");
            startScreen.fire();
        });
        modifyStation.setOnAction(e -> {
            if (!Maintenance.fieldCompletionCheck())
                return;
            currentStation.setUnitPrice(Double.parseDouble(textfields.get(1).getText()));
            currentStation.setDisUnitPrice(Double.parseDouble(textfields.get(2).getText()));
            currentStation.setExchangePrice(Double.parseDouble(textfields.get(3).getText()));
            currentStation.setInductivePrice((Double.parseDouble(textfields.get(4).getText())));
            currentStation.setChargingRatioFast((Double.parseDouble(textfields.get(5).getText())));
            currentStation.setChargingRatioSlow((Double.parseDouble(textfields.get(6).getText())));
            currentStation.setDisChargingRatio((Double.parseDouble(textfields.get(7).getText())));
            currentStation.setInductiveChargingRatio((Double.parseDouble(textfields.get(8).getText())));
            currentStation.setUpdateSpace((Integer.parseInt(textfields.get(9).getText())));
            currentStation.setAutomaticQueueHandling(automaticHandling);
            currentStation.setAutomaticUpdateMode(automaticUpdate);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("The station was modified successfully.");
            alert.showAndWait();
            startScreen.fire();
        });
        newBattery.setOnAction(e -> {
            if (!Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            EVLibSim.grid.setMaxSize(800, 800);
            t.setText("Battery Creation");
            TextField boo;
            Label foo;
            t.setId("welcome");
            EVLibSim.grid.add(t, 0, 0, 2, 1);
            foo = new Label("Battery capacity: ");
            EVLibSim.grid.add(foo, 0, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Battery remaining: ");
            EVLibSim.grid.add(foo, 2, 1);
            boo = new TextField();
            EVLibSim.grid.add(boo, 3, 1);
            textfields.add(boo);
            EVLibSim.grid.add(batteryCreation, 0, 2);
            batteryCreation.setDefaultButton(true);
            EVLibSim.root.setCenter(EVLibSim.grid);
        });
        batteryCreation.setOnAction(e -> {
            if (!Maintenance.fieldCompletionCheck())
                return;
            if (Double.parseDouble(textfields.get(0).getText()) < Double.parseDouble(textfields.get(1).getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("The capacity cannot be smaller than the remaining amount.");
                alert.showAndWait();
                return;
            }
            Battery bat = new Battery(Integer.parseInt(textfields.get(1).getText()), Integer.parseInt(textfields.get(0).getText()));
            currentStation.joinBattery(bat);
        });
        return station;
    }
}
