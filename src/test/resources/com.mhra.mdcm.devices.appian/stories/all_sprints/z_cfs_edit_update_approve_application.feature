@cfs
Feature: Users should be able to add remove and edit for CFS new manufacturer application


  @1974 @4698 @1989 @5126 @3979 @5212 @5128 @_sprint15 @_sprint17 @1962 @_sprint23
  Scenario: Users should be able to go to cfs page and add device to existing manufacturer
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "RT01" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType         | Active Implantable Device |
      | gmdnDefinition     | Desiccating chamber       |
      | customMade         | false                     |
      | notifiedBody       | NB 0086 BSI               |
      | listOfProductNames | Product1,Product2         |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval

  @5748 @_sprint23
  Scenario: Error message should be displayed when we try to add a duplicate device to a new cfs manufacturer
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
    And I select a specific device for CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
    Then I should see the following "This device already exists for this manufacturer" error message


  @5748 @_sprint23
  Scenario: Error message should be displayed when we try to add a duplicate device to an existing cfs manufacturer
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "RT01" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I select a specific device for CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
    Then I should see the following "This device already exists for this manufacturer" error message

  @1944 @1988 @5583 @5126 @5212 @5128 @_sprint15 @_sprint17 @_sprint18 @1962 @_sprint23
  Scenario Outline: Able to upload certificates with different notified body
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | deviceType           | <deviceType>           |
      | gmdnDefinition       | <gmdnDefinition>       |
      | customMade           | <customMade>           |
      | riskClassification   | <riskClassification>   |
      | relatedDeviceSterile | <relatedDeviceSterile> |
      | notifiedBody         | <notifiedBody>         |
      | listOfProductNames   | <listOfProductNames>   |
      | certificateType      | <certificateType>      |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    Examples:
      | user             | searchTerm | notifiedBody     | certificateType    | deviceType                | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | RT01Test   | BSI              | Inspection Quality | General Medical Device    | Blood weighing scale | false      | Class2A            | true                 | Product1,Product2  |
      | manufacturerAuto | RT01Test   | Amtac            | Batch Verification | General Medical Device    | Blood weighing scale | false      | Class2B            | true                 | Product1,Product2  |
      | manufacturerAuto | RT01Test   | Amtac            | Design Exam        | General Medical Device    | Blood weighing scale | false      | Class3             | false                | Product1,Product2  |
      | manufacturerAuto | RT01Test   | Lloyd            | Production Quality | Active Implantable Device | Desiccating chamber  | false      |                    |                      | Product1,Product2  |
      | manufacturerAuto | RT01Test   | SGS              | Type Exam          | Active Implantable Device | Desiccating chamber  | false      |                    |                      | Product1,Product2  |
      | manufacturerAuto | RT01Test   | UL International | Full Quality       | Active Implantable Device | Desiccating chamber  | false      |                    |                      | Product1,Product2  |


  @1944 @5141 @5583 @_sprint15 @_sprint17 @_sprint18 @1962 @_sprint23
  Scenario Outline: Users can upload different types of CE Certificates
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | addDevices           | <addDevices>           |
      | addCertificate       | <addCertificate>       |
      | addProducts          | <addProducts>          |
      | deviceType           | <deviceType>           |
      | gmdnDefinition       | <gmdnDefinition>       |
      | customMade           | <customMade>           |
      | riskClassification   | <riskClassification>   |
      | relatedDeviceSterile | <relatedDeviceSterile> |
      | notifiedBody         | <notifiedBody>         |
      | listOfProductNames   | <listOfProductNames>   |
      | docType              | <docType>              |
    Then I should not be able to proceed to the next step
    Examples:
      | user              | searchTerm | docType                  | addDevices | addCertificate | addProducts | notifiedBody | deviceType                | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto  | RT01Test   | pdf, jpg,png , tif ,docx | true       | true           | false       | Amtac        | General Medical Device    | Blood weighing scale | false      | Class2B            | true                 | Product1,Product2  |
      | authorisedRepNoor | RT01Test   | jpg                      | true       | true           | false       | SGS          | Active Implantable Device | Desiccating chamber  | false      |                    |                      | Product1,Product2  |


  @5583 @5578 @_sprint18 @1958 @1960 @_sprint22 @4207 @_sprint23 @wip
  Scenario Outline: Verify certain elements are disable by defaults like Upload Certificate button
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | addDevices           | <addDevices>           |
      | addCertificate       | <addCertificate>       |
      | addProducts          | <addProducts>          |
      | deviceType           | <deviceType>           |
      | gmdnDefinition       | <gmdnDefinition>       |
      | customMade           | <customMade>           |
      | riskClassification   | <riskClassification>   |
      | relatedDeviceSterile | <relatedDeviceSterile> |
      | notifiedBody         | <notifiedBody>         |
      | listOfProductNames   | <listOfProductNames>   |
    Then I should not be able to proceed to the next step
    When I save cfs new manufacturer application for later
    Then I should see application tab showing my application with correct details
    Examples:
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | RT01Test   | false      |                |             | BSI          | General Medical Device    | Blood weighing scale | false      | Class2A            | true                 | Product1,Product2  |
      | manufacturerAuto | RT01Test   | true       | false          |             | Amtac        | General Medical Device    | Blood weighing scale | false      | Class2B            | true                 | Product1,Product2  |
      | manufacturerAuto | RT01Test   | true       | true           | false       | SGS          | Active Implantable Device | Desiccating chamber  | false      |                    |                      | Product1,Product2  |


  @5125 @_sprint17
  Scenario Outline: Verify removing certificate should prevent us from moving to add products step
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | addCertificate       | <addCertificate>       |
      | addProducts          | <addProducts>          |
      | addDevices           | <addDevices>           |
      | deviceType           | <deviceType>           |
      | gmdnDefinition       | <gmdnDefinition>       |
      | customMade           | <customMade>           |
      | riskClassification   | <riskClassification>   |
      | relatedDeviceSterile | <relatedDeviceSterile> |
      | notifiedBody         | <notifiedBody>         |
      | listOfProductNames   | <listOfProductNames>   |
    When I remove the attached certificate
    Then I should not be able to proceed to the next step
    Examples:
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                | gmdnDefinition      | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | RT01Test   | true       | true           | false       | SGS          | Active Implantable Device | Desiccating chamber | false      |                    |                      | Product1,Product2  |


  @5126 @_sprint17 @wip
  Scenario Outline: Verify removing all the added products should prevent us from moving to submit CFS application page
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | addCertificate       | <addCertificate>       |
      | addProducts          | <addProducts>          |
      | addDevices           | <addDevices>           |
      | deviceType           | <deviceType>           |
      | gmdnDefinition       | <gmdnDefinition>       |
      | customMade           | <customMade>           |
      | riskClassification   | <riskClassification>   |
      | relatedDeviceSterile | <relatedDeviceSterile> |
      | notifiedBody         | <notifiedBody>         |
      | listOfProductNames   | <listOfProductNames>   |
    When I remove the attached product
    Then I should not be able to proceed to the next step
    Examples:
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                | gmdnDefinition      | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | RT01Test   | true       | true           | true        | SGS          | Active Implantable Device | Desiccating chamber | false      |                    |                      | ford,hyundai       |


  @1944 @_sprint17
  Scenario Outline: Error messages is displayed for incorrect date values
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I try to add a device to SELECTED CFS manufacturer with following data
      | addProducts          | false                  |
      | deviceType           | <deviceType>           |
      | gmdnDefinition       | <gmdnDefinition>       |
      | customMade           | <customMade>           |
      | riskClassification   | <riskClassification>   |
      | relatedDeviceSterile | <relatedDeviceSterile> |
      | notifiedBody         | <notifiedBody>         |
      | monthsInFutureOrPast | <monthsInFutureOrPast> |
    Then I should see the following field "<errorMsg>" error message
    Examples:
      | user             | searchTerm | errorMsg                                                                                      | monthsInFutureOrPast | deviceType             | notifiedBody | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile |
      | manufacturerAuto | RT01Test   | According to this date your certificate has expired. MHRA cannot accept expired certificates. | -1                   | General Medical Device | BSI          | Blood weighing scale | false      | class3             | false                |
      | manufacturerAuto | RT01Test   | According to this date your certificate has expired. MHRA cannot accept expired certificates. | 0                    | General Medical Device | BSI          | Blood weighing scale | false      | class2b            | false                |
