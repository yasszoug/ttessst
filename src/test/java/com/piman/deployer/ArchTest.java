package com.piman.deployer;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.piman.deployer");

        noClasses()
            .that()
            .resideInAnyPackage("com.piman.deployer.service..")
            .or()
            .resideInAnyPackage("com.piman.deployer.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.piman.deployer.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
