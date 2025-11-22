package polymorphia.characters;

import org.slf4j.Logger;

import polymorphia.Die;
import polymorphia.EventType;
import polymorphia.RandomDie;
import polymorphia.artifacts.IArtifact;
import polymorphia.maze.Room;
import polymorphia.observers.EventBus;
import polymorphia.strategy.PlayStrategy;

public abstract class Character {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Character.class);

    public static final Double HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME = 0.5;
    public static final Double HEALTH_LOST_IN_MOVING_ROOMS = 0.25;

    protected String name;
    private Double health;
    Die die;
    PlayStrategy strategy;

    private Room currentLocation;

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public Character(String name, Double health, PlayStrategy strategy) {
        this(name, health, RandomDie.sixSided(), strategy);
    }

    public Character(String name, Double initialHealth, Die die, PlayStrategy strategy) {
        this.name = name;
        this.health = initialHealth;
        this.die = die;
        this.strategy = strategy;
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

    public double getMovingCost() {
        return HEALTH_LOST_IN_MOVING_ROOMS;
    }

    public Boolean isAdventurer() {
        return true;
    }

    public Boolean isCreature() {
        return false;
    }

    public Boolean fight(Character foe) {
        Integer myRoll = getRoll();
        Integer foeRoll = foe.getRoll();
        logger.info(getName() + " is fighting " + foe);

        logger.info(getName() + " rolled " + myRoll);
        logger.info(foe + " rolled " + foeRoll);

        boolean won = false;
        if (myRoll > foeRoll) {
            won = true;
            foe.loseFightDamage(getDamageInflicted(myRoll, foeRoll));
        } else if (foeRoll > myRoll) {
            loseFightDamage(foe.getDamageReceived(myRoll, foeRoll));
        }

        loseHealth(Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);
        foe.loseHealth(Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);

        String eventMessage = String.format("%s fought %s and %s!", getName(), foe.getName(), won ? "won" : "lost");
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.FightOccurred, eventMessage);
        return won;
    }

    private Integer getRoll() {
        return die.roll();
    }

    public void doAction() {
        strategy.doAction(this, getCurrentLocation());
    }

    public void move(Room destinationRoom) {
        assert getCurrentLocation().hasNeighbor(destinationRoom);
        String eventMessage = String.format("%s moved from %s to %s", getName(), destinationRoom.getName(), getCurrentLocation().getName());
        logger.info(eventMessage);
        EventBus.INSTANCE.broadcast(EventType.MovedRooms, eventMessage);
        destinationRoom.enter(this);
        loseHealth(getMovingCost());
    }

    public Die getDie() {
        return die;
    }

    public void setDie(Die die) {
        this.die = die;
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
