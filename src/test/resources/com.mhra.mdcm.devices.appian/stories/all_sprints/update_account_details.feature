Feature: As a business user, I want to be able to update party details associated with an account
  so that customers unable to use the portal can still provide the latest information on the account, and I can correct minor errors made by the customer

  @mdcm-175 @regression
  Scenario Outline: Users should be able to edit and update account details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    When I search for account with following text "<searchTerm>"
    Then I should see at least 1 account matches
    When I view a randomly searched account and update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in the account page
    Examples:
      | user         | link     | searchTerm       | keyValuePairs                                         |
      | businessAuto | Accounts | ManufacturerST | job.title                                             |
      | businessAuto | Accounts | ManufacturerST | org.name                                              |
      | businessAuto | Accounts | ManufacturerST | address.line1,address.line2,city.town,country,postcod |
      | businessAuto | Accounts | ManufacturerST | org.telephone,org.fax                                 |


  @mdcm-149 @regression
  Scenario Outline: User should be able to update contact details
    Given I am logged into appian as "<user>" user
    When I go to my accounts page
    And I update the following data "<keyValuePairs>"
    Then I should see the changes "<keyValuePairs>" in my accounts page
    Examples:
      | user              | keyValuePairs                                                                                      |
      | authorisedrepNoor | contact.title,contact.firstname,contact.lastname,contact.job.title,contact.email,contact.telephone |
      | manufacturerNoor  | contact.job.title,contact.email,contact.telephone,contact.firstname,contact.lastname               |


