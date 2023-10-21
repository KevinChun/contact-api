package homin.chun.contactlist.application.port.in.usecase;

import homin.chun.contactlist.application.port.in.command.CreateContactsCommand;
import homin.chun.contactlist.common.domainannotation.UseCase;

import java.util.Map;

@UseCase
public interface CreateContactsUserCase {
    Map<String, String> createContacts(CreateContactsCommand command);
}
