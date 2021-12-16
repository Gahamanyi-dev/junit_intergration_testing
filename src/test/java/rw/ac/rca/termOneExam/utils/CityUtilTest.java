package rw.ac.rca.termOneExam.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityUtilTest {
    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void weatherLessThan40_success(){
        List<City> cities = cityRepository.findAll();
        for(City city: cities){
            assertTrue(city.getWeather() < 40);
        }
    }

    @Test
    public void weatherGreaterThan10_success(){
        List<City> cities = cityRepository.findAll();
        for(City city: cities){
            assertTrue(city.getWeather() > 10);
        }
    }

    @Test
    public void ContainMusanzeAndKigali_success(){
        List<City> cities = cityRepository.findAll();
        List<String> cities_name = new ArrayList<String>();
        for(City city: cities){
            cities_name.add(city.getName());
        }
        assertTrue(cities_name.contains("Musanze"));
        assertTrue(cities_name.contains("Kigali"));
    }

    @Test
    public void list_spying() {

        ArrayList<City> arrayListSpy = spy(ArrayList.class);
        City city = new City(101,"Ruzagayura",11);
        arrayListSpy.add(city);
        System.out.println(arrayListSpy.get(0).getWeather());//Test0
        System.out.println(arrayListSpy.size());//1

        arrayListSpy.add(city);
        arrayListSpy.add(city);
        System.out.println(arrayListSpy.size());//3

        when(arrayListSpy.size()).thenReturn(5);
        System.out.println(arrayListSpy.size());//5
        //now call is lost so 5 will be returned no matter what

        arrayListSpy.add(city);
        System.out.println(arrayListSpy.size());
    }

    @Test
    public void list_mocking() {
        City city = new City(101,"Ruzagayura",11);
        List<City> mockList = mock(List.class);
        when(mockList.size()).thenReturn(5);
        assertEquals(5, mockList.size());
        when(mockList.get(0)).thenReturn(city);
        assertEquals("Ruzagayura", mockList.get(0).getName());
        assertEquals(null, mockList.get(1));
        verify(mockList,atLeast(1)).get(anyInt());
    }


}
