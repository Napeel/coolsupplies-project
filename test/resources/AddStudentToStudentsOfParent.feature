Feature: Add Student to Students of Parent (p16)
As the parent, I want to add a student to the students of a parent in the system.

  Background:
    Given the following parent entities exists in the system (p16)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
    Given the following grade entities exists in the system (p16)
      | level |
      |   5th |
    Given the following student entities exists in the system (p16)
      | name  | gradeLevel |
      | Aaron |        5th |

  Scenario Outline: Successfully add a student to a parent in the system
    When the parent attempts to add a student with name "<name>" to an existing parent "<parentEmail>" (p16)
    Then the number of student entities in the system shall be "1" (p16)
    Then the number of student entities for parent "<parentEmail>" in the system shall be "1" (p16)
    Then the student "<name>" shall exist for parent "<parentEmail>" in the system (p16)

    Examples:
      | name  | parentEmail   |
      | Aaron | mom@gmail.com |

  Scenario Outline: Unsuccessfully add a student that does not exist to a parent in the system
    When the parent attempts to add a student with name "<name>" to an existing parent "<parentEmail>" (p16)
    Then the number of student entities in the system shall be "1" (p16)
    Then the number of student entities for parent "<parentEmail>" in the system shall be "0" (p16)
    Then the student "<name>" shall not exist for parent "<parentEmail>" in the system (p16)
    Then the error "<error>" shall be raised (p16)

    Examples:
      | name    | parentEmail   | error                       |
      | Barbara | mom@gmail.com | The student does not exist. |
