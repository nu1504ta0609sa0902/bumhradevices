@cfs
Feature: Aa a user I would like to verify CFS features which are not related to ordering certificates
  So that I can be reassured my tests will not fail due to unexpected circumstances


  @1845 @1945 @_sprint18 @6024 @_sprint24 @readonly
  Scenario: Check application reference is correct format
    Given I am logged into appian as "businessAuto" user
    And I go to application WIP page
    Then Check the application reference number format is valid


  @1974 @_sprint15 @readonly
  Scenario: Users should be able to go to cfs page
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS


  @5571 @3856 @_sprint18 @readonly
  Scenario: Users should be able to go to BACK from the application
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    And I fill out the form called tell us about your organisation
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "yes" on the alert box
    Then I should see a list of manufacturers available for CFS


  @5571 @4203 @4698 @_sprint15 @_sprint18 @readonly
  Scenario Outline: Users should be able to go to BACK from the application 2
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "<alertOption>" on the alert box
    Then I should see a list of manufacturers available for CFS
    Examples:
      | user             | alertOption |
      | manufacturerAuto | Yes         |
      | manufacturerAuto | No          |


  @4330 @4203 @_sprint15 @_sprint16 @readonly
  Scenario: Users should be able to tell what stage of device registration they are in
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    Then I should see current stage of indication
