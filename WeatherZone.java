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
        int elapsed = currentTime - startTime;
        duration--;
        return duration > 0;
    }
}
