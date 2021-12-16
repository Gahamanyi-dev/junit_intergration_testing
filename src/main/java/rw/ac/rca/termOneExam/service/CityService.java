package rw.ac.rca.termOneExam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

@Service
public class CityService {

	@Autowired
	private ICityRepository cityRepository;
	
	public Optional<City> getById(long id) {
		Optional<City> cityOptional = cityRepository.findById(id);
		if(cityOptional.isPresent()) {
			City city = cityOptional.get();
			city.setFahrenheit((city.getWeather()*1.8)+32);
			return cityOptional;
		}
		throw new RuntimeException("city with this id not found");
//		return cityRepository.findById(id);
	}

	public List<City> getAll() {
		List<City> cities = cityRepository.findAll();
		for(City city:cities) {
			city.setFahrenheit((city.getWeather()*1.8)+32);
		}
		return cities;
//		return cityRepository.findAll();
	}

	public boolean existsByName(String name) {

		return cityRepository.existsByName(name);
	}

	public City save(CreateCityDTO dto) {
		if(!existsByName(dto.getName())){
			City city =  new City(dto.getName(), dto.getWeather());
			city.setFahrenheit((dto.getWeather()*1.8)+32);
			return cityRepository.save(city);
		}
		throw new RuntimeException("city name already exists");
	}


}
