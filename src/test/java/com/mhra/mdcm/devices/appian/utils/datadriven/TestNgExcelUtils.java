package com.mhra.mdcm.devices.appian.utils.datadriven;

import com.mhra.mdcm.devices.appian.domains.junit.User;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tayyibah on 06/05/2016.
 */
public class TestNgExcelUtils {

    private final String resourceFolder = "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    public Object[][] getListOfUsers(String fileName, String sheet){

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
                    String username = excelData[0];
                    String password = excelData[1];
                    listOfCountries.add(new User(username, password));
                }catch (Exception e){
                    //Empty lines will cause error
                }
            }
            lineCount++;
        }

        //Convert to 2D Array Object
        Object[][] o = convertListTo2DArray(listOfCountries);

        return o;
    }
//
//
//    public Object[][] getListOfJourneys(String fileName, String sheet) {
//
//        //Point to the resource file
//        String dataFile = getDataFileFullPath(fileName);
//
//        //Get all the data as string separated by \n
//        String linesOfData = getDataFromFile(dataFile, sheet);
//
//        //Create arraylist
//        List<Journey> listOfCountries = new ArrayList<Journey>();
//        String[] linesOfCSVData = linesOfData.split("\n");
//        int lineCount = 0;
//        for(String line: linesOfCSVData){
//
//            if(lineCount > 0) {
//
//                try {
//                    String[] excelData = line.split(",");
//                    String from = excelData[0];
//                    String to = excelData[1];
//
//                    listOfCountries.add(new Journey(from, to));
//                }catch(Exception e){
//                    System.out.println("Line : " + lineCount + " is not valid");
//                    //Expecting both field to be filled
//                }
//            }
//            lineCount++;
//        }
//
//        //Convert to 2D Array Object
//        Object[][] o = convertListTo2DArray(listOfCountries);
//
//        return o;
//    }


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

    private String getDataFileFullPath(String fileName) {
        File file = new File("");
        String rootFolder = file.getAbsolutePath();
        String data = (rootFolder + File.separator + resourceFolder + File.separator + fileName);
        return data;
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

}
