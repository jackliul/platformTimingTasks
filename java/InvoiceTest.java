import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import net.intelink.zmframework.model.UserModelTest;
import net.intelink.zmframework.util.PDFUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InvoiceTest {
    public static void main(String[] args) throws Exception{

//        try
//        {
//            Document document = new Document(PageSize.A4.rotate());
//            PdfWriter.getInstance(document, new FileOutputStream("/Users/suzhongqiang/Work/test/9090009090.pdf"));
//
//            //设置字体
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//            com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
//            com.itextpdf.text.Font FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font.BOLD);
//            com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16, com.itextpdf.text.Font.BOLD);
//            com.itextpdf.text.Font FontChinese12 = new com.itextpdf.text.Font(bfChinese, 12, com.itextpdf.text.Font.NORMAL);
//            com.itextpdf.text.Font FontChinese11Bold = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.BOLD);
//            com.itextpdf.text.Font FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.ITALIC);
//            com.itextpdf.text.Font FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.NORMAL);
//
//            document.open();
//            //table1
//            PdfPTable table1 = new PdfPTable(3);
//            PdfPCell cell11 = new PdfPCell(new Paragraph("INVOICE",FontChinese24));
//            cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell11.setBorder(0);
//            //设置每列宽度比例
//            int width11[] = {35,40,25};
//            table1.setWidths(width11);
//            table1.getDefaultCell().setBorder(0);
//            table1.addCell(new Paragraph(""));
//            table1.addCell(cell11);
//            table1.addCell(new Paragraph(""));
//            document.add(table1);
//            //加入空行
//            Paragraph blankRow1 = new Paragraph(18f, " ", FontChinese18);
//            document.add(blankRow1);
//
//            //table2
//            PdfPTable table2 = new PdfPTable(2);
//            //设置每列宽度比例
//            int width21[] = {2,98};
//            table2.setWidths(width21);
//            table2.getDefaultCell().setBorder(0);
//            PdfPCell cell21 = new PdfPCell(new Paragraph("",FontChinese16));
//            cell21.setBorder(0);
//            table2.addCell(new Paragraph(""));
//            table2.addCell(cell21);
//            document.add(table2);
//            //加入空行
//            Paragraph blankRow2 = new Paragraph(18f, " ", FontChinese18);
//            document.add(blankRow2);
//
//            //table3
//            PdfPTable table3 = new PdfPTable(4);
//            int width3[] = {10,40,10,40};
//            table3.setWidths(width3);
//            PdfPCell cell31 = new PdfPCell(new Paragraph("NO: ",FontChinese11Normal));
//            PdfPCell cell32 = new PdfPCell(new Paragraph("  Date : ",FontChinese11Normal));
//            PdfPCell cell33 = new PdfPCell(new Paragraph("xxxxxxx",FontChinese11Normal));
//            PdfPCell cell34 = new PdfPCell(new Paragraph("2011-11-11",FontChinese11Normal));
//            cell31.setBorder(0);
//            cell32.setBorder(0);
//            cell33.setBorder(0);
//            cell34.setBorder(0);
////            cell31.setBorder();
////            cell33.setBorder(0);
//            table3.addCell(cell31);
//            table3.addCell(cell33);
//            table3.addCell(cell32);
//            table3.addCell(cell34);
//            document.add(table3);
//            //加入空行
//            Paragraph blankRow31 = new Paragraph(18f, " ", FontChinese11);
//            document.add(blankRow31);
//
//            //table4
//            PdfPTable table4 = new PdfPTable(2);
//            int width4[] = {40,60};
//            table4.setWidths(width4);
//            PdfPCell cell41 = new PdfPCell(new Paragraph(new Paragraph("INVOICE of: "+"123456789",FontChinese11Normal)));
//            PdfPCell cell42 = new PdfPCell(new Paragraph(""));
//            cell41.setBorder(0);
//            cell42.setBorder(0);
//            table4.addCell(cell41);
//            table4.addCell(cell42);
//            document.add(table4);
//            //加入空行
//            Paragraph blankRow41 = new Paragraph(18f, " ", FontChinese11);
//            document.add(blankRow41);
//
//            //table5
//            PdfPTable table5 = new PdfPTable(1);
//            PdfPCell cell51 = new PdfPCell(new Paragraph("For account and risk of Messrs.："+"XXX",FontChinese11));
//            cell51.setBorder(0);
//            table5.addCell(cell51);
//            document.add(table5);
//            //加入空行
//            Paragraph blankRow51 = new Paragraph(18f, " ", FontChinese18);
//            document.add(blankRow51);
//            //加入空行
//            document.add(new Paragraph(18f, " ", FontChinese11));
//            //加入空行
//            document.add(new Paragraph(18f, " ", FontChinese11));
//
//            //table5
//            PdfPTable table6 = new PdfPTable(2);
//            PdfPCell cell61 = new PdfPCell(new Paragraph("Shipped by: "+"XXX",FontChinese11));
//            PdfPCell cell62 = new PdfPCell(new Paragraph("Per: "+"xxxxxxx",FontChinese11Normal));
//            cell61.setBorder(0);
//            cell62.setBorder(0);
//            table6.addCell(cell61);
//            table6.addCell(cell62);
//            document.add(table6);
//
//            //table5
//            PdfPTable table10 = new PdfPTable(3);
//            int width10[] = {50,35,15};
//            table10.setWidths(width10);
//            PdfPCell cell101 = new PdfPCell(new Paragraph("Sailing on or about: "+"XXX",FontChinese11));
//            PdfPCell cell102 = new PdfPCell(new Paragraph("From: "+"xxxxxxx",FontChinese11Normal));
//            PdfPCell cell103 = new PdfPCell(new Paragraph("to: "+"xxxxxxx",FontChinese11Normal));
//            cell101.setBorder(0);
//            cell102.setBorder(0);
//            cell103.setBorder(0);
//            table10.addCell(cell101);
//            table10.addCell(cell102);
//            table10.addCell(cell103);
//            document.add(table10);
//
//            //table5
//            PdfPTable table11 = new PdfPTable(2);
//            PdfPCell cell111 = new PdfPCell(new Paragraph("L/C No.: "+"XXX",FontChinese11));
//            PdfPCell cell112 = new PdfPCell(new Paragraph("Contract No."+"xxxxxxx",FontChinese11Normal));
//            cell111.setBorder(0);
//            cell112.setBorder(0);
//            table11.addCell(cell111);
//            table11.addCell(cell112);
//            document.add(table11);
//
//
//            //加入空行
//            Paragraph blankRow4 = new Paragraph(18f, " ", FontChinese16);
//            document.add(blankRow4);
//
//
//            List<UserModelTest> tableList = new ArrayList<>();
//            UserModelTest userModelTest1 = new UserModelTest();
//            UserModelTest userModelTest2 = new UserModelTest();
//            UserModelTest userModlTest3 = new UserModelTest();
//            tableList.add(userModelTest1);
//            tableList.add(userModelTest2);
//            tableList.add(userModlTest3);
//            tableList.add(userModelTest1);
//            tableList.add(userModelTest2);
//            tableList.add(userModlTest3);
//
//
//            PdfPTable tableTemp = PDFUtil.createTableTemp(tableList);
////            int width71[] = {20,18,13,20,14,15,20,18,13,20,14,15};
////            tableTemp.setWidths(width71);
//            tableTemp.setTotalWidth(610);
//            tableTemp.setLockedWidth(true);
//            document.add(tableTemp);
//
//
//            document.add(new Paragraph(18f, " ", FontChinese16));
//            document.add(new Paragraph(18f, " ", FontChinese16));
//            document.add(new Paragraph(18f, " ", FontChinese16));
//
//
//            PdfPTable table12 = new PdfPTable(1);
//            PdfPCell cell121 = new PdfPCell(new Paragraph("Value For Customs Purpose Only.",FontChinese11));
//            cell121.setBorder(0);
//            table12.addCell(cell121);
//            document.add(table12);
//
//            //加入空行
//            document.add(new Paragraph(18f, " ", FontChinese16));
//            document.add(new Paragraph(18f, " ", FontChinese16));
//
//            PdfPTable table13 = new PdfPTable(1);
//            PdfPCell cell131 = new PdfPCell(new Paragraph("N.W.: 0",FontChinese11));
//            cell131.setBorder(0);
//            table13.addCell(cell131);
//            document.add(table13);
//
//            PdfPTable table14 = new PdfPTable(1);
//            PdfPCell cell141 = new PdfPCell(new Paragraph("G.W.: 0",FontChinese11));
//            cell141.setBorder(0);
//            table14.addCell(cell141);
//            document.add(table14);
//
//
//            PdfPTable table15 = new PdfPTable(2);
//            PdfPCell cell151 = new PdfPCell(new Paragraph("Measurement:",FontChinese11));
//            PdfPCell cell152 = new PdfPCell(new Paragraph("",FontChinese11));
//            cell151.setBorder(0);
//            cell152.setBorder(1);
//            table15.addCell(cell151);
//            table15.addCell(cell152);
//            document.add(table15);
//
//
//
//             document.close();
//
//        } catch (Exception ex)
//        {
//          ex.printStackTrace();
//        }


        InvoiceRecord invoiceRecord = new InvoiceRecord();

        List<UserModelTest> tableList = new ArrayList<>();
        UserModelTest userModelTest1 = new UserModelTest();
        UserModelTest userModelTest2 = new UserModelTest();
        UserModelTest userModlTest3 = new UserModelTest();
        tableList.add(userModelTest1);
        tableList.add(userModelTest2);
        tableList.add(userModlTest3);
        tableList.add(userModelTest1);
        tableList.add(userModelTest2);
        tableList.add(userModlTest3);

        invoiceRecord.setList(tableList);
        ByteArrayOutputStream pdfInvoice = new InvoiceTest().createPdfInvoice(invoiceRecord);



        pdfInvoice.writeTo(new FileOutputStream("/Users/suzhongqiang/Work/test/88888881.pdf"));
    }


    public ByteArrayOutputStream createPdfInvoice(InvoiceRecord invoiceRecord){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document,baos);

            //设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font FontChinese24 = new com.itextpdf.text.Font(bfChinese, 24, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese18 = new com.itextpdf.text.Font(bfChinese, 18, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese16 = new com.itextpdf.text.Font(bfChinese, 16, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font FontChinese11 = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.ITALIC);
            com.itextpdf.text.Font FontChinese11Normal = new com.itextpdf.text.Font(bfChinese, 11, com.itextpdf.text.Font.NORMAL);

            document.open();


            //table1
            PdfPTable table1 = new PdfPTable(3);
            PdfPCell cell11 = new PdfPCell(new Paragraph(invoiceRecord.getTitle(),FontChinese24));
            cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell11.setBorder(0);
            //设置每列宽度比例
            int width11[] = {35,40,25};
            table1.setWidths(width11);
            table1.getDefaultCell().setBorder(0);
            table1.addCell(new Paragraph(""));
            table1.addCell(cell11);
            table1.addCell(new Paragraph(""));
            document.add(table1);
            //加入空行
            Paragraph blankRow1 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow1);

            //table2
            PdfPTable table2 = new PdfPTable(2);
            //设置每列宽度比例
            int width21[] = {2,98};
            table2.setWidths(width21);
            table2.getDefaultCell().setBorder(0);
            PdfPCell cell21 = new PdfPCell(new Paragraph("",FontChinese16));
            cell21.setBorder(0);
            table2.addCell(new Paragraph(""));
            table2.addCell(cell21);
            document.add(table2);
            //加入空行
            Paragraph blankRow2 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow2);

            //table3
            PdfPTable table3 = new PdfPTable(4);
            int width3[] = {10,40,10,40};
            table3.setWidths(width3);
            PdfPCell cell31 = new PdfPCell(new Paragraph("NO: ",FontChinese11Normal));
            PdfPCell cell32 = new PdfPCell(new Paragraph("  Date : ",FontChinese11Normal));
            PdfPCell cell33 = new PdfPCell(new Paragraph(invoiceRecord.getNo(),FontChinese11Normal));
            PdfPCell cell34 = new PdfPCell(new Paragraph(invoiceRecord.getDate(),FontChinese11Normal));
            cell31.setBorder(0);
            cell32.setBorder(0);
            cell33.setBorder(0);
            cell34.setBorder(0);
