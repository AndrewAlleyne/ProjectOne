import java.util.Vector;

public class Castle {

    private Gate gateOne;
    private Gate gateTwo;
    private static MyObject lock = new MyObject();

    // Active attackers on the playing field
    private static int numAttackers = 0;

    // Active defenders on the playing field
    private int numDefenders = 0;

    // Active waitingAttakcers Store Thread name and the object its holding onto
    Vector activeWaitingAttakcers = new Vector<>();

    // activeWaitingDefenders Store Thread name and the object its holding onto
    Vector activeWaitingDefenders = new Vector<>();

    // activeWaitingDefenders Store Thread name and the object its holding onto
    Vector remainingWaitingAttakcers = new Vector<>();

    // totalAttackerDamage
    static int totalAttackerDamage;

    // totalDefendereDamage
    static int totalDefendereDamage;

    public Castle(Gate gateOne, Gate gateTwo) {
        this.gateOne = gateOne;
        this.gateTwo = gateTwo;
    }

    public boolean checkGateOne(String name, int rAttackNumber, boolean isAvailable) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lock) {
            int gateOneSpace = gateOne.gateSpace(name);
            if (gateOneSpace > 0) {
                // grab rAttackNumber

                totalAttackerDamage += rAttackNumber;

                activeWaitingAttakcers.addElement(lock);
                if (activeWaitingAttakcers.size() <= 3) {
                    while (true) {
                        try {
                            System.out.println(name + " is waiting!");
                            remainingWaitingAttakcers.addElement(lock);
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            continue;
                        }
                    }

                }
                return true;
            } else {
                System.out.println(name + " didnt make it to gate 1");
                isAvailable = true;
                return false;
            }

        }

    }

    public synchronized void checkGateTwo(String name, int rAttackNumber) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lock) {
            int gateTwoSpace = gateTwo.gateSpace(name);

            if (gateTwoSpace > 0) {
                totalAttackerDamage += rAttackNumber;

                System.out.println(name + " is at gate2");
                activeWaitingAttakcers.addElement(lock);

                if (activeWaitingAttakcers.size() == 6) {

                    activeWaitingAttakcers.notifyAll();
                    System.out.println();
                    System.out.println("======= Attackers sum up to : ======= " + totalAttackerDamage);
                    System.out.println();

                }

            } else {
                System.out.println("didnt make it to gate 2");
            }

        }

    }

    public boolean def_checkGateOne(String name, int rAttackNumber, boolean isAvailable) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lock) {
            int gateOneSpace = gateOne.gateSpace2(name);

            if (gateOneSpace > 0) {
                // grab rAttackNumber

                totalDefendereDamage += rAttackNumber;

                System.out.println(name + " is at gate1");
                activeWaitingDefenders.addElement(lock);

                if (activeWaitingDefenders.size() == 3) {

                    while (true) {
                        try {
                            System.out.println(name + " is waiting!");
                            lock.wait();
                            break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            continue;
                        }
                    }

                }
                return true;
            } else {
                System.out.println(name + " didnt make it to gate 1");
                isAvailable = true;
                return false;
            }

        }

    }

    public synchronized void def_checkGateTwo(String name, int rAttackNumber) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lock) {
            int gateTwoSpace = gateTwo.gateSpace2(name);

            if (gateTwoSpace > 0) {
                System.out.println("activeDefQueue " + activeWaitingDefenders.size());

                totalDefendereDamage += rAttackNumber;

                System.out.println(name + " is at gate2");
                activeWaitingDefenders.addElement(lock);

                if (activeWaitingDefenders.size() == 6) {
                    System.out.println(name + " notifying all Threads!");
                    lock.notifyAll();
                    System.out.println("======= Defender sum up to : ======= " + totalDefendereDamage);
                }

            } else {
                System.out.println(name + " didnt make it to gate 2");
            }

        }

    }

}
