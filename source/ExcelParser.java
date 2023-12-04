package packager;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelParser {
	
	@SuppressWarnings("rawtypes")
	//Data dynamic array list begins useful information at index 5
	private DynamicArrayList Data = new DynamicArrayList<String>();
	private int rowIndex = 0;
	private int columnIndex = 0;
	private String dataTime;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ExcelParser(String filePath) {
		try {
			File file = new File(filePath);
				FileInputStream fileInput = new FileInputStream(file);
				@SuppressWarnings("resource")
				XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
				XSSFSheet sheet = workbook.getSheetAt(3);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						Data.addToInnerArray(columnIndex, rowIndex, cell.getStringCellValue());
						rowIndex++;
						}
					columnIndex++;
					rowIndex = 0;
				}
		} catch(Exception e) {
			e.printStackTrace();
		}
		dataTime = (String) (((ArrayList) Data.get(2)).get(0));
	}
	
	@SuppressWarnings("rawtypes")
	public DynamicArrayList getArray() {
		return Data;
	}
	
	public int getNumStudents() {
		return Data.size() - 6;
	}
	
	public String getDataTime() {
		return this.dataTime.split("\\r?\\n")[1];
	}
	
	@SuppressWarnings("rawtypes")
	public int getNumEvents() {
		return ((ArrayList) Data.get(10)).size() - 2;
	}

}