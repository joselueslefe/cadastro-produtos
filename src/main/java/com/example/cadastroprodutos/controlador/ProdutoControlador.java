package com.example.cadastroprodutos.controlador;

import com.example.cadastroprodutos.modelo.Produto;
import com.example.cadastroprodutos.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoControlador {

    @Autowired
    private ProdutoRepositorio repositorio;

    // -------------------------------
    // 1. LISTAR TODOS
    // -------------------------------
    @GetMapping
    public List<Produto> listarTodos() {
        return repositorio.findAll();
    }

    // -------------------------------
    // 2. BUSCAR POR ID
    // -------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = repositorio.findById(id);

        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------------------
    // 3. CADASTRAR PRODUTO
    // -------------------------------
    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return repositorio.save(produto);
    }

    // -------------------------------
    // 4. ATUALIZAR PRODUTO
    // -------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto dadosAtualizados) {

        Optional<Produto> produtoOptional = repositorio.findById(id);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            produto.setNome(dadosAtualizados.getNome());
            produto.setDescricao(dadosAtualizados.getDescricao());
            produto.setPreco(dadosAtualizados.getPreco());
            produto.setQuantidade(dadosAtualizados.getQuantidade());

            repositorio.save(produto);

            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------------------
    // 5. EXCLUIR PRODUTO
    // -------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 - OK sem conteúdo
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
