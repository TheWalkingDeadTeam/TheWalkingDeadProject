package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.ReportTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 11.05.2016.
 */
public interface ReportService {
    /**
     * Get all the existing report templates.
     *
     * @return Report templates list.
     */
    List<ReportTemplate> getReports();

    /**
     * Retrive report template by id
     * @param id report template id
     * @return report id with id
     */
    ReportTemplate getReportById(Integer id);

    /** Create new report template
     * @param report the report template to create
     */
    void createReport(ReportTemplate report);

    /** Update existing report template.
     * @param report the report template to update
     */
    void updateReport(ReportTemplate report);

    /** Delete report template.
     * @param report the report template to delete
     */
    void deleteReport(ReportTemplate report);

    /** Get report rows from execute report template
     * @param report the report that execute
     * @return Report rows
     * @throws DAOException if cant execute report template query
     */
    List<Map<String,Object>> getReportRows(ReportTemplate report) throws DAOException;

    /** Get report for users profile in ces with id
     * @param id the id of ces where get user profile
     * @return report template for users profile in ces with id
     */
    ReportTemplate getUsersProfileQueryById(Integer id);
}
