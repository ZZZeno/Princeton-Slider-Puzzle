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
        for (int i = 0; i < this.dimension(); i ++) {
            for (int j = 0; j < this.dimension(); j ++) {
                if (tiles[i][j] != i * this.dimension() + (j+1)) {
                    ret += 1;
                }
            }
        }
        if (tiles[this.dimension()-1][this.dimension()-1] == 0) {
            ret -= 1;
        }
        return ret;
    }

    public static void main(String[] args) {
        int[][] tiles = {
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0},
        };
        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println(board.hamming());
    }
}
