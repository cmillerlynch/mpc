package main;

/**
 * @author Conor Miller-Lynch
 *
 * A class representing a table for a multiple producer consumer system.
 */

public class Table {

    private String course;
    private Boolean isEmpty = true;
    private final Object obj = new Object();

    /**
     * Default constructor for Table.
     */

    public Table() {}

    /**
     * Returns true if the table is empty, otherwise false.
     * @return whether the table is empty
     */

    public Boolean getIsEmpty () { return isEmpty; }

    /**
     * Used to serve a course to the table. Sets isEmpty to false.
     * @param course the course being served
     */

    public void serve(String course) {
        synchronized (obj) {
            while (!isEmpty) {

                try {
                    obj.wait();
                } catch (InterruptedException ie) {
                    System.out.println("InterruptedException: " + ie.getMessage());
                }
            }
            isEmpty = false;
            obj.notifyAll();
            this.course = course;
        }
    }

    /**
     * Eat the course currently on the table. Sets isEmpty to true.
     * @return the course that was eaten
     */

    public String eat() {
        synchronized (obj) {
            while (isEmpty) {

                try {
                    obj.wait();
                } catch (InterruptedException ie) {
                    System.out.println("InterruptedException: " + ie.getMessage());
                }
            }
            isEmpty = true;
            obj.notifyAll();
            return course;
        }
    }

}