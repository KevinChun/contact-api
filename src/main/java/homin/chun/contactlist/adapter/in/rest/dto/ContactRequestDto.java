package homin.chun.contactlist.adapter.in.rest.dto;

import homin.chun.contactlist.domain.ContactModel;

public record ContactRequestDto(String name, String email, String tel, String joined) {
    public ContactModel toModel() {
        return new ContactModel(name, email, tel, joined);
    }
}
