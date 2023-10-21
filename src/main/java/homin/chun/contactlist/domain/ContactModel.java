package homin.chun.contactlist.domain;

public record ContactModel(
        String name,
        String email,
        String phoneNumber,
        String joinedDate
) {
}
