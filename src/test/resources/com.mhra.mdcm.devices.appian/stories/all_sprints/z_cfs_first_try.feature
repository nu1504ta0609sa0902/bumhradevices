Feature: As a UK based organisation I need to obtain a CERTIFICATE OF FREE SALE to export medical devices to non-EU countries


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
    And I submit the cfs application for approval
    When I logout and log back into appian as "businessNoor" user
    And I go to application WIP page


  @1974 @4698 @_sprint15 @1989 @wip
  Scenario: Users should be able to go to cfs page and add device to existing manufacturer
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
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
    And I submit the cfs application for approval

  @5583 @_sprint18
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
    And I submit the cfs application for approval
    Examples:
      | user             | searchTerm | notifiedBody     | deviceType                         | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerNoor | TestNoor   | BSI              | General Medical Device             | Blood weighing scale | false      | Class2A            | true                 | ford,hyundai       |
      | manufacturerNoor | TestNoor   | Amtac            | General Medical Device             | Blood weighing scale | false      | Class2B            | true                 | ford,hyundai       |
      | manufacturerNoor | TestNoor   | Lloyd            | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |
      | manufacturerNoor | TestNoor   | SGS              | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |
      | manufacturerNoor | TestNoor   | UL International | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |

  @1944 @_sprint17
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
      | user             | searchTerm | docType                  | addDevices | addCertificate | addProducts | notifiedBody | deviceType             | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerNoor | TestNoor   | pdf, jpg,png , tif ,docx | true       | true           | false       | Amtac        | General Medical Device | Blood weighing scale | false      | Class2B            | true                 | ford,hyundai       |
#      | authorisedRepNoor | TestNoor   |   jpg      |true       | true           | false       | SGS          | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |


  @5583 @_sprint18
  Scenario Outline: Verify certain elements are disable by defaults like Upload Certificate button
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
    Then I should not be able to proceed to the next step
    Examples:
      | user             | searchTerm | addDevices | addCertificate | addProducts | notifiedBody | deviceType                         | gmdnDefinition       | customMade | riskClassification | relatedDeviceSterile | listOfProductNames |
      | manufacturerNoor | TestNoor   | false      |                |             | BSI          | General Medical Device             | Blood weighing scale | false      | Class2A            | true                 | ford,hyundai       |
      | manufacturerNoor | TestNoor   | true       | false          |             | Amtac        | General Medical Device             | Blood weighing scale | false      | Class2B            | true                 | ford,hyundai       |
      | manufacturerNoor | TestNoor   | true       | true           | false       | SGS          | Active Implantable Medical Devices | Desiccating chamber  | false      |                    |                      | ford,hyundai       |


  @1845 @1945 @_sprint18 @wip
  Scenario: Users should receive task in application WIP page
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I click on a organisation name begins with "TestNoor" which needs cfs
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

  @1845 @1945 @_sprint18 @wip
  Scenario: Check application reference is correct format
    Given I am logged into appian as "businessNoor" user
    And I go to application WIP page
    Then Check the application reference number format is valid

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

  @4330 @4203 @_sprint16 @wip
  Scenario: Users should be able to tell what stage of device registration they are in
    Given I am logged into appian as "manufacturerNoor" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I goto add a new cfs manufacturer page
    Then I should see current stage of indication


#    Repeat for AuthorisedRep and Distributor accounts
  @5207 @_sprint16
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
      | manufacturerNoor | TestNoor   | This device must be registered with MHRA | General Medical Device             | Blood weighing scale | true       |                    |
      | manufacturerNoor | TestNoor   | This device must be registered with MHRA | General Medical Device             | Blood weighing scale | false      | class1             |
      | manufacturerNoor | TestNoor   | This device must be registered with MHRA | Active Implantable Medical Devices | Blood weighing scale | true       |                    |
      | manufacturerNoor | TestNoor   | This device must be registered with MHRA | In Vitro Diagnostic Device         |                      |            |                    |
      | manufacturerNoor | TestNoor   | This device must be registered with MHRA | System or Procedure Pack           |                      |            |                    |


  @1974 @1978 @_sprint15 @wip
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

