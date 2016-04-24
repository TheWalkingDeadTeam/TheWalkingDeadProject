package ua.nc.entity;

/**
 * Created by Alexander on 21.04.2016.
 * Mail Entity class
 */
public class Mail {

    private Integer id;
    private String bodyTemplate;
    private String headTemplate;

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadTemplate() {
        return headTemplate;
    }

    public void setHeadTemplate(String headTemplate) {
        this.headTemplate = headTemplate;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", bodyTemplate='" + bodyTemplate + '\'' +
                ", headTemplate='" + headTemplate + '\'' +
                '}';
    }
}
