package programer.sekop.utill.dataTransfer.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateContactRequest {
    @NotBlank
    private String firstname;
    
    private String lastname;
    
    @Email
    private String email;
    
    private String phone;
}
