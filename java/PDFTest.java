import net.intelink.oms.core.dto.order.PrintShippingMark;
import net.intelink.oms.web.model.FarDarPrintOrder;
import net.intelink.zmframework.enums.EnumPdfCheckBox;
import net.intelink.zmframework.util.ImagesUtil;
import net.intelink.zmframework.util.*;
import net.intelink.zmframework.util.PDFUtil.ImageCover;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @description
 * @author 刘华东
 * @date 2018/3/15 14:27
 * @version 1.0.0
 */
public class PDFTest {
    public static void main(String[] args) throws Exception{

        test();

    }


    public static void test() throws Exception{
        String text = "7747477747477474774";
        List<PDFUtil.IPdfTemplate> printShippingMarkList = new ArrayList<>();
        PrintShippingMark userModelTest = new PrintShippingMark();
        userModelTest.setVotNo("asdsadsad");
        userModelTest.setTransType("asdsadasdsadsad");
        userModelTest.setWeight("15");
        userModelTest.setSpec("15");
        byte[] barCode = ImagesUtil.toBarCodeToStream(text,100 , 30).toByteArray();
        byte[] barCode1 = ImagesUtil.toBarCodeToStream(text,100 , 40).toByteArray();
        List<ImageCover> imageCovers = userModelTest.getImageCovers();
        imageCovers.add(new ImageCover(70, 242, barCode));
        imageCovers.add(new ImageCover(70, 170, barCode1));
        printShippingMarkList.add(userModelTest);


        String pdfTemplate = "C:/Users/intelink010/Desktop/FBA外箱面单.pdf";

        ByteArrayOutputStream outputStream = PDFUtil.fillPDFTemplate(pdfTemplate, userModelTest);
        outputStream.writeTo(new FileOutputStream("D:/templepdf/fardar.pdf"));
    }
}

class PrintTestModel implements PDFUtil.IPdfTemplate {
    private String test1 = "";
    private String test2 = "";
    private String test3 = "";
    private String test4 = "";
    private String test5 = "";
    private String test6 = "";
    private String test7 = "";
    private String test8 = "";
    private String test9 = "";
    private List<ImageCover> imageCovers = new ArrayList();
    public String getTest1() {
        return test1;
    }

    public void setTest1(String test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    public String getTest3() {
        return test3;
    }

    public void setTest3(String test3) {
        this.test3 = test3;
    }

    public String getTest4() {
        return test4;
    }

    public void setTest4(String test4) {
        this.test4 = test4;
    }

    public String getTest5() {
        return test5;
    }

    public void setTest5(String test5) {
        this.test5 = test5;
    }

    public String getTest6() {
        return test6;
    }

    public void setTest6(String test6) {
        this.test6 = test6;
    }

    public String getTest7() {
        return test7;
    }

    public void setTest7(String test7) {
        this.test7 = test7;
    }

    public String getTest8() {
        return test8;
    }

    public void setTest8(String test8) {
        this.test8 = test8;
    }

    public String getTest9() {
        return test9;
    }

    public void setTest9(String test9) {
        this.test9 = test9;
    }

    public void setImageCovers(List<ImageCover> imageCovers) {
        this.imageCovers = imageCovers;
    }

    public PrintTestModel() {
    }

    @Override
    public List<ImageCover> getImageCovers() {
        return this.imageCovers;
    }
}
