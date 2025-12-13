public class Position{
    private double x;
    private double y;
    
    public Position(double nx , double ny){
        this.x = nx;
        this.y = ny;
    }
    
    public void setX(double nx){this.x = nx;}
    public void setY(double ny){this.y = ny;}

    public double getX(){return x;}
    public double getY(){return y;}

    public double distanceTo(Position other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public void moveTo(Position destination, double step) {
        double distance = distanceTo(destination);
        if (distance == 0) return;
        double ratio = step / distance;
        this.x = this.x + ratio * (destination.x - this.x);
        this.y = this.y + ratio * (destination.y - this.y);
    }

}
