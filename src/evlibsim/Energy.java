package evlibsim;

import EVLib.Sources.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.*;

import static evlibsim.EVLibSim.*;

class Energy {

    private static Menu energy = new Menu("Energy");
    private static final MenuItem updateStorage = new MenuItem("Update Storage");
    static final MenuItem newEnergyPackages = new MenuItem("New amounts");
    private static final MenuItem sortEnergies = new MenuItem("Sort Energies");
    private static final Button addEnergies = new Button("Add");
    private static final Button sort = new Button("Sort");
    private static MenuItem newEnergy = new MenuItem("New EnergySource");
    private static MenuItem deletion = new MenuItem("Remove EnergySource");

    static Menu createEnergyMenu()
    {
        energy.getItems().addAll(newEnergy, deletion, new SeparatorMenuItem(),
                newEnergyPackages, updateStorage, new SeparatorMenuItem(), sortEnergies);

        newEnergy.setOnAction(e -> {
            List<String> energies = new ArrayList<>();
            String[] a = {"solar", "wind", "wave", "nonrenewable", "hydroelectric", "geothermal", "discharging"};
            String[] b = currentStation.getSources();
            List<String> aL = Arrays.asList(a);
            List<String> bL = Arrays.asList(b);
            Set<String> common = new HashSet<>(aL);
            common.retainAll(bL);
            Set<String> uncommon = new HashSet<>(aL);
            uncommon.addAll(bL);
            uncommon.removeAll(common);
            uncommon.forEach(en -> energies.add(en));
            ChoiceDialog<String> dialog = new ChoiceDialog<String>(energies.get(0), energies);
            dialog.setTitle("EnergySource Insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose an EnergySource: ");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent())
            {
                switch (result.get())
                {
                    case "solar":
                        currentStation.addEnergySource(new Solar());
                        break;
                    case "nonrenewable":
                        currentStation.addEnergySource(new NonRenewable());
                        break;
                    case "geothermal":
                        currentStation.addEnergySource(new Geothermal());
                        break;
                    case "wind":
                        currentStation.addEnergySource(new Wind());
                        break;
                    case "wave":
                        currentStation.addEnergySource(new Wave());
                        break;
                    case "hydroelectric":
                        currentStation.addEnergySource(new HydroElectric());
                        break;
                }
                Maintenance.completionMessage("EnergySource insertion");
            }
        });

