Feature: Get Student from Students of Parent (p3)
As the parent, I want to get a student from the students of a parent in the system.

  Background:
    Given the following parent entities exists in the system (p3)
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
    Given the following grade entities exists in the system (p3)
      | level |
      |   5th |
    Given the following student entities exists in the system (p3)
      | name    | gradeLevel |
      | Aaron   |        5th |
      | Barbara |        5th |
      | Carl    |        5th |
    Given the following student entities exist for a parent in the system (p3)
      | name    | parentEmail   |
      | Aaron   | mom@gmail.com |
      | Barbara | mom@gmail.com |

  Scenario: Successfully get a student from the students of a parent in the system
    When the parent attempts to get a student with name "Aaron" from a parent with email "mom@gmail.com" (p3)
    Then the number of student entities in the system shall be "3" (p3)
    Then the number of student entities for parent "mom@gmail.com" in the system shall be "2" (p3)
    Then the following student entities shall be presented (p3)
      | name  | gradeLevel |
      | Aaron |        5th |

  Scenario: Successfully get all students of a parent in the system
    When the parent attempts to get all students from a parent with email "mom@gmail.com" (p3)
    Then the number of student entities in the system shall be "3" (p3)
    Then the number of student entities for parent "mom@gmail.com" in the system shall be "2" (p3)
    Then the following student entities shall be presented (p3)
      | name    | gradeLevel |
      | Aaron   |        5th |
      | Barbara |        5th |

  Scenario: Unsuccessfully get a student from the students of a parent that does not exist in the system
    When the parent attempts to get a student with name "Aaron" from a parent with email "uncle@gmail.com" (p3)
    Then the number of student entities in the system shall be "3" (p3)
    Then no student entities shall be presented (p3)

  Scenario: Unsuccessfully get all students of a parent that does not exist in the system
    When the parent attempts to get all students from a parent with email "uncle@gmail.com" (p3)
    Then the number of student entities in the system shall be "3" (p3)
    Then no student entities shall be presented (p3)

  Scenario: Unsuccessfully get a student that does not exist in the students of a parent in the system
    When the parent attempts to get a student with name "Carl" from a parent with email "mom@gmail.com" (p3)
    Then the number of student entities in the system shall be "3" (p3)
    Then the number of student entities for parent "mom@gmail.com" in the system shall be "2" (p3)
    Then no student entities shall be presented (p3)
