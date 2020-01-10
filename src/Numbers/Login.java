package Numbers;

public class Login {

    private String id;
    private String pw;

    public Login(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getID() { return id; }
    public String getPW() { return pw; }

    public void setID(String set){ this.id = set;}
    public void setPW(String set){ this.pw = set;}

    public boolean compare(Login compare){

        if(id.equals(compare.getID()) && pw.equals(compare.getPW())) return true;
        else return false;
    }
    public boolean compareID(Login compare){

        if(id.equals(compare.getID())) return true;
        else return false;
    }
    public boolean comparePW(Login compare){
        if(pw.equals(compare.getPW())) return true;
        else return false;
    }

}