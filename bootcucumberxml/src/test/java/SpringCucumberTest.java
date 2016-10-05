
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author slattu_w
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = false, features = "src/test/resources", format = {"pretty", "json:src/test/report/cucumber.json"}, tags={"@version, @person"})
public class SpringCucumberTest {
    
}
