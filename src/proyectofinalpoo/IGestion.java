/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo;

/**
 *
 * @author N04520
 * @param <T>
 */
public interface IGestion<T> {
    void registrar(T data);
    void modificar(int id, T data);
    void eliminar(int id);
}
