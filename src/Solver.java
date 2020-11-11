
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private static class GameTreeNode implements Comparable<GameTreeNode> {
        private final Board board;
        public GameTreeNode parent;
        public int moves;
        private final boolean twin;

        public GameTreeNode(Board board, boolean twin) {
            this.board = board;
            this.parent = null;
            this.twin = twin;
        }

        public boolean isTwin() {
            return this.twin;
        }

        public int compareTo(GameTreeNode o) {
            if (this.priority() > o.priority()) return 1;
            if (this.priority() < o.priority()) return -1;
            return 0;
        }

        public int priority() {
            return board.manhattan() + moves;
        }
    }

    private final MinPQ<GameTreeNode> gameTreeNodesMinPQ = new MinPQ<GameTreeNode>();
    private boolean solvable = false;
    private int moves = 0;
    private final Stack<Board> boardStack = new Stack<Board>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        Board twin = initial.twin();

        GameTreeNode gameTreeNode = new GameTreeNode(initial, false);
        gameTreeNode.moves = 0;

        GameTreeNode gameTreeNodeTwin = new GameTreeNode(twin, true);
        addToTree(gameTreeNodeTwin, gameTreeNode);

        gameTreeNodesMinPQ.insert(gameTreeNode);
        gameTreeNodesMinPQ.insert(gameTreeNodeTwin);
        resolve();
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    private void addToTree(GameTreeNode son, GameTreeNode parent) {
        son.parent = parent;
        son.moves = parent.moves + 1;
    }

    private void resolve() {
        while (!gameTreeNodesMinPQ.isEmpty()) {
//            System.out.println(gameTreeNodesMinPQ.size());
////            System.out.println(gameTreeNodesMinPQ.min());
//            System.out.println(gameTreeNodesMinPQ.min().board);
//            System.out.println("***************************");

            GameTreeNode gameTreeNode = gameTreeNodesMinPQ.min();
            gameTreeNodesMinPQ.delMin();
            GameTreeNode temp = gameTreeNode;
            if (gameTreeNode.board.isGoal()) {
                // 如果队列前端的是Goal状态
                if (gameTreeNode.isTwin()) {
                    this.solvable = false;
                    this.moves = -1;
                } else {
                    this.solvable = true;
                    this.moves = gameTreeNode.moves;
                }
                while (temp.parent != null) {
                    boardStack.push(temp.board);
                    temp = temp.parent;
                }
                return;
            }
            // 如果非Goal状态，则将所有的neighbor塞入队列，放到树内
            for (Board neighbor : gameTreeNode.board.neighbors()) {
                GameTreeNode newGameTreeNode = new GameTreeNode(neighbor, gameTreeNode.isTwin());
                GameTreeNode temp2 = gameTreeNode.parent;
                boolean exists = false;
                while (temp2 != null) {
                    if (temp2.board.equals(newGameTreeNode.board)) {
                        exists = true;
                        break;
                    }
                    temp2 = temp2.parent;
                }
                if (!exists) {
                    addToTree(newGameTreeNode, gameTreeNode);
                    gameTreeNodesMinPQ.insert(newGameTreeNode);
                }
            }
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return boardStack;
    }

    public static void main(String[] args) {
        int[][] tiles = {
                {0, 2, 3},
                {1, 5, 6},
                {4, 7, 8},
        };
        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        System.out.println(solver.isSolvable());
        System.out.println(solver.moves());

        for (Board tempBoard : solver.solution()) {
            System.out.println(tempBoard);
        }
    }
}
