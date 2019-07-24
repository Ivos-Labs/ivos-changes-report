package com.ivoslabs.changesreport.excel;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontCharset;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ivoslabs.changesreport.dtos.Change;

/**
 * 
 * @author www.ivoslabs.com
 *
 */
public class Excel {

    /** */
    private static final int FIRS_ROW = 12;

    /** */
    private static final int LAST_ROW = 54;

    /**
     * 
     * @param changes
     * @param path
     * @param title
     */
    public void write(List<Change> changes, String path, String title) {
	try {
	    XSSFWorkbook wb = getWorkbook();
	    XSSFSheet sheet = getSheetByWorkbook(wb, 2);
	    sheet.getRow(3).getCell(2).setCellValue(title);
	    int last = changes.size() >= LAST_ROW ? LAST_ROW : FIRS_ROW + changes.size() - 1;
	    for (int i = FIRS_ROW; i <= last; i++) {
		Row row = sheet.getRow(i);
		Cell cell1 = row.getCell(1);
		Cell cell2 = row.getCell(2);
		Cell cell3 = row.getCell(3);
		Cell cell4 = row.getCell(4);
		Change crow = changes.get(i - FIRS_ROW);
		cell1.setCellValue(createRichTextStringFile(crow.getFile().getParentPath() + "\n" + crow.getFile().getName(), wb));
		cell2.setCellValue("");
		cell3.setCellValue(crow.getFile().getProject());
		cell4.setCellValue(createRichTextString(crow.getDescription() + "\n" + crow.getContent().toString(), wb));
	    }
	    writeXLSX(path, wb);
	    if (changes.size() > 64) {
		System.err.println("more than 53 changes");
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    /**
     * 
     * @param file
     * @param wb
     * @return
     */
    @SuppressWarnings("deprecation")
    private RichTextString createRichTextStringFile(String file, XSSFWorkbook wb) {
	Font boldFont = wb.createFont();
	boldFont.setCharSet(FontCharset.EASTEUROPE.getValue());
	boldFont.setBoldweight((short) 700);
	RichTextString richString = new XSSFRichTextString(file);
	richString.applyFont(file.indexOf("\n"), file.length(), boldFont);
	return richString;
    }

    /**
     * 
     * @param description
     * @param wb
     * @return
     */
    @SuppressWarnings("deprecation")
    private RichTextString createRichTextString(String description, XSSFWorkbook wb) {
	short point = 9;
	description = description.replaceAll("\t", "  ");
	Font boldFont = wb.createFont();
	boldFont.setCharSet(FontCharset.EASTEUROPE.getValue());
	boldFont.setBoldweight((short) 700);
	boldFont.setFontHeightInPoints(point);
	boldFont.setFontName("Courier New");
	Font curierNew = wb.createFont();
	curierNew.setFontHeightInPoints(point);
	curierNew.setFontName("Courier New");
	RichTextString richString = new XSSFRichTextString(description);
	if (description != null && description.length() > 0 && description.contains("Line")) {
	    richString.applyFont(description.indexOf("Line"), description.length(), curierNew);
	}
	List<Index> indexes = getIndexOfLinea(description);
	for (Index index : indexes) {
	    try {
		richString.applyFont(index.getIndexa(), index.getIndexb(), boldFont);
	    } catch (Exception e) {
		System.out.println("index a" + index.getIndexa());
		System.out.println("index b" + index.getIndexb());
		e.printStackTrace(System.err);
	    }
	}
	return richString;
    }

    private List<Index> getIndexOfLinea(String description) {
	List<Index> index = new ArrayList<>();
	while (description.lastIndexOf("Line") != -1) {
	    int indexOfLinea = description.lastIndexOf("Line");
	    Index ind = new Index();
	    ind.setIndexa(indexOfLinea);
	    ind.setIndexb(indexOfLinea + 13);
	    description = description.substring(0, indexOfLinea);
	    index.add(ind);
	}
	return index;
    }

    private XSSFWorkbook getWorkbook() throws Exception {

//	InputStream ExcelFileToRead = Excel.class.getResourceAsStream("/changes/control/creator/entity/-000.xlsx");

	XSSFWorkbook wb = new XSSFWorkbook("TASK-000-template.xlsx");
	return wb;
    }

    private XSSFSheet getSheetByWorkbook(XSSFWorkbook wb, int sheetnum) throws Exception {
	XSSFSheet sheet = wb.getSheetAt(sheetnum - 1);
	return sheet;
    }

    private void writeXLSX(String pathExcelFile, XSSFWorkbook wb) throws Exception {
	FileOutputStream fileOut = new FileOutputStream(pathExcelFile);
	Throwable localThrowable2 = null;
	try {
	    wb.write(fileOut);
	    fileOut.flush();
	} catch (Throwable localThrowable1) {
	    localThrowable2 = localThrowable1;
	    throw localThrowable1;
	} finally {
	    if (fileOut != null) {
		if (localThrowable2 != null) {
		    try {
			fileOut.close();
		    } catch (Throwable x2) {
			localThrowable2.addSuppressed(x2);
		    }
		} else {
		    fileOut.close();
		}
	    }
	}
    }

    public static class Index {
	private int indexa;
	private int indexb;

	public int getIndexa() {
	    return this.indexa;
	}

	public void setIndexa(int indexa) {
	    this.indexa = indexa;
	}

	public int getIndexb() {
	    return this.indexb;
	}

	public void setIndexb(int indexb) {
	    this.indexb = indexb;
	}
    }
}