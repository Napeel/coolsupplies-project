Feature: Delete Grade (p2)
As the school admin, I want to delete a grade from the system.

  Background:
    Given the following grade entities exists in the system (p2)
      | level |
      |   5th |

  Scenario: Successfully delete a grade from the system
    When the school admin attempts to delete from the system the grade with level "5th" (p2)
    Then the number of grade entities in the system shall be "0" (p2)

  Scenario: Unsuccessfully delete a grade that does not exist in the system
    When the school admin attempts to delete from the system the grade with level "6th" (p2)
    Then the number of grade entities in the system shall be "1" (p2)
    Then the following grade entities shall exist in the system (p2)
      | level |
      |   5th |
    Then the error "The grade does not exist." shall be raised (p2)
