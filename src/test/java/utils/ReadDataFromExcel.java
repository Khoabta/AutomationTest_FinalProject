package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadDataFromExcel {
    public static XSSFWorkbook excelWBook;
    public static XSSFSheet excelWSheet;
    public static XSSFCell cell;
    public static XSSFRow row;

    public static void setExcelFile(String path,String sheetName) throws Exception {
        try {
            File file = new File(path);
            FileInputStream excelFile = new FileInputStream(file);
            // Access the test data sheet
            excelWBook = new XSSFWorkbook(excelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
        } catch (Exception e){
            throw (e);
        }
    }

    public static String getCellData(int rowNum, int colNum) throws Exception{
        try
        {
            cell = excelWSheet.getRow(rowNum).getCell(colNum);
            DataFormatter formatter = new DataFormatter();
            String cellData = formatter.formatCellValue(cell);
            return cellData;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return"";
        }
    }

    //Get index of row when input a test case
    public static int getRowByTestCaseID (String testCase) throws Exception {
        int rowTotal = excelWSheet.getLastRowNum() - excelWSheet.getFirstRowNum();
        for(int i = 1; i < rowTotal + 1; i++){
            cell = excelWSheet.getRow(i).getCell(0);
            DataFormatter formatter = new DataFormatter();
            String rowText = formatter.formatCellValue(cell);
            if(rowText.equals(testCase)){
                return i;
            }
        }
        return -1;
    }

    public static List<String> getTestCaseIDByTypeOfTestCase(String typeOfTestCase){
        int rowTotal = excelWSheet.getLastRowNum() - excelWSheet.getFirstRowNum();
        int headerRow = excelWSheet.getFirstRowNum();
        List<String> listOfTestCase = new ArrayList<>();
        for (int i = 1; i <= rowTotal; i++){
            cell = excelWSheet.getRow(i).getCell(2);
            DataFormatter formatter = new DataFormatter();
            String rowTypeOfTC = formatter.formatCellValue(cell);
            if(rowTypeOfTC.equals(typeOfTestCase)){
                cell = excelWSheet.getRow(i).getCell(0);
                DataFormatter formatter1 = new DataFormatter();
                String rowText = formatter1.formatCellValue(cell);
                listOfTestCase.add(rowText);
            }
        }
        return listOfTestCase;
    }

    //Get index of Column Name
    public static int getCol (String columnName) throws Exception{
        row = excelWSheet.getRow(0);
        for(int i = 0; i < row.getLastCellNum(); i++){
            String name = String.valueOf(row.getCell(i));
            if(name.equals(columnName)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Sử dụng Hashmap để lưu trữ tất cả các dữ liệu của 1 dòng khi truyền vào 1 giá trị cụ thể
     * @param testCaseID: là giá trị truyền vào để Hashmap lưu trữ các dữ liệu của dòng có chứa giá trị này trong excel
     * @return
     * @throws Exception
     */
    public static HashMap getDataFromExcelUsingHashmap(String testCaseID) throws Exception{
        int headerRow = excelWSheet.getFirstRowNum();
        int testCaseRow = getRowByTestCaseID(testCaseID);
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < excelWSheet.getRow(headerRow).getLastCellNum(); i++){
            map.put(getCellData(headerRow, i), getCellData(testCaseRow, i));
        }
        return map;
    }
}
