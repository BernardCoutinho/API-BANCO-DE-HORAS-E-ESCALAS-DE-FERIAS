package com.serratec.projeto.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.serratec.projeto.config.MailConfig;
import com.serratec.projeto.dto.AlterarApontamentoFeriasDTO;
import com.serratec.projeto.dto.ApontamentoFeriasDTO;
import com.serratec.projeto.dto.CriarApontamentoFeriasDTO;
import com.serratec.projeto.dto.CriarMarcarFolgaPorPeriodoDTO;
import com.serratec.projeto.dto.UsuarioDTO;
import com.serratec.projeto.exception.RecursoBadRequestException;
import com.serratec.projeto.exception.RecursoNotFoundException;
import com.serratec.projeto.model.ApontamentoFerias;
import com.serratec.projeto.model.Equipe;
import com.serratec.projeto.model.Nivel;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.ApontamentoFeriasRepository;
import com.serratec.projeto.repository.EquipeRepository;
import com.serratec.projeto.repository.UsuarioRepository;

@Service
public class ApontamentoFeriasService {
	private ApontamentoFeriasRepository apontamentoFeriasRepository;
	private UsuarioRepository usuarioRepository;
	private EquipeRepository equipeRepository;
	private UsuarioService usuarioService;
	private EquipeService equipeService;

	@Autowired
	MailConfig mailConfig;

	@Autowired
	public ApontamentoFeriasService(EquipeService equipeService, EquipeRepository equipeRepository,
			ApontamentoFeriasRepository apontamentoFeriasRepository, UsuarioRepository usuarioRepository,
			UsuarioService usuarioService) {
		super();
		this.equipeService = equipeService;
		this.apontamentoFeriasRepository = apontamentoFeriasRepository;
		this.usuarioRepository = usuarioRepository;
		this.equipeRepository = equipeRepository;
		this.usuarioService = usuarioService;
	}

	/**
	 * MÉTODO PARA CRIAR UM APONTAMENTO DE FÉRIAS
	 * 
	 * @param request
	 * @return
	 */

	public CriarApontamentoFeriasDTO criarApontamentoFerias(CriarApontamentoFeriasDTO request) {
		ApontamentoFerias apontamento = new ApontamentoFerias();
		Usuario user = usuarioRepository.getById(request.getId_usuario());
		int qtdDia = 1;
		usuarioService.DescontarFerias(user, qtdDia);
		apontamento.setDiaFolga(request.getDiaFolga());
		apontamento.setUsuario(user);
		apontamentoFeriasRepository.save(apontamento);

		DateTimeFormatter dt = DateTimeFormatter.ISO_DATE;
		String texto, assunto;
		assunto = String.format("AlterStatus - Ferias Marcada: %s", dt.format(request.getDiaFolga()));
		texto = String.format("O usuário %s da equipe %s marcou férias no dia %s", user.getNome(),
				user.getEquipe().getNomeEquipe(), dt.format(request.getDiaFolga()));
		mailConfig.enviarEmail(user.getEmail(), assunto, texto);
		mailConfig.enviarEmail("bernard_coutinho2002@hotmail.com", assunto, texto);
		return new CriarApontamentoFeriasDTO(apontamento);
	}

	/**
	 * MÉTODO PARA LISTAR APONTAMENTOS DE FÉRIAS
	 * 
	 * @return
	 */

	public List<ApontamentoFerias> listar() {
		return apontamentoFeriasRepository.findAll();
	}

