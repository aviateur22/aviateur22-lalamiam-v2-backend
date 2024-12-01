package com.ctoutweb.lalamiam.infra.model.captcha;

import com.ctoutweb.lalamiam.infra.model.image.IBase64Image;

public interface ICaptcha {
  /**
   * Titre principal du captcha
   *
   * ex: Calculer le bon résultat
   * ex: Recopier le mot
   * ex: Quelle est cette image
   * ....
   * @return String
   */
  public String getCaptchaTitle();


  /**
   * Id de la réponse en base
   * @return Srting
   */
  Long getResponseId();

  /**
   * Image au format base64 de la question du captcha
   *
   * ex: pour un captcha type calcul:
   * La question 05 + 02 qui sera transformée en image
   *
   * ex: pour un captcha type text
   * La question KjhdLL qui sera transformée en image
   *
   * ex: pour un captcha type Image
   * La question: path/image/a/charger
   *
   * @return IBase64Image
   */
  IBase64Image getCaptchaQuestionBase64();
}
