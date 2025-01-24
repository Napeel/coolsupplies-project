Feature: Add Student (p4)
As the school admin, I want to add a student to the system.

  Background:
    Given the following grade entities exists in the system (p4)
      | level |
      |   5th |
    Given the following student entities exists in the system (p4)
      | name  | gradeLevel |
      | Aaron |        5th |

  Scenario Outline: Successfully add a student to the system
    When the school administrator attempts to add a new student in the system with name "<name>" and grade level "<gradeLevel>" (p4)
    Then the number of student entities in the system shall be "2" (p4)
    Then the student "<name>" with grade level "<gradeLevel>" shall exist in the system (p4)

    Examples:
      | name    | gradeLevel |
      | Barbara |        5th |

  Scenario Outline: Unsuccessfully add a student to the system with invalid values
    When the school admin attempts to add a new student in the system with name "<name>" and grade level "<gradeLevel>" (p4)
    Then the number of student entities in the system shall be "1" (p4)
    Then the error "<error>" shall be raised (p4)

    Examples:
      | name    | gradeLevel | error                       |
      | Aaron   |        5th | The name must be unique.    |
      |         |        5th | The name must not be empty. |
      | Barbara |        6th | The grade does not exist.   |
