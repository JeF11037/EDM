public class Driver {
    public static void main(String[] args){
        Test test = new Test(new String[]{"Test1", "Test2"});
        if (test.status()){
            EDM edm = new EDM();
            edm.setup();
            edm.start();
            edm.end();
        } else {
            System.out.println("Tests failed");
        }
    }
}