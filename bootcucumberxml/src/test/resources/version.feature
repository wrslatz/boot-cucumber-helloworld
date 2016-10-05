@version
Feature: Get the version of the program
	Scenario: client makes call to GET /test/version
		When the client calls /test/version
		Then the client receives status code of 200
		And the client receives server version Version 1.0
