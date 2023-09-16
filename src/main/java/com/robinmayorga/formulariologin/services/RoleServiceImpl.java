package com.robinmayorga.formulariologin.services;

import java.util.ArrayList;

public class RoleServiceImpl implements RoleService{

    private List<Role> roles;

    public RoleServiceImpl(){
        this.roles=new ArrayList<>();
        this.roles.add(new Role(1, "Administrador", "ROLE_ADMIN"));
        this.roles.add(new Role(2, "Usuario"));
        this.roles.add(new Role(3, "Moderador", "ROLE_MODERATOR"));
    }

    @Override
    public List<Role> listar(){return roles;}

    @Override
    public Role obtenerPorId(Integer id){
        Role reusultado = null;
        for(Role role: roles){
            if(id == role.getId()){
                resultado =role;
                break;
            }
        }
        return reusultado;
    }

}
