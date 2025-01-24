Feature: Add Bundle Item to Bundle Items of Grade Bundle (p12)
As the school admin, I want to add a bundle item to the bundle items of a grade bundle in the system.

  Background:
    Given the following grade entities exists in the system (p12)
      | level |
      |   5th |
    Given the following item entities exists in the system (p12)
      | name     | price |
      | pencil   |     2 |
      | textbook |   150 |
    Given the following grade bundle entities exists in the system (p12)
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
    Given the following bundle item entities exists in the system (p12)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |

  Scenario Outline: Successfully add a bundle item to a grade bundle in the system
    When the school admin attempts to add a new bundle item with quantity "<quantity>", level "<level>", and item name "<itemName>" to an existing grade bundle "<gradeBundleName>" (p12)
    Then the number of bundle item entities in the system shall be "2" (p12)
    Then the number of bundle item entities for grade bundle "<gradeBundleName>" in the system shall be "2" (p12)
    Then the bundle item with quantity "<quantity>", level "<level>", and item name "<itemName>" shall exist for grade bundle "<gradeBundleName>" in the system (p12)

    Examples:
      | quantity | level       | gradeBundleName | itemName |
      |        5 | Recommended | Bundle 5        | textbook |

  Scenario Outline: Unsuccessfully add a bundle item to a gradeBundle in the system with invalid values
    When the school admin attempts to add a new bundle item with quantity "<quantity>", level "<level>", and itemName "<itemName>" to a gradeBundle "<gradeBundleName>" (p12)
    Then the number of bundle item entities in the system shall be "1" (p12)
    Then the number of bundle item entities for grade bundle "<gradeBundleName>" in the system shall be "<numItems>" (p12)
    Then the error "<error>" shall be raised (p12)

    Examples:
      | quantity | level       | gradeBundleName | itemName         | error                                                  | numItems |
      |        0 | Recommended | Bundle 5        | textbook         | The quantity must be greater than 0.                   |        1 |
      |        5 | notALiteral | Bundle 5        | textbook         | The level must be Mandatory, Recommended, or Optional. |        1 |
      |        5 | Recommended | Bundle 6        | textbook         | The grade bundle does not exist.                       |        0 |
      |        5 | Recommended | Bundle 5        | pencil           | The item already exists for the bundle.                |        1 |
      |        5 | Recommended | Bundle 5        | science textbook | The item does not exist.                               |        1 |
