import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TipoRiesgo } from '../tiporiesgo';
import { TiporiesgoService } from '../tiporiesgo.service';
import { TiposRiesgoComponent } from '../tipos-riesgo.component';


@Component({
  selector: 'detalle-riesgo',
  templateUrl: './detalleriesgo.component.html',
  styleUrls: ['./detalleriesgo.component.css']
})
export class DetalleRiesgo implements OnInit {
  private errores: string[];

  @Input() riesgo: TipoRiesgo;

  titulo: string = "Datos del Tipo Riesgo";
  constructor(private riesgoService: TiporiesgoService, private router: Router, 
    private activatedRoute: ActivatedRoute, private tipoRiesgoComponent: TiposRiesgoComponent,
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.riesgoService.create(this.riesgo).subscribe(
      json => {
      swal.fire('Nuevo Tipo Riesgo', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.tipoRiesgoComponent.getTipos();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    console.log(this.riesgo);
    this.riesgoService.update(this.riesgo).subscribe(json =>{
      swal.fire('Tipo de Riesgo Actualizado',  `${json.mensaje}`, 'success')
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
