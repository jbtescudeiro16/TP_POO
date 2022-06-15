import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal implements Serializable {

    Map<String,House> houses;
    Map<String,Fornecedor> forn;

    LocalDate time=LocalDate.now();


     public Principal(){
         this.houses = new HashMap<String, House>();
         this.forn=new HashMap<String, Fornecedor>();
     }


     public Principal (Map<String,House> houses, Map<String,Fornecedor> forn){
         this.houses = houses.entrySet()
                 .stream()
                 .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));
         this.forn=forn.entrySet()
                 .stream()
                 .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));
         ;
     }
     public Principal(Principal a){
         this.houses=a.getHouses();
         this.forn=a.getForn();
     }

    public Map<String, House> getHouses() {

        return this.houses.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }

    public void adddevCasa(SmartDevice a , String nif){
         this.houses.get(nif).addDevHouse(a.getID(),a);
    }

    public void adddevCasaRoom(SmartDevice a , String nif,String room){
        this.houses.get(nif).addDevToRoom(a.getID(),room,a);
    }
    public LocalDate getTime() {

       // System.out.println("o time tá" + this.time);
        return this.time;
    }

    public Set<String> rooms(String nif){
        return this.houses.get(nif).getRoom().keySet();
    }

    public void setHouses(Map<String, House> houses) {
        this.houses = houses.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }

    public Map<String, Fornecedor> getForn() {
    // return this.forn;
        return this.forn.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }
    public void createDiv(String nif,String room){
         if (this.houses.containsKey(nif)){
        this.houses.get(nif).addRoom(room);
         }
         else System.out.println("Mal");
    }

    public void addFornP(String forn,House a ){
         this.forn.get(forn).addHouse(a.getNif(),a);
    }

    public void setForn(Map<String, Fornecedor> forn) {
        this.forn = forn.entrySet()
                .stream()
                .collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));

    }

    public  boolean setDev(int id,String prop,boolean on) {
        if (this.houses.get(prop).existsDevice(id)) {
            if (on == true) {
                try {
                    this.houses.get(prop).setDeviceon(id);
                    //System.out.println("Aparelho Ligado !");
                    return true;
                }
                catch (NaoExisteDeviceException e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                try {
                    this.houses.get(prop).setDevicOff(id);
                    return true;
                    //System.out.println("Aparelho desligado!");
                }
                catch (NaoExisteDeviceException e){
                    System.out.println(e.getMessage());
                }

            }
        }
        return false;
    }

    public void add_House(String nif,House a) throws JaExisteCasaException{
         if (!existsHouse(nif)){
             this.houses.put(nif,a);
         }
         else throw new JaExisteCasaException("A casa já Existe");
    }

public void showProp(){
    System.out.println(this.houses.keySet());
}
    public void add_Forn(String name,Fornecedor a){
        if (!existsForn(name)){
            this.forn.put(name,a);
        }
        else System.out.println("Já existe um fornecedor com este nome !");
    }

    public void remHouseForn(String forn,String nif){
     if (this.forn.containsKey(forn)) {
            this.forn.get(forn).removeHouse(nif);
        }
        else System.out.println("Já existe um proprietário com este nif !");
    }
    public void remHouse(String nif){
        if (this.houses.containsKey(nif)) {
            this.houses.remove(nif);
        }
        else System.out.println("Nao existe este nif !");
    }
    public boolean existsForn(String name){
         if (this.forn.containsKey(name)){
             return true;
         }
         else  return false;
    }

    public void remove_forn(String name){
        if (existsForn(name)){
            this.forn.remove(name);
        }
    }
    public boolean existsHouse(String a)
    {
    if (this.houses.containsKey(a)){
        return true;
    }
    else return false;
    }

    public void setForn(String forn ,String nif){
         this.houses.get(nif).setForn_name(forn);
    }


    public void removeHouse(String a) {
         if (existsHouse(a)){
             this.houses.remove(a);
         }
    }

    public void advgenerate(Principal col,int days) {

        for (Map.Entry<String,Fornecedor> aux : this.forn.entrySet())
        {

            aux.getValue().generateforall(this.time,sayData(days),days);

        }
        advancedata(days);
        System.out.println("Faturas geradas");
    }

    public boolean existsRoom(String room,String nif){
         if( this.houses.containsKey(nif)){
             return this.houses.get(nif).existsRoom(room);
        }
         return false;
    }
    public void deleteDev(int id,String nif) throws NaoExisteDeviceException {

        if (this.houses.get(nif).getDevices().containsKey(id)) {


            this.houses.get(nif).removeDev(id);
        } else throw new NaoExisteDeviceException("Aparelho não Existe");
    }



    public void setallOn(String nif) {
            this.houses.get(nif).setAllOn();

    }

    public void setallOff(String nif){
        this.houses.get(nif).setAllOff();
    }

    public void removediv(String div,String nif){
         if (existsRoom(div,nif)){
             this.houses.get(nif).removeRoom(div);
        }
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Principal that = (Principal) o;
        return Objects.equals(houses, that.houses) && Objects.equals(forn, that.forn);
    }

    public Principal clone(){
         return new Principal(this);
    }

    public void calculate(int dias){
        String auxiliar=null ;
        double most=0;
            for (Map.Entry<String,House> aux : this.houses.entrySet())
            {
                if (most< aux.getValue().calculatetotalspentDay()) {
                    most = aux.getValue().calculatetotalspentDay();
                    auxiliar=aux.getKey();
                }
            }
        System.out.println(" A casa que mais gastou nos " + dias + " dias  foi :");
        System.out.println("Nif do proprietário: "+ auxiliar);
        System.out.println("Nome do proprietário: " +this.houses.get(auxiliar).getOwner());
        System.out.println("Gasto diário :"+most);
        System.out.println("Gasto nos "+dias + " dias é :" + dias*most);

        }

    public void imprimeHouse(){
        for (Map.Entry<String, House> a : this.houses.entrySet()) {
            System.out.println("----------------------------------------");
            System.out.println("Nif do proprietário: " + a.getValue().getNif());
            System.out.println("Nome do proprietário: " + a.getValue().getOwner());
            System.out.println("Divisões : " + a.getValue().getRoom());
            System.out.println("Aprelhos : "+ a.getValue().getDevices());
            System.out.println("Fornecedor: " + a.getValue().getForn());
            System.out.println("Faturas: " + a.getValue().getFaturas());
            System.out.println("----------------------------------------");
        }
    }

    public void setOnDiv(String nif,String div) {
         if (this.houses.containsKey(nif)){
          this.houses.get(nif).setAllOndiv(div);
         }
         else System.out.println("nao existe casa");
    }

    public void setOffDiv(String nif,String div){
        if (this.houses.containsKey(nif)){
            this.houses.get(nif).setAllOffdiv(div);
        }
        else System.out.println("nao existe casa");
    }




    public String toString() {

        return "Collections{" +'\n'+
                "houses=" + houses + '\n'+
                "forn=" + forn +
                '}' +'\n';
    }

    public  void imprimedata(){
        System.out.println(time);
    }

    public  LocalDate advancedata(int days){
        this.time=this.time.plusDays(days);
        return this.time;
    }
    public LocalDate sayData(int days){
         return this.time.plusDays(days);
    }

    public  void savefile() throws IOException {

        FileOutputStream file=new FileOutputStream("data.obj");

        ObjectOutputStream oos=new ObjectOutputStream(file);
        oos.writeObject(this);

        SmartBulb a =new SmartBulb();
        Fatura b = new Fatura();


       savenumb((long)a.getID());
       savenumb2((long)b.getId());
        // oos.write(a.getID());
        oos.close();
     }

    public static void savenumb(long number) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("aux"))) {
            dos.writeLong(number);
          //  System.out.println("Guardei o numero");
        }
    }

    public static void savenumb2(long number) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("aux2"))) {
            dos.writeLong(number);
            //  System.out.println("Guardei o numero");
        }
    }

    public static long readnumb(long valueIfNotFound) {
        if (!new File("aux").canRead()) return valueIfNotFound;
        try (DataInputStream dis = new DataInputStream(new FileInputStream("aux"))) {
            return dis.readLong();
        } catch (IOException ignored) {
            return valueIfNotFound;
        }
    }
    public static long readnumb2(long valueIfNotFound) {
        if (!new File("aux2").canRead()) return valueIfNotFound;
        try (DataInputStream dis = new DataInputStream(new FileInputStream("aux2"))) {
            return dis.readLong();
        } catch (IOException ignored) {
            return valueIfNotFound;
        }
    }
     public static Principal loadFile() throws IOException, ClassNotFoundException {
         FileInputStream file=new FileInputStream("data.obj");
         ObjectInputStream ois=new ObjectInputStream(file);
         Principal a = (Principal) ois.readObject();
         long b =readnumb(7000);
         long c =readnumb2(700);

         SmartBulb k = new SmartBulb();
         k.setId((int)b);
         Fatura ka = new Fatura();
         ka.setId((int)c);
         ois.close();
         return a;
     }


    public int counthouses(){

        return this.houses.keySet().size();
    }

    public String counttotalprofit(){

         String forn=null;
         double prof =0;
         for (Map.Entry<String,Fornecedor> au : this.forn.entrySet()){
            // System.out.println("chave :"+ au.getKey() + "  valor :" + au.getValue().gettotalProfit());
        if (prof < au.getValue().gettotalProfit()){
            prof = au.getValue().gettotalProfit();
            forn =au.getValue().getName();
        }
         }

         return forn;
    }

    public List<House> ordena(){
         return (this.houses.values().stream().map(House::clone).sorted((h1,h2)->Double.compare(h1.calculatetotalspentDay(),h2.calculatetotalspentDay())).collect(Collectors.toList()));
    }

    public void imprimefaturas(String forn) {

        if (this.forn.containsKey(forn)) {


            if (this.forn.get(forn).getFaturas().entrySet().size() == 0) {
                System.out.println("O Comercializador não possui faturas!");
            } else {
                for (Map.Entry<Integer, Fatura> a : this.forn.get(forn).getFaturas().entrySet()) {
                    System.out.println("----------------------------------------");
                    System.out.println("ID da Fatura: " + a.getKey());
                    System.out.println("Nif do proprietário: " + a.getValue().getNif());
                    System.out.println("Nome do proprietário: " + a.getValue().getProp());
                    System.out.println("Preço antes: " + a.getValue().getCostbefore());
                    System.out.println("Imposto: " + a.getValue().getTax());
                    System.out.println("Preço Final: " + a.getValue().getFullprice());
                    System.out.println("Data inicio: " + a.getValue().getBefore());
                    System.out.println("Data fim: " + a.getValue().getAfter());
                    System.out.println("----------------------------------------");
                }
            }
        } else System.out.println("Não Existe o fornecedor!");
    }
    public boolean exDevROOM(String nif,int id,String room){
    return this.houses.get(nif).getRoom().get(room).contains(id);
    }

    public void imprimemore (){

            int big=0;
            String aux =null;
            for (Map.Entry<String,House>a :this.houses.entrySet())
            {
                if (a.getValue().countRooms() > big){
                    big = a.getValue().countRooms();
                    aux =a.getValue().getNif();
                }
            }
            System.out.println("A casa que tem mais divisões é a do proprietário com o nif: "+aux);
            System.out.println("Tem no total  "+big+" divisões");
        }
    public void imprimemoredevice(){
        int big=0;
        String aux =null;
        for (Map.Entry<String,House>a :this.houses.entrySet())
        {
            if (a.getValue().countdevices() > big){
                big = a.getValue().countdevices();
                aux =a.getValue().getNif();
            }
        }
        System.out.println("A casa que tem mais aparelhos é a do proprietário com o nif: "+aux);
        System.out.println("Tem no total  "+big+" aparelhos");
    }

    public void imprimemoreforn (){
        int big=0;
        String name ="";
        for (Map.Entry<String,Fornecedor>a :this.forn.entrySet())
        {
            if (a.getValue().countHouse() > big){
                big = a.getValue().countHouse();
                name =a.getValue().getName();
            }
        }
        System.out.println("O fornecedor com mais casas é : "+name + " com " + big  + " casas associadas!");
    }

    public void setEnPrice(String forn,double val){
        if (this.existsForn(forn)){
            this.forn.get(forn).setEnergyprice(val);
        }
    }
    public void setImp(String forn,double val){
        if (this.existsForn(forn)){
            this.forn.get(forn).setImposto(val);
        }
    }

}
