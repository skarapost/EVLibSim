package evlibsim;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.util.Optional;

import static evlibsim.EVLibSim.*;
import static evlibsim.MenuStation.scroll;

class Maintenance {

    static boolean fieldCompletionCheck()
    {
        for(TextField f: textfields) {
            if (f.getText().isEmpty()||f.getText().equals(" ") || f.getText().equals("  ") || f.getText().equals("   ") || f.getText().equals("    ")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields. Numbers have to be >=0.");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    static boolean stationCheck()
    {
        if(stations.size() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please add a ChargingStation.");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    static void cleanScreen()
    {
        grid.getChildren().clear();
        root.setCenter(null);
        root.setLeft(null);
        scroll.setContent(null);
        textfields.clear();
    }

// --Commented out by Inspection START (27/9/2017 3:29 μμ):
//    static void energyAlert(String energySource)
//    {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText(null);
//        alert.setContentText(energySource + " energy is already an option.");
//        alert.showAndWait();
//    }
// --Commented out by Inspection STOP (27/9/2017 3:29 μμ)

// --Commented out by Inspection START (27/9/2017 3:29 μμ):
//    static void notEnergyAlert(String energySource)
//    {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Error");
//        alert.setHeaderText(null);
//        alert.setContentText(energySource + " energy is not an option.");
//        alert.showAndWait();
//    }
// --Commented out by Inspection STOP (27/9/2017 3:29 μμ)

    static void completionMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The " + message + " was successfull.");
        alert.showAndWait();
    }

    static void queueInsertion()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The event was inserted in the waiting list.");
        alert.showAndWait();
    }

    static void noExecution()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The event cannot be executed.");
        alert.showAndWait();
    }

    static boolean confirmCreation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("PricingPolicy" + " creation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure about the creation?");
        Optional<ButtonType> option = alert.showAndWait();
        return !option.isPresent() || option.get() != ButtonType.OK;
    }

    static boolean checkEnergy(String energy)
    {
        for(String energ: currentStation.getSources())
            if(energ.equals(energy))
                return true;
        return false;
    }

    static void refillBlanks() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in the blanks again.");
        alert.showAndWait();
    }
}
