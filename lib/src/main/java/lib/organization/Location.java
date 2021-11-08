package lib.organization;

import java.io.Serializable;

public class Location implements Serializable {
    private long x;
    private Float y; //Поле не может быть null
    private double z;

    public Location() {}

    public Location(long x, Float y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public Float getY() {
        return y;
    }

    public long getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Location: " +
                "x=" + x + "\n" +
                "y=" + y + "\n" +
                "z=" + z;

    }
}
