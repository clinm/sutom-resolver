package com.mclin.sutom.shared.result.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mclin.sutom.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class ResultTest {

  @Test
  void successResult() {
    String successValue = "My success value";
    Result<String, String> result = Result.success(successValue);

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.isFailure()).isFalse();
    assertThat(result.getValue()).contains(successValue);
    assertThat(result.getErrors()).isEmpty();
  }

  @Test
  void failureResult() {
    List<String> failureValue = List.of("My failure value");
    Result<String, String> result = Result.failure(failureValue);

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.isFailure()).isTrue();
    assertThat(result.getValue()).isEmpty();
    assertThat(result.getErrors()).contains(failureValue);
  }
}
