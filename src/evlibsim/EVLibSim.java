package evlibsim;

import java.util.ArrayList;

import Sources.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import Station.*;

public class EVLibSim extends Application
{
    private BorderPane root;
    private GridPane grid;
    private ArrayList<TextField> textfields;
    private ArrayList<ChargingStation> stations;
    private ArrayList<String> energies;
    private boolean automaticHandling = true;
    private boolean automaticUpdate = false;
    private Text t;
    private Button stationCreation;
    private Button chargerCreation;
    private Button dischargerCreation;
    private Button exchangeCreation;
    private Button parkingSlotCreation;
    private Button chargingEventCreation;
    private Button disChargingEventCreation;
    private Button parkingEventCreation;
    private Button exchangeEventCreation;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {    
        primaryStage.setTitle("EVLibSim");
        root = new BorderPane();
        grid = new GridPane();
        textfields = new ArrayList<>();
        stations = new ArrayList<>();
        energies = new ArrayList<>();
        stationCreation = new Button("Creation");
        chargerCreation = new Button("Creation");
        dischargerCreation = new Button("Creation");
        exchangeCreation = new Button("Creation");
        parkingSlotCreation = new Button("Creation");
        chargingEventCreation = new Button("Creation");
        disChargingEventCreation = new Button("Creation");
        parkingEventCreation = new Button("Creation");
        exchangeCreation = new Button("Creation");
        Scene scene = new Scene(root, 1350, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("View");
        MenuItem startScreenMenuItem = new MenuItem("Start Screen");
        MenuItem totalActivity = new MenuItem("Total Activity");
        MenuItem queue = new MenuItem("Queue of events");
        Menu search = new Menu("Search");
        MenuItem searchCharging = new MenuItem("Search ChargingEvent");
        MenuItem searchDisCharging = new MenuItem("Search DisChargingEvent");
        MenuItem searchExchangeEvent = new MenuItem("Search Exchange Battery Event");
        MenuItem searchParkingSlot = new MenuItem("Search ParkingEvent");
        Menu station = new Menu("Station");
        MenuItem newStation = new MenuItem("New ChargingStation");
        MenuItem newCharger = new MenuItem("New Charger");
        MenuItem newDisCharger = new MenuItem("New DisCharger");
        MenuItem newExchangeHandler = new MenuItem("New ExchangeHandler");
        MenuItem newParkingSlot = new MenuItem("New ParkingSlot");
        station.getItems().addAll(newStation, newCharger, newDisCharger, newExchangeHandler, newParkingSlot);
        search.getItems().addAll(searchCharging, searchDisCharging, searchExchangeEvent, searchParkingSlot);
        Menu energy = new Menu("Energy");
        MenuItem hydro = new MenuItem("Hydroelectric energy");
        MenuItem wind = new MenuItem("Wind energy");
        MenuItem wave = new MenuItem("Wave energy");
        MenuItem solar = new MenuItem("Solar energy");
        MenuItem geothermal = new MenuItem("Geothermal energy");
        MenuItem nonrenewable = new MenuItem("Nonrenewable energy");
        energy.getItems().addAll(hydro, wind, wave, solar, geothermal, nonrenewable);
        MenuItem totalEnergy = new MenuItem("Total energy");
        menuBar.getMenus().addAll(file, edit, view);
        root.setTop(menuBar);
        MenuItem exitMenuItem = new MenuItem("Exit");
        MenuItem about = new MenuItem("About");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());
        file.getItems().addAll(startScreenMenuItem, new SeparatorMenuItem(), search, new SeparatorMenuItem(),exitMenuItem);
        view.getItems().addAll(totalActivity, totalEnergy, queue, new SeparatorMenuItem(), about);
        Menu event = new Menu("Event");
        MenuItem charging = new MenuItem("Charging");
        MenuItem discharging = new MenuItem("Discharging");
        MenuItem exchange = new MenuItem("Exchange of Battery");
        MenuItem parking = new MenuItem("Parking/Inductive charging");
        event.getItems().addAll(charging, discharging, exchange, parking);
        edit.getItems().addAll(station, event, energy);
        scene.getStylesheets().add(EVLibSim.class.getResource("EVLibSim.css").toExternalForm());
        grid.getStyleClass().add("grid");
        about.setOnAction((ActionEvent e) -> 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Creator: Karapostolakis Sotirios\nemail: skarapos@outlook.com\nYear: 2017");
            alert.showAndWait();
        });
        searchCharging.setOnAction((ActionEvent e)-> {
            cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t = new Text("Charging Event Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            root.setCenter(grid);
        });
        searchDisCharging.setOnAction((ActionEvent e)-> {
            cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t = new Text("Discharging Event Search");
            grid.add(t, 0, 0, 2, 1);
            t.setId("welcome");
            foo = new Label("Model of car:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Amount of energy:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            root.setCenter(grid);
        });
        searchExchangeEvent.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(350, 280);
            TextField boo;
            Label foo;
            t = new Text("Exchange Event Search");
            t.setId("welcome");
            grid.add(t, 0, 0, 2, 1);
            foo = new Label("Model of car:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 1);
            foo = new Label("Driver of car:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 2);
            foo = new Label("Kind of battery:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            boo.setText(null);
            textfields.add(boo);
            grid.add(boo, 1, 3);
            root.setCenter(grid);
        });
        newStation.setOnAction((ActionEvent e)->
        {
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("Charging Station Creation");
            TextField boo;
            Label foo;
            t.setId("welcome");
            grid.add(t, 1, 0, 2, 1);
            foo = new Label("Name of charging station:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Number of fast chargers:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Number of slow chargers:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Number of exchange slots:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Number of discharging slots:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Number of parking slots:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Energy sources:");
            grid.add(foo, 0, 4);
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
            grid.add(sourc, 1, 4);
            foo = new Label("Charging fee per unit:");
            grid.add(foo, 2, 4);
            boo = new TextField("1");
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("DisCharging fee per unit:");
            grid.add(foo, 0, 5);
            boo = new TextField("1");
            grid.add(boo, 1, 5);
            textfields.add(boo);
            foo = new Label("Battery exchange fee per unit:");
            grid.add(foo, 2, 5);
            boo = new TextField("1");
            grid.add(boo, 3, 5);
            textfields.add(boo);
            foo = new Label("Inductive charging fee per unit:");
            grid.add(foo, 0, 6);
            boo = new TextField("1");
            grid.add(boo, 1, 6);
            textfields.add(boo);
            foo = new Label("Fast charging ratio:");
            grid.add(foo, 2, 6);
            boo = new TextField("0.01");
            grid.add(boo, 3, 6);
            textfields.add(boo);
            foo = new Label("Slow charging ratio:");
            grid.add(foo, 0, 7);
            boo = new TextField("0.001");
            grid.add(boo, 1, 7);
            textfields.add(boo);
            foo = new Label("Discharging ratio:");
            grid.add(foo, 2, 7);
            boo = new TextField("0.01");
            grid.add(boo, 3, 7);
            textfields.add(boo);
            foo = new Label("Inductive charging ratio:");
            grid.add(foo, 0, 8);
            boo = new TextField("0.01");
            grid.add(boo, 1, 8);
            textfields.add(boo);
            foo = new Label("Automatic energy update:");
            grid.add(foo, 2, 8);
            sourc = new MenuBar();
            sourc.setMaxWidth(100);
            src = new Menu("Choice");
            ToggleGroup r = new ToggleGroup();
            RadioMenuItem t = new RadioMenuItem("True");
            RadioMenuItem f = new RadioMenuItem("False");
            r.getToggles().addAll(t, f);
            f.setSelected(true);
            src.getItems().addAll(t, f);
            sourc.getMenus().add(src);
            grid.add(sourc, 3, 8);
            foo = new Label("Automatic queue handling:");
            grid.add(foo, 0, 9);
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
            grid.add(sourc, 1, 9);
            foo = new Label("Update space(millis):");
            grid.add(foo, 2, 9);
            boo = new TextField("1000");
            grid.add(boo, 3, 9);
            textfields.add(boo);
            grid.add(stationCreation, 0, 10);
            stationCreation.setDefaultButton(true);
            root.setCenter(grid);
            t.setOnAction((ActionEvent et) ->
            {
                if (t.isSelected())
                    automaticUpdate = true;
                else
                    automaticUpdate = false;
            });
            tr.setOnAction((ActionEvent et) ->
            {
                if (t.isSelected())
                    automaticHandling = true;
                else
                    automaticHandling = false;
            });
            sol.setOnAction( (ActionEvent et) -> {
                if (sol.isSelected())
                    energies.add("solar");
                else
                    energies.remove("solar");
            });
            win.setOnAction((ActionEvent et) -> {
                if (win.isSelected())
                    energies.add("wind");
                else
                    energies.remove("wind");
            });
            wav.setOnAction((ActionEvent et) -> {
                if (wav.isSelected())
                    energies.add("wave");
                else
                    energies.remove("wave");
            });
            hydr.setOnAction((ActionEvent et) -> {
                if (hydr.isSelected())
                    energies.add("hydroelectric");
                else
                    energies.remove("hydroelectric");
            });
            geo.setOnAction((ActionEvent et) -> {
                if (geo.isSelected())
                    energies.add("geothermal");
                else
                    energies.remove("geothermal");
            });
            non.setOnAction((ActionEvent et) -> {
                if (non.isSelected())
                    energies.add("nonrenewable");
                else
                    energies.remove("nonrenewable");
            });
        });
        newCharger.setOnAction((ActionEvent e) -> {
            cleanScreen();
            grid.setMaxSize(500, 300);
            t = new Text("Charger Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 4, 1);
            TextField boo;
            Label foo;
            foo = new Label("Kind of charger:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Name of station:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            grid.add(chargerCreation, 0, 3);
            chargerCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        newDisCharger.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(500, 300);
            t = new Text("DisCharger Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 4, 1);
            TextField boo;
            Label foo;
            foo = new Label("Name of station:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            grid.add(dischargerCreation, 0, 2);
            dischargerCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        newExchangeHandler.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(500, 300);
            t = new Text("ExchangeHandler Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 4, 1);
            TextField boo;
            Label foo;
            foo = new Label("Name of station:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            grid.add(exchangeCreation, 0, 2);
            exchangeCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        newParkingSlot.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(500, 300);
            t = new Text("ParkingSlot Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 4, 1);
            TextField boo;
            Label foo;
            foo = new Label("Name of station:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            grid.add(parkingSlotCreation, 0, 2);
            parkingSlotCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        charging.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(800, 500);
            t = new Text("ChargingEvent Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 4, 1);
            TextField boo;
            Label foo;
            foo = new Label("Name of station:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Driver's name:");
            grid.add(foo, 2, 1);
            boo = new TextField();
            grid.add(boo, 3, 1);
            textfields.add(boo);
            foo = new Label("Vehicle's brand:");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            foo = new Label("Vehicle's cubism:");
            grid.add(foo, 2, 2);
            boo = new TextField();
            grid.add(boo, 3, 2);
            textfields.add(boo);
            foo = new Label("Battery capacity:");
            grid.add(foo, 0, 3);
            boo = new TextField();
            grid.add(boo, 1, 3);
            textfields.add(boo);
            foo = new Label("Battery remaining:");
            grid.add(foo, 2, 3);
            boo = new TextField();
            grid.add(boo, 3, 3);
            textfields.add(boo);
            foo = new Label("Amount of energy:");
            grid.add(foo, 0, 4);
            boo = new TextField();
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Waiting time:");
            grid.add(foo, 2, 4);
            boo = new TextField();
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Name of station:");
            grid.add(foo, 0, 5);
            boo = new TextField();
            grid.add(boo, 1, 5);
            textfields.add(boo);
            grid.add(chargingEventCreation, 0, 6);
            chargingEventCreation.setDefaultButton(true);
            root.setCenter(grid);
        });
        discharging.setOnAction((ActionEvent e) -> {
            cleanScreen();
        });
        exchange.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
        });
        parking.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
        });
        //Buttons
        chargerCreation.setOnAction((ActionEvent e) ->
        {
            Charger ch;
            for(ChargingStation cs: stations)
                if(cs.reName() == textfields.get(1).getText())
                {
                    ch = new Charger(cs, textfields.get(0).getText());
                    cs.addCharger(ch);
                }
        });
        dischargerCreation.setOnAction((ActionEvent e) ->
        {
            DisCharger ch;
            for(ChargingStation cs: stations)
                if(cs.reName() == textfields.get(0).getText())
                {
                    ch = new DisCharger(cs);
                    cs.addDisCharger(ch);
                }
        });
        exchangeCreation.setOnAction((ActionEvent e) ->
        {
            ExchangeHandler ch;
            for(ChargingStation cs: stations)
                if(cs.reName() == textfields.get(0).getText())
                {
                    ch = new ExchangeHandler(cs);
                    cs.addExchangeHandler(ch);
                }
        });
        parkingSlotCreation.setOnAction((ActionEvent e) ->
        {
            ParkingSlot ch;
            for(ChargingStation cs: stations)
                if(cs.reName() == textfields.get(0).getText())
                {
                    ch = new ParkingSlot(cs);
                    cs.addParkingSlot(ch);
                }
        });
        stationCreation.setOnAction((ActionEvent e) ->
        {
            ChargingStation s;
            s = new ChargingStation(textfields.get(0).getText());
            s.setUnitPrice(Double.parseDouble(textfields.get(6).getText()));
            s.setDisUnitPrice(Double.parseDouble(textfields.get(7).getText()));
            s.setExchangePrice(Double.parseDouble(textfields.get(8).getText()));
            s.setInductiveChargingRatio(Double.parseDouble(textfields.get(9).getText()));
            s.setChargingRatioFast(Double.parseDouble(textfields.get(10).getText()));
            s.setChargingRatioSlow(Double.parseDouble(textfields.get(11).getText()));
            s.setDisChargingRatio(Double.parseDouble(textfields.get(12).getText()));
            s.setInductiveChargingRatio(Double.parseDouble(textfields.get(13).getText()));
            s.setUpdateSpace(Integer.parseInt(textfields.get(14).getText()));
            s.setUpdateMode(automaticUpdate);
            s.setAutomaticQueueHandling(automaticHandling);
            if (textfields.get(1).getText() != null){
                int len = Integer.parseInt(textfields.get(1).getText());
                Charger ch;
                for (int i = 0; i<len; i++) {
                    ch = new Charger(s, "fast");
                    s.addCharger(ch); }
            }
            if (textfields.get(2).getText() != null){
                int len = Integer.parseInt(textfields.get(2).getText());
                Charger ch;
                for (int i = 0; i<len; i++) {
                    ch = new Charger(s, "slow");
                    s.addCharger(ch); }
            }
            if(textfields.get(3).getText() != null) {
                int len = Integer.parseInt(textfields.get(3).getText());
                ExchangeHandler exch;
                for (int i = 0; i<len; i++) {
                    exch = new ExchangeHandler(s);
                    s.addExchangeHandler(exch); }
            }
            if (textfields.get(4).getText() != null)
            {
                int len = Integer.parseInt(textfields.get(4).getText());
                DisCharger dsch;
                for (int i=0; i<len; i++) {
                    dsch = new DisCharger(s);
                    s.addDisCharger(dsch); }
            }
            if (textfields.get(5).getText() != null)
            {
                int len = Integer.parseInt(textfields.get(5).getText());
                ParkingSlot ps;
                for (int i=0; i<len; i++) {
                    ps = new ParkingSlot(s);
                    s.addParkingSlot(ps); }
            }
            EnergySource en;
            for(String enr: energies)
            {
                if (enr == "solar") {
                    en = new Solar(s);
                    s.addEnergySource(en); }
                else if(enr == "geothermal") {
                    en = new Geothermal(s);
                    s.addEnergySource(en); }
                else if(enr == "wind") {
                    en = new Wind(s);
                    s.addEnergySource(en); }
                else if(enr == "wave") {
                    en = new Wave(s);
                    s.addEnergySource(en); }
                else if(enr == "nonrenewable") {
                    en = new NonRenewable(s);
                    s.addEnergySource(en); }
                else if(enr == "hydroelectric") {
                    en = new HydroElectric(s);
                    s.addEnergySource(en); }
            }
            stations.add(s);
            textfields.clear();
        });
    }
    public void cleanScreen()
    {
        grid.getChildren().clear();
        root.setCenter(null);
        root.setLeft(null);
        root.setRight(null);
        root.setBottom(null);
        textfields.clear();
    }
}