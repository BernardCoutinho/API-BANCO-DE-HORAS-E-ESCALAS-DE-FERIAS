package com.serratec.projeto.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.serratec.projeto.config.MailConfig;
import com.serratec.projeto.dto.AlterarBancoHorasDTO;
import com.serratec.projeto.dto.BancoHorasDTO;
import com.serratec.projeto.dto.CriarBancoHorasDTO;
import com.serratec.projeto.dto.CriarRegistroCompensacaoDTO;
import com.serratec.projeto.dto.CriarRegistroCompensadoDTO;
import com.serratec.projeto.exception.RecursoBadRequestException;
import com.serratec.projeto.exception.RecursoNotFoundException;
import com.serratec.projeto.model.BancoHoras;
import com.serratec.projeto.model.Usuario;
import com.serratec.projeto.repository.BancoHorasRepository;
import com.serratec.projeto.repository.UsuarioRepository;

@Service
public class BancoHorasService {
	private BancoHorasRepository bhRepository;
	private UsuarioRepository usuarioRepository;
	private RegistroCompensacaoService regCompensacaoService;
	private RegistroCompensadoService regCompensadoService;

	@Autowired
	MailConfig mailConfig;

	@Autowired
	public BancoHorasService(BancoHorasRepository bhRepository, UsuarioRepository usuarioRepository,
			RegistroCompensacaoService regCompensacaoService, RegistroCompensadoService regCompensadoService) {
		super();
		this.bhRepository = bhRepository;
		this.usuarioRepository = usuarioRepository;
		this.regCompensacaoService = regCompensacaoService;
		this.regCompensadoService = regCompensadoService;
	}

	/**
	 * MÉTODO PARA CRIAR UM BANCO DE HORAS
	 * 
	 * @param request
	 * @return RETORNA UM BANCO DE HORAS
	 */

	public BancoHoras criarBancoHoras(CriarBancoHorasDTO request) {
		BancoHoras bh = new BancoHoras();
		bh.setSaldo_minutos(request.getSaldo_minutos());
		bh.setUsuario(usuarioRepository.getById(request.getId_usuario()));
		return bhRepository.save(bh);
	}

	/**
	 * MÉTODO PARA LISTAR OS BANCOS DE HORAS
	 * 
	 * @return UMA LISTA DE BANCOS DE HORAS
	 */

	public List<BancoHoras> listar() {
		return bhRepository.findAll();
	}

	/**
	 * MÉTODO PARA BUSCAR UM BANCO DE HORAS POR ID
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<BancoHoras> buscarPorId(Long id) throws RecursoNotFoundException {
		Optional<BancoHoras> bh = bhRepository.findById(id);
		if (bh.isPresent()) {
			return ResponseEntity.ok(bh.get());
		} else {
			throw new RecursoNotFoundException("Banco de horas não encontrado");
		}

	}

	/**
	 * MÉTODO PARA ALTERAR UM BANCO DE HORAS
	 * 
	 * @param id
	 * @param request
	 * @return
	 */

	public BancoHorasDTO alterar(Long id, AlterarBancoHorasDTO alterarBancoHorasDTO) throws RecursoNotFoundException {
		if (bhRepository.existsById(id)) {
			BancoHoras bancoHoras = new BancoHoras(alterarBancoHorasDTO);
			bancoHoras.setId_banco(id);
			bancoHoras.setSaldo_minutos(alterarBancoHorasDTO.getSaldo_minutos());
			bancoHoras.setUsuario(alterarBancoHorasDTO.getUsuario());
			bhRepository.save(bancoHoras);
			return new BancoHorasDTO(bancoHoras);
		} else {
			throw new RecursoNotFoundException("Banco de horas não encontrado");
		}

	}

	/**
	 * MÉTODO PARA DELETAR UM BANCO DE HORAS
	 * 
	 * @param id
	 * @return
	 */

