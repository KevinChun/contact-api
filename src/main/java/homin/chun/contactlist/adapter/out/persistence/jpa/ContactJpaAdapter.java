package homin.chun.contactlist.adapter.out.persistence.jpa;

import homin.chun.contactlist.adapter.out.persistence.jpa.mapper.ContactEntityMapper;
import homin.chun.contactlist.adapter.out.persistence.jpa.repository.ContactJpaRepository;
import homin.chun.contactlist.application.port.out.ContactCommandPort;
import homin.chun.contactlist.application.port.out.ContactSearchPort;
import homin.chun.contactlist.common.domainannotation.PersistenceAdapter;
import homin.chun.contactlist.domain.ContactModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class ContactJpaAdapter implements ContactCommandPort, ContactSearchPort {
    private final ContactJpaRepository contactJpaRepository;

    @Override
    public void createContacts(List<ContactModel> contactModels) {
        var entities = contactModels
                .stream()
                .map(ContactEntityMapper::toEntity)
                .collect(Collectors.toList());

        contactJpaRepository.saveAll(entities);
    }

    @Override
    public Page<ContactModel> searchAllContacts(Pageable pageable) {
        var pageableResult = contactJpaRepository.findAll(pageable)
                .stream()
                .map(ContactEntityMapper::toModel)
                .toList();
        return new PageImpl<ContactModel>(pageableResult, pageable, pageableResult.size());
    }

    @Override
    public Optional<ContactModel> findContactByName(String name) {
        return contactJpaRepository.findByName(name)
                .map(ContactEntityMapper::toModel);
    }
}
