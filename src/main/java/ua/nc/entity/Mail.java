package ua.nc.entity;

/**
 * Created by Alexander on 24.04.2016.
 */
public class Mail implements Identified<Integer>{
    private Integer id;
    private String bodyTemplate;
    private String headTemplate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public String getHeadTemplate() {
        return headTemplate;
    }

    public void setHeadTemplate(String headTemplate) {
        this.headTemplate = headTemplate;
    }
}
