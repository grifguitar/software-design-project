package itmo.practice.initializer;

import itmo.practice.domain.Role;
import itmo.practice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BaseDbInitializer {
    private final RoleRepository roleRepository;

    public void initialize() {
        initializeRoles();
    }

    private void initializeRoles() {
        for (Role.Name name : Role.Name.values()) {
            if (roleRepository.countByName(name) == 0) {
                roleRepository.save(new Role(name));
            }
        }
    }
}
