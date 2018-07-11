import net.intelink.zmframework.enums.EnumPdfCheckBox;
import net.intelink.zmframework.util.PDFUtil;

import java.util.ArrayList;
import java.util.List;

public class TestModel implements PDFUtil.IPdfTemplate{

    private String desc = "沙发上 说法冯啊冯";
    private String shipperName = "张三";
    private String shipperAddress = "广东省深圳市南山区";
    private String consigneesName = "李四";
    private String consigneesAddress = "湖南省长沙市岳麓区麓谷企业广场";
    private Integer pieces = 8;
    private Double lssuedWeight = 90.1;
    private Double chargableWeight = 88.1;
    private String shipperTel = "17665468383";
    private String consigneesTel = "17665468383";
    private String contactName1 = "wang ss";
    private Boolean d = true;
    private Boolean s;
    private String custNo = "SDDEG1212";
    private String contactName = "uuuuu";
    private String unifiedCode = "90909090";
    private Double length = 10d;
    private Double width = 7d;
    private Double height = 5d;
    private Double volweight = 3332d;
    private Integer qty = 1;
    private Boolean dpic ;
    private String goodsList;
    private Boolean urgeent;
    private EnumPdfCheckBox air ;
    private Boolean ec = false;
    private String value = "sss";
    private Boolean outtax = true;

    private List<PDFUtil.ImageCover> imageCovers = new ArrayList();

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public String getConsigneesName() {
        return consigneesName;
    }

    public void setConsigneesName(String consigneesName) {
        this.consigneesName = consigneesName;
    }

    public String getConsigneesAddress() {
        return consigneesAddress;
    }

    public void setConsigneesAddress(String consigneesAddress) {
        this.consigneesAddress = consigneesAddress;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public Double getLssuedWeight() {
        return lssuedWeight;
    }

    public void setLssuedWeight(Double lssuedWeight) {
        this.lssuedWeight = lssuedWeight;
    }

    public Double getChargableWeight() {
        return chargableWeight;
    }

    public void setChargableWeight(Double chargableWeight) {
        this.chargableWeight = chargableWeight;
    }

    public String getShipperTel() {
        return shipperTel;
    }

    public void setShipperTel(String shipperTel) {
        this.shipperTel = shipperTel;
    }

    public String getConsigneesTel() {
        return consigneesTel;
    }

    public void setConsigneesTel(String consigneesTel) {
        this.consigneesTel = consigneesTel;
    }

    public String getContactName1() {
        return contactName1;
    }

    public void setContactName1(String contactName1) {
        this.contactName1 = contactName1;
    }

    public Boolean getD() {
        return d;
    }

    public void setD(Boolean d) {
        this.d = d;
    }

    public Boolean getS() {
        return s;
    }

    public void setS(Boolean s) {
        this.s = s;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUnifiedCode() {
        return unifiedCode;
    }

    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getVolweight() {
        return volweight;
    }

    public void setVolweight(Double volweight) {
        this.volweight = volweight;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Boolean getDpic() {
        return dpic;
    }

    public void setDpic(Boolean dpic) {
        this.dpic = dpic;
    }

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }

    public Boolean getUrgeent() {
        return urgeent;
    }

    public void setUrgeent(Boolean urgeent) {
        this.urgeent = urgeent;
    }

    public EnumPdfCheckBox getAir() {
        return air;
    }

    public void setAir(EnumPdfCheckBox air) {
        this.air = air;
    }

    public Boolean getEc() {
        return ec;
    }

    public void setEc(Boolean ec) {
        this.ec = ec;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getOuttax() {
        return outtax;
    }

    public void setOuttax(Boolean outtax) {
        this.outtax = outtax;
    }

    @Override
    public List<PDFUtil.ImageCover> getImageCovers() {
        return imageCovers;
    }
}
