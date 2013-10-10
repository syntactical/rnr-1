Scenario: Calculate based on user entered accrual rate

Given I am a Thoughtworker
When I go to rnr.thoughtworks.com
And I enter my start date prior to the current calendar year
And I enter my accrual rate
And I enter my rollover days
And I click submit
Then my vacation days are displayed