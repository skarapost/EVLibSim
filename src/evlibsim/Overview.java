package evlibsim;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

import static evlibsim.EVLibSim.currentStation;
import static evlibsim.EVLibSim.grid;
import static evlibsim.EVLibSim.root;

public class Overview {
    static final Button showTotalActivity = new Button("Activity");
    static final Button report = new Button("Report");
    static final Button totalEnergy = new Button("Energy");
    private static final VBox mBox = new VBox();

    static VBox createOverviewMenu() {
        mBox.getStyleClass().add("mini-box");
        Text title = new Text("Overview");
        title.setStyle("-fx-font-weight: bold;");
        mBox.getChildren().addAll(title, report, showTotalActivity, totalEnergy);
        mBox.setMaxSize(220, 150);
        showTotalActivity.setOnAction(e -> View.totalActivity.fire());

        showTotalActivity.setPrefSize(220, 50);
        report.setPrefSize(220, 50);
        totalEnergy.setPrefSize(220, 50);

        report.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Path insertion");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter an absolute path. " +
                    "The name has to be a text(.txt) file: ");
            Optional<String> path = dialog.showAndWait();
            path.ifPresent(s -> currentStation.genReport(s));
        });

        totalEnergy.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(600, 350);
            Label foo;
            foo = new Label("Total energy: ");
            grid.add(foo, 0, 1);
            foo = new Label(String.valueOf(currentStation.getTotalEnergy()));
            grid.add(foo, 1, 1);
            if(Maintenance.checkEnergy("solar"))
                foo = new Label("Solar*: ");
            else
                foo = new Label("Solar: ");
            grid.add(foo, 2, 1);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("solar")));
            grid.add(foo, 3, 1);
            if(Maintenance.checkEnergy("wind"))
                foo = new Label("Wind*: ");
            else
                foo = new Label("Wind: ");
            grid.add(foo, 0, 2);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("wind")));
            grid.add(foo, 1, 2);
            if(Maintenance.checkEnergy("wave"))
                foo = new Label("Wave*: ");
            else
                foo = new Label("Wave: ");
            grid.add(foo, 2, 2);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("wave")));
            grid.add(foo, 3, 2);
            if(Maintenance.checkEnergy("hydroelectric"))
                foo = new Label("Hydro-Electric*: ");
            else
                foo = new Label("Hydro-Electric: ");
            grid.add(foo, 0, 3);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("hydroelectric")));
            grid.add(foo, 1, 3);
            if(Maintenance.checkEnergy("nonrenewable"))
                foo = new Label("Non-Renewable*: ");
            else
                foo = new Label("Non-Renewable: ");
            grid.add(foo, 2, 3);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("nonrenewable")));
            grid.add(foo, 3, 3);
            if(Maintenance.checkEnergy("geothermal"))
                foo = new Label("Geothermal*: ");
            else
                foo = new Label("Geothermal: ");
            grid.add(foo, 0, 4);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("geothermal")));
            grid.add(foo, 1, 4);
            foo = new Label("Discharging*: ");
            grid.add(foo, 2, 4);
            foo = new Label(String.valueOf(currentStation.getSpecificAmount("discharging")));
            grid.add(foo, 3, 4);
            foo = new Label("*Selected");
            grid.add(foo, 0, 5);
            root.setCenter(grid);
        });
        return mBox;
    }
}
