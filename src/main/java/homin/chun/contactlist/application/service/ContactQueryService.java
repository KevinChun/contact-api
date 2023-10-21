package homin.chun.contactlist.application.service;

import homin.chun.contactlist.application.port.in.usecase.SearchContactUseCase;
import homin.chun.contactlist.application.port.out.ContactSearchPort;
import homin.chun.contactlist.domain.ContactModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactQueryService implements SearchContactUseCase {
    private final ContactSearchPort contactSearchPort;


    @Override
    public Page<ContactModel> searchAllContacts(Pageable pageable) {
        return contactSearchPort.searchAllContacts(pageable);
    }

    @Override
    public Optional<ContactModel> searchContactInfoByName(String name) {
        return contactSearchPort.findContactByName(name);
    }
}
