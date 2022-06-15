import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class House implements Serializable {
    private String owner;
    private String nif;
    private Map<String, Set<Integer>> room;

     private Map<Integer,SmartDevice> devices;

    //lista de  classe faturas

    private String forn_name;
    private Map<Integer,Fatura>faturas;


    /*
        Construtor por omissão
     */
    public House() {
        this.owner = "";
        this.nif = "";
        this.room = new HashMap<String, Set<Integer>>();
        this.devices=new HashMap<Integer, SmartDevice>();
        this.forn_name="Default_Fornecedor";
        this.faturas=new HashMap<Integer, Fatura>();
    }

    public House(String owner, String nif) {

        this.owner = owner;
        this.nif = nif;
        this.room  = new HashMap<String, Set<Integer>>();
        this.devices = new HashMap<Integer, SmartDevice>();
        this.forn_name="Default_Fornecedor";
        this.faturas=new HashMap<Integer, Fatura>();
    }

    public House(House a) {
        this.owner = a.getOwner();
        this.nif = a.getNif();
        this.room = a.getRoom();
        this.devices=a.getDevices();
        this.forn_name=a.getForn();
        this.faturas=a.getFaturas();
    }

    //método que liga todos os aparelhos de uma casa
    public void setAllOn(){
        for (Map.Entry<Integer,SmartDevice> aux : this.devices.entrySet() ) {
            aux.getValue().turnOn();
        }
    }

    public void setAllOndiv(String div){

       Set<Integer> a = this.room.get(div);

       Iterator k = a.iterator();
    while ((k.hasNext())){
        this.devices.get(k.next()).turnOn();

    }
    }

    public void setAllOffdiv(String div){

        Set<Integer> a = this.room.get(div);

        Iterator k = a.iterator();
        while ((k.hasNext())){
            this.devices.get(k.next()).turnOff();

        }
    }




    //método que desliga todos os aparelhos de uma casa
   public void setAllOff(){
        for (Map.Entry<Integer,SmartDevice> aux : this.devices.entrySet() ) {
            aux.getValue().turnOff();
        }
    }

    public void addFatura(Fatura a){
        if (this.faturas.containsKey(a.getId())){
            System.out.println("Já existe esta fatura");
        }
        else  {
            //System.out.println("Adicionei fatura");
            this.faturas.put(a.getId(),a);

        }
    }

//método que liga um aparelho
    public void setDeviceon(int id) throws NaoExisteDeviceException{
        if (existsDevice(id)){
            for (Map.Entry<Integer,SmartDevice> aux : this.devices.entrySet() ) {
               if (aux.getKey()==id) {
                   aux.getValue().turnOn();
               }
            }
        }
        else throw new NaoExisteDeviceException("Não Existe aparelho");
    }

    //método que desliga um aparelho
    public void setDevicOff(int id) throws NaoExisteDeviceException {
        if (existsDevice(id)){
            for (Map.Entry<Integer,SmartDevice> aux : this.devices.entrySet() ) {
                if (aux.getKey()==id) {
                    aux.getValue().turnOff();
                }
        }
    }
    else throw new NaoExisteDeviceException("Não Existe aparelho");
}

    public void setDevices(Map<Integer, SmartDevice> devices) {

        this.devices = devices.entrySet().stream().collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));


    }

    public String getForn() {
        return this.forn_name;
    }

    public void setForn_name(String forn_name) {
        this.forn_name = forn_name;
    }

    public Map<Integer, Fatura> getFaturas() {

        return this.faturas.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
    }

    public void setFaturas(Map<Integer, Fatura> faturas) {
        this.faturas = faturas.entrySet().stream().collect(Collectors.toMap(k-> k.getKey(), k-> k.getValue().clone()));
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<Integer,SmartDevice> getDevices() {
        //return this.devices;
       // return this.devices.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
        return new HashMap<>(this.devices);
    }

    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Map<String, Set<Integer>> getRoom() {
//return this.room;
        return this.room.entrySet().stream().collect(Collectors.toMap(k-> k.getKey(), k-> new HashSet<>(k.getValue())));
    }

    public void setRoom(Map<String, Set<Integer>> room) {

        this.room = room.entrySet().stream().collect(Collectors.toMap(k-> k.getKey(), k-> new HashSet<>(k.getValue())));
    }


    //método que adiciona uma sala a uma casa
    public void addRoom(String room) {
        if (this.room.containsKey(room)==false) {
        this.room.put(room,new HashSet<Integer>());
        }
    }

    //método que adiciona um aparelho a uma sala
    public void addDevToRoom(int dev ,String room,SmartDevice a){
        if (existsRoom(room) && !existsDevice(dev)) {
            this.room.get(room).add(dev);
            this.devices.put(dev,a);
        }
        else {
            Set<Integer> k = new HashSet<Integer>();
            k.add(dev);
            this.room.put(room, k);
        }

    }

    //método que diz se existe uma sala
    public boolean existsRoom(String room) {
        if (this.room.containsKey(room)) return true;
        else return false;
    }

    //método que diz se existe um aparelho numa casa
    public boolean existsDevice(int id) {
        boolean result=false;

            for (Map.Entry<Integer,SmartDevice> aux : this.devices.entrySet() ) {
                if (result==false) {
                    if (aux.getKey()==id) result = true;
                }

        }
        return result;
    }

    public boolean existsDeviceRoom(String Room, int  id) {
        if (existsRoom(Room)) {
            Set<Integer> a = this.room.get(Room);
            if (a.contains(id)) return true;
            else return false;
        }
        return false;
    }

    public void addDevHouse(int  id,SmartDevice a){
        if (existsRoom("Default_Room")){
            addDevToRoom(id,"Default_Room",a);
        }
        else {
            addRoom("Default_Room");
            addDevToRoom(id,"Default_Room",a);
            this.devices.put(id,a);
        }
    }

/*
Temos de decidir se quando uma sala fica vazia a removemos ou não
 */
    public void removeDevRomm(int id,String room) throws NaoExisteDeviceException{

        if (existsDeviceRoom(room,id)) {
            this.room.get(room).remove(id);
            this.devices.remove(id);

            System.out.println("Succesfully removed");
        }
        else  throw new NaoExisteDeviceException("Não Existe aparelho");
    }

    public String getRoom2(int  id) {

        for (Map.Entry<String, Set<Integer>> aux : this.room.entrySet()) {
            if (existsDeviceRoom(aux.getKey(), id)) {
                return (aux.getKey());
            }
        }
        return null;
    }


     public void removeDev(int id) {
         if (existsDevice(id)){
             this.room.get(getRoom2(id)).remove(id);
             this.devices.remove(id);
         }
     }
     public void removeRoom(String room){
        if (existsRoom(room)) {
            this.room.get(room).forEach((e) -> {
                this.devices.remove(e);
            });
            this.room.remove(room);
        }
        else System.out.println("Não existe a sala");

    }


    public House clone(){
        return new House(this);
}

    public String toString() {


        return "House{" +'\n'+
                "Owner='" + this.owner + '\n' +
                "Nif='" + this.nif + '\n' +
                "Room=" + this.room +'\n'+
                "Devices = "+this.devices+'\n'+
                "Faturas= "+this.faturas+'\n'+
                "Fornecedor = "+this.forn_name+
                '}'+'\n'+
                 "----------------------------"+'\n';
    }


//rever esta função
    public  double calculatetotalspentDay(){
        double gasto =0.0;
        for (Map.Entry<Integer,SmartDevice> aux : this.devices.entrySet() ) {
                gasto+= spentdevice(aux.getValue());
            }
        return gasto;

    }


    public double spentdevice(Object o){
        if (o.getClass()==SmartBulb.class) {
            SmartBulb aux = (SmartBulb) o;
            return aux.calculateconsumpt();
        }
        if (o.getClass()==SmartSpeaker.class) {
            SmartSpeaker aux = (SmartSpeaker) o;
            return aux.calculateconsumpt();
        }
        if (o.getClass() ==SmartCamera.class){
            SmartCamera aux = (SmartCamera) o;
            return aux.calculateconsumpt();
        }
        return 0;
    }

    public int countRooms(){
        return this.room.keySet().size();
    }

    public int countdevices(){
        return this.devices.keySet().size();
    }

}
