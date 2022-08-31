package org.sonar.samples.java.checks;

import org.junit.jupiter.api.Test;

import org.sonar.java.checks.verifier.CheckVerifier;

public class AvoidFloatZeroComparisonRuleTest {
  @Test
  void detected(){
    CheckVerifier.newVerifier()
      .onFile("src/test/files/AvoidFloatZeroComparisonRule.java")
      .withCheck(new AvoidFloatZeroComparisonRule())
      .verifyIssues();
    //JavaCheckVerifier.verify("src/test/files/AvoidFloatZeroComparisonRule.java",new AvoidFloatZeroComparisonRule());
  }
}
