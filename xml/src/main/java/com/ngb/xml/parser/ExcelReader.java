package com.ngb.xml.parser;

import com.ngb.xml.bean.ExcelConsumer;
import com.ngb.xml.pathUtil.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.monitorjbl.xlsx.StreamingReader;


public class ExcelReader {

     List<ExcelConsumer> excelConsumer = new ArrayList<ExcelConsumer>();

    public List<ExcelConsumer> excelReader() throws Exception {
       // List<ExcelConsumer> excelConsumer = new ArrayList<ExcelConsumer>();
        // String xmlFilesDirectory = "C:\\Users\\Napster\\Downloads\\NGB\\NGB";
        //File directory = new File(xmlFilesDirectory);
        File excel = new File(PathUtil.baseExcelFolder + "consumerNO.xlsx");
        InputStream is = new FileInputStream(excel);
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(100)
                .bufferSize(4096)
                .open(is);
        System.out.println("Excel File opened successfully!!");

        // int exceptionCount = 0;
        // File[] allXmlFiles = directory.listFiles();
        File outputFile = new File("C:\\Users\\Napster\\Downloads\\NGB\\Output\\parserOutput.txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }

        Sheet sheet = workbook.getSheetAt(0);

        // SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        // Session session = null;

        BigInteger big = BigInteger.ZERO;

        //  session = sessionFactory.openSession();
        // long startTime = System.currentTimeMillis();

        SimpleDateFormat ccnbDateFormat = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat billMonthFormat = new SimpleDateFormat("MMM-yyyy");

        for (Row r : sheet) {
          //  ExcelConsumer[] excelConsumer = new ExcelConsumer();
            // session.clear();

            ExcelConsumer e = new ExcelConsumer();

            if (r.getRowNum() == 0)
                continue;

            System.out.println();

            for (Cell c : r) {
                String cellValue = (c.getStringCellValue() == null) ? "" : c.getStringCellValue().trim();

                //For setting values of all columns of the current row into bean object
                switch (c.getColumnIndex()) {


                    case 0:
                        e.setConsumerNo(cellValue);
                        break;
                    case 1 :
                        e.setSerialNo(cellValue);
                        break;
                    case 2:
                        e.setMeterMakeCode(cellValue);
                        break;

                }
            }

            excelConsumer.add(e);
        }

      for (ExcelConsumer e :excelConsumer)
      {
          System.out.println(e);
      }

      return excelConsumer;
    }

}
