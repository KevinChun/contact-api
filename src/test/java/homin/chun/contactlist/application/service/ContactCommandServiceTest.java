package homin.chun.contactlist.application.service;

import homin.chun.contactlist.application.port.in.command.CreateContactsCommand;
import homin.chun.contactlist.application.port.out.ContactCommandPort;
import homin.chun.contactlist.application.port.out.ContactSearchPort;
import homin.chun.contactlist.domain.ContactModel;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContactCommandServiceTest {

    @InjectMocks
    private ContactCommandService contactCommandService;
    @Mock
    private ContactCommandPort contactCommandPort;
    @Mock
    private ContactSearchPort contactSearchPort;

    @Test
    public void contactCommandServiceTest() {
        ArrayList<ContactModel> contactList = getContactModels();
        var testCommand = new CreateContactsCommand(contactList);

        when(contactSearchPort.findContactByName("김철수"))
                .thenReturn(Optional.of(contactList.get(0)));
        when(contactSearchPort.findContactByName("박영희"))
                .thenReturn(Optional.of(contactList.get(1)));

        doNothing().when(contactCommandPort).createContacts(any(ArrayList.class));

        var result = contactCommandService.createContacts(testCommand);
        assertEquals(3, result.size());
        assertEquals("Already exists", result.get("김철수"));
        assertEquals("Already exists", result.get("박영희"));
        assertEquals("Created", result.get("홍길동"));
    }

    @NotNull
    private static ArrayList<ContactModel> getContactModels() {
        ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();
        contactList.add(
                new ContactModel(
                        "김철수", "charles@clovf.com", "010-1234-5678", "2020-01-05"
                )
        );
        contactList.add(
                new ContactModel(
                        "박영희", "matilda@clovf.com", "010-1234-5566",  "2021-04-28"
                )
        );
        contactList.add(
                new ContactModel(
                        "홍길동", "kildong.hong@clovf.com", "010-1234-4433",  "2015-08-15"
                )
        );
        return contactList;
    }
}