	/**
	 * MÉTODO PARA BUSCAR UM APONTAMENTO POR ID
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<ApontamentoFerias> buscarPorId(Long id) {
		Optional<ApontamentoFerias> apontamento = apontamentoFeriasRepository.findById(id);
		if (apontamento.isPresent())
			return ResponseEntity.ok(apontamento.get());
		return ResponseEntity.notFound().build();
	}

	/**
	 * MÉTODO PARA ALTERAR UM APONTAMENTO
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public ApontamentoFeriasDTO alterar(Long id, AlterarApontamentoFeriasDTO alterarApontamentoFeriasDTO)
			throws RecursoNotFoundException {
		if (apontamentoFeriasRepository.existsById(id)) {
			ApontamentoFerias apontamento = new ApontamentoFerias(alterarApontamentoFeriasDTO);
			apontamento.setDiaFolga(alterarApontamentoFeriasDTO.getDiaFolga());
			apontamento.setUsuario(usuarioRepository.getById(alterarApontamentoFeriasDTO.getIdUsuario()));
			apontamentoFeriasRepository.save(apontamento);
			return new ApontamentoFeriasDTO(apontamento);
		} else {
			throw new RecursoNotFoundException("Apontamento de férias não encontrado");
		}

	}

	/**
	 * MÉTODO PARA DELETAR UM APONTAMENTO
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<Void> deletar(Long id) {
		if (!apontamentoFeriasRepository.existsById(id))
			return ResponseEntity.notFound().build();

		ApontamentoFerias apontamento = apontamentoFeriasRepository.getById(id);
		usuarioService.acrescentarFerias(apontamento.getUsuario().getIdUsuario(), 1);
		apontamentoFeriasRepository.deleteById(id);
		DateTimeFormatter dt = DateTimeFormatter.ISO_DATE;
		String texto, assunto;
		assunto = String.format("AlterStatus - Ferias Desmarcada: %s", dt.format(apontamento.getDiaFolga()));
		texto = String.format("O usuário %s da equipe %s desmarcou folga no dia %s", apontamento.getUsuario().getNome(),
				apontamento.getUsuario().getEquipe().getNomeEquipe(), dt.format(apontamento.getDiaFolga()));
		mailConfig.enviarEmail(apontamento.getUsuario().getEmail(), assunto, texto);
		mailConfig.enviarEmail("bernard_coutinho2002@hotmail.com", assunto, texto);
		return ResponseEntity.noContent().build();
	}

	/*
	 * MÉTODO PARA LISTAR AS FERIAS DO USUÁRIO
	 * 
	 * @param id_usuario
	 * 
	 * @return
	 * 
	 * @throws RecursoBadRequestException
	 */
	public List<ApontamentoFerias> feriasDoUsuario(Long id) {
		List<ApontamentoFerias> aptF = listar();
		List<ApontamentoFerias> aptUser = new ArrayList<ApontamentoFerias>();
		Usuario usuario = usuarioRepository.getById(id);

		for (ApontamentoFerias apt : aptF) {
			if (apt.getUsuario() == usuario) {
				aptUser.add(apt);
			}
		}
		return aptUser;
	}

