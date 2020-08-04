import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Oblig2 {

  public static void main(String[] args) {
    String filnavn = null;
    if (args.length > 0) {
        filnavn = args[0];
    } else {
        System.out.println("Skriv inn filnavn");
    }
    File fil = new File(filnavn);
    TaskLager taskLager = null;
    try {
        taskLager = TaskLager.lesFraFil(fil);
    } catch (FileNotFoundException e) {
        System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
        System.exit(1);
    }
    taskLager.leggTilKant();
    Boolean sykel = taskLager.harSykel(taskLager);
    if (sykel == true) {
      System.exit(0);
    }
    taskLager.leggTilForeldre();
    taskLager.topologiskSort();
    taskLager.slack();

    // for (Task t : taskLager.noder) {
    //   System.out.println(t.hentId() + ": " + t.hentEarliestStart() + " " + t.hentTime());
    // }

    taskLager.print();





  }


}
