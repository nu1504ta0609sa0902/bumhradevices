@smoke_test_all
Feature: As a tester I would like to perform smoke test on the system
  So that I can verify the stability of the system before we start doing any testing

  @smoke_test
  Scenario Outline: As a user I should see error message when credentials are incorrect
    Given I try to login to appian as username "<user>" and password "<password>"
    Then I should see error message with text "<errorMsg>"
    Examples:
      | user                 | password | errorMsg                                 |
      | businessAuto         | testTest | The username/password entered is invalid |
      | manufacturerAuto     | testTest | The username/password entered is invalid |
      | authorisedrepAuto    | testTest | The username/password entered is invalid |


  @smoke_test
  Scenario Outline: As a user I should be able to login and logout
    Given I am logged into appian as "<user>" user
    Then I should see correct page heading "<heading>"
    When I logout of the application
    Then I should be in login page
    Examples:
      | user                 | heading      |
      | businessAuto         | Tasks        |
      | manufacturerAuto     | MHRA Service |
      | authorisedrepAuto    | MHRA Service |


  @smoke_test
  Scenario Outline: As a business user I should be able to navigate to different sections
    Given I am logged into appian as "<user>" user
    When I go to "<page>" page
    Then I should see correct page heading "<pageHeading>"
    Examples:
      | user            | page    | pageHeading |
      | businessAuto    | Records | Records     |
      | businessAuto    | News    | News        |
      | businessAuto    | Tasks   | Tasks       |
      | businessAuto    | Reports | Reports     |
      | businessAuto    | Actions | Actions     |


  @smoke_test
  Scenario Outline: As a business user I should be able to view accounts, devices and products page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    Examples:
      | user            | pageHeading | link     |
      | businessAuto    | Accounts    | Accounts |
      | businessAuto    | Devices     | Devices  |
      | businessAuto    | Products    | Products |


  @smoke_test
  Scenario Outline: Check correct links are displayed and links clickable for Manufacturer and AuthorisedRep
    Given I am logged into appian as "<user>" user
    Then I should see the following portal "<expectedLinks>" links
    And All the links "<expectedLinks>" are clickable
    Examples:
      | user                 | expectedLinks             |
      | manufacturerAuto     | Manufacturer Registration |
      | authorisedrepAuto    | Manufacturer Registration |


  @smoke_test
  Scenario Outline: Create a new account request task
    Given I am logged into appian as "<user>" user
    When I create a new account using test harness page
    Then I should see a new task for the new account
    Examples:
      | user            |
      | businessAuto    |