//            cell31.setBorder();
//            cell33.setBorder(0);
            table3.addCell(cell31);
            table3.addCell(cell33);
            table3.addCell(cell32);
            table3.addCell(cell34);
            document.add(table3);
            //加入空行
            Paragraph blankRow31 = new Paragraph(18f, " ", FontChinese11);
            document.add(blankRow31);

            //table4
            PdfPTable table4 = new PdfPTable(2);
            int width4[] = {40,60};
            table4.setWidths(width4);
            PdfPCell cell41 = new PdfPCell(new Paragraph(new Paragraph("INVOICE of: "+invoiceRecord.getInvoiceOf(),FontChinese11Normal)));
            PdfPCell cell42 = new PdfPCell(new Paragraph(""));
            cell41.setBorder(0);
            cell42.setBorder(0);
            table4.addCell(cell41);
            table4.addCell(cell42);
            document.add(table4);
            //加入空行
            Paragraph blankRow41 = new Paragraph(18f, " ", FontChinese11);
            document.add(blankRow41);

            //table5
            PdfPTable table5 = new PdfPTable(1);
            PdfPCell cell51 = new PdfPCell(new Paragraph("For account and risk of Messrs.："+invoiceRecord.getFaarom(),FontChinese11));
            cell51.setBorder(0);
            table5.addCell(cell51);
            document.add(table5);
            //加入空行
            Paragraph blankRow51 = new Paragraph(18f, " ", FontChinese18);
            document.add(blankRow51);
            //加入空行
            document.add(new Paragraph(18f, " ", FontChinese11));
            //加入空行
            document.add(new Paragraph(18f, " ", FontChinese11));

            //table5
            PdfPTable table6 = new PdfPTable(2);
            PdfPCell cell61 = new PdfPCell(new Paragraph("Shipped by: "+invoiceRecord.getShippedBy(),FontChinese11));
            PdfPCell cell62 = new PdfPCell(new Paragraph("Per: "+invoiceRecord.getPer(),FontChinese11Normal));
            cell61.setBorder(0);
            cell62.setBorder(0);
            table6.addCell(cell61);
            table6.addCell(cell62);
            document.add(table6);

            //table5
            PdfPTable table10 = new PdfPTable(3);
            int width10[] = {50,35,15};
            table10.setWidths(width10);
            PdfPCell cell101 = new PdfPCell(new Paragraph("Sailing on or about: "+invoiceRecord.getSailingOnOrAbout(),FontChinese11));
            PdfPCell cell102 = new PdfPCell(new Paragraph("From: "+invoiceRecord.getFrom(),FontChinese11Normal));
            PdfPCell cell103 = new PdfPCell(new Paragraph("to: "+invoiceRecord.getTo(),FontChinese11Normal));
            cell101.setBorder(0);
            cell102.setBorder(0);
            cell103.setBorder(0);
            table10.addCell(cell101);
            table10.addCell(cell102);
            table10.addCell(cell103);
            document.add(table10);

            //table5
            PdfPTable table11 = new PdfPTable(2);
            PdfPCell cell111 = new PdfPCell(new Paragraph("L/C No.: "+invoiceRecord.getLcNo(),FontChinese11));
            PdfPCell cell112 = new PdfPCell(new Paragraph("Contract No."+invoiceRecord.getContractNo(),FontChinese11Normal));
            cell111.setBorder(0);
            cell112.setBorder(0);
            table11.addCell(cell111);
            table11.addCell(cell112);
            document.add(table11);


            //加入空行
            Paragraph blankRow4 = new Paragraph(18f, " ", FontChinese16);
            document.add(blankRow4);


            PdfPTable tableTemp = PDFUtil.createTableTemp(invoiceRecord.getList());
