package evlibsim;

import Sources.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Optional;
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
    private static final ArrayList<MenuItem> newEnergies = new ArrayList<>();
    private static final ArrayList<MenuItem> newAmounts = new ArrayList<>();
    private static final ArrayList<MenuItem> deletions = new ArrayList<>();

    public static Menu createEnergyMenu()
    {
        MenuItem newEnergy = new MenuItem("Hydroelectric energy");
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
        newEnergy = new MenuItem("Nonrenewable energy");
        newAmount = new MenuItem("New amount of nonrenewable energy");
        deletion = new MenuItem("Energy removal");
        nonrenewable.getItems().addAll(newEnergy, newAmount, deletion);
        newEnergies.add(newEnergy);
        newAmounts.add(newAmount);
        deletions.add(deletion);
        energy.getItems().addAll(hydro, wind, wave, solar, geothermal, nonrenewable, new SeparatorMenuItem(), updateStorage);
        for(MenuItem m: newEnergies) {
            m.setOnAction(e -> {
                if (!Maintenance.stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("Wind energy")) {
                    if ((currentStation.searchEnergySource("wind") == null)) {
                        energySource = new Wind(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        Maintenance.energyAlert("Wind");
                } else if (mi.getText().equals("Wave energy")) {
                    if ((currentStation.searchEnergySource("wave") == null)) {
                        energySource = new Wave(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        Maintenance.energyAlert("Wave");
                } else if (mi.getText().equals("Solar energy")) {
                    if ((currentStation.searchEnergySource("solar") == null)) {
                        energySource = new Solar(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        Maintenance.energyAlert("Solar");
                } else if (mi.getText().equals("Geothermal energy")) {
                    if ((currentStation.searchEnergySource("geothermal") == null)) {
                        energySource = new Geothermal(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        Maintenance.energyAlert("Geothermal");
                } else if (mi.getText().equals("Nonrenewable energy")) {
                    if ((currentStation.searchEnergySource("nonrenewable") == null)) {
                        energySource = new NonRenewable(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        Maintenance.energyAlert("Nonrenewable");
                } else {
                    if ((currentStation.searchEnergySource("hydroelectric") == null)) {
                        energySource = new HydroElectric(currentStation);
                        currentStation.addEnergySource(energySource);
                    } else
                        Maintenance.energyAlert("Hydroelectric");
                }
            });
        }
        for(MenuItem m: newAmounts) {
            m.setOnAction(e -> {
                if (!Maintenance.stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("New amount of wind energy")) {
                    energySource = currentStation.searchEnergySource("wind");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        energySource.insertAmount(Double.parseDouble(result.get()));
                    }
                    else
                        Maintenance.notEnergyAlert("Wind");
                } else if (mi.getText().equals("New amount of wave energy")) {
                    energySource = currentStation.searchEnergySource("wave");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        energySource.insertAmount(Double.parseDouble(result.get()));
                    }
                    else
                        Maintenance.notEnergyAlert("Wave");
                } else if (mi.getText().equals("New amount of solar energy")) {
                    energySource = currentStation.searchEnergySource("solar");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        energySource.insertAmount(Double.parseDouble(result.get()));
                    }
                    else
                        Maintenance.notEnergyAlert("Solar");
                } else if (mi.getText().equals("New amount of geothermal energy")) {
                    energySource = currentStation.searchEnergySource("geothermal");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        energySource.insertAmount(Double.parseDouble(result.get()));
                    }
                    else
                        Maintenance.notEnergyAlert("Geothermal");
                } else if (mi.getText().equals("New amount of nonrenewable energy")) {
                    energySource = currentStation.searchEnergySource("nonrenewable");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        energySource.insertAmount(Double.parseDouble(result.get()));
                    }
                    else
                        Maintenance.notEnergyAlert("Nonrenewable");
                } else {
                    energySource = currentStation.searchEnergySource("hydroelectric");
                    if ((energySource != null)) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Addition of energy amount");
                        dialog.setHeaderText(null);
                        dialog.setContentText("New amount");
                        Optional<String> result = dialog.showAndWait();
                        energySource.insertAmount(Double.parseDouble(result.get()));
                    }
                    else
                        Maintenance.notEnergyAlert("Hydroelectric");
                }
            });
        }
        for(MenuItem m: deletions) {
            m.setOnAction(e -> {
                if (!Maintenance.stationCheck())
                    return;
                MenuItem mi = (MenuItem) e.getSource();
                EnergySource energySource;
                if (mi.getText().equals("Remove wind energy")) {
                    energySource = currentStation.searchEnergySource("wind");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Wind");
                } else if (mi.getText().equals("Remove wave energy")) {
                    energySource = currentStation.searchEnergySource("wave");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Wave");
                } else if (mi.getText().equals("Remove solar energy")) {
                    energySource = currentStation.searchEnergySource("solar");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Solar");
                } else if (mi.getText().equals("Remove geothermal energy")) {
                    energySource = currentStation.searchEnergySource("geothermal");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Geothermal");
                } else if (mi.getText().equals("Remove nonrenewable energy")) {
                    energySource = currentStation.searchEnergySource("nonrenewable");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Non-Renewable");
                } else {
                    energySource = currentStation.searchEnergySource("hydroelectric");
                    if (energySource != null)
                        currentStation.deleteEnergySource(energySource);
                    else
                        Maintenance.notEnergyAlert("Hydro-Electric");
                }
            });
        }
        updateStorage.setOnAction((ActionEvent e) -> {
            if (!Maintenance.stationCheck())
                return;
            if(!currentStation.reUpdateMode())
                currentStation.updateStorage();
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Update Storage");
                alert.setHeaderText(null);
                alert.setContentText("No automatic update storage.");
                alert.showAndWait();
            }
        });
        return energy;
    }
}
