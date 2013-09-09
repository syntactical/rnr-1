Feature: Accessible website

   Scenario Outline: Viewable banner

      In order to know that I am at the right place
      As a user of rnr.thoughtworks.com
      I want to be able to see an 'rnr' banner when I am on the site.

      Given a user is using the browser <browser>
      When the user goes to rnr.thoughtworks.com
      Then a banner should be visible

      Examples:
         | browser |
         | Firefox |
         | Chrome  |
      #  | Safari  |  Safari is soo slooow.  :(
