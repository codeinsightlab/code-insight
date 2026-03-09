package lab.codeinsight.backend.scan.main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.util.ArrayList;
import java.util.List;
import lab.codeinsight.backend.scan.model.report.EntityInfo;
import lab.codeinsight.backend.scan.model.report.FieldInfo;
import org.springframework.stereotype.Service;

/**
 * Scans parsed classes and extracts JPA entity metadata.
 */
@Service
public class EntityScanner {

	private final FieldScanner fieldScanner;

	/**
	 * Creates entity scanner with field scanner dependency.
	 */
	public EntityScanner(FieldScanner fieldScanner) {
		this.fieldScanner = fieldScanner;
	}

	/**
	 * Returns entity info list from compilation units.
	 */
	public List<EntityInfo> scan(List<JavaSourceUnit> sourceUnits) {
		List<EntityInfo> entities = new ArrayList<>();

		for (JavaSourceUnit sourceUnit : sourceUnits) {
			for (ClassOrInterfaceDeclaration declaration : sourceUnit.compilationUnit()
					.findAll(ClassOrInterfaceDeclaration.class)) {
				if (!AnnotationSupport.hasAnnotation(declaration, "Entity")) {
					continue;
				}

				String tableName = AnnotationSupport.resolveTableName(declaration)
						.orElse(declaration.getNameAsString());
				List<FieldInfo> fields = fieldScanner.scan(declaration);

				entities.add(new EntityInfo(declaration.getNameAsString(), tableName, fields));
			}
		}

		return entities;
	}
}
