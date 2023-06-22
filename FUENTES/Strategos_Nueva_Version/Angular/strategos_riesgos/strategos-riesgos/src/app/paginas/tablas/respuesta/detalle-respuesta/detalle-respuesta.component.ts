import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Respuesta } from 'src/app/paginas/configuracion/model/respuesta';
import { RespuestaService } from 'src/app/paginas/configuracion/services/respuesta.service';
import swal from 'sweetalert2';
import { RespuestaComponent } from '../respuesta.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-respuesta',
  templateUrl: './detalle-respuesta.component.html',
  styleUrls: ['./detalle-respuesta.component.css']
})
export class DetalleRespuestaComponent implements OnInit {

  public errores: string[];

  @Input() respuesta: Respuesta;

  titulo: string = "Datos de la Respuesta";
  constructor(private respuestaService: RespuestaService, private router: Router, 
    private activatedRoute: ActivatedRoute, private respuestaComponent: RespuestaComponent, 
    public modalservice: ModalService
   ) { }

  ngOnInit() {
  }

  
 
  create(): void{
    this.respuestaService.create(this.respuesta).subscribe(
      json => {
      swal.fire('Nueva Respuesta', `${json.mensaje}`, 'success');
      this.cerrarModal();
      this.respuestaComponent.getRespuesta();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
  }

  update(): void{
    this.respuestaService.update(this.respuesta).subscribe(json =>{
      swal.fire('Respuesta Actualizada',  `${json.mensaje}`, 'success')
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
