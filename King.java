
public class King extends Thread {

    private Castle castle;
    private int i;
    private static MyObject lockA;

    public King(Castle castle) {
        this.castle = castle;

    }

    /*
     * King wants to check for opportunituy to escape. for that to happen attackers
     * need to not be present
     */

    public void run() {
        kingPackNow(i, lockA);
    }

    public static void kingPackNow(int i, MyObject lockA) {
        King.lockA = lockA;

        // static king sleep
        try {
            Thread.sleep(4000);
            if (i > 0) {
                System.out.println("An attacker have enetererd! ");
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
