package base;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Asus on 4/23/2017.
 */
public class DataManager {
    // Read data from excel (data driven testing)
    public Object[][] readExcel(String fileName, String sheetName) throws IOException, InvalidFormatException {
        FileInputStream stream = new FileInputStream(fileName);
        Workbook workbook = WorkbookFactory.create(stream);
        Sheet s = workbook.getSheet(sheetName);
        int rowcount = s.getLastRowNum();
        int cellcount = s.getRow(0).getLastCellNum();
        String data[][] = new String[rowcount - 1][cellcount];

        for (int i = 1; i < rowcount; i++) {
            Row r = s.getRow(i);
            for (int j = 0; j < cellcount; j++) {
                Cell c = r.getCell(j);
                try {
                    if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                        data[i - 1][j] = c.getStringCellValue();
                    } else {
                        data[i - 1][j] = String.valueOf(c.getNumericCellValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }
}
