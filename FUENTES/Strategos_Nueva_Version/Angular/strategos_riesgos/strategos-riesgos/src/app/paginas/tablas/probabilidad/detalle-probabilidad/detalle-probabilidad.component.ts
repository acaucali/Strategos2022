import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Probabilidad } from 'src/app/paginas/configuracion/model/probabilidad';
import { ProbabilidadService } from 'src/app/paginas/configuracion/services/probabilidad.service';
import { ProbabilidadComponent } from '../probabilidad.component';
import { ModalService } from './modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'detalle-probabilidad',
  templateUrl: './detalle-probabilidad.component.html',
  styleUrls: ['./detalle-probabilidad.component.css']
})
export class DetalleProbabilidadComponent implements OnInit {

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
