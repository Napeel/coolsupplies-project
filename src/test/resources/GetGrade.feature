Feature: Get Grade (p2)
As the school admin, I want to get a grade from the system.

  Background:
    Given the following grade entities exists in the system (p2)
      | level |
      |   5th |
      |   6th |

  Scenario: Successfully get a grade from the system
    When the school admin attempts to get from the system the grade with level "5th" (p2)
    Then the number of grade entities in the system shall be "2" (p2)
    Then the following grade entities shall be presented (p2)
      | level |
      |   5th |

  Scenario Outline: Unsuccessfully get a grade that does not exist in the system
    When the school admin attempts to get from the system the grade with level "<level>" (p2)
    Then the number of grade entities in the system shall be "2" (p2)
    Then no grade entities shall be presented (p2)

    Examples:
      | level |
      |   7th |
      |     5 |

  Scenario: Successfully get all grades from the system
    When the school admin attempts to get from the system all the grades (p2)
    Then the number of grade entities in the system shall be "2" (p2)
    Then the following grade entities shall be presented (p2)
      | level |
      |   5th |
      |   6th |
