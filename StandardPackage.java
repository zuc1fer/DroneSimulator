public class StandardPackage implements Deliverable{

    private double weight;
    private Position Destination;

    public StandardPackage(double nweight , Position nDestination){

        this.weight = nweight;
        this.Destination = nDestination;
    }
    @Override 
    public double getWeight(){return weight;}
    @Override
    public Position getDestination(){return destination;}

    public void setWeight(double nweight){this.weight = nweight;}
    public void setDestination(Position nDestination){this.Destination = nDestination;}

}
