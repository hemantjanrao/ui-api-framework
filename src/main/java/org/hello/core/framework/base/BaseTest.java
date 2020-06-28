package org.hello.core.framework.base;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseTest {


    protected WebDriver driver;
    protected Logger log = Logger.getLogger(getClass());

    @BeforeClass(alwaysRun=true)
    public void baseTestBeforeClass() {
        log.info("Starting the Before class of 'Base Test'");
    }

    @AfterClass(alwaysRun=true)
    public void baseTestAfterClass() {
        log.info("Starting the After class of 'Base Test'");
    }

    @BeforeMethod(alwaysRun = true)
    public void logStartMethod(Method testMethod) {
        log.info(" =============================================== Starting test method [" + testMethod.getName()+"] ===================================");
    }

    @AfterMethod(alwaysRun = true)
    public void logEndMethod(Method testMethod) {
        log.info("=============================================== Ending test method [" + testMethod.getName()+"] ===================================");
    }


    /**
     * Method to read data input from csv file
     * @param fileName
     * @return
     * @throws IOException
     */
    protected  String[][] parseExcelDataToDataProvider(String fileName, String sheetName) throws IOException
    {
        log.info("Reading data from excel '"+fileName+"' with sheet name '"+sheetName+"'");
        String[][] arrayExcelData = null;
        try
        {
            FileInputStream fs = new FileInputStream(fileName);
            Workbook wb = Workbook.getWorkbook(fs);
            Sheet sh = wb.getSheet(sheetName);
            int totalNoOfCols = sh.getColumns();
            int totalNoOfRows = sh.getRows();

            arrayExcelData = new String[totalNoOfRows][totalNoOfCols];
            for (int i= 0 ; i < totalNoOfRows; i++) {

                for (int j=0; j < totalNoOfCols; j++) {
                    arrayExcelData[i][j] = sh.getCell(j, i).getContents();
                }

            }
        }
        catch (FileNotFoundException e) {
            log.error("Error reading the exel fileException-"+e);
        } catch (IOException e) {
            log.error("Error reading the exel fileException-"+e);
        } catch (BiffException e) {
            log.error("Error reading the exel fileException-"+e);
        }
        return arrayExcelData;

    }

}
