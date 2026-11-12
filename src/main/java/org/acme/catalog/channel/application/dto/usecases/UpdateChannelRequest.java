
package org.acme.catalog.channel.application.dto.usecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateChannelRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @NotBlank
        @Pattern(regexp = "TEXT|VOICE")
        String type
) {
}
