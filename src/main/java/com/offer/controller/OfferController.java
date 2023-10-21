package com.offer.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.offer.domain.dto.request.OfferDtoRequest;
import com.offer.domain.dto.request.OfferUpdateDtoRequest;
import com.offer.domain.dto.response.OfferDtoResponse;

import com.offer.service.OfferService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api("offers")
@RestController
@RequestMapping("/api/offers")

public class OfferController {
	@Autowired
	private OfferService service;

	@ApiOperation(value = "Buscar varias ofertas pelos IDS", notes = "Este endpoint busca varias ofertas pelos ids")
	@ApiResponses({ @ApiResponse(code = 200, message = "Oferta encontrada com sucesso"),
			@ApiResponse(code = 404, message = "Oferta não encontrada"),
			@ApiResponse(code = 401, message = "O cliente deve está autenticado ao sistema"),
			@ApiResponse(code = 500, message = "Erro interno do servidor") })
	@PostMapping("/")
	public ResponseEntity<List<Long>> findAllByIds(@RequestBody List<Long> ids) {
		log.info("Method={} message={}", "findAllByIds", "buscando por ids");
		return ResponseEntity.ok().body(service.findMissingIds(ids));
	}

	@ApiOperation(value = "Retorna uma lista de oferta", notes = "Este endpoint retorna uma lista de oferta")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Requisição feita com sucesso"),
			@ApiResponse(code = 403, message = "Cliente não autorizado"),
			@ApiResponse(code = 401, message = "O cliente deve está autenticado no sistema"),
			@ApiResponse(code = 500, message = "Erro interno do servidor") })
	@GetMapping()
	public ResponseEntity<Page<OfferDtoResponse>> findByTitle(
			@RequestParam(required = false, defaultValue = "%") String title,
			@PageableDefault(sort = "title", direction = Direction.ASC, page = 0, size = 5) Pageable pageable) {
		log.info("Method={} message={}", "findByTitle", "lista oferta");
		Page<OfferDtoResponse> byTitle = service.findByTitle(title, pageable);
		List<OfferDtoResponse> delayedElements = new ArrayList<>();
		for(OfferDtoResponse offer: byTitle.getContent()){
			try {
				Thread.sleep(1000);
				delayedElements.add(offer);
			} catch (Exception ex){

			}
		}
		PageImpl<OfferDtoResponse> offerDtoResponses = new PageImpl<>(delayedElements, pageable, delayedElements.size());
		return ResponseEntity.ok().body(offerDtoResponses);
	}

	@ApiOperation(value = "Salvar oferta", notes = "Este endpoint salvar uma oferta ")
	@ApiResponses({ @ApiResponse(code = 201, message = "Oferta cadastrada com sucesso"),
			@ApiResponse(code = 401, message = "O cliente deve está autenticado ao sistema"),
			@ApiResponse(code = 403, message = "Cliente não autorizado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor") })
	@PostMapping()
	public ResponseEntity<OfferDtoResponse> save( @RequestBody OfferDtoRequest offerDtoRequest) {
		OfferDtoResponse save = service.save(offerDtoRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(save.getId()).toUri();
		log.info("Method={} message={}", "save", "savando uma oferta");
		return ResponseEntity.created(uri).body(save);
	}

	@ApiOperation(value = "Atualizar uma oferta", notes = "Este endpoint atualizar uma oferta")
	@ApiResponses({ @ApiResponse(code = 200, message = "Oferta atualizada com sucesso"),
			@ApiResponse(code = 404, message = "Oferta não encontrada"),
			@ApiResponse(code = 401, message = "O cliente deve está autenticado ao sistema"),
			@ApiResponse(code = 403, message = "Cliente não autorizado"),
			@ApiResponse(code = 500, message = "Erro interno do servidor") })
	@PutMapping(value = "/{id}")
	public ResponseEntity<OfferDtoResponse> update(@PathVariable Long id,
			@RequestBody @Valid OfferUpdateDtoRequest offerUpdateDtoRequest) {
		log.info("Method={} message={}", "update", "atualizando uma oferta");
		return ResponseEntity.ok().body(service.update(id, offerUpdateDtoRequest));
	}

	@ApiOperation(value = "Exclui uma oferta", notes = "Este endpoint exclui uma oferta")
	@ApiResponses({ @ApiResponse(code = 204, message = "Oferta excluida com sucesso"),
			@ApiResponse(code = 500, message = "Erro interno do servidor") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		log.info("Method={} message={}", "delete", "deleta uma oferta");
		return ResponseEntity.noContent().build();
	}
}