enum SwingType {
    L,
    R
}

public class Swing {
    private int swingCount = 0;
    private SwingType swingType;

    public Swing(int swingCount, SwingType swingType){
        this.swingCount = swingCount;
        this.swingType = swingType;
    }

    public int getSwingCount(){
        return this.swingCount;
    }

    public SwingType getSwingType(){
        return this.swingType;
    }
}
