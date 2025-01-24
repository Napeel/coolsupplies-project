Feature: Add Grade Bundle (p10)
As the school admin, I want to add a grade bundle to the system.

  Background:
    Given the following grade entities exists in the system (p10)
      | level |
      |   5th |
      |   6th |
    Given the following grade bundle entities exists in the system (p10)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |

  Scenario Outline: Successfully add a grade bundle to the system
    When the school admin attempts to add a new grade bundle in the system with name "<name>", discount "<discount>", and grade level "<gradeLevel>" (p10)
    Then the number of grade bundle entities in the system shall be "2" (p10)
    Then the grade bundle "<name>" with discount "<discount>" and grade level "<gradeLevel>" shall exist in the system (p10)

    Examples:
      | name     | discount | gradeLevel |
      | Bundle 6 |        0 |        6th |

  Scenario Outline: Unsuccessfully add a grade bundle to the system with invalid values
    When the school admin attempts to add a new grade bundle in the system with name "<name>", discount "<discount>", and grade level "<gradeLevel>" (p10)
    Then the number of grade bundle entities in the system shall be "1" (p10)
    Then the error "<error>" shall be raised (p10)

    Examples:
      | name     | discount | gradeLevel | error                                                                          |
      | Bundle 5 |        0 |        6th | The name must be unique.                                                       |
      |          |        0 |        6th | The name must not be empty.                                                    |
      | Bundle 6 |       -1 |        6th | The discount must be greater than or equal to 0 and less than or equal to 100. |
      | Bundle 6 |      101 |        6th | The discount must be greater than or equal to 0 and less than or equal to 100. |
      | Bundle 6 |        0 |        7th | The grade does not exist.                                                      |
      | Bundle 6 |        0 |        5th | A bundle already exists for the grade.                                         |
