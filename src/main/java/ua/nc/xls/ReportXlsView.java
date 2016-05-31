package ua.nc.xls;


import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.nc.entity.ReportTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 15.05.2016.
 */
public class ReportXlsView extends AbstractXlsView {
    private static final Logger LOGGER = Logger.getLogger(ReportXlsView.class);


    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> reportRows = (List<Map<String, Object>>) map.get("reportRows");
        ReportTemplate report = (ReportTemplate) map.get("report");
        // set file Report Name
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + " report_" + report.getName() + ".xls\"");
        Map<String, CellStyle> styles = createStyles(workbook);
        Sheet sheet = createSheet(workbook);
        //set Logo picture on sheet with row, cell (0, 0)
        Picture pict = setImageToSheet(request, sheet, "/resources/images/logo.png", 0, 0);
        //set header and title rows on row, cell (9, 0)
        setTableHeaderTitleToSheet(reportRows, report, sheet, 9, 0, styles.get("header"), styles.get("title"));
        //set body rows at row (10)
        setTableBodyToSheet(reportRows, sheet, 10, styles.get("cell"));
        //Reset to the original size
        resize(sheet, reportRows.get(0).size(), pict);
        LOGGER.info("Report " + report.getName() + " xls successfully created");
    }

    private void resize(Sheet sheet, Integer columAmount, Picture picture) {
        for (int i = 0; i < columAmount; i++) {
            sheet.autoSizeColumn(i);
        }
        picture.resize();
    }

    private void setTableHeaderTitleToSheet(List<Map<String, Object>> reportRows, ReportTemplate report,
                                            Sheet sheet, Integer rowHeader, Integer firstColumn,
                                            CellStyle cellStyleHeader, CellStyle cellStyleTitle) {
        Row headerRow = sheet.createRow(rowHeader);
        headerRow.setHeightInPoints(40);
        for (Map.Entry<String, Object> entry : reportRows.get(0).entrySet()) {
            Cell cell = headerRow.createCell(firstColumn++);
            cell.setCellValue(entry.getKey());
            cell.setCellStyle(cellStyleHeader);
        }
        Row titleRow = sheet.createRow(--rowHeader);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(report.getName());
        titleCell.setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(rowHeader, rowHeader, 0, --firstColumn));
    }

    private void setTableBodyToSheet(List<Map<String, Object>> reportRows, Sheet sheet,
                                     Integer firstRow, CellStyle cellStyle) {
        for (Map<String, Object> rows : reportRows) {
            Row row = sheet.createRow(firstRow++);
            int col = 0;
            for (Map.Entry<String, Object> entry : rows.entrySet()) {
                if (entry.getValue() != null) {
                    Cell cell = row.createCell(col++);
                    cell.setCellValue(String.valueOf(entry.getValue()));
                    cell.setCellStyle(cellStyle);
                }
            }
        }
    }

    private Map<String, CellStyle> createStyles(Workbook workbook) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        addBorderBlackThin(style);
        style.setFont(headerFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        addBorderBlackThin(style);
        styles.put("cell", style);

        return styles;
    }

    private void addBorderBlackThin(CellStyle style) {
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    }

    private Picture setImageToSheet(HttpServletRequest request, Sheet sheet, String path,
                                    Integer topLeftCol, Integer topLeftRow) throws IOException {
        // Get image
        InputStream inputStream = new FileInputStream(request.getSession().
                getServletContext().getRealPath(path));
        //Get the contents of an InputStream as a byte[].
        byte[] bytes = IOUtils.toByteArray(inputStream);
        inputStream.close();

        //Returns an object that handles instantiating concrete classes
        CreationHelper helper = sheet.getWorkbook().getCreationHelper();

        //Creates the top-level drawing patriarch.
        Drawing drawing = sheet.createDrawingPatriarch();

        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner for the image
        anchor.setCol1(topLeftCol);
        anchor.setRow1(topLeftRow);

        //Creates a picture on sheet
        return drawing.createPicture(anchor, sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_PNG));
    }

    private Sheet createSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Report");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        return sheet;
    }
}