package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	private PersonRepository repository;
	
	public List<PersonVO> findAll() {

		logger.info("Finding all people!");

		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one PersonVOV2");

		var entity =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(PersonVO person) {

		logger.info("Creating one PersonVOV2");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); ;
		return vo;
	}
	
	public PersonVO update(PersonVO PersonVO) {

		logger.info("Updating one PersonVOV2");

		var entity = repository.findById(PersonVO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(PersonVO.getFirstName());
		entity.setLastName(PersonVO.getLastName());
		entity.setAddress(PersonVO.getAddress());
		entity.setGender(PersonVO.getGender());

		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); ;
		return vo;
	}

	public void delete(Long id) {

		logger.info("Deleting one PersonVOV2");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}

}
