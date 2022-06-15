import java.io.Serializable;

public class SmartCamera extends SmartDevice implements Serializable {

    private String res;
    private int filesize;

    private  double dailyconsumpt;


    //let 100X100 be the default resolution

    public SmartCamera(){
        super();
        this.res="100x100";
        this.filesize=1;
        this.dailyconsumpt=1.0;
    }

    public SmartCamera( boolean ligado, String res,int filesize,double dailyconsumpt){
        super(ligado);
        this.res=res;
        this.filesize=filesize;
        this.dailyconsumpt=dailyconsumpt;
    }
    public SmartCamera(SmartCamera a ) {
        super(a);
        this.res=a.getres();
        this.filesize=a.getFilesize();
        this.dailyconsumpt=a.getDailyconsumpt();
    }

    public void setRes(String res){this.res=res;}


    public double getDailyconsumpt() {
        return this.dailyconsumpt;
    }
    public String getres() {
        return this.res;
    }


    public void setFilesize(int  filesize) {
        this.filesize = filesize;
    }
    public int getFilesize(){
        return this.filesize;
    }

    public String toString(){
       return (
               " ID: "+super.getID()+"| "+
                       this.getClass()+"|"+
               " (isOn?): "+ super.getState()+"|"+
               " Resolution: "+this.res+ "|"+
               " FileSize: "+this.filesize+ "|"+
               " DailyConsumpt: " +this.dailyconsumpt+ '\n');
    }


    public boolean equals(Object obj){
        if (obj==this) return true;
        if (obj==null||obj.getClass() !=this.getClass()) return false;
        boolean a = super.equals(obj);
        if (a==true) {
        SmartCamera n = (SmartCamera ) obj;
        return(this.res.equals(n.getres()) && this.filesize==n.getFilesize());
        }
        else return  false;
    }

    public SmartCamera clone(){
      return (new SmartCamera(this));

    }

    public double calculateconsumpt()
    {

        double result =0;

        if (super.getState()==true)
        {
            this.res = this.res.replace("(", "");
            this.res = this.res.replace(")", "");

            Integer a = 0;
            Integer b = 0;

            if (this.res.contains("x")) {
                String[] campos = this.res.split("x");

                a = Integer.parseInt(campos[0]);
                b = Integer.parseInt(campos[1]);
            } else if (this.res.contains("X")) {
                String[] campos = this.res.split("X");

                a = Integer.parseInt(campos[0]);
                b = Integer.parseInt(campos[1]);
            } else if (this.res.contains("-")) {
                String[] campos = this.res.split("-");

                a = Integer.parseInt(campos[0]);
                b = Integer.parseInt(campos[1]);
            }

            result = this.filesize * 0.01 + 0.0001 * (a + b) + this.dailyconsumpt;
        }
    return result;
    }


}
