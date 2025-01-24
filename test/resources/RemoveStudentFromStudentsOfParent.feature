Feature: Remove Student from Students of Parent (p16)
As the parent, I want to remove a student from the students of a parent in the system.

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
    Given the following student entities exist for a parent in the system (p16)
      | name  | parentEmail   |
      | Aaron | mom@gmail.com |

  Scenario: Successfully remove a student from the students of a parent in the system
    When the parent attempts to remove a student with name "Aaron" from a parent with email "mom@gmail.com" (p16)
    Then the number of student entities in the system shall be "1" (p16)
    Then the number of student entities for parent "mom@gmail.com" in the system shall be "0" (p16)

  Scenario: Unsuccessfully remove a student from the students of a parent that does not exist in the system
    When the parent attempts to remove a student with name "Aaron" from a parent with email "dad@gmail.com" (p16)
    Then the number of student entities in the system shall be "1" (p16)
    Then the number of student entities for parent "mom@gmail.com" in the system shall be "1" (p16)
    Then the following student entities for a parent shall exist in the system (p16)
      | name  | parentEmail   |
      | Aaron | mom@gmail.com |
    Then the error "The parent does not exist." shall be raised (p16)

  Scenario: Unsuccessfully remove a student that does not exist in the students of a parent in the system
    When the parent attempts to remove a student with name "Barbara" from a parent with email "mom@gmail.com" (p16)
    Then the number of student entities in the system shall be "1" (p16)
    Then the number of student entities for parent "mom@gmail.com" in the system shall be "1" (p16)
    Then the following student entities shall exist in the system (p16)
      | name  | parentEmail   |
      | Aaron | mom@gmail.com |
    Then the error "The student does not exist." shall be raised (p16)
