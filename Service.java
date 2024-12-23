public class Service {
    private String name;
    private double cost;
    private String duration;
    private String description;

    public Service(String name, double cost, String duration, String description) {
        this.name = name;
        this.cost = cost;
        this.duration = duration;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " - " + cost + "€ (" + duration + ")";
    }
}
