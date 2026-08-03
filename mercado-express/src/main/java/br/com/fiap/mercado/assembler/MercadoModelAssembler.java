package br.com.fiap.mercado.assembler;

import br.com.fiap.mercado.controller.MercadoController;
import br.com.fiap.mercado.model.Mercado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MercadoModelAssembler implements RepresentationModelAssembler<Mercado, EntityModel<Mercado>> {

    @Override
    public EntityModel<Mercado> toModel(Mercado mercado) {
        return EntityModel.of(
                mercado,
                linkTo(methodOn(MercadoController.class).buscarPorId(mercado.getId())).withSelfRel(),
                linkTo(methodOn(MercadoController.class).listarTodos()).withRel("todos-produtos"),
                linkTo(methodOn(MercadoController.class).atualizar(mercado.getId(), mercado)).withRel("atualizar"),
                linkTo(methodOn(MercadoController.class).deletar(mercado.getId())).withRel("deletar")
        );
    }
}
