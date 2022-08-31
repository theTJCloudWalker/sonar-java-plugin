package org.sonar.samples.java.checks;

import com.google.common.collect.ImmutableList;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.MethodTree;

import java.util.Collections;
import java.util.List;

@Rule(key="CommentStandardCheck")
public class CommentStandardCheck extends IssuableSubscriptionVisitor {

  private static final String MSG_NO_COMMENT = "There is no comment under class";
  private static final String MSG_NO_AUTHOR = "There is no @author inside comment";

  private static final String MSG_LACK_PARAM = "There is missed param inside comment";

  private static final String MSG_NO_RETURN = "There is no @return inside comment";

  private Tree actualTree = null;

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.TRIVIA, Tree.Kind.CLASS,Tree.Kind.METHOD);
  }

  @Override
  public void visitTrivia(SyntaxTrivia syntaxTrivia) {
    String comment = syntaxTrivia.comment();
    if (syntaxTrivia.column() != 0)
      //reportIssue(actualTree, MSG_NO_COMMENT);
      return;
    if (comment == null) {
      reportIssue(actualTree, MSG_NO_COMMENT);
      return;
    }

    if (!comment.contains("@author")) {
      reportIssue(actualTree, MSG_NO_AUTHOR);

    }
    if(actualTree.is(Tree.Kind.METHOD)) {
      if (!comment.contains("@param")) {
        reportIssue(actualTree, MSG_LACK_PARAM);

      }
      if (!comment.contains("@return")) {
        reportIssue(actualTree, MSG_NO_RETURN);
      }
    }
  }



  @Override
  public void visitNode(Tree tree) {
    if (tree.is(Tree.Kind.CLASS)||tree.is(Tree.Kind.METHOD)) {
      actualTree = tree;
    }
  }

}
