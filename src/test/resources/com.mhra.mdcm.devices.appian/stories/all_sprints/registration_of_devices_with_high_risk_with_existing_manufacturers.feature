Feature: As an account holder with access to the Device Registration Service I want to register a manufacturer and declare higher risk devices and declare the specific products being manufactured
  so that they can place the device for sale on the EU market

  @regression @mdcm-183 @mdcm-21 @sprint3 @sprint5
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
      #| manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | list b             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      #| manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | self-test          | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @sprint3 @mdcm-148 @sprint7
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


  @regression @3560 @sprint10
  Scenario Outline: Error message is displayed for high risk devices with certain risk classification
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I try to add an incomplete device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
    Then I should see validation error message in devices page with text "<errorMsg>"
    And I should be prevented from adding the high risk devices
    Examples:
      | user              | deviceType                         | gmdnDefinition      | customMade | notifiedBody | errorMsg                                                                             |
      | authorisedRepAuto | Active Implantable Medical Devices | Desiccating chamber | false      | NB 0086 BSI  | You cannot register non custom made active implantable medical devices with the MHRA |
      | manufacturerAuto  | Active Implantable Medical Devices | Desiccating chamber | false      | NB 0086 BSI  | You cannot register non custom made active implantable medical devices with the MHRA |


  @regression @mdcm-183 @sprint3 @mdcm-148 @sprint7
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
      #| manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | list b             | honda,ferrari      | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      #| manufacturerAuto | In Vitro Diagnostic Device | Androgen receptor IVD | Cat             | self-test          | ferrari,peugeot    | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @mdcm-240 @sprint3 @sprint4  @mdcm-148 @sprint7 @wip
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
    Then I view new task with link "<link>" for the new account
    And I assign the task to me and "approve" the generated task
    Then The task with link "<link>" should be removed from tasks list
    Examples:
      | user              | logBackInAas | deviceType                         | gmdnDefinition      | gmdnDefinition2 | customMade | listOfProductNames | link                                     |
      | authorisedRepAuto | businessAuto | Active Implantable Medical Devices | Desiccating chamber | suction         | true       | ford,hyundai       | Update Manufacturer Registration Request |
      #| manufacturerAuto | businessAuto | Active Implantable Medical Devices | Desiccating chamber | suction         | true       | ford,hyundai       | Update Manufacturer Registration Request |
#      | manufacturerAuto | businessAuto | Active Implantable Medical Devices | Blood          | false      | vauxhall,honda     | Update Manufacturer Registration Request |

