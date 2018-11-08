package location;

public class City {
    private int id;
    private String name, state;

    public City(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public City(int id) {
        this.id = id;
        //TODO: search id in API
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
        return name + " - " + state;
    }

}
