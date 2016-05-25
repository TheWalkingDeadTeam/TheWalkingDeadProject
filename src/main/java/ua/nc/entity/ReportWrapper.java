package ua.nc.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 23.05.2016.
 */
public class ReportWrapper {
    private ReportTemplate report;
    private List<Map<String, Object>> reportRows;

    public ReportWrapper() {
    }

    public ReportWrapper(ReportTemplate report, List<Map<String, Object>> reportRows) {
        this.report = report;
        this.reportRows = reportRows;
    }

    public ReportTemplate getReport() {
        return report;
    }

    public void setReport(ReportTemplate report) {
        this.report = report;
    }

    public List<Map<String, Object>> getReportRows() {
        return reportRows;
    }

    public void setReportRows(List<Map<String, Object>> reportRows) {
        this.reportRows = reportRows;
    }
}
