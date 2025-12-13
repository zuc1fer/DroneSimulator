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
}
