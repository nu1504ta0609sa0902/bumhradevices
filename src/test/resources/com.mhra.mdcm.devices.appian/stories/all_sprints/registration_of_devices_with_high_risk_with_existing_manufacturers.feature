Feature: As an account holder with access to the Device Registration Service I want to register a manufacturer and declare higher risk devices and declare the specific products being manufactured
  so that they can place the device for sale on the EU market

  @regression @mdcm-183 @mdcm-21 @_sprint3 @_sprint5 @2049 @_sprint8
  Scenario Outline: Register device with SINGLE product for IVD devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productName        | <productName>        |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
    Then I should see option to add another device
    Examples:
      | user              | deviceType                 | gmdnDefinition        | riskClassification | productName | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list b             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | self-test          | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @_sprint3 @mdcm-148 @_sprint7
  Scenario Outline: Register device with SINGLE product for active implantable medical devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    Then I should see option to add another device
    Examples:
      | user              | deviceType                         | gmdnDefinition      | customMade | productName |
      | authorisedRepAuto | Active Implantable Medical Devices | Desiccating chamber | true       | ford focus  |
      #| manufacturerAuto | Active Implantable Medical Devices | Desiccating chamber | true       | ford focus  |
#      | manufacturerAuto | Active Implantable Medical Devices | Blood          | false      | ford focus  | can't register if custom made is false


  @regression @3559 @2143 @_sprint10 @2185 @_sprint8
  Scenario Outline: Error message is displayed for devices with certain risk classification
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I try to add an incomplete device to SELECTED manufacturer with following data
      | deviceType           | <deviceType>         |
      | gmdnDefinition       | <gmdnDefinition>     |
      | customMade           | false                |
      | riskClassification   | <riskClassification> |
      | notifiedBody         | <notifiedBody>       |
      | isBearingCEMarking   | <isBearingCEMarking> |
      | devicesCompatible    | <devicesCompatible>  |
      | relatedDeviceSterile | <deviceSterile>      |
    Then I should see validation error message in devices page with text "<errorMsg>"
    And I should be prevented from adding the devices
    Examples:
      | user              | deviceType               | gmdnDefinition      | deviceSterile | riskClassification | notifiedBody | isBearingCEMarking | devicesCompatible | errorMsg                                                                                         |
#      | authorisedRepAuto | General Medical Device   | Blood               |               | class2a            | NB 0086 BSI  |                    |                   | You cannot register class IIa devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               |               | class2b            | NB 0086 BSI  |                    |                   | You cannot register class IIb devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               |               | class3             | NB 0086 BSI  |                    |                   | You cannot register class III devices with the MHRA                                              |
      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber | false         |                    |              | true               |                   | You cannot register this as a System/procedure pack because all the components must be CE marked |
      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber | true          |                    | NB 0086 BSI  |                    | false             | You cannot register this as a System/procedure pack because all the components must be CE marked |
      | manufacturerAuto  | System or Procedure Pack | Desiccating chamber |               |                    | NB 0086 BSI  | false              | false             | This System/procedure pack cannot be registered with us                                          |


  @regression @3560 @2143 @_sprint10
  Scenario Outline: Error message is displayed for AIMD devices with certain risk classification
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I try to add an incomplete device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | notifiedBody   | <notifiedBody>   |
    Then I should see validation error message in devices page with text "<errorMsg>"
    And I should be prevented from adding the high risk devices
    Examples:
      | user              | deviceType                         | gmdnDefinition      | customMade | notifiedBody | errorMsg                                                                             |
      | authorisedRepAuto | Active Implantable Medical Devices | Desiccating chamber | false      | NB 0086 BSI  | You cannot register non custom made active implantable medical devices with the MHRA |
      | manufacturerAuto  | Active Implantable Medical Devices | Desiccating chamber | false      | NB 0086 BSI  | You cannot register non custom made active implantable medical devices with the MHRA |


  @regression @mdcm-183 @_sprint3 @mdcm-148 @_sprint7 @2049 @_sprint8
  Scenario Outline: Register device with MULTIPLE products and devices for IVD devices type
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
      | listOfProductNames | <listOfProductNames> |
    When I add another device to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition2>    |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
      | listOfProductNames | <listOfProductNames> |
    Then I should see option to add another device
    Examples:
      | user              | deviceType                 | gmdnDefinition        | gmdnDefinition2 | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | list a             | ford,hyundai       | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | list a             | ford,honda         | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | list b             | honda,ferrari      | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | self-test          | ferrari,peugeot    | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @mdcm-240 @_sprint3 @_sprint4 @3777 @2184 @_sprint9 @mdcm-148 @2910 @_sprint7
  Scenario Outline: Register a device with MULTIPLE product and devices for active implantable medical devices type
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I add multiple devices to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    When I add another device to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition2>    |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    Then I should see option to add another device
    And Proceed to payment and confirm submit device details
    When I logout of the application
    And I login to appian as "<logBackInAas>" user
    When I go to WIP tasks page
    And I wait for task to appear for stored manufacturer in WIP page
    Then The WIP task for stored manufacturer should contain a paper click image
    When I view task for the stored account in WIP page
    Then Task contains correct devices and products and other details for "<deviceType>"
    And Task shows devices which are arranged by device types
    And I assign the task to me and "approve" the generated task
    Then The completed task status should update to "Completed"
    Examples:
      | user              | logBackInAas | deviceType                         | gmdnDefinition      | gmdnDefinition2 | customMade | listOfProductNames  | link                                     |
      | authorisedRepAuto | businessAuto | Active Implantable Medical Devices | Desiccating chamber | suction         | true       | ford,hyundai        | Update Manufacturer Registration Request |
      | manufacturerAuto  | businessAuto | Active Implantable Medical Devices | Desiccating chamber | suction         | true       | ford,hyundai,toyota | Update Manufacturer Registration Request |

