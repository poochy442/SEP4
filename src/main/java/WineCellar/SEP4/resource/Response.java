package WineCellar.SEP4.resource;

public class Response {

    String data;
    int port;
    String EUI;
    String cmd;
    long ts;

    public Response(String data, int port, String EUI, String cmd, long ts) {
        this.data = data;
        this.port = port;
        this.EUI = EUI;
        this.cmd = cmd;
        this.ts = ts;
    }

    public String getData() {
        return data;
    }

    public int getDataInt() {
        return Integer.parseInt(data);
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getEUI() {
        return EUI;
    }

    public void setEUI(String EUI) {
        this.EUI = EUI;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
