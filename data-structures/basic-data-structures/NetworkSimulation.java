import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkSimulation {
    private static class Request {
        private int arrivalTime;
        private int processTime;

        public Request(int arrivalTime, int processTime) {
            this.arrivalTime = arrivalTime;
            this.processTime = processTime;
        }

        public int getArrivalTime() {
            return arrivalTime;
        }

        public int getProcessTime() {
            return processTime;
        }
    }

    private static class Response {
        private boolean dropped;
        private int startTime;

        public Response(boolean dropped, int startTime) {
            this.dropped = dropped;
            this.startTime = startTime;
        }

        public boolean isDropped() {
            return dropped;
        }

        public int getStartTime() {
            return startTime;
        }
    }

    private static class Buffer {
        private int size;
        private ArrayList<Integer> finishTime;

        public Buffer(int size) {
            this.size = size;
            this.finishTime = new ArrayList<Integer>();
        }

        public Response Process(Request request) {
            while (!finishTime.isEmpty()) {
                if (finishTime.get(0) <= request.getArrivalTime()) finishTime.remove(0);
                else break;
            }

            if (finishTime.size() == size) return new Response(true, -1);

            if (finishTime.isEmpty()) {
                finishTime.add(request.getArrivalTime() + request.getProcessTime());
                return new Response(false, request.getArrivalTime());
            }

            int lastElement = finishTime.get(finishTime.size() - 1);
            finishTime.add(lastElement + request.getProcessTime());
            return new Response(false, lastElement);
        }
    }

    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requestsCount = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requestsCount; ++i) {
            int arrivalTime = scanner.nextInt();
            int processTime = scanner.nextInt();
            requests.add(new Request(arrivalTime, processTime));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.isDropped()) {
                System.out.println(-1);
            } else {
                System.out.println(response.getStartTime());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int bufferMaxSize = scanner.nextInt();
        Buffer buffer = new Buffer(bufferMaxSize);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
