package vishal.alumini_final.model;

public class LatestEvents {

    private final String date;
    private final String heading;
    private final String category;

    public LatestEvents(String date, String heading, String category) {
        this.date = date;
        this.heading = heading;
        this.category = category;
    }

    public String getHeading() {
        return heading;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }
}
