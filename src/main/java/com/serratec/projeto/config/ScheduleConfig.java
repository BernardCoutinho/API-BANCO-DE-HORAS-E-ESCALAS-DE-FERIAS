package com.serratec.projeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.serratec.projeto.service.BancoHorasService;
import com.serratec.projeto.service.UsuarioService;

@Component
@EnableScheduling
public class ScheduleConfig {

	@Autowired
	private BancoHorasService bhService;

	@Autowired
	private UsuarioService userService;
	
	 private static final String TIME_ZONE = "America/Sao_Paulo";
	 
	 
	@Scheduled(zone = TIME_ZONE, cron = "0 0 7/1 1 * *")
	public void executeConfigVerification() {
		bhService.zeraBanco();
	}
	

	@Scheduled(zone = TIME_ZONE, cron = "0 1 7/1 15,20 * * ")
	public void mailSender() {
		
		bhService.enviaEmails();
	}

	@Scheduled(cron = "0 0 7/1 * * * ")
	public void feriasReset() {
		userService.resetarQuantidadeFeriasEAlterarDatas();
	}
}
