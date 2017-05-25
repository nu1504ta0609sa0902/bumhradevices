Feature: As a UK based organisation I need to obtain a CERTIFICATE OF FREE SALE to export medical devices to non-EU countries

  @1974 @_sprint15
  Scenario: Users should be able to go to cfs page
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS


  @5571 @3856 @_sprint18 @wip
  Scenario: Users should be able to go to BACK from the application
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    And I fill out the form called tell us about your organisation
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "yes" on the alert box
    Then I should see a list of manufacturers available for CFS


  @5571 @4203 @4698 @_sprint18 @wip
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

  @4330 @_sprint16 @wip
  Scenario: Users should be able to tell what stage of device registration they are in
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    Then I should see current stage of indication

  @1974 @4330 @5141 @3979 @5212 @_sprint15 @wip
  Scenario: Users should be able to go to cfs page and add a new manufacturer
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Medical Devices |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid                         |
      | productModel   | FocusYeah                          |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2b                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Res                    |
      | customMade           | false                  |
      | riskClassification   | Class1                 |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And Proceed to payment and confirm submit device details


  @1974 @_sprint15 @1989 @wip
  Scenario: Users should be able to go to cfs page and add device to existing manufacturer
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
#    When I click on a random organisation which needs cfs
    When I click on a organisation name begins with "TestNoor" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType         | Active Implantable Medical Devices |
      | gmdnDefinition     | Desiccating chamber                |
      | customMade         | false                              |
      | notifiedBody       | NB 0086 BSI                        |
      | listOfProductNames | ford,hyundai                       |
    And Proceed to payment and confirm submit device details

  @1974 @_sprint15 @wip
  Scenario Outline: Users should be able to go to cfs page and add to a random manufacturer from the list
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs review page
    When I submit payment for the CFS
    Examples:
      | country    | noCFS |
      | Brazil     | 15    |
      | Bangladesh | 10    |


  @1974 @1978 @_sprint15 @wip
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


  @1974 @1978 @_sprint15 @wip
  Scenario: Users should be able to go to edit country and number of certificates
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    Then I should see the correct details in cfs review page
    When I update the country added for CFS to "Bangladesh"
    And I update the no of certificates for CFS to 9
    Then I should see the correct details in cfs review page


  @1974 @1978 @_sprint15 @wip
  Scenario Outline: Users should be able to order CFS for multiple countries
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for multiple countries with following data
      | listOfCFSCountryPair | <countryAndCertificateNumber> |
    Then I should see correct details for all the countries and certificate in cfs review page
    When I submit payment for the CFS
    Examples:
      | countryAndCertificateNumber                                                |
      | Switzerland=5,Norway=10,British Virgin=15,British Indian=1,Lichtenstein=20 |
      | Bangladesh=5,Brazil=2,United States=3                                      |
      | Turkey=5,Iceland=10,United States=20                                       |


#  @1974 @_sprint15 @ignore
#  Scenario: Users should be able to update multiple country and number of certificates
#    Given I am logged into appian as "manufacturerNoor" user
#    And I go to device certificate of free sale page
#    Then I should see a list of manufacturers available for CFS
#    When I click on a random organisation which needs cfs
#    And I order cfs for multiple countries with following data
#      | listOfCFSCountryPair | Bangladesh=5,Brazil=10,United States=20 |
#    Then I should see correct details for all the countries and certificate in cfs review page
#    And I update cfs for multiple countries with following data
#      | listOfCFSCountryPair | Bangladesh=51,Brazil=10|
