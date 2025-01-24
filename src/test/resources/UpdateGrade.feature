Feature: Update Grade (p13)
As the school admin, I want to update a grade in the system.

  Background:
    Given the following grade entities exists in the system (p13)
      | level |
      |   5th |

  Scenario Outline: Successfully update a grade in the system
    When the school admin attempts to update grade "<level>" in the system with level "<updatedLevel>" (p13)
    Then the number of grade entities in the system shall be "1" (p13)
    Then the grade "<updatedLevel>" shall exist in the system (p13)

    Examples:
      | level | updatedLevel |
      |   5th |          6th |

  Scenario Outline: Unsuccessfully update a grade that does not exist in the system
    When the school admin attempts to update grade "<level>" in the system with level "<updatedLevel>" (p13)
    Then the number of grade entities in the system shall be "1" (p13)
    Then the grade "<updatedLevel>" shall not exist in the system (p13)
    Then the following grade entities shall exist in the system (p13)
      | level |
      |   5th |
    Then the error "<error>" shall be raised (p13)

    Examples:
      | level | updatedLevel | error                     |
      |   6th |          7th | The grade does not exist. |

  Scenario Outline: Unsuccessfully update a grade in the system with invalid values
    When the school admin attempts to update grade "<level>" in the system with level "<updatedLevel>" (p13)
    Then the number of grade entities in the system shall be "1" (p13)
    Then the grade "<updatedLevel>" shall not exist in the system (p13)
    Then the following grade entities shall exist in the system (p13)
      | level |
      |   5th |
    Then the error "<error>" shall be raised (p13)

    Examples:
      | level | updatedLevel | error                        |
      |   5th |              | The level must not be empty. |

  Scenario Outline: Unsuccessfully update a grade in the system with a duplicate unique value
    Given the following grade entities exists in the system (p13)
      | level |
      |   6th |
    When the school admin attempts to update grade "<level>" in the system with level "<updatedLevel>" (p13)
    Then the number of grade entities in the system shall be "2" (p13)
    Then the following grade entities shall exist in the system (p13)
      | level |
      |   5th |
      |   6th |
    Then the error "<error>" shall be raised (p13)

    Examples:
      | level | updatedLevel | error                     |
      |   5th |          6th | The level must be unique. |
