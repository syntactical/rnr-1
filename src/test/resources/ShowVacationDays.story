Scenario: User gets number of vacation days accrued as of current date

Given I started two weeks ago
When I request my number of vacation days as of today
Then the number of vacation days I have is my daily accrual rate times 14