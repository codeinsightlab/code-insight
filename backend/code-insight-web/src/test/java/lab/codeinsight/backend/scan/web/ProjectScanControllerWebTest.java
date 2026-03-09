package lab.codeinsight.backend.scan.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lab.codeinsight.backend.scan.main.ProjectScanner;
import lab.codeinsight.backend.scan.main.ProjectStructureScanner;
import lab.codeinsight.backend.scan.model.report.ProjectReport;
import lab.codeinsight.backend.web.common.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Web-layer test for scan controller API contract.
 */
@ExtendWith(MockitoExtension.class)
class ProjectScanControllerWebTest {

	private MockMvc mockMvc;

	@Mock
	private ProjectScanner projectScanner;

	@Mock
	private ProjectStructureScanner projectStructureScanner;

	/**
	 * Builds standalone MockMvc environment for controller testing.
	 */
	@BeforeEach
	void setUp() {
		ProjectScanController controller = new ProjectScanController(projectScanner, projectStructureScanner);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	/**
	 * Verifies `/api/scan` returns wrapped report payload.
	 */
	@Test
	void shouldReturnProjectReportWhenScanApiCalled() throws Exception {
		given(projectScanner.scan("/tmp/demo")).willReturn(new ProjectReport(2, 5, 1, 12));

		mockMvc.perform(post("/api/scan").contentType(MediaType.APPLICATION_JSON).content("""
				{
				  "projectPath": "/tmp/demo"
				}
				""")).andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("OK")).andExpect(jsonPath("$.data.controllerCount").value(2))
				.andExpect(jsonPath("$.data.endpointCount").value(5)).andExpect(jsonPath("$.data.entityCount").value(1))
				.andExpect(jsonPath("$.data.javaFileCount").value(12));
	}
}
