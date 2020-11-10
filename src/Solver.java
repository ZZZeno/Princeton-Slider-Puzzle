import java.util.ArrayList;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    MinPQ<Board> boardMinPQ = new MinPQ<Board>();
    public Solver(Board initial) {
        boardMinPQ.insert(initial);
        
    }

    public boolean isSolvable() {
        return true;
    }

    public int moves() {
        if (!isSolvable()) return -1;
        return 0;
    }
    
    private void resolve() {
        
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        ArrayList<Board> boardArrayList = new ArrayList<Board>();
        return boardArrayList;
    }

    public static void main(String[] args) {

    }
}
