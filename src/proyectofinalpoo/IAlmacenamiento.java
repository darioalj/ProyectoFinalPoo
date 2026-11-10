package proyectofinalpoo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author N04520
 * @param <T>
 */
public interface IAlmacenamiento<T> {
    void create(T data);
    T findOne(int id);
    T[] find();
    void update(int id, T data);
    void delete(int id);
}
    