Feature: SIM Card Activation
  As a telecom provider
  I want to activate SIM cards for customers
  So that they can use our mobile services

  Scenario: Successful SIM card activation
    Given a customer with email "customer@example.com" and ICCID "1255789453849037777"
    When I submit an activation request for the SIM card
    Then the SIM card should be successfully activated
    And the database record with ID 1 should show the SIM card as active

  Scenario: Failed SIM card activation
    Given a customer with email "customer@example.com" and ICCID "8944500102198304826"
    When I submit an activation request for the SIM card
    Then the SIM card activation should fail
    And the database record with ID 2 should show the SIM card as inactive