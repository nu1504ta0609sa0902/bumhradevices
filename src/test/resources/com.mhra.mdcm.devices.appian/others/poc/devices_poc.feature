Feature: Write PC for devices

  @poc
  Scenario Outline: Login to devices app
    Given I am logged into appian as "<user>" user
    Then I should see name of logged in user as "<user>"
    Examples:
      | user           |
      | businessAuto      |