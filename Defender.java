
public class Defender extends Thread {

    private int i;
    private Castle castle;

    private boolean isAvailable = false;

    // Object lockA
    private static Armory2 armory2 = new Armory2();

    // rAttack
    int rAttackNumber = (int) (Math.random() * 10) + 1;

    public Defender(int i, Castle castle) {
        this.i = i;
        this.castle = castle;
        setName("Defender " + i);
    }

    public void run() {
        // Defenders raid the armory
        // while (true) {
        raidArmory();

        boolean castleGateCheck = castle.def_checkGateOne(Thread.currentThread().getName(), rAttackNumber, isAvailable);

        if (castleGateCheck) {
            System.out.println(Thread.currentThread().getName() + " is at gate 1");
        } else {
            castle.def_checkGateTwo(Thread.currentThread().getName(), rAttackNumber);
            System.out.println(Thread.currentThread().getName() + " is at gate 2");
        }
        // }

    }

    private int randomSleep() {
        int randomSleeper = (int) (Math.random() * 3000) + 2000;

        // System.out.println(Thread.currentThread().getName() + " randomly napping" +
        // randomSleeper);

        return randomSleeper;
    }

    private void raidArmory() {
        synchronized (armory2) {

            try {
                Thread.sleep(randomSleep());
                System.out.println(Thread.currentThread().getName() + " rDef_Attack: " + rAttackNumber + " "
                        + armory2.randomWeapon());

            } catch (InterruptedException e) {
                System.out.println("I woke up earlier than I should");
            }

        }
    }

}
