package Controll;

public class Main {
    public static void main(String[] args) {
        Skeleton s = Skeleton.SKELETON;
/**
        while(true){
            s.start();
        try {
            s.getChoosenTestCase();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        }
 * */


        //System.out.println(s.getNumericInput("Integer", 3, 6));
        //System.out.println(s.getBoolInput("False"));
        //System.out.println(s.getBoolInput("Trrue"));

        s.start();


    }
}
