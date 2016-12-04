package vishal.alumini_final.model;

public class UpcomingEvents {

    private String date;
    private String heading;
    private String category;


    public UpcomingEvents(){

    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public UpcomingEvents(String date, String heading, String category) {
//        this.date = date;
//        this.heading = heading;
//        this.category = category;
//    }

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
