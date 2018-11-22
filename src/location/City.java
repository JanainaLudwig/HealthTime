package location;

import utils.LocationUtils;

public class City {
    private int id;
    private String name, state;

    public City(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
        this.state = "";
    }

//    public City(int id) {
//        this.id = id;
//        //TODO: search id in API
//    }

    public City(int id) {
        this.id = id;
        this.name = LocationUtils.getCity(String.valueOf(id)).getName();
        this.state = LocationUtils.getCity(String.valueOf(id)).getState();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        if (this.id == 0) {
            return name;
        } else {
            return name + " - " + state;
        }
    }

}
