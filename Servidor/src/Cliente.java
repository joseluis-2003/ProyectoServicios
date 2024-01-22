import java.net.InetAddress;

public class Cliente {

    private String apodo;
    private InetAddress inetAddress;

    public Cliente() {
    }

    public Cliente(String apodo, InetAddress inetAddress) {
        this.apodo = apodo;
        this.inetAddress = inetAddress;
    }

    public Cliente(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
        apodo = null;
    }

    public String getApodo() {
        if(apodoValidator()) {
            return apodo;
        }
        else{
            //throw error
            return "";
        }
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    private boolean apodoValidator(){
        if(apodo == null){
            return false;
        }
        return true;
    }
}
