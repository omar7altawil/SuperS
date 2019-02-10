package com.example.gamer.supers;

import java.util.Arrays;

/**
 * Created by Gamer on 2/3/2018.
 */

public class Sensors {
    private String  Altitude;
    private String  Humidity;
    private String  Pressure;
    private String TempC;
    private String flame;
    private String  light;


    public Sensors(){}

    public Sensors(String altitude, String humidity, String pressure, String tempC, String flame, String light) {
        Altitude = altitude;
        Humidity = humidity;
        Pressure = pressure;
        TempC = tempC;
        this.flame = flame;
        this.light = light;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getTempC() {
        return TempC;
    }

    public void setTempC(String tempC) {
        TempC = tempC;
    }

    public String getFlame() {
        return flame;
    }

    public void setFlame(String flame) {
        this.flame = flame;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    @Override
    public String toString() {
        return "Sensors{" +
                "Altitude=" + Altitude +
                ", Humidity=" + Humidity +
                ", Pressure=" + Pressure +
                ", TempC=" + TempC +
                ", flame=" + flame +
                ", light=" + light +
                '}';
    }
}
