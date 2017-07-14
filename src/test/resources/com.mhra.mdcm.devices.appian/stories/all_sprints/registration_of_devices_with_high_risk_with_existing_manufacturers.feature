Feature: As an account holder, I want to register manufacturers and declare higher risk devices
  and declare the specific products being manufactured
  so that they can place the device for sale on the EU market

  @regression @mdcm-183 @2259 @mdcm-21 @2323 @_sprint3 @_sprint5 @2049 @_sprint8
  Scenario Outline: Register device with SINGLE product for IVD devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
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
      | manufacturerAuto  | In Vitro Diagnostic Device | Androgen receptor IVD | list b             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto  | In Vitro Diagnostic Device | Androgen receptor IVD | self-test          | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @2259 @_sprint3 @mdcm-148 @2284 @_sprint7
  Scenario Outline: Register device with SINGLE product for Active Implantable Device
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    Then I should see option to add another device
    Examples:
      | user              | deviceType                | gmdnDefinition      | customMade | productName |
      | authorisedRepAuto | Active Implantable Device | Desiccating chamber | true       | ford focus  |
      | manufacturerAuto  | Active Implantable Device | Desiccating chamber | true       | ford focus  |
#      | manufacturerAuto | Active Implantable Device | Blood          | false      | ford focus  | can't register if custom made is false


  @regression @3559 @2143 @_sprint10 @2185 @_sprint8
  Scenario Outline: Error message is displayed for devices with certain risk classification
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
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
# GMD can only have risk classification of class1 therefore error messages will not be displayed
#      | authorisedRepAuto | General Medical Device   | Blood               |               | class1            | NB 0086 BSI  |                    |                   | You cannot register class IIa devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               |               | class1            | NB 0086 BSI  |                    |                   | You cannot register class IIb devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               |               | class1             | NB 0086 BSI  |                    |                   | You cannot register class III devices with the MHRA                                              |
      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber | false         |                    |              | true               |                   | You cannot register this as a System/procedure pack because all the components must be CE marked |
      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber | true          |                    | NB 0086 BSI  |                    | false             | You cannot register this as a System/procedure pack because all the components must be CE marked |
      | manufacturerAuto  | System or Procedure Pack | Desiccating chamber |               |                    | NB 0086 BSI  | false              | false             | This System/procedure pack cannot be registered with us                                          |


#    Apparently this is no longer required : bus testers will confirm
  @regression @3560 @_sprint10 @4908 @_sprint16 @bug @ignore
  Scenario Outline: Error message is displayed for AIMD devices with certain risk classification
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I try to add an incomplete device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | notifiedBody   | <notifiedBody>   |
    Then I should see validation error message in devices page with text "<errorMsg>"
    And I should be prevented from adding the high risk devices
    Examples:
      | user              | deviceType                | gmdnDefinition      | customMade | notifiedBody | errorMsg                                                                    |
      | authorisedRepAuto | Active Implantable Device | Desiccating chamber | false      | NB 0086 BSI  | You cannot register non custom made active implantable device with the MHRA |
      | manufacturerAuto  | Active Implantable Device | Desiccating chamber | false      | NB 0086 BSI  | You cannot register non custom made active implantable device with the MHRA |


  @regression @mdcm-183 @2259 @_sprint3 @mdcm-148 @2284 @_sprint7 @2049 @_sprint8
  Scenario Outline: Register device with MULTIPLE products and devices for IVD devices type
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
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
      | manufacturerAuto  | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | list b             | honda,ferrari      | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto  | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | self-test          | ferrari,peugeot    | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @2259 @mdcm-240 @2216 @_sprint3 @_sprint4 @3777 @2184 @_sprint9 @mdcm-148 @2284 @2910 @_sprint7
  Scenario Outline: Register a device with MULTIPLE product and devices for Active Implantable Device type
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
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
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the new account
    Then Check task contains correct devices "<gmdnDefinition>" and other details
    And Task contains correct devices and products and other details for "<deviceType>"
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | deviceType                | gmdnDefinition      | gmdnDefinition2 | customMade | listOfProductNames  |
      | authorisedRepAuto | businessAuto | Active Implantable Device | Desiccating chamber | suction         | true       | ford,hyundai        |
      | manufacturerAuto  | businessAuto | Active Implantable Device | Desiccating chamber | suction         | true       | ford,hyundai,toyota |


  @regression @mdcm-489 @2027 @_sprint5 @mdcm-148 @2284 @_sprint7 @3755 @_sprint11 @2910 @_sprint7
  Scenario Outline: Verify product details after adding IVD products
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
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
    Then I should see option to add another device
    And The gmdn code or term is "displayed" in summary section
    And Verify name make model and other details are correct
    Examples:
      | user              | deviceType                 | gmdnDefinition        | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto  | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | Ford               | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | Opal               | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | authorisedRepAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | General Motors     | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
