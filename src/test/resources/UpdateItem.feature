Feature: Update Item (p14)
As the school admin, I want to update an item in the system.

  Background:
    Given the following item entities exists in the system (p14)
      | name   | price |
      | pencil |     2 |

  Scenario Outline: Successfully update an item in the system
    When the school admin attempts to update item "<name>" in the system with name "<updatedName>" and price "<updatedPrice>" (p14)
    Then the number of item entities in the system shall be "1" (p14)
    Then the item "<updatedName>" with price "<updatedPrice>" shall exist in the system (p14)

    Examples:
      | name   | updatedName      | updatedPrice |
      | pencil | science textbook |          150 |

  Scenario Outline: Unsuccessfully update an item that does not exist in the system
    When the school admin attempts to update item "<name>" in the system with name "<updatedName>" and price "<updatedPrice>" (p14)
    Then the number of item entities in the system shall be "1" (p14)
    Then the item "<updatedName>" with price "<updatedPrice>" shall not exist in the system (p14)
    Then the following item entities shall exist in the system (p14)
      | name   | price |
      | pencil |     2 |
    Then the error "<error>" shall be raised (p14)

    Examples:
      | name             | updatedName         | updatedPrice | error                    |
      | science textbook | science II textbook |          150 | The item does not exist. |

  Scenario Outline: Unsuccessfully update an item in the system with invalid values
    When the school admin attempts to update item "<name>" in the system with name "<updatedName>" and price "<updatedPrice>" (p14)
    Then the number of item entities in the system shall be "1" (p14)
    Then the item "<updatedName>" with price "<updatedPrice>" shall not exist in the system (p14)
    Then the following item entities shall exist in the system (p14)
      | name   | price |
      | pencil |     2 |
    Then the error "<error>" shall be raised (p14)

    Examples:
      | name   | updatedName      | updatedPrice | error                                         |
      | pencil |                  |          150 | The name must not be empty.                   |
      | pencil | science textbook |           -1 | The price must be greater than or equal to 0. |

  Scenario Outline: Unsuccessfully update a item in the system with a duplicate unique value
    Given the following item entities exists in the system (p14)
      | name     | price |
      | textbook |   150 |
    When the school admin attempts to update item "<name>" in the system with name "<updatedName>" and price "<updatedPrice>" (p14)
    Then the number of item entities in the system shall be "2" (p14)
    Then the item "<updatedName>" with price "<updatedPrice>" shall not exist in the system (p14)
    Then the following item entities shall exist in the system (p14)
      | name     | price |
      | pencil   |     2 |
      | textbook |   150 |
    Then the error "<error>" shall be raised (p14)

    Examples:
      | name   | updatedName | updatedPrice | error                    |
      | pencil | textbook    |          100 | The name must be unique. |
