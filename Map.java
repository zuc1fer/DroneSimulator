import java.util.List;

public class Map{
    private List<DeliveryZone> dz;
    private List<NoFlyZone> nfz;

    public Map(List<DeliveryZone> ndz , List<NoFlyZone> nnfz ){

        this.dz = ndz;
        this.nfz = nnfz;
    }

    public void setDZ(List<DeliveryZone> ndz){this.dz = ndz;}
    public void setNFZ(List<NoFlyZone> nnfz){this.nfz = nnfz;}

    public List<DeliveryZone> getDZ(){return dz;}
    public List<NoFlyZone> getNFZ(){return nfz;}

    public boolean isAllowed(Position p) {
        for (NoFlyZone zone : nfz) {
            if (zone.contains(p)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isForbidden(Position p) {
        return !isAllowed(p);
    }
}