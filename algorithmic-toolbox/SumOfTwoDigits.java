import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SumOfTwoDigits {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();

        String[] numbersAsString = input.split(" +");

        int firstNumber = Integer.parseInt(numbersAsString[0]);
        int secondNumber = Integer.parseInt(numbersAsString[1]);

        int res = firstNumber + secondNumber;

        System.out.println(res);
    }
}
