Feature: Delete Student (p5)
As the school admin, I want to delete a student from the system.

  Background:
    Given the following grade entities exists in the system (p5)
      | level |
      |   5th |
    Given the following student entities exists in the system (p5)
      | name  | gradeLevel |
      | Aaron |        5th |

  Scenario: Successfully delete a student from the system
    When the school admin attempts to delete from the system the student with name "Aaron" (p5)
    Then the number of student entities in the system shall be "0" (p5)

  Scenario: Unsuccessfully delete a student that does not exist in the system
    When the school admin attempts to delete from the system the student with name "Barbara" (p5)
    Then the number of student entities in the system shall be "1" (p5)
    Then the following student entities shall exist in the system (p5)
      | name  | gradeLevel |
      | Aaron |        5th |
    Then the error "The student does not exist." shall be raised (p5)