#      | manufacturerAuto | RT01Test   | This certificate expires within 3 months                                                     | 2                    | General Medical Device | BSI          | Blood weighing scale | false      | class2a            | false                |


  @smoke_test_cfs @1845 @1945 @_sprint18
  Scenario: Users should receive task in application WIP page
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "RT01Test" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessAuto" user
    And I go to application WIP page
    Then Check the application reference number format is valid


  @5672 @_sprint20
  Scenario Outline: Manufacturers should be able to remove devices from a CFS application
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
      | productName    | Product12                 |
      | productModel   | Model13                   |
    And I add another device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | <deviceToRemove>       |
      | customMade           | false                  |
      | riskClassification   | Class2a                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    Then I should see correct device data in the review page
    When I remove device called "<deviceToRemove>" from list of devices
    Then I should see correct device data in the review page
    When I go back to the CE certificates page
    Then I should see all the certificates previously uploaded
    Examples:
      | user              | deviceToRemove       |
      | manufacturerAuto  | Blood weighing scale |
      | authorisedRepAuto | Blood weighing scale |


  @1986 @5593 @_sprint21 @5673 @5674 @_sprint22 @_sprint23
  Scenario: Business users should be able to view cfs new manufacturer and its devices and able to change approval decisions
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
    And I assign the AWIP page task to me and "approve" without completing the application
    Then I should see information related to the approver
    Then I should see a button with the following text "Change decision"
    When I select a single cfs devices
    And I click on change decision
    Then I should see a button with the following text "Approve manufacturer"