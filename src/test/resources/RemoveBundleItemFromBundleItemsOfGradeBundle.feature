Feature: Remove Bundle Item from Bundle Items of Grade Bundle (p11)
As the school admin, I want to remove a bundle item from the bundle items of a grade bundle in the system.

  Background:
    Given the following grade entities exists in the system (p11)
      | level |
      |   5th |
    Given the following item entities exists in the system (p11)
      | name   | price |
      | pencil |     2 |
    Given the following grade bundle entities exists in the system (p11)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
    Given the following bundle item entities exists in the system (p11)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |

  Scenario: Successfully remove a bundle item from the bundle items of a grade bundle in the system
    When the school admin attempts to remove a bundle item with name "pencil" from a grade bundle with name "Bundle 5" (p11)
    Then the number of bundle item entities in the system shall be "0" (p11)
    Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "0" (p11)

  Scenario: Unsuccessfully remove a bundle item from the bundle items of a grade bundle that does not exist in the system
    When the school admin attempts to remove a bundle item with name "pencil" from a grade bundle with name "Bundle 6" (p11)
    Then the number of bundle item entities in the system shall be "1" (p11)
    Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "1" (p11)
    Then the following bundle item entities shall exist in the system (p11)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |
    Then the error "The grade bundle does not exist." shall be raised (p11)

  Scenario: Unsuccessfully remove a bundle item that does not exist in the bundle items of a grade bundle in the system
    When the school admin attempts to remove a bundle item with name "textbook" from a grade bundle with name "Bundle 5" (p11)
    Then the number of bundle item entities in the system shall be "1" (p11)
    Then the number of bundle item entities for grade bundle "Bundle 5" in the system shall be "1" (p11)
    Then the following bundle item entities shall exist in the system (p11)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |
    Then the error "The bundle item does not exist." shall be raised (p11)
