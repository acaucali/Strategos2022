import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import swal from 'sweetalert2';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { GestionideasComponent } from '../gestionideas.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detallegestion',
  templateUrl: './detallegestion.component.html',
  styleUrls: ['./detallegestion.component.css']
})
export class DetallegestionComponent implements OnInit {

  private errores: string[];
  @Input() idea: IdeasProyectos;
  
  titulo: string = "Datos de la idea";
  constructor(public modalservice: ModalService, private router: Router, 
    private activatedRoute: ActivatedRoute, private ideaService: IdeasProyectosService, private ideaComponent: GestionideasComponent) { }

  ngOnInit(): void {
  }

  create(): void{
    this.ideaService.create(this.idea).subscribe(
      json => {
      swal.fire('Nueva Idea', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.ideaComponent.getIdeas();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.ideaService.update(this.idea).subscribe(json =>{
      swal.fire('Idea Actualizada',  `${json.mensaje}`, 'success')
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
