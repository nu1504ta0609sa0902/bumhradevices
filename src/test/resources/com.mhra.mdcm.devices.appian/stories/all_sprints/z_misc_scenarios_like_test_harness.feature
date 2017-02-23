Feature: Aa a user I would like to verify features which are not related to devices and manufacturers
  So that I can be reassured my tests will not fail due to unexpected circumstances

  @2399 @readonly @sprint7
  Scenario Outline: Verify the new countries list in business test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    When I go to test harness page
    And I enter "<searchTerm>" in the new country field
    Then I should see following "<matches>" returned by autosuggests
    Examples:
      | user         | searchTerm | matches          |
      | businessNoor | ZZ         | No results found |
      | businessNoor | Ba         | Bangladesh       |
      | businessNoor | Au         | Australia        |

  @2399 @3365 @readonly @sprint7
  Scenario Outline: Verify the new countries list in MANUFACTURER test harness uses autosuggestion
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I enter "<searchTerm>" in the new country field in manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm | matches          |
      | manufacturerAuto  | Au         | Australia        |
      | manufacturerAuto  | ZZ         | No results found |
      | authorisedRepAuto | Au         | Australia        |
      | authorisedRepAuto | ZZ         | No results found |


  @mdcm-465 @mdcm-164 @readonly @sprint5 @sprint6
  Scenario Outline: Verify no EU countries are displayed to authorisedReps
    Given I am logged into appian as "<user>" user
    And I go to register a new manufacturer page
    And I enter "<searchTerm>" in the new country field in manufacturer test harness
    Then I should see following "<matches>" returned by manufacturer test harness autosuggests
    Examples:
      | user              | searchTerm      | matches          |
      | manufacturerAuto  | randomEUCountry | No results found |
      | authorisedRepAuto | randomEUCountry | No results found |


  @regression @1838 @sprint13 @wip
  Scenario Outline: Users should be able to search using  GMDN code or term
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I search for device type "<deviceType>" with gmdn "<gmdn>"
    Then I should see at least <count> devices matches
    Examples:
      | user              | deviceType             | gmdn      | count |
      | authorisedRepAuto | General Medical Device | Blood     | 1     |
      | authorisedRepAuto | General Medical Device | 17500     | 1     |
      | authorisedRepAuto | General Medical Device | HllNBlood | 0     |
      | authorisedRepAuto | General Medical Device | 175001    | 0     |
      | manufacturerAuto  | General Medical Device | Blood     | 1     |
      | manufacturerAuto  | General Medical Device | 17500     | 1     |

  @regression @1838 @4211 @sprint12 @sprint13 @wip
  Scenario Outline: Users should be able to view all gmdn terms or definitions
    Given I am logged into appian as "<user>" user
    And I go to list of manufacturers page
    And I click on a random manufacturer
    When I click on view all gmdn term or definitions for device type "<deviceType>"
    Then I should see all gmdn term and definition table
    When I search for gmdn "<gmdn>"
    Then I should see at least <count> devices matches
    Examples:
      | user              | deviceType                        | gmdn      | count |
      | authorisedRepAuto | General Medical Device            | Air       | 1     |
      | authorisedRepAuto | In Vitro Diagnostic Device        | Blood     | 1     |
      | authorisedRepAuto | Active Implantable Medical Device | Blood     | 1     |
      | authorisedRepAuto | System or Procedure Pack          | Blood     | 1     |
      | manufacturerAuto  | General Medical Device            | Air       | 1     |
      | manufacturerAuto  | In Vitro Diagnostic Device        | Blood     | 1     |
      | manufacturerAuto  | Active Implantable Medical Device | Blood | 1     |
      | manufacturerAuto  | System or Procedure Pack          | Blood    | 1     |
