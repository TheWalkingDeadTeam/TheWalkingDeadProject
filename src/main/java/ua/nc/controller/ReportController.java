package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Mail;
import ua.nc.entity.ReportTemplate;
import ua.nc.service.ReportService;
import ua.nc.service.ReportServiceImpl;

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

    @RequestMapping(value = "/reports/download/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ModelAndView downloadReport(@PathVariable Integer id) {
        ReportTemplate report = reportService.getReportById(id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<Map<String, Object>> reportRows = reportService.getReportRows(report);
            modelAndView.setViewName("excelView");
            modelAndView.addObject("report", report);
            modelAndView.addObject("reportRows", reportRows);
        } catch (DAOException e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "Wrong query");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/reports/view/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public  ResponseEntity<List<Map<String, Object>>> showReport(@PathVariable Integer id) {
        ReportTemplate report = reportService.getReportById(id);
        List<Map<String, Object>> reportRows = null;
        try {
            reportRows = reportService.getReportRows(report);
            return new ResponseEntity<List<Map<String, Object>>>(reportRows, HttpStatus.OK);

        } catch (DAOException e) {
            return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/report/view", method = RequestMethod.GET)
    public String reportid() {
        return "report-id" ;
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report() {
        return "report-statistic";
    }
}
