package main;

public class Rover {

    int NS;
    int WE;
    Orientation orientation;
    String status;

    public Rover(int WE, int NS, Orientation orientation) {
        this.NS = NS;
        this.WE = WE;
        this.orientation = orientation;
        this.status = "";
    }

    public int getNS() {
        return NS;
    }

    public void setNS(int NS) {
        this.NS = NS;
    }

    public int getWE() {
        return WE;
    }

    public void setWE(int WE) {
        this.WE = WE;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
