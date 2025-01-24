Feature: Add Grade (p13)
As the school admin, I want to add a grade to the system.

  Background:
    Given the following grade entities exists in the system (p13)
      | level |
      |   5th |

  Scenario Outline: Successfully add a grade to the system
    When the school admin attempts to add a new grade in the system with level "<level>" (p13)
    Then the number of grade entities in the system shall be "2" (p13)
    Then the grade "<level>" shall exist in the system (p13)

    Examples:
      | level |
      |   6th |

  Scenario Outline: Unsuccessfully add a grade to the system with invalid values
    When the school admin attempts to add a new grade in the system with level "<level>" (p13)
    Then the number of grade entities in the system shall be "1" (p13)
    Then the error "<error>" shall be raised (p13)

    Examples:
      | level | error                        |
      |   5th | The level must be unique.    |
      |       | The level must not be empty. |
