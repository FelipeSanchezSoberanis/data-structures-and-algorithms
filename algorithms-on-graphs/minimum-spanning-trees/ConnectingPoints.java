import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ConnectingPoints {
    private static MyLogger LOGGER = new MyLogger(ConnectingPoints.class.getName());

    private static double minimumDistance(int[] x, int[] y) {
        LOGGER.info("minimumDistance(int[] x, int[] y)");
        LOGGER.infoFormat("x: %s", Arrays.toString(x));
        LOGGER.infoFormat("y: %s", Arrays.toString(y));

        double result = 0;
        // write your code here

        LOGGER.info("");
        return result;
    }

    public static void main(String[] args) {
        DataReader dataReader = new DataReader();
        dataReader.readData();

        System.out.format("%.9f", minimumDistance(dataReader.getX(), dataReader.getY()));
    }
}

class Edge {
    private Vertex vertexA;
    private Vertex vertexB;
    private double distance;

    public Edge(Vertex vertexA, Vertex vertexB) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.distance = Vertex.getDistance(vertexA, vertexB);
    }

    public Vertex getVertexA() {
        return vertexA;
    }

    public Vertex getVertexB() {
        return vertexB;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(distance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((vertexA == null) ? 0 : vertexA.hashCode());
        result = prime * result + ((vertexB == null) ? 0 : vertexB.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Edge other = (Edge) obj;
        if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
            return false;
        if (vertexA == null) {
            if (other.vertexA != null) return false;
        } else if (!vertexA.equals(other.vertexA)) return false;
        if (vertexB == null) {
            if (other.vertexB != null) return false;
        } else if (!vertexB.equals(other.vertexB)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Edge [distance=" + distance + ", vertexA=" + vertexA + ", vertexB=" + vertexB + "]";
    }
}

class Vertex {
    private static MyLogger LOGGER = new MyLogger(Vertex.class.getName()).disabled();

    private int x;
    private int y;

    public static double getDistance(Vertex a, Vertex b) {
        LOGGER.infoFormat("getDistance(Vertex a, Vertex b)");
        LOGGER.infoFormat("Vertex a: %s", a);
        LOGGER.infoFormat("Vertex b: %s", b);

        double diff1 = a.getX() - b.getX();
        double diff2 = a.getY() - b.getY();
        double distance = Math.sqrt(Math.pow(diff1, 2.0) + Math.pow(diff2, 2.0));

        LOGGER.infoFormat("Distance: %s", distance);

        return distance;
    }

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vertex other = (Vertex) obj;
        if (x != other.x) return false;
        if (y != other.y) return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vertex [x=" + x + ", y=" + y + "]";
    }
}
class DataReader {
    private int[] x;
    private int[] y;

    public void readData() {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        x = new int[n];
        y = new int[n];

        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }

        scanner.close();
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }
}

class MyLogger {
    private Logger logger;
    private Level loggerLevel;
    private static final Level GLOBAL_LOGGER_LEVEL = Level.OFF;

    public MyLogger disabled() {
        this.logger.setLevel(Level.OFF);
        return this;
    }

    public MyLogger(String className) {
        this.logger = Logger.getLogger(className);
        this.loggerLevel = GLOBAL_LOGGER_LEVEL;

        configureLogger();
    }

    private void configureLogger() {
        logger.setLevel(loggerLevel);

        logger.setUseParentHandlers(false);

        SimpleFormatter simpleFormatter =
                new SimpleFormatter() {
                    @Override
                    public String format(LogRecord logRecord) {
                        return String.format(
                                "[%s]: %s - %s",
                                logRecord.getLevel(),
                                logRecord.getLoggerName(),
                                logRecord.getMessage());
                    }
                };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);

        logger.addHandler(consoleHandler);
    }

    public void infoFormat(String stringToFormat, Object... variables) {
        logger.info(String.format(stringToFormat + "\n", variables));
    }

    public void info(String info) {
        logger.info(info + "\n");
    }
}
