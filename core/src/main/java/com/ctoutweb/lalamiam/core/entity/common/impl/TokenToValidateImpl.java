package com.ctoutweb.lalamiam.core.entity.common.impl;

import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;

public record TokenToValidateImpl(String plainTextToken, String cryptographicToken) implements ITokenToValidate {
  public static ITokenToValidate getTokenToValidateImpl(String plainTextToken, String cryptographicToken) {
    return new TokenToValidateImpl(plainTextToken, cryptographicToken);
  }

  @Override
  public String getCryptographicToken() {
    return cryptographicToken;
  }

  @Override
  public String getPlainTextToken() {
    return plainTextToken;
  }
}
