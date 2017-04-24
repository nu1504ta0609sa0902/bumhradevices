Feature: As a customer I want to register new manufacturers with devices
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @mdcm-14 @mdcm-39 @mdcm-496 @_sprint3 @_sprint5 @2185 @_sprint8 @1956 @_sprint9 @5441 @5368 @_sprint16 @wip
  Scenario Outline: Users should be able to register new manufacturers with devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | Blood                |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    Examples:
      | user              | accountType   | countryName | deviceType             | deviceType               | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody |
      | manufacturerAuto  | manufacturer  | Brazil      | General Medical Device | General Medical Device   | true       |               |                 |                    |              |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device | General Medical Device   | false      | true          | true            | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto  | Brazil      | Desiccating chanber    | System or Procedure Pack |            | true          |                 | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto  | Bangladesh  | Desiccating chanber    | System or Procedure Pack |            | false         |                 | class1             | NB 0086 BSI  |


  @regression @mdcm-485 @mdcm-374 @mdcm-186 @_sprint2 @1838 @3777 @1924 @_sprint8 @_sprint9 @_sprint13 @_sprint5 @wip @bug
  Scenario Outline: Users should be able to register new manufacturers with devices  and verify devices are added
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | customMade             | <customMade>         |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    When I logout of the application
    And I am logged into appian as "<user>" user
#    And I go to list of manufacturers page
    When I go to list of manufacturers page and click on stored manufacturer
    Then Verify devices displayed and GMDN details are correct
    And I should be able to view products related to stored devices
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | manufacturer  | Bangladesh  | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto | authorisedRep | Brazil      | General Medical Device | true       |               |                 | Registered | Blood weighing scale |                    |              |


  @regression @readonly @mdcm-39 @_sprint5 @1840 @_sprint9
  Scenario Outline: Verify manufacturers landing page contents
    Given I am logged into appian as "<user>" user
    When I go to portal page
    Then Landing page displays correct title and contact name
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @regression @mdcm-374 @mdcm-134 @_sprint5 @3762 @_sprint9 @mdcm-164 @_sprint6 @1956 @_sprint9 @bug
  Scenario Outline: Users should be able to add and remove devices from a newly created manufacturers and submit for approval
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn2>      |
      | customMade     | true         |
    Then I should see option to add another device
    When I remove the stored device with gmdn code or definition
    Then I should see option to add another device
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType             | gmdn1                | gmdn2           |
      | manufacturerAuto  | businessAuto | manufacturer  | Belarus     | General Medical Device | Blood weighing scale | Autopsy measure |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | General Medical Device | Blood weighing scale | Autopsy measure |

