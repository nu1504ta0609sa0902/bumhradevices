@cfs
Feature: Able to add CFS for products and devices that are already registered
  So that manufacturers can export products outside EU without going through MHRA again


  @1974 @4330 @5141 @3979 @5212 @5126 @1845 @5128 @5349 @_sprint21 @_sprint15 @_sprint17 @_sprint18 @create_new_org
  Scenario Outline: Users should be able to add a new cfs application with multiple devices
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
      | productName    | Product1                  |
      | productModel   | Model1                    |
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
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @smoke_test_cfs @1961 @_sprint21 @create_new_org
  Scenario Outline: Users should be able to approve and reject a cfs application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | Brazil        |
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
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | user              | expectedStatus | approveReject | accountType   |
      | manufacturerAuto  | Completed      | approve       | manufacturer  |
      | authorisedRepAuto | Completed      | reject        | authorisedRep |


  @5679 @1954 @5680 @5681 @_sprint20 @5593 @5665 @5349 @_sprint21 @5674 @5674 @_sprint22 @6024 @_sprint24 @create_new_org
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
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier


  @5682 @5664 @_sprint21 @5673 @5674 @_sprint22 @_sprint23 @create_new_org
  Scenario Outline: Business users should be able approve or reject individual devices for cfs new application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | Brazil        |
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
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | user              | approveOrReject | status   | reasons          | accountType   |
      | authorisedRepAuto | reject          | Rejected | Registered twice | authorisedRep |
      | manufacturerAuto  | approve         | Approved |                  | manufacturer  |


  @5682 @5664 @_sprint21 @5673 @5674 @_sprint22 @_sprint23 @create_new_org
  Scenario Outline: Business users should be able approve or reject BULK devices for cfs new application
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | Brazil        |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber       |
      | customMade     | false                     |
      | notifiedBody   | NB 0086 BSI               |
      | productName    | Product12                 |
      | productModel   | Model13                   |
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
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
    Examples:
      | user              | approveOrReject | status   | reasons                | accountType   |
      | authorisedRepAuto | reject          | Rejected | Other,Registered twice | authorisedRep |
      | manufacturerAuto  | approve         | Approved |                        | manufacturer  |


  @6024 @_sprint24 @create_new_org
  Scenario Outline: Verify Application WIP page entry data is correct for new CFS applications
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | Brazil        |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search for task in AWIP page for the manufacturer
    Then Verify the AWIP entry details for the new cfs manufacturer application
    Examples:
      | logInAs           | accountType   |
      | manufacturerAuto  | manufacturer  |
      | authorisedRepAuto | authorisedRep |

  @4329 @4744 @_sprint23 @create_new_org @wip
  Scenario Outline: Register a manufacturer for CFS and verify it appears in the registered manufacturers list with correct devices
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | Brazil        |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    When I logout and log back into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search and view for the newly created cfs manufacturer
    Then I should see the correct details in the summary tab
    And Verify devices displayed and GMDN details are correct for CFS
    Examples:
      | logInAs           | accountType   |
      | manufacturerAuto  | manufacturer  |
      | authorisedRepAuto | authorisedRep |

  @1992 @5349 @_sprint21 @6012 @_sprint22 @create_new_org
  Scenario Outline: Users should be able to search and filter for products and devices
    Given I am logged into appian as "<logInAs>" user
# Submit a new CFS manufacturer application
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
      | productName    | Product1NU1               |
      | productModel   | Model1NU1                 |
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
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading containing "Free Sale" and stored application identifier
# Now verify search and filter functionalities
    Given I am logged into appian as "<logInAs>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I search for registered manufacturer ""
    And I click on a random organisation which needs cfs
    And I search for product by "<searchBy>" for the value "<searchTerm>"
    Then I should see <count> products matching search results
    Examples:
      | logInAs           | searchBy            | searchTerm           | count |
      | authorisedRepAuto | gmdn term           | Blood weighing scale | 1     |
      | manufacturerAuto  | medical device name | Product              | 2     |