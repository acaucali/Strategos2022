import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import swal from 'sweetalert2';
import { Respuesta } from '../../configuracion/model/respuesta';
import { RespuestaService } from '../../configuracion/services/respuesta.service';
import { ModalService } from './detalle-respuesta/modal.service';

@Component({
  selector: 'app-respuesta',
  templateUrl: './respuesta.component.html',
  styleUrls: ['./respuesta.component.css']
})
export class RespuestaComponent implements OnInit {

  pageResR: number =1;

  respuestas: Respuesta[];
  paginador: any;
  respuestaSeleccionado: Respuesta; 

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private respuestaService: RespuestaService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.getRespuesta();
  }

  

  /* metodos respuesta */

  delete(respuesta: Respuesta): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la respuesta ${respuesta.respuesta} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.respuestaService.delete(respuesta.respuestaId).subscribe(
          response =>{
            this.getRespuesta();
            swal.fire(
              'Respuesta eliminada!',
              'La respuesta ha sido eliminada con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(respuesta: Respuesta){
    this.respuestaSeleccionado= respuesta;
    this.modalservice.abrirModal();
  }

  crearRespuesta(){
    this.respuestaSeleccionado = new Respuesta();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/respuesta/pdf"); 
  }

  getRespuesta(){
    this.respuestas = null;
    this.elements = [];
    this.previous = [];
    this.respuestaService.getRespuestasList().subscribe(response =>{
      this.respuestas = response;
      if(this.respuestas.length >0){
        this.respuestas.forEach(res =>{
          this.elements.push({respuestaId: res.respuestaId, respuesta: res.respuesta, respuestaDescripcion: res.respuestaDescripcion});
        });
      }
     
    });
  }

}
