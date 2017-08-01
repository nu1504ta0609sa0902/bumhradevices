@ignore
Feature: As a business user, I want a task to be created when new account request is submitted
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-10 @2327 @mdcm-41 @2311 @mdcm-178 @2263 @_sprint1 @_sprint2 @2328 @_sprint19 @create_new_org
  Scenario Outline: Create new account as business user and approve tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Accounts"
    When I search for stored organisation in "Accounts" page
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     |


  @regression @mdcm-41 @2311 @mdcm-178 @2263 @_sprint2 @2328 @_sprint19 @create_new_org
  Scenario Outline: Create new account as business user and reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    When I search and view new task in AWIP page for the new account
    And I assign the AWIP page task to me and "reject" with following "<reason>"
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Accounts"
    When I search for stored organisation in "Accounts" page
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | count | countryName | reason                             |
      | businessNoor | manufacturer  | 1     | Turkey      | Account already exists             |
      | businessNoor | authorisedRep | 1     | Estonia     | No authorisation evidence provided |


  @3761 @_sprint9 @create_new_org @ignore @bug
  Scenario Outline: Register my organisation button is displayed to UK Manufacturers who are not registered yet
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    When I search and view new task in AWIP page for the new account
    And I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA12345A"
    And I go to list of manufacturers page
    And Provide indication of devices made
    Then I should see stored manufacturer appear in the manufacturers list
    Examples:
      | user         | accountType   | logBackInAs       | approveReject | count | countryName    |
      | businessNoor | manufacturer  | manufacturerNoor  | approve       | 1     | United Kingdom |
      | businessNoor | authorisedRep | authorisedRepNoor | approve       | 1     | Netherland     |


  @regression @mdcm-41 @2311 @_sprint2 @3365 @_sprint7 @2833 @_sprint14 @create_new_org
  Scenario Outline: Verify WIP section shows newly created tasks and users can approve reject tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Accounts"
    When I search for stored organisation in "Accounts" page
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom |
      | businessNoor | manufacturer  | reject        | 1     | Turkey         |
      | businessNoor | authorisedRep | reject        | 1     | Estonia        |


  @regression @mdcm-178 @2263 @_sprint2 @create_new_org @bug
  Scenario Outline: Create new account and verify WIP task details are correct
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I search and view new task in AWIP page for the new account
    Then validate task is displaying correct new account details
    Examples:
      | user         | accountType   | count | countryName | reason                             |
      | businessNoor | manufacturer  | 0     | Turkey      | Account already exists             |
      | businessNoor | authorisedRep | 0     | Estonia     | No authorisation evidence provided |


  @1945 @_sprint18 @create_new_org
  Scenario Outline: Create new account as business user and approve AWIP tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Accounts"
    When I search for stored organisation in "Accounts" page
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessNoor | manufacturer  | approve       | 1     | United Kingdom |
      | businessNoor | authorisedRep | approve       | 1     | Netherland     |

  @1945 @_sprint18 @create_new_org
  Scenario Outline: Create new account as business user and reject AWIP tasks
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Accounts"
    When I search for stored organisation in "Accounts" page
    Then I should see at least <count> account matches
    Examples:
      | user         | accountType   | approveReject | count | countryName    |
      | businessNoor | manufacturer  | reject        | 1     | United Kingdom |
      | businessNoor | authorisedRep | reject        | 1     | Netherland     |

  @1945 @_sprint18
  Scenario Outline: Verify application reference number format in AWIP tasks page is correct
    Given I am logged into appian as "<user>" user
    And I go to AWIP page
    Then Check the application reference number format is valid
    Examples:
      | user         |
      | businessNoor |