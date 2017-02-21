Feature: As a business and account holder, I want to be able to update party details associated with an account
  so that customers unable to use the portal can still provide the latest information on the account, and I can correct minor errors made by the customer

  @regression @mdcm-175 @sprint2
  Scenario Outline: Business users should be able to edit and update manufacturer account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm       | keyValuePairs                                          |
      | businessAuto | Accounts | ManufacturerRT01 | address.line1,address.line2,city.town,country,postcode |
      | businessAuto | Accounts | ManufacturerRT01 | org.telephone,org.fax,job.title                        |
      | businessAuto | Accounts | ManufacturerRT00 | org.name                                               |


  @regression @mdcm-175 @sprint2
  Scenario Outline: Business users should be able to edit and update authorisedRep account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm        | keyValuePairs                                  |
      | businessAuto | Accounts | AuthorisedRepRT01 | address.line1,address.line2,city.town,postcode |
      | businessAuto | Accounts | AuthorisedRepRT01 | org.telephone,org.fax,job.title                |
      | businessAuto | Accounts | AuthorisedRepRT00 | org.name                                       |


  @regression @mdcm-149 @mdcm-171 @mdcm-162 @mdcm-164 @sprint3 @sprint5 @sprint6
  Scenario Outline: Manufacturer and authorisedRep user should be able to update account contact details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the contact person details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    And I should see creation and association dates
    Examples:
      | user              | keyValuePairs                                                                                      |
      | authorisedRepAuto | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone |
      | manufacturerAuto  | contact.job.title,contact.email,contact.telephone,contact.firstname,contact.lastname               |


  @regression @mdcm-13 @mdcm-171 @mdcm-162  @mdcm-164@sprint4 @sprint5 @sprint6 @wip
  Scenario Outline: Manufacturer and authorisedRep user should be able to update organisation details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the organisation details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    And I should see creation and association dates
    Examples:
      | user              | keyValuePairs                                                             |
      | authorisedRepAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | manufacturerAuto  | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | authorisedRepAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |
      | manufacturerAuto  | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website |


  @regression @readonly @mdcm-171 @mdcm-261 @mdcm-277 @sprint4 @sprint5
  Scenario Outline: Verify correct roles are displayed for approved UK account holder
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    Then I should see the correct "<expectedRoles>" roles
    When I click on edit "Organisation" details
    And Address type is not editable
    Examples:
      | user              | expectedRoles                          |
      | authorisedRepAuto | Authorised Representative              |
      | manufacturerAuto  | Manufacturer,Authorised Representative |


  @regression @readonly @mdcm-21 @sprint5
  Scenario Outline: Verify list of manufacturer table showing correct details
    Given I am logged into appian as "<user>" user
    When I go to list of manufacturers page
    Then I should see the following columns for "<expectedHeadings>" list of manufacturer table
    Examples:
      | user              | expectedHeadings                                                                             |
      | manufacturerAuto  | Organisation name,Organisation address,Organisation country,Manufacturer registration status |
      | authorisedRepAuto | Organisation name,Organisation address,Organisation country,Manufacturer registration status |


  @regression @mdcm-21 @mdcm-162 @mdcm-171 @mdcm-485 @sprint5 @wip @ignore
  Scenario Outline: Manufacturer and authorisedRep user should be able to update manufacturer details
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on random manufacturer with status "<status>"
    And I update the manufacturer details with following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my manufacturer details page
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    Then Verify the WIP entry details for the "New Manufacturer Registration Request" task is correct
    And Verify task information matches users changes
    Examples:
      | user             | logBackInAs  | keyValuePairs                                                             | status     |
#      | manufacturerAuto | businessAuto | org.address1,org.address2,org.city,org.postcode,org.telephone,org.website | Registered |
#      | manufacturerAuto | businessAuto |contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone | Registered |
      | authorisedRepAuto | businessAuto |org.address1,org.address2,org.city,org.postcode,org.telephone,org.website | Registered |
      | authorisedRepAuto | businessAuto |contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone          | Registered |


  @regression @mdcm-263 @sprint6 @mdcm-275 @sprint7 @wip
  Scenario Outline: Verify only 1 task is created when update EXISTING manufacturer with multiple devices
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on random manufacturer with status "<status>"
    When I add a device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | <gmdn1>                |
      | customMade     | true                   |
    And I add another device to SELECTED manufacturer with following data
      | deviceType     | General Medical Device |
      | gmdnDefinition | <gmdn2>                |
      | customMade     | true                   |
    And Proceed to payment and confirm submit device details
    Then I should see stored manufacturer appear in the manufacturers list
    When I logout of the application
    And I am logged into appian as "<logBackInAs>" user
    And I go to WIP tasks page
    Then Verify the WIP entry details for the "<taskType>" task is correct
    When I view task for the new account in WIP page
    Then Task contains correct devices and products and other details
    And Task shows devices which are arranged by device types
    And I assign the task to me and "<approveReject>" the generated task
#    Then The task should be removed from WIP tasks list
    Then The completed task status should update to "Completed"
    When I search accounts for the stored organisation name
    Then I should see at least 0 account matches
    Examples:
      | user             | logBackInAs  | status         | gmdn1                | gmdn2           | approveReject | taskType                                 |
      | authorisedRepAuto | businessAuto | Registered     | Blood weighing scale | Autopsy measure | approve       | Update Manufacturer Registration Request    |
      | authorisedRepAuto | businessAuto | Not Registered | Blood weighing scale | Autopsy measure | reject       | Update Manufacturer Registration Request |
#      | manufacturerAuto | businessAuto | Registered     | Blood weighing scale | Autopsy measure | approve       | Update Manufacturer Registration Request    |
#      | manufacturerAuto | businessAuto | Not Registered | Blood weighing scale | Autopsy measure | reject       | Update Manufacturer Registration Request |

