package homin.chun.contactlist.application.service;

import homin.chun.contactlist.application.port.in.command.CreateContactsCommand;
import homin.chun.contactlist.application.port.out.ContactCommandPort;
import homin.chun.contactlist.domain.ContactModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class ContactCommandServiceIntegrationTest {
    @Autowired
    private ContactCommandService contactCommandService;

    @Autowired
    private ContactCommandPort contactCommandPort;

    @Test
    public void contactCommandServiceTest() {
        var contactList = new ArrayList<ContactModel>();
        contactList.add(
                new ContactModel(
                        "homin",
                        "010-1234-5678",
                        "homin@test.io",
                        "2023-10-01"
                        )
        );
        var command = new CreateContactsCommand(contactList);

        Assertions.assertDoesNotThrow(
                () -> contactCommandService.createContacts(command)
        );
    }
}