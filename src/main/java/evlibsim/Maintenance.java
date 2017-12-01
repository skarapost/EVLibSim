package evlibsim;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.Optional;

import static evlibsim.EVLibSim.*;

class Maintenance {

    static boolean fieldCompletionCheck() {
        for (TextField f : textfields) {
            if (f.getText().isEmpty() && !f.isDisabled()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the required fields.");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    static void trimTextfields() {
        textfields.forEach(a -> a.setText(a.getText().trim()));
    }

    static boolean positiveOrZero()
    {
        for (TextField f: textfields) {
            if (!f.isDisabled()) {
                if (Integer.parseInt(f.getText()) < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill with positive numbers or zero.");
                    alert.showAndWait();
                    return true;
                }
            }
        }
        return false;
    }

    static boolean positiveOrZero(int... numbers) {
        for (int f: numbers) {
            if (Double.parseDouble(textfields.get(f).getText()) < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill with positive numbers or zero.");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    static boolean stationCheck() {
        if (stations.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please add a charging station.");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    static void cleanScreen() {
        EVLibSim.refreshButton.setDisable(true);
        energies.clear();
        grid.getChildren().clear();
        root.setCenter(null);
        root.setBottom(null);
        textfields.clear();
    }

    static void completionMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The " + message + " was successfull.");
        alert.showAndWait();
    }

    static void queueInsertion() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The event was inserted in the waiting list.");
        alert.showAndWait();
    }

    static void noExecution() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("The event cannot be executed.");
        alert.showAndWait();
    }

    static boolean confirmDeletion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure about the deletion?");
        Optional<ButtonType> option = alert.showAndWait();
        return option.isPresent() || option.get() == ButtonType.OK;
    }

    static boolean checkEnergy(String energy) {
        for (String energ : currentStation.getSources())
            if (energ.equals(energy))
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

    /*static void toolTipCustomization (Node node) {
        Label label;
        Button button;
        if (node instanceof Label) {
            label = (Label) node;
            label.getTooltip().setPrefWidth(200);
            label.getTooltip().setWrapText(true);
            label.setOnMouseEntered(event -> {
                Point2D p = label.localToScreen(label.getLayoutBounds().getMaxX(), label.getLayoutBounds().getMaxY());
                label.getTooltip().show(label, p.getX(), p.getY());
            });
            label.setOnMouseExited(event -> label.getTooltip().hide());
        }
        else if (node instanceof Button) {
            button = (Button) node;
            button.getTooltip().setPrefWidth(200);
            button.getTooltip().setWrapText(true);
            button.setOnMouseEntered(event -> {
                Point2D p = button.localToScreen(button.getLayoutBounds().getMaxX(), button.getLayoutBounds().getMaxY());
                button.getTooltip().show(button, p.getX(), p.getY());
            });
            button.setOnMouseExited(event -> button.getTooltip().hide());
        }
    }*/
}
