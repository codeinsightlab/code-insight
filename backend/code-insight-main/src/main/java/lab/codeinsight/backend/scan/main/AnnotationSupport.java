package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

final class AnnotationSupport {

  private AnnotationSupport() {}

  static boolean hasAnnotation(NodeWithAnnotations<?> node, String simpleName) {
    return node.getAnnotations().stream()
        .map(annotation -> annotation.getName().getIdentifier())
        .anyMatch(simpleName::equals);
  }

  static List<String> annotationNames(NodeWithAnnotations<?> node) {
    return node.getAnnotations().stream()
        .map(annotation -> "@" + annotation.getName().getIdentifier())
        .toList();
  }

  static Optional<String> resolveRequestPath(NodeWithAnnotations<?> node) {
    for (AnnotationExpr annotation : node.getAnnotations()) {
      String name = annotation.getName().getIdentifier();
      if (!isMappingAnnotation(name)) {
        continue;
      }

      Optional<String> byValue = resolveAnnotationString(annotation, "value");
      if (byValue.isPresent()) {
        return byValue;
      }

      Optional<String> byPath = resolveAnnotationString(annotation, "path");
      if (byPath.isPresent()) {
        return byPath;
      }
    }
    return Optional.empty();
  }

  static Optional<String> resolveHttpMethod(NodeWithAnnotations<?> node) {
    for (AnnotationExpr annotation : node.getAnnotations()) {
      String name = annotation.getName().getIdentifier();
      if (name.endsWith("Mapping") && !"RequestMapping".equals(name)) {
        return Optional.of(name.replace("Mapping", "").toUpperCase(Locale.ROOT));
      }

      if ("RequestMapping".equals(name)) {
        Optional<String> method = resolveAnnotationString(annotation, "method");
        if (method.isPresent()) {
          return Optional.of(method.get().replace("RequestMethod.", "").toUpperCase(Locale.ROOT));
        }
        return Optional.of("REQUEST");
      }
    }
    return Optional.empty();
  }

  static Optional<String> resolveTableName(NodeWithAnnotations<?> node) {
    for (AnnotationExpr annotation : node.getAnnotations()) {
      if (!"Table".equals(annotation.getName().getIdentifier())) {
        continue;
      }
      Optional<String> name = resolveAnnotationString(annotation, "name");
      if (name.isPresent()) {
        return name;
      }
    }
    return Optional.empty();
  }

  static Optional<String> resolveAnnotationValue(
      NodeWithAnnotations<?> node, String annotationSimpleName, String key) {
    for (AnnotationExpr annotation : node.getAnnotations()) {
      if (!annotationSimpleName.equals(annotation.getName().getIdentifier())) {
        continue;
      }
      Optional<String> value = resolveAnnotationString(annotation, key);
      if (value.isPresent()) {
        return value;
      }
    }
    return Optional.empty();
  }

  private static boolean isMappingAnnotation(String simpleName) {
    return "RequestMapping".equals(simpleName)
        || "GetMapping".equals(simpleName)
        || "PostMapping".equals(simpleName)
        || "PutMapping".equals(simpleName)
        || "DeleteMapping".equals(simpleName)
        || "PatchMapping".equals(simpleName);
  }

  private static Optional<String> resolveAnnotationString(AnnotationExpr annotation, String key) {
    if (annotation instanceof SingleMemberAnnotationExpr singleMemberAnnotationExpr) {
      if ("value".equals(key)) {
        return Optional.of(expressionToString(singleMemberAnnotationExpr.getMemberValue()));
      }
      return Optional.empty();
    }

    if (annotation instanceof NormalAnnotationExpr normalAnnotationExpr) {
      for (MemberValuePair pair : normalAnnotationExpr.getPairs()) {
        if (key.equals(pair.getNameAsString())) {
          return Optional.of(expressionToString(pair.getValue()));
        }
      }
    }

    return Optional.empty();
  }

  private static String expressionToString(Expression expression) {
    if (expression.isStringLiteralExpr()) {
      return expression.asStringLiteralExpr().asString();
    }

    if (expression instanceof FieldAccessExpr fieldAccessExpr) {
      return fieldAccessExpr.toString();
    }

    if (expression instanceof NameExpr nameExpr) {
      return nameExpr.getNameAsString();
    }

    if (expression instanceof MethodCallExpr methodCallExpr) {
      return methodCallExpr.toString();
    }

    if (expression instanceof ArrayInitializerExpr arrayInitializerExpr) {
      List<String> values = new ArrayList<>();
      for (Expression value : arrayInitializerExpr.getValues()) {
        values.add(expressionToString(value));
      }
      return String.join(",", values);
    }

    return expression.toString().replace("\"", "");
  }
}
