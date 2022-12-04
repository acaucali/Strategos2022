import { Component, Input, OnInit } from '@angular/core';
import { Actividad } from '../../../../configuracion/model/actividad';
import { ModalService } from './modal.service';
import { Responsable } from '../../../../configuracion/model/responsable';
import { ActividadService } from '../../../../configuracion/services/actividad.service';
import { ResponsableService } from '../../../../configuracion/services/responsable.service';
import { TareasComponent } from '../tareas.component';
import swal from 'sweetalert2';
import { Unidad } from 'src/app/paginas/configuracion/model/unidad';
import { UnidadService } from 'src/app/paginas/configuracion/services/unidad.service';

@Component({
  selector: 'detalle-tarea',
  templateUrl: './detalle-tarea.component.html',
  styleUrls: ['./detalle-tarea.component.css']
})
export class DetalleTareaComponent implements OnInit {

  private errores: string[];
  @Input() tarea: Actividad = new Actividad;
  unidades: Unidad[];
  
  objetivo: string = "";
  titulo: string = "Datos de la Tarea";

  responsables: Responsable[];

  constructor(public modalservice: ModalService, public tareaService: ActividadService, private responsableService: ResponsableService, private tareaComponent: TareasComponent,
    public unidadService: UnidadService) { }

  ngOnInit(): void {
    this.responsableService.getResponsablesList().subscribe(response => {this.responsables = response});
    this.unidadService.getUnidadesList().subscribe(response =>{this.unidades = response});
    this.objetivo = localStorage.getItem('actividadNombre');
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  update(){

    this.tareaService.update(this.tarea).subscribe(json =>{
      swal.fire('Tarea Actualizada',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );

  }

  create(){

    this.tareaService.create(this.tarea, Number(localStorage.getItem('actividad')), Number(localStorage.getItem('objetivoId')), Number(localStorage.getItem('organizacion'))).subscribe(json =>{
      swal.fire('Nueva Tarea',  `${json.mensaje}`, 'success')
      this.cerrarModal();
      this.tareaComponent.getTareas();
    },err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );

  }

}
