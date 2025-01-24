Feature: Add Parent (p1)
As the parent, I want to add a parent to the system.

  Background:
    Given the following parent entities exists in the system (p1)
      | email              | password | name | phoneNumber |
      | john.doe@gmail.com |     1234 | Anna |     1234567 |

  Scenario Outline: Successfully add a parent to the system
    When the parent attempts to add a new parent in the system with email "<email>", password "<password>", name "<name>", and phone number "<phoneNumber>" (p1)
    Then the number of parent entities in the system shall be "2" (p1)
    Then the parent "<email>" with password "<password>", name "<name>", and phone number "<phoneNumber>" shall exist in the system (p1)

    Examples:
      | email              | password | name    | phoneNumber |
      | jane.doe@gmail.com | password | Bernard |     1234568 |

  Scenario Outline: Unsuccessfully add a parent to the system with invalid values
    When the parent attempts to add a new parent in the system with email "<email>", password "<password>", name "<name>", and phone number "<phoneNumber>" (p1)
    Then the number of parent entities in the system shall be "1" (p1)
    Then the error "<error>" shall be raised (p1)

    Examples:
      | email              | password | name    | phoneNumber | error                                  |
      | john.doe@gmail.com | password | Bernard |     1234567 | The email must be unique.              |
      |                    | password | Bernard |     1234567 | The email must not be empty.           |
      | admin@cool.ca      | password | Bernard |     1234567 | The email must not be admin@cool.ca.   |
      | john @gmail.com    | password | Bernard |     1234567 | The email must not contain spaces.     |
      | dony@gmail@.com    | password | Bernard |     1234567 | The email must be well-formed.         |
      | kyle@gmail.        | password | Bernard |     1234567 | The email must be well-formed.         |
      | greg.gmail@com     | password | Bernard |     1234567 | The email must be well-formed.         |
      | @gmail.com         | password | Bernard |     1234567 | The email must be well-formed.         |
      | karl@.com          | password | Bernard |     1234567 | The email must be well-formed.         |
      | jane.doe@gmail.com |          | Bernard |     1234567 | The password must not be empty.        |
      | jane.doe@gmail.com | password |         |     1234567 | The name must not be empty.            |
      | jane.doe@gmail.com | password | Bernard |      999999 | The phone number must be seven digits. |
      | jane.doe@gmail.com | password | Bernard |    10000000 | The phone number must be seven digits. |
