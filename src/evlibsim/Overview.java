package evlibsim;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

import static evlibsim.EVLibSim.*;

class Overview {
    private static final Button showTotalActivity = new Button("Activity");
    private static final Button report = new Button("Report");
    private static final Button totalEnergy = new Button("Energy");
    private static final VBox mBox = new VBox();

    static VBox createOverviewMenu() {
        mBox.getStyleClass().add("mini-box");
        Text title = new Text("Overview");
        title.setStyle("-fx-font-weight: bold;");
        mBox.getChildren().addAll(title, report, showTotalActivity, totalEnergy);
        mBox.setMaxSize(300, 150);
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
            dialog.setContentText("Please enter an absolute path. The name has to be a text(.txt) file: ");
            Optional<String> path = dialog.showAndWait();
            path.ifPresent(s -> currentStation.genReport(path.get()));
        });

        totalEnergy.setOnAction(e -> {
            if(Maintenance.stationCheck())
                return;
            Maintenance.cleanScreen();
            grid.setMaxSize(700, 300);
            Label foo;
            TextField boo;
            foo = new Label("Total energy: ");
            grid.add(foo, 0, 1);
            boo = new TextField(String.valueOf(currentStation.getTotalEnergy()));
            boo.setEditable(false);
            grid.add(boo, 1, 1);
            if(Maintenance.checkEnergy("Solar"))
                foo = new Label("Solar*: ");
            else
                foo = new Label("Solar: ");
            grid.add(foo, 2, 1);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Solar")));
            boo.setEditable(false);
            grid.add(boo, 3, 1);
            if(Maintenance.checkEnergy("Wind"))
                foo = new Label("Wind*: ");
            else
                foo = new Label("Wind: ");
            grid.add(foo, 0, 2);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Wind")));
            boo.setEditable(false);
            grid.add(boo, 1, 2);
            if(Maintenance.checkEnergy("Wave"))
                foo = new Label("Wave*: ");
            else
                foo = new Label("Wave: ");
            grid.add(foo, 2, 2);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Wave")));
            boo.setEditable(false);
            grid.add(boo, 3, 2);
            if(Maintenance.checkEnergy("Hydroelectric"))
                foo = new Label("Hydroelectric*: ");
            else
                foo = new Label("Hydroelectric: ");
            grid.add(foo, 0, 3);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Hydroelectric")));
            boo.setEditable(false);
            grid.add(boo, 1, 3);
            if(Maintenance.checkEnergy("Nonrenewable"))
                foo = new Label("Nonrenewable*: ");
            else
                foo = new Label("Nonrenewable: ");
            grid.add(foo, 2, 3);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Nonrenewable")));
            boo.setEditable(false);
            grid.add(boo, 3, 3);
            if(Maintenance.checkEnergy("Geothermal"))
                foo = new Label("Geothermal*: ");
            else
                foo = new Label("Geothermal: ");
            grid.add(foo, 0, 4);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("Geothermal")));
            boo.setEditable(false);
            grid.add(boo, 1, 4);
            foo = new Label("DisCharging*: ");
            grid.add(foo, 2, 4);
            boo = new TextField(String.valueOf(currentStation.getSpecificAmount("DisCharging")));
            boo.setEditable(false);
            grid.add(boo, 3, 4);
            foo = new Label("*Selected");
            grid.add(foo, 0, 5);
            root.setCenter(grid);
        });
        return mBox;
    }
}
