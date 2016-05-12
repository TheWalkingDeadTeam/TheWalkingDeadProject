package ua.nc.xls;


import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import ua.nc.entity.ReportTemplate;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 11.05.2016.
 */
public class ExportXLS implements Export {
    @Override
    public void export(List<Map<String, Object>> reportRows, ReportTemplate report) {
        Workbook wb = new HSSFWorkbook();
        Map<String, CellStyle> styles = createStyles(wb);
        Sheet sheet = wb.createSheet("Report");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        try {
        InputStream inputStream = new FileInputStream("D:\\TheWalkingDeadProject\\web\\resources\\images\\logo.png");
        //Get the contents of an InputStream as a byte[].
        byte[] bytes = IOUtils.toByteArray(inputStream);
        //Adds a picture to the workbook
        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        //close the input stream
        inputStream.close();

        //Returns an object that handles instantiating concrete classes
        CreationHelper helper = wb.getCreationHelper();

        //Creates the top-level drawing patriarch.
        Drawing drawing = sheet.createDrawingPatriarch();

        //Create an anchor that is attached to the worksheet
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner for the image
        anchor.setCol1(0);
        anchor.setRow1(0);

        //Creates a picture
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        //Reset the image to the original size
        pict.resize();

        Row titleRow = sheet.createRow(8);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(report.getName());
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$9:$L$9"));

        //header row

        Row headerRow = sheet.createRow(9);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        int column = 0;
        for (Map.Entry<String, Object> entry : reportRows.get(1).entrySet()) {
            headerCell = headerRow.createCell(column++);
            headerCell.setCellValue(entry.getKey());
            headerCell.setCellStyle(styles.get("header"));
        }



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
            for (int i = 0; i < reportRows.get(0).size(); i++) {
                sheet.autoSizeColumn(i);
            }

        // Write the output to a file
        String file = "report_" + report.getName() + ".xls";
        FileOutputStream out = null;
            out = new FileOutputStream("D:\\TheWalkingDeadProject\\" +file);
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {

            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("My Sample Excel");

            //FileInputStream obtains input bytes from the image file
            InputStream inputStream = new FileInputStream("E:\\TheWalkingDeadProject\\web\\resources\\images\\logo.png");
            //Get the contents of an InputStream as a byte[].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            //Adds a picture to the workbook
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            //close the input stream
            inputStream.close();

            //Returns an object that handles instantiating concrete classes
            CreationHelper helper = wb.getCreationHelper();

            //Creates the top-level drawing patriarch.
            Drawing drawing = sheet.createDrawingPatriarch();

            //Create an anchor that is attached to the worksheet
            ClientAnchor anchor = helper.createClientAnchor();
            //set top-left corner for the image
            anchor.setCol1(0);
            anchor.setRow1(0);

            //Creates a picture
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            //Reset the image to the original size
            pict.resize();

            //Write the Excel file
            FileOutputStream fileOut = null;
            fileOut = new FileOutputStream("E:\\myFile.xls");
            wb.write(fileOut);
            fileOut.close();

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private static Integer getImage(Workbook wb, String path) {
        Integer pictureIdx = null;
        try {
            InputStream inputStream = new FileInputStream(path);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pictureIdx;
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
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
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
