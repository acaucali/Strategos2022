import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CriteriosEvaluacion } from '../../../model/criteriosevaluacion';
import { CriteriosEvaluacionService } from '../../../services/criteriosevaluacion.service';
import { ModalService } from './modal.service';
import swal from 'sweetalert2';
import { CriteriosComponent } from '../criterios.component';

@Component({
  selector: 'detalle-criterio',
  templateUrl: './detalle-criterio.component.html',
  styleUrls: ['./detalle-criterio.component.css']
})
export class DetalleCriterioComponent implements OnInit {

  private errores: string[];
  @Input() criterio: CriteriosEvaluacion = new CriteriosEvaluacion;

  titulo: string = "Datos del criterio";
  constructor(public modalservice: ModalService, private router: Router, private criteriosService: CriteriosEvaluacionService, private criterioComponent: CriteriosComponent) { }

  ngOnInit(): void {
  }

  create(): void{
    
    this.criteriosService.create(this.criterio).subscribe(
      json => {
      swal.fire('Nuevo Criterio', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.criterioComponent.getCriterios();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.criteriosService.update(this.criterio).subscribe(json =>{
      swal.fire('Criterio Actualizado',  `${json.mensaje}`, 'success')
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
