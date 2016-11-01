package com.mhra.mdcm.devices.appian.utils.datadriven;

import com.mhra.mdcm.devices.appian.domains.junit.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

import java.util.*;

/**
 * Created by TPD_Auto on 01/11/2016.
 */
public class ExcelDataSheet {

    private final String resourceFolder = "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    private transient Collection data = null;

//    public ExcelDataSheet(final InputStream excelInputStream) throws IOException {
//        this.data = loadFromSpreadsheet(excelInputStream);
//    }

    public ExcelDataSheet(){

    }

    public Collection getData() {
        return data;
    }

    /**
     * Read any file and return each line separated by \n
     * @param dataFile
     * @return
     */
    private String getDataFromFile(String dataFile, String sheetName) {
        StringBuilder sb = new StringBuilder();

        try {
            File myFile = new File(dataFile);
            FileInputStream fis = new FileInputStream(myFile);

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheet(sheetName);

            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();

            // Traversing over each row of XLSX file
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    String v = cell.toString();
                    sb.append(v + ",");
                }
                sb.append("\n");

            }

            System.out.println(sb.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    public List<User> getListOfUsers(String fileName, String sheet){

        //Point to the resource file
        String dataFile = getDataFileFullPath(fileName);

        //Get all the data as string separated by \n
        String linesOfData = getDataFromFile(dataFile, sheet);

        //Create arraylist
        List<User> listOfCountries = new ArrayList<User>();
        String[] linesOfCSVData = linesOfData.split("\n");
        int lineCount = 0;
        for(String line: linesOfCSVData){

            if(lineCount > 0) {
                try {
                    String[] excelData = line.split(",");
                    String userName = excelData[0];
                    String password = excelData[1];
                    //String age = excelData[2];
                    //String job = excelData[3];
                    listOfCountries.add(new User(userName, password));
                }catch (Exception e){}
            }
            lineCount++;
        }

        //Convert to 2D Array Object
        //Object[][] o = convertListTo2DArray(listOfCountries);

        return listOfCountries;
    }


    public Object[][] getListOf2DObjects(String fileName, String sheet){

        //Point to the resource file
        String dataFile = getDataFileFullPath(fileName);

        //Get all the data as string separated by \n
        String linesOfData = getDataFromFile(dataFile, sheet);

        //Create arraylist
        List<User> listOfCountries = new ArrayList<User>();
        String[] linesOfCSVData = linesOfData.split("\n");
        int lineCount = 0;
        for(String line: linesOfCSVData){

            if(lineCount > 0) {
                try {
                    String[] excelData = line.split(",");
                    String userName = excelData[0];
                    String password = excelData[1];
                    //String age = excelData[2];
                    //String job = excelData[3];
                    listOfCountries.add(new User(userName, password));
                }catch (Exception e){}
            }
            lineCount++;
        }

        //Convert to 2D Array Object
        Object[][] o = convertListTo2DArray(listOfCountries);

        return o;
    }


    private Object[][] convertListTo2DArray(List<?> listOfCountries) {
        Object[][] o = new Object[listOfCountries.size()][1];
        int pos = 0;
        for(Object c: listOfCountries){
            o[pos][0] = c;
            pos++;
        }

        return o;
    }


    private String getDataFileFullPath(String fileName) {
        File file = new File("");
        String rootFolder = file.getAbsolutePath();
        String data = (rootFolder + File.separator + resourceFolder + File.separator + fileName);
        return data;
    }

//    private Collection loadFromSpreadsheet(final InputStream excelFile)
//            throws IOException {
//        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);
//
//        data = new ArrayList();
//        Sheet sheet = workbook.getSheetAt(0);
//
//        int numberOfColumns = countNonEmptyColumns(sheet);
//        List rows = new ArrayList();
//        List rowData = new ArrayList();
//
//        for (Row row : sheet) {
//            if (isEmpty(row)) {
//                break;
//            } else {
//                rowData.clear();
//                for (int column = 0; column < numberOfColumns; column++) {
//                    Cell cell = row.getCell(column);
//                    rowData.add(objectFrom(workbook, cell));
//                }
//                rows.add(rowData.toArray());
//            }
//        }
//        return rows;
//    }
//
//    private boolean isEmpty(final Row row) {
//        Cell firstCell = row.getCell(0);
//        boolean rowIsEmpty = (firstCell == null)
//                || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
//        return rowIsEmpty;
//    }
//
//    /**
//     * Count the number of columns, using the number of non-empty cells in the
//     * first row.
//     */
//    private int countNonEmptyColumns(final Sheet sheet) {
//        Row firstRow = sheet.getRow(0);
//        return firstEmptyCellPosition(firstRow);
//    }
//
//    private int firstEmptyCellPosition(final Row cells) {
//        int columnCount = 0;
//        for (Cell cell : cells) {
//            if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
//                break;
//            }
//            columnCount++;
//        }
//        return columnCount;
//    }
//
//    private Object objectFrom(final HSSFWorkbook workbook, final Cell cell) {
//        Object cellValue = null;
//
//        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//            cellValue = cell.getRichStringCellValue().getString();
//        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            cellValue = getNumericCellValue(cell);
//        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            cellValue = cell.getBooleanCellValue();
//        } else if (cell.getCellType()  ==Cell.CELL_TYPE_FORMULA) {
//            cellValue = evaluateCellFormula(workbook, cell);
//        }
//
//        return cellValue;
//
//    }
//
//    private Object getNumericCellValue(final Cell cell) {
//        Object cellValue;
//        if (DateUtil.isCellDateFormatted(cell)) {
//            cellValue = new Date(cell.getDateCellValue().getTime());
//        } else {
//            cellValue = cell.getNumericCellValue();
//        }
//        return cellValue;
//    }
//
//    private Object evaluateCellFormula(final HSSFWorkbook workbook, final Cell cell) {
//        FormulaEvaluator evaluator = workbook.getCreationHelper()
//                .createFormulaEvaluator();
//        CellValue cellValue = evaluator.evaluate(cell);
//        Object result = null;
//
//        if (cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            result = cellValue.getBooleanValue();
//        } else if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            result = cellValue.getNumberValue();
//        } else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
//            result = cellValue.getStringValue();
//        }
//
//        return result;
//    }
}
