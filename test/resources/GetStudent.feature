Feature: Get Student (p5)
As the school admin, I want to get a student from the system.

  Background:
    Given the following grade entities exists in the system (p5)
      | level |
      |   5th |
    Given the following student entities exists in the system (p5)
      | name    | gradeLevel |
      | Aaron   |        5th |
      | Barbara |        5th |

  Scenario: Successfully get a student from the system
    When the school admin attempts to get from the system the student with name "Aaron" (p5)
    Then the number of student entities in the system shall be "2" (p5)
    Then the following student entities shall be presented (p5)
      | name  | gradeLevel |
      | Aaron |        5th |

  Scenario Outline: Unsuccessfully get a student that does not exist in the system
    When the school admin attempts to get from the system the student with name "<name>" (p5)
    Then the number of student entities in the system shall be "2" (p5)
    Then no student entities shall be presented (p5)

    Examples:
      | name  |
      | Cat   |
      | aaron |

  Scenario: Successfully get all students from the system
    When the school admin attempts to get from the system all the students (p5)
    Then the number of student entities in the system shall be "2" (p5)
    Then the following student entities shall be presented (p5)
      | name    | gradeLevel |
      | Aaron   |        5th |
      | Barbara |        5th |
