package ua.nc.entity;

/**
 * Created by Rangar on 02.05.2016.
 */
public class ReportTemplate implements Identified<Integer> {
    private int reportTemplateID;
    private String query;
    private String name;

    @Override
    public Integer getId() {
        return reportTemplateID;
    }

    protected void setID(int id){
        this.reportTemplateID = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportTemplate(String query, String name){
        this.query = query;
        this.name = name;
    }
}
