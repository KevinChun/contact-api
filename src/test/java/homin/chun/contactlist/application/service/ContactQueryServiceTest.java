package homin.chun.contactlist.application.service;

import homin.chun.contactlist.adapter.out.persistence.jpa.ContactJpaAdapter;
import homin.chun.contactlist.domain.ContactModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ContactQueryServiceTest {

    @InjectMocks
    private ContactQueryService contactQueryService;
    @Mock
    private ContactJpaAdapter contactJpaAdapter;

    @BeforeEach
    public void setUp() {
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

        when(contactJpaAdapter.searchAllContacts(any(Pageable.class)))
                .thenReturn(new PageImpl<ContactModel>(contactList));
        when(contactJpaAdapter.findContactByName("김철수"))
                .thenReturn(Optional.of(contactList.get(0)));
    }

    @Test
    public void test_searchAllContacts() {
        var searchResult = contactQueryService.searchAllContacts(Pageable.ofSize(3));
        assertTrue(searchResult.hasContent());
        assertEquals(3, searchResult.getTotalElements());
    }

    @Test
    public void test_searchContactInfoByName() {
        var findResult = contactQueryService.searchContactInfoByName("김철수");

        assertTrue(findResult.isPresent());
        assertEquals("김철수", findResult.get().name());
        assertEquals("010-1234-5678", findResult.get().phoneNumber());
        assertEquals("charles@clovf.com", findResult.get().email());
        assertEquals("2020-01-05", findResult.get().joinedDate());
    }
}