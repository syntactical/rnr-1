Scenario: User can specify number of rollover vacation days as of Jan 1

Given I am a Thoughtworker
When I go to rnr.thoughtworks.com
And I enter my start date exactly one year ago
And I enter my rollover days
And I enter my accrual rate
And I click submit
Then my vacation days are displayed
