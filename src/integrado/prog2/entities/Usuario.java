package integrado.prog2.entities;

import integrado.prog2.enums.Rol;
import java.util.Objects;

public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contraseña;
    private Rol rol;

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String apellido, String mail,
                   String celular, String contraseña, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public Usuario(Long id, String nombre, String apellido, String mail,
                   String celular, String contraseña, Rol rol) {
        super();
        this.setId(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }    

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    private String getMailNormalizado() {
        if (mail == null) {
            return "";
        }
        return mail.trim().toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Usuario)) {
            return false;
        }

        Usuario otro = (Usuario) obj;

        return this.getMailNormalizado().equals(otro.getMailNormalizado());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMailNormalizado());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", celular='" + celular + '\'' +
                ", rol=" + rol +
                '}';
    }
}
