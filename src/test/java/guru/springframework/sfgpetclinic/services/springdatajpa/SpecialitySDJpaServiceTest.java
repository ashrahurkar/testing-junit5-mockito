package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialitySDJpaService specialitySDJpaService;

    @Test
    void testDeleteByObject() {
        Speciality speciality = new Speciality();
        specialitySDJpaService.delete(speciality);
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void testFindById() {
        Speciality speciality = new Speciality();
        when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality));
        Speciality foundSpecialty = specialitySDJpaService.findById(1l);
        assertThat(foundSpecialty).isNotNull();
        verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);
        verify(specialtyRepository, times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdAtLeast() {
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);
        verify(specialtyRepository, atLeastOnce()).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);
        verify(specialtyRepository, atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);
        verify(specialtyRepository, atLeastOnce()).deleteById(1l);
        verify(specialtyRepository, never()).deleteById(5l);
    }

    @Test
    void delete() {
        specialitySDJpaService.delete(new Speciality());
    }
}