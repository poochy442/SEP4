package WineCellar.SEP4.resource;


import javax.persistence.*;

public class Room {

    private String roomName;
    private String EUI;
    private int temperature, humidity, CO2, acuator;

    public Room() {
    }

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public Room(String name, String eui) {
        this.roomName = name;
        this.EUI = eui;
    }

    public Room(String name, int temperature, int humidity, int CO2, int acuator) {
        this.roomName = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.CO2 = CO2;
        this.acuator = acuator;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String name) {
        this.roomName = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public String getEUI() {
        return EUI;
    }

    public void setEUI(String EUI) {
        this.EUI = EUI;
    }

    public int getAcuator() {
        return acuator;
    }

    public void setAcuator(int acuator) {
        this.acuator = acuator;
    }
}
