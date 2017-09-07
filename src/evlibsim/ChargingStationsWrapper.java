package evlibsim;

import Station.ChargingStation;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChargingStations")
class ChargingStationsWrapper {

    private List<ChargingStation> stations;

    @XmlElement(name = "ChargingStation")
    List<ChargingStation> getChargingStations() {
        return stations;
    }

    void setChargingStations() {
        this.stations = EVLibSim.stations;
    }
}