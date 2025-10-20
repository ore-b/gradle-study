package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

public class FileSizeDiffPluginTest {

    @Test
    void pluginRegistersATask() {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("org.example.filesizediff");

        // Verify the result
        assertNotNull(project.getTasks().findByName("fileSizeDiff"));
    }

}
