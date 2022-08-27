package com.amaro.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amaro.helpdesk.domain.Chamado;
import com.amaro.helpdesk.domain.Cliente;
import com.amaro.helpdesk.domain.Tecnico;
import com.amaro.helpdesk.domain.dtos.ChamadoDTO;
import com.amaro.helpdesk.domain.enums.Prioridade;
import com.amaro.helpdesk.domain.enums.Status;
import com.amaro.helpdesk.repositories.ChamadoRepository;
import com.amaro.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	//Faz a comunicação com o banco de dados....
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	
	public Chamado findById(Integer id){
		Optional<Chamado> obj = chamadoRepository.findById(id);	
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
	}
	

	public List<Chamado> findAll(){
		return chamadoRepository.findAll();
	}
	
	public Chamado create(@Valid ChamadoDTO objDTO) {
		//Antes de criar o chamado, verifica informações referente ao tecnico, cliente e se o chamado já existe.
		return chamadoRepository.save(newChamado(objDTO));
	}
	

	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		
		return chamadoRepository.save(oldObj);
	}
	
	private Chamado newChamado(ChamadoDTO objDTO) {
		//Verificar se existe o técnico e o cliente.
		Tecnico t =  tecnicoService.findById(objDTO.getTecnico());
		Cliente c = clienteService.findById(objDTO.getCliente());
		
		Chamado ch = new Chamado();
		
		//Verificar se chamado já existe, caso exista, atualize
		//Chamada sincrona
		if(objDTO.getId() != null) {
			ch.setId(objDTO.getId());
		}
		
		if(objDTO.getStatus().equals(2)) {
			ch.setDataFechamento(LocalDate.now());
		}
		ch.setTecnico(t);
		ch.setCliente(c);
		ch.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
		ch.setStatus(Status.toEnum(objDTO.getStatus()));
		ch.setTitulo(objDTO.getTitulo());
		ch.setObvervacoes(objDTO.getObservações());
		
		return ch;	
				
	}	

}
