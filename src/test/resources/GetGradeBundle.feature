Feature: Get Grade Bundle (p9)
As the school admin, I want to get a grade bundle from the system.

  Background:
    Given the following grade entities exists in the system (p9)
      | level |
      |   5th |
      |   6th |
    Given the following grade bundle entities exists in the system (p9)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
      | Bundle 6 |       30 |        6th |

  Scenario: Successfully get a grade bundle from the system
    When the school admin attempts to get from the system the grade bundle with name "Bundle 6" (p9)
    Then the number of grade bundle entities in the system shall be "2" (p9)
    Then the following grade bundle entities shall be presented (p9)
      | name     | discount | gradeLevel |
      | Bundle 6 |       30 |        6th |

  Scenario Outline: Unsuccessfully get a grade bundle that does not exist in the system
    When the school admin attempts to get from the system the grade bundle with name "<name>" (p9)
    Then the number of grade bundle entities in the system shall be "2" (p9)
    Then no grade bundle entities shall be presented (p9)

    Examples:
      | name     |
      | Bundle 7 |
      | bundle 5 |

  Scenario: Successfully get all bundles from the system
    When the school admin attempts to get from the system all the bundles (p9)
    Then the number of grade bundle entities in the system shall be "2" (p9)
    Then the following grade bundle entities shall be presented (p9)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
      | Bundle 6 |       30 |        6th |
