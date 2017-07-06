Feature: Users are able to send their multiplication
  attempts, which may be correct or not. When users
  send a correct attempt, they get a response indicating
  that the result is the right one. Also, they get points
  and potentially some badges when they are right, so they
  get motivation to come back and keep playing. Badges are
  won for the first right attempt and when the user gets 100,
  500 and 999 points respectively. If users send a wrong
  attempt, they don't get any point or badge.

  Scenario: The user sends a first right attempt and gets a badge
    When the user john_snow sends 1 right attempts
    Then the user gets a response indicating the attempt is right
    And the user gets 10 points for the attempt
    And the user gets the FIRST_WON badge

  Scenario: The user sends a second right attempt and gets points only
    Given the user john_snow sends 1 right attempts
    And the user gets the FIRST_WON badge
    When the user john_snow sends 1 right attempts
    Then the user gets a response indicating the attempt is right
    And the user gets 10 points for the attempt
    And the user does not get any badge

  Scenario: The user sends a right attempt, gets to 100 points and win a badge
    Given the user john_snow sends 9 right attempts
    When the user john_snow sends 1 right attempts
    Then the user gets a response indicating the attempt is right
    And the user gets 10 points for the attempt
    And the user gets the BRONZE_MULTIPLICATOR badge

  Scenario: The user sends a right attempt, gets to 500 points and win a badge
    Given the user john_snow sends 49 right attempts
    When the user john_snow sends 1 right attempts
    Then the user gets a response indicating the attempt is right
    And the user gets 10 points for the attempt
    And the user gets the SILVER_MULTIPLICATOR badge

  Scenario: The user sends a right attempt, gets to 999 points and win a badge
    Given the user john_snow sends 99 right attempts
    When the user john_snow sends 1 right attempts
    Then the user gets a response indicating the attempt is right
    And the user gets 10 points for the attempt
    And the user gets the GOLD_MULTIPLICATOR badge

  Scenario: The user sends a wrong attempt and gets nothing
    When the user john_snow sends 1 wrong attempts
    Then the user gets a response indicating the attempt is wrong
    And the user gets 0 points for the attempt
    And the user does not get any badge