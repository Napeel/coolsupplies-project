Feature: Add Order (p15)
As the parent, I want to add an order to the system.

  Background:
    Given the following parent entities exists in the system (p15)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
    Given the following grade entities exists in the system (p15)
      | level |
      |   5th |
    Given the following student entities exists in the system (p15)
      | name  | gradeLevel |
      | Aaron |        5th |
    Given the following student entities exist for a parent in the system (p15)
      | name  | parentEmail   |
      | Aaron | mom@gmail.com |
    Given the following order entities exists in the system (p15)
      | number | date       | level     | parentEmail   | studentName |
      |      1 | 2024-01-01 | Mandatory | mom@gmail.com | Aaron       |

  Scenario Outline: Successfully add an order to the system
    When the parent attempts to add a new order in the system with number "<number>", date "<date>", level "<level>", parent email "<parentEmail>", and student name "<studentName>" (p15)
    Then the number of order entities in the system shall be "2" (p15)
    Then the order "<number>" with date "<date>", level "<level>", parent email "<parentEmail>", and student name "<studentName>" shall exist in the system (p15)

    Examples:
      | number | date       | level       | parentEmail   | studentName |
      |      2 | 2024-01-31 | Recommended | mom@gmail.com | Aaron       |

  Scenario Outline: Unsuccessfully add an order to the system with invalid values
    When the parent attempts to add a new order in the system with number "<number>", date "<date>", level "<level>", parent email "<parentEmail>", and student name "<studentName>" (p15)
    Then the number of order entities in the system shall be "1" (p15)
    Then the error "<error>" shall be raised (p15)

    Examples:
      | number | date       | level       | parentEmail   | studentName | error                                                  |
      |      1 | 2024-01-31 | Recommended | mom@gmail.com | Aaron       | The number must be unique.                             |
      |      0 | 2024-01-31 | Recommended | mom@gmail.com | Aaron       | The number must be greater than 0.                     |
      |      2 | 2024-01-31 | notALiteral | mom@gmail.com | Aaron       | The level must be Mandatory, Recommended, or Optional. |
      |      2 | 2024-01-31 | Recommended | dad@gmail.com | Aaron       | The parent does not exist.                             |
      |      2 | 2024-01-31 | Recommended | mom@gmail.com | Barbara     | The student does not exist.                            |
