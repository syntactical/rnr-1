Feature: Accessible website

   Scenario: Viewable banner in Firefox

      Given a user is using the browser Firefox
      When the user goes to rnr.thoughtworks.com
      Then a banner should be visible

   Scenario: Viewable banner in Chrome

      Given a user is using the browser Chrome
      When the user goes to rnr.thoughtworks.com
      Then a banner should be visible
