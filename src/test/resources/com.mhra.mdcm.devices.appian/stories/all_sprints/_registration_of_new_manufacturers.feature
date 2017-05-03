Feature: As a customer I want to register new manufacturers
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


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
    And I view new task with link "New Manufacturer Registration Request" for the new account
    #Then I should see a new task for the new account
#    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    #And The completed task status should update to "Completed"
    When I go to records page and click on "Organisations"
    And I search for stored organisation in "Organisations" page
    Then All organisation search result should return 1 matches
    Examples:
      | user              | logBackInAs  | accountType   | countryName |
#      | manufacturerAuto  | businessAuto | manufacturer  | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  |


  @mdcm-292 @mdcm-496 @mdcm-21 @_sprint3 @_sprint5 @mdcm-134 @mdcm-164 @_sprint6 @wip @bug
  Scenario Outline: Verify new product id is generated for each product submitted by manufacturer
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
    #Then I should see a new task for the new account
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    #And The completed task status should update to "Completed"
    When I go to records page and click on "Organisations"
    And I search for stored organisation in "Organisations" page
    Then All organisation search result should return 1 matches
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | count | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | 0     | Brazil      |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | 0     | Belarus     |

