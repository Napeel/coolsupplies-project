Feature: Get order
As the parent / school admin, I want to get an order from the system

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
      | Bundle 5 |       20 |        5th |
      | Bundle 6 |       10 |        6th |
    Given the following bundle item entities exist in the system
      | quantity | level       | gradeBundleName | itemName |
      |        3 | Mandatory   | Bundle 5        | pencil   |
      |        2 | Recommended | Bundle 5        | textbook |
      |        2 | Mandatory   | Bundle 6        | pencil   |
      |        1 | Recommended | Bundle 6        | textbook |
      |        1 | Optional    | Bundle 6        | eraser   |
    Given the following order entities exist in the system
      | number | date       | level       | parentEmail   | studentName | status    | authorizationCode | penaltyAuthorizationCode |
      |      1 | 2024-01-01 | Mandatory   | mom@gmail.com | Aaron       | Started   |                   |                          |
      |      2 | 2024-01-01 | Recommended | mom@gmail.com | Aaron       | Paid      |              1234 |                          |
      |      3 | 2024-01-01 | Optional    | mom@gmail.com | Wendy       | Penalized |                   |                          |
      |      4 | 2024-01-01 | Mandatory   | dad@gmail.com | Sarah       | Prepared  |              1235 |                          |
      |      5 | 2024-01-01 | Recommended | dad@gmail.com | Sarah       | PickedUp  |              1236 |                     5678 |
      |      6 | 2024-01-01 | Optional    | dad@gmail.com | Sarah       | Paid      |              1237 |                          |
    Given the following order item entities exist in the system
      | quantity | orderNumber | itemName |
      |        3 |           1 | eraser   |
      |        1 |           1 | Bundle 5 |
      |        3 |           2 | eraser   |
      |        2 |           3 | Bundle 5 |
      |        1 |           4 | Bundle 6 |
      |        1 |           5 | Bundle 6 |
      |        2 |           6 | Bundle 6 |

  Scenario: Successfully get first order from the system
    When the parent attempts to get from the system the order with number "1"
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status  | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | mom@gmail.com | Aaron       | Started |      1 | 2024-01-01 | Mandatory   |                   |                          |          9 |
    Then the following order items shall be presented for the order with number "1"
      | quantity | itemName | gradeBundleName | price | discount |
      |        3 | eraser   |                 |     1 |          | 
      |        3 | pencil   | Bundle 5        |     2 |          |

  Scenario: Successfully get second order from the system
    When the parent attempts to get from the system the order with number "2"
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | mom@gmail.com | Aaron       | Paid   |      2 | 2024-01-01 | Recommended |              1234 |                          |          3 |
    Then the following order items shall be presented for the order with number "2"
      | quantity | itemName | gradeBundleName | price | discount |
      |        3 | eraser   |                 |     1 |          | 

  Scenario: Successfully get third order from the system
    When the parent attempts to get from the system the order with number "3"
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status    | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | mom@gmail.com | Wendy       | Penalized |      3 | 2024-01-01 | Optional    |                   |                          |      489.6 |
    Then the following order items shall be presented for the order with number "3"
      | quantity | itemName | gradeBundleName | price | discount |
      |        6 | pencil   | Bundle 5        |     2 |     -0.4 |
      |        4 | textbook | Bundle 5        |   150 |      -30 |

  Scenario: Successfully get fourth order from the system
    When the parent attempts to get from the system the order with number "4"
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status   | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | dad@gmail.com | Sarah       | Prepared |      4 | 2024-01-01 | Mandatory   |              1235 |                          |          4 |
    Then the following order items shall be presented for the order with number "4"
      | quantity | itemName | gradeBundleName | price | discount |
      |        2 | pencil   | Bundle 6        |     2 |          |

  Scenario: Successfully get fifth order from the system
    When the parent attempts to get from the system the order with number "5"
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status   | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | dad@gmail.com | Sarah       | PickedUp |      5 | 2024-01-01 | Recommended |              1236 |                     5678 |      138.6 |
    Then the following order items shall be presented for the order with number "5"
      | quantity | itemName | gradeBundleName | price | discount |
      |        2 | pencil   | Bundle 6        |     2 |     -0.2 |
      |        1 | textbook | Bundle 6        |   150 |      -15 |

  Scenario: Successfully get sixth order from the system
    When the parent attempts to get from the system the order with number "6"
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | dad@gmail.com | Sarah       | Paid   |      6 | 2024-01-01 | Optional    |              1237 |                          |        279 |
    Then the following order items shall be presented for the order with number "6"
      | quantity | itemName | gradeBundleName | price | discount |
      |        4 | pencil   | Bundle 6        |     2 |     -0.2 |
      |        2 | textbook | Bundle 6        |   150 |      -15 |
      |        2 | eraser   | Bundle 6        |     1 |     -0.1 | 

  Scenario: Unsuccessfully get an order that does not exist in the system
    When the parent attempts to get from the system the order with number "7"
    Then the number of orders in the system shall be "6"
    Then no order entities shall be presented

  Scenario: Successfully get all orders from the system
    When the school admin attempts to get from the system all orders
    Then the number of orders in the system shall be "6"
    Then the following order entities shall be presented
      | parentEmail   | studentName | status    | number | date       | level       | authorizationCode | penaltyAuthorizationCode | totalPrice |
      | mom@gmail.com | Aaron       | Started   |      1 | 2024-01-01 | Mandatory   |                   |                          |          9 |
      | mom@gmail.com | Aaron       | Paid      |      2 | 2024-01-01 | Recommended |              1234 |                          |          3 |
      | mom@gmail.com | Wendy       | Penalized |      3 | 2024-01-01 | Optional    |                   |                          |      489.6 |
      | dad@gmail.com | Sarah       | Prepared  |      4 | 2024-01-01 | Mandatory   |              1235 |                          |          4 |
      | dad@gmail.com | Sarah       | PickedUp  |      5 | 2024-01-01 | Recommended |              1236 |                     5678 |      138.6 |
      | dad@gmail.com | Sarah       | Paid      |      6 | 2024-01-01 | Optional    |              1237 |                          |        279 |
    Then the following order items shall be presented for the order with number "1"
      | quantity | itemName | gradeBundleName | price | discount |
      |        3 | eraser   |                 |     1 |          | 
      |        3 | pencil   | Bundle 5        |     2 |          |
    Then the following order items shall be presented for the order with number "2"
      | quantity | itemName | gradeBundleName | price | discount |
      |        3 | eraser   |                 |     1 |          | 
    Then the following order items shall be presented for the order with number "3"
      | quantity | itemName | gradeBundleName | price | discount |
      |        6 | pencil   | Bundle 5        |     2 |     -0.4 |
      |        4 | textbook | Bundle 5        |   150 |      -30 |
   Then the following order items shall be presented for the order with number "4"
      | quantity | itemName | gradeBundleName | price | discount |
      |        2 | pencil   | Bundle 6        |     2 |          |
    Then the following order items shall be presented for the order with number "5"
      | quantity | itemName | gradeBundleName | price | discount |
      |        2 | pencil   | Bundle 6        |     2 |     -0.2 |
      |        1 | textbook | Bundle 6        |   150 |      -15 |
    Then the following order items shall be presented for the order with number "6"
      | quantity | itemName | gradeBundleName | price | discount |
      |        4 | pencil   | Bundle 6        |     2 |     -0.2 |
      |        2 | textbook | Bundle 6        |   150 |      -15 |
      |        2 | eraser   | Bundle 6        |     1 |     -0.1 | 
