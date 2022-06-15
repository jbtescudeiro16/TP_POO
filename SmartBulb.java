import java.io.Serializable;

public class SmartBulb extends SmartDevice implements Serializable {

    public static  int Warm = 2;
    public static  int Neutral = 1;
    public static  int Cold = 0;

    //size in cm

    private double dailycomsumpt ;
    private int size;
    private int tone;

    public SmartBulb() {
        super();
        this.tone = Neutral;
    }

    public SmartBulb( boolean ligado ,int tone,int size,double dailycomsumpt) {
        super(ligado);
        this.tone = tone;
        this.size=size;
        this.dailycomsumpt=dailycomsumpt;
    }

    public SmartBulb (SmartBulb a){
        super(a);
        this.size=a.getSize();
        this.tone=a.getTone();
        this.dailycomsumpt=a.getdailComsumpt();
    }

    public void setTone(int t) {
        if (t==0) this.tone = 0;
        else if (t==1) this.tone =1;
        else if( t==2) this.tone = 2;
    }

    public void setDailycomsumpt(double dailycomsumpt){
        this.dailycomsumpt=dailycomsumpt;
    }

    public void setSize(int  c){
        this.size=c;
    }


    public int getTone() {
        return this.tone;
    }

    public int getSize() {
        return this.size;
    }


    //está mal
    public double getdailComsumpt(){

       return this.dailycomsumpt;
    }


    public String toString(){
        return(" ID :"+super.getID()+"| "+
                this.getClass()+"|"+
                " (isOn?): "+ super.getState()+"|"+
                " Tone: "+this.tone +"|"+
                " Size: "+this.size+"|"+
                " DailyConsumpt: "+this.dailycomsumpt +'\n');
    }

    public SmartBulb clone(){
        return new SmartBulb(this);
    }

    public boolean equals(Object obj){
        if (obj==this) return true;
        if (obj==null||obj.getClass() !=this.getClass()) return false;
        boolean a = super.equals(obj);
        if (a==true) {
            SmartBulb n = (SmartBulb ) obj;
            return(this.dailycomsumpt ==n.getdailComsumpt() && this.size == n.getSize() && this.tone==n.getTone());
        }
        else return  false;

    }

    public double calculateconsumpt()
    {
        double result = 0;
        if (super.getState() == true) {
            //as lampadas frias economizam cerca de 75 porcento em relação as brancas
            if (this.tone == 0) {
                result= this.dailycomsumpt+(1/2000)*this.size;
            }
            //neutra
            else if (this.tone == 1)
            {
                result=this.dailycomsumpt+0.001*this.size;

            }

            //quente
            else if (this.tone == 2) {
                result= this.dailycomsumpt+0.002*this.size;
            }
        }
    return result;
    }

}

