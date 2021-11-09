public class Driver {
    public static void main(String[] args){
        EDM edm = new EDM();
        edm.start(edm.setup());
        edm.end();
    }
}