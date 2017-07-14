Feature: As an account holder, I should be able to add devices to existing manufacturers
  I want to register a manufacturer and declare devices being manufacturer
  so that they can place the device for sale on the EU market

  @readonly @mdcm-14 @2325 @mdcm-21 @2323 @_sprint3 @_sprint5
  Scenario Outline: Users should be able to view already registered manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    When I view a random manufacturer with status "<status>"
    Then I should see the correct manufacturer details
    Examples:
      | user              | status     |
      | manufacturerAuto  | Registered |
      | authorisedRepAuto | Registered |

  @regression @readonly @mdcm-14 @2325 @mdcm-21 @2323 @_sprint3 @_sprint5
  Scenario Outline: Verify correct device type options are displayed on add devices page
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    Then I should see correct device types
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @regression @mdcm-14 @2325 @mdcm-467 @mdcm-21 @2323 @_sprint3 @_sprint5 @2185 @_sprint8 @3207 @5349 @5995 @_sprint21
  Scenario Outline: Verify correct options are displayed on add devices page for different combinations
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdnDefinition>     |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | isBearingCEMarking     | <isBearingCEMarking> |
      | devicesCompatible      | <devicesCompatible>  |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    And Verify save the application button is not displayed
#    Then I should see stored manufacturer appear in the registration in progress list
    Then I should see stored manufacturer appear in the manufacturers list
    Examples:
      | user              | status     | deviceType                 | gmdnDefinition                    | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody | isBearingCEMarking | devicesCompatible |
      | authorisedRepAuto | Registered | General Medical Device     | Blood weighing scale              | false      | true          | true            | class1             | NB 0086 BSI  |                    |                   |
      | authorisedRepAuto | Registered | General Medical Device     | Blood                             | true       |               |                 |                    |              |                    |                   |
      | authorisedRepAuto | Registered | In Vitro Diagnostic Device | Androgen receptor IVD, calibrator |            |               |                 | ivd general        |              |                    |                   |
      | authorisedRepAuto | Registered | System or Procedure Pack   | Desiccating chamber               |            | true          | true            | class1             | NB 0086 BSI  | false              | true              |
      | manufacturerAuto  | Registered | System or Procedure Pack   | Desiccating chamber               |            | false         | true            |                    |              | false              | true              |
      | manufacturerAuto  | Registered | General Medical Device     | Blood weighing scale              | false      | true          | true            | class1             | NB 0086 BSI  |                    |                   |
      | manufacturerAuto  | Registered | General Medical Device     | Blood                             | true       |               |                 |                    |              |                    |                   |
      | manufacturerAuto  | Registered | In Vitro Diagnostic Device | Androgen receptor IVD, calibrator |            |               |                 | ivd general        |              |                    |                   |
      | manufacturerAuto  | Registered | System or Procedure Pack   | Desiccating chamber               |            | true          | true            | class1             | NB 0086 BSI  | false              | true              |


  @regression @mdcm-14 @2325 @mdcm-489 @2027 @_sprint3 @_sprint5 @mdcm-134 @2290 @_sprint6
  Scenario Outline: Users should be able to add devices to existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | Blood        |
      | customMade     | true         |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    Examples:
      | user              | deviceType             |
      | authorisedRepAuto | General Medical Device |
      | manufacturerAuto  | General Medical Device |


  @regression @mdcm-134 @2290 @_sprint6 @bug
  Scenario Outline: Users should be able to remove devices from manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn2>      |
      | customMade     | true         |
    Then I should see option to add another device
    When I remove ALL the stored device with gmdn code or definition
    Then I should not see option to add another device
    Examples:
      | user              | deviceType             | gmdn1                | gmdn2           |
      | authorisedRepAuto | General Medical Device | Blood weighing scale | Autopsy measure |
      | manufacturerAuto  | General Medical Device | Blood weighing scale | Autopsy measure |


  @regression @mdcm-489 @2027 @_sprint5 @mdcm-134 @2290 @_sprint6 @mdcm-148 @2284 @_sprint7 @3762 @_sprint9 @bug
  Scenario Outline: Users should be able to add and remove devices using GMDN code from existing manufacturers
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>       |
      | gmdnDefinition | <gmdnDefinitionD1> |
      | gmdnCode       | <gmdnCodeD1>       |
      | customMade     | true               |
    And I add another device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>       |
      | gmdnDefinition | <gmdnDefinitionD2> |
      | gmdnCode       | <gmdnCodeD2>       |
      | customMade     | true               |
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    When I remove the stored device with gmdn code or definition
    Then The gmdn code or term is "removed" in summary section
    Examples:
      | user              | deviceType             | gmdnDefinitionD1 | gmdnCodeD1 | gmdnDefinitionD2 | gmdnCodeD2 |
      | authorisedRepAuto | General Medical Device | Blood            |            |                  | 18148      |
      | manufacturerAuto  | General Medical Device | Blood            |            |                  | 18148      |
      | authorisedRepAuto | General Medical Device |                  | 18148      | Dress            |            |
      | manufacturerAuto  | General Medical Device |                  | 18148      | Dress            |            |

  @mdcm-485 @2030 @mdcm-374 @2112 @3777 @_sprint9 @_sprint5 @1924 @_sprint8 @4337 @_sprint14 @4908 @_sprint16 @3343 @_sprint21 @wip @bug
  Scenario Outline: Users should be able to add devices to existing manufacturers and verify devices are added
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I click on random manufacturer with status "<status>" to add device
    And I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
      | isBearingCEMarking     | <isBearingCEMarking> |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "was completed" and stored application identifier
    When I logout of the application
    And I am logged into appian as "<user>" user
    When I go to list of manufacturers page and click on stored manufacturer
    Then Verify devices displayed and GMDN details are correct
    And I should be able to view stored device details
    Examples:
      | user              | logBackInAs  | deviceType                | isBearingCEMarking | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | General Medical Device    |                    | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |
      | authorisedRepAuto | businessAuto | Active Implantable Device |                    | true       |               |                 | Registered | Desiccating chamber  |                    |              |
      | manufacturerAuto  | businessAuto | General Medical Device    |                    | true       |               |                 | Registered | Blood weighing scale |                    |              |
      | manufacturerAuto  | businessAuto | System or Procedure Pack  | false              |            | true          | true            | Registered | Desiccating chamber  | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto | System or Procedure Pack  | false              |            | false         | true            | Registered | Desiccating chamber  | class1             | NB 0086 BSI  |


  @regression @readonly @mdcm-485 @2030 @_sprint5 @wip
  Scenario Outline: Users should be able to VIEW additional info related to registered manufacturers
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    Then I should see organisation details
    And I should see contact person details
    Examples:
      | user              | status         |
#      | manufacturerAuto  | Not Registered |
      | authorisedRepAuto | Not Registered |