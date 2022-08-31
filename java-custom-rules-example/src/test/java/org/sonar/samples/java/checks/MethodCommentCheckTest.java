package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class MethodCommentCheckTest {
  @Test
  void verify(){
    CheckVerifier.newVerifier()
      .onFile("src/test/files/MethodCommentCheck.java")
      .withCheck(new CommentStandardCheck())
      .verifyIssues();
  }
}
