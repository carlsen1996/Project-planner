import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Task {
  private int id;
  private int time;
  private int staff;
  private String name;
  public int earliestStart;
  public int latestStart;
  private List<Task> outEdges = new ArrayList<>();
  public int cntPredecessors;
  private List<Integer> outEdgesInt = new ArrayList<>();
  private boolean visited = false;
  private List<Task> inEdges = new ArrayList<>();
  public int slack;



  public Task(int id, String name, int time, int staff, List<Integer> outEdgesInt) {
    this.id = id;
    this.name = name;
    this.time = time;
    this.staff = staff;
    this.outEdgesInt = outEdgesInt;
  }
  public void visit() {
    visited = true;
  }
  public List<Task> utKanter() {
    return outEdges;
  }
  public List<Task> innKanter() {
    return inEdges;
  }
  public int hentId() {
    return id;
  }
  public int hentTime() {
    return time;
  }
  public int hentStaff() {
    return staff;
  }
  public String hentName() {
    return name;
  }
  public int hentEarliestStart() {
    return earliestStart;
  }
  public int hentLatestStart() {
    return latestStart;
  }
  public int hentCntPredecessors() {
    return cntPredecessors;
  }
  public List<Integer> hentOutEdgesInt() {
    return outEdgesInt;
  }
  public int hentSlack() {
    return slack;
  }

}
