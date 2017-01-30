Feature: As a customer I want to register new manufacturers
  and upload proof so that I am an authorised representative for an organisation when
  so that I am granted access to that and can then register overseas manufacturers on their behalf


  @regression @mdcm-161 @mdcm-21 @mdcm-232 @mdcm-496 @sprint4 @sprint5 @wip @bug
  Scenario Outline: Users should be able to upload proof they are authorised reps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | General Medical Device |
      | gmdnDefinition         | Blood weighing scale    |
      | customMade             | true                   |
      | relatedDeviceSterile   | true                   |
      | relatedDeviceMeasuring | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I view new task with link "New Manufacturer Registration Request" for the new account
    #Then I should see a new task for the new account
#    When I download the letter of designation
    And I assign the task to me and "approve" the generated task
    Then The task should be removed from tasks list
    And The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user              | logBackInAs  | accountType  | countryName |
      | manufacturerAuto  | businessAuto | manufacturer | Bangladesh  |
      | authorisedRepAuto | businessAuto | authorisedRep | Bangladesh  |


  @regression @mdcm-292 @mdcm-496 @mdcm-21 @sprint3 @sprint5 @wip @bug
  Scenario Outline: Verify new product id is generated for each product submitted by manufacturer
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    When I create a new manufacturer using manufacturer test harness page with following data
      | accountType | <accountType> |
      | countryName | <countryName> |
    And I add devices to NEWLY created manufacturer with following data
      | deviceType             | General Medical Device |
      | gmdnDefinition         | Blood weighing scale    |
      | customMade             | true                   |
      | relatedDeviceSterile   | true                   |
      | relatedDeviceMeasuring | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    #Then I should see a new task for the new account
    And I view new task with link "New Manufacturer Registration Request" for the new account
    When I assign the task to me and "<approveReject>" the generated task
    Then The task should be removed from tasks list
    And The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least <count> account matches
    Examples:
      | user              | logBackInAs  | accountType   | approveReject | count | countryName |
      | manufacturerAuto  | businessAuto | manufacturer  | approve       | 1     | Nepal       |
      | authorisedRepAuto | businessAuto | authorisedRep | approve       | 1     | Belarus     |


#  @regression @mdcm-23 @sprint6
#  Scenario Outline: Verify user is able register manufacturer WIHTOUT DEVICES
#    Given I am logged into appian as "<user>" user
#    And I go to register a new manufacturer page
#    When I create a new manufacturer using manufacturer test harness page with following data
#      | accountType | <accountType> |
#      | countryName | <countryName> |
##    And I add devices to NEWLY created manufacturer with following data
##      | deviceType             | General Medical Device |
##      | gmdnDefinition         | Blood weighing scale    |
##      | customMade             | true                   |
##      | relatedDeviceSterile   | true                   |
##      | relatedDeviceMeasuring | true                   |
##    And Proceed to payment and confirm submit device details
#    Then I should see stored manufacturer appear in the manufacturers list
#    When I logout of the application
#    And I am logged into appian as "<logBackInAs>" user
#    #Then I should see a new task for the new account
#    And I view new task with link "New Manufacturer Registration Request" for the new account
#    When I assign the task to me and "<approveReject>" the generated task
##    Then The task should be removed from tasks list
#    Then The task should be removed from WIP tasks list
#    And The completed task status should update to "Completed"
#    And I search for a stored organisation in all organisation page
#    Then The all organisation search result should return <count> matches
##    When I search accounts for the stored organisation name
##    Then I should see at least <count> account matches
#    Examples:
#      | user              | logBackInAs  | accountType   | approveReject | count | countryName |
#      | manufacturerAuto  | businessAuto | manufacturer  | approve       | 1     | Nepal       |
#      | authorisedRepAuto | businessAuto | authorisedRep | approve       | 1     | Belarus     |


