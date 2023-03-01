import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class NetworkSimulation {
    private static final Logger LOGGER = Logger.getLogger(NetworkSimulation.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    private static void configureLogger(Logger LOGGER) throws IOException {
        LOGGER.setLevel(LOGGER_LEVEL);

        LOGGER.setUseParentHandlers(false);

        SimpleFormatter simpleFormatter =
                new SimpleFormatter() {
                    @Override
                    public String format(LogRecord logRecord) {
                        return String.format(
                                "[%-7s] - %s \n", logRecord.getLevel(), logRecord.getMessage());
                    }
                };

        FileHandler fileHandler = new FileHandler("/home/felipe/Documents/uady/coursera/log");
        ConsoleHandler consoleHandler = new ConsoleHandler();

        fileHandler.setFormatter(simpleFormatter);
        consoleHandler.setFormatter(simpleFormatter);

        LOGGER.addHandler(fileHandler);
        LOGGER.addHandler(consoleHandler);
    }

    public static class Packet {
        private int arrivalTime;
        private int processingTime;

        public Packet(int arrivalTime, int processingTime) {
            this.arrivalTime = arrivalTime;
            this.processingTime = processingTime;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(int arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public int getProcessingTime() {
            return processingTime;
        }

        public void setProcessingTime(int processingTime) {
            this.processingTime = processingTime;
        }

        @Override
        public String toString() {
            return "Packet [arrivalTime="
                    + arrivalTime
                    + ", processingTime="
                    + processingTime
                    + "]";
        }
    }

    private static int[] simulateNetwork(int bufferSize, Packet[] packets) {
        LOGGER.info("=== Simulation ===");

        List<Integer> processingStartTimes = new ArrayList<>(packets.length);
        Deque<Packet> packetsInBuffer = new LinkedList<>();

        int time = 0;
        int startSearchAt = 0;
        int processingTime = 0;
        int processingTimeSum = 0;
        while (processingStartTimes.size() < packets.length) {

            if (!packetsInBuffer.isEmpty()) {
                Packet oldestPacket = packetsInBuffer.peekFirst();

                if (processingTime >= oldestPacket.getProcessingTime()) {
                    packetsInBuffer.removeFirst();

                    processingTimeSum -= oldestPacket.getProcessingTime();
                    processingTime = 0;
                }
            } else {
                processingTime = 0;
            }

            for (int i = startSearchAt; i < packets.length; i++) {
                Packet packet = packets[i];

                if (packet.getArrivalTime() > time) {
                    startSearchAt = i;
                    break;
                }

                if (packetsInBuffer.size() < bufferSize) {
                    if (packet.getProcessingTime() != 0) packetsInBuffer.addLast(packet);

                    processingStartTimes.add(time + processingTimeSum - processingTime);

                    processingTimeSum += packet.getProcessingTime();
                } else {
                    LOGGER.info(String.format("Packet dropped: %s", packet.toString()));

                    processingStartTimes.add(-1);
                }
            }

            LOGGER.info(String.format("Time: %s", time));
            LOGGER.info(String.format("Processing time: %s", processingTime));
            LOGGER.info(String.format("Processing time sum: %s", processingTimeSum));
            LOGGER.info(String.format("Packets in buffer: %s", packetsInBuffer.toString()));

            time++;
            processingTime++;
        }

        return processingStartTimes.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        configureLogger(LOGGER);

        String testFilesDir =
                "/home/felipe/Documents/uady/coursera/data-structures-statements-starters/week1_basic_data_structures/3_network_simulation/tests";
        String testFile = "/20";
        String answerFile = testFile + ".a";

        Scanner scanner = new Scanner(new File(testFilesDir + testFile));

        int bufferSize = scanner.nextInt();
        int noPackets = scanner.nextInt();
        Packet[] packets = new Packet[noPackets];

        for (int i = 0; i < noPackets; i++) {
            packets[i] = new Packet(scanner.nextInt(), scanner.nextInt());
        }

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            LOGGER.info("=== Parsed packets ===");
            int counter = 0;
            for (Packet packet : packets) {
                LOGGER.info(String.format("Packet %s: %s", counter, packet.toString()));
                counter++;
            }
        }

        scanner.close();
        scanner = new Scanner(new File(testFilesDir + answerFile));

        int[] answerResult = new int[noPackets];

        for (int i = 0; i < noPackets; i++) {
            answerResult[i] = scanner.nextInt();
        }

        scanner.close();

        LOGGER.info("=== General data ===");
        LOGGER.info(String.format("Buffer size: %s", bufferSize));
        LOGGER.info(String.format("Number of packets: %s", noPackets));

        int[] result = simulateNetwork(bufferSize, packets);

        LOGGER.info("=== Output ===");
        Arrays.stream(result).forEach(System.out::println);

        if (LOGGER_LEVEL.equals(Level.INFO)) {
            LOGGER.info("=== Arrays comparison ===");
            LOGGER.info("Correct | Computed");
            for (int i = 0; i < noPackets; i++) {
                LOGGER.info(String.format("%7s | %s", answerResult[i], result[i]));
            }
        }

        LOGGER.info("=== Result ===");
        LOGGER.info(String.format("Correct result? %s", Arrays.equals(result, answerResult)));
    }
}
