Feature: As an account holder with access to the Device Registration Service I want to register a manufacturer and declare higher risk devices and declare the specific products being manufactured
  so that they can place the device for sale on the EU market

  @regression @mdcm-183
  Scenario Outline: Register device with SINGLE product for IVD devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a registered manufacturer
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
      | user             | deviceType                 | gmdnDefinition | riskClassification | productName | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list b             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | self-test          | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183
  Scenario Outline: Register device with SINGLE product for active implantable medical devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a registered manufacturer
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    Then I should see option to add another device
    Examples:
      | user             | deviceType                         | gmdnDefinition | customMade | productName |
      | manufacturerAuto | Active Implantable Medical Devices | Adhesive       | true       | ford focus  |
      | manufacturerAuto | Active Implantable Medical Devices | Adhesive       | false      | ford focus  |


  @regression @mdcm-183
  Scenario Outline: Register device with MULTIPLE products for IVD devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a registered manufacturer
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
    Examples:
      | user             | deviceType                 | gmdnDefinition | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list a             | ford,hyundai       | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list a             | ford,honda         | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list b             | honda,ferrari      | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | self-test          | ferrari,peugeot    | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-183 @mdcm-240 @wip
  Scenario Outline: Register a device with MULTIPLE product for active implantable medical devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a registered manufacturer
    When I add multiple devices to SELECTED manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    When I logout of the application
    And I login to appian as "<logBackInAas>" user
    Then I should see a new task with link "<link>" for the new account
    And I assign the task to me and "approve" the generated task
    Then The task with link "<link>" should be removed from tasks list
    Examples:
      | user             | logBackInAas | deviceType                         | gmdnDefinition | customMade | listOfProductNames | link |
      | manufacturerAuto | businessAuto | Active Implantable Medical Devices | Adhesive       | true       | ford,hyundai       |Update Manufacturer Registration Request|
      | manufacturerAuto | businessAuto | Active Implantable Medical Devices | Adhesive       | false      | vauxhall,honda     |Update Manufacturer Registration Request|

