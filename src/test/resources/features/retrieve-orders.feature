Feature: Retrieve Orders from 4 DB Instances

  Scenario: Retrieving Orders
    When Service is up and running "orders"
      |@Q(txnUUID=1000)|@H(storeNumber=0,regNo)|
    Then Service should be "200"
    And Response body is "response-payloads/orders.json"
