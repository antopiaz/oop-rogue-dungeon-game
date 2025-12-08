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

    @Override
    public Double getHealth(){
        return decoratedCharacter.getHealth();
    }

    public void doAction(){
        decoratedCharacter.doAction();
    }


}
