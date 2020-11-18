
public class Attacker extends Thread {

    private int i;
    private Castle castle;

    private boolean isAvailable = false;

    // Object lock
    private static Armory armory = new Armory();

    // rAttack
    int rAttackNumber = (int) (Math.random() * 10) + 1;

    public Attacker(int i, Castle castle) {
        this.i = i;
        this.castle = castle;
        setName("Attacker " + i);
    }

    public void run() {

        raidArmory();

        boolean castleGateCheck1 = castle.checkGateOne(Thread.currentThread().getName(), rAttackNumber, isAvailable);

        if (castleGateCheck1) {
            System.out.println(Thread.currentThread().getName() + " is at gate 1");
        } else {
            castle.checkGateTwo(Thread.currentThread().getName(), rAttackNumber);
            System.out.println(Thread.currentThread().getName() + " is at gate 2");
        }

    }

    // Threads check the gate. They bring with them their stats
    // @param -Thread.currentThread().getName() Thread name
    // @param rAttackNumber - Random Attack Number

    private void raidArmory() {
        synchronized (armory) {

            // Attackers raid the armory

            try {

                Thread.sleep(randomSleep(Thread.currentThread().getName()));
                System.out.println(Thread.currentThread().getName() + " rAttckr_Attack: " + rAttackNumber + " weapon: "
                        + armory.randomWeapon());

                // try {
                // Thread.sleep(3000);
                // } catch (InterruptedException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }

            } catch (InterruptedException e) {
                System.out.println("I woke up earlier than I should");
            }

        }
    }

    private int randomSleep(String string) {
        int randomSleeper = (int) (Math.random() * 3000) + 2000;
        // System.out.println(string + " randomly napping " + randomSleeper + "ms");

        return randomSleeper;
    }

}
