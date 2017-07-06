@ignore
Feature: As a customer I want to receive email notifications when ever a account or manufacturer is created or updated
  So that I am aware of what is going on

  @regression @2191 @_sprint10
  Scenario Outline: Email should be generated for newly created account for business user
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "<emailHeader>"
    Examples:
      | user         | accountType   | approveReject | countryName    | emailHeader                  |
      | businessNoor | manufacturer  | approve       | United Kingdom | Account request approved for |
      | businessNoor | authorisedRep | approve       | Netherland     | Account request approved for |


  @regression @2192 @_sprint10 @bug
  Scenario Outline: Email should be generated for new business accounts which are rejected
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "reject" with following "<reason>"
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "New Account Rejected"
    Examples:
      | user         | accountType   | countryName | reason                             | link                    |
      | businessNoor | manufacturer  | Turkey      | Account already exists             | New Account Request for |
      | businessNoor | authorisedRep | Estonia     | No authorisation evidence provided | New Account Request for |


  @regression @2191 @2193 @2190 @_sprint10 @3207 @2151 @_sprint21
  Scenario Outline: Email should be generated for newly created manufacturers and authorisedReps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
      | productName    | Product1               |
    And Proceed to payment and confirm submit device details
    Then I should received an email with subject heading "WorldPay Payment"
    When I logout and log back into appian as "<logBackInAs>" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "<emailHeading>" and stored application identifier
    And I should received an email for stored manufacturer with heading "has been Approved" and stored application identifier
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | countryName | emailHeading                          |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | Brazil      | Request for manufacturer registration |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | Belarus     | Request for manufacturer registration |

  @regression @2192 @2190 @_sprint10 @2151 @3207 @_sprint21 @wip @bug
  Scenario Outline: Email should be generated for newly created manufacturers and authorisedReps which are rejected
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | false                   |
      | productName    | Product1               |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    And I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "reject" with following "<reason>"
    Then The task status in AWIP page should be "Completed" for the new account
    Then I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "Manufacturer Registration Request for"
    Examples:
      | user              | logBackInAs  | accountType   | reason                 | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | Rejected because I can | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | Rejected because I can | Belarus     |


  @regression @1836 @_sprint8 @2193 @2190 @2192 @_sprint10 @bug
  Scenario Outline: Email notification should be generated when organisation is approved for registration service
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType           | <accountType>           |
      | accountNameBeginsWith | <accountNameBeginsWith> |
      | countryName           | United Kingdom          |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "Account request approved for"
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA12345A"
#    Log back in as manufacturer/authorisedRep
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
    When I logout and log back into appian as "<user>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "has been Approved" and stored application identifier
    Examples:
      | user         | accountType   | approveReject | logBackInAs       | countryName   | accountNameBeginsWith    |
      | businessNoor | manufacturer  | approve       | manufacturerNoor  | Bangladesh    | ManufacturerAccountRT00  |
      | businessNoor | authorisedRep | reject        | authorisedRepNoor | United States | AuthorisedRepAccountRT00 |
