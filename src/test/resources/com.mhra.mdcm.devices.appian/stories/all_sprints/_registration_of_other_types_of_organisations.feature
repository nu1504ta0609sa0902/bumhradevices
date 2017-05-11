Feature: As a customer I want to register other types of organisations such as Distributors
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @2109 @_sprint13 @wip @bug
  Scenario Outline: Create new distributor account as business user and approve the tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    And I assign the task to me and "approve" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I logout and log back into appian as "<logBackInAs>" user
    And I go to list of manufacturers page
    Then I should see error message "<errorMessage>" in instead of list of manufacturers
    Examples:
      | user         | accountType | count | countryName | link                | logBackInAs      | errorMessage                                                                                        |
      | businessNoor | distributor | 1     | Turkey      | New Account Request | manufacturerNoor | Unfortunately, you are not eligible for registering the devices. Only manufacturers based in the UK |


  @regression @1996 @_sprint9 @2833 @_sprint14 @wip @bug
  Scenario Outline: Check new role added to create distributor account as business user and approve the tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account in WIP page
    Then validate task is displaying correct new account details
    And I assign the task to me and "approve" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I go to records page and click on "<pageHeading>"
    And I perform a search for "<searchFor>" in "<pageHeading>" page
    Then I should see at least <count> matches in "<pageHeading>" page search results
    Examples:
      | user         | accountType | count | countryName | link                | pageHeading   | searchFor   |
      | businessNoor | distributor | 1     | Turkey      | New Account Request | Organisations | Distributor |

