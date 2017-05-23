Feature: As a UK based organisation I need to obtain a CERTIFICATE OF FREE SALE to export medical devices to non-EU countries

  @1974 @_sprint15
  Scenario: Users should be able to go to cfs page
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS


  @5571 @_sprint18 @wip
  Scenario: Users should be able to go to BACK from the application
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    And I fill out the form called tell us about your organisation
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "yes" on the alert box
    Then I should see a list of manufacturers available for CFS


  @5571 @_sprint18 @wip
  Scenario Outline: Users should be able to go to BACK from the application 2
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "<alertOption>" on the alert box
    Then I should see a list of manufacturers available for CFS
    Examples:
      | user             | alertOption |
      | manufacturerNoor | Yes         |
      | manufacturerNoor | No          |

  @1974 @_sprint15 @wip
  Scenario: Users should be able to go to cfs page and add a new manufacturer
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created manufacturer with following data
#    When I add multiple devices to SELECTED manufacturer with following data
      | deviceType         | Active Implantable Medical Devices |
      | gmdnDefinition     | Desiccating chamber                |
      | customMade         | false                               |
      | listOfProductNames | ford,hyundai                       |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And I add another device to SELECTED manufacturer with following data
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
      | listOfCFSCountryPair | Bangladesh=5,Brazil=10,United States=20 |
    Then I should see multiple country details in cfs review page
    When I submit payment for the CFS

