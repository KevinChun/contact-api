package homin.chun.contactlist.adapter.in.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import homin.chun.contactlist.adapter.in.rest.InputValidator;
import homin.chun.contactlist.exception.ContactNotFoundException;
import homin.chun.contactlist.exception.InvalidParameterException;
import homin.chun.contactlist.exception.NotSupportedException;
import homin.chun.contactlist.adapter.in.rest.dto.ContactRequestDto;
import homin.chun.contactlist.adapter.in.rest.dto.ContactResponseDto;
import homin.chun.contactlist.application.port.in.command.CreateContactsCommand;
import homin.chun.contactlist.application.port.in.usecase.CreateContactsUserCase;
import homin.chun.contactlist.application.port.in.usecase.SearchContactUseCase;
import homin.chun.contactlist.domain.ContactModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactListController {
    private final SearchContactUseCase searchContactUseCase;
    private final CreateContactsUserCase createContactsUserCase;
    private final ObjectMapper objectMapper;

    @GetMapping("/employee")
    public Page<ContactResponseDto> getContactList(
            @RequestParam(required = false, name = "page") int page,
            @RequestParam(required = false, name = "pageSize", defaultValue = "20") int size
    ) {
        return searchContactUseCase
                .searchAllContacts(Pageable.ofSize(size).withPage(page))
                .map(model -> {
                    return new ContactResponseDto(
                            model.name(),
                            model.email(),
                            model.phoneNumber(),
                            model.joinedDate()
                    );
                });

    }

    @GetMapping("/employee/{name}")
    public ContactResponseDto getContactInfo(
            @PathVariable String name) {
        return searchContactUseCase.searchContactInfoByName(name)
                .map(model -> {
                    return new ContactResponseDto(
                            model.name(),
                            model.email(),
                            model.phoneNumber(),
                            model.joinedDate()
                    );
                })
                .orElseThrow(() -> new ContactNotFoundException("Contact Found by name: " + name ));
    }

    @PostMapping("/employee")
    public Map<String, String> addContactInfo(
        @RequestParam(required = false) MultipartFile file,
        @RequestBody(required = false) String contactRequests
    ) throws IOException {
        if (file != null) {
            return createContactsUserCase.createContacts(
                    getCommandFromUploadedFile(file)
            );
        } else if (contactRequests != null) {
            return createContactsUserCase.createContacts(
                    buildCommandFromInputString(contactRequests)
            );
        } else {
            throw new InvalidParameterException("Either file or request body should be provided");
        }
    }

    private CreateContactsCommand getCommandFromUploadedFile(MultipartFile file) throws IOException {
        if (Objects.equals(file.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            var contacts = objectMapper.readValue(
                    file.getInputStream(), new TypeReference<List<ContactRequestDto>>() {});
            var contactModels = contacts.stream().map(ContactRequestDto::toModel).toList();
            return new CreateContactsCommand(contactModels);
        } else if (Objects.equals(file.getContentType(), "text/csv")) {
            List<ContactModel> contacts = getContactModels(file);
            return new CreateContactsCommand(contacts);
        }
        throw new NotSupportedException("Not supported file type");
    }

    private static List<ContactModel> getContactModels(MultipartFile file) throws IOException {
        List<ContactModel> contacts = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                var contact = line.split(",");
                var contactRequest = new ContactRequestDto(
                        contact[0].trim(), contact[1].trim(), contact[2].trim(), contact[3].trim()
                );
                contacts.add(contactRequest.toModel());
            }
        }
        return contacts;
    }

    private CreateContactsCommand buildCommandFromInputString(String contactRequests) throws JsonProcessingException {
        if(new InputValidator(objectMapper).isValidJson(contactRequests)) {
            var contacts = objectMapper.readValue(
                    contactRequests, new TypeReference<List<ContactRequestDto>>() {});
            var contactModels = contacts.stream().map(ContactRequestDto::toModel).toList();
            return new CreateContactsCommand(contactModels);
        } else {
            var contactModels = Arrays.stream(contactRequests.split("\n")).map(line -> {
                var contact = line.split(",");
                return new ContactRequestDto(
                        contact[0].trim(), contact[1].trim(), contact[2].trim(), contact[3].trim()
                );
            })
                    .map(ContactRequestDto::toModel).toList();
            return new CreateContactsCommand(contactModels);
        }
    }
}
