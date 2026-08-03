package br.com.fiap.mercado.controller;

import br.com.fiap.mercado.assembler.MercadoModelAssembler;
import br.com.fiap.mercado.model.Mercado;
import br.com.fiap.mercado.repository.MercadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/mercado")
public class MercadoController {

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private MercadoModelAssembler assembler;

    // Listar todos os produtos
    @GetMapping
    public CollectionModel<EntityModel<Mercado>> listarTodos() {
        List<EntityModel<Mercado>> produtos = mercadoRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                produtos,
                linkTo(methodOn(MercadoController.class).listarTodos()).withSelfRel()
        );
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public EntityModel<Mercado> buscarPorId(@PathVariable Long id) {
        Mercado produto = mercadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o ID: " + id));

        return assembler.toModel(produto);
    }

    // Cadastrar produto
    @PostMapping
    public ResponseEntity<EntityModel<Mercado>> cadastrar(@RequestBody Mercado produto) {
        Mercado novoProduto = mercadoRepository.save(produto);
        EntityModel<Mercado> entityModel = assembler.toModel(novoProduto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Atualizar produto completo
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Mercado>> atualizar(@PathVariable Long id, @RequestBody Mercado produtoAtualizado) {
        Mercado produtoExistente = mercadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o ID: " + id));

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setTipo(produtoAtualizado.getTipo());
        produtoExistente.setSetor(produtoAtualizado.getSetor());
        produtoExistente.setTamanho(produtoAtualizado.getTamanho());
        produtoExistente.setPreco(produtoAtualizado.getPreco());

        Mercado produtoSalvo = mercadoRepository.save(produtoExistente);
        return ResponseEntity.ok(assembler.toModel(produtoSalvo));
    }

    // Atualizar parcial (patch)
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Mercado>> atualizarParcial(@PathVariable Long id, @RequestBody Mercado produtoParcial) {
        Mercado produtoExistente = mercadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o ID: " + id));

        if (produtoParcial.getNome() != null) {
            produtoExistente.setNome(produtoParcial.getNome());
        }
        if (produtoParcial.getTipo() != null) {
            produtoExistente.setTipo(produtoParcial.getTipo());
        }
        if (produtoParcial.getSetor() != null) {
            produtoExistente.setSetor(produtoParcial.getSetor());
        }
        if (produtoParcial.getTamanho() != null) {
            produtoExistente.setTamanho(produtoParcial.getTamanho());
        }
        if (produtoParcial.getPreco() != null) {
            produtoExistente.setPreco(produtoParcial.getPreco());
        }

        Mercado produtoSalvo = mercadoRepository.save(produtoExistente);
        return ResponseEntity.ok(assembler.toModel(produtoSalvo));
    }

    // Deletar produto pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Mercado produtoExistente = mercadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o ID: " + id));

        mercadoRepository.delete(produtoExistente);
        return ResponseEntity.noContent().build();
    }
}
