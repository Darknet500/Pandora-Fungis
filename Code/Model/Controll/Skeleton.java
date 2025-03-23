package Controll;

import Bug.*;
import Shroomer.*;
import Tekton.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Skeleton {

    /**
     * This is a global skeleton object. It can be called anywhere. It is static to not let it overwrite by anything
     */
    public static final Skeleton SKELETON = new Skeleton();
    /**
     * List to store the called objects
     */
    private LinkedList<Object> objectStack;


    /**
     * Boolean to controll the printout
     */
    public static boolean print=false;

    /**
     * Hashmap that stores the name of the object that was called
     */
    public HashMap<Object, String> objectNameMap;

    /**
     * Skeleton Constructor
     */
    public Skeleton(){

        objectNameMap = new HashMap<>();
        objectStack = new LinkedList<>();
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
            System.out.println("\t\t" + (i+1) + ":\t" + useCases[i]); /** User inputs with numbers and testcases*/
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
            System.out.println("Please choose the number (1-13) of the testCase you'd like to use (or 'q' to quit):");
            choosenTestCase = scn.nextLine();

            /** Exit if user gives 'q' as input */
            if (choosenTestCase.equalsIgnoreCase("q")) {
                System.exit(0);
            }

            try {
                int number = Integer.parseInt(choosenTestCase);

                /** If the number is correct it leaves the loop and calls the excecuter method */
                if (number >= 1 && number <= 13) {
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

    /**
     * Gets numeric input from user. It is called when the model needs unimplemented information about the model
     * @param message What to ask from the user
     * @param min minimum number the user must give
     * @param max Maximum number the user must give
     * @return number the user gave
     */
    public int getNumericInput(String message, int min, int max) {
        printCall(this, Collections.emptyList(),"getNumericInput");

        System.out.println(message);
        Scanner scn = new Scanner(System.in);

        /**Looping while user gives a correct input */
        while (true) {
            String input = scn.nextLine();

            try {
                int number = Integer.parseInt(input);

                /** If the number is correct it return the user's input as integer */
                if (number >= min && number <= max) {
                    printReturn(String.format("%d",number));
                    return number;
                } else {
                    System.out.println("Invalid number! Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        }

    }

    /**
     * Gets boolean input from user. It is called when the model needs unimplemented information about the model
     * @param message What to ask from the user
     * @return boolean value the user gave
     */
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

    /**
     * Prints out the object and the called object and the method that was used with the parameters. (Synchronous calls)
     * @param called Called object
     * @param parameters List of the parameters gave
     * @param functionHeader Name of the method
     */
    public void printCall(Object called, List<Object> parameters, String functionHeader) {
        if (print) {
            /** Makes a tree like look */
            for (int i = 0; i < objectStack.size(); i++) {
                System.out.print('\t');
            }
            /** prints out the caller -> called: Method(parameters)*/
            System.out.print(SKELETON.objectNameMap.get(objectStack.getLast()) + "->" + SKELETON.objectNameMap.get(called) + ": " + functionHeader + "(");
            /** gets the parameters of the method */
            for (Object o : parameters) {
                if (SKELETON.objectNameMap.containsKey(o)) {
                    System.out.print(SKELETON.objectNameMap.get(o));
                } else if(o==null){
                    System.out.print("null");
                }
                System.out.print((o==parameters.get(parameters.size()-1)?"":", "));
            }
            System.out.print(")\n");

            /** Adding last called object to the end of list*/
            objectStack.addLast(called);

        }
    }

    /**
     * Prints out the return value of the called object to the caller (Asynchronous call)
     * @param message The returned value's String
     */
    public void printReturn(String message){
        if(print) {
            /** Makes a tree like look */
            for (int i = 0; i < objectStack.size() - 1; i++) {
                System.out.print('\t');
            }
            /** prints out the called --> caller: message */
            System.out.println(SKELETON.objectNameMap.get(objectStack.getLast()) + "-->" + SKELETON.objectNameMap.get(SKELETON.objectStack.get(SKELETON.objectStack.size() - 2)) + (message==""?"":": " + message));
            objectStack.removeLast();
        }
    }

    /**
     * Bug bite (No effect) same as in the sequence diagramm
     */
    public void testCase1(){
        System.out.println("Bogár harap (nincs rajta spóra hatás)");
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

    /**
     * Bug wants to bite (biteBlocked) same as in the sequence diagramm
     */
    public void testCase2(){
        System.out.println("Bogár harapni akar (harapás gátolt)");
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

    /**
     * Bug move (No Spore effect) same as in the sequence diagramm
     */
    public void testCase3(){
        System.out.println("Bogár mozog (nincs rajta hatás)");
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

    /**
     * Bug move (Booster Spore effect) same as in the sequence diagramm
     */
    public void testCase4(){
        System.out.println("Bogár mozog (gyorsított)");
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

    /**
     * Bug move (Slower Spore effect) same as in the sequence diagramm
     */
    public void testCase5(){
        System.out.println("Bogár mozog (lassított)");
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

    /**
     * Bug eatSpore same as in the sequence diagramm
     */
    public void testCase6(){
        System.out.println("Bogár elfogyaszt egy gyorsító spórát");
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
        location.storeSpore(boospore);

        print = true;
        bug.eat(boospore);
        print = false;

    }

    /**
     * Shroomer end of turn same as in the sequence diagramm
     */
    public void testCase7(){
        System.out.println("Gombász kör végén fonalak korát növeli");
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

    /**
     * Grow Hypa from tekton without Spore same as in the sequence diagramm
     */
    public void testCase8(){
        System.out.println("Gombász olyan tektonról növeszt fonalat, ahol nincs spóra");
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

    /**
     * Grow Hypa from tekton with Spore same as in the sequence diagramm
     */
    public void testCase9(){
        System.out.println("A gombász olyan tektonról növeszt fonalat, ahol van (nem feltétlenül saját) saját spóra");
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

    /**
     * Throw Spore with boosterMushroom same as in the sequence diagramm
     */
    public void testCase10(){
        System.out.println("A gombász spórát szór az egyik gombával");
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
        //shroomer.throwSpore(mushroom, tekton2);
        print = false;

    }

    /**
     * At the end of game turn deletable hypas destruction same as in the sequence diagramm
     */
    public void testCase11(){
        System.out.println("Játék kör végén a Swampokon a törlendő fonalak törlése");
        Swamp swamp = new Swamp();
        objectNameMap.put(swamp, "swamp");
        Tekton tekton = new Tekton();
        objectNameMap.put(tekton, "tekton");
        swamp.setNeighbours(List.of(tekton));
        tekton.setNeighbours(List.of(swamp));
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

    /**
     * At the end of game turn a tekton breaks in two same as in the sequence diagramm
     */
    public void testCase12(){
        System.out.println("Kör végén egy tekton kettétörik");
        Tekton breaking = new Tekton();
        objectNameMap.put(breaking, "breaking");
        Tekton tekton = new Tekton();
        breaking.addNeighbour(tekton);
        tekton.addNeighbour(breaking);
        objectNameMap.put(tekton, "tekton");
        breaking.setNeighbours(List.of(tekton));
        tekton.setNeighbours(List.of(breaking));
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa = new Hypa(breaking, tekton, shroomer);
        objectNameMap.put(hypa,"hypa");
        shroomer.addHypa(hypa);
        breaking.connectHypa(hypa);
        tekton.connectHypa(hypa);
        BoosterSpore boospore = new BoosterSpore(shroomer);
        objectNameMap.put(boospore, "boospore");
        breaking.storeSpore(boospore);

        print = true;
        breaking.breakTekton();
        print = false;
    }

    /**
     * Grow mushroom same as in the sequence diagramm
     */
    public void testCase13(){
        System.out.println("Egy gombatest növesztése megfelelő körülmények után");
        Tekton applicable = new Tekton();
        objectNameMap.put(applicable, "applicable");
        Tekton tekton = new Tekton();
        applicable.setNeighbours(List.of(tekton));
        tekton.setNeighbours(List.of(applicable));
        objectNameMap.put(tekton, "tekton");
        Shroomer shroomer = new Shroomer((x, y)->new BoosterMushroom(x, y));
        objectNameMap.put(shroomer, "shroomer");
        Hypa hypa = new Hypa(applicable, tekton, shroomer);
        objectNameMap.put(hypa,"hypa");
        applicable.connectHypa(hypa);
        tekton.connectHypa(hypa);
        shroomer.addHypa(hypa);
        BoosterSpore spore1 = new BoosterSpore(shroomer);
        BoosterSpore spore2 = new BoosterSpore(shroomer);
        BoosterSpore spore3 = new BoosterSpore(shroomer);
        objectNameMap.put(spore1, "spore1");
        objectNameMap.put(spore2, "spore2");
        objectNameMap.put(spore3, "spore3");

        applicable.storeSpore(spore1);
        applicable.storeSpore(spore2);
        applicable.storeSpore(spore3);

        print = true;
        shroomer.tryGrowMushroom(applicable);
        print = false;
    }

}
