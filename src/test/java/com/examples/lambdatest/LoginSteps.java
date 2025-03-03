/*
 * (C) Copyright 2020 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.examples.lambdatest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class LoginSteps {

    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        String browser = System.getProperty("browser", "chrome");
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", "");
        ltOptions.put("accessKey", "");
        ltOptions.put("build", "junit5-cucumber-debug");
        ltOptions.put("w3c", true);

        try {
            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setPlatformName("Windows 10");
                    chromeOptions.setBrowserVersion("122.0");
                    ltOptions.put("selenium_version", "4.0.0");
                    chromeOptions.setCapability("LT:Options", ltOptions);
                    System.out.println("Browser is " + browser);

                    ClientConfig config = ClientConfig.defaultConfig().connectionTimeout(Duration.ofMinutes(20))
                            .readTimeout(Duration.ofMinutes(20));
                    //WebDriver testDriver = RemoteWebDriver.builder().oneOf(chromeOptions).address("https://hub.lambdatest.com/wd/hub").config(config).build();
                    //driver = new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), chromeOptions);
                    driver = RemoteWebDriver.builder().oneOf(chromeOptions).address("https://hub.lambdatest.com/wd/hub").config(config).build();
                    break;

                case "safari":
                    SafariOptions safariOptions = new SafariOptions();
                    safariOptions.setPlatformName("macOS Sonoma");
                    safariOptions.setBrowserVersion("17.0");
                    safariOptions.setCapability("LT:Options", ltOptions);
                    System.out.println("Browser is " + browser);
                    driver = new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), safariOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setPlatformName("Windows 10");
                    firefoxOptions.setBrowserVersion("120.0");
                    firefoxOptions.setCapability("LT:Options", ltOptions);
                    System.out.println("Browser is " + browser);
                    driver = new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), firefoxOptions);
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driver.get(url);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    @And("I login with the username {string} and password {string}")
    public void iLogin(String username, String password) {

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

    }

    @And("I click Submit")
    public void iPressEnter() {
        driver.findElement(By.cssSelector("button")).click();
    }

    @Then("I should be see the message {string}")
    public void iShouldSee(String result) {
        try {
            driver.findElement(
                    By.xpath("//*[contains(text(), '" + result + "')]"));
        } catch (NoSuchElementException e) {
            throw new AssertionError(
                    "\"" + result + "\" not available in results");
        } finally {
            driver.quit();
        }
    }

}
