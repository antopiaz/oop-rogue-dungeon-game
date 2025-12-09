package dungeon.characters;

import org.slf4j.Logger;

import dungeon.maze.DirectionType;
import dungeon.artifacts.IArtifact;
import dungeon.maze.Room;
import dungeon.strategy.PlayStrategy;
import java.util.Random;

public abstract class Character {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Character.class);

    protected String name;
    private Double health;
    PlayStrategy strategy;

    private Room currentLocation;

    Random random = new Random();

    public Character(String name, Double health, PlayStrategy playStrategy) {
        this.name = name;
        this.health = health;
        this.strategy = playStrategy;
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public void enterRoom(Room room) {
        if (getCurrentLocation() != null) {
            if (getCurrentLocation().equals(room)) {
                return;
            }
            getCurrentLocation().remove(this);
        }
        this.currentLocation = room;
    }

    @Override
    public String toString() {
        String formattedHealth = String.format("%.2f", getHealth());
        return getName() + "(" + formattedHealth + ")";
    }

    void loseHealth(Double healthPoints) {
        if (health <= 0) {
            return;
        }     // already dead, probably called for mandatory health loss for having a fight

        health -= healthPoints;
        if (health <= 0) {
            String eventMessage = name + " just died!";
            logger.info(eventMessage);
        }
    }

    void gainHealth(Double healthPoints) {
        health += healthPoints;
    }

    public Double getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public Boolean isAlive() {
        return getHealth() > 0;
    }

    void loseFightDamage(double fightDamage) {
        loseHealth(fightDamage);
    }

    public double dealFightDamage(double baseDamage) {
        return baseDamage;
    }

    public Boolean isAdventurer() {
        return true;
    }

    public Boolean isCreature() {
        return false;
    }

    public Boolean fight(Character self, Character foe, int myFightAction, int foeFightAction) {
        logger.info(self.getName() + " is fighting " + foe);

        logger.info(self.getName() + " chose " + myFightAction);
        logger.info(foe + " chose " + foeFightAction);
        double finalDamage;
        boolean won = false;
        if ((myFightAction==1 && foeFightAction == 0) || (myFightAction==2 && foeFightAction == 1)|| ( myFightAction == 0 && foeFightAction==2)) {
            won = true;
            finalDamage = self.dealFightDamage(3.0);
            foe.loseFightDamage(finalDamage);
        } else if ((myFightAction==0 && foeFightAction == 1) || (myFightAction==1 && foeFightAction == 2)|| ( myFightAction == 2 && foeFightAction==0)) {
            finalDamage = foe.dealFightDamage(3.0);
            self.loseFightDamage(finalDamage);
        }
        else if(myFightAction==foeFightAction) {
            self.loseFightDamage(1.5);
            foe.loseFightDamage(1.5);
        }
        String eventMessage = String.format("%s fought %s and %s!", getName(), foe.getName(), won ? "won" : "lost");
        logger.info(eventMessage);
        return won;
    }

    public int getFightAction() {
        return strategy.getFightAction(this);
    }


    public void doAction() {
        strategy.doAction(this, getCurrentLocation());
    }

    public void move(DirectionType direction) {
        assert (getCurrentLocation().hasNeighbor(direction));
        Room destinationRoom = getCurrentLocation().getNeighbor(direction);
        String eventMessage = String.format("%s moved %s from %s to %s", getName(), direction, getCurrentLocation().getName(), destinationRoom.getName());
        logger.info(eventMessage);
        destinationRoom.enter(this);
    }

    public void eat(IArtifact food) {
        this.gainHealth(food.getValue());
        String eventMessage = String.format("%s ate %s", getName(), food.getName());
        logger.info(eventMessage);
    }

    public void obtain(IArtifact treasure) {
        String eventMessage = String.format("%s picked up %s", getName(), treasure.getName());
        logger.info(eventMessage);
    }

    public void wear(IArtifact armor) {
        Character armoredCharacter = CharacterFactory.createArmoredCharacter(this, armor);
        currentLocation.alterCharacter(this, armoredCharacter);
    }

    public void equip(IArtifact weapon) {
        Character weaponizedCharacter = CharacterFactory.createWeaponizedCharacter(this, weapon);
        currentLocation.alterCharacter(this, weaponizedCharacter);
    }

    public void setStrategy(PlayStrategy strategy) {
        this.strategy = strategy;
    }

    public PlayStrategy getPlayStrategy() {
        return strategy;
    }
}
