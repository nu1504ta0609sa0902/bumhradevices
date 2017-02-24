Feature: As a business user I want to view all the organisations associated with an account, not just the account holder details
  so that I can understand relationships between account holders and organisations, and retrieve information quickly

  @mdcm-126 @readonly @sprint1 @mdcm-125 @sprint6 @wip
  Scenario Outline: As a business user I should be able to view specific account in all accounts page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    And I search for account with following text "RT01Test_"
    And I view a random account from search result
    Then I should see account displaying correct fields
    Examples:
      | user         | link     | pageHeading | columns                                                                                                          |
      | businessAuto | Accounts | Accounts    | Organisation name,Account number,Organisation role,Contact name,Organisation address,Organisation country,Status |


  @regression @mdcm-125 @sprint6 @wip
  Scenario Outline: Business users should be able to view all the associated organisations related with an account
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | General Medical Device |
      | gmdnDefinition         | Blood weighing scale    |
      | customMade             | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    And I search accounts for the stored organisation name
    And I view a random account from search result
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | count | countryName |link     | pageHeading |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | 1     | Brazil       |Accounts | Accounts    |
#      | authorisedRepAuto | businessAuto | authorisedRep | approve       | 0     | Belarus     |Accounts | Accounts    |