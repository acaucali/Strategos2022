import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Ejercicios } from 'src/app/paginas/configuracion/model/ejercicios';
import { EjerciciosService } from 'src/app/paginas/configuracion/services/ejercicios.service';
import { EjerciciosComponent } from '../ejercicios.component';
import { ModalService } from './modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'detalle-ejercicio',
  templateUrl: './detalle-ejercicios.component.html',
  styleUrls: ['./detalle-ejercicios.component.css']
})
export class DetalleEjerciciosComponent implements OnInit {

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
