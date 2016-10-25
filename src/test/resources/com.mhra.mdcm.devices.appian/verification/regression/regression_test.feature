@Regression_Pack
Feature: As a user I would like to have a regression test suite which I can run when a new deployment is done
  So that I can make sure that no previously known issues are re-introduced

  Scenario Outline: Approve an account creation
    Given I am logged into appian as "<user>" user
    When I create a new account using test harness page
    Examples:
      | user         |
      | businessAuto |

  Scenario: Reject an account creation

  Scenario: View and amend an account