        deletion.setOnAction(e -> {
            ArrayList<String> energies = new ArrayList<>(Arrays.asList(currentStation.getSources()));
            energies.remove("discharging");
            ChoiceDialog<String> dialog = new ChoiceDialog<>(energies.get(0), energies);
            dialog.setTitle("EnergySource Removal");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose an EnergySource: ");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent())
            {
                switch (result.get())
                {
                    case "solar":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("solar"));
                        break;
                    case "nonrenewable":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("nonrenewable"));
                        break;
                    case "geothermal":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("geothermal"));
                        break;
                    case "wind":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("wind"));
                        break;
                    case "wave":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("wave"));
                        break;
                    case "hydroelectric":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("hydroelectric"));
                        break;
                }
                Maintenance.completionMessage("EnergySource removal");
            }
        });

        updateStorage.setOnAction((ActionEvent e) -> {
            if (Maintenance.stationCheck())
                return;
            if(!currentStation.getUpdateMode()) {
                currentStation.updateStorage();
                Maintenance.completionMessage("storage update");
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Automatic update storage.");
                alert.showAndWait();
            }
        });
        sortEnergies.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(600, 350);
            TextField boo;
            Label foo;
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("solar"))
                foo = new Label("Solar*: ");
            else {
                foo = new Label("Solar: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 1);
            grid.add(boo, 1, 1);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("wind")) {
                foo = new Label("Wind*: ");
            } else {
                foo = new Label("Wind: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 1);
            grid.add(boo, 3, 1);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("wave"))
                foo = new Label("Wave*: ");
            else {
                foo = new Label("Wave: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 2);
            grid.add(boo, 1, 2);
            boo = new TextField("0");
            textfields.add(boo);
            if(Maintenance.checkEnergy("hydroelectric"))
                foo = new Label("Hydro-Electric*: ");
            else {
                foo = new Label("Hydro-Electric: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 2);
            grid.add(boo, 3, 2);
            boo = new TextField("0");
            textfields.add(boo);
            if(Maintenance.checkEnergy("nonrenewable"))
                foo = new Label("Non-Renewable*: ");
            else {
                foo = new Label("Non-Renewable: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 3);
            grid.add(boo, 1, 3);
            boo = new TextField("0");
            textfields.add(boo);
            if(Maintenance.checkEnergy("geothermal"))
                foo = new Label("Geothermal*: ");
            else {
                foo = new Label("Geothermal: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 3);
            grid.add(boo, 3, 3);
            boo = new TextField("0");
            textfields.add(boo);
            foo = new Label("DisCharging*: ");
            grid.add(foo, 0, 4);
            grid.add(boo, 1, 4);
            foo = new Label("*Selected");
            grid.add(foo, 0, 5, 2, 1);
            grid.add(sort, 0, 6);
            sort.setDefaultButton(true);
            root.setCenter(grid);
        });
        newEnergyPackages.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(800, 500);
            TextField boo;
            Label foo;
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("solar"))
                foo = new Label("Solar*: ");
            else {
                foo = new Label("Solar: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 1);
            grid.add(boo, 1, 1);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("wind")) {
                foo = new Label("Wind*: ");
            } else {
                foo = new Label("Wind: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 1);
            grid.add(boo, 3, 1);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("wave"))
                foo = new Label("Wave*: ");
            else {
                foo = new Label("Wave: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 2);
            grid.add(boo, 1, 2);
            boo = new TextField("0");
            textfields.add(boo);
            if(Maintenance.checkEnergy("hydroelectric"))
                foo = new Label("Hydro-Electric*: ");
            else {
                foo = new Label("Hydro-Electric: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 2);
            grid.add(boo, 3, 2);
            boo = new TextField("0");
            textfields.add(boo);
            if(Maintenance.checkEnergy("nonrenewable"))
                foo = new Label("Non-Renewable*: ");
            else {
                foo = new Label("Non-Renewable: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 3);
            grid.add(boo, 1, 3);
            boo = new TextField("0");
            textfields.add(boo);
            if(Maintenance.checkEnergy("geothermal"))
                foo = new Label("Geothermal*: ");
            else {
                foo = new Label("Geothermal: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 3);
            grid.add(boo, 3, 3);
            grid.add(addEnergies, 0, 4);
            addEnergies.setDefaultButton(true);
            foo = new Label("*Selected");
            grid.add(foo, 0, 5, 2, 1);
            root.setCenter(grid);
        });

        addEnergies.setOnAction(e -> {
            for(String en: currentStation.getSources())
                switch (en)
                {
                    case "solar":
                        currentStation.getEnergySource("solar").insertAmount(Double.parseDouble(textfields.get(0).getText()));
                        break;
                    case "wind":
                        currentStation.getEnergySource("wind").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                        break;
                    case "wave":
                        currentStation.getEnergySource("wave").insertAmount(Double.parseDouble(textfields.get(2).getText()));
                        break;
                    case "hydroelectric":
                        currentStation.getEnergySource("hydroelectric").insertAmount(Double.parseDouble(textfields.get(3).getText()));
                        break;
                    case "nonrenewable":
                        currentStation.getEnergySource("nonrenewable").insertAmount(Double.parseDouble(textfields.get(4).getText()));
                        break;
                    case "geothermal":
                        currentStation.getEnergySource("geothermal").insertAmount(Double.parseDouble(textfields.get(5).getText()));
                        break;
                    default:
                        break;
                }
            Maintenance.completionMessage("insertion of energy amounts");
        });
        sort.setOnAction(e -> {
            if(Maintenance.fieldCompletionCheck())
                return;
            HashSet<String> b = new HashSet<>();
            int counter = 0;
            for(TextField s: textfields)
                if(!s.isDisabled()) {
                    b.add(s.getText());
                    counter++;
                }
            if (b.size() != counter)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("All values have to be unique.");
                alert.showAndWait();
                return;
            }
            for(TextField a: textfields)
                if(!a.isDisabled()) {
                    if (Integer.parseInt(a.getText()) > currentStation.getSources().length || Integer.parseInt(a.getText()) < 1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please put numbers from 1-" + currentStation.getSources().length + ".");
                        alert.showAndWait();
                        return;
                    }
                }
            String[] sources = new String[currentStation.getSources().length];
            if (!textfields.get(0).isDisabled())
                sources[Integer.parseInt(textfields.get(0).getText()) - 1] = "solar";
            if (!textfields.get(1).isDisabled())
                sources[Integer.parseInt(textfields.get(1).getText()) - 1] = "wind";
            if (!textfields.get(2).isDisabled())
                sources[Integer.parseInt(textfields.get(2).getText()) - 1] = "wave";
            if (!textfields.get(3).isDisabled())
                sources[Integer.parseInt(textfields.get(3).getText()) - 1] = "hydroelectric";
            if (!textfields.get(4).isDisabled())
                sources[Integer.parseInt(textfields.get(4).getText()) - 1] = "nonrenewable";
            if (!textfields.get(5).isDisabled())
                sources[Integer.parseInt(textfields.get(5).getText()) - 1] = "geothermal";
            if (!textfields.get(6).isDisabled())
                sources[Integer.parseInt(textfields.get(6).getText()) - 1] = "discharging";
            currentStation.customEnergySorting(sources);
            Maintenance.completionMessage("sorting");
        });
        return energy;
    }
}
