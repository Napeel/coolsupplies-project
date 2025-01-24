Feature: Add Item (p14)
As the school admin, I want to add an item to the system.

  Background:
    Given the following item entities exists in the system (p14)
      | name   | price |
      | pencil |     2 |

  Scenario Outline: Successfully add an item to the system
    When the school admin attempts to add a new item in the system with name "<name>" and price "<price>" (p14)
    Then the number of item entities in the system shall be "2" (p14)
    Then the item "<name>" with price "<price>" shall exist in the system (p14)

    Examples:
      | name     | price |
      | textbook |   150 |

  Scenario Outline: Unsuccessfully add an item to the system with invalid values
    When the school admin attempts to add a new item in the system with name "<name>" and price "<price>" (p14)
    Then the number of item entities in the system shall be "1" (p14)
    Then the error "<error>" shall be raised (p14)

    Examples:
      | name     | price | error                                         |
      | pencil   |   150 | The name must be unique.                      |
      |          |   150 | The name must not be empty.                   |
      | textbook |    -1 | The price must be greater than or equal to 0. |
