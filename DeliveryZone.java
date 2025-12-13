public class DeliveryZone{
    private double center;
    private double radius;

    public DeliveryZone(double nc , double nr){
        this.center = nc;
        this.radius = nr;
    }

    public double getCentre(){return center;}
    public double getRadius(){return radius;}

    public void setCenter(double nc){this.center = nc;}
    public void setRadius(double nr){this.radius = nr;}
}
