public class Armory2 {

    public String randomWeapon() {
        int weaponChoice = (int) (Math.random() * 3);

        String[] weapons = { "Knife", "Sword", "Dorans Ring" };

        return weapons[weaponChoice];
    }

}