Feature: As a UK based organisation I need to obtain a CERTIFICATE OF FREE SALE to export medical devices to non-EU countries


  @1974 @_sprint15
  Scenario: Users should be able to go to cfs page
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS


  @1974 @_sprint15 @wip
  Scenario: Users should be able to go to cfs page and add a new manufacturer
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | authorisedRep |
      | countryName | Brazil        |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details

  @1974 @_sprint15 @wip
  Scenario: Users should be able to go to cfs page and add to a random manufacturer from the list
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    #Then I should see the correct number of certificates "10" in review page
    Then I should see the correct details in cfs review page
    When I submit payment for the CFS


  @1974 @_sprint15 @wip
  Scenario: Users should be able to go to edit list of devices added for initial CFS process
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    Then I should see the correct details in cfs review page
    When I edit the list of devices added for CFS
    Then I should see the correct details in cfs review page

  @1974 @_sprint15 @wip
  Scenario: Users should be able to order CFS for multiple countries
    Given I am logged into appian as "authorisedRepNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for multiple countries with following data
      | listOfCFSCountryPair | Bangladesh=5,Brazil=10,United States=20|
    Then I should see multiple country details in cfs review page
    When I submit payment for the CFS



























  @wip @remove @ignore
  Scenario Outline: Previous steps
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    #Then I should see a new task for the new account
#    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    #And The completed task status should update to "Completed"
    When I go to records page and click on "Organisations"
    And I search for stored organisation in "Organisations" page
    Then All organisation search result should return 1 matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName |
#      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  |
