package br.com.aweb.sistema_manutencao.controller;

import java.lang.ProcessBuilder.Redirect;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.sistema_manutencao.model.Manutencao;
import br.com.aweb.sistema_manutencao.repository.ManutencaoRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/sistema-manutencao")
public class ManutencaoController {
    
    @Autowired
    ManutencaoRepository manutencaoRepository;

    @GetMapping
    public ModelAndView list(){
        return new ModelAndView("list", Map.of("manutencoes", manutencaoRepository.findAll()));
    }

    @GetMapping("/nova-manutencao")
    public ModelAndView nova(){
        return new ModelAndView("form", Map.of("manutencao", new Manutencao()));
    }

    @PostMapping("/nova-manutencao")
    public String nova(@Valid Manutencao manutencao, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors())
            return "form";
        
        manutencaoRepository.save(manutencao);
        
        return "redirect:/sistema-manutencao";

    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable long id){
        Optional<Manutencao> manutencao = manutencaoRepository.findById(id);
        if(manutencao.isPresent() && manutencao.get().getDataFinalizado()==null)
            return new ModelAndView("form", Map.of("manutencao", manutencao.get()));

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid Manutencao manutencao, BindingResult result){
        if(result.hasErrors())
            return "form";
        
            manutencaoRepository.save(manutencao);
            return "redirect:/sistema-manutencao";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        var manutencao =  manutencaoRepository.findById(id);
        if(manutencao.isPresent()){
            return new ModelAndView("delete", Map.of("manutencao", manutencao.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{id}")
    public String delete(Manutencao manutencao){
        manutencaoRepository.delete(manutencao);
        return "redirect:/sistema-manutencao";
    }

    @PostMapping("/finalizar/{id}")
    public String finalizar(@PathVariable Long id){
        var optionalManutencao = manutencaoRepository.findById(id);
        if (optionalManutencao.isPresent()) {
            var manutencao = optionalManutencao.get();
            manutencao.setDataFinalizado(LocalDate.now());
            manutencaoRepository.save(manutencao);
            return "redirect:/sistema-manutencao";
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
