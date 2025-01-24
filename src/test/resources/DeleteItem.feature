Feature: Delete Item (p6)
As the school admin, I want to delete an item from the system.

  Background:
    Given the following item entities exists in the system (p6)
      | name   | price |
      | pencil |     2 |

  Scenario: Successfully delete an item from the system
    When the school admin attempts to delete from the system the item with name "pencil" (p6)
    Then the number of item entities in the system shall be "0" (p6)

  Scenario: Unsuccessfully delete an item that does not exist in the system
    When the school admin attempts to delete from the system the item with name "science textbook" (p6)
    Then the number of item entities in the system shall be "1" (p6)
    Then the following item entities shall exist in the system (p6)
      | name   | price |
      | pencil |     2 |
    Then the error "The item does not exist." shall be raised (p6)
