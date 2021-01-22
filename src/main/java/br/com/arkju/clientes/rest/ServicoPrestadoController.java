package br.com.arkju.clientes.rest;

import br.com.arkju.clientes.model.entity.Cliente;
import br.com.arkju.clientes.model.entity.ServicoPrestado;
import br.com.arkju.clientes.model.repository.ClienteRepository;
import br.com.arkju.clientes.model.repository.ServicoPrestadoRepository;
import br.com.arkju.clientes.rest.dto.ServicoPrestadoDTO;
import br.com.arkju.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto){
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(()->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST,"cliente não encontrado"));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setValor(bigDecimalConverter.converter(dto.getValor()));
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);

        return repository.save(servicoPrestado);
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(
            @RequestParam(name = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(name = "mes", required = false) Integer mes
    ){
        return repository.findByNomeClienteandMes("%" + nome + "%", mes);
    }
}
