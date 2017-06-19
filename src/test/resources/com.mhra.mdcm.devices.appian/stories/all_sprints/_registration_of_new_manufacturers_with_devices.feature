Feature: As a customer I want to register new manufacturers with devices
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @mdcm-14 @mdcm-39 @mdcm-496 @_sprint3 @_sprint5 @2185 @_sprint8 @1956 @_sprint9 @wip
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
      | productName            | TooManyChanges       |
    And Proceed to payment and confirm submit device details
    Then I should be returned to the manufacturers list page
    Examples:
      | user              | accountType   | countryName | deviceType               | customMade | deviceSterile | deviceMeasuring | riskClassification | notifiedBody |
      | manufacturerAuto  | manufacturer  | Brazil      | General Medical Device   | true       |               |                 |                    |              |
      | authorisedRepAuto | authorisedRep | Bangladesh  | General Medical Device   | false      | true          | true            | class1             | NB 0086 BSI  |
      | manufacturerAuto  | manufacturer  | Brazil      | System or Procedure Pack |            | true          |                 | class1             | NB 0086 BSI  |
      | authorisedRepAuto | authorisedRep | Bangladesh  | System or Procedure Pack |            | false         |                 | class1             | NB 0086 BSI  |


  @regression @mdcm-485 @mdcm-374 @mdcm-186 @_sprint2 @1838 @3777 @1924 @_sprint8 @_sprint9 @_sprint13 @_sprint5 @wip @bug
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
    And I am logged into appian as "<logBackInAas>" user
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I logout of the application
    And I am logged into appian as "<user>" user
    When I go to list of manufacturers page and click on stored manufacturer
    Then Verify devices displayed and GMDN details are correct
    And I should be able to view stored device details
    Examples:
      | user              | logBackInAas | accountType   | countryName | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | manufacturer  | Bangladesh  | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto | authorisedRep | Brazil      | General Medical Device | true       |               |                 | Registered | Blood weighing scale |                    |              |


  @readonly @mdcm-39 @_sprint5 @1840 @_sprint9
  Scenario Outline: Verify manufacturers landing page contents
    Given I am logged into appian as "<user>" user
    When I go to portal page
    Then Landing page displays correct title and contact name
    Examples:
      | user              |
      | manufacturerAuto  |
      | authorisedRepAuto |


  @regression @mdcm-374 @mdcm-134 @_sprint5 @3762 @_sprint9 @mdcm-164 @_sprint6 @1956 @_sprint9 @bug
  Scenario Outline: Users should be able to add and remove devices from a newly created manufacturers and submit for approval
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    When I add a device to SELECTED manufacturer with following data
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
      | user               | accountType   | countryName | deviceType             | gmdn1                | gmdn2           |
      | manufacturerAuto   | manufacturer  | Belarus     | General Medical Device | Blood weighing scale | Autopsy measure |
      | authorisedRepAuto  | authorisedRep | Bangladesh  | General Medical Device | Blood weighing scale | Autopsy measure |


  @regression @mdcm-161 @mdcm-21 @mdcm-232 @mdcm-496 @_sprint4 @_sprint5
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
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "Organisations"
    And I search for stored organisation in "Organisations" page
    Then All organisation search result should return 1 matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  |


  @mdcm-292 @mdcm-496 @mdcm-21 @_sprint3 @_sprint5 @mdcm-134 @mdcm-164 @_sprint6 @mdcm-490 @_sprint7 @wip @bug
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
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I go to records page and click on "<page>"
    And I search for stored organisation in "<page>" page
    And I click on a link which matches the stored organisations in "<page>" page
    Then I should see new product id generated for my device
    Examples:
      | user              | logBackInAs  | accountType   | countryName | page                | deviceType                         | gmdnDefinition        | customMade | riskClassification | productName | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | authorisedRepAuto | businessAuto | manufacturer  | Bangladesh  | Registered Products | In Vitro Diagnostic Device         | Androgen receptor IVD |            | list a             | ford focus  | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | manufacturerAuto  | businessAuto | authorisedRep | Brazil      | Registered Products | Active Implantable Medical Devices | Desiccating chamber   | true       |                    | ford focus  |             |              |              |                   |            |               |

