Feature: As a UK based organisation I need to obtain a CERTIFICATE OF FREE SALE to export medical devices to non-EU countries


  @1974 @4330 @5141 @3979 @5212 @5126 @1845 @5128 @_sprint15 @_sprint17 @_sprint18
  Scenario: Users should be able to go to cfs page and add a new manufacturer
    Given I am logged into appian as "manufacturerAuto" user
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
    When I logout and log back into appian as "businessNoor" user
    When I logout and log back into appian as "businessAuto" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Application"


  @1974 @4698 @1989 @5126 @3979 @5212 @5128 @_sprint15 @_sprint17
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
      | deviceType         | Active Implantable Medical Devices |
      | gmdnDefinition     | Desiccating chamber                |
      | customMade         | false                              |
      | notifiedBody       | NB 0086 BSI                        |
      | listOfProductNames | ford,hyundai                       |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval

  @1944 @1988 @5583 @5126 @5212 @5128 @_sprint15 @_sprint17 @_sprint18
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
      | user             | searchTerm | notifiedBody     | certificateType    | deviceType                         | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | AccountST  | BSI              | Inspection Quality | General Medical Device             | Blood weighing scale | false      | Class2A            | true                 | ford,hyundai       |
      | manufacturerAuto | AccountST  | Amtac            | Batch Verification | General Medical Device             | Blood weighing scale | false      | Class2B            | true                 | ford,hyundai       |
      | manufacturerAuto | AccountST  | Amtac            | Design Exam        | General Medical Device             | Blood weighing scale | false      | Class3             | false                | ford,hyundai       |
      | manufacturerAuto | AccountST  | Lloyd            | Production Quality | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |
      | manufacturerAuto | AccountST  | SGS              | Type Exam          | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |
      | manufacturerAuto | AccountST  | UL International | Full Quality       | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |


  @1944 @5141 @5583 @_sprint15 @_sprint17 @_sprint18
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
      | user              | searchTerm | docType                  | addDevices | addCertificate | addProducts | notifiedBody | deviceType                         | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto  | AccountST  | pdf, jpg,png , tif ,docx | true       | true           | false       | Amtac        | General Medical Device             | Blood weighing scale | false      | Class2B            | true                 | ford,hyundai       |
      | authorisedRepNoor | AccountST  | jpg                      | true       | true           | false       | SGS          | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |


  @5583 @5578 @_sprint18
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
    Examples:
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                         | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | AccountST  | false      |                |             | BSI          | General Medical Device             | Blood weighing scale | false      | Class2A            | true                 | ford,hyundai       |
      | manufacturerAuto | AccountST  | true       | false          |             | Amtac        | General Medical Device             | Blood weighing scale | false      | Class2B            | true                 | ford,hyundai       |
      | manufacturerAuto | AccountST  | true       | true           | false       | SGS          | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |


  @5125 @_sprint17 @wip
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
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                         | gmdnDefinition      | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | AccountST  | true       | true           | false       | SGS          | Active Implantable Medical Devices | Desiccating chamber | false      |                    |                      | ford,hyundai       |


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
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                         | gmdnDefinition      | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerAuto | AccountST  | true       | true           | true        | SGS          | Active Implantable Medical Devices | Desiccating chamber | false      |                    |                      | ford,hyundai       |


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
      | manufacturerAuto | AccountST  | According to this date your certificate has expired. MHRA cannot accept expired certificates. | -1                   | General Medical Device | BSI          | Blood weighing scale | false      | class3             | false                |
      | manufacturerAuto | AccountST  | According to this date your certificate has expired. MHRA cannot accept expired certificates. | 0                    | General Medical Device | BSI          | Blood weighing scale | false      | class2b            | false                |
