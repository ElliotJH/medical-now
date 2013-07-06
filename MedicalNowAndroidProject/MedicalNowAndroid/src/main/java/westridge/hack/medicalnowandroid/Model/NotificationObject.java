package westridge.hack.medicalnowandroid.Model;

/**
 * Created by jordan on 7/6/13.
 */
public class NotificationObject {
    private int id;
    private String name;
    private String description;
    private String bmjURI;

    public NotificationObject(int id, String name, String description, String bmjURI) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bmjURI = bmjURI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBmjURI() {
        return bmjURI;
    }

    public void setBmjURI(String bmjURI) {
        this.bmjURI = bmjURI;
    }
}
