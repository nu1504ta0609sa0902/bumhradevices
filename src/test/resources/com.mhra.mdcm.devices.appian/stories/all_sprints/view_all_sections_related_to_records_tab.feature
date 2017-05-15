Feature: As a business user, I want to navigate to different sections like Accounts and organisations
  so that I can quickly confirm if they are known to the MHRA and retrieve key contact information

  @regression @mdcm-23 @mdcm-126 @2797 @mdcm-626 @readonly @_sprint1 @_sprint6 @_sprint7
  Scenario Outline: As a business user I should be able to view all sections in records page and verify details
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link                | pageHeading         | columns                                                                                                          |
      | businessAuto | GMDN Devices        | GMDN Devices        | Device type,GMDN code,GMDN term                                                                                  |
      | businessAuto | Registered Products | Registered Products | Device Type,Product Make,Product Model,Product Name,Device Label,Manufacturer,Country,Authorised Representative  |
      | businessAuto | Organisations       | Organisations       | Name,Role,Contact name,Address,Country,Status                                                                    |
      | businessAuto | Accounts            | Accounts            | Organisation name,Account number,Organisation role,Contact name,Organisation address,Organisation country,Status |

  @regression @4006 @_sprint11 @readonly
  Scenario Outline: Completed tasks page should show correct data
    Given I am logged into appian as "<user>" user
    When I go to completed task page
    Then I should see the following columns for completed task page
      | columns | <columns> |
    Examples:
      | user         | columns                                   |
      | businessAuto | Task,Name,Role,Submitted,Status,Documents |


  @regression @mdcm-23 @mdcm-126 @readonly @_sprint1 @_sprint6
  Scenario Outline: As a business user I should be able to view all organisation page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And I should see the following columns for "<link>" page
      | columns | <columns> |
    Examples:
      | user         | link          | pageHeading   | columns                                       |
      | businessAuto | Organisations | Organisations | Name,Role,Contact name,Address,Country,Status |


  @mdcm-126 @mdcm-23 @readonly @_sprint1 @_sprint6 @bug
  Scenario Outline: By default list of accounts should be displayed in a to z order
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    And The items in "<pageHeading>" page are displayed in alphabetical order
    Examples:
      | user         | link          | pageHeading   |
      | businessAuto | Organisations | Organisations |
      | businessAuto | Accounts      | Accounts      |


  @regression @mdcm-126 @mdcm-23 @1937 @3837 @3895 @_sprint10 @readonly @_sprint1 @_sprint6
  Scenario Outline: Users should be able to filter search and sort by headings
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    When I perform a search for "<searchTerm>" in "<link>" page
    And I filter by "<filterBy>" for the value "<filterValue>" in "<pageHeading>" page
    Then I should see table column "<column>" displaying only "<filterValue>" in "<pageHeading>" page
    When I clear the filter by "<filterBy>" in "<pageHeading>" page
    And I sort items in "<pageHeading>" page by "<tableHeading>"
    Then I should see table column "<column>" also displaying "<unFilteredData>" in "<pageHeading>" page
    Examples:
      | user         | link                | pageHeading         | filterBy    | filterValue | unFilteredData     | tableHeading | searchTerm | column      |
