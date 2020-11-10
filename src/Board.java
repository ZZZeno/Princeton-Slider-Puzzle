import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private int[][] tiles;

    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(new String(String.valueOf(this.dimension()) + "\n"));
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
        return tiles.length;
    }

    public int hamming() {
        int ret = 0;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * this.dimension() + (j + 1))
                    ret += 1;
        return ret;
    }

    public int manhattan() {
        int ret = 0;
        for (int i = 0; i < this.dimension(); i++)
            for (int j = 0; j < this.dimension(); j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * this.dimension() + (j + 1)) {
                    int realI = tiles[i][j] / this.dimension();
                    int realJ = tiles[i][j] % this.dimension() - 1;
                    ret += Math.abs(realI - i) + Math.abs(realJ - j);
                }
        return ret;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board obj2 = (Board) y;
        if (this.dimension() != obj2.dimension()) return false;
        return this.toString().equals(obj2.toString());

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
        for (int[] n : neigh) {
            int[][] newTiles = new int[dimension()][dimension()];
            for (int k = 0; k < dimension(); k ++) {
                System.arraycopy(tiles[k], 0, newTiles[k], 0, dimension());
            }
            boolean res = exch(newTiles, i0, j0, n[0], n[1]);
            if (res) {
                boardArrayList.add(new Board(newTiles));
            }
        }

        return boardArrayList;
    }

    private int[][] neighbors(int i, int j) {
        return new int[][]{
                {i-1, j},
                {i, j-1},
                {i+1, j},
                {i, j+1},
        };
    }

    private boolean exch(int[][] newTiles, int i, int j, int newI, int newJ) {
        if (newI >= 0 && newJ >= 0 && newI < dimension() && newJ < dimension()) {
            newTiles[i][j] = newTiles[newI][newJ];
            newTiles[newI][newJ] = 0;
            return true;
        }
        return false;
    }

    public Board twin() {
        return new Board(null);
    }

    public static void main(String[] args) {
        int[][] tiles = {
                {8, 1, 3},
                {4, 2, 0},
                {7, 6, 5},
        };
        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println(board.manhattan());
        for (Board temp : board.neighbors()) {
            System.out.println(temp);
        }
    }
}
