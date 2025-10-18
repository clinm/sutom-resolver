package com.mclin.sutom.shared.result.domain;

import com.mclin.sutom.shared.error.domain.Assert;
import java.util.List;
import java.util.Optional;

public final class Result<T, E> {

  private final T value;
  private final List<E> errors;

  private Result(T value, List<E> errors) {
    this.value = value;
    this.errors = errors;
  }

  public static <T, E> Result<T, E> success(T value) {
    Assert.notNull("value", value);
    return new Result<>(value, null);
  }

  public static <T, E> Result<T, E> failure(List<E> errors) {
    Assert.notEmpty("errors", errors);
    return new Result<>(null, errors);
  }

  public boolean isSuccess() {
    return errors == null;
  }

  public boolean isFailure() {
    return !isSuccess();
  }

  public Optional<T> getValue() {
    return Optional.ofNullable(value);
  }

  public Optional<List<E>> getErrors() {
    return Optional.ofNullable(errors);
  }
}
