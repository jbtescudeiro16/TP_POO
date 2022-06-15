import java.io.Serializable;

public abstract class   SmartDevice implements Serializable {



    private static int  newid =1;
    private int  id;
    private boolean state;



    public SmartDevice() {
        this.id=this.newid ;
        this.newid++;

        //this.id = "";
        this.state = false;
    }

    public SmartDevice(boolean b) {
        this.id = this.newid;
        this.newid ++;
        this.state = b;
    }
    public  SmartDevice(SmartDevice a){

        this.state=a.getState();
        this.id=a.getID();
    }

    public void turnOn() {
        this.state = true;
    }

    public void turnOff() {
        this.state = false;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean b) {
        this.state = b;
    }


public void setId(int id){
        this.id=id;
        this.newid=id;
        newid++;

}
    public int getID() {
        return this.id;
    }

    public boolean equals(Object a) {
        if (a==this) return true;
        if (a==null||a.getClass() !=this.getClass()) return false;

        SmartDevice b= (SmartDevice) a;
        return (this.id==(b.getID())  && this.state==b.getState());
    }

    public  abstract SmartDevice clone();

    @Override
    public String toString() {
/*
        System.out.println("SmartDevice{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", installation_cost=" + installation_cost +
                '}');;
                */
        return ("SmartDevice{ " +'\n'+
                " id= " + id + '\'' +
                " state= " + state +
                '}');
    }


}
