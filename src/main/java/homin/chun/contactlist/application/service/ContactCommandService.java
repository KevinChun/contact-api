package homin.chun.contactlist.application.service;

import homin.chun.contactlist.application.port.in.command.CreateContactsCommand;
import homin.chun.contactlist.application.port.in.usecase.CreateContactsUserCase;
import homin.chun.contactlist.application.port.out.ContactCommandPort;
import homin.chun.contactlist.application.port.out.ContactSearchPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContactCommandService implements CreateContactsUserCase {
    private final ContactCommandPort contactCommandPort;
    private final ContactSearchPort contactSearchPort;

    @Override
    public Map<String, String> createContacts(CreateContactsCommand command) {
        var requestedContacts = command.contacts();
        var existsContacts = requestedContacts.stream().filter(contact -> {
            return contactSearchPort.findContactByName(contact.name()).isPresent();
        }).toList();

        // requestedContacts is an immutable list, so we need to create a new list.
        var targetContacts = new java.util.ArrayList<>(Collections.unmodifiableList(requestedContacts));
        targetContacts.removeAll(existsContacts);

        if(!targetContacts.isEmpty()) {
            contactCommandPort.createContacts(targetContacts);
        }

        var resultMap = new HashMap<String, String>();
        existsContacts.forEach(contact -> {
            resultMap.put(contact.name(), "Already exists");
        });
        targetContacts.forEach(contact -> {
            resultMap.put(contact.name(), "Created");
        });

        return resultMap;
    }
}
