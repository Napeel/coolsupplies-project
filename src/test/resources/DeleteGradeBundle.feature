Feature: Delete Grade Bundle (p9)
As the school admin, I want to delete a grade bundle from the system.

  Background:
    Given the following grade entities exists in the system (p9)
      | level |
      |   5th |
    Given the following grade bundle entities exists in the system (p9)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |

  Scenario: Successfully delete a grade bundle from the system
    When the school admin attempts to delete from the system the gradeBundle with name "Bundle 5" (p9)
    Then the number of grade bundle entities in the system shall be "0" (p9)

  Scenario: Unsuccessfully delete a grade bundle that does not exist in the system
    When the school admin attempts to delete from the system the gradeBundle with name "Bundle 6" (p9)
    Then the number of grade bundle entities in the system shall be "1" (p9)
    Then the following grade bundle entities shall exist in the system (p9)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
    Then the error "The grade bundle does not exist." shall be raised (p9)
