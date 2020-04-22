package WineCellar.SEP4.resource;

public class Room {

    private String name;
    private float temperature, humidity, CO2;

    public Room(String name, float temperature, float humidity, float CO2) {
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
        this.CO2 = CO2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getCO2() {
        return CO2;
    }

    public void setCO2(float CO2) {
        this.CO2 = CO2;
    }
}
