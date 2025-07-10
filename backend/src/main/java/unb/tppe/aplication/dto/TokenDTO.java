package unb.tppe.aplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import jakarta.enterprise.context.ApplicationScoped;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class TokenDTO {
    String token;
}
