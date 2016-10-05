
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.http.HttpStatus;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author slattu_w
 */
public class TestStepDefs extends SpringIntegrationTest{
    
    @When("^the client calls /test/version$") 
    public void client_issues_GET_version() throws Throwable{
        String url = "http://localhost:" + port + "/test/version";
        response = restTemplate.getForEntity(url, String.class);
    }
    
    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of (int statusCode) throws Throwable{
        HttpStatus currentStatusCode = response.getStatusCode();
        assertThat(currentStatusCode.value(), is(statusCode));
    }
    
    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version(String vers) throws Throwable {
        assertThat(response.getBody(), is(vers));
    }
}
