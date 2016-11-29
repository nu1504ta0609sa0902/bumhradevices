Feature: As an account holder with access to the Device Registration Service I want to register a manufacturer and declare higher risk devices and declare the specific products being manufactured
  so that they can place the device for sale on the EU market

  @regression @mdcm-183 @wip
  Scenario Outline: Register a single IVD devices
    Given I am logged into appian as "<user>" user
    And I go to register another manufacturer page
    When I select a random manufacturer from list
    When I go to add devices page for the stored manufacturer
    When I add a device to selected manufacturer with following data
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
    Examples:
      | user             | deviceType                 | gmdnDefinition | riskClassification | productName | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | false         |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | list b             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto | In Vitro Diagnostic Device | Glucose        | self-test          | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |

  @regression @mdcm-183 @wip
  Scenario Outline: Register a single active implantable medical devices
    Given I am logged into appian as "<user>" user
    And I go to register another manufacturer page
    When I select a random manufacturer from list
    When I go to add devices page for the stored manufacturer
    When I add a device to selected manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    Examples:
      | user             | deviceType                         | gmdnDefinition | customMade | productName |
      | manufacturerAuto | Active Implantable Medical Devices | Adhesive       | true       | ford focus  |
      | manufacturerAuto | Active Implantable Medical Devices | Adhesive       | false      | ford focus  |
