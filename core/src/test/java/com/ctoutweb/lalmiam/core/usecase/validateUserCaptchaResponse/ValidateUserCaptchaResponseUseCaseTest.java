package com.ctoutweb.lalmiam.core.usecase.validateUserCaptchaResponse;

import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.validateUserCaptchaResponse.impl.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryOutputAdapter;
import com.ctoutweb.lalamiam.core.exception.ApplicationException;
import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.INotificationService;
import com.ctoutweb.lalamiam.core.useCase.impl.ValidateCaptchaResponseUseCase;

import static common.DataForTest.*;

/**
 * Case d'utilisation - captcha
 */
public class ValidateUserCaptchaResponseUseCaseTest {
  @Mock
  IMessageService messageService;

  @Mock
  ICryptographicService cryptographicService;

  @Mock
  INotificationService notificationService;

  ValidateCaptchaResponseUseCase captchaValidateClientResponseUseCase;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);

    captchaValidateClientResponseUseCase = new ValidateCaptchaResponseUseCase(
            messageService,
            cryptographicService,
            notificationService
    );

  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_throw_BadRequestException_when_user_captcha_response_wrong() {
    /**
     * given
     */

    Mockito.when(cryptographicService.isHashValid(Mockito.any())).thenReturn(false);

    String exceptionMessage = "mauvaise réponse captcha";
    Mockito.when(messageService.getMessage("captcha.invalid.response")).thenReturn(exceptionMessage);
    IBoundaryInputAdapter validateUserCaptchaResponseInformationAdapter = fakeUserCaptchaResponseAdapter();

    /**
     * when
     */
    ValidateCaptchaResponseUseCase.Input validateUserCaptchaResponseUseCaseInput = ValidateCaptchaResponseUseCase.Input.getUseCaseInput(validateUserCaptchaResponseInformationAdapter);
    Exception exception = Assertions.assertThrows(BadRequestException.class, ()->captchaValidateClientResponseUseCase.execute(validateUserCaptchaResponseUseCaseInput));
    Assertions.assertEquals(exceptionMessage, exception.getMessage());
  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_return_true_when_user_reponse_is_valid() {

    /**
     * given
     */

    Mockito.when(cryptographicService.isHashValid(Mockito.any())).thenReturn(true);

    IBoundaryInputAdapter boundaryInputAdapter = DataForTest.fakeUserCaptchaResponseAdapter();
    ValidateCaptchaResponseUseCase.Input input = ValidateCaptchaResponseUseCase.Input.getUseCaseInput(boundaryInputAdapter);

    /**
     * when
     */
    ValidateCaptchaResponseUseCase.Output output = captchaValidateClientResponseUseCase.execute(input);
    IBoundaryOutputAdapter boundaryOutput = output.getOutputBoundaryAdapter();

    /**
     * then
     */
    Assertions.assertTrue(boundaryOutput.getIsClientResponseValid());



  }


  @Test
  public void concatanate_user_response_with_captcha_token_should_return_aaaaabbbbccccffff() {
/**
 * given
 */
    IBoundaryInputAdapter validateUserCaptchaResponseAdapter = fakeUserCaptchaResponseAdapter();
    BoundaryInputImpl validateUserCaptchaResponse = BoundaryInputImpl.getBoundaryInputImpl(
            validateUserCaptchaResponseAdapter.getCaptchaResponseByUser(),
            validateUserCaptchaResponseAdapter.getHashOrDecrypteCaptchaResponse(),
            validateUserCaptchaResponseAdapter.getCryptographicType(),
            validateUserCaptchaResponseAdapter.getCaptchaToken(),
            validateUserCaptchaResponseAdapter.getCaptchaTokenSeparator()
    );

    /**
     * when
     */

    String actualResponse = captchaValidateClientResponseUseCase.AddCaptchaConfToClientResponse(validateUserCaptchaResponseAdapter.getCaptchaResponseByUser(), validateUserCaptchaResponseAdapter.getCaptchaToken(), validateUserCaptchaResponseAdapter.getCaptchaTokenSeparator());
    String expectedResponse = validateUserCaptchaResponseAdapter.getCaptchaToken() + validateUserCaptchaResponseAdapter.getCaptchaTokenSeparator() + validateUserCaptchaResponse.getCaptchaResponseByUser() ;
    /**
     * then
     */
    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_throw_ApplicationException_when_CaptchaConfigurationToken_not_present() {
    /**
     * given
     */
    String exceptionMessage = "Données manquantes pour résoudre le captcha";

    Mockito.when(messageService.getMessage("captcha.data.error")).thenReturn(exceptionMessage);
    ValidateCaptchaResponseUseCase.Input input = new ValidateCaptchaResponseUseCase.Input(fakeUserCaptchaResponseAdapterWithoutToken());

    /**
     * When
     */
    Exception exception = Assertions.assertThrows(ApplicationException.class, ()-> captchaValidateClientResponseUseCase.execute(input));

    /**
     * Then
     */
    Assertions.assertEquals(exceptionMessage, exception.getMessage());


  }

  @Test
  public void ValidateUserCaptchaResponseUseCase_should_throw_ApplicationException_when_CaptchaConfigurationStringSeparator_not_present() {
    /**
     * given
     */
    String exceptionMessage = "Données manquantes pour résoudre le captcha";

    Mockito.when(messageService.getMessage("captcha.data.error")).thenReturn(exceptionMessage);
    ValidateCaptchaResponseUseCase.Input input = new ValidateCaptchaResponseUseCase.Input(fakeUserCaptchaResponseAdapterWithoutToken());

    /**
     * When
     */
    Exception exception = Assertions.assertThrows(ApplicationException.class, ()-> captchaValidateClientResponseUseCase.execute(input));

    /**
     * Then
     */
    Assertions.assertEquals(exceptionMessage, exception.getMessage());


  }
}
