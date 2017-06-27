@e2e
Feature: End 2 End Scenarios to verify system is behaving correctly from a high level view

  @1836 @1929 @2327 @2278 @2260 @2193 @2290 @2273 @2324 @2191 @2190 @2311 @2263 @2292 @2258
  Scenario Outline: S1 Manufacturer account registration
#Register new manufacturer account, approve the task and check MHRA approval email received
    Given I am logged into appian as "<businessUser>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "Account request approved for"
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA12345A"
#Log back in as newly created manufacturer account user and register a new organisation with devices
    And I go to list of manufacturers page
    And Provide indication of devices made
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType>      |
      | countryName | <countryNameNonEU> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
#Log back and verify task created for newly created manufacturer
    When I logout and log back into appian as "<businessUser>" user
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "has been Approved" and stored application identifier
    Examples:
      | businessUser | logBackInAas     | accountType  | approveReject | countryName    | countryNameNonEU | newAccountEmail         | newOrganisationEmail                  |
      | businessNoor | manufacturerNoor | manufacturer | approve       | United Kingdom | Bangladesh       | New Account Request for | Manufacturer Registration Request for |


  @1836 @1929 @2327 @2278 @2260 @2193 @2290 @2273 @2324 @2191 @2190 @2311 @2263 @2292 @2258
  Scenario Outline: S2 AuthorisedRep account registration for non uk manufacturers
#Register new manufacturer account, approve the task and check MHRA approval email received
    Given I am logged into appian as "<businessUser>" user
    When I create a new account using business test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored account with heading "Account request approved for"
    And I should received an email with password for new account with heading "account creation" and stored username
    When I logout and logback in with newly created account and update the password to "MHRA12345A"
#Log back in as newly created manufacturer account user and register a new organisation with devices
    And I go to list of manufacturers page
    And Provide indication of devices made
    And I go to register a new manufacturer page
    And I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType>   |
      | countryName | <countryNameEU> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
