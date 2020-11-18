
public class Defender extends Thread {

    private int i;
    private Castle castle;

    private boolean isAvailable = false;

    // Object lock
    private static Object armory2 = new Object();

    // rAttack
    int rAttackNumber = (int) (Math.random() * 10) + 1;

    public Defender(int i, Castle castle) {
        this.i = i;
        this.castle = castle;
        setName("Defender " + i);
    }

    public void run() {
        // Defenders raid the armory
        try {
            Thread.sleep(randomSleep());
        } catch (InterruptedException e) {
            System.out.println("I woke up earlier than I should");
        }

        raidArmory();

        boolean castleGateCheck = castle.def_checkGateOne(Thread.currentThread().getName(), rAttackNumber, isAvailable);

        if (castleGateCheck) {
            System.out.println(Thread.currentThread().getName() + " Done with gate 1");
        } else {
            castle.def_checkGateTwo(Thread.currentThread().getName(), rAttackNumber);
            System.out.println(Thread.currentThread().getName() + " Done with gate 2");
        }

    }

    private int randomSleep() {
        int randomSleeper = (int) (Math.random() * 3000) + 1;
        System.out.println(Thread.currentThread().getName() + " randomly napping" + randomSleeper);

        return randomSleeper;
    }

    private void raidArmory() {
        synchronized (armory2) {

            System.out.println(Thread.currentThread().getName() + " rDef_Attack: " + rAttackNumber);

        }
    }

}
