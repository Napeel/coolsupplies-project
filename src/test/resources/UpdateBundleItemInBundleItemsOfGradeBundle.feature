Feature: Update Bundle Item in Bundle Items of Grade Bundle (p12)
As the school admin, I want to update a bundle item to the bundle items of a grade bundle in the system.

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

  Scenario Outline: Successfully update a bundle item in a grade bundle in the system
    When the school admin attempts to update a bundle item "<itemName>" of grade bundle "<gradeBundleName>" with quantity "<updatedQuantity>" and level "<updatedLevel>" (p12)
    Then the number of bundle item entities in the system shall be "1" (p12)
    Then the number of bundle item entities for grade bundle "<gradeBundleName>" in the system shall be "1" (p12)
    Then the bundle item with quantity "<updatedQuantity>", level "<updatedLevel>", and item name "<itemName>" shall exist for grade bundle "<gradeBundleName>" in the system (p12)

    Examples:
      | updatedQuantity | updatedLevel | gradeBundleName | itemName |
      |               5 | Recommended  | Bundle 5        | pencil   |

  Scenario Outline: Unsuccessfully update a bundle item that does not exist in a grade bundle in the system
    When the school admin attempts to update a bundle item "<itemName>" of grade bundle "<gradeBundleName>" with quantity "<updatedQuantity>" and level "<updatedLevel>" (p12)
    Then the number of bundle item entities in the system shall be "1" (p12)
    Then the number of bundle item entities for grade bundle "<gradeBundleName>" in the system shall be "1" (p12)
    Then the bundle item with quantity "<updatedQuantity>", level "<updatedLevel>", and item name "<itemName>" shall not exist for grade bundle "<gradeBundleName>" in the system (p12)
    Then the following bundle item entities shall exist in the system (p12)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |
    Then the error "<error>" shall be raised (p12)

    Examples:
      | updatedQuantity | updatedLevel | gradeBundleName | itemName | error                                                |
      |               5 | Recommended  | Bundle 5        | textbook | The bundle item does not exist for the grade bundle. |

  Scenario Outline: Unsuccessfully update a bundle item in a grade bundle in the system with invalid values
    When the school admin attempts to update a bundle item "<itemName>" of grade bundle "<gradeBundleName>" with quantity "<updatedQuantity>" and level "<updatedLevel>" (p12)
    Then the number of bundle item entities in the system shall be "1" (p12)
    Then the number of bundle item entities for grade bundle "<gradeBundleName>" in the system shall be "<numItems>" (p12)
    Then the bundle item with quantity "<updatedQuantity>", level "<updatedLevel>", and item name "<itemName>" shall not exist for grade bundle "<gradeBundleName>" in the system (p12)
    Then the following bundle item entities shall exist in the system (p12)
      | quantity | level     | gradeBundleName | itemName |
      |        3 | Mandatory | Bundle 5        | pencil   |
    Then the error "<error>" shall be raised (p12)

    Examples:
      | updatedQuantity | updatedLevel | gradeBundleName | itemName | error                                                  | numItems |
      |               0 | Recommended  | Bundle 5        | pencil   | The quantity must be greater than 0.                   |        1 |
      |               5 | notALiteral  | Bundle 5        | pencil   | The level must be Mandatory, Recommended, or Optional. |        1 |
      |               5 | Recommended  | Bundle 6        | pencil   | The grade bundle does not exist.                       |        0 |
