package homin.chun.contactlist.application.port.in.command;

import homin.chun.contactlist.domain.ContactModel;

import java.util.List;

public record CreateContactsCommand(List<ContactModel> contacts) {
}
