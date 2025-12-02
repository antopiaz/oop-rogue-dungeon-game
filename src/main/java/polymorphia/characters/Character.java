package polymorphia.characters;

import org.slf4j.Logger;

import polymorphia.EventType;
import polymorphia.artifacts.IArtifact;
import polymorphia.maze.Room;
import polymorphia.observers.EventBus;
import polymorphia.strategy.PlayStrategy;
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
            EventBus.INSTANCE.broadcast(EventType.Death, eventMessage);
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

    public double getDamageInflicted(double myRoll, double foeRoll) {
        return myRoll - foeRoll;
    }

    public double getDamageReceived(double myRoll, double foeRoll) {
        return foeRoll - myRoll;
    }


    public Boolean isAdventurer() {
        return true;
    }

    public Boolean isCreature() {
        return false;
    }

    public Boolean fight(Character foe) {
        Integer myFightAction = getFightAction(); //TODO
        Integer foeFightAction = foe.getFightAction();
        logger.info(getName() + " is fighting " + foe);

        logger.info(getName() + " chose " + myFightAction);
        logger.info(foe + " chose " + foeFightAction);

        boolean won = false;
        if (myFightAction > foeFightAction || (foeFightAction==2 && myFightAction == 0)) {
            won = true;
            foe.loseFightDamage(2.0);
        } else if (foeFightAction > myFightAction || (myFightAction==2 && foeFightAction == 0)) {
            loseFightDamage(2.0);
        }

        String eventMessage = String.format("%s fought %s and %s!", getName(), foe.getName(), won ? "won" : "lost");
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.FightOccurred, eventMessage);
        return won;
    }

    public int getFightAction() {
        return random.nextInt(3);
    }

    public void doAction() {
        strategy.doAction(this, getCurrentLocation());
    }

    public void move(Room destinationRoom) {
        assert getCurrentLocation().hasNeighbor(destinationRoom); // Causes assertion error in Cucumuber tests
        String eventMessage = String.format("%s moved from %s to %s", getName(), destinationRoom.getName(), getCurrentLocation().getName());
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.MovedRooms, eventMessage);
        destinationRoom.enter(this);
    }

    public void eat(IArtifact food) {
        this.gainHealth(food.getHealthValue());
        String eventMessage = String.format("%s ate %s", getName(), food.getName());
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.SomethingEaten, eventMessage);
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
