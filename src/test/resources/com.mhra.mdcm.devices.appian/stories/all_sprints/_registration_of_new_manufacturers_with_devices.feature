Feature: As a customer I want to register new manufacturers with devices
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @mdcm-14 @2325 @mdcm-39 @2312 @mdcm-496 @_sprint3 @_sprint5 @2185 @_sprint8 @1956 @_sprint9 @3343 @5749 @_sprint21 @5752 @5750 @5753 @_sprint23 @create_new_org
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
      | isBearingCEMarking     | false                |
      | productName            | Product1             |
    And Proceed to payment via "<paymentType>" and confirm submit device details
    Then I should be returned to the manufacturers list page
    Examples:
      | user              | accountType   | countryName | paymentType | deviceType               | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody |
      | manufacturerAuto  | manufacturer  | Brazil      | Worldpay    | System or Procedure Pack |            | true          |                 | class1             | NB 0086 BSI  |
      | manufacturerAuto  | manufacturer  | Brazil      | BACS        | General Medical Device   | true       |               |                 |                    |              |
      | authorisedRepAuto | authorisedRep | Bangladesh  | Worldpay    | System or Procedure Pack |            | false         |                 | class1             | NB 0086 BSI  |
      | authorisedRepAuto | authorisedRep | Bangladesh  | BACS        | General Medical Device   | false      | true          | true            | class1             | NB 0086 BSI  |


  @regression @mdcm-485 @2030 @mdcm-374 @2112 @mdcm-186 @2258 @_sprint2 @1838 @3777 @1924 @_sprint8 @_sprint9 @_sprint13 @_sprint5 @create_new_org
  Scenario Outline: Users should be able to register new manufacturers with devices and verify devices are added
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
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I logout of the application
    And I am logged into appian as "<user>" user
    When I go to list of manufacturers page and click on stored manufacturer
    Then Verify devices displayed and GMDN details are correct
    And I should be able to view stored device details
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | General Medical Device | true       |               |                 | Registered | Blood weighing scale |                    |              |


  @readonly @mdcm-39 @2312 @_sprint5 @1840 @_sprint9
  Scenario Outline: Verify manufacturers landing page contents
    Given I am logged into appian as "<user>" user
    When I go to portal page
    Then Landing page displays correct title and contact name
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @regression @mdcm-374 @2112 @mdcm-134 @2290 @_sprint5 @3762 @_sprint9 @mdcm-164 @2274 @_sprint6 @1956 @_sprint9 @create_new_org @bug
  Scenario Outline: Users should be able to add and remove devices from a newly created manufacturers and submit for approval
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
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
    Then I should be returned to the manufacturers list page
    Examples:
      | user              | accountType   | countryName | deviceType             | gmdn1                | gmdn2           |
      | manufacturerAuto  | manufacturer  | Belarus     | General Medical Device | Blood weighing scale | Autopsy measure |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device | Blood weighing scale | Autopsy measure |


  @regression @mdcm-161 @2276 @mdcm-21 @2323 @mdcm-232 @2221 @mdcm-496 @_sprint4 @_sprint5 @create_new_org
  Scenario Outline: Users should be able to upload proof they are authorised reps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Organisations"
    And I search for stored organisation in "Organisations" page
    Then All organisation search result should return 1 matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  |


  @mdcm-292 @2177 @mdcm-496 @mdcm-21 @2323 @_sprint3 @_sprint5 @mdcm-134 @2290 @mdcm-164 @2274 @_sprint6 @mdcm-490 @2026 @_sprint7 @create_new_org @wip @bug
  Scenario Outline: Verify new product id is generated for each product submitted by manufacturer
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | customMade         | <customMade>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productName        | <productName>        |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
    And Proceed to payment and confirm submit device details
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "<page>"
    And I search for stored organisation in "<page>" page
    And I click on a link which matches the stored organisations in "<page>" page
    Then I should see new product id generated for my device
    Examples:
      | user              | logBackInAs  | accountType   | countryName | page                | deviceType                 | gmdnDefinition        | customMade | riskClassification | productName | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Registered Products | In Vitro Diagnostic Device | Androgen receptor IVD |            | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | Registered Products | Active Implantable Device  | Desiccating chamber   | true       |                    | ford focus  |             |              |              |                   |            |               |

  @create_new_org @1957 @5997 @5995 @_sprint21 @bug @wip
  Scenario Outline: Users should be able to save and resume newly created application
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | customMade         | <customMade>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | riskClassification | <riskClassification> |
      | notifiedBody       | <notifiedBody>       |
      | productName        | <productName>        |
      | productMake        | <productMake>        |
      | productModel       | <productModel>       |
      | subjectToPerfEval  | <subjectToPerfEval>  |
      | newProduct         | <newProduct>         |
      | conformsToCTS      | <conformsToCTS>      |
    When I save the application and confirm to exit
    And Proceed to payment and confirm submit device details
    Then I should be returned to the manufacturers list page
    Examples:
      | user              | accountType   | countryName | page                | deviceType                 | gmdnDefinition        | customMade | riskClassification | productName | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto  | manufacturer  | Brazil      | Registered Products | Active Implantable Device  | Desiccating chamber   | true       |                    | ford focus  |             |              |              |                   |            |               |
      | authorisedRepAuto | authorisedRep | Bangladesh  | Registered Products | In Vitro Diagnostic Device | Androgen receptor IVD |            | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
