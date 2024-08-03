package programer.sekop.utill.dataTransfer.contact;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateContactRequest {
    @NotBlank
    private String id;
    
    @NotBlank
    private String firstname;
    
    private String lastname;
    
    @Email
    private String email;
    
    private String phone;
}
