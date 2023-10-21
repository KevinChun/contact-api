package homin.chun.contactlist.adapter.out.persistence.jpa.mapper;

import homin.chun.contactlist.adapter.out.persistence.jpa.entity.ContactEntity;
import homin.chun.contactlist.domain.ContactModel;

public class ContactEntityMapper {
    public static ContactEntity toEntity(ContactModel contactModel) {
        return new ContactEntity(
                contactModel.name(),
                contactModel.email(),
                contactModel.phoneNumber(),
                contactModel.joinedDate()
        );
    }

    public static ContactModel toModel(ContactEntity contactEntity) {
        return new ContactModel(
                contactEntity.name,
                contactEntity.email,
                contactEntity.phoneNumber,
                contactEntity.joinedDate
        );
    }
}
