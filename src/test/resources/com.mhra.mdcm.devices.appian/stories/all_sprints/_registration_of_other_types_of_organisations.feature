Feature: As a customer I want to register other types of organisations such as Distributors
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @2109 @sprint13 @wip
  Scenario Outline: Create new distributor account as business user and approve the tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    And I assign the task to me and "approve" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I go to list of manufacturers page
    Then I should see error message "<errorMessage>" in instead of list of manufacturers
    When I click the back button
    Then I should be in the portal home page
    Examples:
      | user         | accountType | count | countryName | link                | logBackInAs | errorMessage |
      | businessNoor | distributor | 1     | Turkey      | New Account Request | manufacturerNoor | Unfortunately, you are not eligible for registering the devices. Only manufacturers based in the UK |
#      | businessNoor | authorisedRep | 0     | Switzerland     | No authorisation evidence provided | New Account Request |
