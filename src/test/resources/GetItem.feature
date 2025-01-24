Feature: Get Item (p6)
As the school admin, I want to get an item from the system.

  Background:
    Given the following item entities exists in the system (p6)
      | name   | price |
      | pencil |     2 |
      | eraser |     1 |

  Scenario: Successfully get an item from the system
    When the school admin attempts to get from the system the item with name "pencil" (p6)
    Then the number of item entities in the system shall be "2" (p6)
    Then the following item entities shall be presented (p6)
      | name   | price |
      | pencil |     2 |

  Scenario Outline: Unsuccessfully get an item that does not exist in the system
    When the school admin attempts to get from the system the item with name "<name>" (p6)
    Then the number of item entities in the system shall be "2" (p6)
    Then no item entities shall be presented (p6)

    Examples:
      | name     |
      | textbook |
      | PenCile  |

  Scenario: Successfully get all items from the system
    When the school admin attempts to get from the system all the items (p6)
    Then the number of item entities in the system shall be "2" (p6)
    Then the following item entities shall be presented (p6)
      | name   | price |
      | pencil |     2 |
      | eraser |     1 |
