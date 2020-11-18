/**
 * Author: Andrew Alleyne Synchrnization : Long live the king projectOne
 */
public class projectOne {
    public static void main(String[] args) {

        int playerSpace = 3;
        int castleGates = 2;

        Gate gateOne = new Gate(playerSpace, castleGates);
        Gate gateTwo = new Gate(playerSpace, castleGates);

        Castle castle = new Castle(gateOne, gateTwo);
        // The king has access to the castle and will often periodically check to see if
        // he can escape
        // @param
        King king = new King(castle);

        for (int i = 0; i < 12; i++) {
            /// Each attacker knows themself,has access to the Castle
            // @param i - Thread identity
            // @param castle
            Attacker attacker = new Attacker(i, castle);
            attacker.start();
        }

        for (int i = 0; i < 12; i++) {
            // Each defender knows themself,has access to the Castle
            // @param i - Thread identity
            // @param castle
            Defender defender = new Defender(i, castle);
            defender.start();
        }

    }
}