package ua.nc.entity;

import java.util.List;

/**
 * Created by Alexander on 24.04.2016.
 */
public class Mail implements Identified<Integer> {
    private Integer id;
    private String bodyTemplate;
    private String headTemplate;
    private List<Integer> usersId;

    public Integer getMailIdUser() {
        return mailIdUser;
    }

    public void setMailIdUser(Integer mailIdUser) {
        this.mailIdUser = mailIdUser;
    }

    private Integer mailIdUser;

    public List<Integer> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<Integer> usersId) {
        this.usersId = usersId;
    }

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
