package polymorphia.characters;

public abstract class CharacterDecorator extends Character {
    protected Character decoratedCharacter;

    public CharacterDecorator(Character decoratedCharacter) {
        super(decoratedCharacter.getName(), decoratedCharacter.getHealth(), decoratedCharacter.getPlayStrategy());
        this.decoratedCharacter = decoratedCharacter;
    }

    @Override
    public abstract String getName();

}
