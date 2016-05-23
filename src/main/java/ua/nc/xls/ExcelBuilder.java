package ua.nc.xls;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import ua.nc.entity.ReportTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 15.05.2016.
 */
public class ExcelBuilder extends AbstractXlsView {


    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<Map<String, Object>> reportRows = (List<Map<String, Object>>) map.get("reportRows");
        ReportTemplate report = (ReportTemplate) map.get("report");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + " report_" + report.getName() + ".xls\"");
        Map<String, CellStyle> styles = createStyles(workbook);
        Sheet sheet = workbook.createSheet("Report");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        InputStream inputStream = new FileInputStream(httpServletRequest.getSession().getServletContext().getRealPath("/resources/images/logo.png"));
        //Get the contents of an InputStream as a byte[].
        byte[] bytes = IOUtils.toByteArray(inputStream);
        //Adds a picture to the workbook
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        //close the input stream
        inputStream.close();

        //Returns an object that handles instantiating concrete classes
        CreationHelper helper = workbook.getCreationHelper();

        //Creates the top-level drawing patriarch.
        Drawing drawing = sheet.createDrawingPatriarch();

        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner for the image
        anchor.setCol1(0);
        anchor.setRow1(0);


        //Creates a picture
        Picture pict = drawing.createPicture(anchor, pictureIdx);


        //header row
        Row headerRow = sheet.createRow(9);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        int column = 0;
        for (Map.Entry<String, Object> entry : reportRows.get(0).entrySet()) {
            headerCell = headerRow.createCell(column++);
            headerCell.setCellValue(entry.getKey());
            headerCell.setCellStyle(styles.get("header"));
        }

        Row titleRow = sheet.createRow(8);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(report.getName());
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, --column));


        int rownum = 10;
        for (Map<String, Object> rows : reportRows) {
            Row row = sheet.createRow(rownum++);
            int j = 0;
            for (Map.Entry<String, Object> entry : rows.entrySet()) {
                Cell cell = row.createCell(j++);
                cell.setCellValue(String.valueOf(entry.getValue()));
                cell.setCellStyle(styles.get("cell"));
            }
        }

        //Reset to the original size
        for (int i = 0; i < reportRows.get(0).size(); i++) {
            sheet.autoSizeColumn(i);
        }
        pict.resize();
    }


    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short) 11);
        monthFont.setColor(IndexedColors.BLACK.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        return styles;
    }
}