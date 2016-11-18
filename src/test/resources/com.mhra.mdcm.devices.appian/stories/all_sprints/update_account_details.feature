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
      | user         | link     | searchTerm       | keyValuePairs                                                                            |
      | businessAuto | Accounts | ManufacturerTest | job.title=random                                                                         |
      | businessAuto | Accounts | ManufacturerTest | org.name=random                                                                          |
      | businessAuto | Accounts | ManufacturerTest | address.line1=random,address.line2=random,city.town=random,country=random,postcod=random |
      | businessAuto | Accounts | ManufacturerTest | org.telephone=random,org.fax=random                                                      |
