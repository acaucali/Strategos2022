import { Component, Input, OnInit } from '@angular/core';
import { Perspectiva } from '../../../configuracion/model/perspectiva';
import { ModalService } from './modal.service';
import { PerspectivaService } from '../../../configuracion/services/perspectiva.service';
import swal from 'sweetalert2';
import { PlanService } from '../../../configuracion/services/plan.service';
import { ProyectoService } from '../../../configuracion/services/proyectos.service';
import { Plan } from '../../../configuracion/model/plan';
import { Proyectos } from '../../../configuracion/model/proyectos';
import { PlanComponent } from '../plan.component';

@Component({
  selector: 'detalle-perspectiva',
  templateUrl: './detalle-perspectiva.component.html',
  styleUrls: ['./detalle-perspectiva.component.css']
})
export class DetallePerspectivaComponent implements OnInit {

  private errores: string[];
  @Input() perspectiva: Perspectiva = new Perspectiva;
  objetivo: string = "prueba";

  titulo: string = "Datos de la Perspectiva";

  private plan: Plan = new Plan();
  private proyecto: Proyectos = new Proyectos();

  constructor(public modalservice: ModalService, public perspectivaService: PerspectivaService, public planService: PlanService, public proyectoService: ProyectoService,
    private objetivoComponent: PlanComponent) { }

  ngOnInit(): void {

  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  create(){
    this.perspectivaService.create(this.perspectiva, localStorage.getItem('planId'),localStorage.getItem('proyectoId')).subscribe(
      json => {
      swal.fire('Nuevo Objetivo', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.objetivoComponent.getPerspectivas();
      //this.ideaComponent.getIdeas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );

  }

  update(){
    this.perspectivaService.update(this.perspectiva).subscribe(json =>{
      swal.fire('Objetivo Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

}
