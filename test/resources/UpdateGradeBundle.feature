Feature: Update Grade Bundle (p10)
As the school admin, I want to update a grade bundle in the system.

  Background:
    Given the following grade entities exists in the system (p10)
      | level |
      |   5th |
      |   6th |
    Given the following grade bundle entities exists in the system (p10)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |

  Scenario Outline: Successfully update a grade bundle in the system
    When the school admin attempts to update grade bundle "<name>" in the system with name "<updatedName>", discount "<updatedDiscount>", and grade level "<updatedGradeLevel>" (p10)
    Then the number of grade bundle entities in the system shall be "1" (p10)
    Then the grade bundle "<updatedName>" with discount "<updatedDiscount>" and grade level "<updatedGradeLevel>" shall exist in the system (p10)

    Examples:
      | name     | updatedName | updatedDiscount | updatedGradeLevel |
      | Bundle 5 | Bundle 6    |               0 |               6th |

  Scenario Outline: Unsuccessfully update a grade bundle that does not exist in the system
    When the school admin attempts to update grade bundle "<name>" in the system with name "<updatedName>", discount "<updatedDiscount>", and grade level "<updatedGradeLevel>" (p10)
    Then the number of grade bundle entities in the system shall be "1" (p10)
    Then the grade bundle "<updatedName>" with discount "<updatedDiscount>" and grade level "<updatedGradeLevel>" shall not exist in the system (p10)
    Then the following grade bundle entities shall exist in the system (p10)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
    Then the error "<error>" shall be raised (p10)

    Examples:
      | name     | updatedName | updatedDiscount | updatedGradeLevel | error                      |
      | Bundle 6 |  6th Bundle |               0 |               6th | The bundle does not exist. |

  Scenario Outline: Unsuccessfully update a gradeBundle in the system with invalid values
    When the school admin attempts to update grade bundle "<name>" in the system with name "<updatedName>", discount "<updatedDiscount>", and grade level "<updatedGradeLevel>" (p10)
    Then the number of grade bundle entities in the system shall be "1" (p10)
    Then the grade bundle "<updatedName>" with discount "<updatedDiscount>" and grade level "<updatedGradeLevel>" shall not exist in the system (p10)
    Then the following grade bundle entities shall exist in the system (p10)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
    Then the error "<error>" shall be raised (p10)

    Examples:
      | name     | updatedName | updatedDiscount | updatedGradeLevel | error                                                                          |
      | Bundle 5 |             |               0 |               6th | The name must not be empty.                                                    |
      | Bundle 5 | Bundle 6    |              -1 |               6th | The discount must be greater than or equal to 0 and less than or equal to 100. |
      | Bundle 5 | Bundle 6    |             101 |               6th | The discount must be greater than or equal to 0 and less than or equal to 100. |
      | Bundle 5 | Bundle 6    |               0 |               7th | The grade does not exist.                                                      |

  Scenario Outline: Unsuccessfully update a grade bundle in the system with a duplicate unique value
    Given the following grade bundle entities exists in the system (p10)
      | name     | discount | gradeLevel |
      | Bundle 6 |        0 |        6th |
    Given the following grade entities exists in the system (p10)
      | level |
      |   7th |
    When the school admin attempts to update grade bundle "<name>" in the system with name "<updatedName>", discount "<updatedDiscount>", and grade level "<updatedGradeLevel>" (p10)
    Then the number of grade bundle entities in the system shall be "2" (p10)
    Then the grade bundle "<updatedName>" with discount "<updatedDiscount>" and grade level "<updatedGradeLevel>" shall not exist in the system (p10)
    Then the following grade bundle entities shall exist in the system (p10)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
      | Bundle 6 |        0 |        6th |
    Then the error "<error>" shall be raised (p10)

    Examples:
      | name     | updatedName | updatedDiscount | updatedGradeLevel | error                                  |
      | Bundle 5 | Bundle 6    |               0 |               7th | The name must be unique.               |
      | Bundle 6 | Bundle 6    |               0 |               5th | A bundle already exists for the grade. |
