package View;

import java.io.*;
import java.util.Scanner;
import Controller.Controller;
import Model.Bridge.GameBoard;
import Model.Tekton.Tekton;
import java.util.List;


public class CommandLineView implements View {

    /**
     * referencia a kontrollerre, konstruktorban kell beállítani.
     */
    private Controller controller;

    /**
     * scanner, amit Std IO olvasásra használ az objektum, hogy ne kelljen minden sor olvasásához újat példányosítani
     */
    private Scanner scanner;

    public CommandLineView(Controller controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    /**
     * egy sort olvas a standard inputról, és hívja a controller handleInput metódusát, átadja neki a sort.
     * nem validál semmit, csak adja a beolvasott sort, a kontroller felelőssége annak értelmezése
     * (ARRANGE vagy ACT vagy ASSERT mód, ha ACT, akkor milyen játékos következik stb..)
     * Ha a controller nem tudja értelmezni a parancsot, neki kell a hibát jelezni úgy, hogy visszahív a View-ba
     * (serveResult metódus)
     */
    @Override
    public void getInput(){
        String s = scanner.nextLine();
        controller.HandleInput(s);
    }

    /**
     *
     * @param f : az olvasandó fájl.
     * A metódus megnyitja a paraméterként olvasott fájlt, soronként végig olvassa és minden sort átadja a controller
     * handleInput metódusának.
     * Ha a fájl nem létezik, vagy hiba történik az olvasása során, kezeli a kivételt.
     */
    @Override
    public void readInputFile(File f){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while((line = br.readLine())!=null) {
                controller.HandleInput(line);
            }
        } catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        } catch (IOException ioe){
            System.out.println("I/O exception");
        }
    }

    /**
     *
     * @param msg : az üzenet, amit meg kell jeleníteni.
     * Kiírja a konzolra a msg-t.
     */
    @Override
    public void serveResult(String msg) {
        System.out.println(msg);
    }

    /**
     * Ezzel a metódussal a teljes játékpályát megjeleníti, az ARRANGE nyelv formátumában.
     * @param gameBoard : A játékpálya, amit a hívó meg szeretne jeleníttetni
     */
    @Override
    public void displayGameBoard(GameBoard gameBoard){
        List<Tekton> tektons = gameBoard.getTektons();

        for(Tekton tekton : tektons){
            System.out.println(tekton);
        }
    }
}
