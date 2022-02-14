import { Component, OnInit, Input } from '@angular/core';
import swal from 'sweetalert2';
import { HttpEventType } from '@angular/common/http';
import { ModalService } from './modal.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Respuesta } from '../respuesta';
import { RespuestaService } from '../respuesta.service';
import { RespuestaComponent } from '../respuesta.component';



@Component({
  selector: 'detalle-respuesta',
  templateUrl: './detallerespuesta.component.html',
  styleUrls: ['./detallerespuesta.component.css']
})
export class DetalleRespuesta implements OnInit {
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
