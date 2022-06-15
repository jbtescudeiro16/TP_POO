import java.io.Serializable;

public class SmartSpeaker extends SmartDevice implements Serializable {

    public static final int MAX = 50; //volume máximo
    private int volume;
    private String channel;
    private String brand;
    private double dailyconsumpt;


    public SmartSpeaker() {
        super();
        this.volume = 10;
        this.channel = "Default";
        this.brand="default_Brand";
        this.dailyconsumpt=1.0;
    }

    public SmartSpeaker(int volume , String brand,String channel,boolean o,double dailyconsumpt) {
        super(o);
        this.channel =channel;
        this.brand=brand;
        this.volume = volume;
        this.dailyconsumpt=dailyconsumpt;
    }

    public SmartSpeaker(SmartSpeaker a) {
        super(a);
        this.volume = a.getVolume();
        this.channel=a.getChannel();
        this.brand=a.getBrand();
        this.dailyconsumpt=a.getDailyconsumpt();
    }

    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }

    public int getVolume() {return this.volume;}

    public void setVolume(int volume) {this.volume=volume;}

    public String getChannel() {return this.channel;}

    public void setChannel(String c) {this.channel = c;}

    public void setBrand(String s) {
        this.brand=s;
    }
    public void setDailyconsumpt(double s) {
        this.dailyconsumpt=s;
    }
    public double getDailyconsumpt() {
        return this.dailyconsumpt;
    }
    public String getBrand() {
       return this.brand;
    }


    public String toString(){
        return (
                " ID: "+super.getID()+"| "+
                        this.getClass()+"|"+
                " (isOn?): "+ super.getState()+"|"+
                " volume: "+this.volume+"|"+
                " channel: "+this.channel+"|"+
                " Brand: "+this.brand+"|"+
                " DailyConsumpt: " +this.dailyconsumpt+ '\n');
    }

    public boolean equals(Object obj) {
        if (obj==this) return  true;
        if (obj ==null||obj.getClass()!=this.getClass()) return false;
        SmartSpeaker a = (SmartSpeaker) obj;
        return (this.volume==a.getVolume()  &&
                this.brand==a.getBrand() &&
                this.channel==a.getChannel() &&
                this.dailyconsumpt==a.getDailyconsumpt());

    }
    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }


    //retorna o gasto base + 0.01 vezes o volume em que está a tocar

    public double calculateconsumpt()
    {
        double result=0.0;
        if (super.getState()==true)
        {
        result=(this.dailyconsumpt) + 0.01*this.volume;
        }
        return  result;
    }
}