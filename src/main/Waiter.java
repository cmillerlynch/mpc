package main;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Conor Miller-Lynch
 *
 * A class representing a waiter for a multiple producer consumer system.
 */

public class Waiter implements Runnable {

    private final static int MAX_WAITER_MILLIS = 4000;
    private final static int N_COURSES = 3;

    private final Table[] tables;
    private final String waiterName;
    private final String[] customerNames;
    private final String[][] courses;
    private final int[] currentCourse;
    private int totalCoursesServed = 0;

    /**
     * Default constructor for Waiter class.
     * @param tables tables the waiter serves
     * @param waiterName name of the waiter
     * @param customerNames names of the customers
     * @param courses courses for each customer
     */

    public Waiter( Table[] tables, String waiterName, String[] customerNames, String[][] courses) {
        this.tables = tables;
        this.waiterName = waiterName;
        this.customerNames = customerNames;
        this.courses = courses;
        currentCourse = new int[customerNames.length];
        Arrays.fill(currentCourse, 0);
    }

    public void run() {

        Random random = new Random();

        for ( int i = 0; i < customerNames.length; i++) {
            Thread t = new Thread (new Customer(tables[i], customerNames[i]));
            t.start();
        }

        while (totalCoursesServed < N_COURSES * customerNames.length) {
            for (int i = 0; i < customerNames.length; i++) {
                if (tables[i].getIsEmpty() && currentCourse[i] < N_COURSES) {
                    System.out.println(waiterName + " serves " + customerNames[i] + " " + courses[i][currentCourse[i]]);
                    tables[i].serve(courses[i][currentCourse[i]]);
                    currentCourse[i]++;
                    totalCoursesServed++;
                    try {
                        Thread.sleep(random.nextInt(MAX_WAITER_MILLIS));
                    } catch (InterruptedException ie) {
                        System.out.println("InterruptedException " + ie.getMessage());
                    }
                }
            }
        }
        for (int i = 0; i < customerNames.length; i++) {
            tables[i].serve("Done");
        }

    }

}
