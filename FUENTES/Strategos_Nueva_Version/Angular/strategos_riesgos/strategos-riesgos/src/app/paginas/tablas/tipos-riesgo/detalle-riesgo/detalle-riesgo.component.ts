import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TipoRiesgo } from 'src/app/paginas/configuracion/model/tiporiesgo';
import { TiporiesgoService } from 'src/app/paginas/configuracion/services/tiporiesgo.service';
import swal from 'sweetalert2';
import { TiposRiesgoComponent } from '../tipos-riesgo.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-riesgo',
  templateUrl: './detalle-riesgo.component.html',
  styleUrls: ['./detalle-riesgo.component.css']
})
export class DetalleRiesgoComponent implements OnInit {

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