//            int width71[] = {20,18,13,20,14,15,20,18,13,20,14,15};
//            tableTemp.setWidths(width71);
            tableTemp.setTotalWidth(610);
            tableTemp.setLockedWidth(true);
            document.add(tableTemp);


            document.add(new Paragraph(18f, " ", FontChinese16));
            document.add(new Paragraph(18f, " ", FontChinese16));
            document.add(new Paragraph(18f, " ", FontChinese16));


            PdfPTable table12 = new PdfPTable(1);
            PdfPCell cell121 = new PdfPCell(new Paragraph("Value For Customs Purpose Only.",FontChinese11));
            cell121.setBorder(0);
            table12.addCell(cell121);
            document.add(table12);

            //加入空行
            document.add(new Paragraph(18f, " ", FontChinese16));
            document.add(new Paragraph(18f, " ", FontChinese16));

            PdfPTable table13 = new PdfPTable(1);
            PdfPCell cell131 = new PdfPCell(new Paragraph("N.W.: "+invoiceRecord.getNw(),FontChinese11));
            cell131.setBorder(0);
            table13.addCell(cell131);
            document.add(table13);

            PdfPTable table14 = new PdfPTable(1);
            PdfPCell cell141 = new PdfPCell(new Paragraph("G.W.: "+invoiceRecord.getGw(),FontChinese11));
            cell141.setBorder(0);
            table14.addCell(cell141);
            document.add(table14);


            PdfPTable table15 = new PdfPTable(2);
            PdfPCell cell151 = new PdfPCell(new Paragraph("Measurement:",FontChinese11));
            PdfPCell cell152 = new PdfPCell(new Paragraph(invoiceRecord.getMeasurement(),FontChinese11));
            cell151.setBorder(0);
            cell152.setBorder(1);
            table15.addCell(cell151);
            table15.addCell(cell152);
            document.add(table15);



            document.close();

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return baos;


    }
}