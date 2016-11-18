Feature: As an account holder with access to the device registration service
  I want to be able to enter more than one device at the point of registration
  So that I don't have to return to add further devices post-registration

  @mdcm-143 @readonly
  Scenario Outline: Check correct links are displayed and links clickable for Manufacturer and AuthorisedRep
    Given I am logged into appian as "<user>" user
    Then I should see the following portal "<expectedLinks>" links
    And All the links "<expectedLinks>" are clickable
    Examples:
      | user                 | expectedLinks             |
      | manufacturerAuto     | Manufacturer Registration |
      | authorisedrepAuto    | Manufacturer Registration |

