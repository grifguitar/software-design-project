package itmo.practice.repository;

import itmo.practice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    int countByName(Role.Name name);
}
