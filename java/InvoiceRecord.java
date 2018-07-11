import java.util.List;

public class InvoiceRecord {

    private String title = "INVOICE";

    //NO.
    private String no;

    //Date
    private String date;

    //invoice of
    private String invoiceOf;

    //For account and risk of Messrs.
    private String faarom;

    //Shipped by
    private String shippedBy;

    //Per
    private String per;

    //Sailing on or about
    private String sailingOnOrAbout;

    //From
    private String from;

    //to
    private String to;

    //L/C No.
    private String lcNo;

    //Contract No
    private String contractNo;


    //N.W.
    private String nw;

    //G.W.
    private String gw;

    //Measurement
    private String measurement;

    //total
    private String total;

    //list record
    private List<?> list ;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceOf() {
    	
        return invoiceOf;
    }

    public void setInvoiceOf(String invoiceOf) {
        this.invoiceOf = invoiceOf;
    }

    public String getFaarom() {
        return faarom;
    }

    public void setFaarom(String faarom) {
        this.faarom = faarom;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getSailingOnOrAbout() {
        return sailingOnOrAbout;
    }

    public void setSailingOnOrAbout(String sailingOnOrAbout) {
        this.sailingOnOrAbout = sailingOnOrAbout;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLcNo() {
        return lcNo;
    }

    public void setLcNo(String lcNo) {
        this.lcNo = lcNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getNw() {
        return nw;
    }

    public void setNw(String nw) {
        this.nw = nw;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
