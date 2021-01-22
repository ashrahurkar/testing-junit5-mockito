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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitSDJpaService visitSDJpaService;

    @Test
    void findAll() {
        Visit visit1 = new Visit();
        Visit visit2 = new Visit();
        Set<Visit> visits = new HashSet<>();
        visits.add(visit1);
        visits.add(visit2);
        // when call to findall made, return visits set
        when(visitRepository.findAll()).thenReturn(visits);
        Set<Visit> foundVisits = visitSDJpaService.findAll();
        verify(visitRepository).findAll();
        assertThat(visitSDJpaService.findAll()).isNotNull();
        assertThat(visitSDJpaService.findAll()).hasSize(2);
    }

    @Test
    void findById() {
        Visit visit = new Visit();
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        visitSDJpaService.findById(1L);
        verify(visitRepository).findById(anyLong());
    }

    @Test
    void save() {
        Visit visit = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = visitSDJpaService.save(new Visit());
        verify(visitRepository).save(any(Visit.class));
        assertThat(savedVisit).isNotNull();
    }

    @Test
    void delete() {
        Visit visit = new Visit();
        visitSDJpaService.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        visitSDJpaService.deleteById(1L);
        visitSDJpaService.deleteById(1L);
        verify(visitRepository, times(2)).deleteById(anyLong());
    }
}