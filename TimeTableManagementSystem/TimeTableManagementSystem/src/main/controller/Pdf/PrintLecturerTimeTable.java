package main.controller.Pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PrintLecturerTimeTable {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);

    //
    /*-------------------Generate Current Date -----------------*/
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String newDate = dateFormat.format(date);

        return newDate;
    }

    //
    /*-------------------Generate Current Time -----------------*/
    public static String getCurrentTime() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");

        return (sdf.format(cal.getTime()));

    }


    public void generateCustomerReportPdf(String[][] arr,String [][]timeString, int workingDaysCount, int hourSize,String lecName) {

        String fileName = getCurrentDate() + "_" + getCurrentTime() +"-"+lecName+ ".pdf";
        String FILE = "C:/Users/" + System.getProperty("user.name") + "/Documents/" + fileName;

        try {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document,lecName);
            addTitlePage(document,lecName);
            createTable(document, arr,timeString,workingDaysCount,hourSize);
            File myFile = new File(FILE);
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }


            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void addMetaData(Document document,String GroupId) {

        document.addTitle(GroupId);

    }


    private static void addTitlePage(Document document,String groupId)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph(groupId, catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        document.add(preface);
    }

    private static void createTable(Document subCatPart, String[][] arr,String [][] timeString, int workingDaysCount, int hourSize) throws BadElementException {
        PdfPTable table1 = new PdfPTable(6);

        PdfPCell c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);

        table1.addCell(c1);


        c1 = new PdfPCell(new Phrase("Monday"));;
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("TuesDay"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("WednesDay"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(c1);


        c1 = new PdfPCell(new Phrase("ThursDay"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Friday"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(c1);

        table1.setHeaderRows(1);
        int count=0;
        for (int i = 0; i <(int)hourSize; i++) {
            table1.addCell(timeString[i][0]);
            for (int j = 0; j < workingDaysCount; j++) {
                if(arr[i][j]!=null){
                    PdfPCell c2 = new PdfPCell(new Phrase(arr[i][j],new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table1.addCell(c2);
                }else{
                    PdfPCell c2 = new PdfPCell(new Phrase("-",new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table1.addCell(c2);
                }

            }
        }

        try {
            subCatPart.add(table1);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    //
//
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
