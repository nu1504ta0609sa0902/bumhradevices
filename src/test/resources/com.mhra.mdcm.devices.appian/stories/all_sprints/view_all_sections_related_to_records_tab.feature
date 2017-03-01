Feature: As a business user, I want to access a list of organisations with an account
  so that I can quickly confirm if they are known to the MHRA and retrieve key contact information

  @regression @mdcm-23 @mdcm-126 @2797 @mdcm-626 @readonly @sprint1 @sprint6 @sprint7
  Scenario Outline: As a business user I should be able to view all sections in records page and verify details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link              | pageHeading       | columns                                                                                                          |
      | businessAuto | All Devices       | All Devices       | Device type,GMDN code,GMDN term                                                                                  |
      | businessAuto | All Products      | All Products      | Device Type,Product Make,Product Model,Product Name,Device Label,Manufacturer,Country,Authorised Representative  |
      | businessAuto | All Organisations | All Organisations | Name,Role,Contact name,Address,Country,Status                                                                    |
      | businessAuto | Accounts          | Accounts          | Organisation name,Account number,Organisation role,Contact name,Organisation address,Organisation country,Status |

  @regression @4006 @sprint11
  Scenario Outline: Completed tasks page should show correct data
    Given I am logged into appian as "<user>" user
    When I go to completed task page
    Then I should see the following columns for completed task page
      | columns | <columns> |
    Examples:
      | user         | columns                                   |
      | businessAuto | Task,Name,Role,Submitted,Status,Documents |


  @regression @mdcm-23 @mdcm-126 @readonly @sprint1 @sprint6
  Scenario Outline: As a business user I should be able to view all organisation page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link              | pageHeading       | columns                                       |
      | businessAuto | All Organisations | All Organisations | Name,Role,Contact name,Address,Country,Status |


  @mdcm-126 @mdcm-23 @readonly @sprint1 @sprint6 @bug
  Scenario Outline: By default list of accounts should be displayed in a to z order
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And The items in "<pageHeading>" page are displayed in alphabetical order
    Examples:
      | user         | link              | pageHeading       |
      | businessAuto | All Organisations | All Organisations |
      | businessAuto | Accounts          | Accounts          |


  @mdcm-126 @mdcm-23 @1937 @3837 @sprint10 @readonly @sprint1 @sprint6
  Scenario Outline: Users should be able to filter search and sort by headings
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    When I perform a search for "<searchTerm>" in "<link>" page
    And I filter by "<filterBy>" for the value "<organisationRole>" in "<pageHeading>" page
    And I sort items in "<pageHeading>" page by "<tableHeading>"
    Then I should see table column "<column>" displaying only "<organisationRole>" in "<pageHeading>" page
    When I clear the filter by "<filterBy>" in "<pageHeading>" page
    Then I should see table column "<column>" also displaying "<organisationRole2>" in "<pageHeading>" page
    Examples:
      | user         | link              | pageHeading       | filterBy          | organisationRole | organisationRole2 | tableHeading      | searchTerm | column |
      | businessAuto | Accounts          | Accounts          | Organisation Role | Authorised       | Manufacturer      | Organisation name | RT01       | Role   |
      | businessAuto | Accounts          | Accounts          | Organisation Role | Manufacturer     | Authorised        | Organisation name | RT01       | Role   |
      | businessAuto | All Organisations | All Organisations | Registered status | REGISTERED       | Manufacturer      | Name              | RT01       | Role   |
      | businessAuto | All Organisations | All Organisations | Organisation Role | Manufacturer     | Authorised        | Name              | RT01       | Role   |


  @mdcm-23 @readonly @sprint6 @3837 @sprint10
  Scenario Outline: As a business user I should be able to search and filter for an existing organisation
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I search for a "<existing>" organisation
    Then All organisation search result should return <count> matches
    Examples:
      | user         | link              | existing     | count |
      | businessAuto | All Organisations | existing     | 1     |
      | businessAuto | All Organisations | non existing | 0     |


  @mdcm-2797 @readonly @mdcm-626 @sprint7
  Scenario Outline: As a business user I should be able to view accounts, devices, all organisations and products page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    Examples:
      | user         | pageHeading  | link         |
      | businessAuto | All Products | All Products |
      | businessAuto | Devices      | Devices      |
      | businessAuto | Accounts     | Accounts     |
      | businessAuto | All Devices  | All Devices  |


  @mdcm-23 @2797 @mdcm-626 @readonly @sprint6 @sprint7
  Scenario Outline: As a business user I should be able to RANDOMLY search for an existing organisation products and accounts
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for random account organisation or product in "<page>" page
    Then I should see at least <count> matches in "<page>" page search results
    Examples:
      | user         | page              | count |
      | businessAuto | All Organisations | 1     |
      | businessAuto | All Devices       | 1     |
      | businessAuto | All Products      | 1     |

  @2797 @mdcm-626 @readonly @sprint7
  Scenario Outline: As a business user I should be able to perform SPECIFIC search for an existing organisation products devices and accounts
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    Then I should see at least <count> matches in "<page>" page search results
    Examples:
      | user         | page              | searchTerm           | count |
      | businessAuto | All Devices       | AuthorisedRepRT      | 1     |
      | businessAuto | All Devices       | ManufacturerRT       | 1     |
      | businessAuto | All Devices       | 13459                | 1     |
      | businessAuto | All Devices       | Blood weighing scale | 1     |
      | businessAuto | All Products      | 56797                | 1     |
      | businessAuto | All Products      | AuthorisedRepRT      | 1     |
      | businessAuto | All Products      | ManufacturerRT       | 1     |
      | businessAuto | All Organisations | AuthorisedRepRT      | 1     |


  @2797 @readonly @sprint7
  Scenario Outline: As a business user I should be able to view product details by gmdn and organisation names
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
#    Then I should see at least <count> matches in "<page>" page search results
    When I view a random product by "<fieldName>"
    Then I should see all the correct product details
    Examples:
      | user         | page         | searchTerm      | count | fieldName    |
      | businessAuto | All Products | 56797           | 1     | Product Name |
      | businessAuto | All Products | AuthorisedRepRT | 1     | Product Name |
      | businessAuto | All Products | ManufacturerRT  | 1     | Product Name |


  @2797 @readonly @sprint7
  Scenario Outline: As a business user I should be able to manufacturer details after a search
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    Then I should see business manufacturer details page for the manufacturer
    Examples:
      | user         | page         | searchTerm      | count |
      | businessAuto | All Products | AuthorisedRepRT | 1     |
      | businessAuto | All Products | ManufacturerRT  | 1     |


  @2797 @mdcm-626 @readonly @3894 @sprint10 @sprint7 @wip
  Scenario Outline: As a business user I should be able to view all manufacturers who are using a gmdn code or gmdn term
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<pageHeading>"
    And I filter items in "<pageHeading>" page by device type "<deviceType>"
    Then I should see only see device of type "<deviceType>" in "<pageHeading>" page
    When I clear the filter than I should see device of type "<deviceType2>"
    And I perform a search for "<searchTerm>" in "<pageHeading>" page
    Then I should see at least <count> matches in "<pageHeading>" page search results
    When I click on a random gmdn in all devices page
    Then I should see a list of manufacturers using this gmdn product
    And I should see the following columns "<columns>" for all devices list of manufacturer table
    Examples:
      | user         | pageHeading | searchTerm      | count | deviceType | deviceType2 | columns                                                          |
      | businessAuto | All Devices | AuthorisedRepRT | 1     | IVD        | Non-IVD     | Organisation name,Country,Authorised Representative              |
      | businessAuto | All Devices | ManufacturerRT  | 1     | Non-IVD    | IVD         | Organisation name,Organisation country,Authorised Representative |