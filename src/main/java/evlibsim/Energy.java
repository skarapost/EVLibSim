package evlibsim;

import evlib.sources.*;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.function.Predicate;

import static evlibsim.EVLibSim.*;

class Energy {

    static final MenuItem newEnergyPackages = new MenuItem("Add energy");
    static final MenuItem newEnergySource = new MenuItem("New energy source");
    static final MenuItem deleteEnergySource = new MenuItem("Remove energy source");
    static final MenuItem updateStorage = new MenuItem("Update storage");
    static final MenuItem sortEnergies = new MenuItem("Sort energies");
    private static final Menu energy = new Menu("Energy");
    private static final Button addEnergies = new Button("Add");
    private static final Button sort = new Button("Sort");

    //Building of Energy menu item.
    static Menu createEnergyMenu() {
        energy.getItems().addAll(newEnergySource, deleteEnergySource, new SeparatorMenuItem(),
                newEnergyPackages, updateStorage, new SeparatorMenuItem(), sortEnergies);

        //Building of NewEnergySource menu item
        newEnergySource.setOnAction(e -> {
            List<String> energies = new ArrayList<>();
            String[] a = {"Solar", "Wind", "Wave", "Nonrenewable", "Hydroelectric", "Geothermal", "DisCharging"};
            String[] b = currentStation.getSources();
            List<String> aL = Arrays.asList(a);
            List<String> bL = Arrays.asList(b);
            Set<String> common = new HashSet<>(aL);
            common.retainAll(bL);
            Set<String> uncommon = new HashSet<>(aL);
            uncommon.addAll(bL);
            uncommon.removeAll(common);
            energies.addAll(uncommon);
            ChoiceDialog<String> dialog = new ChoiceDialog<>(energies.get(0), energies);
            dialog.setTitle("EnergySource Insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose an EnergySource: ");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> {
                switch (s) {
                    case "Solar":
                        currentStation.addEnergySource(new Solar());
                        break;
                    case "Nonrenewable":
                        currentStation.addEnergySource(new Nonrenewable());
                        break;
                    case "Geothermal":
                        currentStation.addEnergySource(new Geothermal());
                        break;
                    case "Wind":
                        currentStation.addEnergySource(new Wind());
                        break;
                    case "Wave":
                        currentStation.addEnergySource(new Wave());
                        break;
                    case "Hydroelectric":
                        currentStation.addEnergySource(new Hydroelectric());
                        break;
                }
                Maintenance.completionMessage("EnergySource insertion");
                startScreen.fire();
            });
        });

