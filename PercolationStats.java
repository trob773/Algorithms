public class PercolationStats {
    private int iteration;
    private double mean = 0;
    private double stddev = 0;
    private double confidenceLo = 0;
    private double confidenceHi = 0;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) 
    {
        //StdOut.printf("PercolationStats(%d,%d)\n",N,T);
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();
        
        iteration = 0;
        int cells = N*N;
        
        double[] estimates = new double[T];
        
        while (iteration != T)
        {
            //StdOut.printf("Iteration(%d)\n",iteration);
            Percolation perc = new Percolation(N);
            int cellsOpened = 0;
            while (!perc.percolates())
            {
                if (cellsOpened > cells)
                    throw new java.lang.IllegalArgumentException();
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if (!perc.isOpen(i, j))
                {
                    //StdOut.printf("opening cell(%d,%d)\n",i,j);
                    perc.open(i, j);
                    ++cellsOpened;
                    //StdOut.printf("percolates %b\n",perc.percolates());
                }
                else
                {
                    //StdOut.printf("already opened cell(%d,%d)\n",i,j);
                }
            }
            
            estimates[iteration++] = ((double) cellsOpened)/((double) cells);
        }
        
        double sum = 0;
        for (double estimate : estimates)
            sum += estimate;
        
        mean = sum / T;

        double stdevsq = 0;
        
        for (double estimate : estimates)
            stdevsq += (estimate - mean)*(estimate - mean);

        stddev = Math.sqrt(stdevsq / (T-1));
        
        confidenceLo = mean - ((1.96*stddev) / Math.sqrt(T));
        confidenceHi = mean + ((1.96*stddev) / Math.sqrt(T));
    }
    // sample mean of percolation threshold
    public double mean()                     
    {
        return mean;
    }
    // sample standard deviation of percolation threshold
    public double stddev()                   
    {
        return stddev;
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo()          
    {
        return confidenceLo;
    }
    // returns upper bound of the 95% confidence interval
    public double confidenceHi()             
    {
        return confidenceHi;
    }
    // test client, described below
    public static void main(String[] args)   
    {
        //int N = Integer.parseInt(args[0]);
        //int T = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(2, 10000);
        
        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("(95%% confidence interval = %f %f\n",
                      stats.confidenceLo(),
                      stats.confidenceHi());
    }
}