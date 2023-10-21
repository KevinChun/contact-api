package homin.chun.contactlist.application.port.out;

import homin.chun.contactlist.domain.ContactModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ContactSearchPort {
    Page<ContactModel> searchAllContacts(Pageable pageable);

    Optional<ContactModel> findContactByName(String name);
}
