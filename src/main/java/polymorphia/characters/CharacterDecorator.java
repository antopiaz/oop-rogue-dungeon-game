package polymorphia.characters;

import java.util.Scanner;

public abstract class CharacterDecorator extends Character {
    protected Character decoratedCharacter;

    public CharacterDecorator(Character decoratedCharacter) {
        super(decoratedCharacter.getName(), decoratedCharacter.getHealth(), decoratedCharacter.getPlayStrategy());
        this.decoratedCharacter = decoratedCharacter;
    }

    @Override
    public abstract String getName();

    public String[] getAction(){
        return ((Player) decoratedCharacter).getAction();
    }

    public void doAction(){
        decoratedCharacter.doAction();
    }


}
