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



#  @regression @2049 @sprint8
#  Scenario Outline: Check correct options shown to users when adding devices
#    Given I am logged into appian as "<user>" user
#    And I go to list of manufacturers page
#    And I click on a random manufacturer
#    When I try to add an incomplete device to SELECTED manufacturer with following data
#      | deviceType         | <deviceType>         |
#      | gmdnDefinition     | <gmdnDefinition>     |
#      | customMade         | false                |
#      | riskClassification | <riskClassification> |
#      | notifiedBody       | <notifiedBody>       |
#      | isBearingCEMarking | <isBearingCEMarking> |
#      | devicesCompatible  | <devicesCompatible>  |
#    Then I should see validation error message in devices page with text "<errorMsg>"
#    And I should be prevented from adding the devices
#    Examples:
#      | user              | deviceType               | gmdnDefinition      | riskClassification | notifiedBody | isBearingCEMarking | devicesCompatible | errorMsg                                                                                         |
#      | authorisedRepAuto | General Medical Device   | Blood               | class2a            | NB 0086 BSI  |                    |                   | You cannot register class IIa devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               | class2b            | NB 0086 BSI  |                    |                   | You cannot register class IIb devices with the MHRA                                              |
#      | manufacturerAuto  | General Medical Device   | Blood               | class3             | NB 0086 BSI  |                    |                   | You cannot register class III devices with the MHRA                                              |
#      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber |                    | NB 0086 BSI  | true               |                   | You cannot register this as a System/procedure pack because all the components must be CE marked |
#      | authorisedRepAuto | System or Procedure Pack | Desiccating chamber |                    | NB 0086 BSI  |                    | false             | You cannot register this as a System/procedure pack because all the components must be CE marked |
#      | manufacturerAuto  | System or Procedure Pack | Desiccating chamber |                    | NB 0086 BSI  | false              | false             | This System/procedure pack cannot be registered with us                                          |

