Feature: Get Parent (p8)
As the school admin, I want to get a parent from the system.

  Background:
    Given the following parent entities exists in the system (p8)
      | email         | password | name    | phoneNumber |
      | mom@gmail.com |     1234 | Anna    |     1234567 |
      | dad@gmail.com | password | Bernard |     1234568 |

  Scenario: Successfully get a parent from the system
    When the school admin attempts to get from the system the parent with email "mom@gmail.com" (p8)
    Then the number of parent entities in the system shall be "2" (p8)
    Then the following parent entities shall be presented (p8)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |

  Scenario Outline: Unsuccessfully get a parent that does not exist in the system
    When the school admin attempts to get from the system the parent with email "<email>" (p8)
    Then the number of parent entities in the system shall be "2" (p8)
    Then no parent entities shall be presented (p8)

    Examples:
      | email           |
      | uncle@gmail.com |
      | aunt@gmail.com  |

  Scenario: Successfully get all parents from the system
    When the school admin attempts to get from the system all the parents (p8)
    Then the number of parent entities in the system shall be "2" (p8)
    Then the following parent entities shall be presented (p8)
      | email         | password | name    | phoneNumber |
      | mom@gmail.com |     1234 | Anna    |     1234567 |
      | dad@gmail.com | password | Bernard |     1234568 |
