package evlibsim;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.util.Optional;

import static evlibsim.EVLibSim.*;

class Maintenance {

    static boolean fieldCompletionCheck()
    {
        for(TextField f: textfields) {
            if (f.getText().isEmpty()||f.getText().equals(" ") || f.getText().equals("  ") || f.getText().equals("   ") || f.getText().equals("    ")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Caution");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields. Numbers have to be >=0.");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

    static boolean stationCheck()
    {
        if(stations.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Caution");
            alert.setHeaderText(null);
            alert.setContentText("Please add a ChargingStation.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    static void cleanScreen()
    {
        grid.getChildren().clear();
        root.setCenter(null);
        root.setLeft(null);
        root.setRight(null);
        textfields.clear();
    }

    static void energyAlert(String energySource)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Caution");
        alert.setHeaderText(null);
        alert.setContentText(energySource + " energy is already an option.");
        alert.showAndWait();
    }

    static void notEnergyAlert(String energySource)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Caution");
        alert.setHeaderText(null);
        alert.setContentText(energySource + " energy is not an option.");
        alert.showAndWait();
    }

    static void completionMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Caution");
        alert.setHeaderText(null);
        alert.setContentText("The " + message + " was created successfully.");
        alert.showAndWait();
    }

    static boolean confirmCreation(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(message + " creation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure about the creation?");
        Optional<ButtonType> option = alert.showAndWait();
        return option.get() == ButtonType.OK;
    }

}
