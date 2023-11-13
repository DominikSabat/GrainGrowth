import java.awt.*;

public class Nucleon {

    boolean isActive;
    int ID;
    Color kolor;

    public Nucleon(boolean isActive, int ID, Color kolor) {
        this.isActive = isActive;
        this.ID = ID;
        this.kolor = kolor;
    }

    public Nucleon(boolean isActive) {
        this.isActive = isActive;
    }
}
