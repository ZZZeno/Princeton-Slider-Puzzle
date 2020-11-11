import java.util.ArrayList;


public class Board {
    private final int[][] tiles;
    private final int hamming;
    private final int manhattan;
    private final int n;


    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        this.tiles = tiles;
        int ham = 0;
        int manha = 0;
        n = tiles.length;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * this.dimension() + (j + 1)) {
                    ham += 1;
                    int realI = tiles[i][j] / this.dimension();
                    int realJ = tiles[i][j] % this.dimension() - 1;
                    if (realJ < 0) {
                        realI -= 1;
                        realJ = dimension()-1;
                    }
                    manha += Math.abs(realI - i) + Math.abs(realJ - j);
                }
        hamming = ham;
        manhattan = manha;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(this.dimension() + "\n");
        for (int[] row : tiles) {
            for (int col : row) {
                s.append(" ");
                s.append(col);
            }
            s.append("\n");
        }

        return s.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board obj2 = (Board) y;
        return this.equals(obj2);

    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> boardArrayList = new ArrayList<Board>();
        int i0 = 0;
        int j0 = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }

        int[][] neigh = neighbors(i0, j0);
        for (int[] ne : neigh) {
            int[][] newTiles = geneTiles();
            boolean res = exch(newTiles, i0, j0, ne[0], ne[1]);
            if (res) {
                boardArrayList.add(new Board(newTiles));
            }
        }

        return boardArrayList;
    }

    private int[][] neighbors(int i, int j) {
        return new int[][]{
                {i - 1, j},
                {i, j - 1},
                {i + 1, j},
                {i, j + 1},
        };
    }

    private boolean exch(int[][] newTiles, int i, int j, int newI, int newJ) {
        if (newI >= 0 && newJ >= 0 && newI < dimension() && newJ < dimension()) {
            int temp = newTiles[i][j];
            newTiles[i][j] = newTiles[newI][newJ];
            newTiles[newI][newJ] = temp;
            return true;
        }
        return false;
    }

    private int[][] geneTiles() {
        int[][] newTiles = new int[dimension()][dimension()];
        for (int k = 0; k < dimension(); k++) {
            System.arraycopy(tiles[k], 0, newTiles[k], 0, dimension());
        }
        return newTiles;
    }

    public Board twin() {
        int n = dimension();
        int[][] temp = geneTiles();
        for (int i = 0; i < n * n - 1; i++) {
            int x = i / n;
            int y = i % n;
            int xx = (i + 1) / n;
            int yy = (i + 1) % n;
            if (tiles[x][y] != 0 && tiles[xx][yy] != 0) {
                exch(temp, x, y, xx, yy);
                break;
            }
        }
        return new Board(temp);
    }

    public static void main(String[] args) {
        int[][] tiles = {
                {8, 1, 3},
                {4, 2, 0},
                {7, 6, 5},
        };
        tiles = new int[][]{
                {5, 8, 7},
                {1, 4, 6},
                {3, 0, 2},
        };
        Board board = new Board(tiles);
//        System.out.println(board);
//        System.out.println(board.manhattan());
//        for (Board temp : board.neighbors()) {
//            System.out.println(temp);
//        }
        System.out.println(board.manhattan());
    }
}
