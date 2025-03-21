package Controll;

import Bug.*;
import Shroomer.*;
import Tekton.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Skeleton {
    public static final Skeleton SKELETON = new Skeleton();
    private List<Object> objectStack;

    public static boolean print=false;

    public HashMap<Object, String> objectNameMap;

    public Skeleton(){

        objectNameMap = new HashMap<>();
        objectStack = new ArrayList<>();
    }

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

    /** Title and the user inputs printing*/
    public void start(){
        System.out.println("|-------------------------------------------------------|\n" +
                            "|\t\t\t\t\t\tSkeleton\t\t\t\t\t\t|\n" +                    /**Title*/
                            "|-------------------------------------------------------|");

        for (int i = 0; i < useCases.length; i++) {
            System.out.println("\t\t" + i + ":\t" + useCases[i]); /** User inputs with numbers and testcases*/
        }

        System.out.println("press q if you want to exit"); //Quite obvious :)
        while (true) {
            try {
                getChoosenTestCase();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
            objectStack.addFirst(this);
            objectNameMap.put(this, "Skeleton");
            objectNameMap.put(null, "null");
            m.invoke(this);
            objectStack.clear();
            objectNameMap.clear();




        } catch (IllegalArgumentException | ReflectiveOperationException e) {
            System.out.println("Error executing test case: " + name);
        }
    }

    public int getNumericInput(String message, int min, int max) {
        printCall(this, Collections.emptyList(),"getNumericInput( "+message+")");
        System.out.println(message);
        System.out.print("Please enter a number between " + min + " and " + max + ": ");
        Scanner scn = new Scanner(System.in);

        /**Looping while user gives a correct input */
        while (true) {
            String input = scn.nextLine();

            try {
                int number = Integer.parseInt(input);

                /** If the number is correct it return the user's input as integer */
                if (number >= min && number <= max) {
                    printReturn("numericInput: int");
                    return number;
                } else {
                    System.out.println("Invalid number! Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        }

    }

    public boolean getBoolInput(String message) {
        System.out.println(message);
        System.out.print("Please enter a number between 0 (false) and 1 (ture): ");
        Scanner scn = new Scanner(System.in);

        /**Looping while user gives a correct input */
        while (true) {
            String input = scn.nextLine();

            try {
                int number = Integer.parseInt(input);

                /** If the number is correct it return the user's input as integer */
                if (number == 0) {
                    return false;
                } else if (number == 1) {
                    return true;
                } else {
                    System.out.println("Invalid number!Please enter a number between 0 (false) and 1 (ture): ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number between 0 (false) and 1 (ture): ");
            }
        }
    }

    public void printCall(Object called, List<Object> parameters, String functionHeader) {
        if (print) {
            for (int i = 0; i < objectStack.size(); i++) {
                System.out.print('\t');
            }
            System.out.print(SKELETON.objectNameMap.get(objectStack.getLast()) + "->" + SKELETON.objectNameMap.get(called) + ": " + functionHeader + "(");
            for (Object o : parameters) {
                if (SKELETON.objectNameMap.containsKey(o)) {
                    System.out.print(SKELETON.objectNameMap.get(o) + (o==parameters.getLast()?"":", "));
                }
            }
            System.out.print(")\n");
            objectStack.addLast(called);

        }
    }

    public void printReturn(String message){
        if(print) {
            for (int i = 0; i < objectStack.size() - 1; i++) {
                System.out.print('\t');
            }
            System.out.println(SKELETON.objectNameMap.get(objectStack.getLast()) + "-->" + SKELETON.objectNameMap.get(SKELETON.objectStack.get(SKELETON.objectStack.size() - 2)) + (message==""?"":": " + message));
            objectStack.removeLast();
        }
    }

    public void testCase1(){
        System.out.println("Test case 1");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Tekton tekton2 = new Tekton();
        objectNameMap.put(tekton2, "tekton2");
        location.setNeighbours(List.of(tekton2));
        tekton2.setNeighbours(List.of(location));
        Bug bug = new Bug();
        objectNameMap.put(bug, "bug");
        bug.setLocation(location);
        location.setBug(bug);
        Normal strategy = new Normal();
        objectNameMap.put(strategy, "strategy");
        bug.setStrategy(strategy);
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa= new Hypa(location, tekton2, shroomer);
        objectNameMap.put(hypa, "hypa");
        location.connectHypa(hypa);
        tekton2.connectHypa(hypa);
        shroomer.addHypa(hypa);

        print = true;
        bug.bite(hypa);
        print = false;

    }
    public void testCase2(){
        System.out.println("Test case 2");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Tekton tekton2 = new Tekton();
        objectNameMap.put(tekton2, "tekton2");
        location.setNeighbours(List.of(tekton2));
        tekton2.setNeighbours(List.of(location));
        Bug bug = new Bug();
        objectNameMap.put(bug, "bug");
        bug.setLocation(location);
        location.setBug(bug);
        BiteBlocked strategy = new BiteBlocked();
        objectNameMap.put(strategy, "strategy");
        bug.setStrategy(strategy);
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");

        Hypa hypa= new Hypa(location, tekton2, shroomer);
        SKELETON.objectNameMap.put(hypa, "hypa");
        location.connectHypa(hypa);
        tekton2.connectHypa(hypa);
        shroomer.addHypa(hypa);

        print = true;
        bug.bite(hypa);
        print = false;


    }
    public void testCase3(){
        System.out.println("Test case 3");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Tekton to = new Tekton();
        objectNameMap.put(to, "to");
        location.setNeighbours(List.of(to));
        to.setNeighbours(List.of(location));
        Bug bug = new Bug();
        objectNameMap.put(bug, "bug");
        bug.setLocation(location);
        location.setBug(bug);
        Normal strategy = new Normal();
        objectNameMap.put(strategy, "strategy");
        bug.setStrategy(strategy);
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa= new Hypa(location, to, shroomer);
        SKELETON.objectNameMap.put(hypa, "hypa");
        location.connectHypa(hypa);
        to.connectHypa(hypa);
        shroomer.addHypa(hypa);

        print = true;
        bug.move(to);
        print = false;


    }
    public void testCase4(){
        System.out.println("Test case 4");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Tekton to = new Tekton();
        objectNameMap.put(to, "to");
        location.setNeighbours(List.of(to));
        to.setNeighbours(List.of(location));
        Bug bug = new Bug();
        objectNameMap.put(bug, "bug");
        bug.setLocation(location);
        location.setBug(bug);
        Boosted strategy = new Boosted();
        objectNameMap.put(strategy, "strategy");
        bug.setStrategy(strategy);
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa= new Hypa(location, to, shroomer);
        SKELETON.objectNameMap.put(hypa, "hypa");
        location.connectHypa(hypa);
        to.connectHypa(hypa);
        shroomer.addHypa(hypa);

        print = true;
        bug.move(to);
        print = false;

    }
    public void testCase5(){
        System.out.println("Test case 5");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Tekton to = new Tekton();
        objectNameMap.put(to, "to");
        location.setNeighbours(List.of(to));
        to.setNeighbours(List.of(location));
        Bug bug = new Bug();
        objectNameMap.put(bug, "bug");
        bug.setLocation(location);
        location.setBug(bug);
        Slowed strategy = new Slowed();
        objectNameMap.put(strategy, "strategy");
        bug.setStrategy(strategy);
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa= new Hypa(location, to, shroomer);
        SKELETON.objectNameMap.put(hypa, "hypa");
        location.connectHypa(hypa);
        to.connectHypa(hypa);
        shroomer.addHypa(hypa);

        print = true;
        bug.move(to);
        print = false;

    }
    public void testCase6(){
        System.out.println("Test case 6");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Bug bug = new Bug();
        objectNameMap.put(bug, "bug");
        bug.setLocation(location);
        location.setBug(bug);
        Normal strategy = new Normal();
        objectNameMap.put(strategy, "strategy");
        bug.setStrategy(strategy);
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        BoosterSpore boospore = new BoosterSpore(shroomer);
        objectNameMap.put(boospore, "boospore");

        print = true;
        bug.eat(boospore);
        print = false;

    }
    public void testCase7(){
        System.out.println("Test case 7");
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Tekton end1 = new Tekton();
        objectNameMap.put(end1, "end1");
        Tekton end2 = new Tekton();
        objectNameMap.put(end2, "end2");
        end1.setNeighbours(List.of(end2));
        end2.setNeighbours(List.of(end1));
        Hypa hypa= new Hypa(end1, end2, shroomer);
        SKELETON.objectNameMap.put(hypa, "hypa");
        end1.connectHypa(hypa);
        end2.connectHypa(hypa);
        BoosterMushroom mushroom = new BoosterMushroom(shroomer, end1);
        objectNameMap.put(mushroom, "mushroom");
        shroomer.addHypa(hypa);
        print = true;
        shroomer.endOfRoundAdministration();
        print = false;

    }

    public void testCase8(){
        System.out.println("Test case 8");
        Tekton start = new Tekton();
        objectNameMap.put(start, "start");
        Tekton target = new Tekton();
        objectNameMap.put(target, "target");
        start.setNeighbours(List.of(target));
        target.setNeighbours(List.of(start));
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        BoosterMushroom mushroom = new BoosterMushroom(shroomer, start);
        start.setMushroom(mushroom);
        shroomer.addMushroom(mushroom);
        objectNameMap.put(shroomer, "shroomer");
        objectNameMap.put(mushroom, "mushroom");

        print = true;
        shroomer.growHypa(start, target);
        print = false;
    }

    public void testCase9(){
        System.out.println("Test case 9");
        Tekton start = new Tekton();
        objectNameMap.put(start, "start");
        Tekton middle = new Tekton();
        objectNameMap.put(middle, "middle");
        Tekton target = new Tekton();
        objectNameMap.put(target, "target");

        start.setNeighbours(List.of(middle));
        middle.setNeighbours(List.of(start, target));
        target.setNeighbours(List.of(middle));
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        BoosterMushroom mushroom = new BoosterMushroom(shroomer, start);
        objectNameMap.put(mushroom, "mushroom");
        shroomer.addMushroom(mushroom);
        start.setMushroom(mushroom);
        BoosterSpore boospore = new BoosterSpore(shroomer);
        objectNameMap.put(boospore, "boospore");

        print = true;
        shroomer.growHypaFar(start,middle, target);
        print = false;


    }
    public void testCase10(){
        System.out.println("Test case 10");
        Tekton location = new Tekton();
        objectNameMap.put(location, "location");
        Tekton tekton1 = new Tekton();
        objectNameMap.put(tekton1, "tekton1");
        Tekton tekton2 = new Tekton();
        objectNameMap.put(tekton2, "tekton2");

        location.setNeighbours(List.of(tekton1));
        tekton1.setNeighbours(List.of(location, tekton2));
        tekton2.setNeighbours(List.of(tekton1));
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        BoosterMushroom mushroom = new BoosterMushroom(shroomer, location);
        objectNameMap.put(mushroom, "mushroom");
        shroomer.addMushroom(mushroom);
        location.setMushroom(mushroom);

        print = true;
        shroomer.throwSpore(mushroom, tekton1);
        shroomer.throwSpore(mushroom, tekton2);
        print = false;

    }
    public void testCase11(){
        System.out.println("Test case 11");
        Swamp swamp = new Swamp();
        objectNameMap.put(swamp, "swamp");
        Tekton tekton = new Tekton();
        objectNameMap.put(tekton, "tekton");
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa1 = new Hypa(swamp, tekton, shroomer);
        objectNameMap.put(hypa1,"hypa1");
        Hypa hypa2 = new Hypa(swamp, tekton, shroomer);
        objectNameMap.put(hypa2,"hypa2");
        shroomer.addHypa(hypa1);
        shroomer.addHypa(hypa2);
        swamp.connectHypa(hypa1);
        swamp.connectHypa(hypa2);
        tekton.connectHypa(hypa1);
        tekton.connectHypa(hypa2);

        print = true;
        swamp.checkForDeleteHypa();
        print = false;


    }
    public void testCase12(){
        System.out.println("Test case 12");
        Tekton breaking = new Tekton();
        objectNameMap.put(breaking, "breaking");
        Tekton tekton = new Tekton();
        objectNameMap.put(tekton, "tekton");
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa = new Hypa(breaking, tekton, shroomer);
        objectNameMap.put(hypa,"hypa");
        BoosterSpore boospore = new BoosterSpore(shroomer);
        objectNameMap.put(boospore, "boospore");
        breaking.storeSpore(boospore);

        print = true;
        breaking.breakTekton();
        print = false;
    }

}
