Feature: As a business user, I want a task to be created each time a customer submits a request for a new account
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-10 @mdcm-41 @mdcm-178
  Scenario Outline: Create new account and approve tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessAuto | manufacturer  | approve       | 1     | United Kingdom |
      | businessAuto | authorisedRep | approve       | 1     | Netherland     |


  @regression @mdcm-41 @mdcm-178
  Scenario Outline: Create new account and reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and reject the task for following reason "<reason>"
    Then The task should be removed from tasks list
    And The task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName | reason                             |
      | businessAuto | manufacturer  | reject        | 0     | Turkey      | Account already exists             |
      | businessAuto | authorisedRep | reject        | 0     | Estonia     | No authorisation evidence provided |


  @regression @mdcm-41
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
      | user         | accountType   | approveReject | count | countryName |
      | businessAuto | manufacturer  | approve       | 1     | United Kingdom |
      | businessAuto | authorisedRep | approve       | 1     | Netherland     |
      | businessAuto | manufacturer  | reject        | 0     | Turkey      |
      | businessAuto | authorisedRep | reject        | 0     | Estonia     |

