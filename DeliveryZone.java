public class DeliveryZone{
    private Position center;
    private double radius;

    public DeliveryZone(Position nc , double nr){
        this.center = nc;
        this.radius = nr;
    }

    public Position getCentre(){return center;}
    public double getRadius(){return radius;}

    public void setCenter(Position nc){this.center = nc;}
    public void setRadius(double nr){this.radius = nr;}

    public boolean contains(Position p) {
        return center.distanceTo(p) <= radius;
    }
}
