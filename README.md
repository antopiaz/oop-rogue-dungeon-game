[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/rH8BAy2h)
# Polymorphia Homework Sequence

Put all team members' names here:

    Names: Miles Zheng, Kevin Wang
    Java Version: 23
    Test Coverage Screenshot pasted below:
    ______________________________________
    Game Output pasted below (or linked):
    Test ran to produce the output below: <testname here>
    ______________________________________

# Homework 9: Decorating our Characters to Add New Behaviors

![Armor.png](images/Armor.png)![Cloaked.png](images/Cloaked.png)![Potion.png](images/Potion.png)

## Introduction

In this assignment, I’ve given you a Scenario written in Gherkin (Behavior Driven Design language) and you need to
implement it, using the Decorator pattern. And implement the new step definitions, so that the new BDD scenarios
run successfully.

I've created an IArtifact interface and made Food an implementer of it. Armor is also an implementer of IArtifact.
When you add your new artifact, it will probably also be an implementer of IArtifact. I did this so that we can add
various artifacts to Rooms without adding new collections to hold them. The Rooms now have a list of artifacts and we
can query if they are food or armor or whatever. In doing this, I've eliminated the FoodFactory object and moved those
methods to the ArtifactFactory.

To implement this functionality, you'll likely need to change/add methods to the following classes:

* Room
* Maze Builder
* Strategy object(s)

After implementing the Armored Characters, you must select another artifact to add to the game and implemented it using
the Decorator Pattern. Start with a BDD scenario that describes what you want to happen.
You are free to choose whatever feature you like. Here are some ideas (which you can use) to get your
creative juices flowing:

* Weapons or shields placed in the maze that could give a character an advantage in a fight (make a slight cost for
moving rooms while carrying these)
* An invisibility cloak that, when worn by the character, makes that character invisible to creatures. This cloak
  should decay with each turn before disappearing. In this case you'll have to undecorate your Character!
* A reincarnation potion that, when drunk by the character, allows them to be reborn after they die (with some initial
  health). Picking up this potion would cause the Character to be decorated and reincarnation would cause the Character
  to become undecorated.
* A magic wand that, when wielded by the character, transports them to a “safe” room – one with the least amount of
creatures in it.
* A neutron bomb that, when tossed into a neighboring room, kills all the characters in that room.
* A radiation device that doubles the health value of any one food item.
* A crystal ball that allows the character to see what’s in the adjacent rooms

START EARLY!

In this assignment, you will implement the Decorator pattern to extend the capabilities of our Characters.

## Adding Armor to the Game by using the Decorator Pattern

Use the Decorator Pattern to implement a Character that is armored. The Character becomes armored by choosing to
wear Armor if the Character is in the same room with the Armor. Once worn by the Character, the Armor should be
removed from the Room, just like we do when Food is eaten.

When worn, Armor reduces the damage caused in a fight by the value of the armor’s strength. It is heavy, though, and
incurs an additional cost when moving rooms specified by the movingCost.

Create an ArtifactFactory to create any items you need to place in the maze. For this homework, the ArtifactFactory must
at least create Armor and whatever else you add to the game. If a Fighter enters a room containing Armor then there
should be some circumstances where it will choose to wear the Armor. That’s up to you. I recommend having the Fighter
always move if a Demon is present and if not, then always wear the Armor if the Armor is present. You can decide, but
the following BDD scenario must pass. And you must implement the missing BDD steps:

```gherkin
  Scenario: Armored suits are available in the maze and worn by knights for extra protection during fights
Given a maze with the following rooms:
| Starting |
And a knight named Galahad in the Starting room with a fixed die of 4
And the Starting room contains the following artifacts:
| Type  | Name    | Health Value | Moving Cost | Defense Value |
| Armor | iron    | 0.0          | 0.3         | 2.0           |
| Food  | cupcake | 1.0          | 0.0         | 0.0           |

When the game plays a turn

Then Galahad picked up the Armor
And there is no armor in Starting room

Given a demon named Satan in the Starting room with a fixed die of 6
When the game plays a turn

Then a fight has occurred
    # Despite losing the fight by 2, the armor prevents the loss
    # But both fight each other, so they both lose double the default fight cost
And Galahad has lost 2 times the default fight cost in health
And Satan has lost 2 times the default fight cost in health


@Disabled
Scenario: Armored suits are heavy and incur extra health costs when moving
Given a maze with the following rooms:
| Starting |
| Other    |
And a knight named Galahad in the Starting room
And the Starting room contains the following artifacts:
| Type  | Name | Health Value | Defense Value | Moving Cost |
| Armor | iron | 0.0          | 2.0           | 0.3         |

When the game plays a turn

Then Galahad picked up the Armor
And there is no armor in Starting room

When the game plays a turn

Then Galahad is now in the Other room
And Galahad lost the default-moving cost plus 0.3 in health
```

These are the new step definitions that you’ll have to implement for this test:

