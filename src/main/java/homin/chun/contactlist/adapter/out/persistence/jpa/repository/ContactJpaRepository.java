package homin.chun.contactlist.adapter.out.persistence.jpa.repository;

import homin.chun.contactlist.adapter.out.persistence.jpa.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactJpaRepository extends JpaRepository<ContactEntity, Long> {
    Optional<ContactEntity> findByName(String name);
}
