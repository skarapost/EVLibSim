package evlibsim;

import java.util.ArrayList;
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
    private Text t;
    private Button stationCreation;

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
        textfields = new ArrayList<TextField>();
        stations = new ArrayList<ChargingStation>();
        energies = new ArrayList<String>();
        stationCreation = new Button("Creation");
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
        MenuItem searchCharging = new MenuItem("Search Charging");
        MenuItem searchDisCharging = new MenuItem("Search DisCharging");
        MenuItem searchExchangeEvent = new MenuItem("Search Exchange Battery");
        Menu station = new Menu("Station");
        MenuItem newStation = new MenuItem("New station");
        MenuItem newCharger = new MenuItem("New charger");
        MenuItem newDisCharger = new MenuItem("New discharger");
        MenuItem newExchangeHandler = new MenuItem("New exchange handler");
        station.getItems().addAll(newStation, newCharger, newDisCharger, newExchangeHandler);
        search.getItems().addAll(searchCharging, searchDisCharging, searchExchangeEvent);
        Menu energy = new Menu("Energy");
        MenuItem hydro = new MenuItem("Hydroelectric energy");
        MenuItem wind = new MenuItem("Wind energy");
        MenuItem wave = new MenuItem("Wave energy");
        MenuItem solar = new MenuItem("Solar energy");
        MenuItem geothermal = new MenuItem("Geothermal energy");
        energy.getItems().addAll(hydro, wind, wave, solar, geothermal);
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
        event.getItems().addAll(charging, discharging, exchange);
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
            foo = new Label("Energy sources:");
            grid.add(foo, 2, 3);
            MenuBar sourc = new MenuBar();
            sourc.setMaxWidth(100);
            Menu src = new Menu("Energies");
            RadioMenuItem sol = new RadioMenuItem("Solar");
            RadioMenuItem win = new RadioMenuItem("Wind");
            RadioMenuItem wav = new RadioMenuItem("Wave");
            RadioMenuItem hydr = new RadioMenuItem("Hydroelectric");
            RadioMenuItem non = new RadioMenuItem("Non-renewable");
            non.setSelected(true);
            energies.add("nonrenewable");
            RadioMenuItem geo = new RadioMenuItem("Geothermal");
            src.getItems().addAll(sol, win, wav, hydr, non, geo);
            sourc.getMenus().add(src);
            grid.add(sourc, 3, 3);
            foo = new Label("Charging fee:");
            grid.add(foo, 0, 4);
            boo = new TextField("1");
            grid.add(boo, 1, 4);
            textfields.add(boo);
            foo = new Label("Dis-charging fee:");
            grid.add(foo, 2, 4);
            boo = new TextField("1");
            grid.add(boo, 3, 4);
            textfields.add(boo);
            foo = new Label("Battery change fee:");
            grid.add(foo, 0, 5);
            boo = new TextField("1");
            grid.add(boo, 1, 5);
            textfields.add(boo);
            grid.add(stationCreation, 3, 6);
            stationCreation.setDefaultButton(true);
            root.setCenter(grid);
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
        newCharger.setOnAction((ActionEvent e) ->
            {
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
                foo = new Label("Charging station link:");
                grid.add(foo, 0, 2);
                boo = new TextField();
                grid.add(boo, 1, 2);
                textfields.add(boo);
                foo = new Label("Name of charger(optional):");
                grid.add(foo, 0, 3);
                boo = new TextField();
                grid.add(boo, 1, 3);
                textfields.add(boo);
                root.setCenter(grid);
            }
        );
        newDisCharger.setOnAction((ActionEvent e) ->
        {
            cleanScreen();
            grid.setMaxSize(500, 300);
            t = new Text("Dis-charger Creation");
            t.setId("welcome");
            grid.add(t, 0, 0, 4, 1);
            TextField boo;
            Label foo;
            foo = new Label("Charging station link:");
            grid.add(foo, 0, 1);
            boo = new TextField();
            grid.add(boo, 1, 1);
            textfields.add(boo);
            foo = new Label("Name of dis-charger(optional):");
            grid.add(foo, 0, 2);
            boo = new TextField();
            grid.add(boo, 1, 2);
            textfields.add(boo);
            root.setCenter(grid);
        });
        stationCreation.setOnAction((ActionEvent e) ->
        {
            ChargingStation s;
            if ((textfields.get(1).getText()==null)&&(textfields.get(2).getText()==null)) {
                s = new ChargingStation(stations.size() + 1, textfields.get(0).getText());
                s.setUnitPrice(Integer.parseInt(textfields.get(5).getText()));
                s.setUnitPrice(Integer.parseInt(textfields.get(6).getText()));
                s.setUnitPrice(Integer.parseInt(textfields.get(7).getText()));
                if(textfields.get(4).getText() != null)
                    s.setExchangeSlots(Integer.parseInt(textfields.get(4).getText()));
                if (textfields.get(5).getText() != null)
                {
                    int len = Integer.parseInt(textfields.get(4).getText());
                    DisCharger dsch;
                    for (int i=0; i<len; i++)
                        dsch = new DisCharger(s.reDisChargers().length, s);
                }
            }
            else if(textfields.get(2).getText()==null)
            {
                String[] kinds;
                String[] energ;
                int len = Integer.parseInt(textfields.get(1).getText());
                kinds = new String[len];
                energ = new String[energies.size()];
                for(int i=0; i<len; i++)
                    kinds[i] = "fast";
                for(int i=0; i<energies.size(); i++)
                    energ[i] = energies.get(i);
                s = new ChargingStation(stations.size() + 1, textfields.get(0).getText(), kinds, energ);
                s.setUnitPrice(Integer.parseInt(textfields.get(5).getText()));
                s.setUnitPrice(Integer.parseInt(textfields.get(6).getText()));
                s.setUnitPrice(Integer.parseInt(textfields.get(7).getText()));
                if(textfields.get(4).getText() != null)
                    s.setExchangeSlots(Integer.parseInt(textfields.get(4).getText()));
                if (textfields.get(5).getText() != null)
                {
                    len = Integer.parseInt(textfields.get(4).getText());
                    DisCharger dsch;
                    for (int i=0; i<len; i++)
                        dsch = new DisCharger(s.reDisChargers().length, s);
                }
            }
            else
            {
                String[] kinds;
                String[] energ;
                int len = Integer.parseInt(textfields.get(1).getText());
                int len1 = Integer.parseInt(textfields.get(2).getText());
                kinds = new String[len + len1];
                energ = new String[energies.size()];
                for(int i=0; i<len; i++)
                    kinds[i] = "fast";
                for(int i = len; i<len+len1; i++)
                    kinds[i] = "slow";
                for(int i=0; i<energies.size(); i++)
                    energ[i] = energies.get(i);
                s = new ChargingStation(stations.size() + 1, textfields.get(0).getText(), kinds, energ);
                s.setUnitPrice(Integer.parseInt(textfields.get(5).getText()));
                s.setUnitPrice(Integer.parseInt(textfields.get(6).getText()));
                s.setUnitPrice(Integer.parseInt(textfields.get(7).getText()));
                if(textfields.get(4).getText() != null)
                    s.setExchangeSlots(Integer.parseInt(textfields.get(4).getText()));
                if (textfields.get(5).getText() != null)
                {
                    len = Integer.parseInt(textfields.get(4).getText());
                    DisCharger dsch;
                    for (int i=0; i<len; i++) {
                        dsch = new DisCharger(s.reDisChargers().length, s);
                        s.insertDisCharger(dsch);
                    }
                }
            }
            stations.add(s);
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