package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.dto.SimCardRequestDto;
import au.com.telstra.simcardactivator.dto.SimCardResponseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private String customerEmail;
    private String iccid;
    private ResponseEntity<SimCardResponseDto> activationResponse;

    @Given("a customer with email {string} and ICCID {string}")
    public void aCustomerWithEmailAndICCID(String email, String iccid) {
        this.customerEmail = email;
        this.iccid = iccid;
    }

    @When("I submit an activation request for the SIM card")
    public void iSubmitAnActivationRequestForTheSimCard() {
        SimCardRequestDto requestDto = new SimCardRequestDto(iccid, customerEmail);
        activationResponse = restTemplate.postForEntity(
                "http://localhost:8080/api/simcards",
                requestDto,
                SimCardResponseDto.class
        );
    }

    @Then("the SIM card should be successfully activated")
    public void theSimCardShouldBeSuccessfullyActivated() {
        Assert.assertEquals(HttpStatus.OK, activationResponse.getStatusCode());
        Assert.assertNotNull(activationResponse.getBody());
        Assert.assertTrue(activationResponse.getBody().isActive());
    }

    @Then("the SIM card activation should fail")
    public void theSimCardActivationShouldFail() {
        Assert.assertEquals(HttpStatus.OK, activationResponse.getStatusCode());
        Assert.assertNotNull(activationResponse.getBody());
        Assert.assertFalse(activationResponse.getBody().isActive());
    }

    @And("the database record with ID {int} should show the SIM card as active")
    public void theDatabaseRecordWithIDShouldShowTheSimCardAsActive(int id) {
        ResponseEntity<SimCardResponseDto> queryResponse = restTemplate.getForEntity(
                "http://localhost:8080/api/simcards?simCardId=" + id,
                SimCardResponseDto.class
        );
        
        Assert.assertEquals(HttpStatus.OK, queryResponse.getStatusCode());
        Assert.assertNotNull(queryResponse.getBody());
        Assert.assertEquals(iccid, queryResponse.getBody().getIccid());
        Assert.assertEquals(customerEmail, queryResponse.getBody().getCustomerEmail());
        Assert.assertTrue(queryResponse.getBody().isActive());
    }

    @And("the database record with ID {int} should show the SIM card as inactive")
    public void theDatabaseRecordWithIDShouldShowTheSimCardAsInactive(int id) {
        ResponseEntity<SimCardResponseDto> queryResponse = restTemplate.getForEntity(
                "http://localhost:8080/api/simcards?simCardId=" + id,
                SimCardResponseDto.class
        );
        
        Assert.assertEquals(HttpStatus.OK, queryResponse.getStatusCode());
        Assert.assertNotNull(queryResponse.getBody());
        Assert.assertEquals(iccid, queryResponse.getBody().getIccid());
        Assert.assertEquals(customerEmail, queryResponse.getBody().getCustomerEmail());
        Assert.assertFalse(queryResponse.getBody().isActive());
    }
}