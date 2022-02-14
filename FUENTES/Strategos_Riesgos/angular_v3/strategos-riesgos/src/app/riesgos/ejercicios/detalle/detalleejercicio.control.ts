import { Component, OnInit, Input } from '@angular/core';
import { ModalService } from './modal.service';
import { Ejercicios } from '../ejercicios';
import { EjerciciosService } from '../ejercicios.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { EjerciciosComponent } from '../ejercicios.component';

@Component({
  selector: 'detalle-ejercicio',
  templateUrl: './detalleejercicio.component.html',
  styleUrls: ['./detalleejercicio.component.css']
})
export class DetalleEjercicio implements OnInit {

  titulo: string = "Datos del Ejercicio";
  @Input() ejercicio: Ejercicios;
  private errores: string[];

  constructor(private ejercicioService: EjerciciosService, private router: Router, 
    private activatedRoute: ActivatedRoute,
    public modalservice: ModalService, private ejerciciosComponent: EjerciciosComponent) { }

  ngOnInit() {
  }

  create(): void{
    this.ejercicioService.create(this.ejercicio).subscribe(
      json => {
      swal.fire('Nuevo Ejercicio', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.ejerciciosComponent.ejercicios= null;
      this.ejerciciosComponent.cargarEjercicios();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.ejercicioService.update(this.ejercicio).subscribe(json =>{
      swal.fire('Ejercicio Actualizado',  `${json.mensaje}`, 'success')
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
