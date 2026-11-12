
package org.acme.catalog.role.application.dto.usecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateRoleRequest(

        @NotBlank
        @Size(max = 100)
        String name
) {
}
