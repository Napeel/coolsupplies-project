Feature: Delete Parent (p8)
As the school admin, I want to delete a parent from the system.

  Background:
    Given the following parent entities exists in the system (p8)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |

  Scenario: Successfully delete a parent from the system
    When the school Administrator attempts to delete from the system the parent with email "mom@gmail.com" (p8)
    Then the number of parent entities in the system shall be "0" (p8)

  Scenario: Unsuccessfully delete a parent that does not exist in the system
    When the school Administrator attempts to delete from the system the parent with email "dad@gmail.com" (p8)
    Then the number of parent entities in the system shall be "1" (p8)
    Then the following parent entities shall exist in the system (p8)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
    Then the error "The parent does not exist." shall be raised (p8)
