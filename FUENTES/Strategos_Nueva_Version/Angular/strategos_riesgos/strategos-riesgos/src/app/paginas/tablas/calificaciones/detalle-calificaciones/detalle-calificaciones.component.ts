import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Calificaciones } from '../../../configuracion/model/calificaciones';
import { CalificacionesService } from '../../../configuracion/services/calificaciones.service';
import { Router, ActivatedRoute } from '@angular/router';
import { CalificacionesComponent } from '../calificaciones.component';

@Component({
  selector: 'detalle-calificacion',
  templateUrl: './detalle-calificaciones.component.html',
  styleUrls: ['./detalle-calificaciones.component.css']
})
export class DetalleCalificacion implements OnInit {
  private errores: string[];
  colores: string[] = ['rojo', 'naranja', 'amarillo', 'verde']

  @Input() calificacion: Calificaciones;

  titulo: string = "Datos de la calificación";
  constructor(private calificacionesService: CalificacionesService, private router: Router, 
    private activatedRoute: ActivatedRoute, private calificacionComponent: CalificacionesComponent,
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.calificacionesService.create(this.calificacion).subscribe(
      json => {
      swal.fire('Nueva Calificación', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.calificacionComponent.getCalificaciones();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.calificacionesService.update(this.calificacion).subscribe(json =>{
      swal.fire('Calificación Actualizada',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }
}
