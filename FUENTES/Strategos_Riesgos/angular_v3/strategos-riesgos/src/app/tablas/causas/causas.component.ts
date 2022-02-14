import { Component, OnInit, AfterViewInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Causas } from './causas';
import swal from 'sweetalert2';
import { CausasService } from './causas.service';
import { ActivatedRoute } from '@angular/router';
import { ModalService } from './detalle/modal.service';
import { WavesModule, TableModule, MdbTablePaginationComponent, MdbTableService } from 'angular-bootstrap-md';
import { URL_BACKEND } from 'src/app/config/config';

@Component({
  selector: 'app-causas',
  templateUrl: './causas.component.html',
  styleUrls: ['./causas.component.css']
})
export class CausasComponent implements OnInit{

  pageCauR: number =1;


  causas: Causas[];
  causaSeleccionada: Causas;
  causa: Causas = new Causas();

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor( private causasService: CausasService,
    private modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {

    this.getCausas();
  }

  

  /* metodos causas */

  delete(causa: Causas): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la causa ${causa.causaRiesgo} ?`,
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
        this.causasService.delete(causa.causaRiesgoId).subscribe(
          response =>{
            this.getCausas();
            swal.fire(
              'Causa eliminada!',
              'La causa se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(causa: Causas){
    this.causaSeleccionada= causa;
    this.modalservice.abrirModal();
  }

  crearCausa(){
    this.causaSeleccionada = new Causas();
    this.modalservice.abrirModal();
  }

  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/causariesgo/pdf"); 
  }

  getCausas(){
    this.causas = null;
    this.elements = [];
    this.previous = [];
    this.causasService.getCausasList().subscribe(response =>{
      this.causas = response;
      if(this.causas.length >0){
        this.causas.forEach(cau =>{
          this.elements.push({causaRiesgoId: cau.causaRiesgoId, causaRiesgo: cau.causaRiesgo, causaRiesgoDescripcion: cau.causaRiesgoDescripcion});
        });
      }
    
    });
  }
}
