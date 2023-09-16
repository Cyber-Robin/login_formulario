package com.robinmayorga.formulariologin.contollers;


import ch.qos.logback.core.model.Model;
import com.robinmayorga.formulariologin.editors.NombreMayusculaEditor;
import com.robinmayorga.formulariologin.editors.PaisPropertyEditor;
import com.robinmayorga.formulariologin.editors.RolesEditor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;

    @Autowired
    private PaisService paisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PaisPropertyEditor paisEditor;

    @Autowired
    private RolesEditor rolesEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(validator);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, true));

        binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());

        binder.registerCustomEditor(Pais.class, "pais", paisEditor);
        binder.registerCustomEditor(Role.class ,"roles", rolesEditor);
   }

   @ModelAttribute("genero")
    public List<String> genero() { return Arrays.asList("Hombre", "Mujer");}

    @ModelAttribute("listaRoles")
    public List<Role> listaRole() { return this.roleService.listar();}

    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises(){ return  paisService.listar();}

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString(){
        List<String> roles =new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");
        return roles;
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap(){
        Map<String, String> roles = new HashMap<>();
        roles.put("ROLE_ADMIN", "Administrador");
        roles.put("ROLE_USER", "Usuario");
        roles.put("ROLE_MODERATOR", "Moderator");

        return roles;
    }

    @ModelAttribute("paises")
    public List<String> paises(){
        return Arrays.asList("España", "Chile", "Argentina", "Peru", "Colombia", "Venezuela");
    }

    @ModelAttribute("paisesMap")
    public Map<String,String> paisesMap(){
        Map<String, String> paises = new HashMap<>();
        paises.put("ES", "España");
       paises.put("MX", "Mexico");
        paises.put("Cl", "Chile");
        paises.put("VE", "Venezuela");
        paises.put("AR", "Argentina");
        paises.put("PE", "Peru");
        paises.put("CO", "Colombia");

        return paises;
    }

    @GetMapping("/form")
    public String form(Model model) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Jhon");
        usuario.setApellido("Doe");
        usuario.setIdentificador("123.456.789-K"),
        usuario.setHabilitar(true);
        usuario.setValorSecreto ("Algun valor secreto ****");
        usuario.setPais(new Pais(3, "CL", "Chile"));
        usuario.setRoles(Arrays.asList(new Role(2, "Usuario", "ROLE_USER")));

        model.addAtribute("titulo", "Formulario Usiarios");
        model.addAtribute("usuario", usuario);

        return "form";
    }

    @PostMapping("/form"){
        public String procesar(@Valid Usuario usuario, BindingResult result, Model model){

            //validador.valiate(usuario, reuslt);

            if(result.hasErrors()){
                model.addAtribute("titulo", "Resultado form");
                return "form";
            }

            return "redirect:/ver";
        }

        @GetMapping("/ver")
                public String ver(@SessionAttribute(name="usuario", required = false)Usuario usuario, Model model, SessionStatus status) {
            if(usuario == null) {
                return "redirect:/form";
            }

            model.addAttribute("titulo", "Resultado form");

            status.setComplete();
            return "resultado";
        }
    }
}







