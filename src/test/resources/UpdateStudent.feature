Feature: Update Student (p4)
As the school administrator, I want to update a student in the system.

  Background:
    Given the following grade entities exists in the system (p4)
      | level |
      |   5th |
    Given the following student entities exists in the system (p4)
      | name  | gradeLevel |
      | Aaron |        5th |

  Scenario Outline: Successfully update a student in the system
    Given the following grade entities exists in the system (p4)
      | level |
      |   6th |
    When the school admin attempts to update student "<name>" in the system with name "<updatedName>" and grade level "<updatedGradeLevel>" (p4)
    Then the number of student entities in the system shall be "1" (p4)
    Then the student "<updatedName>" with grade level "<updatedGradeLevel>" shall exist in the system (p4)

    Examples:
      | name  | updatedName | updatedGradeLevel |
      | Aaron | Barbara     |               6th |

  Scenario Outline: Unsuccessfully update a student that does not exist in the system
    Given the following grade entities exists in the system (p4)
      | level |
      |   6th |
    When the school admin attempts to update student "<name>" in the system with name "<updatedName>" and grade level "<updatedGradeLevel>" (p4)
    Then the number of student entities in the system shall be "1" (p4)
    Then the student "<updatedName>" with grade level "<updatedGradeLevel>" shall not exist in the system (p4)
    Then the following student entities shall exist in the system (p4)
      | name  | gradeLevel |
      | Aaron |        5th |
    Then the error "<error>" shall be raised (p4)

    Examples:
      | name    | updatedName | updatedGradeLevel | error                       |
      | Barbara | Harry       |               6th | The student does not exist. |

  Scenario Outline: Unsuccessfully update a student in the system with invalid values
    When the school admin attempts to update student "<name>" in the system with name "<updatedName>" and grade level "<updatedGradeLevel>" (p4)
    Then the number of student entities in the system shall be "1" (p4)
    Then the student "<updatedName>" with grade level "<updatedGradeLevel>" shall not exist in the system (p4)
    Then the following student entities shall exist in the system (p4)
      | name  | gradeLevel |
      | Aaron |        5th |
    Then the error "<error>" shall be raised (p4)

    Examples:
      | name  | updatedName | updatedGradeLevel | error                       |
      | Aaron |             |               5th | The name must not be empty. |
      | Aaron | Barbara     |               6th | The grade does not exist.   |

  Scenario Outline: Unsuccessfully update a student in the system with a duplicate unique value
    Given the following grade entities exists in the system (p4)
      | level |
      |   6th |
    Given the following student entities exists in the system (p4)
      | name    | gradeLevel |
      | Barbara |        5th |
    When the school admin attempts to update student "<name>" in the system with name "<updatedName>" and grade level "<updatedGradeLevel>" (p4)
    Then the number of student entities in the system shall be "2" (p4)
    Then the student "<updatedName>" with grade level "<updatedGradeLevel>" shall not exist in the system (p4)
    Then the following student entities shall exist in the system (p4)
      | name    | gradeLevel |
      | Aaron   |        5th |
      | Barbara |        5th |
    Then the error "<error>" shall be raised (p4)

    Examples:
      | name  | updatedName | updatedGradeLevel | error                    |
      | Aaron | Barbara     |              6th  | The name must be unique. |
