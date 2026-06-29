import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF tileSet;
    int[][] numArray;
    String[][] typeArray;
    boolean[][] hasEnd;
    int numOpen;
    int max;
    boolean isPercolated;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N is less than or equal to 0");
        }
        tileSet = new WeightedQuickUnionUF(N * N);
        numOpen = 0;
        max = N - 1;
        isPercolated = false;
        numArray = new int[N][N];
        typeArray = new String[N][N];
        hasEnd = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                numArray[i][j] = i * N + j;
                typeArray[i][j] = "";
                hasEnd[i][j] = i == N - 1;
            }
        }
    }

    public void open(int row, int col) {
        oFBChecker(row, col);
        if (typeArray[row][col].equals("")) {
            numOpen++;
            typeArray[row][col] = "open";
            if (row == 0) {
                typeArray[row][col] = "full";
            }
            openHelper(row, col, row - 1, col);
            openHelper(row, col, row + 1, col);
            openHelper(row, col, row, col - 1);
            openHelper(row, col, row, col + 1);
            if (isFull(row, col) && hasEnd[row][col]) {
                isPercolated = true;
            }
        }

    }

    private void openHelper(int row, int col, int i, int j) {
        String[] tempTypes = new String[2];
        boolean[] tempHasEnds = new boolean[2];
        if ((i >= 0 && i <= max) && (j >= 0 && j <= max)) {
            if (isOpen(i, j)) {
                int root = tileSet.find(numArray[i][j]);
                int newI = root / (max + 1);
                int newJ = root % (max + 1);
                root = tileSet.find(numArray[row][col]);
                int newRow = root / (max + 1);
                int newCol = root % (max + 1);
                tempTypes[0] = typeArray[newI][newJ];
                tempTypes[1] = typeArray[newRow][newCol];
                tempHasEnds[0] = hasEnd[newI][newJ];
                tempHasEnds[1] = hasEnd[newRow][newCol];
                tileSet.union(numArray[i][j], numArray[row][col]);
                root = tileSet.find(numArray[row][col]);
                newRow = root / (max + 1);
                newCol = root % (max + 1);
                if (tempTypes[0].equals("full") || tempTypes[1].equals("full")) {
                    typeArray[newRow][newCol] = "full";
                }
                if (tempHasEnds[0] || tempHasEnds[1]) {
                    hasEnd[newRow][newCol] = true;
                }
                if (hasEnd[newRow][newCol] && typeArray[newRow][newCol].equals("full")) {
                    isPercolated = true;
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        oFBChecker(row, col);
        int root = tileSet.find(numArray[row][col]);
        int newRow = root / (max + 1);
        int newCol = root % (max + 1);
        return typeArray[newRow][newCol].equals("open") || typeArray[newRow][newCol].equals("full");
    }

    public boolean isFull(int row, int col) {
        oFBChecker(row, col);
        int root = tileSet.find(numArray[row][col]);
        int newRow = root / (max + 1);
        int newCol = root % (max + 1);
        return typeArray[newRow][newCol].equals("full");
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean percolates() {
        return isPercolated;
    }

    private void oFBChecker(int row, int col) {
        if ((row < 0 || row > max) || (col < 0 || col > max)) {
            throw new java.lang.IndexOutOfBoundsException("out of bounds");
        }
    }



}