        //Creates the Delete EnergySource MenuItem
        deleteEnergySource.setOnAction(e -> {
            ArrayList<String> energies = new ArrayList<>(Arrays.asList(currentStation.getSources()));
            energies.remove("DisCharging");
            ChoiceDialog<String> dialog = new ChoiceDialog<>(energies.get(0), energies);
            dialog.setTitle("EnergySource Removal");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose an EnergySource: ");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(s -> {
                switch (s) {
                    case "Solar":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("Solar"));
                        break;
                    case "Nonrenewable":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("Nonrenewable"));
                        break;
                    case "Geothermal":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("Geothermal"));
                        break;
                    case "Wind":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("Wind"));
                        break;
                    case "Wave":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("Wave"));
                        break;
                    case "Hydroelectric":
                        currentStation.deleteEnergySource(currentStation.getEnergySource("Hydroelectric"));
                        break;
                }
                Maintenance.completionMessage("EnergySource removal");
                startScreen.fire();
            });
        });

        //Implements the energy storage update, in case the automatic update mode is false.
        updateStorage.setOnAction((ActionEvent e) -> {
            if (Maintenance.stationCheck())
                return;
            if (!currentStation.getUpdateMode()) {
                currentStation.updateStorage();
                Maintenance.completionMessage("storage update");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Automatic update storage.");
                alert.showAndWait();
            }
        });

        //Implements the sorting of the energies. The user sets the order at every charging the Charger will look for energy.
        sortEnergies.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 300);
            TextField boo;
            Label foo;
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Solar") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Solar"))
                foo = new Label("Solar*: ");
            else {
                foo = new Label("Solar: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 0);
            grid.add(boo, 1, 0);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Wind") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wind")) {
                foo = new Label("Wind*: ");
            } else {
                foo = new Label("Wind: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 0);
            grid.add(boo, 3, 0);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Wave") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wave"))
                foo = new Label("Wave*: ");
            else {
                foo = new Label("Wave: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 1);
            grid.add(boo, 1, 1);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Hydroelectric") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Hydroelectric"))
                foo = new Label("Hydroelectric*: ");
            else {
                foo = new Label("Hydroelectric: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 1);
            grid.add(boo, 3, 1);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Nonrenewable") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Nonrenewable"))
                foo = new Label("Nonrenewable*: ");
            else {
                foo = new Label("Nonrenewable: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 2);
            grid.add(boo, 1, 2);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Geothermal") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Geothermal"))
                foo = new Label("Geothermal*: ");
            else {
                foo = new Label("Geothermal: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 2);
            grid.add(boo, 3, 2);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("DisCharging") + 1));
            textfields.add(boo);
            foo = new Label("DisCharging*: ");
            grid.add(foo, 0, 3);
            grid.add(boo, 1, 3);
            foo = new Label("*Selected");
            grid.add(foo, 0, 4, 2, 1);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, sort);
            grid.add(buttonsBox, 0, 5, 2, 1);
            sort.setDefaultButton(true);
            root.setCenter(grid);
        });
        //Choice for adding new energy packages in every EnergySource.
        newEnergyPackages.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 300);
            TextField boo;
            Label foo;
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("Solar"))
                foo = new Label("Solar*: ");
            else {
                foo = new Label("Solar: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 0);
            grid.add(boo, 1, 0);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wind")) {
                foo = new Label("Wind*: ");
            } else {
                foo = new Label("Wind: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 0);
            grid.add(boo, 3, 0);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wave"))
                foo = new Label("Wave*: ");
            else {
                foo = new Label("Wave: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 1);
            grid.add(boo, 1, 1);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("Hydroelectric"))
                foo = new Label("Hydroelectric*: ");
            else {
                foo = new Label("Hydroelectric: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 1);
            grid.add(boo, 3, 1);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("Nonrenewable"))
                foo = new Label("Nonrenewable*: ");
            else {
                foo = new Label("Nonrenewable: ");
                boo.setDisable(true);
            }
            grid.add(foo, 0, 2);
            grid.add(boo, 1, 2);
            boo = new TextField("0");
            textfields.add(boo);
            if (Maintenance.checkEnergy("Geothermal"))
                foo = new Label("Geothermal*: ");
            else {
                foo = new Label("Geothermal: ");
                boo.setDisable(true);
            }
            grid.add(foo, 2, 2);
            grid.add(boo, 3, 2);
            foo = new Label("*Selected");
            grid.add(foo, 0, 3, 2, 1);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, addEnergies);
            grid.add(buttonsBox, 0, 4, 2, 1);
            addEnergies.setDefaultButton(true);
            root.setCenter(grid);
        });

        //Buttons
        addEnergies.setOnAction(e -> {
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^0-9.]", "")));
            try {
                for (String en : currentStation.getSources())
                    switch (en) {
                        case "Solar":
                            currentStation.getEnergySource("Solar").insertAmount(Double.parseDouble(textfields.get(0).getText()));
                            break;
                        case "Wind":
                            currentStation.getEnergySource("Wind").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            break;
                        case "Wave":
                            currentStation.getEnergySource("Wave").insertAmount(Double.parseDouble(textfields.get(2).getText()));
                            break;
                        case "Hydroelectric":
                            currentStation.getEnergySource("Hydroelectric").insertAmount(Double.parseDouble(textfields.get(3).getText()));
                            break;
                        case "Nonrenewable":
                            currentStation.getEnergySource("Nonrenewable").insertAmount(Double.parseDouble(textfields.get(4).getText()));
                            break;
                        case "Geothermal":
                            currentStation.getEnergySource("Geothermal").insertAmount(Double.parseDouble(textfields.get(5).getText()));
                            break;
                        default:
                            break;
                    }
                Maintenance.completionMessage("insertion of energy amounts");
                newEnergyPackages.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                newEnergyPackages.fire();
            }
        });
        sort.setOnAction(e -> {
            if (Maintenance.fieldCompletionCheck())
                return;
            textfields.forEach(field -> field.setText(field.getText().replaceAll("[^0-9]", "")));
            HashSet<String> b = new HashSet<>();
            int counter = 0;
            for (TextField s : textfields)
                if (!s.isDisabled()) {
                    b.add(s.getText());
                    counter++;
                }
            if (b.size() != counter) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("All values have to be unique.");
                alert.showAndWait();
                return;
            }
            textfields.forEach(a -> {
                if (!a.isDisabled()) {
                    if (Integer.parseInt(a.getText()) > currentStation.getSources().length || Integer.parseInt(a.getText()) < 1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please put numbers from 1-" + currentStation.getSources().length + ".");
                        alert.showAndWait();
                    }
                }
            });
            try {
                String[] sources = new String[currentStation.getSources().length];
                if (!textfields.get(0).isDisabled())
                    sources[Integer.parseInt(textfields.get(0).getText()) - 1] = "Solar";
                if (!textfields.get(1).isDisabled())
                    sources[Integer.parseInt(textfields.get(1).getText()) - 1] = "Wind";
                if (!textfields.get(2).isDisabled())
                    sources[Integer.parseInt(textfields.get(2).getText()) - 1] = "Wave";
                if (!textfields.get(3).isDisabled())
                    sources[Integer.parseInt(textfields.get(3).getText()) - 1] = "Hydroelectric";
                if (!textfields.get(4).isDisabled())
                    sources[Integer.parseInt(textfields.get(4).getText()) - 1] = "Nonrenewable";
                if (!textfields.get(5).isDisabled())
                    sources[Integer.parseInt(textfields.get(5).getText()) - 1] = "Geothermal";
                if (!textfields.get(6).isDisabled())
                    sources[Integer.parseInt(textfields.get(6).getText()) - 1] = "DisCharging";
                currentStation.customEnergySorting(sources);
                Maintenance.completionMessage("sorting");
                sortEnergies.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
                sortEnergies.fire();
            }
        });
        return energy;
    }
}