#      | businessAuto | Accounts            | Accounts            | Organisation Role | Authorised         | Manufacturer       | Organisation name | RT01       | Role        |
#      | businessAuto | Accounts            | Accounts            | Organisation Role | Manufacturer       | Authorised         | Organisation name | RT01       | Role        |
#      | businessAuto | Organisations       | Organisations       | Organisation Role | Manufacturer       | Authorised         | Name              | RT01       | Role        |
#      | businessAuto | Organisations       | Organisations       | Registered status | Registered         | Not Registered     | Name              | RT01       | Status      |
#      | businessAuto | Registered Products | Registered Products | Device type       | Active Implantable | In Vitro           | Authorised Representative       | RT01       | Device Type |
      | businessAuto | Registered Products | Registered Products | Device type | In Vitro    | Active Implantable | Manufacturer | RT01       | Device Type |


  @mdcm-23 @readonly @_sprint6 @3837 @_sprint10
  Scenario Outline: As a business user I should be able to search and filter for an existing organisation
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    And I search for a "<existing>" organisation
    Then All organisation search result should return <count> matches
    Examples:
      | user         | link          | existing     | count |
      | businessAuto | Organisations | existing     | 1     |
      | businessAuto | Organisations | non existing | 0     |


  @mdcm-2797 @readonly @mdcm-626 @3895 @_sprint7 @_sprint10
  Scenario Outline: As a business user I should be able to view accounts, devices, all organisations and products page
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<link>"
    Then I should see items and heading "<pageHeading>" for link "<link>"
    Examples:
      | user         | pageHeading         | link                |
      | businessAuto | Registered Products | Registered Products |
      | businessAuto | Registered Devices  | Registered Devices  |
      | businessAuto | Accounts            | Accounts            |
      | businessAuto | GMDN Devices        | GMDN Devices        |


  @mdcm-23 @2797 @mdcm-626 @readonly @3895 @_sprint6 @_sprint7 @_sprint10
  Scenario Outline: As a business user I should be able to RANDOMLY search for an existing organisation products and accounts
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for random account organisation or product in "<page>" page
    Then I should see at least <count> matches in "<page>" page search results
    Examples:
      | user         | page                | count |
      | businessAuto | Organisations       | 1     |
      | businessAuto | GMDN Devices        | 1     |
      | businessAuto | Registered Products | 1     |

  @2797 @mdcm-626 @readonly @_sprint7
  Scenario Outline: As a business user I should be able to perform SPECIFIC search for an existing organisation products devices and accounts
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    Then I should see at least <count> matches in "<page>" page search results
    Examples:
      | user         | page                | searchTerm           | count |
      | businessAuto | GMDN Devices        | AuthorisedRepRT      | 1     |
      | businessAuto | GMDN Devices        | ManufacturerRT       | 1     |
      | businessAuto | GMDN Devices        | 13459                | 1     |
      | businessAuto | GMDN Devices        | Blood weighing scale | 1     |
      | businessAuto | Registered Products | 56797                | 1     |
      | businessAuto | Registered Products | AuthorisedRepRT      | 1     |
      | businessAuto | Registered Products | ManufacturerRT       | 1     |
      | businessAuto | Organisations       | AuthorisedRepRT      | 1     |


  @2797 @readonly @_sprint7
  Scenario Outline: As a business user I should be able to view product details by gmdn and organisation names
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
#    Then I should see at least <count> matches in "<page>" page search results
    When I view a random product by "<fieldName>"
    Then I should see all the correct product details
    Examples:
      | user         | page                | searchTerm      | count | fieldName    |
      | businessAuto | Registered Products | 56797           | 1     | Product Name |
      | businessAuto | Registered Products | AuthorisedRepRT | 1     | Product Name |
      | businessAuto | Registered Products | ManufacturerRT  | 1     | Product Name |


  @2797 @readonly @_sprint7
  Scenario Outline: As a business user I should be able to manufacturer details after a search
    Given I am logged into appian as "<user>" user
    When I go to records page and click on "<page>"
    And I perform a search for "<searchTerm>" in "<page>" page
    And I click on a random organisation link "<searchTerm>" in "<page>" page
    Then I should see business manufacturer details page for the manufacturer
    Examples:
      | user         | page                | searchTerm      | count |
      | businessAuto | Registered Products | AuthorisedRepRT | 1     |
      | businessAuto | Registered Products | ManufacturerRT  | 1     |


  @2797 @mdcm-626 @readonly @3894 @_sprint10 @_sprint7
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
      | user         | pageHeading  | searchTerm      | count | deviceType | deviceType2 | columns                                                          |
      | businessAuto | GMDN Devices | AuthorisedRepRT | 1     | IVD        | Non-IVD     | Organisation name,Country,Authorised Representative              |
      | businessAuto | GMDN Devices | ManufacturerRT  | 1     | Non-IVD    | IVD         | Organisation name,Organisation country,Authorised Representative |