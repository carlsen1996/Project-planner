import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class TaskLager {
  List<Task> noder = new ArrayList<>();
  int optTid = 0;
  int lavest = 0;


  public TaskLager(List<Task> noder) {
    this.noder = noder;

  }

  public static TaskLager lesFraFil(File fil) throws FileNotFoundException{
    List<Task> noder2 = new ArrayList<>();
    Scanner scanner = null;
    scanner = new Scanner(fil);
    String innlest = scanner.nextLine();
    innlest = scanner.nextLine();

    while (scanner.hasNextLine()) {
      List<Integer> ref = new ArrayList<>();
      innlest = scanner.nextLine();
      String[] innlestArr;
      innlest = innlest.replaceAll("\\s+"," ");
      innlestArr = innlest.split(" ");

      int lengde = innlestArr.length;
      if (lengde > 3 && Integer.parseInt(innlestArr[4]) != 0) {
        for (int i = 4; i < lengde - 1; i++) {
          int tall = Integer.parseInt(innlestArr[i]);
          ref.add(tall);
        }
      }
      int id = Integer.parseInt(innlestArr[0]);
      int time = Integer.parseInt(innlestArr[2]);
      int staff = Integer.parseInt(innlestArr[3]);

      Task task = new Task(id, innlestArr[1], time, staff, ref);
      noder2.add(task);
    }
    TaskLager tasklager = new TaskLager(noder2);


    return tasklager;
  }

  public void leggTilForeldre() {
    for (Task denne : noder) {
      for (Task nabo : denne.utKanter()) {
        nabo.cntPredecessors++;
        nabo.innKanter().add(denne);
      }
    }
  }

  public void leggTilKant() {
    for (Task task : noder) {
      for (int i : task.hentOutEdgesInt()) {
        Task enNode = hentTask(i);
        task.utKanter().add(enNode);
      }
    }
  }

  public Task hentTask(int nummer) {
    for (Task task : noder) {
      if (task.hentId() == nummer) {
        return task;
      }
    }
    return null;
  }

  public boolean harSykel(TaskLager tl) {
    List<Task> sortset = new ArrayList<>();
    List<Task> hvitset = new ArrayList<>();
    List<Task> graaset = new ArrayList<>();

    for (Task t : this.noder) {
      hvitset.add(t);
    }

    while (hvitset.size() > 0) {
      Task denne = hvitset.iterator().next();
      if (DFS(denne, hvitset, graaset, sortset)) {
        System.out.println("grafen inneholder en sykel");
        int total = graaset.size();
        for (int i = 0; i < total; i++) {
          System.out.print(graaset.get((total - i) - 1).hentId() + " -> ");
        }
        System.out.print(graaset.get(total - 1).hentId());
        return true;
      }
    }

    return false;

  }
  public boolean DFS(Task t, List<Task> hvit, List<Task> graa, List<Task> sort) {
    flyttTask(t, hvit, graa);

    for (Task ta : t.utKanter()) {
      if (sort.contains(ta)) {
        continue;
      }
      if (graa.contains(ta)) {
        return true;
      }
      if (DFS(ta, hvit, graa, sort)) {
        return true;
      }
    }
    flyttTask(t, graa, sort);
    return false;
  }

  public void flyttTask(Task t, List<Task> fra, List<Task> til) {
    fra.remove(t);
    til.add(t);
  }

  public void topologiskSort() {
    List<Task> midl = new ArrayList<Task>();
    List<Task> midl2 = new ArrayList<Task>();

    for (Task task : noder) {
      if (task.hentCntPredecessors() == 0) {
        midl.add(task);
        task.earliestStart = 0;
      }
    }
    for (Task task1 : noder) {
      optimalTid(task1);

    }
    for (Task task2 : noder) {
      if (task2.innKanter().size() == 0) {
        midl2.add(task2);
        task2.latestStart = optTid - task2.hentTime();
      }
    }
    for (Task task3 : midl2) {
      senestTid(task3);

    }
  }

  public void optimalTid(Task task) {
    for (Task t : task.innKanter()) {
      for (Task ta : t.utKanter()) {
        t.earliestStart = ta.hentEarliestStart() + ta.hentTime();
        optimalTid(t);

      }
    }
    for (Task denne : noder) {
      if (denne.innKanter().size() == 0 && denne.hentEarliestStart() + denne.hentTime() > optTid) {
        optTid = denne.hentEarliestStart() + denne.hentTime();
      }
    }
  }
  public void senestTid(Task task) {
    for (Task t : task.utKanter()) {
      int laveste = optTid;
      for (Task ta : t.innKanter()) {
        if (ta.hentLatestStart() < laveste) {
          laveste = ta.hentLatestStart();
        }
      }
      t.latestStart = laveste - t.hentTime();
      senestTid(t);
    }
  }
  public void slack() {
    for (Task t : noder) {
      t.slack = t.hentLatestStart() - t.hentEarliestStart();
    }
  }
  public void print() {
    int staff = 0;
    int teller = 1;
    for (int i = 0; i <= optTid; i++) {
      System.out.println("Time: " + i);
      teller = 0;
      for (Task t : noder) {
        if (t.hentEarliestStart() == i) {
          System.out.println("           Starting:  " + t.hentId());
          staff = staff + t.hentStaff();
          teller++;
        }
        int slutt = t.hentEarliestStart() + t.hentTime();
        if (slutt == i) {
          System.out.println("           Finished: " + t.hentId());
          staff = staff - t.hentStaff();
          teller++;
        }
      }
      if (teller != 0) {
        System.out.println("           Staff: " + staff);
        System.out.println("");
      }
    }
    System.out.println("Shortest execution is: " + optTid + "\n");
    for (Task task : noder) {
      System.out.println("Id: " + task.hentId());
      System.out.println("Name: " + task.hentName());
      System.out.println("Time needed to finish the task: " + task.hentTime());
      System.out.println("Manpower: " + task.hentStaff());
      System.out.println("Earliest starting time: " + task.hentEarliestStart());
      System.out.println("Slack: " + task.hentSlack());
      System.out.println("Dependencies: " + task.hentOutEdgesInt());
      System.out.println();
    }
  }



}
