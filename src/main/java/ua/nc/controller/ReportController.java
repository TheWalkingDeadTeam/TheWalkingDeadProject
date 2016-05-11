package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.nc.entity.ReportTemplate;
import ua.nc.service.ReportService;
import ua.nc.service.ReportServiceImpl;
import ua.nc.xls.Export;
import ua.nc.xls.ExportXLS;

import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 10.05.2016.
 */
@Controller
public class ReportController {
    private static final Logger LOGGER = Logger.getLogger(ReportController.class);
    private ReportService reportService = new ReportServiceImpl();

    @RequestMapping(value = "/reports", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ReportTemplate>> getReports() {
        List<ReportTemplate> reports = reportService.getReports();
        if (!reports.isEmpty()) {
            LOGGER.info("Return " + reports.size() + " reports");
            return new ResponseEntity<List<ReportTemplate>>(reports, HttpStatus.OK);
        } else {
            LOGGER.info("Reports not found");
            return new ResponseEntity<List<ReportTemplate>>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ReportTemplate> getReport(@PathVariable Integer id) {
        ReportTemplate report = reportService.getReportById(id);
        if (report != null) {
            LOGGER.info("Get report " + id);
            return new ResponseEntity<ReportTemplate>(report, HttpStatus.OK);
        } else {
            LOGGER.info("Report " + id + " not found");
            return new ResponseEntity<ReportTemplate>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reports/", method = RequestMethod.POST)
    public ResponseEntity<Void> createReport(@RequestBody ReportTemplate report) {
        reportService.createReport(report);
        LOGGER.debug("Creating report " + report.getName());
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reports/{id}", method = RequestMethod.POST)
    public ResponseEntity<ReportTemplate> updateReport(@PathVariable("id") Integer id, @RequestBody ReportTemplate report) {
        ReportTemplate reportCurrent = reportService.getReportById(id);
        if (reportCurrent == null) {
            LOGGER.info("Report " + id + " not found");
            return new ResponseEntity<ReportTemplate>(HttpStatus.NOT_FOUND);
        }
        reportCurrent.setName(report.getName());
        reportCurrent.setQuery(report.getQuery());
        reportService.updateReport(report);
        LOGGER.info("Report " + id + "successfully updated");
        return new ResponseEntity<ReportTemplate>(reportCurrent, HttpStatus.OK);
    }


    @RequestMapping(value = "/reports/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ReportTemplate> deleteReport(@PathVariable("id") Integer id) {
        ReportTemplate report = reportService.getReportById(id);
        if (report == null) {
            LOGGER.info("Unable to delete report" + id + "not found");
            return new ResponseEntity<ReportTemplate>(HttpStatus.NOT_FOUND);
        }
        reportService.deleteReport(report);
        LOGGER.info("Report " + id + "successfully deleted");
        return new ResponseEntity<ReportTemplate>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/reports/download/{id}", method = RequestMethod.GET)
    public ModelAndView downloadReport(@PathVariable Integer id) {
        ReportTemplate report = reportService.getReportById(id);
        List<Map<String, Object>> reportRows = reportService.getReportRows(report);
        Export export = new ExportXLS();
        export.export(reportRows, report);
        return new ModelAndView("excelView", "report", report);
    }

    @RequestMapping(value = "/reports/view/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, Object>> showReport(@PathVariable Integer id) {
        ReportTemplate report = reportService.getReportById(id);
        List<Map<String, Object>> reportRows = reportService.getReportRows(report);

/*        for (Map.Entry<String, Object> entry : reportRows.get(1).entrySet()) {
            System.out.print(entry.getKey() + " ");
        }
        System.out.println();

        for (Map<String, Object> rows : reportRows) {
            for (Map.Entry<String, Object> entry : rows.entrySet()) {
                System.out.print(String.valueOf(entry.getValue()) + " ");
            }
            System.out.println();
        }*/

        return reportRows;
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report() {
        return "report";
    }
}
