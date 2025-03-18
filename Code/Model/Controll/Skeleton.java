package Controll;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Skeleton {
    public static final Skeleton SKELETON = new Skeleton();

    private String[] useCases = {"Bogár harap (nincs rajta spóra hatás)",
                                "Bogár harapni akar (harapás gátolt)",
                                "Bogár mozog (nincs rajta hatás)",
                                "Bogár mozog (gyorsított)",
                                "Bogár mozog (lassított)",
                                "Bogár elfogyaszt egy gyorsító spórát",
                                "Gombász kör végén fonalak korát növeli,",
                                "Gombász olyan tektonról növeszt fonalat, ahol nincs spóra",
                                "A gombász olyan tektonról növeszt fonalat, ahol van (nem feltétlenül saját) saját spóra",
                                "A gombász spórát szór az egyik gombával.",
                                "Játék kör végén a Swampokon a törlendő fonalak törlése",
                                "Kör végén egy tekton kettétörik",
                                "Egy gombatest növesztése megfelelő körülmények után"
    };

    public void start(){
        System.out.println("|-------------------------------------------------------|\n" +
                            "|\t\t\t\t\t\tSkeleton\t\t\t\t\t\t|\n" +
                            "|-------------------------------------------------------|");

        for (int i = 0; i < useCases.length; i++) {
            System.out.println("\t\t" + i + ":\t" + useCases[i]);
        }

        System.out.println("press q if you want to exit");

    }

    /** Gets the user input while it's not correct */
    public void getChoosenTestCase() throws NoSuchMethodException {
        Scanner scn = new Scanner(System.in);
        String choosenTestCase;

        /**Looping while user gives a correct input */
        while (true) {
            System.out.println("Please choose the number (1-12) of the testCase you'd like to use (or 'q' to quit):");
            choosenTestCase = scn.nextLine();

            /** Exit if user gives 'q' as input */
            if (choosenTestCase.equalsIgnoreCase("q")) {
                System.exit(0);
            }

            try {
                int number = Integer.parseInt(choosenTestCase);

                /** If the number is correct it leaves the loop and calls the excecuter method */
                if (number >= 1 && number <= 12) {
                    executeTestCase(number);
                    break;
                } else {
                    System.out.println("Invalid number! Please enter a number between 1 and 12.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 12 or 'q' to quit.");
            }
        }
    }
    /** Calls the testCase{number} method
     * @param number
     * */
    private void executeTestCase(int number) {
        String name = "testCase" + number;

        try {
            /** gets the method's name then calls it */
            Method m = this.getClass().getDeclaredMethod(name);
            m.invoke(this);
        } catch (IllegalArgumentException | ReflectiveOperationException e) {
            System.out.println("Error executing test case: " + name);
        }
    }

    public void testCase1(){System.out.println("Test case 1");}
    public void testCase2(){System.out.println("Test case 2");}
    public void testCase3(){System.out.println("Test case 3");}
    public void testCase4(){System.out.println("Test case 4");}
    public void testCase5(){System.out.println("Test case 5");}
    public void testCase6(){System.out.println("Test case 6");}
    public void testCase7(){System.out.println("Test case 7");}
    public void testCase8(){System.out.println("Test case 8");}
    public void testCase9(){System.out.println("Test case 9");}
    public void testCase10(){System.out.println("Test case 10");}
    public void testCase11(){System.out.println("Test case 11");}
    public void testCase12(){System.out.println("Test case 12");}

}
