package base;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Read Test data from exel file and perform Data driven testing for Login Page
 */
public class DataManager {
    // Read data from excel (data driven testing)
    public Object[][] readExcel(String fileName, String sheetName) throws IOException, InvalidFormatException {
        // Open the Excel file
        FileInputStream stream = new FileInputStream(fileName);
        // Access the required test data sheet
        Workbook workbook = WorkbookFactory.create(stream);
        Sheet s = workbook.getSheet(sheetName);
        int rowCount = s.getLastRowNum();
        int cellCount = s.getRow(0).getLastCellNum();
        String data[][] = new String[rowCount - 1][cellCount];
        // Loop through all rows in the sheet
        // Start at row 1 as row 0 is our header row
        for (int i = 1; i < rowCount; i++) {
            Row r = s.getRow(i);
            for (int j = 0; j < cellCount; j++) {
                Cell c = r.getCell(j);
                data[i - 1][j] = c.getStringCellValue();
            }
        }
        return data;
    }
}
