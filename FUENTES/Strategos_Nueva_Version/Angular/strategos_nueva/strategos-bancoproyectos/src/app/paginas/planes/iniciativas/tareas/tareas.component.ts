import { Component, OnInit } from '@angular/core';
import { Actividad } from '../../../configuracion/model/actividad';
import { ModalService } from './detalle-tarea/modal.service';
import { ActividadService } from '../../../configuracion/services/actividad.service';
import swal from 'sweetalert2';
import { ModalConfigService } from './medicion-tareas/config-tarea/modal.service';
import { Router } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-tareas',
  templateUrl: './tareas.component.html',
  styleUrls: ['./tareas.component.css'],
})
export class TareasComponent implements OnInit {
  objetivo: any;
  actividad: any;
  actividadId: string;

  tareaSeleccionada: Actividad;
  tareas: Actividad[];

  pageTare: number = 1;

  constructor(
    public modalservice: ModalService,
    private actividadService: ActividadService,
    public modalConfigservice: ModalConfigService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (localStorage.getItem('objetivoId') != null) {
      var objetivo = Number(localStorage.getItem('objetivoId'));
      this.actividad = localStorage.getItem('actividadNombre');
      this.objetivo = localStorage.getItem('nombreObjetivo');
      this.actividadId = localStorage.getItem('actividad');
      this.actividadService
        .getActividadesProyecto(Number(localStorage.getItem('actividad')))
        .subscribe((response) => {
          this.tareas = response;
        });
    }
  }

  regresar() {}

  crearTarea() {
    this.tareaSeleccionada = new Actividad();
    this.modalservice.abrirModal();
  }

  editarTarea(tar: Actividad) {
    this.tareaSeleccionada = tar;
    this.modalservice.abrirModal();
  }

  eliminarTarea(tar: Actividad) {
    swal
      .fire({
        title: 'Está seguro?',
        text: `¿Seguro que desea eliminar la actividad?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, eliminar!',
        cancelButtonText: 'No, cancelar!',
      })
      .then((result) => {
        if (result.isConfirmed) {
          this.actividadService
            .delete(tar.actividadId)
            .subscribe((response) => {
              this.getTareas();
              swal.fire(
                'Actividad eliminada!',
                'La actividad ha sido eliminada con éxito',
                'success'
              );
            });
        }
      });
  }

  getTareas() {
    this.tareas = [];
    this.actividadService
      .getActividadesProyecto(Number(localStorage.getItem('actividad')))
      .subscribe((response) => {
        this.tareas = response;
      });
  }

  cargarMedicion() {
    this.modalConfigservice.abrirModal();
  }

  presupuesto(tar: Actividad) {
    localStorage.setItem('tarea', tar.actividadId.toString());
    localStorage.setItem('tareaNombre', tar.nombreActividad.toString());
    this.router.navigate(['/', 'presupuesto']);
  }

  descargarXls() {
    window.open(
      URL_BACKEND +
        '/api/strategos/bancoproyectos/presupuesto/excel/' +
        this.actividadId
    );
  }
}