#      | manufacturerAuto | AccountST   | This certificate expires within 3 months                                                     | 2                    | General Medical Device | BSI          | Blood weighing scale | false      | class2a            | false                |


  @1845 @1945 @_sprint18
  Scenario: Users should receive task in application WIP page
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "AccountST" which needs cfs
    And I add a device to SELECTED CFS manufacturer with following data
      | deviceType           | General Medical Device |
      | gmdnDefinition       | Blood weighing scale   |
      | customMade           | false                  |
      | riskClassification   | Class2A                |
      | relatedDeviceSterile | true                   |
      | notifiedBody         | NB 0086 BSI            |
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessNoor" user
    And I go to application WIP page
    Then Check the application reference number format is valid

  @1845 @1945 @_sprint18
  Scenario: Check application reference is correct format
    Given I am logged into appian as "businessNoor" user
    And I go to application WIP page
    Then Check the application reference number format is valid

  @1974 @_sprint15
  Scenario: Users should be able to go to cfs page
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS


  @5571 @3856 @_sprint18
  Scenario: Users should be able to go to BACK from the application
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    And I fill out the form called tell us about your organisation
    And I click on the back button
    Then I should see an alert box asking for confirmation
    When I click "yes" on the alert box
    Then I should see a list of manufacturers available for CFS


  @5571 @4203 @4698 @_sprint15 @_sprint18
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
      | manufacturerAuto | Yes         |
      | manufacturerAuto | No          |

  @4330 @4203 @_sprint15 @_sprint16
  Scenario: Users should be able to tell what stage of device registration they are in
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    Then I should see current stage of indication


  @5207 @5578 @_sprint16 @_sprint18
  Scenario Outline: Error messages should be displayed to user for certain combinations
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "<searchTerm>" which needs cfs
    And I try to add a device to SELECTED CFS manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | riskClassification | <riskClassification> |
    Then I should see the following "<errorMsg>" error message
    Examples:
      | user             | searchTerm | errorMsg                                 | deviceType                         | gmdnDefinition       | customMade | riskClassification |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | General Medical Device             | Blood weighing scale | true       |                    |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | General Medical Device             | Blood weighing scale | false      | class1             |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | Active Implantable Medical Devices | Blood weighing scale | true       |                    |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | In Vitro Diagnostic Device         |                      |            |                    |
      | manufacturerAuto | AccountST  | This device must be registered with MHRA | System or Procedure Pack           |                      |            |                    |


  @1974 @1978 @4704 @_sprint15
  Scenario Outline: Users should be able to go to cfs page and add to a random manufacturer from the list
    Given I am logged into appian as "manufacturerAuto" user
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


  @1974 @1978 @4704 @_sprint15 @wip
  Scenario: Users should be able to go to edit list of devices added for initial CFS process
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    Then I should see the correct details in cfs review page
    When I edit the list of devices added for CFS
    Then I should see the correct details in cfs review page


  @1974 @1978 @_sprint15
  Scenario: Users should be able to go to edit country and number of certificates
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for a country with following data
      | countryName | Brazil |
      | noOfCFS     | 10     |
    Then I should see the correct details in cfs review page
#    When I update the country added for CFS to "Bangladesh"
#    And I update the no of certificates for CFS to 9
    When I update the country to "Bangladesh" and number of certificates to 9
    Then I should see the correct details in cfs review page


  @1974 @1978 @5578 @_sprint15 @_sprint18
  Scenario Outline: Users should be able to order CFS for multiple countries
    Given I am logged into appian as "manufacturerAuto" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a random organisation which needs cfs
    And I order cfs for multiple countries with following data
      | listOfCFSCountryPair | <countryAndCertificateNumber> |
    Then I should see correct details for all the countries and certificate in cfs review page
    When I submit payment for the CFS
    Examples:
      | countryAndCertificateNumber                                |
      | Switzerland=5,Norway=10,British Virgin=15,British Indian=1 |
      | Bangladesh=5,Brazil=2,United States=3                      |
      | Turkey=5,Iceland=10,United States=20,Liechtenstein=20      |