	/**
	 * MÉTODO PARA MARCAR FOLGA DE UM USUÁRIO POR PERÍODO
	 * 
	 * @param request
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public List<CriarApontamentoFeriasDTO> marcarFolgaPorPeriodo(CriarMarcarFolgaPorPeriodoDTO request)
			throws RecursoBadRequestException {
		Usuario user = usuarioRepository.getById(request.getIdUsuario());
		List<LocalDate> dates = Stream.iterate(request.getDataInicial(), date -> date.plusDays(1))
				.limit(ChronoUnit.DAYS.between(request.getDataInicial(), request.getDataFinal().plusDays(1)))
				.collect(Collectors.toList());
		List<CriarApontamentoFeriasDTO> listaDiasMarcados = new ArrayList<CriarApontamentoFeriasDTO>();
		List<String> listB = dates.stream().map((u) -> verificaPossibilidadeFolga(u, user))
				.collect(Collectors.toList());
		boolean pode = true;

		for (String b : listB) {

			if (b.contains("ERROR!")) {
				pode = false;
			}
		}
		if (pode == true) {

			dates.stream().forEach((d) -> {
				CriarApontamentoFeriasDTO aponta = new CriarApontamentoFeriasDTO();
				aponta.setDiaFolga(d);
				if (aponta.getDiaFolga().getDayOfWeek() == DayOfWeek.SUNDAY) {
					System.out.println("Domingo");
				} else {

					aponta.setId_usuario(request.getIdUsuario());
					aponta.setNome(user.getNome());
					listaDiasMarcados.add(aponta);
					criarApontamentoFerias(aponta);
				}

			});
			return listaDiasMarcados;
		} else {
			String erros = Arrays.asList(listB).stream().map(Object::toString).collect(Collectors.joining(", "))
					.replace("[", "").replace("]", "");
			throw new RecursoBadRequestException(erros);
		}

	}

	/**
	 * MÉTODO PARA LISTAR USUÁRIOS QUE ESTÃO DE FOLGA NO DIA
	 * 
	 * @param dia
	 * @param id_equipe
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public List<UsuarioDTO> listaUsuariosDaEquipeDeFolgaNoDia(LocalDate dia, Long id_equipe)
			throws RecursoBadRequestException {
		if (!equipeRepository.existsById(id_equipe))
			throw new RecursoBadRequestException("Equipe não existe");

		List<ApontamentoFerias> listaTotal = apontamentoFeriasRepository.findAll();
		Equipe equipe = equipeRepository.getById(id_equipe);

		List<UsuarioDTO> listaUsuarios = listaTotal.stream().filter((a) -> a.getUsuario().getEquipe().equals(equipe))
				.filter((a) -> a.getDiaFolga().equals(dia)).map((a) -> new UsuarioDTO(a.getUsuario()))
				.collect(Collectors.toList());

		return listaUsuarios;
	}

	/**
	 * MÉTODO PARA VERIFICAR SE O USUARIO JÁ TEM A FOLGA MARCADA NO DIA
	 * 
	 * @param dia
	 * @param Usuario
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public void verificaFolgaRepitida(LocalDate dia, Usuario usuario) throws RecursoBadRequestException {
		Usuario user = usuario;
		if (!usuarioRepository.existsById(user.getIdUsuario()))
			throw new RecursoBadRequestException("Usuario não existe!");

		List<ApontamentoFerias> listaTotal = apontamentoFeriasRepository.findAll();
		for (ApontamentoFerias aponta : listaTotal) {
			DateTimeFormatter df = DateTimeFormatter.ISO_DATE;
			String diaApont = df.format(aponta.getDiaFolga());
			String diaRequest = df.format(dia);

			if (diaApont.equals(diaRequest) && aponta.getUsuario().getIdUsuario() == user.getIdUsuario()) {
				throw new RecursoBadRequestException(String.format("Você já marcou férias no dia %s!", diaApont));
			}
		}
	}

	/**
	 * MÉTODO PARA VERIFICAR A POSSIBILIDADE DE UM USUÁRIO TIRAR FOLGA EM UM
	 * DETERMINADO DIA
	 * 
	 * @param dia
	 * @param id_usuario
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public String verificaPossibilidadeFolga(LocalDate dia, Usuario usuarioRequest) throws RecursoBadRequestException {
		Usuario userRequest = usuarioRequest;
		if (userRequest.getQtdDiasFerias() == 0)
			throw new RecursoBadRequestException("Você já usou todas as sua férias!");

		Long idEquipe = userRequest.getEquipe().getIdEquipe();
		verificaFolgaRepitida(dia, userRequest);
		List<UsuarioDTO> listaUsuarios = listaUsuariosDaEquipeDeFolgaNoDia(dia, idEquipe);
		int senior = 0, junior = 0, trainee = 0, pleno = 0;
		int quantMembros = equipeService.tamanhoEquipe(idEquipe);

		DateTimeFormatter df = DateTimeFormatter.ISO_DATE;
		String response;
		if (dia.isAfter(userRequest.getDataVencimento()) || dia.isBefore(userRequest.getDataPodeIniciarFerias())) {
			return ("ERROR! Não foi possível marcar a folga! Marque entre " + df.format(userRequest.getDataPodeIniciarFerias())
					+ " e " + df.format(userRequest.getDataVencimento()));
		} else {

			for (UsuarioDTO user : listaUsuarios) {
				if (user.getNivel() == Nivel.SENIOR) {
					senior += 1;
				} else if (user.getNivel() == Nivel.PLENO) {
					pleno += 1;
				} else if (user.getNivel() == Nivel.JUNIOR) {
					junior += 1;
				} else {
					trainee += 1;
				}
			}
			if (userRequest.getNivel() == Nivel.SENIOR) {
				if (senior >= 1 && quantMembros <= 12) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Sêniors de folga para um time pequeno já foi alcançado (1)!",
							df.format(dia));
					return (response);
				} else if (senior >= 6 && quantMembros > 12 && quantMembros <= 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Sêniors de folga para um time médio já foi alcançado (6)!",
							df.format(dia));
					return (response);
				} else if (senior >= 8 && quantMembros > 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Sêniors de folga para um time grande já foi alcançado (8)!",
							df.format(dia));
					return (response);
				} else {
					return "OK";
				}

			} else if (userRequest.getNivel() == Nivel.PLENO) {
				if (pleno >= 1 && quantMembros <= 12) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Plenos de folga para um time pequeno já foi alcançado (1)!",
							df.format(dia));
					return (response);
				} else if (pleno >= 6 && quantMembros > 12 && quantMembros <= 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Plenos de folga para um time médio já foi alcançado (6)!",
							df.format(dia));
					return (response);
				} else if (pleno >= 8 && quantMembros > 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Plenos de folga para um time grande já foi alcançado (8)!\n",
							df.format(dia));
					return (response);
				} else {
					return "OK";
				}

			} else if (userRequest.getNivel() == Nivel.JUNIOR) {
				if (junior >= 1 && quantMembros <= 12) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Juniors de folga para um time pequeno já foi alcançado (1)!",
							df.format(dia));
					return (response);
				} else if (junior >= 6 && quantMembros > 12 && quantMembros <= 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Juniors de folga para um time médio já foi alcançado (6)!",
							df.format(dia));
					return (response);
				} else if (junior >= 8 && quantMembros > 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Juniors de folga para um time grande já foi alcançado (8)!",
							df.format(dia));
					return (response);
				} else {
					return "OK";
				}

			} else if (userRequest.getNivel() == Nivel.TRAINEE) {
				if (trainee >= 1 && quantMembros <= 12) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Treinees de folga para um time pequeno já foi alcançado (1)!",
							df.format(dia));
					return (response);
				} else if (trainee >= 6 && quantMembros > 12 && quantMembros <= 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Treiness de folga para um time médio já foi alcançado (6)!",
							df.format(dia));
					return (response);
				} else if (trainee >= 8 && quantMembros > 24) {
					response = String.format(
							"ERROR! Não foi possível marcar as férias no dia %s! Número máximo de Treinees de folga para um time grande já foi alcançado (8)!",
							df.format(dia));
					return (response);
				} else {
					return "OK";
				}
			} else {
				throw new RecursoBadRequestException("Parâmetros inválidos!");
			}
		}
	}

	/**
	 * MÉTODO PARA MARCAR FOLGA
	 * 
	 * @param request
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public CriarApontamentoFeriasDTO marcarFolga(@RequestBody CriarApontamentoFeriasDTO request)
			throws RecursoBadRequestException {

		DayOfWeek diaDaSemana = request.getDiaFolga().getDayOfWeek();
		Usuario user = usuarioRepository.getById(request.getId_usuario());

		if (request.getDiaFolga().isAfter(user.getDataVencimento())
				|| request.getDiaFolga().isBefore(user.getDataPodeIniciarFerias())) {
			DateTimeFormatter df = DateTimeFormatter.ISO_DATE;
			throw new RecursoBadRequestException("Não foi possível marcar a folga! Marque entre "
					+ df.format(user.getDataPodeIniciarFerias()) + " e " + df.format(user.getDataVencimento()));
		} else {
			if (diaDaSemana == DayOfWeek.SUNDAY) {
				throw new RecursoBadRequestException("Não é possível marcar férias no Domingo!");
			} else {
				String resposta;
				resposta = verificaPossibilidadeFolga(request.getDiaFolga(), user);
				if (resposta.contains("ERROR!")) {
					throw new RecursoBadRequestException(resposta);
				} else {
					return criarApontamentoFerias(request);

				}
			}
		}
	}
}
