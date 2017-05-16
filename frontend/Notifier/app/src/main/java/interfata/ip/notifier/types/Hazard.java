package interfata.ip.notifier.types;

import java.util.List;

/**
 * Created by cschifirnet on 14-May-17.
 */

public class Hazard {

    List<Flood> floodList;
    List<Cyclone> cycloneList;

    public Hazard(List<Flood> floodList, List<Cyclone> cycloneList) {
        this.floodList = floodList;
        this.cycloneList = cycloneList;
    }

    public List<Flood> getFloodList() {
        return floodList;
    }

    public void setFloodList(List<Flood> floodList) {
        this.floodList = floodList;
    }

    public List<Cyclone> getCycloneList() {
        return cycloneList;
    }

    public void setCycloneList(List<Cyclone> cycloneList) {
        this.cycloneList = cycloneList;
    }
}
