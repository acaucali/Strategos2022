import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { URL_BACKEND } from 'src/app/config/config';
import swal from 'sweetalert2';
import { EfectividadControles } from '../../configuracion/model/efectividad';
import { EfectividadService } from '../../configuracion/services/efectividad.service';
import { ModalService } from './detalle-efectividad/modal.service';

@Component({
  selector: 'app-efectividad',
  templateUrl: './efectividad.component.html',
  styleUrls: ['./efectividad.component.css']
})
export class EfectividadComponent implements OnInit {

  pageEfeR: number =1;

  efectividades: EfectividadControles[];
  efectividadSeleccionada: EfectividadControles;
  efectividad: EfectividadControles = new EfectividadControles();

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor( private efectividadService: EfectividadService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {

    this.getEfectividades();
  }

 

  /* metodos efectividad */

  delete(efectividad: EfectividadControles): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la efectividad ${efectividad.efectividad} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.efectividadService.delete(efectividad.efectividadId).subscribe(
          response =>{
            this.getEfectividades();
            swal.fire(
              'Efectividad eliminada!',
              'La efectividad se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(efectividad: EfectividadControles){
    this.efectividadSeleccionada= efectividad;
    this.modalservice.abrirModal();
  }

  crearEfectividad(){
    this.efectividadSeleccionada = new EfectividadControles();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/efectividad/pdf"); 
  }

  getEfectividades(){
    this.efectividades = null;
    this.elements = [];
    this.previous = [];
    this.efectividadService.getEfectividadList().subscribe(response =>{
      this.efectividades = response;
      if(this.efectividades.length >0){
        this.efectividades.forEach(efec =>{
          this.elements.push({efectividadId: efec.efectividadId, efectividad: efec.efectividad, efectividadPuntaje: efec.efectividadPuntaje});
        });
      }
  
    });
  }


}