#Log back and verify task created for newly created manufacturer
    When I logout and log back into appian as "<businessUser>" user
    Then I search and view new task in AWIP page for the new account
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    And I should received an email for stored manufacturer with heading "Request for manufacturer registration" and stored application identifier
    And I should received an email for stored manufacturer with heading "has been Approved" and stored application identifier
    Examples:
      | businessUser | logBackInAas     | accountType   | approveReject | countryName    | countryNameEU | newAccountEmail         | newOrganisationEmail              |
      | businessNoor | manufacturerNoor | authorisedRep | approve       | United Kingdom | Netherland    | New Account Request for | Manufacturer registration service |


  @1974 @1978 @4704 @1954 @1952
  Scenario Outline: S3 UK based manufacturer which is already registered and in need of CFS
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


  @1974 @1978 @4704 @1954 @1952
  Scenario Outline: S4 Non UK based authorised reps which is already registered and in need of CFS
    Given I am logged into appian as "authorisedRepAuto" user
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


  @1845 @1974 @1952 @1971 @3979 @4330 @5141 @5212 @5126 @5128 @5673 @5674 @5583
  Scenario Outline: S4b Register and approve Non UK based manufacturers for CFS
    Given I am logged into appian as "<user>" user
    And I go to device certificate of free sale page
    Then I should see a list of manufacturers available for CFS
    When I create a new manufacturer using CFS manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <country>     |
    And I add devices to NEWLY created CFS manufacturer with following data
      | deviceType     | Active Implantable Device |
      | gmdnDefinition | Desiccating chamber                |
      | customMade     | false                              |
      | notifiedBody   | NB 0086 BSI                        |
      | productName    | FordHybrid                         |
      | productModel   | FocusYeah                          |
    Then I should see correct device data in the review page
    And I submit the cfs application for approval
    When I logout and log back into appian as "<businessUser>" user
    And I search and view new task in AWIP page for the newly created manufacturer
    And I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the newly created manufacturer
    And I should received an email for stored manufacturer with heading "Free Sale"
    Examples:
      | user              | businessUser | accountType  | country       | approveReject | status    |
      | authorisedRepAuto | businessAuto | manufacturer | United States | approve       | Completed |
      | manufacturerAuto  | businessAuto | manufacturer | Brazil        | approve       | Completed |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149 @2257
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
    When I logout of the application
    And I am logged into appian as "<businessUser>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdn>" and other details
    And I assign the task to me and "approve" the generated task
    #And The completed task status should update to "Completed"
    And I should received an email for stored manufacturer with heading "<newOrganisationEmail>"
    Examples:
      | user              | businessUser | deviceType             | customMade | deviceSterile | deviceMeasuring | status     | gmdn                 | riskClassification | notifiedBody |
      | authorisedRepAuto | businessAuto | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |
      | manufacturerAuto  | businessAuto | General Medical Device | false      | true          | true            | Registered | Blood weighing scale | class1             | NB 0086 BSI  |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149
  Scenario Outline: S5b Update already registered manufacturers by adding and removing devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I click on random manufacturer with status "Registered"
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | false        |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
      | riskClassification     | class1       |
      | notifiedBody           | NB 0086 BSI  |
    And I remove the device with gmdn "<gmdn1>" code
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    And I view task for the new account in WIP page
    And I assign the task to me and "<approveReject>" the generated task
    #Then The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    And I should received an email for stored manufacturer with heading "<newOrganisationEmail>"
    Examples:
      | user             | logBackInAs  | gmdn1                | gmdn2           | approveReject | deviceType             |
      | manufacturerAuto | businessAuto | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | manufacturerAuto | businessAuto | Blood weighing scale | Autopsy measure | reject        | General Medical Device |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149
  Scenario Outline: S5c Update already registered manufacturers by adding devices with products
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I click on random manufacturer with status "Registered"
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    And I remove the device with gmdn "<gmdn1>" code
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    And I view task for the new account in WIP page
    And I assign the task to me and "<approveReject>" the generated task
    #Then The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    And I should received an email for stored manufacturer with heading "<newOrganisationEmail>"
    Examples:
      | user              | deviceType                         | gmdnDefinition      | customMade | productName |
      | authorisedRepAuto | Active Implantable Device | Desiccating chamber | true       | ford focus  |

  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149
  Scenario Outline: S6a Update manufacturer for authorised rep which is already registered by adding devices
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    And I view a random manufacturer with status "<status>"
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | Blood weighing scale   |
      | customMade     | true                   |
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType>     |
      | gmdnDefinition | <gmdnDefinition> |
      | customMade     | <customMade>     |
      | productName    | <productName>    |
    And Proceed to payment and confirm submit device details
    When I logout of the application
    And I am logged into appian as "<businessUser>" user
    And I view new task with link "Update Manufacturer Registration Request" for the new account
    Then Check task contains correct devices "<gmdnDefinition>" and other details
    And I assign the task to me and "approve" the generated task
    #And The completed task status should update to "Completed"
    And I should received an email for stored account with heading "<emailHeader>"
    Examples:
      | user              | businessUser | deviceType                         | customMade | status     | gmdnDefinition       | productName |
      | authorisedRepAuto | businessAuto | Active Implantable Device | false      | Registered | Blood weighing scale | ford focus  |


  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149
  Scenario Outline: S6b Update manufacturer for authorised rep which is already registered by adding and removing devices
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I click on random manufacturer with status "Registered"
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | <deviceType> |
      | gmdnDefinition | <gmdn1>      |
      | customMade     | true         |
    And I add another device to SELECTED manufacturer with following data
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | false        |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
      | riskClassification     | class1       |
      | notifiedBody           | NB 0086 BSI  |
    And I remove the device with gmdn "<gmdn1>" code
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    And I view task for the new account in WIP page
    And I assign the task to me and "<approveReject>" the generated task
    #Then The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs  | gmdn1                | gmdn2           | approveReject | deviceType             |
      | authorisedRepAuto | businessAuto | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | authorisedRepAuto | businessAuto | Blood weighing scale | Autopsy measure | reject        | General Medical Device |

  @2087 @2284 @2910 @2911 @2294 @2107 @2148 @2149
  Scenario: S6c Update manufacturer for authorised rep which is already registered by adding devices with products and removing products


  @ignore
  Scenario Outline: S7abc Search for organisations, devices and products
    Given I am logged into appian as "<businessUser>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    Then I should see at least <count> matches in "<page>" page search results
    Examples:
      | businessUser | page                | searchTerm      | count |
      | businessAuto | GMDN Devices        | AuthorisedRepRT | 1     |
      | businessAuto | GMDN Devices        | ManufacturerRT  | 1     |
      | businessAuto | Registered Products | AuthorisedRepRT | 1     |
      | businessAuto | Registered Products | ManufacturerRT  | 1     |
      | businessAuto | Organisations       | AuthorisedRepRT | 1     |
      | businessAuto | Organisations       | ManufacturerRT  | 1     |

  @ignore
  Scenario: S7d Verify RAG Status