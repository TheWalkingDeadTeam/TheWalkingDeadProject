package ua.nc.dao.builder;

/**
 * Created by Rangar on 11.05.2016.
 */
public class ApplicationTableBuilder {
    private Integer cesId;
    private Integer limit = null;
    private Integer offset = null;
    private Object orderBy = null;
    private String pattern = null;

    public ApplicationTableBuilder(Integer cesId){
        this.cesId = cesId;
    }

    public ApplicationTableBuilder limitOffset(Integer limit, Integer offset){
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    public ApplicationTableBuilder orderBy(Integer orderBy){
        this.orderBy = orderBy;
        return this;
    }

    public ApplicationTableBuilder orderBy(String orderBy){
        this.orderBy = orderBy;
        return this;
    }

    public ApplicationTableBuilder pattern(String pattern){
        this.pattern = pattern;
        return this;
    }
}
