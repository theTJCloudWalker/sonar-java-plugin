package org.sonar.samples.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.model.SyntacticEquivalence;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

@Rule(key="FloatUsageCheck")
public class FloatUsageCheck extends IssuableSubscriptionVisitor{

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.EQUAL_TO, Tree.Kind.NOT_EQUAL_TO, Tree.Kind.CONDITIONAL_AND, Tree.Kind.CONDITIONAL_OR);
  }

  @Override
  public void visitNode(Tree tree) {
    BinaryExpressionTree binaryExpressionTree = (BinaryExpressionTree) tree;
    if (binaryExpressionTree.is(Tree.Kind.CONDITIONAL_AND, Tree.Kind.CONDITIONAL_OR) && isIndirectEquality(binaryExpressionTree)) {
      binaryExpressionTree = (BinaryExpressionTree) binaryExpressionTree.leftOperand();
    }
    if ((hasFloatingType(binaryExpressionTree.leftOperand()) || hasFloatingType(binaryExpressionTree.rightOperand())) && !isNanTest(binaryExpressionTree)) {
      reportIssue(binaryExpressionTree.operatorToken(), "Equality tests should not be made with floating point values.");
    }
  }

  private static boolean isIndirectEquality(BinaryExpressionTree binaryExpressionTree) {
    return isIndirectEquality(binaryExpressionTree, Tree.Kind.CONDITIONAL_AND, Tree.Kind.GREATER_THAN_OR_EQUAL_TO, Tree.Kind.LESS_THAN_OR_EQUAL_TO)
      || isIndirectEquality(binaryExpressionTree, Tree.Kind.CONDITIONAL_OR, Tree.Kind.GREATER_THAN, Tree.Kind.LESS_THAN);
  }

  private static boolean isIndirectEquality(BinaryExpressionTree binaryExpressionTree, Tree.Kind indirectOperator, Tree.Kind comparator1, Tree.Kind comparator2) {
    if (binaryExpressionTree.is(indirectOperator) && binaryExpressionTree.leftOperand().is(comparator1, comparator2)) {
      BinaryExpressionTree leftOp = (BinaryExpressionTree) binaryExpressionTree.leftOperand();
      if (binaryExpressionTree.rightOperand().is(comparator1, comparator2)) {
        BinaryExpressionTree rightOp = (BinaryExpressionTree) binaryExpressionTree.rightOperand();
        if (leftOp.kind().equals(rightOp.kind())) {
          //same operator
          return SyntacticEquivalence.areEquivalent(leftOp.leftOperand(), rightOp.rightOperand())
            && SyntacticEquivalence.areEquivalent(leftOp.rightOperand(), rightOp.leftOperand());
        } else {
          //different operator
          return SyntacticEquivalence.areEquivalent(leftOp.leftOperand(), rightOp.leftOperand())
            && SyntacticEquivalence.areEquivalent(leftOp.rightOperand(), rightOp.rightOperand());
        }
      }
    }
    return false;
  }

  private static boolean isNanTest(BinaryExpressionTree binaryExpressionTree) {
    return SyntacticEquivalence.areEquivalent(binaryExpressionTree.leftOperand(), binaryExpressionTree.rightOperand());
  }

  private static boolean hasFloatingType(ExpressionTree expressionTree) {
    return expressionTree.symbolType().isPrimitive(Type.Primitives.FLOAT) || expressionTree.symbolType().isPrimitive(Type.Primitives.DOUBLE);
  }
}
