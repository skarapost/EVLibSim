package evlibsim;

import evlib.sources.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.function.Predicate;

import static evlibsim.EVLibSim.*;

class Energy {

    static final MenuItem newEnergyPackages = new MenuItem("Add energy");
    static final MenuItem newEnergySource = new MenuItem("New energy source");
    static final MenuItem deleteEnergySource = new MenuItem("Remove energy source");
    static final MenuItem sortEnergies = new MenuItem("Sort energies");
    private static final Menu energy = new Menu("Energy");
    private static final Button addEnergies = new Button("Add");
    private static final Button sort = new Button("Sort");
    private static final Image help = new Image("/help.png");

    //Building of Energy menu item.
    static Menu createEnergyMenu() {
        energy.getItems().addAll(newEnergySource, deleteEnergySource, new SeparatorMenuItem(),
                newEnergyPackages, new SeparatorMenuItem(), sortEnergies);

        //Building of NewEnergySource menu item
        newEnergySource.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            List<String> energies = new ArrayList<>();
            String[] a = {"Solar", "Wind", "Wave", "Nonrenewable", "Hydroelectric", "Geothermal", "Discharging"};
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
            dialog.setTitle("Energy source insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose an energy source: ");
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
                Maintenance.completionMessage("energy source addition");
            });
        });

        //Creates the Delete EnergySource MenuItem
        deleteEnergySource.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            ArrayList<String> energies = new ArrayList<>(Arrays.asList(currentStation.getSources()));
            energies.remove("Discharging");
            ChoiceDialog<String> dialog = new ChoiceDialog<>(energies.get(0), energies);
            dialog.setTitle("EnergySource removal");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose an energy source: ");
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
                Maintenance.completionMessage("energy source removal");
            });
        });

        //Implements the sorting of the energies. The user sets the order at every charging the Charger will look for energy.
        sortEnergies.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 400);
            TextField boo;
            Label foo;
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Solar") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Solar")) {
                foo = new Label("Solar*: ");
                foo.setTooltip(new Tooltip("The order in which the station will look for energy from solar energy inventory during a charging."));
                foo.getTooltip().setPrefWidth(200);
                foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Solar: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 0, 0);
            grid.add(boo, 1, 0);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Wind") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wind")) {
                foo = new Label("Wind*: ");
                foo.setTooltip(new Tooltip("The order in which the station looks for energy from wind energy inventory during a charging."));
                foo.getTooltip().setPrefWidth(200);
                foo.getTooltip().setWrapText(true);
            } else {
                foo = new Label("Wind: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 2, 0);
            grid.add(boo, 3, 0);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Wave") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wave")) {
                foo = new Label("Wave*: ");
                foo.setTooltip(new Tooltip("The order in which the station looks for energy from wave energy repository during a charging."));
                foo.getTooltip().setPrefWidth(200);
                foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Wave: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 0, 1);
            grid.add(boo, 1, 1);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Hydroelectric") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Hydroelectric")) {
                foo = new Label("Hydroelectric*: ");
                foo.setTooltip(new Tooltip("The order in which the station looks for energy from hydroelectric energy repository during a charging."));
                foo.getTooltip().setPrefWidth(200);
                foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Hydroelectric: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 2, 1);
            grid.add(boo, 3, 1);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Nonrenewable") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Nonrenewable")) {
                foo = new Label("Nonrenewable*: ");
                foo.setTooltip(new Tooltip("The order in which the station looks for energy from nonrenewable energy repository during a charging."));
                foo.getTooltip().setPrefWidth(200);
                foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Nonrenewable: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 0, 2);
            grid.add(boo, 1, 2);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Geothermal") + 1));
            textfields.add(boo);
            if (Maintenance.checkEnergy("Geothermal")) {
                foo = new Label("Geothermal*: ");
                foo.setTooltip(new Tooltip("The order in which the station looks for energy from geothermal energy repository during a charging."));
                foo.getTooltip().setPrefWidth(200);
                foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Geothermal: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 2, 2);
            grid.add(boo, 3, 2);
            boo = new TextField(String.valueOf(Arrays.asList(currentStation.getSources()).indexOf("Discharging") + 1));
            textfields.add(boo);
            foo = new Label("Discharging*: ");
            foo.setTooltip(new Tooltip("The order in which the station looks for energy from discharging energy repository during a charging."));
            foo.setGraphic(new ImageView(help));
            foo.getTooltip().setPrefWidth(200);
            foo.getTooltip().setWrapText(true);
            grid.add(foo, 0, 3);
            grid.add(boo, 1, 3);
            foo = new Label("*Required");
            grid.add(foo, 0, 4, 2, 1);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, sort);
            grid.add(buttonsBox, 2, 4, 2, 1);
            sort.setDefaultButton(true);
            root.setCenter(grid);
        });
        //Choice for adding new energy packages in every EnergySource.
        newEnergyPackages.setOnAction(e -> {
            if (Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 400);
            TextField boo;
            Label foo;
            boo = new TextField();
            textfields.add(boo);
            if (Maintenance.checkEnergy("Solar")) {
                foo = new Label("Solar*: ");
                foo.setTooltip(new Tooltip("Addition of an energy package for the solar source."));
                foo.getTooltip().setPrefWidth(200);foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Solar: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 0, 0);
            grid.add(boo, 1, 0);
            boo = new TextField();
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wind")) {
                foo = new Label("Wind*: ");
                foo.setTooltip(new Tooltip("Addition of an energy package for the wind source."));
                foo.getTooltip().setPrefWidth(200);foo.getTooltip().setWrapText(true);
            } else {
                foo = new Label("Wind: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 2, 0);
            grid.add(boo, 3, 0);
            boo = new TextField();
            textfields.add(boo);
            if (Maintenance.checkEnergy("Wave")) {
                foo = new Label("Wave*: ");
                foo.setTooltip(new Tooltip("Addition of an energy package for the wave source."));
                foo.getTooltip().setPrefWidth(200);foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Wave: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 0, 1);
            grid.add(boo, 1, 1);
            boo = new TextField();
            textfields.add(boo);
            if (Maintenance.checkEnergy("Hydroelectric")) {
                foo = new Label("Hydroelectric*: ");
                foo.setTooltip(new Tooltip("Addition of an energy package for the hydroelectric source."));
                foo.getTooltip().setPrefWidth(200);foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Hydroelectric: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 2, 1);
            grid.add(boo, 3, 1);
            boo = new TextField();
            textfields.add(boo);
            if (Maintenance.checkEnergy("Nonrenewable")) {
                foo = new Label("Nonrenewable*: ");
                foo.setTooltip(new Tooltip("Addition of an energy package for the nonrenewable source."));
                foo.getTooltip().setPrefWidth(200);foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Nonrenewable: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 0, 2);
            grid.add(boo, 1, 2);
            boo = new TextField();
            textfields.add(boo);
            if (Maintenance.checkEnergy("Geothermal")) {
                foo = new Label("Geothermal*: ");
                foo.setTooltip(new Tooltip("Addition of an energy package for the geothermal source."));
                foo.getTooltip().setPrefWidth(200);foo.getTooltip().setWrapText(true);
            }
            else {
                foo = new Label("Geothermal: ");
                boo.setDisable(true);
            }
            foo.setGraphic(new ImageView(help));
            grid.add(foo, 2, 2);
            grid.add(boo, 3, 2);
            foo = new Label("*Required");
            grid.add(foo, 0, 3, 2, 1);
            Predicate buttonPredicate = b -> (b != EVLibSim.cancel);
            HBox buttonsBox = EVLibSim.getButtonsBox();
            buttonsBox.getChildren().removeIf(buttonPredicate);
            buttonsBox.getChildren().add(0, addEnergies);
            grid.add(buttonsBox, 2, 3, 2, 1);
            addEnergies.setDefaultButton(true);
            root.setCenter(grid);
        });

        //Buttons
        addEnergies.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
            if (Maintenance.positiveOrZero())
                return;
            try {
                for (String en : currentStation.getSources())
                    switch (en) {
                        case "Solar":
                            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                                currentStation.getEnergySource("Solar").insertAmount(Double.parseDouble(textfields.get(0).getText()));
                            else
                                currentStation.getEnergySource("Solar").insertAmount(Double.parseDouble(textfields.get(0).getText()) * 1000);
                            break;
                        case "Wind":
                            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                                currentStation.getEnergySource("Wind").insertAmount(Double.parseDouble(textfields.get(1).getText()));
                            else
                                currentStation.getEnergySource("Wind").insertAmount(Double.parseDouble(textfields.get(1).getText()) * 1000);
                            break;
                        case "Wave":
                            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                                currentStation.getEnergySource("Wave").insertAmount(Double.parseDouble(textfields.get(2).getText()));
                            else
                                currentStation.getEnergySource("Wave").insertAmount(Double.parseDouble(textfields.get(2).getText()) * 1000);
                            break;
                        case "Hydroelectric":
                            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                                currentStation.getEnergySource("Hydroelectric").insertAmount(Double.parseDouble(textfields.get(3).getText()));
                            else
                                currentStation.getEnergySource("Hydroelectric").insertAmount(Double.parseDouble(textfields.get(3).getText()) * 1000);
                            break;
                        case "Nonrenewable":
                            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                                currentStation.getEnergySource("Nonrenewable").insertAmount(Double.parseDouble(textfields.get(4).getText()));
                            else
                                currentStation.getEnergySource("Nonrenewable").insertAmount(Double.parseDouble(textfields.get(4).getText()) * 1000);
                            break;
                        case "Geothermal":
                            if (energyUnit.getSelectionModel().getSelectedIndex() == 0)
                                currentStation.getEnergySource("Geothermal").insertAmount(Double.parseDouble(textfields.get(5).getText()));
                            else
                                currentStation.getEnergySource("Geothermal").insertAmount(Double.parseDouble(textfields.get(5).getText()) * 1000);
                            break;
                        default:
                            break;
                    }
                if (!currentStation.getUpdateMode())
                    currentStation.updateStorage();
                Maintenance.completionMessage("insertion of energy amounts");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
            }
        });
        sort.setOnAction(e -> {
            Maintenance.trimTextfields();
            if (Maintenance.fieldCompletionCheck())
                return;
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
            for (TextField a: textfields)
                if (!a.isDisabled()) {
                    if (Integer.parseInt(a.getText()) > currentStation.getSources().length || Integer.parseInt(a.getText()) < 1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please put numbers from 1-" + currentStation.getSources().length + ".");
                        alert.showAndWait();
                        return;
                    }
                }
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
                    sources[Integer.parseInt(textfields.get(6).getText()) - 1] = "Discharging";
                currentStation.customEnergySorting(sources);
                Maintenance.completionMessage("sorting");
                startScreen.fire();
            } catch (Exception ex) {
                Maintenance.refillBlanks();
            }
        });
        return energy;
    }
}
