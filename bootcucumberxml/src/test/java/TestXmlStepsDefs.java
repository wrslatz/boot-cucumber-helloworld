
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author slattu_w
 */
public class TestXmlStepsDefs extends SpringIntegrationTest{
    
    RestTemplate myTemplate = new RestTemplate();
    
    String responseText;
    Scanner scan;
    org.w3c.dom.Document xmlResponse;
    NodeList nodes;
    
    @When("^the client calls /test/person$") 
    public void client_issues_POST_person() throws Throwable{
        String url = "http://localhost:" + port + "/test/person";
        
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("name", "Charlie");
        data.add("age","32");
        data.add("email","charlie@gmail.com");
        
        HttpHeaders header = new HttpHeaders();
        
        HttpEntity<MultiValueMap<String, String>> params = new HttpEntity<MultiValueMap<String,String>>(data, header);
        
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter());
        converters.add(new Jaxb2RootElementHttpMessageConverter());
        
        myTemplate.setMessageConverters(converters);
        
        response = myTemplate.postForEntity(url, params, String.class);
        responseText = response.getBody();
        System.out.println(responseText);
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlResponse = builder.parse(new ByteArrayInputStream(responseText.getBytes()));
        
        xmlResponse.getDocumentElement().normalize();
        nodes = xmlResponse.getElementsByTagName("Person");
    }
    
    @Then("^the client receives POST status code of (\\d+)$")
    public void the_client_receives_POST_status_code_of (int statusCode) throws Throwable{
        HttpStatus currentStatusCode = response.getStatusCode();
        assertThat(currentStatusCode.value(), is(statusCode));
    }
    
    @And("^the client receives xml of type (.+)$")
    public void the_client_receives_xmltype_person(String name) throws Throwable {
        String text = xmlResponse.getDocumentElement().getNodeName();
        assertThat(text, is(name));
    }
    
    @And("^the client receives person name (.+)$")
    public void the_client_receives_person_name(String name) throws Throwable {
        String text = xmlResponse.getElementsByTagName("name").item(0).getTextContent();
        assertThat(text, is(name));
    }
    
    @And("^the client receives person age (.+)$")
    public void the_client_receives_person_age(int age) throws Throwable {
        String ageText = "" + age;
        String text = xmlResponse.getElementsByTagName("age").item(0).getTextContent();
        text = text.replace("<","");
        text = text.replace(">","");
        System.out.println("Age from feature is " + ageText);
        System.out.println("Age from xml is " + text);
        assertThat(text, is(ageText));
    }
    
    @And("^the client receives person email (.+)$")
    public void the_client_receives_person_email(String email) throws Throwable {
        String text = xmlResponse.getElementsByTagName("email").item(0).getTextContent();
        assertThat(text, is(email));
    }
}
