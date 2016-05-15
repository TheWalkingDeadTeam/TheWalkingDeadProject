package ua.nc.xls;

import ua.nc.entity.ReportTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 11.05.2016.
 */
public interface Export {
    void export(List<Map<String, Object>> reportRows, ReportTemplate report);
}
