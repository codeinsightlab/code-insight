package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import java.util.ArrayList;
import java.util.List;
import lab.codeinsight.backend.scan.model.report.EntityInfo;
import lab.codeinsight.backend.scan.model.report.FieldInfo;
import org.springframework.stereotype.Service;

@Service
public class FieldScanner {

  public List<FieldInfo> scan(EntityInfo entityInfo) {
    return entityInfo.fields();
  }

  public List<FieldInfo> scan(ClassOrInterfaceDeclaration declaration) {
    List<FieldInfo> fields = new ArrayList<>();

    for (FieldDeclaration field : declaration.getFields()) {
      for (VariableDeclarator variable : field.getVariables()) {
        String fieldName = variable.getNameAsString();
        String columnName = resolveColumnName(field, fieldName);

        fields.add(
            new FieldInfo(
                fieldName,
                variable.getType().asString(),
                columnName,
                AnnotationSupport.annotationNames(field)));
      }
    }

    return fields;
  }

  private String resolveColumnName(FieldDeclaration field, String defaultName) {
    return AnnotationSupport.resolveAnnotationValue(field, "Column", "name")
        .or(() -> AnnotationSupport.resolveAnnotationValue(field, "JoinColumn", "name"))
        .orElse(defaultName);
  }
}
