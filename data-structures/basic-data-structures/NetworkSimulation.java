import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkSimulation {
    private static final Logger LOGGER = Logger.getLogger(NetworkSimulation.class.getName());
    private static final Level LOGGER_LEVEL = Level.OFF;

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

        public Integer getProcessingTime() {
            return processingTime;
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

    private static List<Integer> simulate(int bufferSize, Packet[] packets) {
        List<Integer> simulationResults = new ArrayList<>(packets.length);

        Deque<Packet> packetsInBuffer = new LinkedList<>();

        int processedTime = 0;
        int processingTimeSum = 0;
        int startSearchAt = 0;
        int currentTime = 0;
        while (simulationResults.size() < packets.length) {
            if (!packetsInBuffer.isEmpty()) {
                Packet firstPacket = packetsInBuffer.peekFirst();

                if (firstPacket.getProcessingTime().equals(processedTime)) {
                    processedTime = 0;
                    packetsInBuffer.removeFirst();
                    processingTimeSum -= firstPacket.getProcessingTime();
                }
            } else {
                processedTime = 0;
            }

            for (int j = startSearchAt; j < packets.length; j++) {
                Packet packet = packets[j];

                if (packet.getArrivalTime() > currentTime) {
                    startSearchAt = j;
                    break;
                }

                if (packetsInBuffer.size() < bufferSize) {
                    if (packet.getProcessingTime() > 0) packetsInBuffer.addLast(packet);
                    simulationResults.add(currentTime + processingTimeSum - processedTime);
                    processingTimeSum += packet.getProcessingTime();
                } else {
                    simulationResults.add(-1);
                }
            }

            processedTime++;
            currentTime++;

            LOGGER.info(String.format("Time: %s", currentTime - 1));
            LOGGER.info(String.format("Packets in buffer: %s", packetsInBuffer.toString()));
            LOGGER.info(String.format("Processed time: %s", processedTime - 1));
            LOGGER.info(String.format("Simulation results: %s", simulationResults.toString()));
        }

        return simulationResults;
    }

    public static void main(String[] args) throws FileNotFoundException {
        LOGGER.setLevel(LOGGER_LEVEL);

        String testFilesDir =
                "/home/felipe/Documents/uady/coursera/data-structures-statements-starters/week1_basic_data_structures/3_network_simulation/tests";
        String testFile = "/20";
        Scanner scanner = new Scanner(new File(testFilesDir + testFile));

        // Scanner scanner = new Scanner(System.in);

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

        List<Integer> result = simulate(bufferSize, packets);

        LOGGER.info(String.format("Result: %s", result.toString()));

        result.forEach(p -> System.out.println(p));
    }
}
