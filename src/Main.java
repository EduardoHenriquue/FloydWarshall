import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by eduardohenrique on 24/06/17.
 */
public class Main {

    public static String[][] MATRIX;
    public static int N;
    public static String LABEL_INF = "INF";
    public static int INFINITE = 999999999;

    /**
     *
     * @param args
     */
    public static void main(String[] args){

        try {
            readFile("teste.txt");
            floydWarshall();
            printMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Read a text file in format:
     *
     * 5
     * 0 3 8 INF -4
     * INF 0 INF 1 7
     * INF 4 0 INF INF
     * 2 INF -5 0 INF
     * INF INF INF 6 0
     *
     * The first line present the number of vertex.
     * Remaining line's presenting the weights of the edges.
     *
     * @param fileName
     * @throws IOException
     */
    public static void readFile(String fileName) throws IOException {

        BufferedReader reader = null;
        String line = null;
        try{

            reader = new BufferedReader(new FileReader(fileName));
            // Read the first line and get the number of vertex
            N = Integer.parseInt(reader.readLine());
            // Initialize the matrix
            MATRIX = new String[N][N];
            // Read the remaining line's
            for(int i = 0; i < N; i++){
                line = reader.readLine();
                if(line != null){
                    addIntoMatrix(i, line);
                }
            }

        } finally {
            if(reader != null){
                reader.close();
            }
        }
    }

    /**
     * Add the read line into matrix
     * @param i
     * @param line
     */
    public static void addIntoMatrix(int i, String line){
        String[] weights = line.split(" ");
        for(int j = 0; j < N; j++){
            MATRIX[i][j] = weights[j];
        }
    }

    /**
     * The Floyd-Warshall Algorithm
     * @return
     */
    public static String[][] floydWarshall(){

        if(!hasNegativeCycle()) {
            String[][] D = MATRIX;
            for (int k = 0; k < N; k++) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        int dij = isInfinite(D[i][j])
                                ? INFINITE
                                : Integer.parseInt(D[i][j]);

                        int dik = isInfinite(D[i][k])
                                ? INFINITE
                                : Integer.parseInt(D[i][k]);

                        int dkj = isInfinite(D[k][j])
                                ? INFINITE
                                : Integer.parseInt(D[k][j]);

                        // Minimum operation
                        if (dij > dik + dkj) {
                            D[i][j] = dik + dkj + "";
                        }
                    }
                }
            }
            return D;
        }

        return null;
    }

    /**
     * Check if the matrix contains a negative cycle
     * @return boolean
     */
    public static boolean hasNegativeCycle(){
        for (int i = 0; i < N; i++){
            int weight = Integer.parseInt(MATRIX[i][i]);
            if(weight < 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Print the matrix
     */
    public static void printMatrix(){
        String matrix = "";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix += MATRIX[i][j] + " ";
            }
            matrix += "\n";
        }
        System.out.println(matrix);
    }

    /**
     * Check if the matrix element's is infinite
     * @param d
     * @return
     */
    public static boolean isInfinite(String d){
        return d.equals(LABEL_INF);
    }
}
