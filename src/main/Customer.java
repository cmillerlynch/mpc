package main;

import java.util.Random;

/**
 * @author Conor Miller-Lynch
 *
 * A class representing a customer for a multiple producer consumer system.
 */

public class Customer implements Runnable {

    private final static int MAX_CUSTOMER_MILLIS = 4000;
    private final Table table;
    private final String customerName;

    /**
     * Constructor for Customer class.
     * @param table The customer's table
     * @param customerName The customer's name
     */

    public Customer( Table table, String customerName) {
        this.table = table;
        this.customerName = customerName;
    }

    public void run() {
        Random random = new Random();

        String currentCourse = table.eat();

        while(!currentCourse.equals("Done")) {
            System.out.println(customerName + " is eating " + currentCourse);
            try {
                Thread.sleep(random.nextInt(MAX_CUSTOMER_MILLIS));
            } catch (InterruptedException ie) {
                System.out.println("InterruptedException: " + ie.getMessage());
            }
            currentCourse = table.eat();
        }
    }

}
