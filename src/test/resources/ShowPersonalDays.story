Scenario: User gets number of vacation days accrued as of current date

Given I started on January 1 of this year
When I request my number of vacation days as of today
Then I should have <days> personal days

Examples:
|days|
|7.0|