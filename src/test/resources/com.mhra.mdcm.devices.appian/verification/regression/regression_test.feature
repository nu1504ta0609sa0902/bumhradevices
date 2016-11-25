@Regression_Pack
Feature: As a user I would like to have a regression test suite which I can run when a new deployment is done
  So that I can make sure that no previously known issues are re-introduced


  Scenario Outline: Approve and reject tasks new account creation
    Given I am logged into appian as "<user>" user
    When I create a new account using test harness page
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | approveReject | count |
      | businessAuto | approve       | 1     |
      | businessAuto | reject        | 0     |


  # Assumes there is at least 1 account
  Scenario Outline: View and amend an existing account
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see the following columns for "<link>" page
      | columns | <columns> |
    When I search for account with following text "OrganisationTest Active"
    And I select a random account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link              | keyValuePairs        | columns                                                                                                             |
      | businessAuto | Accounts          | job.title=The Editor | Organisation Name,Account Number,Organisation Role, Contact Name, Organisation Address,Organisation Country, Status |


  Scenario Outline: Approve New manufacturer registration including device declaration.
    Given I am logged into appian as "<user>" user
    Examples:
      | user             |
      | manufacturerAuto |

  Scenario Outline: REJECT New manufacturer registration including device declaration.
    Given I am logged into appian as "<user>" user
    Examples:
      | user             |
      | manufacturerAuto |

