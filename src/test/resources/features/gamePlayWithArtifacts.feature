#Feature: Game play with artifacts other than food
#  As a game player
#  I want to play a game with a artifacts in the maze
#  So that I have a richer set of choices
#
#  Scenario: Armored suits are available in the maze and worn by knights for extra protection during fights
#    Given a maze with the following rooms:
#      | Starting |
#    And a knight named Galahad in the Starting room with a fixed die of 4
#    And the Starting room contains the following artifacts:
#      | Type  | Name    | Health Value | Moving Cost | Value |
#      | Armor | iron    | 0.0          | 0.3         | 2.0   |
#      | Food  | cupcake | 1.0          | 0.0         | 0.0   |
#
#    When the game plays a turn
#
#    Then Galahad picked up the Armor
#    And there is no armor in Starting room
#
#    Given a demon named Satan in the Starting room with a fixed die of 6
#    When the game plays a turn
#
#    Then a fight has occurred
#    # Despite losing the fight by 2, the armor prevents the loss
#    # But both fight each other, so they both lose double the default fight cost
#    And Galahad has lost 2 times the default fight cost in health
#    And Satan has lost 2 times the default fight cost in health
#
#
#  Scenario: Armored suits are heavy and incur extra health costs when moving
#    Given a fully-connected maze with the following rooms:
#      | Starting |
#      | Other    |
#    And a knight named Galahad in the Starting room
#    And the Starting room contains the following artifacts:
#      | Type  | Name | Health Value | Value | Moving Cost |
#      | Armor | iron | 0.0          | 2.0   | 0.3         |
#
#    When the game plays a turn
#
#    Then Galahad picked up the Armor
#    And there is no armor in Starting room
#
#    When the game plays a turn
#
#    Then Galahad is now in the Other room
#    And Galahad lost the default-moving cost plus 0.3 in health
#
#
#  Scenario: Knight wears two suits of armor to get even more protection
#    Given a maze with the following rooms:
#      | Starting |
#    And a knight named Galahad in the Starting room with a fixed die of 1
#    And the Starting room contains the following artifacts:
#      | Type  | Name    | Health Value | Moving Cost | Value |
#      | Armor | iron    | 0.0          | 0.3         | 2.0   |
#      | Armor | mithril | 0.0          | 0.1         | 3.0   |
#
#    When the game plays a turn
#
#    Then Galahad picked up the Armor
#
#    When the game plays a turn
#    Then Galahad picked up the Armor
#    # Now Galahad is wearing two suits of armor
#    Then there is no armor in Starting room
#
#    Given a demon named Satan in the Starting room with a fixed die of 6
#    When the game plays a turn
#
#    Then a fight has occurred
#    # Despite losing the fight by 2, the armor prevents the loss
#    # But both fight each other, so they both lose double the default fight cost
#    And Galahad has lost 2 times the default fight cost in health
#    And Satan has lost 2 times the default fight cost in health
#
#  Scenario: Weapons are available in the maze and used by knights for extra damage during fights
#    Given a maze with the following rooms:
#      | Starting |
#    And a knight named Galahad in the Starting room with a fixed die of 6
#    And the Starting room contains the following artifacts:
#      | Type   | Name    | Health Value | Value | Moving Cost |
#      | Weapon | sword   | 0.0          | 2.0   | 0.3         |
#      | Food   | cupcake | 1.0          | 0.0   | 0.0         |
#
#    When the game plays a turn
#
#    Then Galahad picked up the Weapon
#    And there is no weapon in Starting room
#
#    Given a demon named Satan in the Starting room with a fixed die of 5
#    When the game plays a turn
#
#    Then a fight has occurred
#    # Both fight each other, so they both lose double the default fight cost
#    # Satan lost the fight by 1, so Satan takes 2.0 damage + an additional 4.0 from Galahad's sword for both fights
#    And Galahad has lost 2 times the default fight cost in health
#    And Satan has lost 2 times the default fight cost plus 6.0 in health
#
#  Scenario: Weapons are heavy and incur extra health costs when moving
#    Given a maze with the following rooms:
#      | Starting |
#      | Other    |
#    And a knight named Galahad in the Starting room
#    And the Starting room contains the following artifacts:
#      | Type   | Name  | Health Value | Value | Moving Cost |
#      | Weapon | sword | 0.0          | 2.0   | 0.2         |
#
#    When the game plays a turn
#
#    Then Galahad picked up the Weapon
#    And there is no weapon in Starting room
#
#    When the game plays a turn
#
#    Then Galahad is now in the Other room
#    And Galahad lost the default-moving cost plus 0.2 in health
#
#
#  Scenario: Knight wears suit of armor and holds a weapon to become stronger
#    Given a maze with the following rooms:
#      | Starting |
#    And a knight named Galahad in the Starting room with a fixed die of 6
#    And the Starting room contains the following artifacts:
#      | Type   | Name    | Health Value | Value       | Moving Cost |
#      | Armor  | mithril | 0.0          | 3.0         | 0.3         |
#      | Weapon | sword   | 0.0          | 2.0         | 0.1         |
#
#    When the game plays a turn
#
#    Then Galahad picked up the Weapon
#
#    When the game plays a turn
#
#    Then Galahad picked up the Armor
#    Then there is no armor in Starting room
#    Then there is no weapon in Starting room
#
#    Given a demon named Satan in the Starting room with a fixed die of 5
#    When the game plays a turn
#
#    Then a fight has occurred
#    # Despite losing the fight by 2, the armor prevents the loss
#    # But both fight each other, so they both lose double the default fight cost
#    And Galahad has lost 2 times the default fight cost in health
#    And Satan has lost 2 times the default fight cost plus 4.0 in health
