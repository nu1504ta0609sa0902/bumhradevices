@Regression_Pack
Feature: As a user I would like to have a regression test suite which I can run when a new deployment is done
  So that I can make sure that no previously known issues are re-introduced


  Scenario Outline: Approve and reject new account creation
    Given I am logged into appian as "<user>" user
    When I create a new account using test harness page
    Then I should see a new task for the new account
    When I accept to update the task and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | approveReject | count |
      | businessAuto | approve       | 1     |
      | businessAuto | reject        | 0     |

  Scenario Outline: View and amend an account
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I view a randomly selected account
    Examples:
      | user         | link     | count |
      | businessAuto | Accounts | 1     |