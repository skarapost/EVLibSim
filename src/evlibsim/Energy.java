package evlibsim;

import EVLib.Sources.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.*;

import static evlibsim.EVLibSim.*;

class Energy {
    private static final Menu energy = new Menu("Energy");
    private static final Menu hydro = new Menu("Hydroelectric energy");
    private static final Menu wind = new Menu("Wind energy");
    private static final Menu wave = new Menu("Wave energy");
    private static final Menu solar = new Menu("Solar energy");
    private static final Menu geothermal = new Menu("Geothermal energy");
    private static final Menu nonrenewable = new Menu("Nonrenewable energy");
    private static final MenuItem updateStorage = new MenuItem("Update Storage");
    static final MenuItem newEnergyPackages = new MenuItem("New amounts");
    private static final MenuItem sortEnergies = new MenuItem("Sort Energies");
    private static final Button addEnergies = new Button("Add");
    private static final Button sort = new Button("Sort");
    private static final ArrayList<MenuItem> newEnergies = new ArrayList<>();
    private static final ArrayList<MenuItem> newAmounts = new ArrayList<>();
    private static final ArrayList<MenuItem> deletions = new ArrayList<>();

    static Menu createEnergyMenu()
    {
        MenuItem newEnergy = new MenuItem("Hydro-Electric energy");
        MenuItem newAmount = new MenuItem("New amount of hydroelectric energy");
        MenuItem deletion = new MenuItem("Energy removal");
        hydro.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        newEnergy = new MenuItem("Wind energy");
        newAmount = new MenuItem("New amount of wind energy");
        deletion = new MenuItem("Energy removal");
        wind.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        newEnergy = new MenuItem("Wave energy");
        newAmount = new MenuItem("New amount of wave energy");
        deletion = new MenuItem("Energy removal");
        wave.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        newEnergy = new MenuItem("Solar energy");
        newAmount = new MenuItem("New amount of solar energy");
        deletion = new MenuItem("Energy removal");
        solar.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        newEnergy = new MenuItem("Geothermal energy");
        newAmount = new MenuItem("New amount of geothermal energy");
        deletion = new MenuItem("Energy removal");
        geothermal.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        newEnergy = new MenuItem("Non-Renewable energy");
        newAmount = new MenuItem("New amount of nonrenewable energy");
        deletion = new MenuItem("Energy removal");
        nonrenewable.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        energy.getItems().addAll(hydro, wind, wave, solar, geothermal, nonrenewable, new SeparatorMenuItem(),
                newEnergyPackages, updateStorage, new SeparatorMenuItem(), sortEnergies);
        for(MenuItem m: newEnergies) {
            m.setOnAction(e -> {
                if (Maintenance.stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("Wind energy")) {
                    if ((currentStation.getEnergySource("wind") == null)) {
                        energySource = new Wind();
                        currentStation.addEnergySource(energySource);
                        Maintenance.completionMessage("energy insertion");
                    } else
                        Maintenance.energyAlert("Wind");
                } else if (mi.getText().equals("Wave energy")) {
                    if ((currentStation.getEnergySource("wave") == null)) {
                        energySource = new Wave();
                        currentStation.addEnergySource(energySource);
                        Maintenance.completionMessage("energy insertion");
                    } else
                        Maintenance.energyAlert("Wave");
                } else if (mi.getText().equals("Solar energy")) {
                    if ((currentStation.getEnergySource("solar") == null)) {
                        energySource = new Solar();
                        currentStation.addEnergySource(energySource);
                        Maintenance.completionMessage("energy insertion");
                    } else
                        Maintenance.energyAlert("Solar");
                } else if (mi.getText().equals("Geothermal energy")) {
                    if ((currentStation.getEnergySource("geothermal") == null)) {
                        energySource = new Geothermal();
                        currentStation.addEnergySource(energySource);
                        Maintenance.completionMessage("energy insertion");
                    } else
                        Maintenance.energyAlert("Geothermal");
                } else if (mi.getText().equals("Non-Renewable energy")) {
                    if ((currentStation.getEnergySource("nonrenewable") == null)) {
                        energySource = new NonRenewable();
                        currentStation.addEnergySource(energySource);
                        Maintenance.completionMessage("energy insertion");
                    } else
                        Maintenance.energyAlert("Non-Renewable");
                } else {
                    if ((currentStation.getEnergySource("hydroelectric") == null)) {
                        energySource = new HydroElectric();
                        currentStation.addEnergySource(energySource);
                        Maintenance.completionMessage("energy insertion");
                    } else
                        Maintenance.energyAlert("Hydro-Electric");
                }
            });
        }
        for(MenuItem m: newAmounts) {
            m.setOnAction(e -> {
                if (Maintenance.stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("New amount of wind energy")) {
                    energySource = currentStation.getEnergySource("wind");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(s -> energySource.insertAmount(Double.parseDouble(s)));
                        Maintenance.completionMessage("amount insertion");
                    }
                    else
                        Maintenance.notEnergyAlert("Wind");
                } else if (mi.getText().equals("New amount of wave energy")) {
                    energySource = currentStation.getEnergySource("wave");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(s -> energySource.insertAmount(Double.parseDouble(s)));
                        Maintenance.completionMessage("amount insertion");
                    }
                    else
                        Maintenance.notEnergyAlert("Wave");
                } else if (mi.getText().equals("New amount of solar energy")) {
                    energySource = currentStation.getEnergySource("solar");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(s -> energySource.insertAmount(Double.parseDouble(s)));
                        Maintenance.completionMessage("amount insertion");
                    }
                    else
                        Maintenance.notEnergyAlert("Solar");
                } else if (mi.getText().equals("New amount of geothermal energy")) {
                    energySource = currentStation.getEnergySource("geothermal");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(s -> energySource.insertAmount(Double.parseDouble(s)));
                        Maintenance.completionMessage("amount insertion");
                    }
                    else
                        Maintenance.notEnergyAlert("Geothermal");
                } else if (mi.getText().equals("New amount of nonrenewable energy")) {
                    energySource = currentStation.getEnergySource("nonrenewable");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(s -> energySource.insertAmount(Double.parseDouble(s)));
                        Maintenance.completionMessage("amount insertion");
                    }
                    else
                        Maintenance.notEnergyAlert("Nonrenewable");
                } else {
                    energySource = currentStation.getEnergySource("hydroelectric");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(s -> energySource.insertAmount(Double.parseDouble(s)));
                        Maintenance.completionMessage("amount insertion");
                    }
                    else
                        Maintenance.notEnergyAlert("Hydroelectric");
                }
            });
        }
        for(MenuItem m: deletions) {
            m.setOnAction(e -> {
                if (Maintenance.stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("Remove wind energy")) {
                    energySource = currentStation.getEnergySource("wind");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Wind");
                } else if (mi.getText().equals("Remove wave energy")) {
                    energySource = currentStation.getEnergySource("wave");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Wave");
                } else if (mi.getText().equals("Remove solar energy")) {
                    energySource = currentStation.getEnergySource("solar");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Solar");
                } else if (mi.getText().equals("Remove geothermal energy")) {
                    energySource = currentStation.getEnergySource("geothermal");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Geothermal");
                } else if (mi.getText().equals("Remove nonrenewable energy")) {
                    energySource = currentStation.getEnergySource("nonrenewable");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Non-Renewable");
                } else {
                    energySource = currentStation.getEnergySource("hydroelectric");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Hydro-Electric");
                }
            });
        }
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
                alert.setContentText("No automatic update storage.");
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
