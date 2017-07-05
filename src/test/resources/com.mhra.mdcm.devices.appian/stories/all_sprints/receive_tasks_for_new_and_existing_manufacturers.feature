Feature: As a business user, I want a task to be created each time a customer submits or updates a MANUFACTURER
  So that I know an action to review the request is required by myself or another team member


  @regression @mdcm-15 @2324 @mdcm-21 @2323 @mdcm-39 @2312 @mdcm-186 @2258 @mdcm-240 @2216 @_sprint4 @_sprint2 @_sprint3 @_sprint5 @2910 @_sprint7 @bug
  Scenario Outline: Business users should be able to review and process manufacturer and device registration tasks
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType                | gmdnDefinition      | customMade | listOfProductNames |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | Active Implantable Device | Desiccating chamber | true       | setmeup1           |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Desiccating chamber | true       | setmeup2           |


  @regression @mdcm-15 @2324 @mdcm-21 @2323 @mdcm-39 @2312 @mdcm-186 @2258 @_sprint2 @_sprint3 @_sprint5 @3755 @_sprint11 @2910 @_sprint7 @2049 @_sprint8 @3207 @5349 @_sprint21
  Scenario Outline: Business users to review and process manufacturer and device registration task for IVD List A
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
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
    And The gmdn code or term is "displayed" in summary section
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And Check task contains correct devices "<gmdnDefinition>" and other details
    When I assign the AWIP page task to me and "approve" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType                 | gmdnDefinition        | riskClassification | listOfProductNames | productMake | productModel | notifiedBody | subjectToPerfEval | newProduct | conformsToCTS |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford,hyundai       | ford        | focus        | NB 0086 BSI  | true              | true       | true          |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | In Vitro Diagnostic Device | Androgen receptor IVD | list a             | ford,honda         | ford        | focus        | NB 0086 BSI  | true              | true       | true          |


  @regression @mdcm-161 @2276 @mdcm-232 @2221 @mdcm-164 @2274 @mdcm-240 @2216 @_sprint4 @_sprint6
  Scenario Outline: Register manufacturer as authorisedRep and verify status of letter of designation is correct
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType         | <deviceType>         |
      | gmdnDefinition     | <gmdnDefinition>     |
      | customMade         | <customMade>         |
      | listOfProductNames | <listOfProductNames> |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    And The designation letter should be attached and the status should be "Awaiting Review"
    Examples:
      | user              | logBackInAs  | accountType   | countryName | deviceType                | gmdnDefinition       | customMade | listOfProductNames |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Desiccating chamber  | true       | ford               |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Active Implantable Device | Blood weighing scale | false      | fiesta             |


  @regression @mdcm-263 @2197 @_sprint6 @4088 @_sprint11 @2185 @_sprint8 @2833 @_sprint14 @wip
  Scenario Outline: Verify only 1 task is created when we create NEW manufacturer with multiple devices
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
      | deviceType             | <deviceType> |
      | gmdnDefinition         | <gmdn2>      |
      | customMade             | false        |
      | relatedDeviceSterile   | true         |
      | relatedDeviceMeasuring | true         |
      | riskClassification     | class1       |
      | notifiedBody           | NB 0086 BSI  |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    Then I search and view new task in AWIP page for the newly created manufacturer
    Then Task contains correct devices and products and other details for "<deviceType>"
    And Task shows devices which are arranged by device types
    When I assign the AWIP page task to me and "<approveReject>" the generated task
    Then The task status in AWIP page should be "Completed" for the new account
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName | gmdn1                | gmdn2           | approveReject | deviceType             |
      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      | Blood weighing scale | Autopsy measure | approve       | General Medical Device |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  | Blood weighing scale | Autopsy measure | approve       | General Medical Device |


  @4090 @_sprint13 @4088 @_sprint11 @wip @bug
  Scenario Outline: Users should be able to search and filter tasks in WIP page
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer to add devices
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | <gmdn>                 |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    When I logout and log back into appian as "<logBackInAs>" user
    And I go to application WIP page
    And I filter WIP tasks by "<filterBy>"
    And I filter WIP tasks by "<filterBy2>"
    Then Check the WIP entry details for the "<taskType>" task is correct
    When I view task for the new account in WIP page
    And I assign the task to me and "approve" the generated task
    #Then The completed task status should update to "Completed"
    Then The task should be removed from tasks list
    Examples:
      | user              | logBackInAs  | gmdn                 | filterBy | filterBy2 | taskType                                 |
      | manufacturerAuto  | businessAuto | Blood weighing scale | orgName  | taskType  | Update Manufacturer Registration Request |
      | authorisedRepAuto | businessAuto | Blood weighing scale | orgName  | taskType  | Update Manufacturer Registration Request |
