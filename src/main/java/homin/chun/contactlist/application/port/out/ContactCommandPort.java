package homin.chun.contactlist.application.port.out;

import homin.chun.contactlist.domain.ContactModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactCommandPort {
    @Transactional
    void createContacts(List<ContactModel> contactModels);
}
