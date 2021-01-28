package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @Mock(lenient = true)
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Captor
    private ArgumentCaptor<String> argumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(argumentCaptor.capture())).willAnswer(invocation -> {
            List<Owner> ownerList = new ArrayList<>();
            String name = invocation.getArgument(0);
            if(name.equals("%R%")) {
                ownerList.add(new Owner(1L,"Ashish","R"));
                return ownerList;
            } else if(name.equals("%Mika%")) {
                return ownerList;
            } else if(name.equals("%Dukati%")) {
                ownerList.add(new Owner(2L,"Ashish","Yo"));
                ownerList.add(new Owner(3L,"Ashish","Go"));
                return ownerList;
            }

            throw new RuntimeException("Invalid argument");
        });
    }

    /*@Test
    void processFindFormWildCardString() {
        // given
        Owner owner = new Owner(1L,"Ashish","R");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);
        // when
        String viewName = ownerController.processFindForm(owner,bindingResult,model);
        // then
        assertThat("%R%").isEqualToIgnoringCase(captor.getValue());
    }*/

    @Test
    void processFindFormWildCardStringAnnotation() {
        // given
        Owner owner = new Owner(1L,"Ashish","R");
        // when
        String viewName = ownerController.processFindForm(owner,bindingResult,model);
        // then
        assertThat("%R%").isEqualToIgnoringCase(argumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
    }

    @Test
    void processFindFormWildCardNotFound() {
        // given
        Owner owner = new Owner(1L,"Ashish","Mika");
        // when
        String viewName = ownerController.processFindForm(owner,bindingResult,model);
        // then
        assertThat("%Mika%").isEqualToIgnoringCase(argumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
    }

    @Test
    void processFindFormWildCardFindMe() {
        // given
        Owner owner = new Owner(1L,"Ashish","Dukati");
        InOrder inOrder = inOrder(ownerService,model);
        // when
        String viewName = ownerController.processFindForm(owner,bindingResult,model);
        // then
        assertThat("%Dukati%").isEqualToIgnoringCase(argumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

        // inorder asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model).addAttribute(anyString(),anyList());
    }

    @Test
    void processCreationFormHasErrors() {
        // given
        Owner owner = new Owner(1L,"Ashish","R");
        given(bindingResult.hasErrors()).willReturn(true);
        // when
        String viewName = ownerController.processCreationForm(owner,bindingResult);
        // then
        assertThat(viewName.equalsIgnoreCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM));
    }

    @Test
    void processCreationFormHasNoErrors() {
        // given
        Owner owner = new Owner(5L,"Ashish","R");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);
        // when
        String viewName = ownerController.processCreationForm(owner,bindingResult);
        // then
        assertThat(viewName).isEqualToIgnoringCase(REDIRECT_OWNERS_5);
    }
}