
public class Percolation {

    private int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF fullUnion;
    private WeightedQuickUnionUF percUnion;
    private int top;
    private int bottom;
    private boolean perculates = false;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)              
    {
        grid = new boolean[N][N];
        size = N;
        fullUnion = new WeightedQuickUnionUF(N*N+1);
        percUnion = new WeightedQuickUnionUF(N*N+2);
        top = N*N;
        bottom = top+1;
    }
    
    private void addUnion(int id1, int id2)
    {
        fullUnion.union(id1, id2);
        percUnion.union(id1, id2);
    }
   
    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        //StdOut.printf("open(%d,%d)\n",i,j);
        if (checkIndices(i, j))
        {
            if (!grid[i-1][j-1])
            {
                grid[i-1][j-1] = true;
                int id = xyTo1D(i, j);
                
                //up
                if (i-1 > 0 && isOpen(i-1, j))
                    addUnion(id, xyTo1D(i-1, j));
                else if (1 == i)
                {
                    fullUnion.union(top, xyTo1D(i, j));
                    percUnion.union(top, xyTo1D(i, j));
                }
                //down
                if (i+1 <= size && isOpen(i+1, j))
                    addUnion(id, xyTo1D(i+1, j));
                else if (size == i)
                {
                    percUnion.union(bottom, xyTo1D(i, j));
                }
                //left
                if (j-1 > 0 && isOpen(i, j-1))
                    addUnion(id, xyTo1D(i, j-1));
                //right
                if (j+1 <= size && isOpen(i, j+1))
                    addUnion(id, xyTo1D(i, j+1));
            }
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)    
    {
        //StdOut.printf("isOpen(%d,%d)\n",i,j);
        boolean open = false;
        if (checkIndices(i, j))
            open = grid[i-1][j-1];
        return open;
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)    
    {
        //StdOut.printf("isFull(%d,%d)\n",i,j);
        return isOpen(i, j) && fullUnion.connected(top, xyTo1D(i, j));
    }
    
    // does the system percolate?
    public boolean percolates()            
    {
        //StdOut.printf("percolates\n");
        if (!perculates)
        {
            perculates = percUnion.connected(top, bottom);
        }
        return perculates;
    }
    
    private boolean checkIndices(int i, int j)
    {
        if (i <= 0 || i > size || j <= 0 || j > size)
            throw new IndexOutOfBoundsException("index out of bounds");
        else
            return true;
    }
    
    private int xyTo1D(int i, int j)
    {
        return (j-1)+(i-1)*size;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int N = 1; //StdIn.readInt();
        //UF uf = new UF(N);
        Percolation perc = new Percolation(N);
        StdOut.printf("percolates %b\n", perc.percolates());
        perc.open(1, 1);
        StdOut.printf("percolates %b\n", perc.percolates());
        /*while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q))
            {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }*/
    }
}
