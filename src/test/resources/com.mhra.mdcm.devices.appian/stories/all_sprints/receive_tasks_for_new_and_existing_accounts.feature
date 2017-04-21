Feature: As a business user, I want a task to be created each time a customer submits a request for a new account
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-10 @mdcm-41 @mdcm-178 @_sprint1 @_sprint2 @bug
  Scenario Outline: Create new account as business user and approve tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    | link                |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom | New Account Request |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     | New Account Request |


  @regression @mdcm-41 @mdcm-178 @_sprint2 @bug
  Scenario Outline: Create new account as business user and reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and reject the task for following reason "<reason>"
#    Then The task should be removed from tasks list
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see no account matches
    Examples:
      | user         | accountType   | count | countryName | reason                             | link                |
      | businessNoor | manufacturer  | 0     | Turkey      | Account already exists             | New Account Request |
      | businessNoor | authorisedRep | 0     | Estonia     | No authorisation evidence provided | New Account Request |

  @regression @3761 @_sprint9 @ignore @wip
  Scenario Outline: Register my organisation button is displayed to UK Manufacturers who are not registered yet
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And Provide indication of devices made
    Then I should see stored manufacturer appear in the manufacturers list
    Examples:
      | user         | accountType   | logBackInAs       | approveReject | count | countryName    | link                |
      | businessNoor | manufacturer  | manufacturerNoor  | approve       | 1     | United Kingdom | New Account Request |
      | businessNoor | authorisedRep | authorisedRepNoor | approve       | 1     | Netherland     | New Account Request |


  @regression @mdcm-41 @_sprint2
  Scenario Outline: Verify WIP section shows newly created tasks and users can approve reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account in WIP page
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from WIP tasks list
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom |
      | businessNoor | manufacturer  | reject        | 0     | Turkey         |
      | businessNoor | authorisedRep | reject        | 0     | Estonia        |


  @regression @mdcm-178 @_sprint2
  Scenario Outline: Create new account and verify WIP task details are correct
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I should see a new task for the new account in WIP page
    Then validate task is displaying correct new account details
    Examples:
      | user         | accountType   | count | countryName | reason                             | link                |
      | businessNoor | manufacturer  | 0     | Turkey      | Account already exists             | New Account Request |
      | businessNoor | authorisedRep | 0     | Estonia     | No authorisation evidence provided | New Account Request |