```gherkin
And Galahad has lost 2 times the default fight cost in health
And Galahad lost the default-moving cost plus 0.3 in health
```

The step that checks to make sure Galahad is wearing the armor does this by checking the names of the characters.
It searches the name of the character for the string “Armor”. For this to be true, you’ll have to override getName()
on your new decorated class.

### Hint

Make sure your character can be decorated twice. Meaning, the character should be able to wear two suits of armor
or armor and drink a potion (if you implement the potion). Make sure you implement getters/setters for the instance
variables on Character and use them strictly to access those variables.

Also, use your debugger or your log statements, to make sure that decorating a character doesn't inadvertently clone
them. It's important that the decorated character replace the undecorated character in the room. To do this, you'll have
to implement a new method on the Room class.

## Rubric

**50 total points**:

* Implementing the Decorator Pattern for the Armored Character: 10 points
    * Behaves correctly for fighting
    * Behaves correctly for moving
    * Behaves correctly for eating
* Change at least one strategy so that a character will wear the Armor and become the Decorated Character.
  Make sure you update the characters in the Room: 10 points
    * Use a new method on CharacterFactory to create the new decorated character
* Implement the two step defs: 6 points
* Implement another feature: 24 points
    * New artifact: 2 points
    * New ArtifactFactor method: 2 points
    * Extend step def to place new artifacts in the maze: 2 points
    * New decorated clas with correct behavior: 6 points
    * Change to at least one strategy to "pick up" the artifact: 2 points
    * New BDD Scenario describing this new behavior and the step defs to implement it: 10 points

## Deductions

Here I list all the ways you can lose points on any programming assignment in this class, going forward.
You will lose 1 point for each of the following, up to a maximum of 10 points. Use this as a checklist before
submitting your code.

- [ ] Magic numbers or strings
- [ ] Poorly named variables, methods, classes, interfaces, etc.
- [ ] Poorly formatted code
- [ ] Breaking encapsulation (you might need to delete or modify a method to fix this. Pay special attention to
  the access modifiers of your methods and variables)
- [ ] Missing or incomplete README.md file (including missing names, Java version, test coverage screenshot, etc.)
- [ ] Missing or incomplete game output files (2 runs of the game)
- [ ] Each method that isn't covered by a test
- [ ] Variables/Methods of the Wrong Type -- instance vs class (static)
- [ ] Each class that isn't covered by a test

---

- [ ] Each method that is larger than 20 lines, if it tries to do more than one thing
- [ ] Each use of System.out.println in your main code – replace it with logging statements.
- [ ] Include citations (URLs or AI agent) of any code from external sources.

Do not change the spirit of any of the existing tests. You can add code to create objects and inject them into other
objects, but do not change the existing tests in any other way. You can add new tests if you want.

For all code submissions, the following will be required:

* The project builds with Gradle and runs successfully
* All BDD tests pass
* Adding any assumptions about your implementation in this README.md file. If anything
  isn't specified to your satisfaction in the homework description, that means you
  are free to decide for yourself. Just make sure to document your assumptions here.

This is a group assignment, as will be all the remaining homework assignments.
You will submit a URL to your GitHub Classroom repository.

# Submission and Grading

The submission will be a URL to the GitHub repository. The repository should contain well-structured OO Java
code for the Polymorphia simulation, the two captured text files with the program results requested,
and a README file that has the names of the team members, the Java version,
and any other comments on the work – including documenting any assumptions or interpretations of the problem.
Only one URL submission is required per team.

## Github Guidelines

All code submissions for this course will be managed through GitHub Classroom.
Since Homework 2 is a team assignment, only one repository submission is required per team. Hence, please do the
following:

* TEAM-MEMBER-1 generates the repository.
* TEAM-MEMBER-1 adds TEAM-MEMBER-2 as a collaborator with write permissions.

ONLY ONE of the team members submits the repository link in Canvas. You'll both get credit for the submission.

Please do not make the repositories public since this violates the Honor Code.
Please note that repositories not established using the GitHub Classroom link will not be considered valid for
submission.

Please contact the class staff if you encounter technical difficulties or have questions about the setup process.

## Overall Project Guidelines

Assignments will be accepted late for one day only with a penalty of 10%.
After that, assignments will be given a 0 and will not be graded.

Use office hours, e-mail, or Piazza to reach the class staff regarding homework/project questions or if you have issues
completing the assignment.

# Homework Sequence

This sequence of homework assignments is designed to
give you practice in OO design and implementation using
the four pillars of OO:

* Abstraction
* Polymorphism
* Inheritance
* Encapsulation

Along with the SOLID principles:

* Single Responsibility Principle
* Open for Extension; Closed for Modifications
* Liskov Substitution Principle (IS-A relationship)
* Interface Fidelity Principle
* Dependency Injection

Plus:

* DRY (Don't Repeat Yourself)
* KISS (Keep It Simple, Stupid)
* YAGNI (You Ain't Gonna Need It)
