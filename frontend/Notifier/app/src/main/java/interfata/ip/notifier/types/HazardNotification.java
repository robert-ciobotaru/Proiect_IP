package interfata.ip.notifier.types;

import java.util.List;

/**
 * Created by cschifirnet on 14-May-17.
 */

public class HazardNotification {

    Hazard hazard;
    List<Earthquake> earthquakeList;

    public HazardNotification(Hazard hazard, List<Earthquake> earthquakeList) {
        this.hazard = hazard;
        this.earthquakeList = earthquakeList;
    }

    public Hazard getHazard() {
        return hazard;
    }

    public void setHazard(Hazard hazard) {
        this.hazard = hazard;
    }

    public List<Earthquake> getEarthquakeList() {
        return earthquakeList;
    }

    public void setEarthquakeList(List<Earthquake> earthquakeList) {
        this.earthquakeList = earthquakeList;
    }
}