	public ResponseEntity<Void> deletar(Long id) {
		if (!bhRepository.existsById(id))
			return ResponseEntity.notFound().build();
		bhRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * MÉTODO PARA ADICIONAR HORAS NO BANCO DE HORAS DO USUÁRIO
	 * 
	 * @param request
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public ResponseEntity<BancoHoras> adicionarHoras(CriarRegistroCompensacaoDTO request)
			throws RecursoBadRequestException {

		Usuario user = usuarioRepository.getById(request.getIdUsuario());
		BancoHoras bh = bhRepository.findByUsuario(user);

		if (!bhRepository.existsById(bh.getId_banco()))
			return ResponseEntity.notFound().build();

		long minTotais = Duration.between(request.getHrInicioCompensacao(), request.getHrTerminoCompensacao())
				.toMinutes();

		if (minTotais > 0) {

			DayOfWeek diaDaSemana = request.getDataCompensacao().getDayOfWeek();
			long maxMinSemana = 120;
			long maxMinSabado = 480;
			if (diaDaSemana == DayOfWeek.SUNDAY) {
				throw new RecursoBadRequestException("Não é possível realizar compensação no Domingo!");
			} else if (diaDaSemana == DayOfWeek.SATURDAY) {
				if (minTotais > maxMinSabado) {
					bh.setSaldo_minutos(Math.addExact(bh.getSaldo_minutos(), maxMinSabado));
					bhRepository.save(bh);
					regCompensacaoService.criarRegistroCompensacao(request);
					throw new RecursoBadRequestException(
							"Apenas oito horas serão computadas! Não é possível realizar mais de oito horas de compensação no Sábado!");
				} else {
					bh.setSaldo_minutos(Math.addExact(bh.getSaldo_minutos(), minTotais));
					bhRepository.save(bh);
					regCompensacaoService.criarRegistroCompensacao(request);
					return ResponseEntity.ok(bh);
				}
			} else if (minTotais > maxMinSemana) {

				bh.setSaldo_minutos(Math.addExact(bh.getSaldo_minutos(), maxMinSemana));
				regCompensacaoService.criarRegistroCompensacao(request);
				throw new RecursoBadRequestException(
						"Apenas duas horas serão computadas! Não é possível realizar mais de duas horas de compensação no meio da semana!");
			} else {
				bh.setSaldo_minutos(Math.addExact(bh.getSaldo_minutos(), minTotais));
				bhRepository.save(bh);
				regCompensacaoService.criarRegistroCompensacao(request);
				return ResponseEntity.ok(bh);
			}
		} else {
			throw new RecursoBadRequestException("Horário inválido!");
		}

	}

	/**
	 * MÉTODO PARA REMOVER HORAS DO BANCO DE HORAS DO USUÁRIO
	 * 
	 * @param request
	 * @return
	 * @throws RecursoBadRequestException
	 */

	public ResponseEntity<BancoHoras> removerHoras(CriarRegistroCompensadoDTO request)
			throws RecursoBadRequestException {
		BancoHoras bh = bhRepository.findByUsuario(usuarioRepository.getById(request.getId_usuario()));
		if (!bhRepository.existsById(bh.getId_banco()))
			return ResponseEntity.notFound().build();
		long minTotais = Duration.between(request.getHrInicioCompensado(), request.getHrTerminoCompensado())
				.toMinutes();
		if (minTotais > 0) {

			DayOfWeek diaDaSemana = request.getDataCompensado().getDayOfWeek();
			if (diaDaSemana == DayOfWeek.SUNDAY) {
				throw new RecursoBadRequestException("Dia inválido! Não é possível usar suas horas no Domingo!");
			} else if (minTotais > 480) {
				throw new RecursoBadRequestException(
						"Horário inválido! Não é possível usar mais de oito horas por dia!");
			} else {
				bh.setSaldo_minutos(Math.subtractExact(bh.getSaldo_minutos(), minTotais));
				bhRepository.save(bh);
				regCompensadoService.criarRegistroCompensado(request);

				return ResponseEntity.ok(bh);
			}
		} else {
			throw new RecursoBadRequestException("Horário inválido!");
		}

	}

	/**
	 * MÉTODO PARA ZERAR O BANCO DE HORAS
	 */

	public void zeraBanco() {
		List<BancoHoras> listBH = bhRepository.findAll();
		for (BancoHoras zbh : listBH) {
			BancoHoras bh = new BancoHoras();
			bh.setSaldo_minutos((long) 0);
			bh.setUsuario(zbh.getUsuario());
			bh.setId_banco(zbh.getId_banco());
			bhRepository.save(bh);
			String texto = "Seu banco de horas foi zerado";
			mailConfig.enviarEmail(bh.getUsuario().getEmail(), "AlterStatus - Banco de horas", texto);
		}
	}

	/**
	 * MÉTODO PARA ENVIAR UM EMAIL PRO USUÁRIO AVISANDO SOBRE AS HORAS DO BANCO
	 */

	public void enviaEmails() {
		String texto;
		String assunto = "AlterStatus - Seu Banco de horas";
		List<BancoHoras> listbh = bhRepository.findAll();

		for (BancoHoras bh : listbh) {

			if (bh.getSaldo_minutos() != 0) {
				Long horas = bh.getSaldo_minutos() / 60;
				Long min = bh.getSaldo_minutos() % 60;
				if (bh.getSaldo_minutos() > 0) {

					texto = String.format("Você ainda tem %d:%d em seu banco para usar!", horas, min);
					mailConfig.enviarEmail(bh.getUsuario().getEmail(), assunto, texto);
				} else {

					texto = String.format("Você ainda tem %d:%d em seu banco para compensar!", (horas * -1),
							(min * -1));
					mailConfig.enviarEmail(bh.getUsuario().getEmail(), assunto, texto);
				}

			}
		}

	}

}
