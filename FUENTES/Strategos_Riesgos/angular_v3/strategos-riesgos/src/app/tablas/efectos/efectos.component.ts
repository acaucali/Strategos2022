import { Component, OnInit } from '@angular/core';
import { Efectos } from './efectos';
import { EfectosService } from './efectos.service';
import { ModalService } from './detalle/modal.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-efectos',
  templateUrl: './efectos.component.html',
  styleUrls: ['./efectos.component.css']
})
export class EfectosComponent implements OnInit {

  pageEfecR: number =1;

  efectos: Efectos[];
  paginador: any;
  efectoSeleccionado: Efectos;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor( private efectosService: EfectosService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() { 
    this.getEfectos();
  }

  /* metodos controles */

  delete(efecto: Efectos): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el efecto ${efecto.efecto} ?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.efectosService.delete(efecto.efectoId).subscribe(
          response =>{
            this.getEfectos();
            swal.fire(
              'Efecto eliminado!',
              'El efecto ha sido eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(efecto: Efectos){
    this.efectoSeleccionado= efecto;
    this.modalservice.abrirModal();
  }

  crearEfecto(){
    this.efectoSeleccionado = new Efectos();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/efectos/pdf"); 
  }
  
  getEfectos(){
    this.efectos = null;
    this.elements = [];
    this.previous = [];
    this.efectosService.getEfectosList().subscribe(response =>{
      this.efectos = response;
      if(this.efectos.length >0){
        this.efectos.forEach(efe =>{
          this.elements.push({efectoId: efe.efectoId, efecto: efe.efecto, efectoDescripcion: efe.efectoDescripcion});
        });
      }
    });
  }

}
