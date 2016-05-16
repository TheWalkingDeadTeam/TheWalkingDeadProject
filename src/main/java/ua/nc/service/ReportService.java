package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.ReportTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 11.05.2016.
 */
public interface ReportService {
    List<ReportTemplate> getReports();

    ReportTemplate getReportById(Integer id);

    void createReport(ReportTemplate report);

    void updateReport(ReportTemplate report);

    void deleteReport(ReportTemplate report);

    List<Map<String,Object>> getReportRows(ReportTemplate report) throws DAOException;
}
