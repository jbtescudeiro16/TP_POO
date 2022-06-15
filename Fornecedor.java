import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Fornecedor implements Serializable {

    private String name;

    private Map<String, House> lista;

    //map propritario,lista de faturas ;
    //vai ter um metodo de calcular fatura de uma casa


    private Map<Integer,Fatura> faturas;
    private double energyprice;

    private double imposto;


    public Fornecedor(){
        this.name="";
        this.lista= new HashMap<String, House>();
        this.energyprice=0.0;
        this.imposto=2.0;
        this.faturas=new HashMap<Integer, Fatura>();
    }

    public Fornecedor(String name,double energyprice ,double imposto){
        this.name=name;
        this.lista=new HashMap<String, House>();
        this.energyprice=energyprice;
        this.imposto=imposto;
        this.faturas=new HashMap<Integer, Fatura>();
    }

    public  Fornecedor(Fornecedor a) {
        this.imposto=a.getImposto();
        this.energyprice=a.getEnergyprice();
        this.name=a.getName();
        this.lista=a.getLista();
        this.faturas=a.getFaturas();
    }

    public Map<Integer, Fatura> getFaturas(){
        return this.faturas.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }

    public void setFaturas(HashMap<Integer, Fatura> fat){
        this.faturas=fat.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }


    public boolean existsFatura(int id)
    {
        if (this.faturas.containsKey(id))
        {
            return true;
        }
        else return false;
    }

    public void removeFatura(int id){
        if (existsFatura(id)){
            this.faturas.remove(id);
        }
        else System.out.println("Não existe fatura com esse id");
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, House> getLista() {
       return this.lista.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));
    }

    public void setLista(Map<String, House> lista) {
        this.lista = lista.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }

    public double getEnergyprice() {
        return this.energyprice;
    }

    public void setEnergyprice(double energyprice) {
        this.energyprice = energyprice;
    }

    public double getImposto() {
        return this.imposto;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public Fornecedor clone(){
        return new Fornecedor(this);
    }

    @Override
    public String toString() {


        return "name='" + this.name + '\n' +
                "lista=" + this.lista.keySet() +'\n'+
                "energyprice=" + this.energyprice +'\n'+
                "imposto=" + this.imposto +'\n'+
                "Faturas"+this.faturas+'\n'+
                '}'+'\n';
    }
    public void addHouse(String nif,House a){
        this.lista.put(nif,a);
        a.setForn_name(this.name);
    }
    public void removeHouse(String nif){
        if (existsHouse(nif)){
    this.lista.remove(nif   );
        }


    }
    public boolean  existsHouse(String nif) {
        boolean result = true;

        for (Map.Entry<String, House> aux : this.lista.entrySet()) {
            if (result == false) {
                if (aux.getKey().equals(nif)) result = true;
            }

        }
        return result;
    }

    public void generateFatura(String nif,LocalDate before,LocalDate after,int days){
        if(existsHouse(nif))
        {
        Fatura a  = new Fatura(nif,lista.get(nif).getOwner(),this.name,calculateprice(nif,days),this.imposto,calculateimposto(nif,days), before,after);
        this.faturas.put(a.getId(),a);
        this.lista.get(nif).addFatura(a);

        }

    }


    //rever esta função
  public double calculateprice(String nif,int days){
    double total =0;
      if(existsHouse(nif)){
         double aux= this.lista.get(nif).calculatetotalspentDay();
         total=this.energyprice *aux;
         total=total*days;
            return total;
      }
      return 0;
  }

    public void generateforall(LocalDate before,LocalDate after,int days) {
        for (Map.Entry<String, House> aux : this.lista.entrySet()) {
            generateFatura(aux.getKey(), before, after,days);
        }
    }

    public double calculateimposto(String nif,int days){
        double total =0;
        if(existsHouse(nif)){
            double aux= this.lista.get(nif).calculatetotalspentDay();
            total=this.energyprice *aux*days;
            total=total+total*(imposto/100);
            return total;
        }
        return 0;
    }
    public double gettotalProfit(){
        double prof=0;
        for (Map.Entry<String,House> a:this.lista.entrySet()){
        prof+=a.getValue().calculatetotalspentDay();
        }
        //System.out.println(this.name+ " ; Porf : "+ prof);
        return prof*energyprice;
    }

    public int countHouse(){
        return this.lista.keySet().size();
    }


}

