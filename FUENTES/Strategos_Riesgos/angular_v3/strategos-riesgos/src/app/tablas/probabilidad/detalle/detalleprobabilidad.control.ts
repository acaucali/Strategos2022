import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Probabilidad } from '../probabilidad';
import { ProbabilidadService } from '../probabilidad.service';
import { ProbabilidadComponent } from '../probabilidad.component';


@Component({
  selector: 'detalle-probabilidad',
  templateUrl: './detalleprobabilidad.component.html',
  styleUrls: ['./detalleprobabilidad.component.css']
})
export class DetalleProbabilidad implements OnInit {
  public errores: string[];

  @Input() probabilidad: Probabilidad;

  titulo: string = "Datos de la Probabilidad";
  constructor(private probabilidadService: ProbabilidadService, private router: Router, 
    private activatedRoute: ActivatedRoute, private probabilidadComponent: ProbabilidadComponent,
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.probabilidadService.create(this.probabilidad).subscribe(
      json => {
      swal.fire('Nueva Probabilidad', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.probabilidadComponent.getProbabilidad();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.probabilidadService.update(this.probabilidad).subscribe(json =>{
      swal.fire('Probabilidad Actualizada',  `${json.mensaje}`, 'success')
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
