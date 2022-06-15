import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Fatura implements Serializable {

    private  static int count=0;

    private int id ;
    private String nif;
    private String prop;
    private String fornecedor;
    private double costbefore;
    private double tax;
    private double fullprice;
    private LocalDate before;
    private LocalDate after;


    public Fatura(){
        this.id=this.count;
        this.count++;
        this.nif="";
        this.prop="";
        this.fornecedor="";
        this.costbefore=0.0;
        this.tax=0.0;
        this.fullprice=0.0;
        this.before=LocalDate.now();
        this.after=LocalDate.now();
    }

    public Fatura(String nif, String prop, String fornecedor, double costbefore, double tax, double fullprice, LocalDate before,LocalDate after) {
        this.id=this.count;
        this.count++;
        this.nif = nif;
        this.prop = prop;
        this.fornecedor = fornecedor;
        this.costbefore = costbefore;
        this.tax = tax;
        this.fullprice = fullprice;
        this.before=before;
        this.after=after;
    }

    public Fatura(Fatura a){
        this.id=a.getId();
        this.nif=a.getNif();
        this.prop=a.getProp();
        this.fornecedor=a.getFornecedor();
        this.costbefore=a.getCostbefore();
        this.tax=a.getTax();
        this.fullprice=a.getFullprice();
        this.after=a.getAfter();
        this.before=a.getBefore();
    }

    public LocalDate getAfter(){
        return this.after;
    }
    public LocalDate getBefore(){
        return this.before;
    }

    public LocalDate setAfter(LocalDate after){
        return this.after;
    }
    public LocalDate setBefore(LocalDate before){
        return this.before;
    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id=id;
        this.count=id;
    }
    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getProp() {
        return this.prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public double getCostbefore() {
        return this.costbefore;
    }

    public void setCostbefore(double costbefore) {
        this.costbefore = costbefore;
    }

    public double getTax() {
        return this.tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getFullprice() {
        return this.fullprice;
    }

    public void setFullprice(double fullprice) {
        this.fullprice = fullprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fatura fatura = (Fatura) o;
        return (this.fullprice==fatura.getFullprice() &&
                this.nif.equals(fatura.getNif()) &&
                this.tax ==fatura.getTax() &&
                this.costbefore == fatura.getTax() &&
                this.prop.equals(fatura.getProp())&&
                this.fornecedor.equals(fatura.getFornecedor())&&
                this.before.equals(fatura.getBefore())&&
                this.after.equals((fatura.getAfter())));
    }



    public Fatura clone() {
        return new Fatura(this);
    }

    public String toString(){

        return "Fatura{" +
                "nif='" + nif + '\'' +
                ", prop='" + prop + '\'' +
                ", fornecedor='" + fornecedor + '\'' +
                "before: " + this.before +"|" +
                "after: " + this.after + "|" +
                ", fullprice=" + fullprice +
                ", costbefore=" + costbefore +
                ", tax=" + tax +
                ", fullprice=" + fullprice +
                '}';
    }

}
