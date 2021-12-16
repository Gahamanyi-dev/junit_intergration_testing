package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getAll_success() {

        when(cityRepository.findAll()).thenReturn(Arrays.asList(new City(1L,"Kigali",28),
                new City(2L,"Rubavu",15)));
        assertEquals("Rubavu",cityService.getAll().get(1).getName());
    }

    @Test
    public void getOneById_success() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(new City(1L,"Kigali",28)));
        assertEquals("Kigali",cityService.getById(1L).get().getName());
    }

    @Test(expected = RuntimeException.class)
    public void getOneById_notFound(){
        doThrow(new RuntimeException()).when(cityRepository).findById(3L);
        cityService.getById(3L);
    }

    @Test
    public  void create_success(){
        CreateCityDTO city=new CreateCityDTO("Kigali",23);
        City createdCity=new City(1L,"Kigali",23);

        when(cityRepository.save(ArgumentMatchers.any(City.class))).thenReturn(createdCity);
        assertEquals("Kigali",cityService.save(city).getName());
    }

    @Test(expected = RuntimeException.class)
    public void create_fail_NameAlreadyExists() {
        CreateCityDTO city=new CreateCityDTO("Kigali",23);
        City createdCity=new City(1L,"Kigali",23);

        when(cityRepository.existsByName(city.getName())).thenReturn(true);
        when(cityRepository.save(any(City.class))).thenReturn(createdCity);
        cityService.save(city);
    }
}
