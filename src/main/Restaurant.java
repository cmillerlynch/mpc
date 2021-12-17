package main;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Conor Miller-Lynch
 *
 * This class is a demonstration of a multiple producer consumer system. It requires the classes Table,
 * Waiter, and Customer to run.
 */


public class Restaurant {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter absolute file path, including extension:");
        try {
            String filename = bufferedReader.readLine();
            Scanner fileReader = new Scanner(new FileReader(filename));
            int numberOfWaiters = Integer.parseInt(fileReader.next());
            Waiter[] waiters = new Waiter[numberOfWaiters];

            for (int i = 0; i < numberOfWaiters; i++) {
                String waiterName = fileReader.next();
                int numberOfCustomers = fileReader.nextInt();
                String[][] courses = new String[numberOfCustomers][3];
                String[] customerNames = new String[numberOfCustomers];
                Table[] tables = new Table[numberOfCustomers];
                for (int j = 0; j < numberOfCustomers; j++) {

                    String customerName = fileReader.next();
                    customerNames[j] = customerName;

                    String appetizer = fileReader.next();
                    String main = fileReader.next();
                    String dessert = fileReader.next();

                    courses[j][0] = appetizer;
                    courses[j][1] = main;
                    courses[j][2] = dessert;

                    tables[j] = new Table();

                }
                waiters[i] = new Waiter( tables, waiterName, customerNames, courses);

            }

            for (int i = 0; i < numberOfWaiters; i++) {
                Thread t = new Thread(waiters[i]);
                t.start();
            }

        } catch (NoSuchElementException nsee) {
            System.out.println("NoSuchElementException: " + nsee.getMessage());
        } catch ( FileNotFoundException fnfe ){
            System.out.println("FileNotFoundException: " + fnfe.getMessage());
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        } catch ( IOException ioe ) {
            System.out.println("IOException: " + ioe.getMessage());
        }
    }

}