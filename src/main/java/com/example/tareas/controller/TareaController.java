package com.example.tareas.controller;

import com.example.tareas.model.Tarea;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>();
    private final AtomicLong contador = new AtomicLong();

    @GetMapping("/")
    public String listarTareas(Model model) {
        model.addAttribute("tareas", tareas);
        return "tareas";
    }

    @PostMapping("/agregar")
    public String agregarTarea(@RequestParam("descripcion") String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            tareas.add(new Tarea(contador.incrementAndGet(), descripcion, false));
        }
        return "redirect:/";
    }

    @PostMapping("/toggle/{id}")
    public String toggleTarea(@PathVariable("id") long id) {
        for (Tarea t : tareas) {
            if (t.getId() == id) {
                t.setCompletada(!t.isCompletada());
                break;
            }
        }
        return "redirect:/";
    }

    @PostMapping("/editar/{id}")
    public String editarTarea(@PathVariable("id") long id,
                              @RequestParam("descripcion") String descripcion) {
        for (Tarea t : tareas) {
            if (t.getId() == id) {
                t.setDescripcion(descripcion);
                break;
            }
        }
        return "redirect:/";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable("id") long id) {
        Iterator<Tarea> iter = tareas.iterator();
        while (iter.hasNext()) {
            Tarea t = iter.next();
            if (t.getId() == id) {
                iter.remove();
                break;
            }
        }
        return "redirect:/";
    }
}
