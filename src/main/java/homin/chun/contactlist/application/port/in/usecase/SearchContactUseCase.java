package homin.chun.contactlist.application.port.in.usecase;

import homin.chun.contactlist.common.domainannotation.UseCase;
import homin.chun.contactlist.domain.ContactModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@UseCase
public interface SearchContactUseCase {
    Page<ContactModel> searchAllContacts(Pageable pageable);

    Optional<ContactModel> searchContactInfoByName(String name);
}
