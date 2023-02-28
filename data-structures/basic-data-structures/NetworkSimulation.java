import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkSimulation {
    private static final Logger LOGGER = Logger.getLogger(NetworkSimulation.class.getName());
    private static final Level LOGGER_LEVEL = Level.INFO;

    private static class Packet {
        private Integer arrivalTime;
        private Integer processingTime;

        public Packet(Integer arrivalTime, Integer processingTime) {
            this.arrivalTime = arrivalTime;
            this.processingTime = processingTime;
        }

        public Integer getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(int arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public Integer getProcessingTime() {
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

    private static int[] simulate(int bufferSize, Packet[] packets) {
        int startSearchIndex = 0;

        int[] simulationResult = new int[packets.length];
        Deque<Packet> packetsInBuffer = new LinkedList<>();

        final int[] iWrapper = new int[1];

        int counter = 0;
        for (int i = 0; i <= packets[packets.length - 1].getArrivalTime(); i++) {
            LOGGER.info(String.format("i: %s", i));

            iWrapper[0] = i;

            LOGGER.info(String.format("Bufer is empty? %s", packetsInBuffer.isEmpty()));
            if (!packetsInBuffer.isEmpty()) {
                LOGGER.info(
                        String.format(
                                "Arrival time + processing time:  %s",
                                packetsInBuffer.peekFirst().getArrivalTime()
                                        + packetsInBuffer.peekFirst().getProcessingTime()));
            }

            if (!packetsInBuffer.isEmpty()
                    && packetsInBuffer.peekFirst().getArrivalTime()
                                    + packetsInBuffer.peekFirst().getProcessingTime()
                            <= i) {
                LOGGER.info("Removing first element in buffer");
                packetsInBuffer.removeFirst();
            }

            LOGGER.info(
                    String.format(
                            "Packets in buffer after striping: %s", packetsInBuffer.toString()));

            for (int j = startSearchIndex; j < packets.length; j++) {
                if (packets[j].getArrivalTime().equals(i)) {
                    if (packetsInBuffer.size() < bufferSize) {
                        simulationResult[counter] = i;
                        packetsInBuffer.addLast(packets[j]);
                    } else {
                        simulationResult[counter] = -1;
                    }

                    counter++;
                } else {
                    startSearchIndex = j;
                    break;
                }
            }

            LOGGER.info(String.format("Packets in buffer: %s", packetsInBuffer.toString()));
        }

        return simulationResult;
    }

    public static void main(String[] args) throws FileNotFoundException {
        LOGGER.setLevel(LOGGER_LEVEL);

        String testFilesDir =
                "/home/felipe/Documents/uady/coursera/data-structures-statements-starters/week1_basic_data_structures/3_network_simulation/tests";
        String testFile = "/16";
        Scanner scanner = new Scanner(new File(testFilesDir + testFile));

        int bufferSize = scanner.nextInt();
        int noPackets = scanner.nextInt();
        Packet[] packets = new Packet[noPackets];

        for (int i = 0; i < noPackets; i++) {
            packets[i] = new Packet(scanner.nextInt(), scanner.nextInt());
        }

        scanner.close();

        LOGGER.info(String.format("Buffer size: %s", bufferSize));
        LOGGER.info(String.format("Number of packets: %s", noPackets));
        LOGGER.info(String.format("Packet array: %s", Arrays.toString(packets)));

        int[] result = simulate(bufferSize, packets);

        LOGGER.info(String.format("Result: %s", Arrays.toString(result)));
    }
}
