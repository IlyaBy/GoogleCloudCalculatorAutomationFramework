package tests;

import driver.BaseTest;
import model.ComputeEngineInstance;
import model.InstanceCreator;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CalculatorPricingPage;
import pages.DetailedViewPage;
import pages.YopMailRecipientPage;
import pages.YopMailSenderPage;


public class CalculatorTest extends BaseTest {


   @Test (groups={"smoke", "regression"})
   public void costComparingTest() {
       ComputeEngineInstance testInstance = InstanceCreator.createFromProperties(properties);
       CalculatorPricingPage calcPage = new CalculatorPricingPage(driver);

       calcPage.createCalculatorPricingPage()
               .setNumberOfInstances(testInstance.getNumberOfInstances())
               .selectOperatingSystem(testInstance.getOperatingSystem())
               .selectProvisioningModel(testInstance.getProvisioningModel())
               .selectMachineFamily(testInstance.getMachineFamily())
               .selectSeries(testInstance.getSeries())
               .selectMachineType(testInstance.getMachineType())
               .selectGPUType(testInstance.getGpuType())
               .selectGPUNumber(testInstance.getGpuNumber())
               .selectLocalSSD(testInstance.getLocalSSD())
               .selectRegion(testInstance.getRegion())
               .selectDiscountOptions(testInstance.getDiscountOptions());

       String calculatorEstimatedCost=calcPage.getEstimatedCost();
       calcPage.openDetailedView();

       DetailedViewPage detailedViewPage = calcPage.switchToDetailedViewPage(2);
       String detailedViewEstimatedCost=detailedViewPage.getDetailedViewCost();
       Assert.assertEquals(calculatorEstimatedCost, detailedViewEstimatedCost, "Costs do not match between calculator and detailed view");
   }

    @Test (groups = {"regression"})
    public void costSendByEmailTest() {
        ComputeEngineInstance testInstance = InstanceCreator.createFromProperties(properties);
        CalculatorPricingPage calcPage = new CalculatorPricingPage(driver);

        calcPage.createCalculatorPricingPage()
                .setNumberOfInstances(testInstance.getNumberOfInstances())
                .selectOperatingSystem(testInstance.getOperatingSystem())
                .selectProvisioningModel(testInstance.getProvisioningModel())
                .selectMachineFamily(testInstance.getMachineFamily())
                .selectSeries(testInstance.getSeries())
                .selectMachineType(testInstance.getMachineType())
                .selectGPUType(testInstance.getGpuType())
                .selectGPUNumber(testInstance.getGpuNumber())
                .selectLocalSSD(testInstance.getLocalSSD())
                .selectRegion(testInstance.getRegion())
                .selectDiscountOptions(testInstance.getDiscountOptions());
        String calculatorEstimatedCost=calcPage.getEstimatedCost();

        YopMailSenderPage mailSenderPage = calcPage.openYopMaiSenderTab();

        mailSenderPage.generateEmailToSend()
                      .fillInEmailSubjectField("Total estimated cost")
                      .fillInEmailBodyField(calculatorEstimatedCost);

        YopMailRecipientPage mailRecipientPage =  mailSenderPage.openYopMaiRecipientTab();

        mailRecipientPage.generateEmailToCopy();

        String recipientPageMail= mailRecipientPage.copyEmail();

        mailRecipientPage.returnToYopMailSenderPage()
                         .sendNewEmail()
                         .fillInEmailRecipientField(recipientPageMail)
                         .sendEmail();

        Assert.assertEquals(mailSenderPage.getTextFromEmailSendConfirmation(), "Your message has been sent", "Message was not sent successfully");
    }
}
