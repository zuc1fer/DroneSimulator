public class WeatherZone extends NoFlyZone {
    
    private int duration;
    private String weatherType;
    private int startTime;

    public WeatherZone(Position center, double radius, String weatherType, int duration, int startTime) {
        super(center, radius);
        this.weatherType = weatherType;
        this.duration = duration;
        this.startTime = startTime;
    }

    public boolean update(int currentTime) {
        duration--;
        return duration > 0;
    }

    public String getWeatherType() { return weatherType; }
    public int getDuration() { return duration; }
    public int getStartTime() { return startTime; }

    @Override
    public String toString() {
        return String.format("WeatherZone[%s, Radius: %.1fkm, Duration: %dmin left]", 
            weatherType, getRadius(), duration);
    }
}
