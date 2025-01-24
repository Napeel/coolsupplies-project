Feature: Get Bundle Item from Bundle Items of Grade Bundle (p11)
As the school admin, I want to get a bundle item from the bundle items of a grade bundle in the system.

  Background:
    Given the following grade entities exists in the system (p11)
      | level |
      |   5th |
      |   6th |
    Given the following item entities exists in the system (p11)
      | name     | price |
      | pencil   |     2 |
      | eraser   |     1 |
      | textbook |     5 |
    Given the following grade bundle entities exists in the system (p11)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
      | Bundle 6 |       30 |        6th |
    Given the following bundle item entities exists in the system (p11)
      | quantity | level       | gradeBundleName | itemName |
      |        3 | Mandatory   | Bundle 5        | pencil   |
      |        2 | Optional    | Bundle 5        | eraser   |
      |        1 | Recommended | Bundle 5        | textbook |
      |        3 | Mandatory   | Bundle 6        | pencil   |

  Scenario: Successfully get a bundle item from the bundle items of a grade bundle in the system
    When the school admin attempts to get a bundle item with name "pencil" from a grade bundle with name "Bundle 5" (p11)
    Then the number of bundle item entities in the system shall be "4" (p11)
    Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "3" (p11)
    Then the following bundle item entities shall be presented (p11)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |

  Scenario: Successfully get all bundle items of a grade bundle in the system
    When the school admin attempts to get all bundle items from a grade bundle with name "Bundle 5" (p11)
    Then the number of bundle item entities in the system shall be "4" (p11)
    Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "3" (p11)
    Then the following bundle item entities shall be presented (p11)
      | quantity | level       | gradeBundleName | itemName |
      |        3 | Mandatory   | Bundle 5        | pencil   |
      |        2 | Optional    | Bundle 5        | eraser   |
      |        1 | Recommended | Bundle 5        | textbook |

  Scenario: Unsuccessfully get a bundle item from the bundle items of a grade bundle that does not exist in the system
    When the school admin attempts to get a bundle item with name "pencil" from a grade bundle with name "Bundle 7" (p11)
    Then the number of bundle item entities in the system shall be "4" (p11)
    Then no bundle item entities shall be presented (p11)

  Scenario: Unsuccessfully get all bundle items of a grade bundle that does not exist in the system
    When the school admin attempts to get all bundle items from a grade bundle with name "Bundle 7" (p11)
    Then the number of bundle item entities in the system shall be "4" (p11)
    Then no bundle item entities shall be presented (p11)

  Scenario: Unsuccessfully get a bundle item that does not exist in the bundle items of a grade bundle in the system
    When the school admin attempts to get a bundle item with name "textbook" from a grade bundle with name "Bundle 6" (p11)
    Then the number of bundle item entities in the system shall be "4" (p11)
    Then the number of bundle item entities for grade bundle "Bundle 6" in the system shall be "1" (p11)
    Then no bundle item entities shall be presented (p11)
