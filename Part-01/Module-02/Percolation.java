/* *****************************************************************************
 *  Name:              Akash Kannapiran
 *  Coursera User ID:  akash-kannapiran
 *  Last modified:     09/03/2026
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSitesCount;
    private final boolean[][] openSites;
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF gridWithoutBottom;
    private final int n;
    private final int top;
    private final int bottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("ERROR");
        }

        this.n = n;
        this.grid = new WeightedQuickUnionUF(n * n + 2);
        this.gridWithoutBottom = new WeightedQuickUnionUF(n * n + 1);
        this.openSites = new boolean[n][n];
        this.openSitesCount = 0;

        top = 0;
        bottom = n * n + 1;

        connectFirstRowToTop();
        connectLastRowToBottom();
    }

    public void open(int row, int col) {
        validateCoordinate(row);
        validateCoordinate(col);

        if (isOpen(row, col)) {
            return;
        }

        openSite(row, col);
        connectToNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        validateCoordinate(row);
        validateCoordinate(col);

        return openSites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateCoordinate(row);
        validateCoordinate(col);

        return isOpen(row, col) && isConnectedToTop(row, col);
    }

    public int numberOfOpenSites() {
        return openSitesCount;
    }

    public boolean percolates() {
        if (unopenedOneSiteGrid()) {
            return false;
        }

        return grid.connected(top, bottom);
    }

    private int convertToGrid(int row, int col) {
        return (row - 1) * n + col;
    }

    private void openSite(int row, int col) {
        openSitesCount++;
        openSites[row - 1][col - 1] = true;
    }

    private boolean isConnectedToTop(int row, int col) {
        return gridWithoutBottom.connected(top, convertToGrid(row, col));
    }

    private void connectFirstRowToTop() {
        for (int col = 1; col <= n; col++) {
            connectToTop(1, col);
        }
    }

    private void connectToTop(int row, int col) {
        grid.union(top, convertToGrid(row, col));
        gridWithoutBottom.union(top, convertToGrid(row, col));
    }

    private void connectLastRowToBottom() {
        for (int col = 1; col <= n; col++) {
            connectToBottom(n, col);
        }
    }

    private void connectToBottom(int row, int col) {
        grid.union(bottom, convertToGrid(row, col));
    }

    private void connectToNeighbors(int row, int col) {
        connect(row, col, row - 1, col); // Top
        connect(row, col, row + 1, col); // Bottom
        connect(row, col, row, col - 1); // Left
        connect(row, col, row, col + 1); // Right
    }

    private void connect(int row1, int col1, int row2, int col2) {
        if ((row2 < 1) || (row2 > n) || (col2 < 1) || (col2 > n)) {
            return;
        }

        if (!isOpen(row2, col2)) {
            return;
        }

        grid.union(convertToGrid(row1, col1), convertToGrid(row2, col2));
        gridWithoutBottom.union(convertToGrid(row1, col1), convertToGrid(row2, col2));
    }

    private void validateCoordinate(int coordinate) {
        if (coordinate < 1 || coordinate > n) {
            throw new IllegalArgumentException("ERROR");
        }
    }

    private boolean unopenedOneSiteGrid() {
        boolean hasOnlyOneSite = n == 1;
        boolean theSiteIsNotOpen = !isOpen(1, 1);

        return hasOnlyOneSite && theSiteIsNotOpen;
    }
}