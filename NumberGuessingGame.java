import java.util.Random;
import java.util.Scanner;
public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        Random random= new Random();
        int actual= random.nextInt(99) + 1;
        int noofattempts= 10;
        for (int i=1; i<= noofattempts; i++ ) {
            System.out.print("Enter your guess: ");
            int guess= sc.nextInt();
            if (actual == guess) {
                int score= noofattempts - i;
                System.out.println("WELL DONE!! Your Score: " +score);
                return;
            }
            else {
                int attemptsleft= noofattempts - i;
                System.out.println("OOPS!! Wrong Guess!! RETRY!! You have " +attemptsleft+ " attempts left");
                if (guess < actual) {
                    System.out.println("HINT: The correct answer is larger than your guess!!");
                }
                else {
                    System.out.println("HINT: The correct answer is smaller than your guess!!");
                }
                if (attemptsleft == 0) {
                    System.out.println("SORRY!! You have 0 attempts left. The correct answer is: " +actual);
                }
            }
        }

    
    }
}


        