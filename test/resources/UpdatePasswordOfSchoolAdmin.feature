Feature: Update Password of School Admin (p7)
As the school admin, I want to update the password of a school admin in the system.

  Background:
    Given the following school admin entities exists in the system (p7)
      | email         | password |
      | admin@cool.ca |     1!We |

  Scenario Outline: Successfully update the password of a school admin in the system
    When the school admin attempts to update school admin "<email>" in the system with password "<updatedPassword>" (p7)
    Then the number of school admin entities in the system shall be "1" (p7)
    Then the school admin "<email>" with password "<updatedPassword>" shall exist in the system (p7)

    Examples:
      | email         | updatedPassword |
      | admin@cool.ca |            2#Er |

  Scenario Outline: Unsuccessfully update the password of a school admin in the system with invalid values
    When the school admin attempts to update school admin "<email>" in the system with password "<updatedPassword>" (p7)
    Then the number of school admin entities in the system shall be "1" (p7)
    Then the school admin "<email>" with password "<updatedPassword>" shall not exist in the system (p7)
    Then the following school admin entities shall exist in the system (p7)
      | email         | password |
      | admin@cool.ca |     1!We |
    Then the error "<error>" shall be raised (p7)

    Examples:
      | email         | updatedPassword | error                                                                                                      |
      | admin@cool.ca |             123 | Password must be at least four characters long.                                                            |
      | admin@cool.ca | WeWe            | Password must contain a special character out of !#$, an upper case character, and a lower case character. |
      | admin@cool.ca | !WWW            | Password must contain a special character out of !#$, an upper case character, and a lower case character. |
      | admin@cool.ca | !eee            | Password must contain a special character out of !#$, an upper case character, and a lower case character. |
