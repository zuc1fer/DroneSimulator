import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class CityMap{
    private List<DeliveryZone> dz;
    private List<NoFlyZone> nfz;
    private List<WeatherZone> weatherZones;

    public CityMap(List<DeliveryZone> ndz , List<NoFlyZone> nnfz ){

        this.dz = ndz;
        this.nfz = nnfz;
        this.weatherZones = new ArrayList<>();
    }

    public void setDZ(List<DeliveryZone> ndz){this.dz = ndz;}
    public void setNFZ(List<NoFlyZone> nnfz){this.nfz = nnfz;}
    public void setWeatherZones(List<WeatherZone> weatherZones) { this.weatherZones = weatherZones; }

    public List<DeliveryZone> getDZ(){return dz;}
    public List<NoFlyZone> getNFZ(){return nfz;}
    public List<WeatherZone> getWeatherZones() { return new ArrayList<>(weatherZones); }

    public boolean isAllowed(Position p) {
        for (NoFlyZone zone : nfz) {
            if (zone.contains(p)) {
                return false;
            }
        }

        for (WeatherZone zone : weatherZones) {
            if (zone.contains(p)) {
                return false;
            }
        }
        return true;
    }

    public void addWeatherZone(WeatherZone zone) {
        weatherZones.add(zone);
    }

    public void updateWeatherZones(int currentTime) {
        Iterator<WeatherZone> iterator = weatherZones.iterator();
        while (iterator.hasNext()) {
            WeatherZone zone = iterator.next();
            if (!zone.update(currentTime)) {
                iterator.remove();
            }
        }
    }

    public int getActiveWeatherZonesCount() {
        return weatherZones.size();
    }

    public boolean isForbidden(Position p) {
        return !isAllowed(p);
    }

    public void updateWeather(Position center, double radius) {
        weatherZones.add(new WeatherZone(center, radius, 60));
    }
}