@ignore
Feature: As a customer I want to receive email notifications when ever a account or manufacturer is created or updated
  So that I am aware of what is going on

  @regression @2191 @sprint10
  Scenario Outline: Email should be received for newly created account for business user
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    And I should received an email for stored account with heading "<emailHeader>"
    Examples:
      | user         | accountType   | approveReject | countryName    | link                | emailHeader             |
      | businessNoor | manufacturer  | approve       | United Kingdom | New Account Request | New Account Request for |
      | businessNoor | authorisedRep | approve       | Netherland     | New Account Request | New Account Request for |


  @regression @2192 @sprint10
  Scenario Outline: Email should be received for new business accounts which are rejected
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and reject the task for following reason "<reason>"
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status of new account should update to "Completed"
    And I should received an email for stored account with heading "New Account Rejected"
    Examples:
      | user         | accountType   | countryName | reason                             | link                |
      | businessNoor | manufacturer  | Turkey      | Account already exists             | New Account Request |
      | businessNoor | authorisedRep | Estonia     | No authorisation evidence provided | New Account Request |


  @regression @2191 @2193 @sprint10
  Scenario Outline: Email should be received for newly created manufacturers and authorisedReps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout and log back into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    And The completed task status should update to "Completed"
    And I should received an email for stored manufacturer with heading "Manufacturer registration service"
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | Belarus     |

  @regression @2192 @sprint10
  Scenario Outline: Email should be received for newly created manufacturers and authorisedReps which are rejected
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout and log back into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and reject the task for following reason "<reason>"
    Then The task should be removed from tasks list
    And The completed task status should update to "Completed"
    And I should received an email for stored manufacturer with heading "Manufacturer registration service"
    Examples:
      | user              | logBackInAs  | accountType   | reason                 | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | Rejected because I can | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | Rejected because I can | Belarus     |


  @regression @2193 @sprint10
  Scenario Outline: Email notification should be received when organisation is approved for registration service
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType           | <accountType>           |
      | accountNameBeginsWith | <accountNameBeginsWith> |
      | countryName           | United Kingdom          |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task with link "<link>" should be removed from tasks list
    And The completed task status should update to "Completed"
#    Log back in as manufacturer/authorisedRep
    When I logout and log back into appian as "<logBackInAas>" user
    And I go to list of manufacturers page
    And Provide indication of devices made
    And I click on register new manufacturer
    And I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
#    Log back in as business and verify email is received
    When I logout and log back into appian as "<user>" user
    Then I view new task with link "New Service Request" for the new account
    And I assign the task to me and "approve" the generated task
    And The completed task status should update to "Completed"
    And I should received an email for stored manufacturer with heading "Manufacturer registration service"
    Examples:
      | user         | accountType   | approveReject | logBackInAas      | countryName | link                | accountNameBeginsWith |
      | businessNoor | manufacturer  | approve       | manufacturerNoor  | Bangladesh  | New Account Request | ManufacturerRT00      |
      | businessNoor | authorisedRep | approve       | authorisedRepNoor | Netherland  | New Account Request | AuthorisedRepRT00     |
