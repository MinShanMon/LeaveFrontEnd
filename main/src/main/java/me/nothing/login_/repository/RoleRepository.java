package me.nothing.login_.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import me.nothing.login_.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
}
