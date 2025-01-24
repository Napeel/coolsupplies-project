Feature: Process order
As the parent / admin / student, I want to process orders in the system

  Background:
    Given the following parent entities exist in the system
      | email         | password | name | phoneNumber |
      | mom@gmail.com |     1234 | Anna |     1234567 |
      | dad@gmail.com |     1234 | John |     1234567 |
    Given the following grade entities exist in the system
      | level |
      |   5th |
      |   6th |
    Given the following student entities exist in the system
      | name  | gradeLevel |
      | Aaron |        5th |
      | Wendy |        5th |
      | Sarah |        6th |
    Given the following student entities exist for a parent in the system
      | name  | parentEmail   |
      | Aaron | mom@gmail.com |
      | Wendy | mom@gmail.com |
      | Sarah | dad@gmail.com |
    Given the following item entities exist in the system
      | name     | price |
      | pencil   |     2 |
      | textbook |   150 |
      | eraser   |     1 |
    Given the following grade bundle entities exist in the system
      | name     | discount | gradeLevel |
      | Bundle 5 |       15 |        5th |
      | Bundle 6 |       10 |        6th |
    Given the following bundle item entities exist in the system
      | quantity | level       | gradeBundleName | itemName |
      |        3 | Mandatory   | Bundle 5        | pencil   |
      |        2 | Recommended | Bundle 5        | textbook |
      |        2 | Mandatory   | Bundle 6        | pencil   |
      |        1 | Recommended | Bundle 6        | textbook |
    Given the following order entities exist in the system
      | number | date       | level       | parentEmail   | studentName |
      |      1 | 2024-01-01 | Mandatory   | mom@gmail.com | Aaron       |
      |      2 | 2024-01-01 | Recommended | mom@gmail.com | Aaron       |
      |      3 | 2024-01-01 | Mandatory   | mom@gmail.com | Wendy       |
      |      4 | 2024-01-01 | Recommended | dad@gmail.com | Sarah       |
    Given the following order item entities exist in the system
      | quantity | orderNumber | itemName |
      |        3 |           1 | eraser   |
      |        1 |           1 | Bundle 5 |

  Scenario Outline: Successfully update an order
    Given the order "<orderNumber>" is marked as "Started"
    When the parent attempts to update an order with number "<orderNumber>" to purchase level "<level>" and student with name "<student>"
    Then the order "<orderNumber>" shall be marked as "Started"
    Then the number of orders in the system shall be "4"
    Then the order "<orderNumber>" shall contain level "<level>" and student "<student>"

    Examples:
      | orderNumber | level       | student |
      |           1 | Recommended | Aaron   |
      |           2 | Recommended | Wendy   |
      |           3 | Optional    | Aaron   |
      |           4 | Optional    | Sarah   |

  Scenario Outline: Unsuccessfully update an order due to wrong input
    Given the order "1" is marked as "Started"
    When the parent attempts to update an order with number "1" to purchase level "<level>" and student with name "<student>"
    Then the order "1" shall be marked as "Started"
    Then the number of orders in the system shall be "4"
    Then the order "1" shall contain level "Mandatory" and student "Aaron"
    Then the error "<error>" shall be raised

    Examples:
      | level       | student | error                                                     |
      | Recommended | Sarah   | Student Sarah is not a child of the parent mom@gmail.com. |
      | NotExist    | Aaron   | Purchase level NotExist does not exist.                   |
      | Optional    | David   | Student David does not exist.                             |

  Scenario Outline: Unsuccessfully update an order due to non-existing order
    When the parent attempts to update an order with number "<orderNumber>" to purchase level "Optional" and student with name "Aaron"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully update an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to update an order with number "1" to purchase level "Recommended" and student with name "Aaron"
    Then the order "1" shall be marked as "<state>"
    Then the number of orders in the system shall be "4"
    Then the order "1" shall contain level "Mandatory" and student "Aaron"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                           |
      | Paid      | Cannot update a paid order      |
      | Penalized | Cannot update a penalized order |
      | Prepared  | Cannot update a prepared order  |
      | PickedUp  | Cannot update a picked up order |

  Scenario Outline: Successfully add an item to an order
    Given the order "<orderNumber>" is marked as "Started"
    When the parent attempts to add an item "<item>" with quantity "<quantity>" to the order "<orderNumber>"
    Then the order "<orderNumber>" shall be marked as "Started"
    Then the number of order items in the system shall be "3"
    Then the order "<orderNumber>" shall contain "<numberOfItem>" items
    Then the order "<orderNumber>" shall contain "<item>" with quantity "<quantity>"

    Examples:
      | orderNumber | item     | quantity | numberOfItem |
      |           1 | pencil   |        3 |            3 |
      |           2 | textbook |        1 |            1 |
      |           3 | Bundle 5 |        1 |            1 |
      |           4 | Bundle 6 |        1 |            1 |

  Scenario Outline: Unsuccessfully add an item to an order due to wrong input
    Given the order "<orderNumber>" is marked as "Started"
    When the parent attempts to add an item "<item>" with quantity "<quantity>" to the order "<orderNumber>"
    Then the order "<orderNumber>" shall be marked as "Started"
    Then the number of order items in the system shall be "2"
    Then the order "<orderNumber>" shall contain "2" items
    Then the order "<orderNumber>" shall not contain "<item>" with quantity "<quantity>"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | item     | quantity | error                                        |
      |           1 | eraser   |        2 | Item eraser already exists in the order 1.   |
      |           1 | Bundle 5 |        2 | Item Bundle 5 already exists in the order 1. |
      |           1 | NotExist |        1 | Item NotExist does not exist.                |
      |           1 | pencil   |        0 | Quantity must be greater than 0.             |
      |           1 | pencil   |       -1 | Quantity must be greater than 0.             |

  Scenario Outline: Unsuccessfully add an item to an order due to non-existing order
    When the parent attempts to add an item "pencil" with quantity "3" to the order "<orderNumber>"
    Then the number of order items in the system shall be "2"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully add an item to an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to add an item "pencil" with quantity "2" to the order "1"
    Then the order "1" shall be marked as "<state>"
    Then the number of order items in the system shall be "2"
    Then the order "1" shall contain "2" items
    Then the order "1" shall not contain "pencil" with quantity "2"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                                 |
      | Paid      | Cannot add items to a paid order      |
      | Penalized | Cannot add items to a penalized order |
      | Prepared  | Cannot add items to a prepared order  |
      | PickedUp  | Cannot add items to a picked up order |

  Scenario Outline: Successfully update an item in an order
    Given the order "1" is marked as "Started"
    When the parent attempts to update an item "<item>" with quantity "<quantity>" in the order "1"
    Then the order "1" shall be marked as "Started"
    Then the number of order items in the system shall be "2"
    Then the order "1" shall contain "2" items
    Then the order "1" shall contain "<item>" with quantity "<quantity>"

    Examples:
      | item     | quantity |
      | eraser   |        2 |
      | Bundle 5 |        2 |

  Scenario Outline: Unsuccessfully update an item in an order due to wrong input
    Given the order "1" is marked as "Started"
    When the parent attempts to update an item "<item>" with quantity "<quantity>" in the order "1"
    Then the order "1" shall be marked as "Started"
    Then the number of order items in the system shall be "2"
    Then the order "1" shall contain "2" items
    Then the order "1" shall not contain "<item>" with quantity "<quantity>"
    Then the error "<error>" shall be raised

    Examples:
      | item     | quantity | error                                        |
      | pencil   |        3 | Item pencil does not exist in the order 1.   |
      | Bundle 6 |        1 | Item Bundle 6 does not exist in the order 1. |
      | NotExist |        1 | Item NotExist does not exist.                |
      | eraser   |        0 | Quantity must be greater than 0.             |
      | eraser   |       -1 | Quantity must be greater than 0.             |

  Scenario Outline: Unsuccessfully update an item in an order due to non-existing order
    When the parent attempts to update an item "eraser" with quantity "3" in the order "<orderNumber>"
    Then the number of order items in the system shall be "2"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully update an item in an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to update an item "eraser" with quantity "2" in the order "1"
    Then the order "1" shall be marked as "<state>"
    Then the number of order items in the system shall be "2"
    Then the order "1" shall contain "2" items
    Then the order "1" shall not contain "eraser" with quantity "2"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                                    |
      | Paid      | Cannot update items in a paid order      |
      | Penalized | Cannot update items in a penalized order |
      | Prepared  | Cannot update items in a prepared order  |
      | PickedUp  | Cannot update items in a picked up order |

  Scenario Outline: Successfully delete an item from an order
    Given the order "1" is marked as "Started"
    When the parent attempts to delete an item "<item>" from the order "1"
    Then the order "1" shall be marked as "Started"
    Then the number of order items in the system shall be "1"
    Then the order "1" shall contain "1" item
    Then the order "1" shall not contain "<item>"

    Examples:
      | item     |
      | eraser   |
      | Bundle 5 |

  Scenario Outline: Unsuccessfully delete an item from an order due to wrong input
    Given the order "1" is marked as "Started"
    When the parent attempts to delete an item "<item>" from the order "1"
    Then the order "1" shall be marked as "Started"
    Then the number of order items in the system shall be "2"
    Then the order "1" shall contain "2" items
    Then the error "<error>" shall be raised

    Examples:
      | item     | error                                        |
      | pencil   | Item pencil does not exist in the order 1.   |
      | Bundle 6 | Item Bundle 6 does not exist in the order 1. |
      | NotExist | Item NotExist does not exist.                |

  Scenario Outline: Unsuccessfully delete an item from an order due to non-existing order
    When the parent attempts to delete an item "eraser" from the order "<orderNumber>"
    Then the number of order items in the system shall be "2"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully delete an item from an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to delete an item "eraser" from the order "1"
    Then the order "1" shall be marked as "<state>"
    Then the number of order items in the system shall be "2"
    Then the order "1" shall contain "2" items
    Then the order "1" shall contain "eraser" with quantity "3"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                                      |
      | Paid      | Cannot delete items from a paid order      |
      | Penalized | Cannot delete items from a penalized order |
      | Prepared  | Cannot delete items from a prepared order  |
      | PickedUp  | Cannot delete items from a picked up order |

  Scenario Outline: Successfully pay for a started order
    Given the order "1" is marked as "Started"
    When the parent attempts to pay for the order "1" with authorization code "<code>"
    Then the order "1" shall be marked as "Paid"
    Then the order "1" shall contain authorization code "<code>"

    Examples:
      | code |
      | 1234 |
      | 5678 |

  Scenario Outline: Unsuccessfully pay for an order due to no items
    Given the order "<orderNumber>" is marked as "Started"
    When the parent attempts to pay for the order "<orderNumber>" with authorization code "1234"
    Then the order "<orderNumber>" shall be marked as "Started"
    Then the order "<orderNumber>" shall not contain authorization code "1234"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                |
      |           2 | Order 2 has no items |
      |           3 | Order 3 has no items |
      |           4 | Order 4 has no items |

  Scenario Outline: Unsuccessfully pay for an order due to wrong input
    Given the order "1" is marked as "Started"
    When the parent attempts to pay for the order "1" with authorization code "<code>"
    Then the order "1" shall be marked as "Started"
    Then the order "1" shall not contain authorization code "<code>"
    Then the error "<error>" shall be raised

    Examples:
      | code | error                         |
      |      | Authorization code is invalid |

  Scenario Outline: Unsuccessfully pay for an order due to non-existing order
    When the parent attempts to pay for the order "<orderNumber>" with authorization code "1234"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully pay for an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to pay for the order "1" with authorization code "1234"
    Then the order "1" shall be marked as "<state>"
    Then the order "1" shall not contain authorization code "1234"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                            |
      | Paid      | The order is already paid        |
      | Penalized | Cannot pay for a penalized order |
      | Prepared  | Cannot pay for a prepared order  |
      | PickedUp  | Cannot pay for a picked up order |

  Scenario Outline: Successfully cancel a started order
    Given the order "<orderNumber>" is marked as "Started"
    When the parent attempts to cancel the order "<orderNumber>"
    Then the order "<orderNumber>" shall not exist in the system
    Then the number of orders in the system shall be "3"

    Examples:
      | orderNumber |
      |           1 |
      |           2 |
      |           3 |
      |           4 |

  Scenario Outline: Successfully cancel a paid order
    Given the order "<orderNumber>" is marked as "Paid"
    When the parent attempts to cancel the order "<orderNumber>"
    Then the order "<orderNumber>" shall not exist in the system
    Then the number of orders in the system shall be "3"

    Examples:
      | orderNumber |
      |           1 |
      |           2 |
      |           3 |
      |           4 |

  Scenario Outline: Unsuccessfully cancel an order due to non-existing order
    When the parent attempts to cancel the order "<orderNumber>"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully cancel an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to cancel the order "1"
    Then the order "1" shall be marked as "<state>"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                           |
      | Penalized | Cannot cancel a penalized order |
      | Prepared  | Cannot cancel a prepared order  |
      | PickedUp  | Cannot cancel a picked up order |

  Scenario Outline: Successfully start a school year for a started order
    Given the order "<orderNumber>" is marked as "Started"
    When the admin attempts to start a school year for the order "<orderNumber>"
    Then the order "<orderNumber>" shall be marked as "Penalized"

    Examples:
      | orderNumber |
      |           1 |
      |           2 |
      |           3 |
      |           4 |

  Scenario Outline: Successfully start a school year for a paid order
    Given the order "<orderNumber>" is marked as "Paid"
    When the admin attempts to start a school year for the order "<orderNumber>"
    Then the order "<orderNumber>" shall be marked as "Prepared"

    Examples:
      | orderNumber |
      |           1 |
      |           2 |
      |           3 |
      |           4 |

  Scenario Outline: Unsuccessfully start a school year for an order due to non-existing order
    When the admin attempts to start a school year for the order "<orderNumber>"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully start a school year for an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the admin attempts to start a school year for the order "1"
    Then the order "1" shall be marked as "<state>"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                                    |
      | Penalized | The school year has already been started |
      | Prepared  | The school year has already been started |
      | PickedUp  | The school year has already been started |

  Scenario Outline: Successfully pay penalty for a penalized order
    Given the order "1" is marked as "Penalized"
    When the parent attempts to pay penalty for the order "1" with penalty authorization code "<code>" and authorization code "<authCode>"
    Then the order "1" shall be marked as "Prepared"
    Then the order "1" shall contain penalty authorization code "<code>"
    Then the order "1" shall contain authorization code "<authCode>"

    Examples:
      | code | authCode |
      | 1234 |     5678 |
      | 5678 |     1234 |

  Scenario Outline: Unsuccessfully pay penalty for an order due to wrong input
    Given the order "1" is marked as "Penalized"
    When the parent attempts to pay penalty for the order "1" with penalty authorization code "<code>" and authorization code "<authCode>"
    Then the order "1" shall be marked as "Penalized"
    Then the order "1" shall not contain penalty authorization code "<code>"
    Then the order "1" shall not contain authorization code "<authCode>"
    Then the error "<error>" shall be raised

    Examples:
      | code | authCode | error                                 |
      |      |     1234 | Penalty authorization code is invalid |
      | 1234 |          | Authorization code is invalid         |

  Scenario Outline: Unsuccessfully pay penalty for an order due to non-existing order
    When the parent attempts to pay penalty for the order "<orderNumber>" with penalty authorization code "1234" and authorization code "5678"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully pay penalty for an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the parent attempts to pay penalty for the order "1" with penalty authorization code "1234" and authorization code "5678"
    Then the order "1" shall be marked as "<state>"
    Then the error "<error>" shall be raised

    Examples:
      | state    | error                                    |
      | Started  | Cannot pay penalty for a started order   |
      | Paid     | Cannot pay penalty for a paid order      |
      | Prepared | Cannot pay penalty for a prepared order  |
      | PickedUp | Cannot pay penalty for a picked up order |

  Scenario Outline: Successfully pickup an order
    Given the order "<orderNumber>" is marked as "Prepared"
    When the student attempts to pickup the order "<orderNumber>"
    Then the order "<orderNumber>" shall be marked as "PickedUp"

    Examples:
      | orderNumber |
      |           1 |
      |           2 |
      |           3 |
      |           4 |

  Scenario Outline: Unsuccessfully pickup an order due to non-existing order
    When the student attempts to pickup the order "<orderNumber>"
    Then the number of orders in the system shall be "4"
    Then the error "<error>" shall be raised

    Examples:
      | orderNumber | error                   |
      |          10 | Order 10 does not exist |
      |          20 | Order 20 does not exist |

  Scenario Outline: Unsuccessfully pickup an order due to wrong state
    Given the order "1" is marked as "<state>"
    When the student attempts to pickup the order "1"
    Then the order "1" shall be marked as "<state>"
    Then the error "<error>" shall be raised

    Examples:
      | state     | error                           |
      | Started   | Cannot pickup a started order   |
      | Paid      | Cannot pickup a paid order      |
      | Penalized | Cannot pickup a penalized order |
      | PickedUp  | The order is already picked up  |
