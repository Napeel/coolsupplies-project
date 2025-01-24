Feature: Update Parent (p1)
As the school admin, I want to update a parent in the system.

  Background:
    Given the following parent entities exists in the system (p1)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |

  Scenario Outline: Successfully update a parent in the system
    When the school admin attempts to update parent "<email>" in the system with password "<updatedPassword>", name "<updatedName>", and phone number "<updatedPhoneNumber>" (p1)
    Then the number of parent entities in the system shall be "1" (p1)
    Then the parent "<email>" with password "<updatedPassword>", name "<updatedName>", and phone number "<updatedPhoneNumber>" shall exist in the system (p1)

    Examples:
      | email         | updatedPassword | updatedName | updatedPhoneNumber |
      | mom@gmail.com | password        | Bernard     |            1234568 |

  Scenario Outline: Unsuccessfully update a parent that does not exist in the system
    When the school admin attempts to update parent "<email>" in the system with password "<updatedPassword>", name "<updatedName>", and phone number "<updatedPhoneNumber>" (p1)
    Then the number of parent entities in the system shall be "1" (p1)
    Then the parent "<email>" with password "<updatedPassword>", name "<updatedName>", and phone number "<updatedPhoneNumber>" shall not exist in the system (p1)
    Then the following parent entities shall exist in the system (p1)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
    Then the error "<error>" shall be raised (p1)

    Examples:
      | email         | updatedPassword | updatedName | updatedPhoneNumber | error                      |
      | dad@gmail.com | password        | Bernard     |            1234568 | The parent does not exist. |

  Scenario Outline: Unsuccessfully update a parent in the system with invalid values
    When the school Administrator attempts to update parent "<email>" in the system with password "<updatedPassword>", name "<updatedName>", and phone number "<updatedPhoneNumber>" (p1)
    Then the number of parent entities in the system shall be "1" (p1)
    Then the parent "<email>" with password "<updatedPassword>", name "<updatedName>", and phone number "<updatedPhoneNumber>" shall not exist in the system (p1)
    Then the following parent entities shall exist in the system (p1)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
    Then the error "<error>" shall be raised (p1)

    Examples:
      | email         | updatedPassword | updatedName | updatedPhoneNumber | error                                  |
      | mom@gmail.com |                 | Bernard     |            1234567 | The password must not be empty.        |
      | mom@gmail.com | password        |             |            1234567 | The name must not be empty.            |
      | mom@gmail.com | password        | Bernard     |             999999 | The phone number must be seven digits. |
      | mom@gmail.com | password        | Bernard     |           10000000 | The phone number must be seven digits. |
