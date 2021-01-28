package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitSDJpaService visitSDJpaService;

    @Test
    void findAll() {
        // given
        Visit visit1 = new Visit();
        Visit visit2 = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit1);
        visits.add(visit2);
        // when call to findall made, return visits set
        given(visitRepository.findAll()).willReturn(visits);
        // when
        Set<Visit> foundVisits = visitSDJpaService.findAll();
        // then
        then(visitRepository).should().findAll();
        assertThat(visitSDJpaService.findAll()).isNotNull();
        assertThat(visitSDJpaService.findAll()).hasSize(2);
    }

    @Test
    void findById() {
        // given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        // when
        visitSDJpaService.findById(1L);
        // then
        then(visitRepository).should(timeout(100)).findById(anyLong());
    }

    // behaviour driven testing
    @Test
    void findByIdBDDTest() {
        // given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        // when
        visitSDJpaService.findById(1L);
        // then
        then(visitRepository).should(times(1)).findById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void save() {
        // given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        // when
        Visit savedVisit = visitSDJpaService.save(new Visit());
        // then
        then(visitRepository).should().save(any(Visit.class));
        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        // given
        Visit visit = new Visit();
        // when
        visitSDJpaService.delete(visit);
        // then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        // given

        // when
        visitSDJpaService.deleteById(1L);
        visitSDJpaService.deleteById(1L);
        // then
        then(visitRepository).should(timeout(100).times(2)).deleteById(anyLong());
    }
}