Feature: Able to add CFS for products and devices that are already registered
  So that manufacturers can export products outside EU without going through MHRA again


  @1974 @4330 @5141 @3979 @5212 @5126 @1845 @5128 @_sprint15 @_sprint17 @_sprint18
  Scenario: Users should be able to add a new cfs application with multiple devices
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid                         |
      | productModel   | FocusYeah                          |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2a                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Res                    |
      | customMade           | false                  |
      | riskClassification   | Class2b                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"


  @1961 @_sprint21
  Scenario Outline: Users should be able to approve and reject a cfs application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2a                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "<expectedStatus>" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"
    Examples:
      | user              | expectedStatus | approveReject |
#      | manufacturerAuto  | Completed      | approve       |
      | authorisedRepAuto | Completed      | reject        |


  @1992 @5960 @_sprint21
  Scenario Outline: Customers can order CFS for already registered manufacturers
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer "<searchTerm>"
    And I click on a random organisation which needs cfs
    And I search by "medical device name" for the value "random" and order cfs for a country with following data
      | countryName | <country> |
      | noOfCFS     | <noCFS>   |
    Then I should see the correct details in cfs review page
    When I submit payment for the CFS
    Examples:
      | country    | noCFS | logInAs           | searchTerm           |
      | Brazil     | 15    | manufacturerAuto  | ManufacturerRT01Test |
      | Bangladesh | 10    | authorisedRepAuto | AuthorisedRepRT01Test |


  @5679 @1954 @5680 @5681 @_sprint20 @5593 @5665 @_sprint21 @5673 @5674 @5673 @5674 @_sprint22
  Scenario: Business users should be able to view cfs new manufacturer and its devices and complete application
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    Then I should see the correct cfs manufacturer details
    And I should see correct device details
    When I view device with gmdn code "Blood weighing scale"
    Then I should see all the correct product and certificate details
    When I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" without completing the application
    And I approve all the cfs devices
    Then All the device status should update to "Approved"
    When I complete the already assigned task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"


  @5682 @5664 @_sprint21 @5673 @5674 @_sprint22
  Scenario Outline: Business users should be able approve or reject individual devices for cfs new application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" without completing the application
    When I select a single cfs devices
    Then All the device status should update to ""
    And I click to "<approveOrReject>" selected devices with following reasons "<reasons>"
    Then All the device status should update to "<status>"
    When I complete the already assigned task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"
    Examples:
      | user              | approveOrReject | status   | reasons          |
      | authorisedRepAuto | reject          | Rejected | Registered twice |
#      | manufacturerAuto  | approve         | Approved ||


  @5682 @5664 @_sprint21 @5673 @5674 @_sprint22
  Scenario Outline: Business users should be able approve or reject BULK devices for cfs new application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | manufacturer |
      | countryName | Brazil       |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | false                     |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | FordHybrid2               |
      | productModel   | FocusYeah3                |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" without completing the application
    When I select all the cfs devices
    And I click to "<approveOrReject>" selected devices with following reasons "<reasons>"
    Then All the device status should update to "<status>"
    When I complete the already assigned task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"
    Examples:
      | user              | approveOrReject | status   | reasons                 |
      | authorisedRepAuto | reject          | Rejected | Other,Registered twice |
#      | manufacturerAuto  | approve         | Approved ||

