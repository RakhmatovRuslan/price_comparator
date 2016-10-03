package service;

import entity.Drug;
import entity.Supplier;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by ruslan on 5/12/16.
 */
public class TemplateParser {

    private File file;
    private Supplier drugs;

    public TemplateParser(File file) {
        this.file = file;
        drugs = new Supplier();
    }

    public Supplier getSuppliyersDrugsByTemplate(){
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            drugs.setSupplierName(sheet.getRow(0).getCell(2).getStringCellValue());
            int rowNumber = 2;
            boolean isCellEmpty = false;
            do{
                Row row = sheet.getRow(rowNumber);
                if(row != null)
                {
                    Drug drug = new Drug();
                    Cell cell ;

                    cell = row.getCell(0);
                    drug.setName(cell.getStringCellValue());

                    cell = row.getCell(1);
                    drug.setPrice(cell.getNumericCellValue());

                    cell = row.getCell(2);
                    drug.setExpireDate(cell.getDateCellValue());

                    cell = row.getCell(3);
                    drug.setProducer(cell.getStringCellValue());

                    drug.setSuppliyerName(sheet.getRow(0).getCell(2).getStringCellValue());

                    System.out.println(drug.toString());
                    drugs.add(drug);

                    rowNumber++;
                } else{
                    isCellEmpty = true;
                }
            }while(!isCellEmpty);
            System.out.println("The parsing is over");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return drugs;
    }

}
