Feature: End 2 End Scenarios to verify system is behaving correctly from a high level view

  @ignore
  Scenario Outline: S1 Manufacturer account registration
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then I should received an email for stored account with heading "<newAccountEmail>"
    And I logout and log back into appian as "<logBackInAas>" user
    And I go to register a new manufacturer page
    And I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType>      |
      | countryName | <countryNameNonEU> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    And I logout and log back into appian as "<user>" user
    Then I view new task with link "New Service Request" for the new account
    And I assign the task to me and "approve" the generated task
    And The completed task status should update to "Completed"
    And I should received an email for stored manufacturer with heading "<newOrganisationEmail>"
    Examples:
      | user         | logBackInAas     | accountType  | approveReject | countryName    | countryNameNonEU | newAccountEmail         | newOrganisationEmail              |
      | businessNoor | manufacturerNoor | manufacturer | approve       | United Kingdom | Bangladesh       | New Account Request for | Manufacturer registration service |


  @ignore
  Scenario Outline: S2 AuthorisedRep account registration for non uk manufacturers
    Given I am logged into appian as "<user>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I should see a new task for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then I should received an email for stored account with heading "<newAccountEmail>"
    And I logout and log back into appian as "<logBackInAas>" user
    And I go to register a new manufacturer page
    And I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType>      |
      | countryName | <countryNameNonEU> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    And I logout and log back into appian as "<user>" user
    Then I view new task with link "New Service Request" for the new account
    And I assign the task to me and "approve" the generated task
    And The completed task status should update to "Completed"
    And I should received an email for stored manufacturer with heading "<newOrganisationEmail>"
    Examples:
      | user         | logBackInAas     | accountType   | approveReject | countryName    | countryNameNonEU | newAccountEmail         | newOrganisationEmail              |
      | businessNoor | manufacturerNoor | authorisedRep | approve       | United Kingdom | Netherland       | New Account Request for | Manufacturer registration service |


  @ignore
  Scenario: S3 UK based manufacturer which is already registered and in need of CFS

  @ignore
  Scenario: S4 Non UK based authorised reps which is already registered and in need of CFS

  @ignore
  Scenario Outline: S5a Update already registered manufacturers by adding new devices
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    And I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    And I assign the task to me and "approve" the generated task
    And The completed task status should update to "Completed"
    And I should received an email for stored account with heading "<emailHeader>"
    Examples:
      | user              | logBackInAas | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |


  @ignore
  Scenario: S5b Update already registered manufacturers by removing and than adding new devices


  @ignore
  Scenario Outline: S6a Update manufacturer for authorised rep which is already registered by adding devices
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    And I add a device to SELECTED manufacturer with following data
      | deviceType             | <deviceType>         |
      | gmdnDefinition         | <gmdn>               |
      | riskClassification     | <riskClassification> |
      | notifiedBody           | <notifiedBody>       |
      | customMade             | <customMade>         |
      | relatedDeviceSterile   | <deviceSterile>      |
      | relatedDeviceMeasuring | <deviceMeasuring>    |
    And Proceed to payment and confirm submit device details
    Then I should see the registered manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAas>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    And I assign the task to me and "approve" the generated task
    And The completed task status should update to "Completed"
    And I should received an email for stored account with heading "<emailHeader>"
    Examples:
      | user              | logBackInAas | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |


  @ignore
  Scenario: S6b Update manufacturer for authorised rep which is already registered by adding and removing devices

  @ignore
  Scenario: S6c Update manufacturer for authorised rep which is already registered by adding products to devices


  @ignore
  Scenario Outline: S7abc Search for organisation products devices
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    Then I should see at least <count> matches in "<page>" page search results
    Examples:
      | user         | page              | searchTerm      | count |
      | businessAuto | All Devices       | AuthorisedRepRT | 1     |
      | businessAuto | All Devices       | ManufacturerRT  | 1     |
      | businessAuto | All Products      | AuthorisedRepRT | 1     |
      | businessAuto | All Products      | ManufacturerRT  | 1     |
      | businessAuto | All Organisations | AuthorisedRepRT | 1     |
      | businessAuto | All Organisations | ManufacturerRT  | 1     |

  @ignore
  Scenario: S7d Verify RAG Status