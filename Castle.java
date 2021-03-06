import java.util.Vector;

public class Castle {

    private Gate gateOne;
    private Gate gateTwo;
    private static MyObject lockA = new MyObject();
    // private static MyObject lockA = new MyObject();

    // Active attackers on the playing field
    private static int numAttackers = 0;

    // Active defenders on the playing field
    private int numDefenders = 0;

    // Active waitingAttakcers Store Thread name and the object its holding onto
    Vector activeWaitingAttakcers = new Vector<>();

    // activeWaitingDefenders Store Thread name and the object its holding onto
    Vector activeWaitingDefenders = new Vector<>();

    // remainingWaitingAttakcers Store Thread name and the object its holding onto
    Vector remainingWaitingAttakcers = new Vector<>();

    // remainingWaitingDefenders Store Thread name and the object its holding onto
    Vector remainingWaitingDefenders = new Vector<>();

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

        // gate#Space computes remaining spaces at gate.
        synchronized (lockA) {

            int gateOneSpace = gateOne.gateSpace(name);
            if (gateOneSpace > 0) {

                // grab total rAttackNumber
                totalAttackerDamage += rAttackNumber;

                // track size of numbers of activeWaitingAttakcers threads using object
                activeWaitingAttakcers.addElement(lockA);

                // TODO: do i really need this function
                if (activeWaitingAttakcers.size() <= 3) {
                    while (true) {
                        try {

                            System.out.println(
                                    name + " is waiting at the gate 1 for the last attacker thread to arrive!");

                            lockA.wait();

                            System.err.println(name + " RELEASED! ");

                            break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            continue;
                        }
                    }
                }
                return true;
            } else {
                System.out.println(name + " ==> Checking gate 2");

                // Do not need this as yet remove !
                // isAvailable = true;
                return false;
            }

        }

    }

    public void checkGateTwo(String name, int rAttackNumber) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lockA) {
            int gateTwoSpace = gateTwo.gateSpace(name);

            if (gateTwoSpace > 0) {
                totalAttackerDamage += rAttackNumber;

                activeWaitingAttakcers.addElement(lockA);

                if (activeWaitingAttakcers.size() == 5) {

                    while (true) {
                        try {

                            System.out
                                    .println(name + " is waiting at the gate2 for the last attacker thread to arrive!");

                            lockA.wait();
                            System.err.println(name + " RELEASED! ");

                            break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            continue;
                        }
                    }

                } else if (activeWaitingDefenders.size() == 6 && activeWaitingAttakcers.size() == 6) {
                    System.out.println();

                    System.out.println(name + " Are there enough defenders?! We are ready to attack");
                    System.out.println("======= Attackers sum up to : ======= " + totalAttackerDamage);

                    System.out.println();

                    System.out.println(name + " is at gate 2");
                    lockA.notifyAll();

                }

            } else {
                System.out.println("didnt make it to gate 2. Waiting for available space to proceed! ");

                // I need to deal with the incoming threads.
                remainingWaitingAttakcers.addElement(lockA);

                // 1: Create a function that if remaining attackers are == 6
                // 2: then we bring them back into the waiting queue.

            }

        }

    }

    public boolean def_checkGateOne(String name, int rAttackNumber, boolean isAvailable) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lockA) {
            int gateOneSpace = gateOne.gateSpace2(name);

            if (gateOneSpace > 0) {
                // grab rAttackNumber

                totalDefendereDamage += rAttackNumber;

                activeWaitingDefenders.addElement(lockA);

                if (activeWaitingDefenders.size() <= 3) {

                    while (true) {
                        try {
                            System.out.println(
                                    name + " is waiting at the gate 1 for the last defender thread to arrive!");
                            lockA.wait();
                            System.err.println(name + " RELEASED! ");
                            break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            continue;
                        }
                    }

                }
                return true;
            } else {
                System.out.println(name + " ==> Checking gate 2");
                // isAvailable = true;
                return false;
            }

        }

    }

    public void def_checkGateTwo(String name, int rAttackNumber) {
        // We have two gates in the castle each with 3 available spaces for attackers.
        // We need to fill the first gate with exactly 3 memember.
        // If gateOne is filled we check on gateTwo.
        // Once gate two is filled we place our remainingAttackers in the waiting queue
        // Build it similar to rwcv

        synchronized (lockA) {
            int gateTwoSpace = gateTwo.gateSpace2(name);

            if (gateTwoSpace > 0) {
                // total defender damage
                totalDefendereDamage += rAttackNumber;

                activeWaitingDefenders.addElement(lockA);

                if (activeWaitingDefenders.size() == 5) {
                    while (true) {
                        try {

                            System.out
                                    .println(name + " is waiting at the gate2 for the last defender thread to arrive!");

                            lockA.wait();
                            System.err.println(name + " RELEASED! ");

                            break;
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            continue;
                        }
                    }

                } else if (activeWaitingDefenders.size() == 6 && activeWaitingAttakcers.size() == 6) {
                    System.out.println();

                    System.out.println(name + " Are there enough defenders?! We are ready to attack");
                    System.out.println("======= Defenders sum up to : ======= " + totalDefendereDamage);

                    System.out.println();

                    System.out.println(name + " is at gate 2");
                    lockA.notifyAll();

                }
                // 1: Should put data structure to record gate assignment

            } else {
                System.out.println("didnt make it to gate 2. Waiting for available space to proceed! ");

                // I need to deal with the incoming threads.
                remainingWaitingDefenders.addElement(lockA);

                // 1: Create a function that if remaining attackers are == 6
                // 2: then we bring them back into the waiting queue.
            }

        }

    }

}
