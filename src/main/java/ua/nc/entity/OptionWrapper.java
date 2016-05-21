package ua.nc.entity;

/**
 * Created by Neltarion on 16.05.2016.
 */

/**
 * Wrapper class for option properties required for new question in student application form
 */
public class OptionWrapper {
    private Integer opt_id;
    private String value;

    public OptionWrapper(Integer opt_id, String value) {
        this.opt_id = opt_id;
        this.value = value;
    }

    public OptionWrapper() {
    }

    @Override
    public String toString() {
        return "OptionWrapper{" +
                "opt_id=" + opt_id +
                ", value='" + value + '\'' +
                '}';
    }

    public Integer getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(Integer opt_id) {
        this.opt_id = opt_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
