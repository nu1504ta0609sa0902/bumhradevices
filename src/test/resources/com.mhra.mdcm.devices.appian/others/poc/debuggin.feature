@ignore

Feature: Help with debugging

  Scenario: Debug to help with testing
    Given I am logged into appian as "businessAuto" user
#    When I go to WIP tasks page
    Then I should see a new task for the new account in WIP page